package table.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
	
	@ApiOperation(value="Adds an order")
	@RequestMapping(path="/api/order", method=RequestMethod.POST)
	public void add(@RequestBody OrderForm form) {
		OrderPojo p = convert(form);
		service.add(p);
	}
	
	

	@ApiOperation(value="Deletes an order")
	@RequestMapping(path="/api/order/{id}", method=RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		 service.delete(id);
	}
	
	
	@ApiOperation(value="Updates an order")
	@RequestMapping(path="/api/order/{id}", method=RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody OrderForm form) throws ApiException {
		 OrderPojo p = convert(form);
		 service.update(id, p);
		 
	}


	@ApiOperation(value="Gets a single order by ID")
	@RequestMapping(path="/api/order/{id}", method=RequestMethod.GET)
	public OrderData get(@PathVariable int id) throws ApiException{
		
		OrderPojo p = service.get(id);
		return convert(p);
	}
	
	


	@ApiOperation(value="Gets list of all orders")
	@RequestMapping(path="/api/order", method=RequestMethod.GET)
	public List <OrderData> getAll(){
		List<OrderPojo> list = service.getAll();
		List<OrderData> list2 = new ArrayList<OrderData>();
		for(OrderPojo p:list) {
			
			list2.add(convert(p));
		}
		return list2;
	}
	
	
	private static  OrderPojo convert(OrderForm form) {
		OrderPojo p = new OrderPojo();
		return p;
	}
	
	private static OrderData convert(OrderPojo p) {
		OrderData data = new OrderData();
		data.setDateTime(p.getDateTime().toString());
		data.setId(p.getId());
		return data;
	}

}
