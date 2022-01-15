package table.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import table.Model.BrandData;
import table.Model.BrandForm;
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
	public void add(@RequestBody BrandForm form) {
		BrandPojo p = convert(form);
		service.add(p);
	}
	
	

	@ApiOperation(value="Deletes an employee")
	@RequestMapping(path="/api/brand/{id}", method=RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		 service.delete(id);
	}
	
	
	@ApiOperation(value="Updates an employee")
	@RequestMapping(path="/api/brand/{id}", method=RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody BrandForm form) throws ApiException {
		 BrandPojo p = convert(form);
		 service.update(id, p);
		 
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
