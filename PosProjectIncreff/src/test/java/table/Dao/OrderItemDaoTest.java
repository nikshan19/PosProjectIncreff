package table.Dao;

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
		p.setBrand("romil jain");
		p.setCategory("nikshan");
		bdao.insert(p);
		ProductPojo pp = new ProductPojo();
//		pp.setBarcode("ggjhg");
//		pp.setBrandPojo(1);
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
		oi.setProductId(1);
		oi.setOrderId(0);
		oi.setQuantity(10);
		oi.setMrp(77);
		oidao.insert(oi,"ggjhg", 77);
		assertEquals(1,oi.getId());
	}
	
	
	
	
	
	

	
	


}
