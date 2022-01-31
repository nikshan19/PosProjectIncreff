package table.Dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.OrderItemDao;
import table.Model.EditOrderForm;
import table.Model.OrderItemData;
import table.Model.OrderItemForm;
import table.Pojo.OrderItemPojo;
import table.Service.ApiException;
import table.Service.OrderItemService;

@Service
public class OrderItemDto {

	@Autowired
	private OrderItemDao dao;
	@Autowired
	private OrderItemService service;

	public void add(OrderItemForm form) throws ApiException {

		OrderItemPojo p = convert(form);
		service.add(p, form.getBarcode(), form.getMrp());
	}

	public List<OrderItemData> getAll(int id) {

		return service.getAll(id);
	}

	public OrderItemPojo convert(OrderItemForm form) {
		OrderItemPojo p = new OrderItemPojo();
		p.setQuantity(form.getQuantity());
		return p;
	}

	public OrderItemPojo convert_edit(EditOrderForm form) {
		OrderItemPojo p = new OrderItemPojo();
		p.setMrp(form.getMrp());
		return p;
	}

	public OrderItemData convert(OrderItemPojo p) {
		OrderItemData data = new OrderItemData();
		data.setId(p.getId());
		data.setOrderId(p.getOrderId());
		data.setProductId(p.getProductId());
		data.setQuantity(p.getQuantity());
		data.setMrp(p.getMrp());
		return data;
	}

	// traanactional can only be used on public methods
	public String normalize(OrderItemPojo p, String barcode) {
		String b = barcode.toLowerCase().trim();
		return b;
	}

}
