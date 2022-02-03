package table.Service;

import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.SalesReportDao;
import table.Dto.SalesReportDto;
import table.Model.SalesReportForm;

@Service
public class SalesReportService {
	
	@Autowired
	private SalesReportDao dao;
	@Autowired
	private SalesReportDto dto;
	
	
	
	@Transactional
	public HashMap<List<String>, HashMap<Integer, Double>> getAll(SalesReportForm form) throws ApiException {
		if((form.getStartdate().length()==0)&&(form.getEnddate().length()==0)&&(form.getBrand().length()==0)&&(form.getCategory().length()==0)){
			return dao.select_everyOne(form);
		}

		if((form.getStartdate().length()>0)&&(form.getEnddate().length()>0)&&(form.getBrand().length()==0)&&(form.getCategory().length()==0)) {
			return dao.brandCategoryNull(form);
		}
		if((form.getStartdate().length()>0)&&(form.getEnddate().length()>0)&&(form.getBrand().length()>0)&&(form.getCategory().length()==0)) {
			dto.normalize2(form);
			return dao.catNull(form);
		}
		if((form.getStartdate().length()>0)&&(form.getEnddate().length()>0)&&(form.getBrand().length()==0)&&(form.getCategory().length()>0)) {
			dto.normalize1(form);
			return dao.brandNull(form);
		}
		if((form.getStartdate().length()>0)&&(form.getEnddate().length()>0)&&(form.getBrand().length()>0)&&(form.getCategory().length()>0)) {
		dto.normalize(form);
		return dao.selectAll(form);
		}
		else {
			throw new ApiException("This action is not supported");
		}
	}
	
	

}
