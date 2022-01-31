package table.Controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import table.Model.OrderItemData;
import table.Pojo.OrderItemPojo;
import table.Service.PDFGeneratorService;

@Api
@RestController
public class PDFExportController {
	
	@Autowired 
	private PDFGeneratorService pservice;
	
	@ApiOperation(value="Gets a single orderitem by ID")
	@RequestMapping(path="/api/pdf", method=RequestMethod.GET)
	
		public void generatePDF(HttpServletResponse response) throws IOException, DocumentException {
	        response.setContentType("application/pdf");
	        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
	        String currentDateTime = dateFormatter.format(new Date());

	        String headerKey = "Content-Disposition";
	        String headerValue = "attachment; filename=pdf_" + currentDateTime + ".pdf";
	        response.setHeader(headerKey, headerValue);
	        List<OrderItemPojo> list = getList(); 
	        pservice.export(response,list);
	        System.out.println("size: "+list.size());
	    }
	
	public List<OrderItemPojo> getList(){
		return pservice.getList();
		
	}

}
