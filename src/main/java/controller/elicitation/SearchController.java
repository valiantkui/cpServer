package controller.elicitation;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.ArticleDao;
import dao.DirectionDao;
import dao.ResourceDao;
import dao.SubjectDao;
import entity.Article;
import entity.Direction;
import entity.Subject;

@Controller
@RequestMapping("/search")
public class SearchController {
	
	@Resource(name="subjectDao")
	private SubjectDao subjectDao;
	
	@Resource(name="articleDao")
	private ArticleDao articleDao;
	
	@Resource(name="directionDao")
	private DirectionDao directionDao;

	@Resource(name="resourceDao")
	private ResourceDao resourceDao;
	
	@RequestMapping(value="/searchSubject",produces="application/json;charset=utf-8")
	@ResponseBody	
	public List<Subject> searchSubject(@RequestParam("searchContent") String searchContent){
		List<Subject> subjectList = subjectDao.searchSubject(searchContent);
		return subjectList;
	}
	
	
	
	@RequestMapping(value="/searchArticle",produces="application/json;charset=utf-8")
	@ResponseBody
	public List<Article> searchArticle(@RequestParam("searchContent") String searchContent){
		List<Article> articleList = articleDao.searchArticle(searchContent);
		
		return articleList;
	}
	
	@RequestMapping(value="/searchDirection",produces="application/json;charset=utf-8")
	@ResponseBody
	public List<Direction> searchDirection(@RequestParam("searchContent") String searchContent){
		List<Direction> directionList = directionDao.searchDirection(searchContent);
		return directionList;
	}
	
	@RequestMapping(value="/searchResource",produces="application/json;charset=utf-8")
	@ResponseBody
	public List<entity.Resource> searchResource(@RequestParam("searchContent") String searchContent){
		List<entity.Resource> resourceList = resourceDao.searchResource(searchContent);
		return resourceList;
	}
}
