package table.Dao;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import table.Model.SalesReportForm;
import table.Pojo.BrandPojo;
import table.Pojo.OrderItemPojo;
import table.Pojo.OrderPojo;
import table.Pojo.ProductPojo;
import table.Service.ApiException;

@Repository
public class SalesReportDao {

	private static String select_dt_order = "select p from OrderPojo p where dateTime BETWEEN :dt1 AND :dt2";
	private static String select_oid_product = "select p from ProductPojo p where id=:id";
	private static String select_brand_cat_brand = "select p from BrandPojo p where brand=:brand AND category=:category";
	private static String select_cat = "select p from BrandPojo p where category=:category";
	private static String select_brand = "select p from BrandPojo p where brand=:brand";
	private static String select_oid_orderitem = "select p from OrderItemPojo p where orderId=:orderId";

	private static String end_date_null = "select o from OrderPojo o where dateTime>=:dt1";
	private static String start_date_null = "select o from OrderPojo o where dateTime<=:dt2";
	private static String all_orders = "select o from OrderPojo o";
	private static String select_all_brands = "select p from BrandPojo p";
	private static String select_brand_with_id = "select p from BrandPojo p where id=:id";

	@PersistenceContext
	EntityManager em;

	public HashMap<List<String>, HashMap<Integer, Double>> selectAll(SalesReportForm form) throws ApiException {
		BrandPojo lll;
		Set<List<String>> s = new HashSet<List<String>>();
		HashMap<List<String>, HashMap<Integer, Double>> hm2 = new HashMap<List<String>, HashMap<Integer, Double>>(); // quantity
		try {
			TypedQuery<BrandPojo> qq = em.createQuery(select_brand_cat_brand, BrandPojo.class);
			qq.setParameter("brand", form.getBrand());
			qq.setParameter("category", form.getCategory());
			lll = qq.getSingleResult();
		} catch (NoResultException e) {
			lll = null;
		}
		if (lll == null) {
			throw new ApiException("No Brand available");

		} else {

			List<String> lbp = new ArrayList<String>();
			lbp.add(lll.getBrand());
			lbp.add(lll.getCategory());
			s.add(lbp);
			HashMap<Integer, Double> chm = new HashMap<Integer, Double>();
			chm.put(0, 0.00);
			hm2.put(lbp, chm);

		}

		Date d1 = Date.valueOf(form.getStartdate());
		Date d2 = Date.valueOf(form.getEnddate());
		if (d1.compareTo(d2) > 0) {
			throw new ApiException("start date cannot occur after end date");
		}
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		if(d1.compareTo(date)>0) {
			throw new ApiException("start date cannot occur after today");
		}
		if(d2.compareTo(date)>0) {
			throw new ApiException("end date cannot occur after today");
		}
		String startdate = form.getStartdate() + " 00:00:00.0";
		String enddate = form.getEnddate() + " 23:59:59.0";
		TypedQuery<OrderPojo> query = em.createQuery(select_dt_order, OrderPojo.class);
		query.setParameter("dt1", Timestamp.valueOf(startdate));
		query.setParameter("dt2", Timestamp.valueOf(enddate));
		List<OrderPojo> lo = query.getResultList();
		if (lo.size() == 0) {
			return hm2;
		}

		for (OrderPojo o : lo) {
			TypedQuery<OrderItemPojo> q1 = em.createQuery(select_oid_orderitem, OrderItemPojo.class);
			q1.setParameter("orderId", o.getId());
			List<OrderItemPojo> oip = q1.getResultList();
			for (OrderItemPojo oi : oip) {
				List<String> brandCat = new ArrayList<String>();
				brandCat.add(form.getBrand());
				brandCat.add(form.getCategory());
				HashMap<Integer, Double> hm = new HashMap<Integer, Double>();

				if (s.contains(brandCat)) {

					HashMap<Integer, Double> hmm = hm2.get(brandCat);

					List<Integer> list2 = new ArrayList<Integer>(hmm.keySet());
					Collections.sort(list2);
					int quantity = list2.get(list2.size() - 1);
					double rev = hmm.get(quantity);
					hmm.put(quantity + oi.getQuantity(), rev + (oi.getQuantity() * oi.getMrp()));

					hm2.put(brandCat, hmm);
				}

			}
		}

		HashMap<List<String>, HashMap<Integer, Double>> hml = new HashMap<List<String>, HashMap<Integer, Double>>();
		List<List<String>> lk = new ArrayList<List<String>>(hm2.keySet());
		for (List<String> ls : lk) {

			HashMap<Integer, Double> hmmm = hm2.get(ls);
			List<Integer> li = new ArrayList<Integer>(hmmm.keySet());
			Collections.sort(li);
			int q = li.get(li.size() - 1);
			double r = hmmm.get(q);
			HashMap<Integer, Double> hmlast = new HashMap<Integer, Double>();
			hmlast.put(q, r);
			hml.put(ls, hmlast);
		}
		return hml;
	}

