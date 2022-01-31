package table.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.OrderDao;
import table.Dto.OrderDto;
import table.Pojo.OrderPojo;

@Service
public class OrderService {

	@Autowired
	private OrderDao dao;
	@Autowired
	private OrderDto dto;

	@Transactional
	public void add(OrderPojo p) throws ApiException {
		dto.normalize(p);
		dao.insert(p);
	}

	@Transactional(rollbackOn = ApiException.class)
	public void delete(int id) throws ApiException {
		dto.getCheck(id);
		dao.delete(id);

	}

	@Transactional(rollbackOn = ApiException.class)
	public OrderPojo get(int id) throws ApiException {
		OrderPojo p = dto.getCheck(id);
		return p;
	}

	@Transactional
	public List<OrderPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackOn = ApiException.class)
	public void update(int id, OrderPojo newPojo) throws ApiException {
		dto.normalize(newPojo);
		OrderPojo ex = dto.getCheck(id);

		dao.update(newPojo);
	}

}
