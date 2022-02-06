package table.Dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import table.Model.OrderItemData;
import table.Pojo.BrandPojo;
import table.Pojo.OrderItemPojo;
import table.Pojo.OrderPojo;
import table.Pojo.ProductPojo;
import table.Service.ApiException;


@Repository
public class PDFGeneratorDao {
	

	private static String select_id = "select p from BrandPojo p where id=:id";
	private static String select_idP = "select p from ProductPojo p where id=:id";
	private static String select_all_order = "select p from OrderPojo p";
	private static String select_an_order = "select p from OrderPojo p where id=:id";
	private static String select_oi = "select p from OrderItemPojo p where orderId=:id";
	
	@PersistenceContext
	EntityManager em;
	
	
	public List<String> get(OrderItemPojo p) {
		
		TypedQuery<ProductPojo> query = em.createQuery(select_idP, ProductPojo.class);
		query.setParameter("id", p.getProductId());
		ProductPojo pp = query.getSingleResult();
		TypedQuery<BrandPojo> query1 = em.createQuery(select_id, BrandPojo.class);
		query1.setParameter("id", pp.getBrandPojo());
		BrandPojo bp = query1.getSingleResult();
		List<String> l = new ArrayList<String>();
		l.add(bp.getBrand());
		l.add(pp.getName());
		return l;
		
	}
	
	public List<OrderItemPojo> getList(){
		
		TypedQuery<OrderPojo> query = em.createQuery(select_all_order, OrderPojo.class);
		List<OrderPojo> l = query.getResultList();
		int latest = l.get(l.size()-1).getId();
		TypedQuery<OrderItemPojo> q = em.createQuery(select_oi, OrderItemPojo.class);
		q.setParameter("id", latest);
		List<OrderItemPojo> ll = q.getResultList();
		return ll;
		
		
	}
	
	public List<OrderItemPojo> getSpecList(int id){
		TypedQuery<OrderItemPojo> query = em.createQuery(select_oi, OrderItemPojo.class);
		query.setParameter("id", id);
		List<OrderItemPojo> l = query.getResultList();
		return l;
		
	}
	
	public OrderPojo getOrder(int orderId) {
		TypedQuery<OrderPojo> query = em.createQuery(select_an_order, OrderPojo.class);
		query.setParameter("id", orderId);
		return query.getSingleResult();
		
	}
	
	

}
