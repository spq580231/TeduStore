package cn.tedu.store.test.text;




import java.io.File;
import java.util.ArrayList;

public class Test {

    public static void main(String[] args) {

        String path = "G:\\music";
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length-1; i++) {
            if (tempList[i].isFile() && i < tempList.length) {
                if (tempList[i].length() == tempList[i+1].length()) {
                    System.out.println(tempList[i].getName());
                }

            }

        }

    }
}
