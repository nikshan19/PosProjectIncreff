package table.Model;

public class SalesReportData extends BrandForm implements Comparable<SalesReportData> {
	
	
	private String category;
	private int quantity;
	private double revenue;
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
	public double getRevenue() {
		return revenue;
	}
	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}
	public int compareTo(SalesReportData o) {
		String a = this.getBrand()+this.getCategory();
		String b = o.getBrand()+o.getCategory();
		return a.compareTo(b);
		
	}
	

}
