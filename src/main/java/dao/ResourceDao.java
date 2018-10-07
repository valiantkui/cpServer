package dao;

import java.util.List;


import org.springframework.stereotype.Repository;

import entity.Resource;

@Repository("resourceDao")
public interface ResourceDao {

	public List<Resource> findResourceByType(String type);
	public void addResource(entity.Resource resource);
	
	public entity.Resource findResourceByR_no(int r_no);
	
	public void updateResource(entity.Resource resource);
	
	public void deleteResourceByR_no(int r_no);
	
	public List<Resource> searchResource(String searchContent);
}
