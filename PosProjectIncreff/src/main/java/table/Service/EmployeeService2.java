package table.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import table.Dao.BrandDao;
import table.Pojo.BrandPojo;

public class EmployeeService2 {
	
	@Autowired
	private BrandDao dao;
	
	public void addList(List<BrandPojo> list) {
		for(BrandPojo p:list) {
			dao.insert(p);
		}
	}
	

}
