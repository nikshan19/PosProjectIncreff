package table.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.ProductDao;
import table.Pojo.ProductPojo;

@Service
public class ProductService {
	
	@Autowired
	private ProductDao dao;
	
	@Transactional
	public void add(ProductPojo p)throws ApiException {
		normalize(p);
		dao.insert(p);
	}
	@Transactional(rollbackOn = ApiException.class)
	public void delete(int id) throws ApiException {
		getCheck(id);
		dao.delete(id);
		
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public ProductPojo get(int id) throws ApiException {
		ProductPojo p = getCheck(id);
		return p;
	}
	
	@Transactional
	public List<ProductPojo> getAll() {
		return dao.selectAll();
	}
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id, ProductPojo newPojo) throws ApiException {
		normalize(newPojo);
		ProductPojo ex = getCheck(id);
		ex.setBarcode(newPojo.getBarcode());
		ex.setBrandPojo(newPojo.getBrandPojo());
		ex.setName(newPojo.getName());
		ex.setMrp(newPojo.getMrp());
		dao.update(newPojo);
	}
	@Transactional(rollbackOn = ApiException.class)
	public ProductPojo getCheck(int id) throws ApiException {
		ProductPojo p = dao.select(id);
		if(p == null) {
			throw new ApiException("Product with given id doesnot exists, id: "+id);
		}
		return p;
	}
	// traanactional can only be used on public methods
	private static void normalize(ProductPojo p) {
		
		p.setBarcode(p.getBarcode().toLowerCase().trim());
		p.setName(p.getName().toLowerCase().trim());
		
	}
	

}
