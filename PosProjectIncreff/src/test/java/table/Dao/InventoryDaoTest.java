package table.Dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import table.Model.InventoryData;
import table.Model.InventoryForm;
import table.Model.ProductForm;
import table.Pojo.BrandPojo;
import table.Pojo.InventoryPojo;
import table.Pojo.ProductPojo;
import table.Service.ApiException;

@Transactional
public class InventoryDaoTest extends AbstractUnitTest{
	
	
	@Autowired
	private BrandDao bdao;
	@Autowired
	private ProductDao pdao;
	@Autowired
	private InventoryDao idao;
	
	

	@Test
	public void testAdd() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("romil jain");
		p.setCategory("nikshan");
		bdao.insert(p);
		ProductPojo pp = new ProductPojo();

		pp.setMrp(77);
		pp.setName(" nnnnn");
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
		assertEquals(1,ppp.getId());
	}
	
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
		InventoryData bp = idao.select(2);
		System.out.println("testGet "+bp.getId());
		assertEquals(2, bp.getId());
	} 
	
		
	
	
	@Test
	public void testGetAll() throws ApiException{
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
		pdao.insert(pp,form);
		InventoryPojo ppp = new InventoryPojo();
		ppp.setQuantity(100);
		InventoryForm form2 = new InventoryForm();
		form2.setBarcode("ggjhg");
		form2.setQuantity(100);
		idao.insert(ppp, form2);
		List<InventoryData> l = idao.selectAll();
		
		assertEquals(1, l.size());
	
	}
	
	
	
	
	
	
	

	
	


}
