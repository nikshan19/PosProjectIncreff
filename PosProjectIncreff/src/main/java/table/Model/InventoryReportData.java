package table.Model;

public class InventoryReportData extends InventoryForm implements Comparable<InventoryReportData> {
	
	private String brand;
	private String category;
	private int quantity;
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int compareTo(InventoryReportData o) {
		String a = this.getBrand()+this.getCategory();
		String b = o.getBrand()+o.getCategory();
		return a.compareTo(b);
	}
	

}
