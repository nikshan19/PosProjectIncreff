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
import table.Pojo.ProductPojo;
import table.Service.ApiException;


@Repository
public class PDFGeneratorDao {
	

	private static String select_id = "select p from BrandPojo p where id=:id";
	private static String select_idP = "select p from ProductPojo p where id=:id";
	
	@PersistenceContext
	EntityManager em;
	
	
	public List<String> get(OrderItemData p) {
		
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
	

}
