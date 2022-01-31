package table.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.BrandDao;
import table.Dto.BrandDto;
import table.Pojo.BrandPojo;

@Service
public class BrandService {

	@Autowired
	private BrandDao dao;
	@Autowired
	private BrandDto dto;

	@Transactional
	public void add(BrandPojo p) throws ApiException {

		dao.insert(p);
	}

	@Transactional(rollbackOn = ApiException.class)
	public void delete(int id) throws ApiException {

		dao.delete(id);

	}

	@Transactional(rollbackOn = ApiException.class)
	public BrandPojo get(int id) throws ApiException {
		BrandPojo p = dto.getCheck(id);
		return p;
	}

	@Transactional
	public List<BrandPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackOn = ApiException.class)
	public void update(int id, BrandPojo newPojo) throws ApiException {

		BrandPojo ex = dto.getCheck(id);
		dao.update(newPojo);
		ex.setBrand(newPojo.getBrand());
		ex.setCategory(newPojo.getCategory());

	}

}
