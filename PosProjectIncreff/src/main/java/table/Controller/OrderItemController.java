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
import table.Dto.OrderItemDto;
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
	private OrderItemDto dto;

	@ApiOperation(value = "Adds an orderitem")
	@RequestMapping(path = "/api/orderitem", method = RequestMethod.POST)
	public ResponseEntity<Object> add(@RequestBody OrderItemForm form) throws ApiException {

		try {
			dto.add(form);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Gets list of all orderitems")
	@RequestMapping(path = "/api/orderitem/{orderid}", method = RequestMethod.GET)
	public List<OrderItemData> getAll(@PathVariable int orderid) {
		return dto.getAll(orderid);
	}

}
