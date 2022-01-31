package table.Service;

import java.util.HashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.InventoryReportDao;
import table.Pojo.BrandPojo;

@Service
public class InventoryReportService {

	@Autowired
	private InventoryReportDao dao;

	@Transactional
	public HashMap<BrandPojo, Integer> getAll() {
		return dao.selectAll();
	}

}
