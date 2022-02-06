package table.Dao;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import table.Pojo.InventoryPojo;
import table.Pojo.OrderItemPojo;
import table.Pojo.OrderPojo;
import table.Service.ApiException;

@Repository
public class OrderDao {

	private static String delete_id = "delete from OrderPojo p where id=:id";
	private static String select_id = "select p from OrderPojo p where id=:id";
	private static String select_all = "select p from OrderPojo p";
	private static String select_all_with_0 = "select p from OrderItemPojo p where orderId=:id";
	private static String select_quantity = "select p from InventoryPojo p where id=:id";
	private static String delete_orderItem = "delete from OrderItemPojo p where id=:id";
	
	

	@PersistenceContext
	EntityManager em;

	public void insert(OrderPojo p) throws ApiException {
		TypedQuery<OrderItemPojo> query1 = em.createQuery(select_all_with_0, OrderItemPojo.class);
		query1.setParameter("id", 0);
		List<OrderItemPojo> l = query1.getResultList();

		if (l.size() == 0) {
			throw new ApiException("No item selected in order");
		}

		else {
			p.setToggle(0);
			em.persist(p);
			TypedQuery<OrderPojo> query = getQuery(select_all);

			int order_id = query.getResultList().get(query.getResultList().size() - 1).getId();

			for (OrderItemPojo oi : l) {
				oi.setOrderId(order_id);
				TypedQuery<InventoryPojo> qIp = em.createQuery(select_quantity, InventoryPojo.class);
				qIp.setParameter("id", oi.getProductId());
				InventoryPojo ip = qIp.getSingleResult();
				if (oi.getQuantity() > ip.getQuantity()) {

					throw new ApiException("qunatity in inventory has value less than added :" + ip.getQuantity());
				} else {

					ip.setQuantity(ip.getQuantity() - oi.getQuantity());
				}

			}
		}

	}

	public void delete(int id) {
		TypedQuery<OrderItemPojo> query = em.createQuery(select_all_with_0, OrderItemPojo.class);
		query.setParameter("id", id);
		List<OrderItemPojo> l = query.getResultList();
		for(OrderItemPojo p: l) {
			TypedQuery<InventoryPojo> q = em.createQuery(select_quantity, InventoryPojo.class);
			q.setParameter("id", p.getProductId());
			InventoryPojo ip = q.getSingleResult();
			ip.setQuantity(ip.getQuantity()+p.getQuantity());
			Query dq = em.createQuery(delete_orderItem);
			dq.setParameter("id", p.getId());
			dq.executeUpdate();
			
		}
		TypedQuery<OrderPojo> dq = getQuery(select_id);
		dq.setParameter("id", id);
		OrderPojo op = dq.getSingleResult();
		op.setToggle(2);
		}

	public OrderPojo select(int id) {
		OrderPojo p;
		try {
			TypedQuery<OrderPojo> query = getQuery(select_id);
			query.setParameter("id", id);
			p = query.getSingleResult();
		} catch (NoResultException e) {
			p = null;
		}
		return p;
	}

	public List<OrderPojo> selectAll() {
		TypedQuery<OrderPojo> query = getQuery(select_all);
		List<OrderPojo> l = query.getResultList();
		Collections.reverse(l);
		return l;
	}

	public void update(int id) {
		OrderPojo p;
		try {
			TypedQuery<OrderPojo> query = getQuery(select_id);
			query.setParameter("id", id);
			p = query.getSingleResult();
		} catch (NoResultException e) {
			p = null;
		}
		if (p!=null) {
			p.setToggle(1);
			 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
			 Date date = new Date();  
			String s = String.valueOf(formatter.format(date));
			p.setdT(s);
		}

	}

	TypedQuery<OrderPojo> getQuery(String jpql) {
		return em.createQuery(jpql, OrderPojo.class);

	}

}
