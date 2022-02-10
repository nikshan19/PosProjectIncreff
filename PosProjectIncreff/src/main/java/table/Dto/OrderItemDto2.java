package table.Dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.OrderItemDao2;
import table.Model.OrderItemData;
import table.Model.OrderItemForm;
import table.Pojo.OrderItemPojo;
import table.Service.ApiException;
import table.Service.OrderItemService2;

@Service
public class OrderItemDto2 {

	@Autowired
	private OrderItemDao2 dao;
	@Autowired
	private OrderItemService2 service;

	public void delete(int id) throws ApiException {
		service.delete(id);
	}

	public void update(int id, OrderItemForm form) throws ApiException {
		OrderItemPojo p = convert(form);
		p.setId(id);
		service.update(p, form.getBarcode(), form.getQuantity(), form.getMrp());

	}

	public OrderItemData get(int id) throws ApiException {

		HashMap<OrderItemPojo, String> hm = service.get(id);
		List<OrderItemPojo> l = new ArrayList<OrderItemPojo>(hm.keySet());
		OrderItemPojo p = l.get(0);
		String barcode = hm.get(p);

		OrderItemData data = convert(p);
		data.setBarcode(barcode);
		return data;

	}

	public OrderItemPojo convert(OrderItemForm form) {
		OrderItemPojo p = new OrderItemPojo();

		return p;
	}

	public OrderItemData convert(OrderItemPojo p) {
		OrderItemData data = new OrderItemData();
		data.setQuantity(p.getQuantity());
		data.setMrp(p.getMrp());

		data.setId(p.getId());
		return data;
	}

	public String normalize(String barcode) throws ApiException {

		String b = barcode.toLowerCase().trim();
		if (b.isBlank()) {
			throw new ApiException("Please enter valid barcode");
		}
		return b;
	}

	@Transactional(rollbackOn = ApiException.class)
	public HashMap<OrderItemPojo, String> getCheck(int id) throws ApiException {
		HashMap<OrderItemPojo, String> hm = dao.select(id);
		List<OrderItemPojo> l = new ArrayList<OrderItemPojo>(hm.keySet());
		OrderItemPojo p = l.get(0);
		if (p == null) {
			throw new ApiException("Brand with given id doesnot exists, id: " + id);
		}
		return hm;
	}

	@Transactional(rollbackOn = ApiException.class)
	public OrderItemPojo getCheck2(int id) throws ApiException {
		OrderItemPojo p = dao.onlySelect(id);

		if (p == null) {
			throw new ApiException("Brand with given id doesnot exists, id: " + id);
		}
		return p;
	}

}
