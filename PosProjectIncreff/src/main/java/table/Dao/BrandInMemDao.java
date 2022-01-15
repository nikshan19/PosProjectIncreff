package table.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import table.Pojo.BrandPojo;

// dao : data access objects
@Repository
public class BrandInMemDao {
	
	
	private HashMap<Integer, BrandPojo> rows;
	private int lastid;
	
	@PostConstruct
	public void init() {
		rows = new HashMap<Integer, BrandPojo>();
	}
	
	public void insert(BrandPojo p) {
		lastid++;
		p.setId(lastid);
		rows.put(lastid, p);
	}
	
	public void delete(int id) {
		rows.remove(id);
	}
	
	public BrandPojo select(int id) {
		return rows.get(id);
	}
	
	public List<BrandPojo> selectAll() {
		ArrayList<BrandPojo> list = new ArrayList<BrandPojo>();
		list.addAll(rows.values());
		return list;
	}
	
	public void update(int id, BrandPojo p ) {
		rows.put(id, p);
		
	}


}
