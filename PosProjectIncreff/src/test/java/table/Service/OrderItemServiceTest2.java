package table.Service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import table.Model.InventoryData;
import table.Model.InventoryForm;
import table.Model.ProductForm;
import table.Pojo.BrandPojo;
import table.Pojo.InventoryPojo;
import table.Pojo.OrderItemPojo;
import table.Pojo.ProductPojo;

@Transactional
public class OrderItemServiceTest2 extends AbstractUnitTest{
	
	
	@Autowired
	private BrandService bservice;
	@Autowired
	private ProductService pservice;
	@Autowired
	private InventoryService iservice;
	@Autowired
	private OrderItemService2 oiservice2;
	@Autowired
	private OrderItemService oiservice;
	
	

	
	
	@Test
	public void testGet() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("romil jain");
		p.setCategory("nikshan");
		bservice.add(p);
		ProductPojo pp = new ProductPojo();

		pp.setMrp(77);
		pp.setName(" nnnnn");
		ProductForm form = new ProductForm();
		form.setBarcode("ggjhg");
		form.setBrand("romil jain");
		form.setCategory("nikshan");
		form.setMrp(77);
		form.setName("nnnnn");
		pservice.add(pp, form);
		InventoryPojo ppp = new InventoryPojo();
		ppp.setQuantity(100);
		InventoryForm form2 = new InventoryForm();
		form2.setBarcode("ggjhg");
		form2.setQuantity(100);
		iservice.add(ppp, form2);
		OrderItemPojo oi = new OrderItemPojo();

		oi.setOrderId(0);

		oi.setQuantity(56);
		oiservice.add(oi, "ggjhg", 56);
		HashMap<OrderItemPojo, String> hm = oiservice2.get(1);
		List<OrderItemPojo> l = new ArrayList<OrderItemPojo>(hm.keySet());
		OrderItemPojo o = l.get(0);
		String barcode = hm.get(o);
		assertEquals("ggjhg", barcode);
	} 
	
		
	
	
	
	@Test
	public void testUpdate() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("romil jain");
		p.setCategory("nikshan");
		bservice.add(p);
		ProductPojo pp = new ProductPojo();

		pp.setMrp(77);
		pp.setName(" nnnnn");
		ProductForm form = new ProductForm();
		form.setBarcode("ggjhg");
		form.setBrand("romil jain");
		form.setCategory("nikshan");
		form.setMrp(77);
		form.setName("nnnnn");
		pservice.add(pp, form);
		InventoryPojo ppp = new InventoryPojo();
		ppp.setQuantity(100);
		InventoryForm form2 = new InventoryForm();
		form2.setBarcode("ggjhg");
		form2.setQuantity(100);
		iservice.add(ppp, form2);
		OrderItemPojo oi = new OrderItemPojo();

		oi.setOrderId(0);

		oi.setQuantity(56);
		oiservice.add(oi, "ggjhg", 56);
		
		oiservice2.update(oi, "ggjhg", 42, 56);
		
		assertEquals(42, oi.getQuantity());
	}
	
	
	
	
	
	

	
	


}
