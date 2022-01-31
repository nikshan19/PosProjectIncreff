package table.Model;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import table.Pojo.ProductPojo;

public class InventoryForm {
	
	
	private int quantity;
	private int id;
	private String barcode;
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBarcode() {
		return barcode;
	}

	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	
	
	 
	
 
}
