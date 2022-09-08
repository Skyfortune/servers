package io;

import java.io.InputStream;

/*I/O:input/Output*/

public class inputStreamEx1 {
	
	public static void main(String[] args) {
		//가장 기본적인 기능을 가지고 있다.
		//바이트 단위로 와리가리 하는 빨때같은 통로...
		InputStream is = System.in; //키보드(system.out : 콘솔창)
		while(true) {
			try {
				int i = is.read(); //내부적으로 쓰레트 대기 상태
				//A -> 64 -> 1byte 변환되어 vm에 전달
				if(i==-1) break; //ctrl+z
				System.out.print((char)i);
			} catch(Exception e) {
				e.printStackTrace(); //예외 모든 경로까지 출력
			}
		}//--while
		
	}//main
	
}//--class


//영어는 그대로 나오지만 한글은 깨지게 된다
