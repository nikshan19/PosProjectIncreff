package table.Service;

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

@Transactional
public class InventoryServiceTest extends AbstractUnitTest{
	
	
	@Autowired
	private BrandService bservice;
	@Autowired
	private ProductService pservice;
	@Autowired
	private InventoryService iservice;
	
	

	@Test
	public void testAdd() throws ApiException {
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
		assertEquals(1,ppp.getId());
	}
	
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
		InventoryData bp = iservice.get(2);
		System.out.println("testGet "+bp.getId());
		assertEquals(2, bp.getId());
	} 
	
		
	
	
	@Test
	public void testGetAll() throws ApiException{
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
		List<InventoryData> l = iservice.getAll();
		
		assertEquals(1, l.size());
	
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
		
		
		form2.setQuantity(120);
		InventoryPojo pnew = new InventoryPojo();
		pnew.setQuantity(120);
		iservice.update("ggjhg",pnew, form2);

		assertEquals(120, ppp.getQuantity());
	}
	
	
	
	
	
	

	
	


}
