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
		System.out.println(file.getPath()+"目录下的java代码行数："+count);	
	}
	/**
	 * 统计指定文件目录下的java文件中的代码行数
	 * @param file
	 */
	public static void countLine(File file) {
		if(file == null) {
			throw new NullPointerException("文件为null");
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


