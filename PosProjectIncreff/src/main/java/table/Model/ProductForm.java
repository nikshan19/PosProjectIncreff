package table.Model;

import table.Pojo.BrandPojo;

public class ProductForm {
	
	private String barcode;
	private int brandPojo;
	private String name;
	private double mrp;
	
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public int getBrandPojo() {
		return brandPojo;
	}
	public void setBrandPojo(int brandPojo) {
		this.brandPojo = brandPojo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getMrp() {
		return mrp;
	}
	public void setMrp(double mrp) {
		this.mrp = mrp;
	}
	
	
 
}
