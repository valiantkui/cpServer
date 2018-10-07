package controller.elicitation;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dao.UserDao;
import entity.User;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource(name="userDao")
	private UserDao userDao;
	
	@RequestMapping(value="/checkId",produces="application/json;charset=utf-8")
	@ResponseBody
	public String checkId(@RequestParam("id") String id) {
		
		
		User user = userDao.findUserByU_id(id);
		if(user == null) {
			return "ok";
		}
		
		
		//if("admin".equals(id))
			
		
		return "false";
	}
	
	
	@RequestMapping(value="/registerUser",produces="application/json;charset=utf-8")
	@ResponseBody
	public String registerUser(@RequestParam("userGson") String userGson) {
		System.out.println(userGson);
		Gson gson = new Gson();
		
		User user = gson.fromJson(userGson, new TypeToken<User>() {
        }.getType());
		try {
			userDao.addUser(user);
		}catch(Exception e) {
			return "false";
		}
		return "ok";
	}
	
	@RequestMapping(value="/loginUser",produces="application/json;charset=utf-8")
	@ResponseBody
	public User loginUser(@RequestParam("id") String id, 
			@RequestParam("password")  String password) {
		User user = userDao.findUserByU_id(id);
		
		return user;
	}
}
