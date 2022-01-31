package table.Dto;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.OrderDao;
import table.Model.OrderData;
import table.Model.OrderForm;
import table.Pojo.OrderPojo;
import table.Service.ApiException;
import table.Service.OrderService;

@Service
public class OrderDto {

	@Autowired
	private OrderDao dao;
	@Autowired
	private OrderService service;

	public void add(OrderForm form) throws ApiException {

		OrderPojo p = convert(form);
		service.add(p);
	}

	public void delete(int id) throws ApiException {
		service.delete(id);

	}

	public void update(int id, OrderForm form) throws ApiException {

		OrderPojo p = convert(form);
		service.update(id, p);

	}

	public OrderData get(int id) throws ApiException {

		OrderPojo p = service.get(id);
		return convert(p);

	}

	public List<OrderData> getAll() {

		List<OrderPojo> list = service.getAll();
		List<OrderData> list2 = new ArrayList<OrderData>();
		for (OrderPojo p : list) {

			list2.add(convert(p));
		}
		return list2;
	}

	public OrderPojo convert(OrderForm form) {
		OrderPojo p = new OrderPojo();
		return p;
	}

	public OrderData convert(OrderPojo p) {
		OrderData data = new OrderData();
		data.setDateTime(p.getDateTime().toString());
		data.setId(p.getId());
		return data;
	}

	@Transactional(rollbackOn = ApiException.class)
	public OrderPojo getCheck(int id) throws ApiException {
		OrderPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("Order with given id doesnot exists, id: " + id);
		}
		return p;
	}

	// traanactional can only be used on public methods
	public void normalize(OrderPojo p) {

	}

}
