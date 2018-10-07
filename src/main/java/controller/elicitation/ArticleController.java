package controller.elicitation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.junit.runners.Parameterized.Parameter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dao.A_s_uDao;
import dao.ArticleDao;
import entity.A_s_u;
import entity.Article;
import tools.Global;


@Controller
@RequestMapping("/article")
public class ArticleController {

	@Resource(name="articleDao")
	private ArticleDao articleDao;
	
	@Resource(name="asuDao")
	private A_s_uDao asuDao;
	
	
	@RequestMapping("/deleteArticleByA_no")
	@ResponseBody
	public String deleteArticleByA_no(@RequestParam("a_no") String a_no) {
		try {
			System.out.println("删除的a_no为："+a_no);
			articleDao.deleteArticleByA_no(a_no);
			asuDao.deleteA_s_uByA_no(a_no);
		}catch(Exception e) {
			return "false";
		}
		
		return "ok";
	}
	
	
	
	@RequestMapping("/downloadContent")
	public void downloadContent(HttpServletRequest request,HttpServletResponse response) {
		
	   try {
		request.setCharacterEncoding("utf-8");
		String content_link = request.getParameter("content_link");
		//有一个文件：1.zip
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachement;filename="
				+URLEncoder.encode(content_link.substring(content_link.lastIndexOf("//")),"utf-8"));
		
		File file = new File(Global.articlePath+content_link);
		
		FileInputStream fis= new FileInputStream(file);
		
		OutputStream os = response.getOutputStream();
		
		byte[] bytes = new byte[1024*10];
		int i = -1;
		while((i=fis.read(bytes)) != -1) {
			os.write(bytes,0,i);
		}
		os.flush();
		
		
			//OutputStream os = response.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	@RequestMapping(value="/findArticleByS_no",produces="application/json;charset=utf-8")
	@ResponseBody
	public List<Article> findArticleByS_no(@RequestParam("s_no") String s_no){
		List<Article> articleList=articleDao.findArticleByS_no(s_no);
		return articleList;
		
	}
	
	@RequestMapping("/findArticleByU_id")
	@ResponseBody
	public List<Article> findArticleByU_id(@RequestParam("u_id") String u_id){
		System.out.println("执行了findArticleByU_id端操作");
		List<Article> articleList = new ArrayList<>();
		articleList = articleDao.findArticleByU_id(u_id);
		
		return articleList;
	}

	@RequestMapping(value="/uploadArticle",produces="application/json;charset=utf-8")
	@ResponseBody
	public String uploadArticle(HttpServletRequest request) {
		
		DiskFileItemFactory dfif=new DiskFileItemFactory();
		//2.创建一个文件解析器
		ServletFileUpload upload=new ServletFileUpload(dfif);
		upload.setFileSizeMax(1024*1024*5);//允许上传的单个文件最大为5Mb
		upload.setSizeMax(1024*1024*10);//允许上传的总共文件大小最大为10mb
		
		String username = null;
		//设置中文上传处理
		upload.setHeaderEncoding("UTF-8"); 
		try {
			//request.setCharacterEncoding("utf-8");
			//对request请求来的数据或文件进行分析
			//parseRequest方法返回一个FileItem类型的集合
			List<FileItem> list=upload.parseRequest(request);
			int a_no = -1;
			Boolean isUploadOk = true;
			Boolean DBInsertOk = true;
			for(FileItem item:list)
			{
				//如果item是普通输入框（即不是文件输入框）
				//System.out.println(item.getName()+",,,"+item.getString()+",,,"+item.getFieldName());
				if(item.isFormField())
				{
					//System.out.println(item.getFieldName()+"  :"+item.getString("utf-8"));
					switch(item.getFieldName())
					{	
						case "username":
							System.out.println(item.getFieldName()+":"+item.getString("utf-8"));
							username = item.getString();
							break;
						case "password":
							System.out.println(item.getFieldName()+":"+item.getString("utf-8"));
							break;
						case "articleGson":
							System.out.println(item.getFieldName()+":"+item.getString("utf-8"));
							a_no = addArticle(item.getString("utf-8"),username);
							if(a_no < 0 ) {
								DBInsertOk = false;
							}
							break;
						case "asuGson":
							System.out.println(item.getFieldName()+":"+item.getString("utf-8"));
							if(!addAsuList(item.getString("utf-8"),a_no)) {
								DBInsertOk = false;
							}
							break;
					}
				}
				else {		
					switch(item.getFieldName())
					{
						
						
						case "cpFileName":	
							System.out.println(item.getFieldName()+",,,"+item.getName());
							String fileName = item.getName();
							InputStream is = item.getInputStream();
							if(!upload(is,fileName,username)) {
								
								isUploadOk = false;
							}
							
						case "zipFileName":
							System.out.println(item.getFieldName()+",,,"+item.getName());
							String fileName2 = item.getName();
							InputStream is2 = item.getInputStream();
							if(!upload(is2,fileName2,username)) {
								
								isUploadOk = false;
							}
							
					}
				}
			}
			
			
			if(isUploadOk && DBInsertOk) {
				return "上传成功";
			}else if(isUploadOk) {
				return "云端数据库更新异常";
			}else {
				return "上传失败";
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
			return "上传失败";
		//	return "redirect:/ok/add_masterDirection_fail.jsp";
		}catch(Exception e )
		{
			e.printStackTrace();
			return "上传失败";
		//	return "redirect:/ok/add_masterDirection_fail.jsp";
		}
		
		
		//return "上传成功";
	}
	
	
	private int addArticle(String articleGson,String username) {
		
		int a_no = -1;
		Gson gson = new Gson();
		final Article article = gson.fromJson(articleGson, new TypeToken<Article>() {
        }.getType());
		
		System.out.println("article:"+article);
		try {
			article.setStatus("cloud");
			article.setContent_link(username+"//"+article.getContent_link());
			articleDao.addArticle(article);
			System.out.println("对应i："+article.getA_no());
			a_no = article.getA_no();
		}catch(Exception e) {
			System.out.println("插入异常了");
			e.printStackTrace();
			return -1;
		}
		
		
		return a_no;
	}
	
	private boolean addAsuList(String asuListGson,int a_no) {
		
		Gson gson = new Gson();
		List<A_s_u> asuList = gson.fromJson(asuListGson, new TypeToken<List<A_s_u>>() {
		}.getType());
		
		System.out.println(asuList);
		
		try {
			
			for(A_s_u asu : asuList) {
				asu.setA_no(a_no);
				asuDao.addAsu(asu);
			}
		}catch(Exception e) {
			System.out.println("插入数据库异常");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private boolean upload(InputStream in,String fileName,String username) {
		if(in == null || fileName == null || "".equals(fileName)) return false;
		FileOutputStream fos = null;
		try {
			String path = Global.articlePath+username+"//";
			File file = new File(path);//指定一个路径
			
			if(!file.exists())
			{
				file.mkdirs();
			}
			
			
			fos = new FileOutputStream(path+fileName);
			
			byte [] bytes = new byte[1024*10];
			int i =-1;
			while((i=in.read(bytes))!=-1) {
				fos.write(bytes, 0, i);
			}
			
			fos.flush();
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
				try {
					if(in != null) {
						in.close();
					}
					
					if(fos != null) {
						fos.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		return true;
		
	}
}
