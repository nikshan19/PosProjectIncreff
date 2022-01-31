package table.Service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import table.Model.InventoryForm;
import table.Model.ProductForm;
import table.Pojo.BrandPojo;
import table.Pojo.InventoryPojo;
import table.Pojo.OrderItemPojo;
import table.Pojo.OrderPojo;
import table.Pojo.ProductPojo;

@Transactional
public class OrderItemServiceTest extends AbstractUnitTest{
	
	
	@Autowired
	private BrandService bservice;
	@Autowired
	private ProductService pservice;
	@Autowired
	private InventoryService iservice;
	@Autowired
	private OrderService oservice;
	@Autowired
	private OrderItemService oiservice;
	
	

	@Test
	public void testAdd() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("romil jain");
		p.setCategory("nikshan");
		bservice.add(p);
		ProductPojo pp = new ProductPojo();
//		pp.setBarcode("  ggjhg");
//		pp.setBrandPojo(1);
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
		oi.setProductId(1);
		oi.setOrderId(0);
		oi.setQuantity(10);
		oi.setMrp(77);
		oiservice.add(oi,"ggjhg", 77);
		assertEquals(1,oi.getId());
	}
	
	
	
	

	
	


}
