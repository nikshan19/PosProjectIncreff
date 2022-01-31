package table.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.BrandDao;
import table.Dao.OrderItemDao2;
import table.Dto.OrderItemDto2;
import table.Pojo.BrandPojo;
import table.Pojo.OrderItemPojo;

@Service
public class OrderItemService2 {

	@Autowired
	private OrderItemDao2 dao;
	@Autowired
	private OrderItemDto2 dto;

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
		String barcode2 = dto.normalize(barcode);
		OrderItemPojo ex = dto.getCheck2(newPojo.getId());
		ex.setOrderId(0);
		dao.update(ex, barcode2, quantity, mrp);
	}

}
