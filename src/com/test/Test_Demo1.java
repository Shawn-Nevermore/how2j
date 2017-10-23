package com.test;

public class Test_Demo1 {
	public static void main(String[] args) {
		StringBuilder sb = new StringBuilder(" h e l l o ");
		System.out.println(sb.capacity());
		System.out.println(sb.length());
		
		//StringBuilder的trimToSize()方法不同于String的trim()方法。
		//StringBuilder是将其capacity缩减到和length一样，并不会去首尾空格
		sb.trimToSize();
		System.out.println(sb.capacity());
		System.out.println(sb.length());
		
		
		String s1 = " h e l l o ";
		s1.trim();
		System.out.println(s1);
		s1 = s1.trim();
		System.out.println(s1);
		System.out.println(s1.length());
	}
}
