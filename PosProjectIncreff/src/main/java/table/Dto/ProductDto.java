package table.Dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import table.Dao.ProductDao;
import table.Model.ProductData;
import table.Model.ProductForm;
import table.Model.ProductFormUpload;
import table.Pojo.ProductPojo;
import table.Service.ApiException;
import table.Service.ProductService;
@Service
public class ProductDto {
	
	@Autowired
	private ProductDao dao;
	@Autowired
	private ProductService service;
	
	
	public void add(ProductForm form) throws ApiException {
		ProductPojo p = convert(form);
		normalize(p,form);
		service.add(p, form);
	}
	public void upload(ProductFormUpload form) throws ApiException {
		HashMap<ProductPojo,ProductForm> hm = convertUpload(form);
		ProductPojo p = new ArrayList<ProductPojo>(hm.keySet()).get(0);
		ProductForm form1 = hm.get(p);
		normalize(p,form1);
		service.add(p, form1);
	}
	public void delete(int id) throws ApiException {
		getCheck(id);
		service.delete(id);
	}
	public void update(int id, ProductForm form) throws ApiException {
		
		ProductPojo p = convert(form);
		normalize(p, form);
		 service.update(id, p, form);
	}
	public ProductData get(int id) throws ApiException {
		ProductPojo p = service.get(id);
		ProductData d =  convert(p);
		
		return d;
	}
	public List<ProductData> getAll() throws ApiException {
		
		List<ProductPojo> list = service.getAll();
		List<ProductData> list2 = new ArrayList<ProductData>();
		for(ProductPojo p:list) {
			ProductData d = convert(p);
			System.out.println(p.getBrandPojo());
			d.setBrand(service.getBP(p.getBrandPojo()).getBrand());
			d.setCategory(service.getBP(p.getBrandPojo()).getCategory());
			list2.add(d);
		}
		return list2;
	}
	public  ProductPojo convert(ProductForm form) {
		ProductPojo p = new ProductPojo();
		p.setName(form.getName());
		p.setMrp(form.getMrp());
		p.setBrandPojo(form.getBrandCategory());
		return p;
	}
	
	public HashMap<ProductPojo,ProductForm> convertUpload(ProductFormUpload form) throws ApiException {
		double mrp;
		if(form.getBarcode()==""|| form.getBarcode()==null) {
			throw new ApiException("Barcode is required");
		}else if(form.getName()==""|| form.getName()==null) {
			throw new ApiException("Name is required");
		}else if(form.getBrand()==""|| form.getBrand()==null) {
			throw new ApiException("Brand is required");
		}else if(form.getCategory()==""|| form.getCategory()==null) {
			throw new ApiException("Category is required");
		}else if(form.getMrp()==""|| form.getMrp()==null) {
			throw new ApiException("MRP is required");
		}
		
		try {
			mrp = Double.valueOf(form.getMrp());
		}catch(Exception e) {
			mrp=0;
		}
		if(mrp<=0) {
			throw new ApiException("MRP added is not accepted");
		}
		
		ProductForm form1 = new ProductForm();
		form1.setBarcode(form.getBarcode());
		form1.setName(form.getName());
		form1.setBrandCategory(getBP(form.getBrand(), form.getCategory()));
		form1.setMrp(mrp);
		ProductPojo p = convert(form1);
		HashMap<ProductPojo, ProductForm> hm = new HashMap<ProductPojo, ProductForm>();
		hm.put(p, form1);
		return hm;
	}
	
	public ProductData convert(ProductPojo p) {
		ProductData data = new ProductData();
		data.setBarcode(p.getBarcode());
		data.setName(p.getName());
		data.setMrp(p.getMrp());
		data.setId(p.getId());
		data.setBrandCategory(p.getBrandPojo());
		return data;
	}

	
	@Transactional(rollbackOn = ApiException.class)
	public ProductPojo getCheck(int id) throws ApiException {
		ProductPojo p = dao.select(id);
		if(p == null) {
			throw new ApiException("Product with given id doesnot exists, id: "+id);
		}
		return p;
	}
	// traanactional can only be used on public methods
	public void normalize(ProductPojo p, ProductForm form) {
		
		form.setBarcode(form.getBarcode().toLowerCase().trim());

		p.setName(p.getName().toLowerCase().trim());
		
	}
	
	public int getBP(String brand, String category) throws ApiException {
		return dao.getBrandCat(brand, category);
	}
	
	

}
