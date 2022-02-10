package table.Dto;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.BrandDao;
import table.Model.BrandData;
import table.Model.BrandForm;
import table.Pojo.BrandPojo;
import table.Service.ApiException;
import table.Service.BrandService;

@Service
public class BrandDto {

	@Autowired
	private BrandDao dao;
	@Autowired
	private BrandService service;

	public void add(BrandForm form) throws ApiException {

		BrandPojo p = convert(form);
		normalize(p);
		service.add(p);
	}

	public void upload(BrandForm form) throws ApiException {

		BrandPojo p = convertUpload(form);
		normalize(p);
		service.add(p);
	}

	public void delete(int id) throws ApiException {
		getCheck(id);
		service.delete(id);

	}

	public void update(int id, BrandForm form) throws ApiException {

		BrandPojo p = convert(form);
		normalize(p);
		service.update(id, p);

	}

	public BrandData get(int id) throws ApiException {

		BrandPojo p = service.get(id);
		return convert(p);

	}

	public List<BrandData> getAll() {

		List<BrandPojo> list = service.getAll();
		List<BrandData> list2 = new ArrayList<BrandData>();
		for (BrandPojo p : list) {

			list2.add(convert(p));
		}

		return list2;
	}

	public List<BrandData> getAllSorted() {

		List<BrandPojo> list = service.getAllSorted();
		List<BrandData> list2 = new ArrayList<BrandData>();
		for (BrandPojo p : list) {

			list2.add(convert(p));
		}

		return list2;
	}

	public BrandPojo convertUpload(BrandForm form) throws ApiException {
		if (form.getBrand() == "" || form.getBrand() == null) {
			throw new ApiException("Brand is required");
		} else if (form.getCategory() == "" || form.getCategory() == null) {
			throw new ApiException("Category is required");
		} else {

			return convert(form);
		}

	}

	public BrandPojo convert(BrandForm form) {
		BrandPojo p = new BrandPojo();
		p.setBrand(form.getBrand());
		p.setCategory(form.getCategory());

		return p;
	}

	public BrandData convert(BrandPojo p) {
		BrandData data = new BrandData();
		data.setBrand(p.getBrand());
		data.setCategory(p.getCategory());

		data.setId(p.getId());
		return data;
	}

	public void normalize(BrandPojo p) throws ApiException {

		p.setBrand(p.getBrand().toLowerCase().trim());
		p.setCategory(p.getCategory().toLowerCase().trim());

		if (p.getBrand().isBlank() || p.getCategory().isBlank()) {
			throw new ApiException("Please enter valid inputs");
		}

	}

	@Transactional(rollbackOn = ApiException.class)
	public BrandPojo getCheck(int id) throws ApiException {
		BrandPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("Brand with given id doesnot exists, id: " + id);
		}
		return p;
	}

}
