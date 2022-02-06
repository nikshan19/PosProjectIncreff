package table.Controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

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

import com.lowagie.text.DocumentException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import table.Dto.BrandDto;
import table.Model.BrandData;
import table.Model.BrandForm;
import table.Model.ErrorData;
import table.Pojo.BrandPojo;
import table.Pojo.OrderItemPojo;
import table.Service.ApiException;
import table.Service.BrandService;
import table.Service.PDFGeneratorService2;

@Api
@RestController
public class BrandController {

	@Autowired
	private BrandService service;
	@Autowired
	private PDFGeneratorService2 pservice;
	@Autowired
	private BrandDto dto;

	private List<BrandData> l;

	@ApiOperation(value = "Adds an employee")
	@RequestMapping(path = "/api/brand", method = RequestMethod.POST)
	public ResponseEntity<Object> add(@RequestBody BrandForm form) {
		try {
			dto.add(form);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Brand-Category Combination cannot be repeated",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Adds an employee")
	@RequestMapping(path = "/api/upload/brand", method = RequestMethod.POST)
	public ResponseEntity<Object> upload(@RequestBody BrandForm form) {
		try {
			dto.upload(form);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Deletes an employee")
	@RequestMapping(path = "/api/brand/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> delete(@PathVariable int id) throws ApiException {
		try {
			dto.delete(id);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Updates an employee")
	@RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@PathVariable int id, @RequestBody BrandForm form) throws ApiException {
		try {
			dto.update(id, form);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (ApiException e) {
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@ApiOperation(value = "Gets a single employee by ID")
	@RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
	public BrandData get(@PathVariable int id) throws ApiException {
		return dto.get(id);
	}

	@ApiOperation(value = "Gets list of all employees")
	@RequestMapping(path = "/api/brand", method = RequestMethod.GET)
	public List<BrandData> getAll() {
		l = new ArrayList<BrandData>(dto.getAll());
		return dto.getAll();
	}
	@ApiOperation(value = "Gets list of all employees")
	@RequestMapping(path = "/api/brandreport", method = RequestMethod.GET)
	public List<BrandData> getAllSorted() {
		l = new ArrayList<BrandData>(dto.getAllSorted());
		return dto.getAllSorted();
	}
	

	@ApiOperation(value = "Gets a single orderitem by ID")
	@RequestMapping(path = "/api/brandreport/pdf", method = RequestMethod.GET)

	public ResponseEntity<Object> generatePDF(HttpServletResponse response) throws IOException, DocumentException {
		try {
			response.setContentType("application/pdf");
			DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
			String currentDateTime = dateFormatter.format(new Date());

			String headerKey = "Content-Disposition";
			String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
			response.setHeader(headerKey, headerValue);
			List<BrandData> list = l;
			pservice.export(response, list);

			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>("Brand Report Empty", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
