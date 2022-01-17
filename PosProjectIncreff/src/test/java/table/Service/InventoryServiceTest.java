package table.Service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
		p.setBrand(" Romil Jain ");
		p.setCategory("   Nikshan");
		bservice.add(p);
		ProductPojo pp = new ProductPojo();
		pp.setBarcode("  ggjhg");
		pp.setBrandPojo(1);
		pp.setMrp(77);
		pp.setName(" nnnnn");
		pservice.add(pp);
		InventoryPojo ppp = new InventoryPojo();
		ppp.setId(1);
		ppp.setQuantity(100);
		iservice.add(ppp);
		assertEquals(1,ppp.getId());
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
		InventoryPojo ppp = new InventoryPojo();
		ppp.setId(2);
		ppp.setQuantity(100);
		iservice.add(ppp);
		InventoryPojo bp = iservice.get(2);
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
		InventoryPojo ppp = new InventoryPojo();
		ppp.setId(3);
		ppp.setQuantity(100);
		iservice.add(ppp);
		List<InventoryPojo> l = iservice.getAll();
		
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
		InventoryPojo ppp = new InventoryPojo();
		ppp.setId(4);
		ppp.setQuantity(100);
		iservice.add(ppp);
		InventoryPojo bp = iservice.get(4);
		bp.setQuantity(120);
		iservice.update(4, bp);
		InventoryPojo pppp = iservice.get(4);

		assertEquals(120, pppp.getQuantity());
	}
	
	
	
	
	
	

	
	


}
