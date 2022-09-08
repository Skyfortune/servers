package io;

import java.io.FileReader;
import java.io.IOException;

public class FileReaderEx1 {

	public static void main(String[] args) {
		///aaa.txt는 동일한 폴더 존재, 절대 경로로도 가능
		//파일리더가 그냥 던져버리기때문에 예외처리가 필요하다
		try {
			FileReader fr = new FileReader("io/aaa.txt");
			int i;
			//다중캐치에 대한 설명
			while((i=fr.read())!=-1) {
				System.out.print((char)i);
			}
			fr.close();
			System.out.println("\nEnd~~~~~~~~~~~");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}
