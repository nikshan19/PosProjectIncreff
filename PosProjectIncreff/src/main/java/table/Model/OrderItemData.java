package table.Model;

import java.time.LocalDateTime;

public class OrderItemData extends OrderItemForm implements Comparable<OrderItemData> {
	
	private int id;
	private int orderId;
	private int productId;
	private String barcode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public int compareTo(OrderItemData o) {
		return this.getBarcode().compareTo(o.getBarcode());
	}

}
