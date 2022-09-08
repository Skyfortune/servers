package io;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

public class FileCopyEx1 {

	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.print("원본파일 : ");
			String sFile = sc.nextLine();
			System.out.print("복사파일 : ");
			String cFile = sc.nextLine();
			FileReader fr = new FileReader("io/"+sFile); 
			//파일생성
			FileWriter fw = new FileWriter("io/"+cFile);
			int c;
			//파일 내용의 제일 마지막 값은 -1
			while((c=fr.read())!=-1) {
				fw.write(c);
			}
			fw.close();
			fr.close();
			System.out.println("Copy End.................");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
