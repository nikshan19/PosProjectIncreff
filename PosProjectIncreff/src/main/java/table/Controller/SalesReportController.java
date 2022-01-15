package table.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import table.Model.BrandData;
import table.Model.SalesReportData;
import table.Model.SalesReportForm;
import table.Pojo.BrandPojo;
import table.Service.SalesReportService;

@Api
@RestController
public class SalesReportController {
	
	@Autowired
	private SalesReportService service;
	
	



	@ApiOperation(value="Gets list of all salesreports")
	@RequestMapping(path="/api/salesreport", method=RequestMethod.POST)
	public List <SalesReportData> getAll(@RequestBody SalesReportForm form){
		HashMap<Integer, Double> hm = service.getAll(form);
		List<SalesReportData> list2 = new ArrayList<SalesReportData>();
		for(Integer i:hm.keySet()) {
			SalesReportData d = new SalesReportData();
			d.setCategory(form.getCategory());
			d.setQuantity(i);
			d.setRevenue(hm.get(i));
			list2.add(d);
		}
		return list2;
	}
	
	


}
