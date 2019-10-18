package cn.tedu.store.test.text;



public class TextRegexpTestCase {
	
	public static void main(String[] args) {
		 // 55-88  
        String regex = "^ABC([5-7]+[0-9]+|[8]+[8]+)$";  
       // String regex1 = "^ABC(00[1-9]|0[1-9][0-9]|100)$";  
        String str="ABC59";
      System.out.println(str.matches(regex));
  
	}


}



