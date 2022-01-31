package table.Dao;

import static org.junit.Assert.assertEquals;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import table.Pojo.BrandPojo;
import table.Service.ApiException;

public class BrandDaoTest extends AbstractUnitTest{
	
	
	
	
	@Autowired
	private BrandDao dao;
	

	@Test
	@Transactional
	public void testInsert() throws ApiException {
		BrandPojo p= new BrandPojo();
		p.setBrand(" Romil Jain ");
		p.setCategory("   Nikshan");
		dao.insert(p);
		int size = dao.selectAll().size();
		assertEquals(1, size);
	}
	
	@Test
	@Transactional
	public void testSelectAll() throws ApiException {
		BrandPojo p= new BrandPojo();
		p.setBrand(" Romil Jain ");
		p.setCategory("   Nikshan");
		dao.insert(p);
		int size = dao.selectAll().size();
		
		assertEquals(1, size);
	}
	
	@Test
	@Transactional
	public void testSelect() throws ApiException {
		BrandPojo p= new BrandPojo();
		p.setBrand(" Romil Jain ");
		p.setCategory("   Nikshan");
		dao.insert(p);
		BrandPojo bp = dao.select(3);
		
		assertEquals(3, bp.getId());
	}
	
	
	
}
