package entity;

public class Admin {
	private String a_id;
	private String name;
	private String password;
	public String getA_id() {
		return a_id;
	}
	public void setA_id(String a_id) {
		this.a_id = a_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Admin [a_id=" + a_id + ", name=" + name + ", password=" + password + "]";
	}
	
	

}
