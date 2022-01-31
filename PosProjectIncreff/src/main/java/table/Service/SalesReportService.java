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
		if((form.getStartdate()==""||form.getStartdate()==null)&&(form.getEnddate()==""||form.getEnddate()==null)&&(form.getBrand()==""||form.getBrand()==null)&&(form.getCategory()==""||form.getCategory()==null)){
			return dao.select_everyOne(form);
		}

		else if((form.getStartdate()!=""||form.getStartdate()!=null)&&(form.getEnddate()!=""||form.getEnddate()!=null)&&(form.getBrand()==""||form.getBrand()==null)&&(form.getCategory()==""||form.getCategory()==null)) {
			return dao.brandCategoryNull(form);
		}
		else if((form.getStartdate()!=""||form.getStartdate()!=null)&&(form.getEnddate()!=""||form.getEnddate()!=null)&&(form.getBrand()!=""||form.getBrand()!=null)&&(form.getCategory()==""||form.getCategory()==null)) {
			dto.normalize2(form);
			return dao.catNull(form);
		}
		else if((form.getStartdate()!=""||form.getStartdate()!=null)&&(form.getEnddate()!=""||form.getEnddate()!=null)&&(form.getBrand()==""||form.getBrand()==null)&&(form.getCategory()!=""||form.getCategory()!=null)) {
			dto.normalize1(form);
			return dao.brandNull(form);
		}
		else if((form.getStartdate()!=""||form.getStartdate()!=null)&&(form.getEnddate()!=""||form.getEnddate()!=null)&&(form.getBrand()!=""||form.getBrand()!=null)&&(form.getCategory()!=""||form.getCategory()!=null)) {
		dto.normalize(form);
		return dao.selectAll(form);
		}
		else {
			throw new ApiException("This action is not supported");
		}
	}
	
	

}
