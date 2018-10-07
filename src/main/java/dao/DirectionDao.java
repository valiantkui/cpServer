package dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import entity.Direction;

@Repository("directionDao")
public interface DirectionDao {
	
	public List<Direction> findMasterDirections();
	public List<Direction> findJobDirections();
	
	public Direction findMasterDirectionByD_no(String d_no);
	public Direction findJobDirectionByD_no(String d_no);
	public Direction findDirectionByD_no(String d_no);
	
	public List<Direction> findDirectionByS_no(String s_no);
	
	/**
	 * ���ݹؼ��ֽ���ģ����ѯ
	 * @param searchContent
	 * @return
	 */
	public List<Direction> searchDirection(String searchContent);
	
}
