package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import entity.Profession;

@Controller
public class TestController {
	
	@RequestMapping("test1")
	@ResponseBody
	public String test1() {
		
		return "hello";
	}
	
	
	@RequestMapping("test2")
	@ResponseBody
	public Profession test2() {
		
		Profession p = new Profession();
		p.setName("ww");
		p.setP_no("22222");
		
		return p;
	}

}
