package table.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import table.Dto.OrderItemDto2;
import table.Model.BrandData;
import table.Model.BrandForm;
import table.Model.ErrorData;
import table.Model.OrderItemData;
import table.Model.OrderItemForm;
import table.Pojo.BrandPojo;
import table.Pojo.OrderItemPojo;
import table.Service.ApiException;
import table.Service.BrandService;
import table.Service.OrderItemService2;

@Api
@RestController
public class OrderItemController2 {

	@Autowired
	private OrderItemService2 service;
	@Autowired
	private OrderItemDto2 dto;

	@ApiOperation(value = "Deletes an employee")
	@RequestMapping(path = "/api/orderitem2/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable int id) throws ApiException {
		try {
			dto.delete(id);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Updates an employee")
	@RequestMapping(path = "/api/orderitem2/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@PathVariable int id, @RequestBody OrderItemForm form) throws ApiException {
		try {
			dto.update(id, form);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ApiOperation(value = "Gets a single employee by ID")
	@RequestMapping(path = "/api/orderitem2/{id}", method = RequestMethod.GET)
	public OrderItemData get(@PathVariable int id) throws ApiException {
		return dto.get(id);
	}

}
