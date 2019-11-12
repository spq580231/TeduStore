package cn.tedu.store.test.text;

import java.util.Scanner;

public class test02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n=sc.nextInt();
        int i=1,sum=1;
        //例如: n=5,i=1
        while(i<=n)//1<5 为true 执行大括号里面的内容
        {
            sum=sum*i;// 1*1 结果赋值给sum
            i++;//i+1
        }

        System.out.print(sum);
    }
}