	public HashMap<List<String>, HashMap<Integer, Double>> select_everyOne(SalesReportForm form) throws ApiException {

		Set<List<String>> s = new HashSet<List<String>>();
		HashMap<List<String>, HashMap<Integer, Double>> hm2 = new HashMap<List<String>, HashMap<Integer, Double>>(); // quantity
		TypedQuery<BrandPojo> qq = em.createQuery(select_all_brands, BrandPojo.class);
		List<BrandPojo> lll = qq.getResultList();
		if (lll.size() == 0) {
			throw new ApiException("No Brand available");

		} else {
			for (BrandPojo bp : lll) {
				List<String> lbp = new ArrayList<String>();
				lbp.add(bp.getBrand());
				lbp.add(bp.getCategory());
				s.add(lbp);
				HashMap<Integer, Double> chm = new HashMap<Integer, Double>();
				chm.put(0, 0.00);
				hm2.put(lbp, chm);
			}

		}
		TypedQuery<OrderPojo> query = em.createQuery(all_orders, OrderPojo.class);
		List<OrderPojo> lo = query.getResultList();
		if (lo.size() == 0) {
			return hm2;
		}

		for (OrderPojo o : lo) {
			TypedQuery<OrderItemPojo> q1 = em.createQuery(select_oid_orderitem, OrderItemPojo.class);
			q1.setParameter("orderId", o.getId());
			List<OrderItemPojo> oip = q1.getResultList();
			for (OrderItemPojo oi : oip) {
				TypedQuery<ProductPojo> q2 = em.createQuery(select_oid_product, ProductPojo.class);
				q2.setParameter("id", oi.getProductId());
				ProductPojo p = q2.getSingleResult();
				TypedQuery<BrandPojo> q3 = em.createQuery(select_brand_with_id, BrandPojo.class);
				q3.setParameter("id", p.getBrandPojo());
				BrandPojo bp = q3.getSingleResult();
				List<String> brandCat = new ArrayList<String>();
				brandCat.add(bp.getBrand());
				brandCat.add(bp.getCategory());
				HashMap<Integer, Double> hm = new HashMap<Integer, Double>();

				if (s.contains(brandCat)) {

					HashMap<Integer, Double> hmm = hm2.get(brandCat);

					List<Integer> list2 = new ArrayList<Integer>(hmm.keySet());
					Collections.sort(list2);
					int quantity = list2.get(list2.size() - 1);
					double rev = hmm.get(quantity);
					hmm.put(quantity + oi.getQuantity(), rev + (oi.getQuantity() * oi.getMrp()));

					hm2.put(brandCat, hmm);
				}

			}
		}

		HashMap<List<String>, HashMap<Integer, Double>> hml = new HashMap<List<String>, HashMap<Integer, Double>>();
		List<List<String>> lk = new ArrayList<List<String>>(hm2.keySet());
		for (List<String> ls : lk) {

			HashMap<Integer, Double> hmmm = hm2.get(ls);
			List<Integer> li = new ArrayList<Integer>(hmmm.keySet());
			Collections.sort(li);
			int q = li.get(li.size() - 1);
			double r = hmmm.get(q);
			HashMap<Integer, Double> hmlast = new HashMap<Integer, Double>();
			hmlast.put(q, r);
			hml.put(ls, hmlast);
		}

		return hml;

	}

