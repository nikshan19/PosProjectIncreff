package table.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import table.Pojo.OrderPojo;

// dao : data access objects
@Repository
public class OrderInMemDao {
	
	
	private HashMap<Integer, OrderPojo> rows;
	private int lastid=1000;
	
	@PostConstruct
	public void init() {
		rows = new HashMap<Integer, OrderPojo>();
	}
	
	public void insert(OrderPojo p) {
		lastid++;
		p.setId(lastid);
		rows.put(lastid, p);
	}
	
	public void delete(int id) {
		rows.remove(id);
	}
	
	public OrderPojo select(int id) {
		return rows.get(id);
	}
	
	public List<OrderPojo> selectAll() {
		ArrayList<OrderPojo> list = new ArrayList<OrderPojo>();
		list.addAll(rows.values());
		return list;
	}
	
	public void update(int id, OrderPojo p ) {
		rows.put(id, p);
		
	}


}
