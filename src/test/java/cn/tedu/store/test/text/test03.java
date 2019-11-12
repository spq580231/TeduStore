package cn.tedu.store.test.text;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class test03 {
    public static void main(String[] args) throws IOException {
        BufferedReader buf = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("请输入高校名:");
        String Sname = buf.readLine();
        int m = Sname.length();
        BufferedReader br = new BufferedReader(new FileReader("d:/1.txt"));
        String result = null;
        while ((result = br.readLine()) != null && result.substring(0, m).equals(Sname) ) {

            System.out.println(result);

        }

    }
}
