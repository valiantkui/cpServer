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
		 * һ.��ͼ
		 */
		//step1,�����ڴ�ӳ�����(����)
		BufferedImage image = 
			new BufferedImage(80,30,
					BufferedImage.TYPE_INT_RGB);
		//step2,��û���
		Graphics g = image.getGraphics();
		//step3,����������ɫ
		g.setColor(new Color(255,255,255));
		//step4,���ñ�����ɫ
		g.fillRect(0, 0, 80, 30);
		//step5,����ǰ����ɫ
		Random r = new Random();
		g.setColor(new Color(
				r.nextInt(255),r.nextInt(255)
				,r.nextInt(255)));
		//step6,��ͼ
		//��������(����,���,��С)
		g.setFont(new Font(null,Font.ITALIC,24));
		String number = r.nextInt(10000)
		+ "";
		
		
		//�������֤��浽session�У���֤��������session�У���������ȫ
		HttpSession  session=request.getSession();
		session.setAttribute("imgCode", number);
		
		g.drawString(number, 5, 26);
		//step7,��һЩ������
		for(int i = 0;i < 6; i++){
			g.drawLine(
					r.nextInt(80), r.nextInt(30),
					r.nextInt(80), r.nextInt(30));
		}
		/*
		 * ��.��ͼƬѹ�������͸������
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
		//write����:�ὫԭʼͼƬ(image)����
		//ָ�����㷨("jpeg")ѹ����Ȼ�������
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
			return "��������֤��";
		}
		
		HttpSession session = request.getSession();
		String imgCode2 =(String) session.getAttribute("imgCode");
		if(! imgCode.equals(imgCode2)) {
			return "��֤�����";
		}
		
		Admin admin = adminDao.findAdminByA_id(id);
		if(admin == null) {
			return "�˺Ų�����";
		}
		
		if(!admin.getPassword().equals(password)) {
			return "�������";
		}
		
		
		
		return "1";//������Ե�¼
		
	}
	
	/**
	 * ��½�ɹ�������½����Ϣ�浽session��

	 * Ȼ���ض��� ��ҳ
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
		
		//�ض�����ҳ
		
		return "redirect:/admin/main.jsp";
		
	}
}
