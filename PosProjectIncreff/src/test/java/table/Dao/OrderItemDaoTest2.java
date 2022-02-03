package table.Dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import table.Model.InventoryForm;
import table.Model.ProductForm;
import table.Pojo.BrandPojo;
import table.Pojo.InventoryPojo;
import table.Pojo.OrderItemPojo;
import table.Pojo.ProductPojo;
import table.Service.ApiException;

@Transactional
public class OrderItemDaoTest2 extends AbstractUnitTest{
	
	
	@Autowired
	private BrandDao bdao;
	@Autowired
	private ProductDao pdao;
	@Autowired
	private InventoryDao idao;
	@Autowired
	private OrderItemDao2 oidao2;
	@Autowired
	private OrderItemDao oidao;
	


	
	@Test
	public void testGet() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("romil jain");
		p.setCategory("nikshan");
		bdao.insert(p);
		ProductPojo pp = new ProductPojo();

		pp.setMrp(77);
		pp.setName("nnnnn");
		ProductForm form = new ProductForm();
		form.setBarcode("ggjhg");
		form.setBrand("romil jain");
		form.setCategory("nikshan");
		form.setMrp(77);
		form.setName("nnnnn");
		pdao.insert(pp, form);
		InventoryPojo ppp = new InventoryPojo();
		ppp.setQuantity(100);
		InventoryForm form2 = new InventoryForm();
		form2.setBarcode("ggjhg");
		form2.setQuantity(100);
		idao.insert(ppp, form2);
		OrderItemPojo oi = new OrderItemPojo();

		oi.setOrderId(0);

		oi.setQuantity(56);
		oidao.insert(oi, "ggjhg", 56);
		HashMap<OrderItemPojo, String> hm = oidao2.select(1);
		List<OrderItemPojo> l = new ArrayList<OrderItemPojo>(hm.keySet());
		OrderItemPojo o = l.get(0);
		String barcode = hm.get(o);
		assertEquals("ggjhg", barcode);
	} 
	
		
	
	
	@Test
	public void testUpdate() throws ApiException{
		BrandPojo p = new BrandPojo();
		p.setBrand("romil jain");
		p.setCategory("nikshan");
		bdao.insert(p);
		ProductPojo pp = new ProductPojo();

		pp.setMrp(77);
		pp.setName("nnnnn");
		ProductForm form = new ProductForm();
		form.setBarcode("ggjhg");
		form.setBrand("romil jain");
		form.setCategory("nikshan");
		form.setMrp(77);
		form.setName("nnnnn");
		pdao.insert(pp, form);
		InventoryPojo ppp = new InventoryPojo();
		ppp.setQuantity(100);
		InventoryForm form2 = new InventoryForm();
		form2.setBarcode("ggjhg");
		form2.setQuantity(100);
		idao.insert(ppp, form2);
		OrderItemPojo oi = new OrderItemPojo();

		oi.setOrderId(0);

		oi.setQuantity(56);
		oidao.insert(oi, "ggjhg", 56);
		
		oidao2.update(oi, "ggjhg", 42, 56);
		
		assertEquals(42, oi.getQuantity());
		
		
	}
	
	
	
	
	
	
	

	
	


}
