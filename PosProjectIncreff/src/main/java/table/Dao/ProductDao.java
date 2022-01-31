package table.Dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import table.Model.ProductForm;
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
	private static String select_barcode = "select p from ProductPojo p where barcode=:barcode";
	private static String select_idBC = "select p from BrandPojo p where brand=:brand AND category=:category";

	@PersistenceContext
	EntityManager em;

	public void insert(ProductPojo p, ProductForm form) throws ApiException {
		ProductPojo pp;
		try {
			TypedQuery<ProductPojo> query = getQuery(select_barcode);
			query.setParameter("barcode", form.getBarcode());
			pp = query.getSingleResult();
		} catch (NoResultException e) {
			pp = null;

		}
		if (pp == null) {
			p.setBarcode(form.getBarcode());
			BrandPojo bp;
			try {
				TypedQuery<BrandPojo> query = em.createQuery(select_idBC, BrandPojo.class);
				query.setParameter("brand", form.getBrand());
				query.setParameter("category", form.getCategory());
				bp = query.getSingleResult();
			} catch (NoResultException e) {
				bp = null;
			}
			if (bp == null) {
				throw new ApiException("brand with given brand-category combination doesnot exist");
			} else {
				p.setBrandPojo(bp.getId());
				em.persist(p);
			}
		} else {
			throw new ApiException("product with barcode: " + form.getBarcode() + " already exists");
		}
	}

	public int delete(int id) throws ApiException {
		TypedQuery<InventoryPojo> q = em.createQuery(select_idP, InventoryPojo.class);
		q.setParameter("id", id);
		if (q.getResultList().size() != 0) {
			throw new ApiException("product with given id exists, id: " + id);
		} else {
			Query query = em.createQuery(delete_id);
			query.setParameter("id", id);
			return query.executeUpdate();
		}
	}

	public ProductPojo select(int id) {
		ProductPojo p;
		try {
			TypedQuery<ProductPojo> query = getQuery(select_id);
			query.setParameter("id", id);
			p = query.getSingleResult();
		} catch (NoResultException e) {
			p = null;
		}
		return p;
	}

	public BrandPojo selectBP(int brandPojo) throws ApiException {
		BrandPojo bp;
		try {
			TypedQuery<BrandPojo> query = em.createQuery(select_idB, BrandPojo.class);
			query.setParameter("id", brandPojo);

			bp = query.getSingleResult();
		} catch (NoResultException e) {
			bp = null;
		}
		if (bp == null) {
			throw new ApiException("brand with given brand-category combination doesnot exist");
		} else {
			return bp;
		}

	}

	public List<ProductPojo> selectAll() {
		Session session = em.unwrap(Session.class);
		String hql = "FROM ProductPojo p ORDER BY p.name asc";
		Query query = session.createQuery(hql);
		return query.getResultList();
	}

	public void update(ProductPojo p, ProductForm form) throws ApiException {
		ProductPojo pp;
		try {
			TypedQuery<ProductPojo> query = getQuery(select_barcode);
			query.setParameter("barcode", form.getBarcode());
			pp = query.getSingleResult();
		} catch (NoResultException e) {
			pp = null;

		}
		if ((pp == null) || (pp != null && pp.getId() == p.getId())) {
			p.setBarcode(form.getBarcode());
			BrandPojo bp;
			try {
				TypedQuery<BrandPojo> query = em.createQuery(select_idBC, BrandPojo.class);
				query.setParameter("brand", form.getBrand());
				query.setParameter("category", form.getCategory());
				bp = query.getSingleResult();
			} catch (NoResultException e) {
				bp = null;
			}
			if (bp == null) {
				throw new ApiException("brand with given brand-category combination doesnot exist");
			} else {
				p.setBrandPojo(bp.getId());
			}
		}

		else {
			throw new ApiException("product with barcode: " + form.getBarcode() + " already exists");
		}
	}

	TypedQuery<ProductPojo> getQuery(String jpql) {
		return em.createQuery(jpql, ProductPojo.class);

	}

}
