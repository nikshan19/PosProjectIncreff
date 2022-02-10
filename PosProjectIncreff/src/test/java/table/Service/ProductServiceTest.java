package table.Service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import table.Model.ProductForm;
import table.Pojo.BrandPojo;
import table.Pojo.ProductPojo;

@Transactional
public class ProductServiceTest extends AbstractUnitTest{
	
	
	@Autowired
	private BrandService bservice;
	@Autowired
	private ProductService pservice;
	
	

	@Test
	public void testAdd() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("romil jain");
		p.setCategory("nikshan");
		bservice.add(p);
		ProductPojo pp = new ProductPojo();
		pp.setBrandPojo(p.getId());
		pp.setMrp(77);
		pp.setName(" nnnnn");
		ProductForm form = new ProductForm();
		form.setBarcode("ggjhg");
		form.setMrp(77);
		form.setName("nnnnn");
		pservice.add(pp, form);
		
		assertEquals(1,pp.getId());
	}
	
	@Test
	public void testGet() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("romil jain");
		p.setCategory("nikshan");
		bservice.add(p);
		ProductPojo pp = new ProductPojo();
		pp.setBrandPojo(p.getId());
		pp.setMrp(77);
		pp.setName(" nnnnn");
		ProductForm form = new ProductForm();
		form.setBarcode("ggjhg");
		form.setMrp(77);
		form.setName("nnnnn");
		pservice.add(pp, form);
		ProductPojo bp = pservice.get(2);
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
		pp.setBrandPojo(p.getId());
		pp.setMrp(77);
		pp.setName(" nnnnn");
		ProductForm form = new ProductForm();
		form.setBarcode("ggjhg");
		form.setMrp(77);
		form.setName("nnnnn");
		pservice.add(pp, form);
	
		List<ProductPojo> l = pservice.getAll();
		
		assertEquals(1, l.size());
	
	}
	
	@Test
	public void testUpdate() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand("romil jain");
		p.setCategory("nikshan");
		bservice.add(p);
		ProductPojo pp = new ProductPojo();
		pp.setBrandPojo(p.getId());
		pp.setMrp(77);
		pp.setName(" nnnnn");
		ProductForm form = new ProductForm();
		form.setBarcode("ggjhg");
		form.setMrp(77);
		form.setName("nnnnn");
		pservice.add(pp, form);
		ProductPojo bp = pservice.get(4);
		bp.setBarcode("aa");
		pservice.update(4, bp, form);
		ProductPojo ppp = pservice.get(4);

		assertEquals("ggjhg", ppp.getBarcode());
	}
	
	
	
	
	
	

	
	


}
