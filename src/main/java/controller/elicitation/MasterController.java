package controller.elicitation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.DirectionDao;
import entity.Direction;
import tools.Global;

@Controller
@RequestMapping("/master")
public class MasterController {

	@Resource(name="directionDao")
	DirectionDao directionDao;
	
	@RequestMapping("/findMasterDirections")
	@ResponseBody
	public List<Direction> getMasterDirections(){
		List<Direction> directionList = new ArrayList<Direction>();
		
		directionList =	directionDao.findMasterDirections();
		
		for(Direction d: directionList)
		{
			System.out.println(d.getUpdate_date());
		}
		return directionList;
	}
	
	
	@RequestMapping("/downLoadDirecitonByD_no")
	public void downLoadDirecitonByD_no(HttpServletRequest request,HttpServletResponse response) {
		
		FileInputStream fis = null;
		OutputStream out = null;
		try {
			request.setCharacterEncoding("utf-8");
			String d_no = request.getParameter("d_no");
			Direction direction = directionDao.findDirectionByD_no(d_no);
			
			String filePath = direction.getIntro_link();
			
			String fileName = filePath.substring(filePath.lastIndexOf("//")+1);
			
			File file = new File(filePath);
			
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachement;filename="
					+URLEncoder.encode(fileName,"utf-8"));
			
			if(!file.exists()) {
				
				response.getWriter().println("找不到资源");
			}
			
			
			fis = new FileInputStream(filePath);
			out =  response.getOutputStream();
			
			int len = -1;
			byte[] bytes = new byte[1024*10];
			while((len = fis.read(bytes)) != -1) {
				
				out.write(bytes,0,len);
			}
			out.flush();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
				try {
					if(out != null) {
						out.close();
					}	
					if(fis != null) {		
						fis.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		
	}
	
}
