package table.Controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import table.Dto.OrderDto;
import table.Model.OrderData;
import table.Model.OrderForm;
import table.Pojo.OrderItemPojo;
import table.Pojo.OrderPojo;
import table.Service.ApiException;
import table.Service.OrderService;
import table.Service.PDFGeneratorService;

@Api
@RestController
public class OrderController {

	@Autowired 
	private PDFGeneratorService pservice;
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
	@RequestMapping(path = "/api/order/update/{id}", method = RequestMethod.GET)
	public void update(@PathVariable int id) throws ApiException {
		dto.update(id);

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
	
	@ApiOperation(value="Gets a single orderitem by ID")
	@RequestMapping(path="/api/order/pdf/{id}", method=RequestMethod.GET)
	
		public void generatePDF(@PathVariable int id, HttpServletResponse response) throws IOException, DocumentException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());

	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	        List<OrderItemPojo> list = pservice.getSpecList(id); 
	        pservice.export(response,list);
	        System.out.println("size: "+list.size());
	    }

}
