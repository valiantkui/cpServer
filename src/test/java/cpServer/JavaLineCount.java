package cpServer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JavaLineCount {
	static int count = 0;
	public static void main(String[] args) {
		File file = new File("D:\\workspace");
		countLine(file);
		System.out.println(file.getPath()+"Ŀ¼�µ�java����������"+count);	
	}
	/**
	 * ͳ��ָ���ļ�Ŀ¼�µ�java�ļ��еĴ�������
	 * @param file
	 */
	public static void countLine(File file) {
		if(file == null) {
			throw new NullPointerException("�ļ�Ϊnull");
		}else if(file.isDirectory()) {
			File [] files = file.listFiles();
			for(File f : files) {
				countLine(f);
			}
		}else if(file.getName().endsWith(".java") 
				|| file.getName().endsWith(".html") 
				|| file.getName().endsWith(".jsp")){
				BufferedReader br = null;
				try {
					br = new BufferedReader(new FileReader(file));
					while(br.readLine() != null) {
						count++;
					}
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					if(br != null) {
						try {
							br.close();
							br = null;
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}	
				}
			}
	}
}


