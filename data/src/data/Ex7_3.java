package data;

import java.util.Scanner;

class Ex7_3 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		OptExp opt = new OptExp();
//		String exp = "(3*5)-(6/2)";
		String exp = sc.next();
		char postfix[];
		int value;
		System.out.println(exp);
		if(opt.testPair(exp)) //괄호검사
			System.out.println("괄호 맞음!");
		else 
			System.out.println("괄호 틀림!");
		
		System.out.printf("/n 후위표기식:");
		//배열을 만들고 출력
			postfix = opt.toPostfix(exp);
			System.out.println(postfix);
			
			sc.close();
	}

}
