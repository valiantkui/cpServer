package homework01;

public class Main {

	
	
	public static void main(String[] args) {
		
		Family family = new Family();
		family.houseNum = 120;
		Father father1 = new Father();
		
		father1.name="王杰";
		father1.id="143";
		father1.job="钢铁";
		
		Children children = new Children();
		children.name="王晓霞";
		children.id="121";
		children.stuNo="131";
		children.father=father1;
		
		Children children2 = new Children();
		children2.name="王光";
		children2.id="122";
		children2.stuNo="132";
		children2.father=father1;
		
		father1.childrenArr=new Children[]{children,children2};
		
		family.childrenArr = father1.childrenArr;
		family.father = father1;
		
		
		
		Family family2 = new Family();
		family.houseNum = 130;
		Father father2 = new Father();
		
		father2.name="许飞";
		father2.id="144";
		father2.job="钢厂";
		
		Children children3 = new Children();
		children3.name="许丽";
		children3.id="123";
		children3.stuNo="133";
		children3.father=father2;
		
	
		
		father2.childrenArr=new Children[]{children3};
		
		family2.childrenArr = father2.childrenArr;
		family2.father = father2;
		
		
		
		
		
	}

}
