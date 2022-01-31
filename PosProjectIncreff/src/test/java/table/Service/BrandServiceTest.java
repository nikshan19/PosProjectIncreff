package table.Service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import table.Pojo.BrandPojo;

@Transactional
public class BrandServiceTest extends AbstractUnitTest{
	
	
	@Autowired
	private BrandService service;
	BrandPojo p;

	@Test
	public void testAdd() throws ApiException {
		p = new BrandPojo();
		p.setBrand(" Romil Jain ");
		p.setCategory("   Nikshan");
		service.add(p);
		
		assertEquals(p.getId(), 1);
	}
	
	@Test
	public void testGet() throws ApiException {
		p = new BrandPojo();
		p.setBrand(" Romil Jain ");
		p.setCategory("   Nikshan");
		service.add(p);
		BrandPojo bp = service.get(2);
		System.out.println("testGet "+bp.getId());
		assertEquals(2, bp.getId());
	} 
	
		
	
	
	@Test
	public void testGetAll() throws ApiException{
		p = new BrandPojo();
		p.setBrand(" Romil Jain ");
		p.setCategory("   Nikshan");
		service.add(p);
	
		List<BrandPojo> l = service.getAll();
		System.out.println(l.get(0).getBrand());
		assertEquals(1, l.size());
	
	}
	
	@Test
	public void testUpdate() throws ApiException {
		p = new BrandPojo();
		p.setBrand(" Romil Jain ");
		p.setCategory("   Nikshan");
		service.add(p);
		BrandPojo bp = service.get(4);
		bp.setBrand("aa");
		service.update(4, bp);
		BrandPojo pp = service.get(4);

		assertEquals("aa", pp.getBrand());
	}
	
	
	
	
	
	

	
	


}
