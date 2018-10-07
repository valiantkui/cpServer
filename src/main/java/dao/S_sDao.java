package dao;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import entity.S_s;
import entity.Subject;


@Repository("s_sDao")
public interface S_sDao {
	public List<S_s> findAll();
	
	public List<Subject> findPreSubjectByNextSubject(String nextSubject);
	

}
