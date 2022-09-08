package io;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;

public class BufferedWriterEx1 {

	public BufferedWriterEx1() {
		
	}
	
	public static void main(String[] args) {
		String str = "오늘은 즐거운 목요일입니다.";
		BufferedWriter bw = 
				new BufferedWriter(new OutputStreamWriter(System.out));
		
		try {
			bw.write(str, 0, str.length());
			bw.newLine();
			bw.write(str);
			bw.newLine();
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
