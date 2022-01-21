package table.Service;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.OrderItemDao;
import table.Pojo.OrderItemPojo;

@Service
public class OrderItemService {
	
	@Autowired
	private OrderItemDao dao;
	
	@Transactional
	public void add(OrderItemPojo p, String barcode, double mrp) throws ApiException {
		String b = normalize(p, barcode);
		dao.insert(p, b, mrp);
	}
	@Transactional(rollbackOn = ApiException.class)
	public void delete(int id) throws ApiException {
		getCheck(id);
		dao.delete(id);
		
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public HashMap<OrderItemPojo, String> get(int id){
		return dao.select(id);
		
	}
	
	@Transactional
	public HashMap<OrderItemPojo, String> getAll() {
		return dao.selectAll();
	}
	@Transactional(rollbackOn = ApiException.class)
	public void update(OrderItemPojo newPojo, String barcode, int id, int quantity) throws ApiException {
		normalize(newPojo, barcode);
		OrderItemPojo ex = dao.onlySelect(id, newPojo.getOrderId());
		if(ex == null) {
			throw new ApiException("OrderItem with given id doesnot exists, id: "+id);
		}
		
		ex.setOrderId(newPojo.getOrderId());
		//ex.setQuantity(newPojo.getQuantity());
		ex.setMrp(newPojo.getMrp());
		
		dao.update(ex, barcode, quantity);
	}
	@Transactional(rollbackOn = ApiException.class)
	public HashMap<OrderItemPojo, String> getCheck(int id) throws ApiException {
		HashMap<OrderItemPojo, String> list = dao.select(id);
		if(list.size() == 0) {
			throw new ApiException("Order with given id doesnot exists in this ORDER, id: "+id);
		}
		return list;
	}
	
	
	// traanactional can only be used on public methods
	private static String normalize(OrderItemPojo p, String barcode) {
		String b = barcode.toLowerCase().trim();
		return b;
	}
	

}
