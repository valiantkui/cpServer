package controller.elicitation;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.DirectionDao;
import entity.Direction;

@Controller
@RequestMapping("/direction")
public class DirectionController {

	@Resource(name="directionDao")
	DirectionDao directionDao;
	
	
	
	@RequestMapping(value="/findDirectionByS_no",produces="application/json;charset=utf-8")
	@ResponseBody
	public List<Direction> findDirectionByS_no(@RequestParam("s_no") String s_no){
		List<Direction> directionList = directionDao.findDirectionByS_no(s_no);
		
		return directionList;
	}
}
