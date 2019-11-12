package cn.tedu.store.test.text;
public class test01 {
    public static void main(String[] args) {
        boolean []b=new boolean[1000];
        int stu=0;
        for (int i = 0; i <b.length ; i+=2) {
            b[i]=true;
        }
        for (int i = 0; i <b.length ; i+=3) {
           if(b[i]){
               b[i]=false;
           }else {
               b[i]=true;
           }
        }
        for (int i = 0; i <b.length; i+=4) {
            if(b[i]){
                b[i]=false;
            }else {
                b[i]=true;
            }
        }
        for (int i = 0; i <b.length ; i+=5) {
            if(b[i]){
                b[i]=false;
            }else {
                b[i]=true;
            }
        }
        for (int j = 0; j < b.length; j++) {
            if(b[j]){
                stu+=1;
            }
        }
        System.out.println(stu);
    }
}