	public HashMap<List<String>, HashMap<Integer, Double>> catNull(SalesReportForm form) throws ApiException {

		Set<List<String>> s = new HashSet<List<String>>();
		HashMap<List<String>, HashMap<Integer, Double>> hm2 = new HashMap<List<String>, HashMap<Integer, Double>>(); // quantity
		TypedQuery<BrandPojo> qq = em.createQuery(select_brand, BrandPojo.class);
		qq.setParameter("brand", form.getBrand());
		List<BrandPojo> lll = qq.getResultList();
		if (lll.size() == 0) {
			throw new ApiException("Brand not available");

		} else {
			for (BrandPojo bp : lll) {
				List<String> lbp = new ArrayList<String>();
				lbp.add(bp.getBrand());
				lbp.add(bp.getCategory());
				s.add(lbp);
				HashMap<Integer, Double> chm = new HashMap<Integer, Double>();
				chm.put(0, 0.00);
				hm2.put(lbp, chm);
			}

		}
		Date d1 = Date.valueOf(form.getStartdate());
		Date d2 = Date.valueOf(form.getEnddate());
		if (d1.compareTo(d2) > 0) {
			throw new ApiException("start date cannot occur after end date");
		}
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		if(d1.compareTo(date)>0) {
			throw new ApiException("start date cannot occur after today");
		}
		if(d2.compareTo(date)>0) {
			throw new ApiException("end date cannot occur after today");
		}
		String startdate = form.getStartdate() + " 00:00:00.0";
		String enddate = form.getEnddate() + " 23:59:59.0";
		TypedQuery<OrderPojo> query = em.createQuery(select_dt_order, OrderPojo.class);
		query.setParameter("dt1", Timestamp.valueOf(startdate));
		query.setParameter("dt2", Timestamp.valueOf(enddate));
		List<OrderPojo> lo = query.getResultList();
		if (lo.size() == 0) {
			return hm2;
		}

		for (OrderPojo o : lo) {
			TypedQuery<OrderItemPojo> q1 = em.createQuery(select_oid_orderitem, OrderItemPojo.class);
			q1.setParameter("orderId", o.getId());
			List<OrderItemPojo> oip = q1.getResultList();
			for (OrderItemPojo oi : oip) {
				TypedQuery<ProductPojo> q2 = em.createQuery(select_oid_product, ProductPojo.class);
				q2.setParameter("id", oi.getProductId());
				ProductPojo p = q2.getSingleResult();
				TypedQuery<BrandPojo> q3 = em.createQuery(select_brand_with_id, BrandPojo.class);
				q3.setParameter("id", p.getBrandPojo());
				BrandPojo bp = q3.getSingleResult();
				List<String> brandCat = new ArrayList<String>();
				brandCat.add(bp.getBrand());
				brandCat.add(bp.getCategory());
				HashMap<Integer, Double> hm = new HashMap<Integer, Double>();

				if (s.contains(brandCat)) {

					HashMap<Integer, Double> hmm = hm2.get(brandCat);

					List<Integer> list2 = new ArrayList<Integer>(hmm.keySet());
					Collections.sort(list2);
					int quantity = list2.get(list2.size() - 1);
					double rev = hmm.get(quantity);
					hmm.put(quantity + oi.getQuantity(), rev + (oi.getQuantity() * oi.getMrp()));

					hm2.put(brandCat, hmm);
				}

			}
		}

		HashMap<List<String>, HashMap<Integer, Double>> hml = new HashMap<List<String>, HashMap<Integer, Double>>();
		List<List<String>> lk = new ArrayList<List<String>>(hm2.keySet());
		for (List<String> ls : lk) {

			HashMap<Integer, Double> hmmm = hm2.get(ls);
			List<Integer> li = new ArrayList<Integer>(hmmm.keySet());
			Collections.sort(li);
			int q = li.get(li.size() - 1);
			double r = hmmm.get(q);
			HashMap<Integer, Double> hmlast = new HashMap<Integer, Double>();
			hmlast.put(q, r);
			hml.put(ls, hmlast);
		}
		return hml;

	}

