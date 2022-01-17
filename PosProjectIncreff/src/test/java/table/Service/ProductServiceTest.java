package table.Service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
		p.setBrand(" Romil Jain ");
		p.setCategory("   Nikshan");
		bservice.add(p);
		ProductPojo pp = new ProductPojo();
		pp.setBarcode("  ggjhg");
		pp.setBrandPojo(1);
		pp.setMrp(77);
		pp.setName(" nnnnn");
		pservice.add(pp);
		
		assertEquals(1,pp.getId());
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
		ProductPojo bp = pservice.get(2);
		System.out.println("testGet "+bp.getId());
		assertEquals(2, bp.getId());
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
	
		List<ProductPojo> l = pservice.getAll();
		
		assertEquals(1, l.size());
	
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
		ProductPojo bp = pservice.get(4);
		bp.setBarcode("aa");
		pservice.update(4, bp);
		ProductPojo ppp = pservice.get(4);

		assertEquals("aa", ppp.getBarcode());
	}
	
	
	
	
	
	

	
	


}
