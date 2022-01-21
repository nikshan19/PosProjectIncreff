package table.Controller;

import java.util.ArrayList;
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

import table.Model.BrandData;
import table.Model.BrandForm;
import table.Model.ErrorData;
import table.Pojo.BrandPojo;
import table.Service.ApiException;
import table.Service.BrandService;

@Api
@RestController
public class BrandController {
	
	@Autowired
	private BrandService service;
	
	
	
	
	@ApiOperation(value="Adds an employee")
	@RequestMapping(path="/api/brand", method=RequestMethod.POST)
	public ResponseEntity<Object> add(@RequestBody BrandForm form) {
		try {
		BrandPojo p = convert(form);
		service.add(p);
		return new ResponseEntity<Object>(HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<Object>("Brand-Category Combination cannot be repeated",HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

	@ApiOperation(value="Deletes an employee")
	@RequestMapping(path="/api/brand/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable int id) throws ApiException {
		try {
		 service.delete(id);
		 return new ResponseEntity<Object>(HttpStatus.OK);
		}catch(ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@ApiOperation(value="Updates an employee")
	@RequestMapping(path="/api/brand/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> update(@PathVariable int id, @RequestBody BrandForm form) throws ApiException {
		try {
		 BrandPojo p = convert(form);
		 service.update(id, p);
		 return new ResponseEntity<Object>(HttpStatus.OK);
		}catch(ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
	}


	@ApiOperation(value="Gets a single employee by ID")
	@RequestMapping(path="/api/brand/{id}", method=RequestMethod.GET)
	public BrandData get(@PathVariable int id) throws ApiException{
		BrandPojo p = service.get(id);
		return convert(p);
	}
	
	


	@ApiOperation(value="Gets list of all employees")
	@RequestMapping(path="/api/brand", method=RequestMethod.GET)
	public List <BrandData> getAll(){
		List<BrandPojo> list = service.getAll();
		List<BrandData> list2 = new ArrayList<BrandData>();
		for(BrandPojo p:list) {
			
			list2.add(convert(p));
		}
		return list2;
	}
	
	
	private static  BrandPojo convert(BrandForm form) {
		BrandPojo p = new BrandPojo();
		p.setBrand(form.getBrand());
		p.setCategory(form.getCategory());
		
		return p;
	}
	
	private static BrandData convert(BrandPojo p) {
		BrandData data = new BrandData();
		data.setBrand(p.getBrand());
		data.setCategory(p.getCategory());
		
		data.setId(p.getId());
		return data;
	}
	
	
	
	

}
