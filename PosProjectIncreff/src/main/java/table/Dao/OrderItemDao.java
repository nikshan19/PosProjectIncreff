package table.Dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import table.Model.OrderItemData;
import table.Pojo.InventoryPojo;
import table.Pojo.OrderItemPojo;
import table.Pojo.ProductPojo;
import table.Service.ApiException;

@Repository
public class OrderItemDao {

	private static String delete_id = "delete from OrderItemPojo p where orderId=:orderId";
	private static String select_id = "select p from OrderItemPojo p where id=:id AND orderId=:orderId";
	private static String select_all = "select p from OrderItemPojo p";
	private static String select_barcode = "select p from ProductPojo p where barcode=:barcode";
	private static String select_productid = "select p from ProductPojo p where id=:id";
	private static String select_all_order = "select p from OrderPojo p";
	private static String delete_order_id = "delete from OrderPojo p where id=:id";
	private static String select_all_order_id = "select p from OrderItemPojo p where orderId=:orderId";
	private static String select_quantity = "select p from InventoryPojo p where id=:id";
	private static String select_2 = "select p from OrderItemPojo p where productId=:productId AND mrp=:mrp AND orderId=:orderId";

	@PersistenceContext
	EntityManager em;

	public void insert(OrderItemPojo p, String barcode, double mrp) throws ApiException {
		ProductPojo pp;
		try {
			TypedQuery<ProductPojo> query = em.createQuery(select_barcode, ProductPojo.class);
			query.setParameter("barcode", barcode);
			pp = query.getSingleResult();
		} catch (NoResultException e) {
			pp = null;
		}
		int order_id = 0;

		if (pp == null) {

			throw new ApiException("product doesnot exist with barcode: " + barcode);
		}

		if (pp.getMrp() < mrp) {

			throw new ApiException("product MRP:" + pp.getMrp() + " is less than selling price entered: " + mrp);
		}

		p.setProductId(pp.getId());
		p.setMrp(mrp);

		p.setOrderId(order_id);
		TypedQuery<InventoryPojo> qIp = em.createQuery(select_quantity, InventoryPojo.class);
		qIp.setParameter("id", p.getProductId());
		InventoryPojo ip = qIp.getSingleResult();
		if (p.getQuantity() > ip.getQuantity()) {

			throw new ApiException("qunatity in inventory has value less than added :" + ip.getQuantity());

		}

		OrderItemPojo op;
		try {
			TypedQuery<OrderItemPojo> query = getQuery(select_2);
			query.setParameter("productId", p.getProductId());
			query.setParameter("mrp", mrp);
			query.setParameter("orderId", 0);
			op = query.getSingleResult();
		} catch (NoResultException e) {
			op = null;
		}
		if (op == null) {
			em.persist(p);
		} else {
			op.setQuantity(op.getQuantity() + p.getQuantity());
		}

	}

	public int delete(int id) throws ApiException {

		Query query = em.createQuery(delete_id);
		query.setParameter("orderId", id);
		return query.executeUpdate();

	}

	public List<OrderItemData> select(int id) {
		TypedQuery<OrderItemPojo> query = em.createQuery(select_all_order_id, OrderItemPojo.class);
		query.setParameter("orderId", id);
		List<OrderItemPojo> l1 = query.getResultList();
		List<OrderItemData> l = new ArrayList<OrderItemData>();
		for (OrderItemPojo o : l1) {
			TypedQuery<ProductPojo> q = em.createQuery(select_productid, ProductPojo.class);
			q.setParameter("id", o.getProductId());

			OrderItemData d = new OrderItemData();
			d.setBarcode(q.getSingleResult().getBarcode());
			d.setId(o.getId());
			d.setOrderId(o.getOrderId());
			d.setProductId(o.getProductId());
			d.setQuantity(o.getQuantity());
			d.setMrp(o.getMrp());
			l.add(d);

		}
		Collections.sort(l);
		return l;

	}

	public OrderItemPojo onlySelect(int id, int orderId) {
		OrderItemPojo p;
		try {
			TypedQuery<OrderItemPojo> query = getQuery(select_id);
			query.setParameter("id", id);
			query.setParameter("orderId", orderId);
			p = query.getSingleResult();
		} catch (NoResultException e) {
			p = null;
		}
		return p;

	}

	public HashMap<String, OrderItemPojo> selectAll() {
		TypedQuery<OrderItemPojo> query = getQuery(select_all);
		List<OrderItemPojo> l1 = query.getResultList();
		HashMap<String, OrderItemPojo> hm = new HashMap<String, OrderItemPojo>();
		for (OrderItemPojo o : l1) {
			TypedQuery<ProductPojo> q = em.createQuery(select_productid, ProductPojo.class);
			q.setParameter("id", o.getProductId());

			hm.put(q.getSingleResult().getBarcode(), o);

		}
		HashMap<String, OrderItemPojo> hml = sortHash(hm);

		return hm;
	}

	public HashMap<String, OrderItemPojo> sortHash(HashMap<String, OrderItemPojo> hm) {

		List<String> l = new ArrayList<String>(hm.keySet());
		Collections.sort(l);
		HashMap<String, OrderItemPojo> hml = new HashMap<String, OrderItemPojo>();
		for (String s : l) {
			hml.put(s, hm.get(s));
		}
		return hml;

	}

	public void update(OrderItemPojo p, String barcode, int quantity) throws ApiException {
		ProductPojo pp;
		try {
			TypedQuery<ProductPojo> query = em.createQuery(select_barcode, ProductPojo.class);
			query.setParameter("barcode", barcode);
			pp = query.getSingleResult();
		} catch (NoResultException e) {
			throw new ApiException("product doesnot exist with barcode: " + barcode);
		}
		int old_pid = p.getProductId();
		TypedQuery<InventoryPojo> qIp = em.createQuery(select_quantity, InventoryPojo.class);
		qIp.setParameter("id", old_pid);
		InventoryPojo ip = qIp.getSingleResult();
		ip.setQuantity(p.getQuantity() + ip.getQuantity());

		p.setProductId(pp.getId());

		TypedQuery<InventoryPojo> qIp2 = em.createQuery(select_quantity, InventoryPojo.class);
		qIp2.setParameter("id", p.getProductId());
		InventoryPojo ip2 = qIp2.getSingleResult();

		int add_value = ip2.getQuantity() - quantity;
		if (add_value < 0) {
			throw new ApiException("qunatity in inventory has value less than added :" + ip.getQuantity());
		} else {
			p.setQuantity(quantity);
			ip2.setQuantity(add_value);
		}

		if (p.getMrp() != pp.getMrp()) {
			throw new ApiException("MRP of this product is: " + pp.getMrp());
		}

	}

	TypedQuery<OrderItemPojo> getQuery(String jpql) {
		return em.createQuery(jpql, OrderItemPojo.class);

	}

}
