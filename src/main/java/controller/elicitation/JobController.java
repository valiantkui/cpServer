package controller.elicitation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.DirectionDao;
import entity.Direction;

@Controller
@RequestMapping("/job")
public class JobController {

	@Resource(name="directionDao")
	DirectionDao directionDao;
	
	@RequestMapping("/findJobDirections")
	@ResponseBody
	public List<Direction> getJobDirections(){
		List<Direction> directionList = new ArrayList<Direction>();
		
		directionList =	directionDao.findJobDirections();
		
		for(Direction d: directionList)
		{
			System.out.println(d.getUpdate_date());
		}
		return directionList;
	}
}
