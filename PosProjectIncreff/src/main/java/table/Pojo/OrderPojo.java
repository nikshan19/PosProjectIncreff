package table.Pojo;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity

public class OrderPojo {

	 @Id
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 private int id;
	 
	 @Temporal(TemporalType.TIMESTAMP)
	 private Date dateTime;
	 
	 @PrePersist
	 public void onCreate() {
		 
		 dateTime = new Date();
	 }
	 private int toggle;
	 private String dT;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDateTime() {
		return dateTime;
	}
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	public int getToggle() {
		return toggle;
	}
	public void setToggle(int toggle) {
		this.toggle = toggle;
	}

	

	public String getdT() {
		return dT;
	}
	public void setdT(String dT) {
		this.dT = dT;
	}
	
	
	

	
	
	
	
	

}
