package table.Pojo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;


@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"barcode"})})
public class ProductPojo {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String barcode;
	
	
//    @JsonBackReference
//
//    @JoinColumn(name = "brand_category", referencedColumnName = "id")
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private int brandPojo;
	private String name;
	private double mrp;
	
	
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
	public int getBrandPojo() {
		return brandPojo;
	}
	public void setBrandPojo(int brandPojo) {
		this.brandPojo = brandPojo;
	}
	

	
	

}
