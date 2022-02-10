package table.Controller;

import java.util.ArrayList;
import java.util.HashMap;
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
import table.Dto.ProductDto;
import table.Model.ProductData;
import table.Model.ProductForm;
import table.Model.ProductFormUpload;
import table.Pojo.ProductPojo;
import table.Service.ApiException;
import table.Service.ProductService;

@Api
@RestController
public class ProductController {

	@Autowired
	private ProductDto dto;

	@ApiOperation(value = "Adds an product")
	@RequestMapping(path = "/api/product", method = RequestMethod.POST)
	public ResponseEntity<Object> add(@RequestBody ProductForm form) throws ApiException {

		try {
			dto.add(form);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ApiOperation(value = "Adds an product")
	@RequestMapping(path = "/api/upload/product", method = RequestMethod.POST)
	public ResponseEntity<Object> upload(@RequestBody ProductFormUpload form) throws ApiException {

		try {
			dto.upload(form);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ApiOperation(value = "Deletes an product")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable int id) throws ApiException {

		try {
			dto.delete(id);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Updates an product")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@PathVariable int id, @RequestBody ProductForm form) throws ApiException {

		try {
			dto.update(id, form);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ApiOperation(value = "Gets a single product by ID")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.GET)
	public ProductData get(@PathVariable int id) throws ApiException {

		return dto.get(id);
	}

	@ApiOperation(value = "Gets list of all products")
	@RequestMapping(path = "/api/product", method = RequestMethod.GET)
	public List<ProductData> getAll() throws ApiException {
		return dto.getAll();
	}

}
