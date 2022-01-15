package table.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import table.Pojo.BrandPojo;
import table.Pojo.InventoryPojo;
import table.Pojo.ProductPojo;
import table.Service.ApiException;


@Repository
public class ProductDao {
	
	private static String delete_id = "delete from ProductPojo p where id=:id";
	private static String select_id = "select p from ProductPojo p where id=:id";
	private static String select_all = "select p from ProductPojo p";
	private static String select_idB = "select p from BrandPojo p where id=:id";
	private static String select_idP = "select p from InventoryPojo p where id=:id";
	
	@PersistenceContext
	EntityManager em;
	
	
	public void insert(ProductPojo p)throws ApiException {
		TypedQuery<BrandPojo> query = em.createQuery(select_idB, BrandPojo.class);
		query.setParameter("id", p.getBrandPojo());
		if(query.getSingleResult()==null) {
			throw new ApiException("brand with given id doesnot exists, id: "+p.getBrandPojo());
		}
		else {
		em.persist(p);
		}
	}
	
	public int delete(int id) throws ApiException {
		TypedQuery<InventoryPojo> q = em.createQuery(select_idP, InventoryPojo.class);
		q.setParameter("id", id);
		if(q.getResultList().size()!=0) {
			throw new ApiException("product with given id exists, id: "+ id);	
			}
		else {
		Query query = em.createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
		}
	}
	
	public ProductPojo select(int id) {
		TypedQuery<ProductPojo> query = getQuery(select_id);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	public List<ProductPojo> selectAll() {
		TypedQuery<ProductPojo> query = getQuery(select_all);
		return query.getResultList();
	}
	
	public void update(ProductPojo p ) {
		
		
	}
	
	TypedQuery<ProductPojo> getQuery(String jpql){
		return em.createQuery(jpql, ProductPojo.class);
		
	}

	

}
