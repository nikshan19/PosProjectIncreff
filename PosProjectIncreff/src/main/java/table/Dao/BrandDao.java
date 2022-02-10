package table.Dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
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
	private static String select_brand_cat = "select p from BrandPojo p where brand=:brand AND category=:category";

	@PersistenceContext
	EntityManager em;

	public void insert(BrandPojo p) throws ApiException {
		BrandPojo bp;
		try {
			TypedQuery<BrandPojo> query = getQuery(select_brand_cat);
			query.setParameter("brand", p.getBrand());
			query.setParameter("category", p.getCategory());
			bp = query.getSingleResult();
		} catch (NoResultException e) {
			bp = null;
		}
		if (bp != null) {
			throw new ApiException("Brand-Category Combination already exists");
		}

		em.persist(p);

	}

	public int delete(int id) throws ApiException {
		TypedQuery<ProductPojo> q = em.createQuery(select_idP, ProductPojo.class);
		q.setParameter("id", id);
		if (q.getResultList().size() != 0) {
			throw new ApiException("Product with given id: '" + id + "' exists");
		} else {
			Query query = em.createQuery(delete_id);
			query.setParameter("id", id);
			return query.executeUpdate();
		}
	}

	public BrandPojo select(int id) {
		BrandPojo p;
		try {
			TypedQuery<BrandPojo> query = getQuery(select_id);
			query.setParameter("id", id);
			p = query.getSingleResult();
		} catch (NoResultException e) {
			p = null;
		}
		return p;
	}

	public List<BrandPojo> selectAll() {
		TypedQuery<BrandPojo> query = getQuery(select_all);
		List<BrandPojo> l = query.getResultList();
		Collections.reverse(l);
		return l;
	}

	public List<BrandPojo> selectAllSorted() {
		TypedQuery<BrandPojo> query = getQuery(select_all);
		List<BrandPojo> l = query.getResultList();
		Collections.sort(l);
		return l;
	}

	public void update(BrandPojo p) throws ApiException {
		BrandPojo bp;
		try {
			TypedQuery<BrandPojo> query = getQuery(select_brand_cat);
			query.setParameter("brand", p.getBrand());
			query.setParameter("category", p.getCategory());
			bp = query.getSingleResult();
		} catch (NoResultException e) {
			bp = null;
		}
		if (bp != null) {
			throw new ApiException("Brand-Category Combination already exists");
		}

	}

	TypedQuery<BrandPojo> getQuery(String jpql) {
		return em.createQuery(jpql, BrandPojo.class);

	}

}
