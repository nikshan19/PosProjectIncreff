package table.Service;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.SalesReportDao;
import table.Model.SalesReportForm;

@Service
public class SalesReportService {
	
	@Autowired
	private SalesReportDao dao;
	
	
	
	
	@Transactional
	public HashMap<Integer, Double> getAll(SalesReportForm form) {
		normalize(form);
		return dao.selectAll(form);
	}
	
	// traanactional can only be used on public methods
	private static void normalize(SalesReportForm form) {
		
		
		form.setStartdate(form.getStartdate().toLowerCase().trim());
		form.setEnddate(form.getEnddate().toLowerCase().trim());
		form.setBrand(form.getBrand().toLowerCase().trim());
		form.setCategory(form.getCategory().toLowerCase().trim());
	}
	

}
