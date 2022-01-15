package table.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import table.Pojo.InventoryPojo;

// dao : data access objects
@Repository
public class InventoryInMemDao {
	
	
	private HashMap<Integer, InventoryPojo> rows;
	private int lastid;
	
	@PostConstruct
	public void init() {
		rows = new HashMap<Integer, InventoryPojo>();
	}
	
	public void insert(InventoryPojo p) {
		lastid++;
		p.setId(lastid);
		rows.put(lastid, p);
	}
	
	public void delete(int id) {
		rows.remove(id);
	}
	
	public InventoryPojo select(int id) {
		return rows.get(id);
	}
	
	public List<InventoryPojo> selectAll() {
		ArrayList<InventoryPojo> list = new ArrayList<InventoryPojo>();
		list.addAll(rows.values());
		return list;
	}
	
	public void update(int id, InventoryPojo p ) {
		rows.put(id, p);
		
	}


}
