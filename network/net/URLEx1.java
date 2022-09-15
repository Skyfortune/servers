package net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class URLEx1 {

	public static void main(String[] args) {
		//자바를 검색했을때 리턴 되는 값들
		String str = "https://search.naver.com:80/search.naver?"
				+ "where=nexearch&sm=top_hty&fbm=1&ie=utf8&"
				+ "query=java#top";
		//자체가 약어로 통용된다.
		try {
			URL url = new URL(str);
			System.out.println("Protocol :" + url.getProtocol());
			System.out.println("Host :" + url.getHost());
			System.out.println("Port :" + url.getPort());
			System.out.println("Path :" + url.getPath());
			System.out.println("Query :" + url.getQuery());
			System.out.println("Filename :" + url.getFile());
			//프론트쪽에서는 민감함
			System.out.println("ref :" + url.getRef());
			url = new URL("https://www.naver.com/");
			BufferedReader br = new BufferedReader(
				new InputStreamReader(url.openStream(), "UTF-8"));
			String line="";
			while(true) {
				line = br.readLine();
				if(line==null) break;
				System.out.println(line);
			}
			br.close();
			System.out.println("-THE END-");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
