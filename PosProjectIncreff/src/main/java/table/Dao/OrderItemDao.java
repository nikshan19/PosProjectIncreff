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
import table.Pojo.OrderPojo;
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
	private static String select_product = "select p from ProductPojo p where id=:id";
	private static String select_inventory = "select p from InventoryPojo p where id=:id";
	private static String select_barcode_product = "select p from ProductPojo p where barcode=:barcode";
	private static String select_idd = "select p from OrderItemPojo p where id=:id";
	private static String delete_idd = "delete from OrderItemPojo p where id=:id";
	private static String select_order_id = "select p from OrderPojo p where id=:id";

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

			throw new ApiException("Product doesnot exist with barcode: '" + barcode + "'");
		}

		if (pp.getMrp() < mrp) {

			throw new ApiException(
					"Product's MRP: '" + pp.getMrp() + "' is less than selling price entered: '" + mrp + "'");
		}

		p.setProductId(pp.getId());
		p.setMrp(mrp);

		p.setOrderId(order_id);
		TypedQuery<InventoryPojo> qIp = em.createQuery(select_quantity, InventoryPojo.class);
		qIp.setParameter("id", p.getProductId());
		InventoryPojo ip = qIp.getSingleResult();
		if (p.getQuantity() > ip.getQuantity()) {

			throw new ApiException("Qunatity limit : '" + ip.getQuantity() + "'");

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

	public void delete(int id) throws ApiException {
		OrderItemPojo p;
		try {
			TypedQuery<OrderItemPojo> query = getQuery(select_idd);
			query.setParameter("id", id);
			p = query.getSingleResult();
		} catch (NoResultException e) {
			p = null;
		}

		if (p == null) {
			throw new ApiException("OrderItem with given barcode doesnot exist");
		}

		Query query = em.createQuery(delete_idd);
		query.setParameter("id", id);

		TypedQuery<InventoryPojo> query1 = em.createQuery(select_inventory, InventoryPojo.class);
		query1.setParameter("id", p.getProductId());
		InventoryPojo ip = query1.getSingleResult();
		int addv = p.getQuantity() + ip.getQuantity();
		ip.setQuantity(addv);

		TypedQuery<OrderItemPojo> qq = em.createQuery(select_all_order_id, OrderItemPojo.class);
		qq.setParameter("orderId", p.getOrderId());
		List<OrderItemPojo> ll = qq.getResultList();
		int orderId = p.getOrderId();
		query.executeUpdate();
		if (ll.size() == 1) {
			TypedQuery<OrderPojo> dq = em.createQuery(select_order_id, OrderPojo.class);
			dq.setParameter("id", orderId);
			OrderPojo op = dq.getSingleResult();
			op.setToggle(2);
		}

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

	public HashMap<OrderItemPojo, String> selectAll(int id) {
		OrderItemPojo p;
		try {
			TypedQuery<OrderItemPojo> query = getQuery(select_idd);
			query.setParameter("id", id);
			p = query.getSingleResult();
		} catch (NoResultException e) {
			p = null;
		}

		TypedQuery<ProductPojo> query1 = em.createQuery(select_product, ProductPojo.class);
		query1.setParameter("id", p.getProductId());
		String barcode = query1.getSingleResult().getBarcode();
		HashMap<OrderItemPojo, String> hm = new HashMap<OrderItemPojo, String>();
		hm.put(p, barcode);
		return hm;
	}

	public void update(OrderItemPojo p, String barcode, int quantity, double mrp) throws ApiException {
		ProductPojo pp;
		try {
			TypedQuery<ProductPojo> query1 = em.createQuery(select_barcode_product, ProductPojo.class);
			query1.setParameter("barcode", barcode);
			pp = query1.getSingleResult();
		} catch (NoResultException e) {

			throw new ApiException("Product with barcode: '" + barcode + "' doesnot exist");
		}

		if (pp.getMrp() < mrp) {
			throw new ApiException("Selling Price: '" + mrp + "' cannot be greater than MRP: '" + pp.getMrp() + "'");
		}

		TypedQuery<InventoryPojo> qIp = em.createQuery(select_inventory, InventoryPojo.class);
		qIp.setParameter("id", p.getProductId());
		InventoryPojo ip = qIp.getSingleResult();
		ip.setQuantity(p.getQuantity() + ip.getQuantity());

		p.setMrp(mrp);
		p.setProductId(pp.getId());

		TypedQuery<InventoryPojo> qIp2 = em.createQuery(select_inventory, InventoryPojo.class);
		qIp2.setParameter("id", p.getProductId());
		InventoryPojo ip2 = qIp2.getSingleResult();

		int add_value = ip2.getQuantity() - quantity;
		if (add_value < 0) {
			throw new ApiException("Qunatity limit: '" + ip2.getQuantity() + "'");
		} else {
			p.setQuantity(quantity);
			ip2.setQuantity(add_value);

		}

	}

	public OrderItemPojo onlySelect2(int id) {
		OrderItemPojo p;
		try {
			TypedQuery<OrderItemPojo> query = getQuery(select_idd);
			query.setParameter("id", id);
			p = query.getSingleResult();
		} catch (NoResultException e) {
			p = null;
		}
		return p;

	}

	TypedQuery<OrderItemPojo> getQuery(String jpql) {
		return em.createQuery(jpql, OrderItemPojo.class);

	}

}
