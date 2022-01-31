package table.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import table.Service.ApiException;
import table.Service.SalesReportService;

@Api
@RestController
public class SalesReportController {
	
	@Autowired
	private SalesReportService service;

	@ApiOperation(value="Gets list of all salesreports")
	@RequestMapping(path="/api/salesreport", method=RequestMethod.POST)
	public ResponseEntity<?> getAll(@RequestBody SalesReportForm form) throws ApiException{
		try {
		HashMap<List<String>, HashMap<Integer, Double>> hm2 = service.getAll(form);
		List<SalesReportData> list2 = new ArrayList<SalesReportData>();
		List<List<String>> list = new ArrayList<List<String>>(hm2.keySet());
		for(List<String> i:list) {
			SalesReportData d = new SalesReportData();
			d.setBrand(i.get(0));
			d.setCategory(i.get(1));
			HashMap<Integer, Double> hm = hm2.get(i);
			int q = new ArrayList<Integer>(hm.keySet()).get(0);
			d.setQuantity(q);
			d.setRevenue(hm.get(q));
			list2.add(d);
		}
		return ResponseEntity.accepted().body(list2);
		}catch(ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	


}
