package table.Dao;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import table.Model.SalesReportForm;
import table.Pojo.BrandPojo;
import table.Pojo.InventoryPojo;
import table.Pojo.OrderItemPojo;
import table.Pojo.OrderPojo;
import table.Pojo.ProductPojo;


@Repository
public class SalesReportDao {
	
	private static String select_dt_order = "select p from OrderPojo p where dateTime BETWEEN :dt1 AND :dt2";
	private static String select_oid_product = "select p from ProductPojo p where id=:id";
	private static String select_brand_cat_brand = "select p from BrandPojo p where brand=:brand AND category=:category";
	private static String select_oid_orderitem = "select p from OrderItemPojo p where orderId=:orderId";
	private static String join_order_and_orderitems = "select o from OrderPojo o AND oi from OrderItemPojo oi o.id=:oi.orderId";
	
	@PersistenceContext
	EntityManager em;

	public HashMap<Integer, Double> selectAll(SalesReportForm form) {
		String startdate = form.getStartdate() +" 00:00:00.0";
		String enddate = form.getEnddate()+" 00:00:00.0";
		TypedQuery<OrderPojo> query = em.createQuery(select_dt_order, OrderPojo.class);
		query.setParameter("dt1", Timestamp.valueOf(startdate));
		query.setParameter("dt2", Timestamp.valueOf(enddate));
		List<OrderPojo> lo = query.getResultList();
		HashMap<Integer, Double> hm = new HashMap<Integer, Double>();
		double rev = 0;
		int quant = 0;
		for(OrderPojo o:lo) {
			TypedQuery<OrderItemPojo> q1 = em.createQuery(select_oid_orderitem, OrderItemPojo.class);
			q1.setParameter("orderId", o.getId());
			List<OrderItemPojo> oip = q1.getResultList();
			for(OrderItemPojo p:oip) {
				TypedQuery<BrandPojo> q2 = em.createQuery(select_brand_cat_brand, BrandPojo.class);
				q2.setParameter("brand", form.getBrand());
				q2.setParameter("category", form.getCategory());
				BrandPojo b = q2.getSingleResult();
				TypedQuery<ProductPojo> qp = em.createQuery(select_oid_product, ProductPojo.class);
				qp.setParameter("id", p.getProductId());
				ProductPojo pp = qp.getSingleResult();
				if(pp.getBrandPojo()==b.getId()) {
					
					quant+=p.getQuantity();
					rev = quant*p.getMrp();
					
				}
			}
			}
		hm.put(quant, rev);
		return hm;
	}
	
	public void select_everyOne(SalesReportForm form) {
		
		
		
	}
	
	
	
	

}
