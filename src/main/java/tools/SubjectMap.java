package tools;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import entity.Subject;

/**
 * ѧ�Ƶ����νڵ�
 * @author KUIKUI
 *
 */
public class SubjectMap {
	public Map<String,Subject> map;//����ѧ�Ʊ����ѧ�Ƶ�ӳ��
	
	public String [] sNoArray;//����ѧ�Ʊ�ŵ�����
	
	
	/**
	 * һ����Ϣ�洢��һ��ѧ�Ƶ��������ѧ��
	 * һ����Ϣ�洢��һ��ѧ�Ƶĺ��ѧ��
	 */
	public int [][]  matrixRelation;

	public SubjectMap(Map<String, Subject> map) {
		super();
		this.map = map;
		int size = map.size();
		this.sNoArray = new String[size];
		int i = 0;
		for(Entry<String, Subject> entry:map.entrySet()) {
			sNoArray[i++] = entry.getKey();
		}
		
		this.matrixRelation = new int[size][size];
	
	}
	
	/**
	 * ��ָ���ļ��ϣ�ָ����λ�ô��ж��Ƿ����ĳ��Ԫ��
	 * @return �������  ����true,���򷵻�false;
	 */
	public static boolean isVisit(List<Integer> list,int start,int end,int elem){
		if(start < 0 || end > list.size()){
			return false;
		}

		for(int i = start;i<end;i++){
			if(list.get(i)==elem){
				return true;
			}
		}

		return false;
	}

	

	
	
}
