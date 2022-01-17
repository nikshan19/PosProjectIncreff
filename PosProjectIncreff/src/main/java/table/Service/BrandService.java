package table.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.BrandDao;
import table.Pojo.BrandPojo;

@Service
public class BrandService {
	
	@Autowired
	private BrandDao dao;
	
	@Transactional
	public void add(BrandPojo p) {
		normalize(p);
		dao.insert(p);
	}
	@Transactional(rollbackOn = ApiException.class)
	public void delete(int id) throws ApiException {
		getCheck(id);
		dao.delete(id);
		
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public BrandPojo get(int id) throws ApiException {
		BrandPojo p = getCheck(id);
		return p;
	}
	
	@Transactional
	public List<BrandPojo> getAll() {
		return dao.selectAll();
	}
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id, BrandPojo newPojo) throws ApiException {
		normalize(newPojo);
		BrandPojo ex = getCheck(id);
		ex.setBrand(newPojo.getBrand());
		ex.setCategory(newPojo.getCategory());
		
		dao.update(newPojo);
	}
	@Transactional(rollbackOn = ApiException.class)
	public BrandPojo getCheck(int id) throws ApiException {
		BrandPojo p = dao.select(id);
		if(p == null) {
			throw new ApiException("Brand with given id doesnot exists, id: "+id);
		}
		return p;
	}
	// traanactional can only be used on public methods
	protected static void normalize(BrandPojo p) {
		
		p.setBrand(p.getBrand().toLowerCase().trim());
		p.setCategory(p.getCategory().toLowerCase().trim());
	}
	

}
