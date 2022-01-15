package table.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import table.Pojo.BrandPojo;
import table.Pojo.ProductPojo;
import table.Service.ApiException;


@Repository
public class BrandDao {
	
	private static String delete_id = "delete from BrandPojo p where id=:id";
	private static String select_id = "select p from BrandPojo p where id=:id";
	private static String select_all = "select p from BrandPojo p";
	private static String select_idP = "select p from ProductPojo p where brandPojo=:id";
	
	@PersistenceContext
	EntityManager em;
	
	
	public void insert(BrandPojo p) {
		
		em.persist(p);
	}
	
	public int delete(int id) throws ApiException {
		TypedQuery<ProductPojo> q = em.createQuery(select_idP, ProductPojo.class);
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
	
	public BrandPojo select(int id) {
		TypedQuery<BrandPojo> query = getQuery(select_id);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	public List<BrandPojo> selectAll() {
		TypedQuery<BrandPojo> query = getQuery(select_all);
		return query.getResultList();
	}
	
	public void update(BrandPojo p ) {
		
		
	}
	
	TypedQuery<BrandPojo> getQuery(String jpql){
		return em.createQuery(jpql, BrandPojo.class);
		
	}


}
