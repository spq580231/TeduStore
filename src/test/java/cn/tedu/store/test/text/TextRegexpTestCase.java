package cn.tedu.store.test.text;



public class TextRegexpTestCase {
	
	public static void main(String[] args) {
		 // 55-88  
		String reg = "[2,4,6,8]|[1-9][0,2,4,6,8]|100";
       // String regex1 = "^ABC(00[1-9]|0[1-9][0-9]|100)$";  
        String str="50";
      System.out.println(str.matches(reg));
  
	}


}



