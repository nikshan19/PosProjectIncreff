package table.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import table.Pojo.BrandPojo;
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

	public int delete(int id) {
		Query query = em.createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
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
		return query.getResultList();
	}

	public void update(OrderPojo p) {

	}

	TypedQuery<OrderPojo> getQuery(String jpql) {
		return em.createQuery(jpql, OrderPojo.class);

	}

}
