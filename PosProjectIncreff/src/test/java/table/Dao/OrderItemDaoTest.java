package table.Dao;

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
import table.Service.ApiException;

@Transactional
public class OrderItemDaoTest extends AbstractUnitTest{
	
	
	@Autowired
	private BrandDao bdao;
	@Autowired
	private ProductDao pdao;
	@Autowired
	private InventoryDao idao;
	@Autowired
	private OrderDao odao;
	@Autowired
	private OrderItemDao oidao;
	
	

	@Test
	public void testinsert() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("Romil Jain");
		p.setCategory("Nikshan");
		bdao.insert(p);
		ProductPojo pp = new ProductPojo();
		pp.setBarcode("ggjhg");
		pp.setBrandPojo(1);
		pp.setMrp(77);
		pp.setName("nnnnn");
		pdao.insert(pp);
		InventoryPojo ppp = new InventoryPojo();
		ppp.setId(1);
		ppp.setQuantity(100);
		idao.insert(ppp);
		OrderPojo o = new OrderPojo();
		odao.insert(o);
		OrderItemPojo oi = new OrderItemPojo();
		oi.setProductId(1);
		oi.setOrderId(10001);
		oi.setQuantity(10);
		oi.setMrp(77);
		oidao.insert(oi,"ggjhg", 77);
		assertEquals(1,oi.getId());
	}
	
	@Test
	public void testGet() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("Romil Jain");
		p.setCategory("Nikshan");
		bdao.insert(p);
		
		ProductPojo pp = new ProductPojo();
		pp.setBarcode("ggjhg");
		pp.setBrandPojo(2);
		pp.setMrp(77);
		pp.setName("nnnnn");
		pdao.insert(pp);
		
		InventoryPojo ppp = new InventoryPojo();
		ppp.setId(2);
		ppp.setQuantity(100);
		idao.insert(ppp);
		
		OrderPojo o = new OrderPojo();
		odao.insert(o);
		
		OrderItemPojo oi = new OrderItemPojo();
		oi.setProductId(2);
		oi.setOrderId(10002);
		oi.setQuantity(10);
		oi.setMrp(77);
		oidao.insert(oi,"ggjhg", 77);
		

		List<OrderItemPojo> l = new ArrayList<OrderItemPojo>(oidao.select(10002).keySet());
		OrderItemPojo oioi = l.get(0);
		assertEquals(2, oioi.getId());
	} 
	
		
	
	
	@Test
	public void testGetAll() throws ApiException{
		BrandPojo p = new BrandPojo();
		p.setBrand("Romil Jain");
		p.setCategory("Nikshan");
		bdao.insert(p);
		ProductPojo pp = new ProductPojo();
		pp.setBarcode("ggjhg");
		pp.setBrandPojo(3);
		pp.setMrp(77);
		pp.setName("nnnnn");
		pdao.insert(pp);
		InventoryPojo ppp = new InventoryPojo();
		ppp.setId(3);
		ppp.setQuantity(100);
		idao.insert(ppp);
		OrderPojo o = new OrderPojo();
		odao.insert(o);
		OrderItemPojo oi = new OrderItemPojo();
		oi.setProductId(3);
		oi.setOrderId(10003);
		oi.setQuantity(10);
		oi.setMrp(77);
		oidao.insert(oi,"ggjhg", 77);
		int s = oidao.selectAll().keySet().size();
		
		assertEquals(1, s);
	
	}
	
	@Test
	public void testUpdate() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("Romil Jain");
		p.setCategory("Nikshan");
		bdao.insert(p);
		ProductPojo pp = new ProductPojo();
		pp.setBarcode("ggjhg");
		pp.setBrandPojo(4);
		pp.setMrp(77);
		pp.setName("nnnnn");
		pdao.insert(pp);
		InventoryPojo ppp = new InventoryPojo();
		ppp.setId(4);
		ppp.setQuantity(100);
		idao.insert(ppp);
		OrderPojo o = new OrderPojo();
		odao.insert(o);
		OrderItemPojo oi = new OrderItemPojo();
		oi.setProductId(4);
		oi.setOrderId(10004);
		oi.setQuantity(10);
		oi.setMrp(77);
		oidao.insert(oi,"ggjhg", 77);
		
		List<OrderItemPojo> l =  new ArrayList<OrderItemPojo>(oidao.select(10004).keySet());
		OrderItemPojo oioi = l.get(0);
		oidao.update(oioi, "ggjhg", 20);
		List<OrderItemPojo> ll =  new ArrayList<OrderItemPojo>(oidao.select(10004).keySet());
		OrderItemPojo pppp = ll.get(0);
		
		assertEquals(20, pppp.getQuantity());
	}
	
	
	
	
	
	

	
	


}
