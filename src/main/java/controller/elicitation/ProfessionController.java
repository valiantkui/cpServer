package controller.elicitation;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.context.Theme;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.ProfessionDao;
import entity.Profession;

@Controller
@RequestMapping("profession")
public class ProfessionController {
	
	@Resource(name="professionDao")
	ProfessionDao professionDao;

	
	
	@RequestMapping("findAll")
	@ResponseBody
	public List<Profession> findAll(){
		
		List<Profession> professionList = professionDao.findAll();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return professionList;
	}
	/**
	 * test
	 * ²âÊÔ½á¹ûÎª404
	 * @return
	 */
	@RequestMapping("findAll2")
	public List<Profession> findAll2(){
		List<Profession> professionList = professionDao.findAll();
		return professionList;
	}
}
