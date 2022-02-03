package table.Dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import table.Model.InventoryData;
import table.Model.InventoryForm;
import table.Pojo.InventoryPojo;
import table.Pojo.ProductPojo;
import table.Service.ApiException;

@Repository
public class InventoryDao {

	private static String delete_id = "delete from InventoryPojo p where id=:id";
	private static String select_id = "select p from InventoryPojo p where id=:id";
	private static String select_all = "select p from InventoryPojo p";
	private static String select_idB = "select p from ProductPojo p where barcode=:barcode";
	private static String select_idP = "select p from ProductPojo p where id=:id";
	private static String select_idi = "select p from InventoryPojo p where id=:id";
	private static String select_idall = "select p from ProductPojo p";

	@PersistenceContext
	EntityManager em;

	public void insert(InventoryPojo p, InventoryForm form) throws ApiException {
		ProductPojo pp;
		try {
			TypedQuery<ProductPojo> query = em.createQuery(select_idB, ProductPojo.class);
			query.setParameter("barcode", form.getBarcode());
			pp = query.getSingleResult();
		} catch (NoResultException e) {
			pp = null;
		}
		if (pp == null) {
			throw new ApiException("product with given barcode doesnot exists: " + form.getBarcode());
		} else {

			InventoryPojo ip;
			try {
				TypedQuery<InventoryPojo> query = getQuery(select_id);
				query.setParameter("id", pp.getId());
				ip = query.getSingleResult();
			} catch (NoResultException e) {
				ip = null;
			}
			if (ip != null) {
				ip.setQuantity(ip.getQuantity() + p.getQuantity());
			} else {
				p.setId(pp.getId());
				em.persist(p);
			}
		}
	}

	public int delete(int id) throws ApiException {

		Query query = em.createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();

	}

	public InventoryData select(int id) throws ApiException {
		ProductPojo pp;
		try {
			TypedQuery<ProductPojo> query = em.createQuery(select_idP, ProductPojo.class);
			query.setParameter("id", id);
			pp = query.getSingleResult();
		} catch (NoResultException e) {
			pp = null;
		}
		if (pp == null) {
			throw new ApiException("Product doesnot exist");
		}

		InventoryPojo p;
		try {
			TypedQuery<InventoryPojo> query = getQuery(select_id);
			query.setParameter("id", id);
			p = query.getSingleResult();
		} catch (NoResultException e) {
			p = null;
		}
		if (p == null) {
			throw new ApiException("Inventory with given id doenot exist");
		}
		InventoryData data = new InventoryData();
		data.setBarcode(pp.getBarcode());
		data.setName(pp.getName());
		data.setId(p.getId());
		data.setQuantity(p.getQuantity());
		return data;
	}

	public List<InventoryData> selectAll() {
		TypedQuery<ProductPojo> query = em.createQuery(select_idall, ProductPojo.class);
		List<ProductPojo> l = query.getResultList();
		List<InventoryData> list = new ArrayList<InventoryData>();

		for (ProductPojo p : l) {
			InventoryPojo pp;
			try {
				TypedQuery<InventoryPojo> q = em.createQuery(select_idi, InventoryPojo.class);
				q.setParameter("id", p.getId());
				pp = q.getSingleResult();
			} catch (NoResultException e) {
				continue;
			}
			InventoryData d = new InventoryData();
			d.setBarcode(p.getBarcode());
			d.setName(p.getName());
			d.setQuantity(pp.getQuantity());
			d.setId(p.getId());
			list.add(d);
		}
		Collections.reverse(list);
		return list;

	}

	public InventoryPojo update(InventoryForm form) throws ApiException {

		ProductPojo p;
		try {
			TypedQuery<ProductPojo> query = em.createQuery(select_idB, ProductPojo.class);
			query.setParameter("barcode", form.getBarcode());
			p = query.getSingleResult();
		} catch (NoResultException e) {
			p = null;
		}

		if (p == null) {
			throw new ApiException("No Product with barcode " + form.getBarcode() + " found");
		} else {
			InventoryPojo ip;
			try {
				TypedQuery<InventoryPojo> query = getQuery(select_id);
				query.setParameter("id", p.getId());
				ip = query.getSingleResult();
			} catch (NoResultException e) {
				ip = null;
			}
			if (ip == null) {
				throw new ApiException("Inventory with given barcode not found");
			}

			return ip;

		}

	}

	public InventoryPojo onlySelect(int id) throws ApiException {
		InventoryPojo p;
		try {
			TypedQuery<InventoryPojo> query = getQuery(select_id);
			query.setParameter("id", id);
			p = query.getSingleResult();
		} catch (NoResultException e) {
			p = null;
		}
		if (p == null) {
			throw new ApiException("Inventory eith given id not found");
		}

		return p;
	}

	TypedQuery<InventoryPojo> getQuery(String jpql) {
		return em.createQuery(jpql, InventoryPojo.class);

	}

}
