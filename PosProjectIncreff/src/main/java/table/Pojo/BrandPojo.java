package table.Pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity

public class BrandPojo implements Comparable<BrandPojo> {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String brand;
	
	@Column(nullable = false)
	private String category;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public int compareTo(BrandPojo o) {
		String a = this.getBrand()+this.getCategory();
		String b = o.getBrand()+o.getCategory();
		return a.compareTo(b);
	}

	
	
	
	

}
