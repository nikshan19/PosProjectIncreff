package table.Service;

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
	public void update(OrderItemPojo newPojo, String barcode, int id, int quantity) throws ApiException {
		dto.normalize(newPojo, barcode);
		OrderItemPojo ex = dao.onlySelect(id, newPojo.getOrderId());
		if (ex == null) {
			throw new ApiException("OrderItem with given id doesnot exists, id: " + id);
		}

		ex.setOrderId(newPojo.getOrderId());
		ex.setMrp(newPojo.getMrp());

		dao.update(ex, barcode, quantity);
	}

}
