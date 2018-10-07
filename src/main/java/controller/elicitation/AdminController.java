package controller.elicitation;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.AdminDao;
import entity.Admin;

@Controller	
@RequestMapping("/admin")
public class AdminController {
	
	@Resource(name="adminDao")
	private AdminDao adminDao;

	
	@RequestMapping("/createImgCode")
	public void createImgCode(HttpServletRequest request,HttpServletResponse response) {
		
		System.out.println("checkcode...");
		/*
		 * 一.绘图
		 */
		//step1,创建内存映像对象(画布)
		BufferedImage image = 
			new BufferedImage(80,30,
					BufferedImage.TYPE_INT_RGB);
		//step2,获得画笔
		Graphics g = image.getGraphics();
		//step3,给笔设置颜色
		g.setColor(new Color(255,255,255));
		//step4,设置背景颜色
		g.fillRect(0, 0, 80, 30);
		//step5,设置前景颜色
		Random r = new Random();
		g.setColor(new Color(
				r.nextInt(255),r.nextInt(255)
				,r.nextInt(255)));
		//step6,绘图
		//设置字体(字体,风格,大小)
		g.setFont(new Font(null,Font.ITALIC,24));
		String number = r.nextInt(10000)
		+ "";
		
		
		//将随机验证码存到session中；验证码必须存在session中，这样更安全
		HttpSession  session=request.getSession();
		session.setAttribute("imgCode", number);
		
		g.drawString(number, 5, 26);
		//step7,加一些干扰线
		for(int i = 0;i < 6; i++){
			g.drawLine(
					r.nextInt(80), r.nextInt(30),
					r.nextInt(80), r.nextInt(30));
		}
		/*
		 * 二.将图片压缩并发送给浏览器
		 */
		response.setContentType("image/jpeg");
		OutputStream ops = null;
		try {
			ops = response.getOutputStream();
			javax.imageio.ImageIO.write(image,"jpeg",ops);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//write方法:会将原始图片(image)按照
		//指定的算法("jpeg")压缩，然后输出。
		try {
			if(ops != null) ops.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@RequestMapping(value="/checkAdminLogin",produces="application/json;charset=utf-8")
	@ResponseBody()
	public String checkAdminLogin(HttpServletRequest request) {
		
		System.out.println("checkAdminLogin()");
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String imgCode = request.getParameter("imgCode");
		
		
		if(imgCode == null) {
			return "请输入验证码";
		}
		
		HttpSession session = request.getSession();
		String imgCode2 =(String) session.getAttribute("imgCode");
		if(! imgCode.equals(imgCode2)) {
			return "验证码错误";
		}
		
		Admin admin = adminDao.findAdminByA_id(id);
		if(admin == null) {
			return "账号不存在";
		}
		
		if(!admin.getPassword().equals(password)) {
			return "密码错误";
		}
		
		
		
		return "1";//代表可以登录
		
	}
	
	/**
	 * 登陆成功！将登陆人信息存到session中

	 * 然后重定向到 主页
	 * @param id
	 * @param password
	 */
	@RequestMapping("/adminLogin")
	public String adminLogin(HttpServletRequest request) {
		String a_id = request.getParameter("id");
		String password = request.getParameter("password");
		Admin admin = adminDao.findAdminByA_id(a_id);
		
		HttpSession session = request.getSession();
		session.setAttribute("a_id", a_id);
		session.setAttribute("a_name", admin.getName());
		
		//重定向到主页
		
		return "redirect:/admin/main.jsp";
		
	}
}
