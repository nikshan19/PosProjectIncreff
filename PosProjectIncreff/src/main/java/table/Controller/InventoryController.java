package table.Controller;

import java.util.ArrayList;
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
import table.Model.InventoryData;
import table.Model.InventoryForm;
import table.Pojo.InventoryPojo;
import table.Service.ApiException;
import table.Service.BrandService;
import table.Service.InventoryService;


@Api
@RestController
public class InventoryController {
	
	@Autowired
	private InventoryService service;
	
	@ApiOperation(value="Adds an inventory")
	@RequestMapping(path="/api/inventory", method=RequestMethod.POST)
	public ResponseEntity<Object> add(@RequestBody InventoryForm form) throws ApiException {
		try {
		InventoryPojo p = convert(form);
		service.add(p);
		return new ResponseEntity<Object>(HttpStatus.OK);
		}catch(ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	

	@ApiOperation(value="Deletes an inventory")
	@RequestMapping(path="/api/inventory/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable int id) throws ApiException {
		try {
		 service.delete(id);
		 return new ResponseEntity<Object>(HttpStatus.OK);
		}catch(ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@ApiOperation(value="Updates an inventory")
	@RequestMapping(path="/api/inventory/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Object> update(@PathVariable int id, @RequestBody InventoryForm form) throws ApiException {
		try {
		 InventoryPojo p = new InventoryPojo();
		 p.setQuantity(form.getQuantity());
		 service.update(id, p);
		 return new ResponseEntity<Object>(HttpStatus.OK);
		}catch(ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		 
	}

// no need to put this code in try catch blocks as id will never be updated.
	@ApiOperation(value="Gets a single inventory by ID")
	@RequestMapping(path="/api/inventory/{id}", method=RequestMethod.GET)
	public InventoryData get(@PathVariable int id) throws ApiException{
		
		InventoryPojo p = service.get(id);
		return convert(p);
	}
	
	


	@ApiOperation(value="Gets list of all employees")
	@RequestMapping(path="/api/inventory", method=RequestMethod.GET)
	public List <InventoryData> getAll(){
		List<InventoryPojo> list = service.getAll();
		List<InventoryData> list2 = new ArrayList<InventoryData>();
		for(InventoryPojo p:list) {
			
			list2.add(convert(p));
		}
		return list2;
	}
	
	
	private static  InventoryPojo convert(InventoryForm form) {
		InventoryPojo p = new InventoryPojo();
		p.setQuantity(form.getQuantity());
		p.setId(form.getId());
		return p;
	}
	
	private static InventoryData convert(InventoryPojo p) {
		InventoryData data = new InventoryData();
		data.setQuantity(p.getQuantity());
		data.setId(p.getId());
		return data;
	}
	
	


}
