package io;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class WriterEx1 {

	public static void main(String[] args) {
		int i = 'A';
		char c = 'b';
		char c1 = '김';
		
		try {
			/*OutputStream os = System.out;
			Writer writer = new OutputStreamWriter(os);*/
			
			Writer writer = new OutputStreamWriter(System.out);
			
			writer.write(i);
			writer.write(c);
			writer.write(c1);
			writer.flush();
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
