package table.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import table.Dao.OrderDao;
import table.Pojo.OrderPojo;

@Service
public class OrderService {
	
	@Autowired
	private OrderDao dao;
	
	@Transactional
	public void add(OrderPojo p) {
		normalize(p);
		dao.insert(p);
	}
	@Transactional(rollbackOn = ApiException.class)
	public void delete(int id) throws ApiException {
		getCheck(id);
		dao.delete(id);
		
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public OrderPojo get(int id) throws ApiException {
		OrderPojo p = getCheck(id);
		return p;
	}
	
	@Transactional
	public List<OrderPojo> getAll() {
		return dao.selectAll();
	}
	@Transactional(rollbackOn = ApiException.class)
	public void update(int id, OrderPojo newPojo) throws ApiException {
		normalize(newPojo);
		OrderPojo ex = getCheck(id);
	
		dao.update(newPojo);
	}
	@Transactional(rollbackOn = ApiException.class)
	public OrderPojo getCheck(int id) throws ApiException {
		OrderPojo p = dao.select(id);
		if(p == null) {
			throw new ApiException("Order with given id doesnot exists, id: "+id);
		}
		return p;
	}
	// traanactional can only be used on public methods
	private static void normalize(OrderPojo p) {
		
	}
	

}
