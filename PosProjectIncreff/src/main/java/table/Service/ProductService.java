package table.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.ProductDao;
import table.Dto.ProductDto;
import table.Model.ProductForm;
import table.Pojo.BrandPojo;
import table.Pojo.ProductPojo;

@Service
public class ProductService {

	@Autowired
	private ProductDao dao;
	@Autowired
	private ProductDto dto;

	@Transactional
	public void add(ProductPojo p, ProductForm form) throws ApiException {

		dao.insert(p, form);
	}

	@Transactional(rollbackOn = ApiException.class)
	public void delete(int id) throws ApiException {

		dao.delete(id);

	}

	@Transactional(rollbackOn = ApiException.class)
	public ProductPojo get(int id) throws ApiException {
		ProductPojo p = dto.getCheck(id);
		return p;
	}

	@Transactional
	public List<ProductPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackOn = ApiException.class)
	public void update(int id, ProductPojo newPojo, ProductForm form) throws ApiException {

		ProductPojo ex = dto.getCheck(id);
		dao.update(ex, form);

		ex.setName(newPojo.getName());
		ex.setMrp(newPojo.getMrp());
		ex.setBrandPojo(newPojo.getBrandPojo());

	}

	@Transactional(rollbackOn = ApiException.class)
	public BrandPojo getBP(int brandPojo) throws ApiException {
		return dao.selectBP(brandPojo);
	}

}
