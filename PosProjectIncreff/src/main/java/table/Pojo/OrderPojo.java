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
	 @TableGenerator(name = "id_generator", table = "id_gen", pkColumnName = "gen_name", valueColumnName = "gen_value",
		        pkColumnValue="task_gen", initialValue=10000, allocationSize=10)
	 @Id
	 @GeneratedValue(strategy = GenerationType.TABLE, generator = "id_generator")
	 private int id;
	 
	 @Temporal(TemporalType.TIMESTAMP)
	 private Date dateTime;
	 
	 @PrePersist
	 public void onCreate() {
		 dateTime = new Date();
	 }
	 
	
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

	
	
	
	

	
	
	
	
	

}
