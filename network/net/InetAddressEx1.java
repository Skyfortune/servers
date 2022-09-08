package net;

import java.net.InetAddress;
import java.util.Iterator;

public class InetAddressEx1 {

	public static void main(String[] args) {
		//ip 및 도메일 객체화 
		
		InetAddress add;
		try {
			add = InetAddress.getLocalHost();
			System.out.println("HOST name : " + add.getHostName());
			System.out.println("HOST Address : " + add.getHostAddress());
			add = InetAddress.getByName("auction.co.kr");
			System.out.println("auction Address : " + add.getHostAddress());
			InetAddress adds[] = InetAddress.getAllByName("naver.com");
			System.out.println("------------");
			//배열 밑에는 항상 for문이 있다
			for (int i = 0; i < adds.length; i++) {
				System.out.println("naver : " + adds[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
