package cn.tedu.store.test.text;

import java.util.regex.Pattern;

public class Test {

	public static void main(String[] args) {
        String s  = "^ABC{1}((00){1}[1-9]{1}|(0){1}[0-9]{2}|100)$";
        System.out.println( "ABC"+","+Pattern.matches(s, "ABC"));
        System.out.println( "ABC000"+","+Pattern.matches(s, "ABC000"));
        System.out.println( "ABC001"+","+Pattern.matches(s, "ABC001"));
        System.out.println( "ABC100"+","+Pattern.matches(s, "ABC100"));
        System.out.println( "ABC101"+","+Pattern.matches(s, "ABC101"));
        System.out.println( "ABCC100"+","+Pattern.matches(s, "ABCC100"));
        System.out.println( "ABCC100"+","+Pattern.matches(s, "ABC333"));
	} 
}
