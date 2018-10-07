package dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import entity.Profession;


@Repository("professionDao")
public interface ProfessionDao {
	
	/**
	 * 从profession表中查询所有的专业
	 * @return
	 */
	public List<Profession> findAll();

}
