package table.Controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import table.Model.BrandData;
import table.Model.InventoryReportData;
import table.Pojo.BrandPojo;
import table.Service.InventoryReportService;
import table.Service.PDFGeneratorService3;

@Api
@RestController
public class InventoryReportController {

	@Autowired
	private InventoryReportService service;
	@Autowired
	private PDFGeneratorService3 pservice;

	private List<InventoryReportData> l;

	@ApiOperation(value = "Gets list of all employees")
	@RequestMapping(path = "/api/inventoryreport", method = RequestMethod.GET)
	public List<InventoryReportData> getAll() {

		HashMap<BrandPojo, Integer> hm = service.getAll();
		List<InventoryReportData> list2 = new ArrayList<InventoryReportData>();
		if (hm.keySet().size() == 0) {
			return list2;
		}

		for (BrandPojo b : hm.keySet()) {
			InventoryReportData d = new InventoryReportData();
			d.setQuantity(hm.get(b));
			d.setBrand(b.getBrand());
			d.setCategory(b.getCategory());
			list2.add(d);
		}
		l = new ArrayList<InventoryReportData>(list2);
		return list2;
	}

	@ApiOperation(value = "Gets a single orderitem by ID")
	@RequestMapping(path = "/api/inventoryreport/pdf", method = RequestMethod.GET)

	public ResponseEntity<Object> generatePDF(HttpServletResponse response) throws IOException, DocumentException {
		try {
			response.setContentType("application/pdf");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
			String currentDateTime = dateFormatter.format(new Date());

			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
			response.setHeader(headerKey, headerValue);
			List<InventoryReportData> list = l;
			pservice.export(response, list);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Inventory report is empty", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
