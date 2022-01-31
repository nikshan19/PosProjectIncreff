package table.Dao;

import java.util.HashMap;
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
import table.Pojo.ProductPojo;
import table.Service.ApiException;

@Repository
public class OrderItemDao2 {

	private static String delete_id = "delete from OrderItemPojo p where id=:id";
	private static String select_id = "select p from OrderItemPojo p where id=:id";
	private static String select_barcode_product = "select p from ProductPojo p where barcode=:barcode";
	private static String select_product = "select p from ProductPojo p where id=:id";
	private static String select_inventory = "select p from InventoryPojo p where id=:id";
	@PersistenceContext
	EntityManager em;

	public int delete(int id) throws ApiException {
		OrderItemPojo p;
		try {
			TypedQuery<OrderItemPojo> query = getQuery(select_id);
			query.setParameter("id", id);
			p = query.getSingleResult();
		} catch (NoResultException e) {
			p = null;
		}

		if (p == null) {
			throw new ApiException("OrderItem with given id doesnot exist");
		}

		Query query = em.createQuery(delete_id);
		query.setParameter("id", id);

		TypedQuery<InventoryPojo> query1 = em.createQuery(select_inventory, InventoryPojo.class);
		query1.setParameter("id", p.getProductId());
		InventoryPojo ip = query1.getSingleResult();
		int addv = p.getQuantity() + ip.getQuantity();
		ip.setQuantity(addv);

		return query.executeUpdate();

	}

	public HashMap<OrderItemPojo, String> select(int id) {
		OrderItemPojo p;
		try {
			TypedQuery<OrderItemPojo> query = getQuery(select_id);
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

	public OrderItemPojo onlySelect(int id) {
		OrderItemPojo p;
		try {
			TypedQuery<OrderItemPojo> query = getQuery(select_id);
			query.setParameter("id", id);
			p = query.getSingleResult();
		} catch (NoResultException e) {
			p = null;
		}
		return p;

	}

	public void update(OrderItemPojo p, String barcode, int quantity, double mrp) throws ApiException {
		ProductPojo pp;
		try {
			TypedQuery<ProductPojo> query1 = em.createQuery(select_barcode_product, ProductPojo.class);
			query1.setParameter("barcode", barcode);
			pp = query1.getSingleResult();
		} catch (NoResultException e) {

			throw new ApiException("no product exist with barcode: " + barcode);
		}

		if (pp.getMrp() < mrp) {
			throw new ApiException("Selling Price: " + mrp + "cannot be greater than MRP; " + pp.getMrp());
		}
		p.setMrp(mrp);

		p.setProductId(pp.getId());

		TypedQuery<InventoryPojo> qIp2 = em.createQuery(select_inventory, InventoryPojo.class);
		qIp2.setParameter("id", p.getProductId());
		InventoryPojo ip2 = qIp2.getSingleResult();

		int add_value = ip2.getQuantity() - quantity;
		if (add_value < 0) {
			throw new ApiException("qunatity in inventory has value less than added :" + ip2.getQuantity());
		} else {
			p.setQuantity(quantity);

		}

	}

	TypedQuery<OrderItemPojo> getQuery(String jpql) {
		return em.createQuery(jpql, OrderItemPojo.class);

	}

}
