package table.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import table.Pojo.OrderPojo;


@Repository
public class OrderDao {
	
	private static String delete_id = "delete from OrderPojo p where id=:id";
	private static String select_id = "select p from OrderPojo p where id=:id";
	private static String select_all = "select p from OrderPojo p";

	
	@PersistenceContext
	EntityManager em;
	
	
	public void insert(OrderPojo p) {
		
		em.persist(p);
	}
	
	public int delete(int id) {
		Query query = em.createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
	}
	
	public OrderPojo select(int id) {
		TypedQuery<OrderPojo> query = getQuery(select_id);
		query.setParameter("id", id);
		return query.getSingleResult();
	}
	
	public List<OrderPojo> selectAll() {
		TypedQuery<OrderPojo> query = getQuery(select_all);
		return query.getResultList();
	}
	
	public void update(OrderPojo p ) {
		
		
	}
	
	TypedQuery<OrderPojo> getQuery(String jpql){
		return em.createQuery(jpql, OrderPojo.class);
		
	}


}