	public HashMap<List<String>, HashMap<Integer, Double>> brandNull(SalesReportForm form) throws ApiException {

		Set<List<String>> s = new HashSet<List<String>>();
		HashMap<List<String>, HashMap<Integer, Double>> hm2 = new HashMap<List<String>, HashMap<Integer, Double>>(); // quantity
		TypedQuery<BrandPojo> qq = em.createQuery(select_cat, BrandPojo.class);
		qq.setParameter("category", form.getCategory());
		List<BrandPojo> lll = qq.getResultList();
		if (lll.size() == 0) {
			throw new ApiException("Brand  with category added not available");

		} else {
			for (BrandPojo bp : lll) {
				List<String> lbp = new ArrayList<String>();
				lbp.add(bp.getBrand());
				lbp.add(bp.getCategory());
				s.add(lbp);
				HashMap<Integer, Double> chm = new HashMap<Integer, Double>();
				chm.put(0, 0.00);
				hm2.put(lbp, chm);
			}

		}
		Date d1 = Date.valueOf(form.getStartdate());
		Date d2 = Date.valueOf(form.getEnddate());
		if (d1.compareTo(d2) > 0) {
			throw new ApiException("start date cannot occur after end date");
		}
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		if(d1.compareTo(date)>0) {
			throw new ApiException("start date cannot occur after today");
		}
		if(d2.compareTo(date)>0) {
			throw new ApiException("end date cannot occur after today");
		}
		String startdate = form.getStartdate() + " 00:00:00.0";
		String enddate = form.getEnddate() + " 23:59:59.0";
		TypedQuery<OrderPojo> query = em.createQuery(select_dt_order, OrderPojo.class);
		query.setParameter("dt1", Timestamp.valueOf(startdate));
		query.setParameter("dt2", Timestamp.valueOf(enddate));
		List<OrderPojo> lo = query.getResultList();
		if (lo.size() == 0) {
			return hm2;
		}

		for (OrderPojo o : lo) {
			TypedQuery<OrderItemPojo> q1 = em.createQuery(select_oid_orderitem, OrderItemPojo.class);
			q1.setParameter("orderId", o.getId());
			List<OrderItemPojo> oip = q1.getResultList();
			for (OrderItemPojo oi : oip) {
				TypedQuery<ProductPojo> q2 = em.createQuery(select_oid_product, ProductPojo.class);
				q2.setParameter("id", oi.getProductId());
				ProductPojo p = q2.getSingleResult();
				TypedQuery<BrandPojo> q3 = em.createQuery(select_brand_with_id, BrandPojo.class);
				q3.setParameter("id", p.getBrandPojo());
				BrandPojo bp = q3.getSingleResult();
				List<String> brandCat = new ArrayList<String>();
				brandCat.add(bp.getBrand());
				brandCat.add(bp.getCategory());
				HashMap<Integer, Double> hm = new HashMap<Integer, Double>();

				if (s.contains(brandCat)) {

					HashMap<Integer, Double> hmm = hm2.get(brandCat);

					List<Integer> list2 = new ArrayList<Integer>(hmm.keySet());
					Collections.sort(list2);
					int quantity = list2.get(list2.size() - 1);
					double rev = hmm.get(quantity);
					hmm.put(quantity + oi.getQuantity(), rev + (oi.getQuantity() * oi.getMrp()));

					hm2.put(brandCat, hmm);
				}

			}
		}

		HashMap<List<String>, HashMap<Integer, Double>> hml = new HashMap<List<String>, HashMap<Integer, Double>>();
		List<List<String>> lk = new ArrayList<List<String>>(hm2.keySet());
		for (List<String> ls : lk) {

			HashMap<Integer, Double> hmmm = hm2.get(ls);
			List<Integer> li = new ArrayList<Integer>(hmmm.keySet());
			Collections.sort(li);
			int q = li.get(li.size() - 1);
			double r = hmmm.get(q);
			HashMap<Integer, Double> hmlast = new HashMap<Integer, Double>();
			hmlast.put(q, r);
			hml.put(ls, hmlast);
		}
		return hml;

	}

