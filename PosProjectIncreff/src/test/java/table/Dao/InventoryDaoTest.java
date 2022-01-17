package table.Dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
		p.setBrand(" Romil Jain ");
		p.setCategory("   Nikshan");
		bdao.insert(p);
		ProductPojo pp = new ProductPojo();
		pp.setBarcode("  ggjhg");
		pp.setBrandPojo(1);
		pp.setMrp(77);
		pp.setName(" nnnnn");
		pdao.insert(pp);
		InventoryPojo ppp = new InventoryPojo();
		ppp.setId(1);
		ppp.setQuantity(100);
		idao.insert(ppp);
		assertEquals(1,ppp.getId());
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
		InventoryPojo ppp = new InventoryPojo();
		ppp.setId(2);
		ppp.setQuantity(100);
		idao.insert(ppp);
		InventoryPojo bp = idao.select(2);
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
		InventoryPojo ppp = new InventoryPojo();
		ppp.setId(3);
		ppp.setQuantity(100);
		idao.insert(ppp);
		List<InventoryPojo> l = idao.selectAll();
		
		assertEquals(1, l.size());
	
	}
	
	
	
	
	
	
	

	
	


}
