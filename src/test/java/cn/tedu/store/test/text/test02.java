package cn.tedu.store.test.text;

import java.util.Scanner;

public class test02 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n=sc.nextInt();
        int i=1,sum=1;
        //����: n=5,i=1
        while(i<=n)//1<5 Ϊtrue ִ�д��������������
        {
            sum=sum*i;// 1*1 �����ֵ��sum
            i++;//i+1
        }

        System.out.print(sum);
    }
}
