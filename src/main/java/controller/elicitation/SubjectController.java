package controller.elicitation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dao.S_sDao;
import dao.SubjectDao;
import entity.Subject;
import tools.SubjectMap;

@Controller
@RequestMapping("/subject")
public class SubjectController {
	static String 	nae;
	
	@Resource(name="subjectDao")
	private SubjectDao subjectDao;
	
	@Resource(name="s_sDao")
	private S_sDao s_sDao;
	
	
	@RequestMapping("/findSubjectByP_no2")
	@ResponseBody
	public SubjectMap findSubjectByP_no() {
		List<Subject> subjectList =	subjectDao.findSubjectByP_no("20180523001");
		
		Map<String ,Subject> map = new HashMap<>();
		
		for(Subject s:subjectList) {
			map.put(s.getS_no(), s);
		}
		SubjectMap subjectMap = new SubjectMap(map);
		
		String [] str = subjectMap.sNoArray;
		
		for(int i  = 0;i<str.length;i++) {
				
				System.out.println(str[i]);
		}
		
		
		int[][] matrix = subjectMap.matrixRelation;
		
		for(Subject subject:subjectList) {
			//�����ݿ��ȡ��ѧ�Ƶ��������ѧ����Ϣ
			String s_no = subject.getS_no();		
			List<Subject> baseSubjectList = s_sDao.findPreSubjectByNextSubject(s_no);
			
			//System.out.println("���鳤�ȣ�"+str.length+",,,,s_no"+s_no);
			int row = getIndex(str, s_no);//�õ���ѧ�Ʊ�Ŷ�Ӧ����
			//System.out.println("�У�"+row);
			
			//�������е�ÿһ��Ԫ��
			for(Subject s:baseSubjectList) {
				int col = getIndex(str, s.getS_no());//�õ���Ԫ�ض�Ӧ�����±�
				System.out.println("�У�"+col);
				matrix[row][col] = 1;
			}

			//����ϵ����ֵ
		}
		
		 List<Integer> tempList = new ArrayList<>();
		 
		 List<Integer> createNodeList = new ArrayList<>();
         //��һ��
         for( int i = 0;i<matrix.length;i++){
             int j = 0;
            second: for(;j<matrix[i].length;j++){
                 if(matrix[i][j] == 1){
                     break second;
                 }
             }
             if(j == matrix[i].length){

                //���ɸýڵ㣬����ӵ�������
                System.out.println("��һ����ӵĻ���ѧ�ƣ�"+subjectMap.map.get(subjectMap.sNoArray[i]).getName());
                createNodeList.add(i);
                tempList.add(i);
             }
         }
         //�ڶ���

         
         
         for(int j = 0;j<tempList.size();j++){
        	 
        	 int index = tempList.get(j);
        	 System.out.println(index+"��ǰ���ʵ��е�ѧ�ƣ�"+subjectMap.map.get(subjectMap.sNoArray[index]));

             //����base_index��
             for(int i = 0;i<matrix.length;i++){
                 if(matrix[i][index] == 1 &&  i != index){
                	 System.out.print("�Ƿ񱻴�����"+SubjectMap.isVisit(createNodeList, 0, createNodeList.size(), i));
                	 System.out.println(",�Ƿ񱻷��ʣ�"+SubjectMap.isVisit(createNodeList, 0, j+1, i));
                	 
                     //�ҵ�baseѧ�Ƶĺ��ѧ��
                     //�жϸú��ѧ���Ƿ񱻷��ʹ��ˣ�������ʹ��ˣ�������ӵ����ϵ��У�ֻ����ѧ�ƽ��
                     if( SubjectMap.isVisit(tempList,0,j+1,i)){
                    	 System.out.println("��������ѧ�������ߣ�"+subjectMap.map.get(subjectMap.sNoArray[index]).getName()
                    			 +"------------>"
                    			 +subjectMap.map.get(subjectMap.sNoArray[i]).getName());
                         //TODO ��������������������
                     }else{
                         //�����ý������ߣ������ýڵ㱣�浽������
                    	 System.out.println("����ѧ�ƣ�"+subjectMap.map.get(subjectMap.sNoArray[i]).getName());
                    	 createNodeList.add(i);
                    	 System.out.println("��������ѧ�������ߣ�"+subjectMap.map.get(subjectMap.sNoArray[index]).getName()+"------------>"+subjectMap.map.get(subjectMap.sNoArray[i]).getName());
                    	 tempList.add(i);
                     }
                 }
             }
         }
		System.out.println(subjectMap.map.size());
		int [][] matrix2 = subjectMap.matrixRelation;
	
		for(int i = 0;i<matrix2.length;i++) {
			for(int j =0;j<matrix2[i].length;j++) {
				System.out.print(" "+matrix2[i][j]);
			}
			
			System.out.println();
		}
		return subjectMap;
	}
	
	public int getIndex(String [] array,String str) {
		
		if(array == null || array.length == 0 ) {
			return -1;
		}
		for(int i = 0;i<array.length;i++) {
			if(array[i].equals(str)) {
				return i;
			}
		}
		
		
		return -1;
	}
	
	
	@RequestMapping("/findAll")
	@ResponseBody		//��ʾ���ص�ֵ��������ͼ���棬����json�ַ���
	public List<Subject> findAll(){
		List<Subject> subjectList = subjectDao.findAll();
		
		return subjectList;
	}

	
	/**
	 * 
	 * @param p_no
	 * @return
	 */
	@RequestMapping("/findSubjectByP_no")
	@ResponseBody			//ָ����ǰ���ض���Ϊjson�����ַ���
	public List<Subject> findSubjectByP_no(@RequestParam("p_no") String p_no){
		List<Subject> subjectList =  subjectDao.findSubjectByP_no(p_no);
		
		return subjectList;
		
	}
	
	
	@RequestMapping("/downLoadImageByS_no")
	public void downLoadImageByS_no(HttpServletRequest request,HttpServletResponse response) {
		FileInputStream fis = null;
		OutputStream out = null;
		try {
			request.setCharacterEncoding("utf-8");
			String s_no = request.getParameter("s_no");
			
			Subject subject = subjectDao.findSubjectByS_no(s_no);
			String imagePath = subject.getImage_address();
			
			String fileName = s_no+".jpg";
			
			File file = new File(imagePath);
			
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", "attachement;filename="
					+URLEncoder.encode(fileName,"utf-8"));
			
			if(!file.exists()) {
				
				response.getWriter().println("�Ҳ���ͼƬ");
			}
			
			
			fis = new FileInputStream(file);
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
