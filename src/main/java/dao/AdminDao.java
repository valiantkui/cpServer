package dao;

import org.springframework.stereotype.Repository;

import entity.Admin;

@Repository("adminDao")
public interface AdminDao {
	
	public Admin findAdminByA_id(String a_id);
	
	

}
