package table.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import table.Model.InventoryReportData;
import table.Pojo.BrandPojo;
import table.Service.InventoryReportService;

@Api
@RestController
public class InventoryReportController {
	
	@Autowired
	private InventoryReportService service;
	

	


	@ApiOperation(value="Gets list of all employees")
	@RequestMapping(path="/api/inventoryreport", method=RequestMethod.GET)
	public List <InventoryReportData> getAll(){
		
		
		HashMap<BrandPojo, Integer> hm = service.getAll();
		List<InventoryReportData> list2 = new ArrayList<InventoryReportData>();
		for(BrandPojo b:hm.keySet()) {
			InventoryReportData d = new InventoryReportData();
			d.setQuantity(hm.get(b));
			d.setBrand(b.getBrand());
			d.setCategory(b.getCategory());
			list2.add(d);
		}
		return list2;
	}
	
	
	

}
