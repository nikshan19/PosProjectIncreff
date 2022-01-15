package table.Service;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.BrandDao;
import table.Dao.InventoryDao;
import table.Pojo.InventoryPojo;

@Service
public class InventoryService {
	
	@Autowired
	private InventoryDao dao;
	
	@Transactional
	public void add(InventoryPojo p) throws ApiException{
		normalize(p);
		dao.insert(p);
	}
	@Transactional(rollbackOn = ApiException.class)
	public void delete(int id) throws ApiException {
		getCheck(id);
		dao.delete(id);
		
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public InventoryPojo get(int id) throws ApiException {
		InventoryPojo p = getCheck(id);
		return p;
	}
	
	@Transactional
	public List<InventoryPojo> getAll() {
		return dao.selectAll();
	}
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id, InventoryPojo newPojo) throws ApiException {
		normalize(newPojo);
		InventoryPojo ex = getCheck(id);
		ex.setQuantity(newPojo.getQuantity());
		
		dao.update(newPojo);
	}
	@Transactional(rollbackOn = ApiException.class)
	public InventoryPojo getCheck(int id) throws ApiException {
		InventoryPojo p = dao.select(id);
		if(p == null) {
			throw new ApiException("Inventory with given id doesnot exists, id: "+id);
		}
		return p;
	}
	// traanactional can only be used on public methods
	private static void normalize(InventoryPojo p) {

	}
	

}
