package homework01;

public class Main {

	
	
	public static void main(String[] args) {
		
		Family family = new Family();
		family.houseNum = 120;
		Father father1 = new Father();
		
		father1.name="����";
		father1.id="143";
		father1.job="����";
		
		Children children = new Children();
		children.name="����ϼ";
		children.id="121";
		children.stuNo="131";
		children.father=father1;
		
		Children children2 = new Children();
		children2.name="����";
		children2.id="122";
		children2.stuNo="132";
		children2.father=father1;
		
		father1.childrenArr=new Children[]{children,children2};
		
		family.childrenArr = father1.childrenArr;
		family.father = father1;
		
		
		
		Family family2 = new Family();
		family.houseNum = 130;
		Father father2 = new Father();
		
		father2.name="���";
		father2.id="144";
		father2.job="�ֳ�";
		
		Children children3 = new Children();
		children3.name="����";
		children3.id="123";
		children3.stuNo="133";
		children3.father=father2;
		
	
		
		father2.childrenArr=new Children[]{children3};
		
		family2.childrenArr = father2.childrenArr;
		family2.father = father2;
		
		
		
		
		
	}

}
