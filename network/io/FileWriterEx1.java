package io;

import java.io.FileWriter;

public class FileWriterEx1 {

	public static void main(String[] args) {
		String str = "Dream as if you'll live forever,\n"
				+ "live as if you`ll die today - 제임스딘";
		try {
			//파일이 없다면 만들어 준다
			FileWriter fw = new FileWriter("io/bbb.txt"); //파일이 없다면 만들어줌
			fw.write(str);
			fw.flush();
			fw.close();
			System.out.println("END.........");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
