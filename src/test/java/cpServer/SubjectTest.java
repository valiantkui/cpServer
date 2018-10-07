package cpServer;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.SubjectDao;
import entity.Subject;


/**
 * ��ȡѧ����Ϣ��������ѧ����Ϣ֮�����ϵ
 * @author KUIKUI
 *
 */

@Controller
public class SubjectTest {

	@Resource(name="subjectDao")
	SubjectDao subjectDao;
	
	@RequestMapping("/run")
	public void run() {
		System.out.println(subjectDao.findAll());
	}
	
	
	public static void main(String[] args) {
		
		SubjectTest test = new SubjectTest();
		test.run();
		
	}
}
class TreeNode{
	Subject subject;
	List<TreeNode> preNodeList;                                                                	
}