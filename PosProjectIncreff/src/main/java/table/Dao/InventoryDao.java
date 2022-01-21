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
import table.Pojo.ProductPojo;
import table.Service.ApiException;


@Repository
public class InventoryDao {
	
	private static String delete_id = "delete from InventoryPojo p where id=:id";
	private static String select_id = "select p from InventoryPojo p where id=:id";
	private static String select_all = "select p from InventoryPojo p";
	private static String select_idB = "select p from ProductPojo p where id=:id";

	
	@PersistenceContext
	EntityManager em;
	
	
	public void insert(InventoryPojo p) throws ApiException {
		ProductPojo pp;
		try {
		TypedQuery<ProductPojo> query = em.createQuery(select_idB, ProductPojo.class);
		query.setParameter("id", p.getId());
		pp=query.getSingleResult();
		}catch(NoResultException e) {
			pp=null;
		}
		if(pp==null) {
			throw new ApiException("product with given id doesnot exists, id: "+p.getId());
		}
		else {
		em.persist(p);
		}
	}
	
	public int delete(int id) throws ApiException {
		
		Query query = em.createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
		
	}
	
	public InventoryPojo select(int id) {
		InventoryPojo p;
		try {
		TypedQuery<InventoryPojo> query = getQuery(select_id);
		query.setParameter("id", id);
		p=query.getSingleResult();
		}catch(NoResultException e) {
			p=null;
		}
		return p;
	}
	
	public List<InventoryPojo> selectAll() {
		TypedQuery<InventoryPojo> query = getQuery(select_all);
		return query.getResultList();
		
	}
	
	public void update(InventoryPojo p ) {
		
		
	}
	
	TypedQuery<InventoryPojo> getQuery(String jpql){
		return em.createQuery(jpql, InventoryPojo.class);
		
	}


}
