package table.Dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import table.Pojo.BrandPojo;
import table.Pojo.ProductPojo;
import table.Service.ApiException;

@Transactional
public class ProductDaoTest extends AbstractUnitTest{
	
	
	@Autowired
	private BrandDao bdao;
	@Autowired
	private ProductDao pdao;
	
	

	@Test
	public void testinsert() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand(" Romil Jain ");
		p.setCategory("   Nikshan");
		bdao.insert(p);
		ProductPojo pp = new ProductPojo();
		pp.setBarcode("  ggjhg");
		pp.setBrandPojo(1);
		pp.setMrp(77);
		pp.setName(" nnnnn");
		pdao.insert(pp);
		
		assertEquals(1,pp.getId());
	}
	
	@Test
	public void testGet() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand(" Romil Jain ");
		p.setCategory("   Nikshan");
		bdao.insert(p);
		ProductPojo pp = new ProductPojo();
		pp.setBarcode("  ggjhg");
		pp.setBrandPojo(2);
		pp.setMrp(77);
		pp.setName(" nnnnn");
		pdao.insert(pp);
		ProductPojo bp = pdao.select(2);
		System.out.println("testGet "+bp.getId());
		assertEquals(2, bp.getId());
	} 
	
		
	
	
	@Test
	public void testGetAll() throws ApiException{
		BrandPojo p = new BrandPojo();
		p.setBrand(" Romil Jain ");
		p.setCategory("   Nikshan");
		bdao.insert(p);
		ProductPojo pp = new ProductPojo();
		pp.setBarcode("  ggjhg");
		pp.setBrandPojo(3);
		pp.setMrp(77);
		pp.setName(" nnnnn");
		pdao.insert(pp);
	
		List<ProductPojo> l = pdao.selectAll();
		
		assertEquals(1, l.size());
	
	}
	
	
	
	
	
	
	
	

	
	


}
