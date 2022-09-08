package io;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class ReaderEx1 {

	
	public static void main(String[] args) {
		//Reader:스트림의 한글 처리 클래스
		InputStream is = System.in;
		//바이트 단위에 Data를 문자 단위로 처리하는 클래스
		Reader reader = new InputStreamReader(is);
		try {
			while(true) {
				int i = reader.read();
				if(i==-1) break;
				System.out.print((char)i);
			}
			reader.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
//		OutputStream os;
//		Writer writer;
	}

}
//한글자씩 튀어나오는식