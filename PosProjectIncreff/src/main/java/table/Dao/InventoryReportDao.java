package table.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import table.Pojo.BrandPojo;
import table.Pojo.InventoryPojo;
import table.Pojo.ProductPojo;


@Repository
public class InventoryReportDao {
	
	private static String select_id_inventory = "select p from InventoryPojo p where id=:id";
	private static String select_bc_product = "select p from ProductPojo p where brandPojo=:brandPojo";
	private static String select_all_brand = "select p from BrandPojo p";
	private static String select_idB = "select p from BrandPojo p where id=:id";
	
	@PersistenceContext
	EntityManager em;
	
	
	
	
	
	
	public HashMap<BrandPojo,Integer> selectAll() {
		TypedQuery<BrandPojo> query =em.createQuery(select_all_brand, BrandPojo.class);
		List<BrandPojo> lB = query.getResultList();
		HashMap<BrandPojo, Integer> hm = new HashMap<BrandPojo, Integer>();
		for(BrandPojo b:lB) {
			TypedQuery<ProductPojo> q1 =em.createQuery(select_bc_product, ProductPojo.class);
			q1.setParameter("brandPojo", b.getId());
			List<ProductPojo> lP = q1.getResultList();
			int quant = 0;
			for(ProductPojo p: lP) {
				TypedQuery<InventoryPojo> q2 =em.createQuery(select_id_inventory, InventoryPojo.class);
				q2.setParameter("id", p.getId());
				InventoryPojo i = q2.getSingleResult();
				quant+=i.getQuantity();
			}
			hm.put(b, quant);
		}
		return hm;
	}
	
	
	TypedQuery<InventoryPojo> getQuery(String jpql){
		return em.createQuery(jpql, InventoryPojo.class);
		
	}


}