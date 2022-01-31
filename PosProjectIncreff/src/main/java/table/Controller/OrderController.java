package table.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import table.Dto.OrderDto;
import table.Model.OrderData;
import table.Model.OrderForm;
import table.Pojo.OrderPojo;
import table.Service.ApiException;
import table.Service.OrderService;

@Api
@RestController
public class OrderController {

	@Autowired
	private OrderService service;
	@Autowired
	private OrderDto dto;

	@ApiOperation(value = "Adds an order")
	@RequestMapping(path = "/api/order", method = RequestMethod.POST)
	public ResponseEntity<Object> add(@RequestBody OrderForm form) throws ApiException {
		try {
			dto.add(form);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Deletes an order")
	@RequestMapping(path = "/api/order/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		dto.delete(id);
	}

	@ApiOperation(value = "Updates an order")
	@RequestMapping(path = "/api/order/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody OrderForm form) throws ApiException {
		dto.update(id, form);

	}

	@ApiOperation(value = "Gets a single order by ID")
	@RequestMapping(path = "/api/order/{id}", method = RequestMethod.GET)
	public OrderData get(@PathVariable int id) throws ApiException {

		return dto.get(id);
	}

	@ApiOperation(value = "Gets list of all orders")
	@RequestMapping(path = "/api/order", method = RequestMethod.GET)
	public List<OrderData> getAll() {
		return dto.getAll();
	}

}
