package table.Model;

import java.time.LocalDateTime;

public class OrderData extends OrderForm {
	
	private int id;
	private String dateTime;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
}