	public HashMap<List<String>, HashMap<Integer, Double>> brandCategoryNull(SalesReportForm form) throws ApiException {

		Set<List<String>> s = new HashSet<List<String>>();
		HashMap<List<String>, HashMap<Integer, Double>> hm2 = new HashMap<List<String>, HashMap<Integer, Double>>(); // quantity
		TypedQuery<BrandPojo> qq = em.createQuery(select_all_brands, BrandPojo.class);
		List<BrandPojo> lll = qq.getResultList();
		if (lll.size() == 0) {
			throw new ApiException("No Brand available");

		} else {
			for (BrandPojo bp : lll) {
				List<String> lbp = new ArrayList<String>();
				lbp.add(bp.getBrand());
				lbp.add(bp.getCategory());
				s.add(lbp);
				HashMap<Integer, Double> chm = new HashMap<Integer, Double>();
				chm.put(0, 0.00);
				hm2.put(lbp, chm);
			}

		}
		Date d1 = Date.valueOf(form.getStartdate());
		Date d2 = Date.valueOf(form.getEnddate());
		if (d1.compareTo(d2) > 0) {
			throw new ApiException("start date cannot occur after end date");
		}
		java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		if(d1.compareTo(date)>0) {
			throw new ApiException("start date cannot occur after today");
		}
		if(d2.compareTo(date)>0) {
			throw new ApiException("end date cannot occur after today");
		}
		String startdate = form.getStartdate() + " 00:00:00.0";
		String enddate = form.getEnddate() + " 23:59:59.0";
		TypedQuery<OrderPojo> query = em.createQuery(select_dt_order, OrderPojo.class);
		query.setParameter("dt1", Timestamp.valueOf(startdate));
		query.setParameter("dt2", Timestamp.valueOf(enddate));
		List<OrderPojo> lo = query.getResultList();
		if (lo.size() == 0) {
			return hm2;
		}

		for (OrderPojo o : lo) {
			TypedQuery<OrderItemPojo> q1 = em.createQuery(select_oid_orderitem, OrderItemPojo.class);
			q1.setParameter("orderId", o.getId());
			List<OrderItemPojo> oip = q1.getResultList();
			for (OrderItemPojo oi : oip) {
				TypedQuery<ProductPojo> q2 = em.createQuery(select_oid_product, ProductPojo.class);
				q2.setParameter("id", oi.getProductId());
				ProductPojo p = q2.getSingleResult();
				TypedQuery<BrandPojo> q3 = em.createQuery(select_brand_with_id, BrandPojo.class);
				q3.setParameter("id", p.getBrandPojo());
				BrandPojo bp = q3.getSingleResult();
				List<String> brandCat = new ArrayList<String>();
				brandCat.add(bp.getBrand());
				brandCat.add(bp.getCategory());
				HashMap<Integer, Double> hm = new HashMap<Integer, Double>();

				if (s.contains(brandCat)) {

					HashMap<Integer, Double> hmm = hm2.get(brandCat);

					List<Integer> list2 = new ArrayList<Integer>(hmm.keySet());
					Collections.sort(list2);
					int quantity = list2.get(list2.size() - 1);
					double rev = hmm.get(quantity);
					hmm.put(quantity + oi.getQuantity(), rev + (oi.getQuantity() * oi.getMrp()));

					hm2.put(brandCat, hmm);
				}

			}
		}

		HashMap<List<String>, HashMap<Integer, Double>> hml = new HashMap<List<String>, HashMap<Integer, Double>>();
		List<List<String>> lk = new ArrayList<List<String>>(hm2.keySet());
		for (List<String> ls : lk) {

			HashMap<Integer, Double> hmmm = hm2.get(ls);
			List<Integer> li = new ArrayList<Integer>(hmmm.keySet());
			Collections.sort(li);
			int q = li.get(li.size() - 1);
			double r = hmmm.get(q);
			HashMap<Integer, Double> hmlast = new HashMap<Integer, Double>();
			hmlast.put(q, r);
			hml.put(ls, hmlast);
		}
		return hml;

	}

}