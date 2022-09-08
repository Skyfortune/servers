package io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.net.ssl.SSLContext;

public class BufferedReaderEx1 {

	public static void main(String[] args) {
//		InputStream is = System.in; //스트림 
//		InputStreamReader isr = new InputStreamReader(is); //문자
//		BufferedReader br = new BufferedReader(isr); //버퍼 기능
		
		BufferedReader br = 
				new BufferedReader(
				new InputStreamReader(System.in));
				
		String s = "";
		while(true) {
			try {
				s= br.readLine();
				System.out.println("출력:" + s);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}//--while
				
	}

}
//버퍼의 기능으로 한줄단위로 처리가 가능해졌다. (문자열 처리)
//이것이 채팅프로그램의 전송방식으로 채택