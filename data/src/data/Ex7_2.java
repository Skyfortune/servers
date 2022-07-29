package data;

public class Ex7_2 {

	public static void main(String[] args) {
		char deletedItem;
		LinkedStack LS = new LinkedStack();
		
		LS.push('A');
		LS.printStack();
		
		LS.push('B');
		LS.printStack();
		
		LS.push('C');
		LS.printStack();
		
		deletedItem = LS.pop();
		if(deletedItem!=0)
			System.out.println("deletedItem :" + deletedItem);
		LS.printStack();
	}

}
