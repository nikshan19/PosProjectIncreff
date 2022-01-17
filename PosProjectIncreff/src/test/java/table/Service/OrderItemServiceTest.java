package table.Service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
		p.setBrand(" Romil Jain ");
		p.setCategory("   Nikshan");
		bservice.add(p);
		ProductPojo pp = new ProductPojo();
		pp.setBarcode("  ggjhg");
		pp.setBrandPojo(1);
		pp.setMrp(77);
		pp.setName(" nnnnn");
		pservice.add(pp);
		InventoryPojo ppp = new InventoryPojo();
		ppp.setId(1);
		ppp.setQuantity(100);
		iservice.add(ppp);
		OrderPojo o = new OrderPojo();
		oservice.add(o);
		OrderItemPojo oi = new OrderItemPojo();
		oi.setProductId(1);
		oi.setOrderId(10001);
		oi.setQuantity(10);
		oi.setMrp(77);
		oiservice.add(oi,"ggjhg", 77);
		assertEquals(1,oi.getId());
	}
	
	@Test
	public void testGet() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand(" Romil Jain ");
		p.setCategory("   Nikshan");
		bservice.add(p);
		
		ProductPojo pp = new ProductPojo();
		pp.setBarcode("  ggjhg");
		pp.setBrandPojo(2);
		pp.setMrp(77);
		pp.setName(" nnnnn");
		pservice.add(pp);
		
		InventoryPojo ppp = new InventoryPojo();
		ppp.setId(2);
		ppp.setQuantity(100);
		iservice.add(ppp);
		
		OrderPojo o = new OrderPojo();
		oservice.add(o);
		
		OrderItemPojo oi = new OrderItemPojo();
		oi.setProductId(2);
		oi.setOrderId(10002);
		oi.setQuantity(10);
		oi.setMrp(77);
		oiservice.add(oi,"ggjhg", 77);
		

		List<OrderItemPojo> l = new ArrayList<OrderItemPojo>(oiservice.get(10002).keySet());
		OrderItemPojo oioi = l.get(0);
		assertEquals(2, oioi.getId());
	} 
	
		
	
	
	@Test
	public void testGetAll() throws ApiException{
		BrandPojo p = new BrandPojo();
		p.setBrand(" Romil Jain ");
		p.setCategory("   Nikshan");
		bservice.add(p);
		ProductPojo pp = new ProductPojo();
		pp.setBarcode("  ggjhg");
		pp.setBrandPojo(3);
		pp.setMrp(77);
		pp.setName(" nnnnn");
		pservice.add(pp);
		InventoryPojo ppp = new InventoryPojo();
		ppp.setId(3);
		ppp.setQuantity(100);
		iservice.add(ppp);
		OrderPojo o = new OrderPojo();
		oservice.add(o);
		OrderItemPojo oi = new OrderItemPojo();
		oi.setProductId(3);
		oi.setOrderId(10003);
		oi.setQuantity(10);
		oi.setMrp(77);
		oiservice.add(oi,"ggjhg", 77);
		int s = oiservice.getAll().keySet().size();
		
		assertEquals(1, s);
	
	}
	
	@Test
	public void testUpdate() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand(" Romil Jain ");
		p.setCategory("   Nikshan");
		bservice.add(p);
		ProductPojo pp = new ProductPojo();
		pp.setBarcode("  ggjhg");
		pp.setBrandPojo(4);
		pp.setMrp(77);
		pp.setName(" nnnnn");
		pservice.add(pp);
		InventoryPojo ppp = new InventoryPojo();
		ppp.setId(4);
		ppp.setQuantity(100);
		iservice.add(ppp);
		OrderPojo o = new OrderPojo();
		oservice.add(o);
		OrderItemPojo oi = new OrderItemPojo();
		oi.setProductId(4);
		oi.setOrderId(10004);
		oi.setQuantity(10);
		oi.setMrp(77);
		oiservice.add(oi,"ggjhg", 77);
		
		List<OrderItemPojo> l =  new ArrayList<OrderItemPojo>(oiservice.get(10004).keySet());
		OrderItemPojo oioi = l.get(0);
		oiservice.update(oioi, "ggjhg", 4, 20);
		List<OrderItemPojo> ll =  new ArrayList<OrderItemPojo>(oiservice.get(10004).keySet());
		OrderItemPojo pppp = ll.get(0);
		
		assertEquals(20, pppp.getQuantity());
	}
	
	
	
	
	
	

	
	


}
