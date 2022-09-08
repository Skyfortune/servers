package io;

public class ArgsEx1 {

	public static void plus(int a, int b) {
		System.out.println(a+b);
	}
	//가변인수
	public static void plus(int...n/*자동배열*/) {
		int sum = 0;
		for (int i=0; i<n.length; i++) {
			sum+=n[i];
		}
		System.out.println(sum);
	}
	public static void main(String[] args) {
		plus(10,20);
		plus(10,20, 30);
		plus(10,20, 30, 40);
		plus(10,20, 30, 50);
		plus(10,20, 30, 40, 50, 60);
	}

}
