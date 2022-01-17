package table.Dao;

import java.util.HashMap;
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
import table.Pojo.ProductPojo;
import table.Service.ApiException;


@Repository
public class OrderItemDao {
	
	private static String delete_id = "delete from OrderItemPojo p where orderId=:orderId";
	private static String select_id = "select p from OrderItemPojo p where id=:id";
	private static String select_all = "select p from OrderItemPojo p";
	private static String select_barcode = "select p from ProductPojo p where barcode=:barcode";
	private static String select_productid = "select p from ProductPojo p where id=:id";
	private static String select_all_order = "select p from OrderPojo p";
	private static String delete_order_id = "delete from OrderPojo p where id=:id";
	private static String select_all_order_id = "select p from OrderItemPojo p where orderId=:orderId";
	private static String select_quantity = "select p from InventoryPojo p where id=:id";
	
	@PersistenceContext
	EntityManager em;
	
	
	public void insert(OrderItemPojo p, String barcode, double mrp) throws ApiException {
		ProductPojo pp;
		try {
			TypedQuery<ProductPojo> query = em.createQuery(select_barcode, ProductPojo.class);
			query.setParameter("barcode", barcode);
			pp = query.getSingleResult();
		}catch(NoResultException e){
			pp=null;
		}
		TypedQuery<OrderPojo> q = em.createQuery(select_all_order, OrderPojo.class);
		int order_id = q.getResultList().get(q.getResultList().size()-1).getId();
		
		
		
		if(pp==null || pp.getMrp()!= mrp) {
			Query qO = em.createQuery(delete_order_id);
			qO.setParameter("id", order_id);
			qO.executeUpdate();
			throw new ApiException("product doesnot exist with barcode: "+barcode+" and mrp: "+mrp);
		}
		
		p.setProductId(pp.getId());
		p.setMrp(mrp);
		
		
		p.setOrderId(order_id);
		TypedQuery<InventoryPojo> qIp = em.createQuery(select_quantity, InventoryPojo.class);
		qIp.setParameter("id", p.getProductId());
		InventoryPojo ip = qIp.getSingleResult();
		if(p.getQuantity()>ip.getQuantity()) {
			Query qO = em.createQuery(delete_order_id);
			qO.setParameter("id", p.getOrderId());
			qO.executeUpdate();
			throw new ApiException("qunatity in inventory has value less than added :"+ip.getQuantity()); 
			
		}
		else {
			System.out.println(ip.getQuantity());
			System.out.println(p.getQuantity());
			ip.setQuantity(ip.getQuantity()-p.getQuantity());
			System.out.println(ip.getQuantity());
		}
		
		em.persist(p);
	}
	
	public int delete(int id) throws ApiException {
		
		Query query = em.createQuery(delete_id);
		query.setParameter("orderId", id);
		return query.executeUpdate();
		
	}
	
	public HashMap<OrderItemPojo, String> select(int id) {
		TypedQuery<OrderItemPojo> query = em.createQuery(select_all_order_id, OrderItemPojo.class);
		query.setParameter("orderId", id);
		List<OrderItemPojo> l1= query.getResultList();
		HashMap<OrderItemPojo,String> hm = new HashMap<OrderItemPojo,String>();
		for(OrderItemPojo o:l1) {
			TypedQuery<ProductPojo> q = em.createQuery(select_productid, ProductPojo.class);
			q.setParameter("id", o.getProductId());
			
			hm.put(o, q.getSingleResult().getBarcode());
			
		}
		return hm;
			
		}
	
	public OrderItemPojo onlySelect(int id) {
		TypedQuery<OrderItemPojo> query = getQuery(select_id);
		query.setParameter("id", id);
		return query.getSingleResult();
		
	}
	
	
	public HashMap<OrderItemPojo, String> selectAll() {
		TypedQuery<OrderItemPojo> query = getQuery(select_all);
		List<OrderItemPojo> l1 = query.getResultList();
		HashMap<OrderItemPojo,String> hm = new HashMap<OrderItemPojo,String>();
		for(OrderItemPojo o:l1) {
			TypedQuery<ProductPojo> q = em.createQuery(select_productid, ProductPojo.class);
			q.setParameter("id", o.getProductId());
			
			hm.put(o, q.getSingleResult().getBarcode());
			
		}
		return hm;
	}
	
	public void update(OrderItemPojo p, String barcode, int quantity) throws ApiException {
		TypedQuery<ProductPojo> query = em.createQuery(select_barcode, ProductPojo.class);
		query.setParameter("barcode", barcode);
		p.setProductId(query.getSingleResult().getId());
		TypedQuery<InventoryPojo> qIp = em.createQuery(select_quantity, InventoryPojo.class);
		qIp.setParameter("id", p.getProductId());
		InventoryPojo ip = qIp.getSingleResult();
		int add_value = ip.getQuantity()+ p.getQuantity()-quantity;
		if(add_value<0) {
			throw new ApiException("qunatity in inventory has value less than added :"+ip.getQuantity());
		}
		else {
			p.setQuantity(quantity);
			ip.setQuantity(add_value);
		}
		
		
	}
	
	TypedQuery<OrderItemPojo> getQuery(String jpql){
		return em.createQuery(jpql, OrderItemPojo.class);
		
	}


}
