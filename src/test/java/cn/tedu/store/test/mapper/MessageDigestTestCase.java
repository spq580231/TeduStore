package cn.tedu.store.test.mapper;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;

public class MessageDigestTestCase {

	public static void main(String[] args) {
		String password = "1234";
		
		String salt = UUID.randomUUID().toString();
		System.out.println("salt=" + salt + ", length=" + salt.length());
		salt = UUID.randomUUID().toString();
		System.out.println("salt=" + salt + ", length=" + salt.length());
		salt = UUID.randomUUID().toString();
		System.out.println("salt=" + salt + ", length=" + salt.length());
		salt = UUID.randomUUID().toString();
		System.out.println("salt=" + salt + ", length=" + salt.length());
		salt = UUID.randomUUID().toString();
		System.out.println("salt=" + salt + ", length=" + salt.length());
		
		String md5 = DigestUtils.md5Hex(password + salt).toUpperCase();
		System.out.println("��1�μ��ܣ�" + md5);
		md5 = DigestUtils.md5Hex(md5).toUpperCase();
		System.out.println("��2�μ��ܣ�" + md5);
		md5 = DigestUtils.md5Hex(md5).toUpperCase();
		System.out.println("��3�μ��ܣ�" + md5);
		md5 = DigestUtils.md5Hex(md5).toUpperCase();
		System.out.println("��4�μ��ܣ�" + md5);
		md5 = DigestUtils.md5Hex(md5).toUpperCase();
		System.out.println("��5�μ��ܣ�" + md5);
		
		System.out.println(70L * 70 * 70 * 70 * 70 * 70);
		System.out.println(70L * 70 * 70 * 70 * 70 * 70 * 70);
	}
	
	// 70 * 70 * 70 * 70 * 70 * 70
	
	
	
}
