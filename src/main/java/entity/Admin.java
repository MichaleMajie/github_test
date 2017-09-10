package entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class Admin implements Serializable {
	private Integer id;
	
	private String password;
	private String name;
	
	public Integer getAdminId() {
		return id;
	}
	public void setAdminId(Integer id) {
		this.id = id;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
