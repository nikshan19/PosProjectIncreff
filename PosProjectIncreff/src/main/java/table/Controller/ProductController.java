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
import table.Model.ProductData;
import table.Model.ProductForm;
import table.Pojo.ProductPojo;
import table.Service.ApiException;
import table.Service.ProductService;

@Api
@RestController
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@ApiOperation(value="Adds an product")
	@RequestMapping(path="/api/product", method=RequestMethod.POST)
	public ResponseEntity<Object> add(@RequestBody ProductForm form) throws ApiException {
		
		try {
		ProductPojo p = convert(form);
		service.add(p);
		return new ResponseEntity<Object>(HttpStatus.OK);
		}catch(ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	

	@ApiOperation(value="Deletes an product")
	@RequestMapping(path="/api/product/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable int id) throws ApiException {
		
		try {
		 service.delete(id);
		 return new ResponseEntity<Object>(HttpStatus.OK);
		}catch(ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@ApiOperation(value="Updates an product")
	@RequestMapping(path="/api/product/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> update(@PathVariable int id, @RequestBody ProductForm form) throws ApiException {
		
		try {
		 ProductPojo p = convert(form);
		 service.update(id, p);
		 return new ResponseEntity<Object>(HttpStatus.OK);
		}catch(ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
	}


	@ApiOperation(value="Gets a single product by ID")
	@RequestMapping(path="/api/product/{id}", method=RequestMethod.GET)
	public ProductData get(@PathVariable int id) throws ApiException{
		
		ProductPojo p = service.get(id);
		return convert(p);
	}
	
	


	@ApiOperation(value="Gets list of all products")
	@RequestMapping(path="/api/product", method=RequestMethod.GET)
	public List <ProductData> getAll(){
		List<ProductPojo> list = service.getAll();
		List<ProductData> list2 = new ArrayList<ProductData>();
		for(ProductPojo p:list) {
			
			list2.add(convert(p));
		}
		return list2;
	}
	
	
	private static  ProductPojo convert(ProductForm form) {
		ProductPojo p = new ProductPojo();
		p.setBarcode(form.getBarcode());
		p.setBrandPojo(form.getBrandPojo());
		p.setName(form.getName());
		p.setMrp(form.getMrp());
		return p;
	}
	
	private static ProductData convert(ProductPojo p) {
		ProductData data = new ProductData();
		data.setBarcode(p.getBarcode());
		data.setBrandPojo(p.getBrandPojo());
		data.setName(p.getName());
		data.setMrp(p.getMrp());
		data.setId(p.getId());
		return data;
	}

}
