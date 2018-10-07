package cpServer;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import dao.ProfessionDao;
import entity.Profession;
import homework01.Family;


public class TestCase {
	
	private ApplicationContext ac;
	ProfessionDao dao;
	@Before
	public void init() {
		ac = new ClassPathXmlApplicationContext("spring_mybatis.xml");
		dao = ac.getBean("professionDao",ProfessionDao.class);
		
	}
	
	@Test
	public void test1() {

		
		List<Profession> list = dao.findAll();
		System.out.println(list);
		
		Family fa = new Family();
		
		
	}
}
