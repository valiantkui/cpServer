package dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import entity.Subject;


@Repository("subjectDao")
public interface SubjectDao {
	public List<Subject> findAll();
	
	public List<Subject> findSubjectByP_no(String p_no);
	public List<Subject> searchSubject(String searchContent);
	
	public Subject findSubjectByS_no(String s_no);
	

}
