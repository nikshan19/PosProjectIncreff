package table.Dto;

import org.springframework.stereotype.Service;

import table.Model.SalesReportForm;
import table.Service.ApiException;

@Service
public class SalesReportDto {

	public void normalize(SalesReportForm form) throws ApiException {

		form.setStartdate(form.getStartdate().toLowerCase().trim());
		form.setEnddate(form.getEnddate().toLowerCase().trim());
		form.setBrand(form.getBrand().toLowerCase().trim());
		form.setCategory(form.getCategory().toLowerCase().trim());
		if (form.getBrand().isBlank() || form.getCategory().isBlank()) {
			throw new ApiException("Please enter valid inputs");
		}

	}

	public void normalize1(SalesReportForm form) throws ApiException {

		form.setStartdate(form.getStartdate().toLowerCase().trim());
		form.setEnddate(form.getEnddate().toLowerCase().trim());
		form.setCategory(form.getCategory().toLowerCase().trim());
		if (form.getCategory().isBlank()) {
			throw new ApiException("Please enter valid category");
		}
	}

	public void normalize2(SalesReportForm form) throws ApiException {

		form.setStartdate(form.getStartdate().toLowerCase().trim());
		form.setEnddate(form.getEnddate().toLowerCase().trim());
		form.setBrand(form.getBrand().toLowerCase().trim());
		if (form.getBrand().isBlank()) {
			throw new ApiException("Please enter valid brand");
		}

	}

}
