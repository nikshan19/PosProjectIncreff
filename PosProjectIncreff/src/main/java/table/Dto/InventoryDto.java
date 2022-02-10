package table.Dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.InventoryDao;
import table.Model.InventoryData;
import table.Model.InventoryForm;
import table.Model.InventoryFormUpload;
import table.Pojo.InventoryPojo;
import table.Service.ApiException;
import table.Service.InventoryService;

@Service
public class InventoryDto {

	@Autowired
	private InventoryDao dao;
	@Autowired
	private InventoryService service;

	public void add(InventoryForm form) throws ApiException {

		InventoryPojo p = convert(form);
		service.add(p, form);
	}

	public void upload(InventoryFormUpload form) throws ApiException {

		HashMap<InventoryPojo, InventoryForm> hm = convertUpload(form);
		InventoryPojo p = new ArrayList<InventoryPojo>(hm.keySet()).get(0);
		service.add(p, hm.get(p));
	}

	public void delete(int id) throws ApiException {
		service.delete(id);

	}

	public void update(String barcode, InventoryForm form) throws ApiException {

		InventoryPojo p = new InventoryPojo();
		p.setQuantity(form.getQuantity());
		p.setId(form.getId());
		service.update(barcode, p, form);

	}

	public InventoryData get(int id) throws ApiException {

		InventoryData p = service.get(id);
		return p;

	}

	public List<InventoryData> getAll() {

		List<InventoryData> list2 = service.getAll();

		return list2;
	}

	public InventoryPojo convert(InventoryForm form) {
		InventoryPojo p = new InventoryPojo();
		p.setQuantity(form.getQuantity());

		return p;
	}

	public HashMap<InventoryPojo, InventoryForm> convertUpload(InventoryFormUpload form) throws ApiException {

		int quantity;

		if (form.getBarcode() == "" || form.getBarcode() == null) {
			throw new ApiException("Barcode is required");
		} else if (form.getQuantity() == "" || form.getQuantity() == null) {
			throw new ApiException("Quantity is required");
		}
		try {
			quantity = Integer.valueOf(form.getQuantity());
		} catch (Exception e) {
			quantity = 0;
		}
		if (quantity <= 0) {
			throw new ApiException("Quantity added is not accepted");
		}

		InventoryForm form1 = new InventoryForm();
		form1.setBarcode(form.getBarcode());
		form1.setQuantity(quantity);

		InventoryPojo p = convert(form1);
		HashMap<InventoryPojo, InventoryForm> hm = new HashMap<InventoryPojo, InventoryForm>();
		hm.put(p, form1);
		return hm;
	}

	public InventoryData convert(InventoryPojo p) {
		InventoryData data = new InventoryData();
		data.setQuantity(p.getQuantity());
		data.setId(p.getId());
		return data;
	}

	public void normalize(InventoryForm p) throws ApiException {
		p.setBarcode(p.getBarcode().toLowerCase().trim());
		if (p.getBarcode().isBlank()) {
			throw new ApiException("Please enter valid barcode");
		}
	}

	@Transactional(rollbackOn = ApiException.class)
	public InventoryData getCheck(int id) throws ApiException {
		InventoryData p = dao.select(id);
		if (p == null) {
			throw new ApiException("Inventory with given id doesnot exists, id: " + id);
		}
		return p;
	}
}
