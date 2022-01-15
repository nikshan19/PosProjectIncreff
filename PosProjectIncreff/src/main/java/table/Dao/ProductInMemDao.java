package table.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import table.Pojo.ProductPojo;

// dao : data access objects
@Repository
public class ProductInMemDao {
	
	
	private HashMap<Integer, ProductPojo> rows;
	private int lastid;
	
	@PostConstruct
	public void init() {
		rows = new HashMap<Integer, ProductPojo>();
	}
	
	public void insert(ProductPojo p) {
		lastid++;
		p.setId(lastid);
		rows.put(lastid, p);
	}
	
	public void delete(int id) {
		rows.remove(id);
	}
	
	public ProductPojo select(int id) {
		return rows.get(id);
	}
	
	public List<ProductPojo> selectAll() {
		ArrayList<ProductPojo> list = new ArrayList<ProductPojo>();
		list.addAll(rows.values());
		return list;
	}
	
	public void update(int id, ProductPojo p ) {
		rows.put(id, p);
		
	}


}
