package table.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.InventoryDao;
import table.Dto.InventoryDto;
import table.Model.InventoryData;
import table.Model.InventoryForm;
import table.Pojo.InventoryPojo;

@Service
public class InventoryService {

	@Autowired
	private InventoryDao dao;
	@Autowired
	private InventoryDto dto;

	@Transactional
	public void add(InventoryPojo p, InventoryForm form) throws ApiException {
		dto.normalize(form);
		dao.insert(p, form);
	}

	@Transactional(rollbackOn = ApiException.class)
	public void delete(int id) throws ApiException {
		dto.getCheck(id);
		dao.delete(id);

	}

	@Transactional(rollbackOn = ApiException.class)
	public InventoryData get(int id) throws ApiException {
		InventoryData p = dao.select(id);
		return p;
	}

	@Transactional
	public List<InventoryData> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackOn = ApiException.class)
	public void update(String barcode, InventoryPojo newPojo, InventoryForm form) throws ApiException {
		dto.normalize(form);
		InventoryPojo ex = dao.update(form);
		ex.setQuantity(newPojo.getQuantity());

	}

}
