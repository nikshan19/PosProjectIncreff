package table.Model;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;

import table.Pojo.ProductPojo;

public class InventoryFormUpload {
	
	private String barcode;
	private String quantity;
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	
	
	
	 
	
 
}
