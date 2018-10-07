package controller.elicitation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.ResourceDao;
import entity.Direction;
import entity.Resource;

@Controller
@RequestMapping("/resource")
public class ResourceController {
	
	@javax.annotation.Resource(name="resourceDao")
	private ResourceDao resourceDao;
	

	
	@RequestMapping("/findResourceByType")
	@ResponseBody
	public List<Resource> getResourcesByType(HttpServletRequest request){
		String type = request.getParameter("type");
		List<Resource> resourceList = resourceDao.findResourceByType(type);
		
		return resourceList;
	}
	
	
	@RequestMapping("/downLoadResourceByR_no")
	public void downLoadResourceByR_no(HttpServletRequest request,HttpServletResponse response) {
		
		FileInputStream fis = null;
		OutputStream out = null;
		try {
			request.setCharacterEncoding("utf-8");
			String r_no = request.getParameter("r_no");
			Resource resource = resourceDao.findResourceByR_no(Integer.parseInt(r_no));
			
			String filePath = resource.getPath();
			
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
