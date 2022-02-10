package table.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import table.Dto.InventoryDto;
import table.Model.InventoryData;
import table.Model.InventoryForm;
import table.Model.InventoryFormUpload;
import table.Model.ProductForm;
import table.Model.ProductFormUpload;
import table.Pojo.InventoryPojo;
import table.Pojo.ProductPojo;
import table.Service.ApiException;
import table.Service.BrandService;
import table.Service.InventoryService;

@Api
@RestController
public class InventoryController {

	@Autowired
	private InventoryDto dto;

	@ApiOperation(value = "Adds an inventory")
	@RequestMapping(path = "/api/inventory", method = RequestMethod.POST)
	public ResponseEntity<Object> add(@RequestBody InventoryForm form) throws ApiException {
		try {
			dto.add(form);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Adds an inventory")
	@RequestMapping(path = "/api/upload/inventory", method = RequestMethod.POST)
	public ResponseEntity<Object> upload(@RequestBody InventoryFormUpload form) throws ApiException {
		try {

			dto.upload(form);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Deletes an inventory")
	@RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable int id) throws ApiException {
		try {
			dto.delete(id);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Updates an inventory")
	@RequestMapping(path = "/api/inventory/{barcode}", method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@PathVariable String barcode, @RequestBody InventoryForm form)
			throws ApiException {
		try {
			dto.update(barcode, form);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ApiOperation(value = "Gets a single inventory by ID")
	@RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
	public InventoryData get(@PathVariable int id) throws ApiException {

		return dto.get(id);
	}

	@ApiOperation(value = "Gets list of all employees")
	@RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
	public List<InventoryData> getAll() {
		return dto.getAll();
	}

}
