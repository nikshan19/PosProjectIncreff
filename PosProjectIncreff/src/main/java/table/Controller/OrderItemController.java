package table.Controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import table.Model.EditOrderForm;
import table.Model.OrderItemData;
import table.Model.OrderItemForm;
import table.Pojo.OrderItemPojo;
import table.Service.ApiException;
import table.Service.OrderItemService;
import table.Service.PDFGeneratorService;

@Api
@RestController
public class OrderItemController {
	
	@Autowired
	private OrderItemService service;
	@Autowired 
	private PDFGeneratorService pservice;
	private List<OrderItemData> list;
	
	@ApiOperation(value="Adds an orderitem")
	@RequestMapping(path="/api/orderitem", method=RequestMethod.POST)
	public ResponseEntity<Object> add(@RequestBody OrderItemForm form) throws ApiException {
		
		try {
		OrderItemPojo p = convert(form);
		service.add(p, form.getBarcode(), form.getMrp());
		return new ResponseEntity<Object>(HttpStatus.OK);
		}catch(ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

	@ApiOperation(value="Deletes an orderitem")
	@RequestMapping(path="/api/orderitem/{orderId}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable int orderId) throws ApiException {
		 try { 
		 service.delete(orderId);
		 return new ResponseEntity<Object>(HttpStatus.OK);
			}catch(ApiException e) {
				return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
	}
	
	
	@ApiOperation(value="Updates an orderitem")
	@RequestMapping(path="/api/orderitem/{orderId}", method=RequestMethod.PUT)
	public ResponseEntity<Object> update(@PathVariable int orderId, @RequestBody EditOrderForm form) throws ApiException {
		try { 
		 OrderItemPojo p = convert_edit(form);
		 p.setOrderId(orderId);
		 service.update(p, form.getBarcode(),form.getId(), form.getQuantity());
		 return new ResponseEntity<Object>(HttpStatus.OK);
		}catch(ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		 
	}


	@ApiOperation(value="Gets a single orderitem by ID")
	@RequestMapping(path="/api/orderitem/{orderId}", method=RequestMethod.GET)
	public List<OrderItemData> get(@PathVariable int orderId){
		HashMap<OrderItemPojo, String> hm = service.get(orderId);
		List<OrderItemData> list2 = new ArrayList<OrderItemData>();
		for(OrderItemPojo p:hm.keySet()) {
			String b = hm.get(p);
			OrderItemData d = convert(p);
			d.setBarcode(b);
			list2.add(d);
		}
		list = new ArrayList<OrderItemData>(list2);
		return list2;
		
	}
	@ApiOperation(value="Gets a single orderitem by ID")
	@RequestMapping(path="/api/pdf/{orderId}", method=RequestMethod.GET)
	
		public void generatePDF(HttpServletResponse response) throws IOException, DocumentException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());

	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);

	        pservice.export(response,list);
	        System.out.println("size: "+list.size());
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
