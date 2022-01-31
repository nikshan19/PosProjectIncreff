package table.Dto;

import org.springframework.stereotype.Service;

import table.Model.SalesReportForm;

@Service
public class SalesReportDto {

	// traanactional can only be used on public methods
	public void normalize(SalesReportForm form) {

		form.setStartdate(form.getStartdate().toLowerCase().trim());
		form.setEnddate(form.getEnddate().toLowerCase().trim());
		form.setBrand(form.getBrand().toLowerCase().trim());
		form.setCategory(form.getCategory().toLowerCase().trim());
	}

	public void normalize1(SalesReportForm form) {

		form.setStartdate(form.getStartdate().toLowerCase().trim());
		form.setEnddate(form.getEnddate().toLowerCase().trim());
		form.setCategory(form.getCategory().toLowerCase().trim());
	}

	public void normalize2(SalesReportForm form) {

		form.setStartdate(form.getStartdate().toLowerCase().trim());
		form.setEnddate(form.getEnddate().toLowerCase().trim());
		form.setBrand(form.getBrand().toLowerCase().trim());

	}

}
