package dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import entity.Profession;


@Repository("professionDao")
public interface ProfessionDao {
	
	/**
	 * ��profession���в�ѯ���е�רҵ
	 * @return
	 */
	public List<Profession> findAll();

}
