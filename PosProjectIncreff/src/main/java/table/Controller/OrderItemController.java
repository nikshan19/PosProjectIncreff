package table.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import table.Model.EditOrderForm;
import table.Model.OrderItemData;
import table.Model.OrderItemForm;
import table.Pojo.OrderItemPojo;
import table.Service.ApiException;
import table.Service.OrderItemService;

@Api
@RestController
public class OrderItemController {
	
	@Autowired
	private OrderItemService service;
	
	@ApiOperation(value="Adds an orderitem")
	@RequestMapping(path="/api/orderitem", method=RequestMethod.POST)
	public void add(@RequestBody OrderItemForm form) throws ApiException {
		OrderItemPojo p = convert(form);
		service.add(p, form.getBarcode(), form.getMrp());
	}
	
	

	@ApiOperation(value="Deletes an orderitem")
	@RequestMapping(path="/api/orderitem/{orderId}", method=RequestMethod.DELETE)
	public void delete(@PathVariable int orderId) throws ApiException {
		 service.delete(orderId);
	}
	
	
	@ApiOperation(value="Updates an orderitem")
	@RequestMapping(path="/api/orderitem/{orderId}", method=RequestMethod.PUT)
	public void update(@PathVariable int orderId, @RequestBody EditOrderForm form) throws ApiException {
		 OrderItemPojo p = convert_edit(form);
		 p.setOrderId(orderId);
		 service.update(p, form.getBarcode(),form.getId(), form.getQuantity());
		 
	}


	@ApiOperation(value="Gets a single orderitem by ID")
	@RequestMapping(path="/api/orderitem/{orderId}", method=RequestMethod.GET)
	public List<OrderItemData> get(@PathVariable int orderId) throws ApiException{
		HashMap<OrderItemPojo, String> hm = service.get(orderId);
		List<OrderItemData> list2 = new ArrayList<OrderItemData>();
		for(OrderItemPojo p:hm.keySet()) {
			String b = hm.get(p);
			OrderItemData d = convert(p);
			d.setBarcode(b);
			list2.add(d);
		}
		return list2;
		
	}
	
	


	@ApiOperation(value="Gets list of all orderitems")
	@RequestMapping(path="/api/orderitem", method=RequestMethod.GET)
	public List <OrderItemData> getAll(){
		HashMap<OrderItemPojo, String> hm = service.getAll();
		List<OrderItemData> list2 = new ArrayList<OrderItemData>();
		for(OrderItemPojo p:hm.keySet()) {
			String b = hm.get(p);
			OrderItemData d = convert(p);
			d.setBarcode(b);
			list2.add(d);
		}
		return list2;
	}
	
	
	private static  OrderItemPojo convert(OrderItemForm form) {
		OrderItemPojo p = new OrderItemPojo();
		p.setQuantity(form.getQuantity());
		return p;
	}
	
	private static OrderItemPojo convert_edit(EditOrderForm form) {
		OrderItemPojo p = new OrderItemPojo();
		p.setMrp(form.getMrp());
		return p;
	}
	
	private static OrderItemData convert(OrderItemPojo p) {
		OrderItemData data = new OrderItemData();
		data.setId(p.getId());
		data.setOrderId(p.getOrderId());
		data.setProductId(p.getProductId());
		data.setQuantity(p.getQuantity());
		data.setMrp(p.getMrp());
		return data;
	}

}
