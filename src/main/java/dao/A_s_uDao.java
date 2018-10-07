package dao;

import org.springframework.stereotype.Repository;

import entity.A_s_u;

@Repository("asuDao")
public interface A_s_uDao {

	public void addAsu(A_s_u asu);
	
	public void deleteA_s_uByA_no(String a_no);
}
