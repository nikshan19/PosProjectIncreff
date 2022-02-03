package table.Service;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.OrderItemDao;
import table.Dto.OrderItemDto;
import table.Model.OrderItemData;
import table.Pojo.OrderItemPojo;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemDao dao;
	@Autowired
	private OrderItemDto dto;

	@Transactional
	public void add(OrderItemPojo p, String barcode, double mrp) throws ApiException {
		String b = dto.normalize(p, barcode);
		dao.insert(p, b, mrp);
	}

	@Transactional
	public List<OrderItemData> getAll(int id) {
		return dao.select(id);
	}
	
	@Transactional(rollbackOn = ApiException.class)
	public void delete(int id) throws ApiException {
		dto.getCheck(id);
		dao.delete(id);

	}
	
	@Transactional(rollbackOn = ApiException.class)
	public HashMap<OrderItemPojo, String> get(int id) throws ApiException {
		HashMap<OrderItemPojo, String> hm = dto.getCheck(id);
		return hm;
	}

	@Transactional(rollbackOn = ApiException.class)
	public void update(OrderItemPojo newPojo, String barcode, int quantity, double mrp) throws ApiException {
		String barcode2 = dto.normalize2(barcode);
		OrderItemPojo ex = dto.getCheck2(newPojo.getId());
		//ex.setOrderId(0);
		dao.update(ex, barcode2, quantity, mrp);
	}
	

//	@Transactional(rollbackOn = ApiException.class)
//	public void update(OrderItemPojo newPojo, String barcode, int id, int quantity) throws ApiException {
//		dto.normalize(newPojo, barcode);
//		OrderItemPojo ex = dao.onlySelect(id, newPojo.getOrderId());
//		if (ex == null) {
//			throw new ApiException("OrderItem with given id doesnot exists, id: " + id);
//		}
//
//		ex.setOrderId(newPojo.getOrderId());
//		ex.setMrp(newPojo.getMrp());
//
//		dao.update(ex, barcode, quantity);
//	}

}
