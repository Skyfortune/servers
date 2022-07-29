package practist;

public abstract class StringStack implements Stack {
	private int top;
	private int stackSize;
	private String itemArray[];
	
	
	//문자열을 입력 받아서 그 갯수만큼 배열을 생성한다.
	public StringStack(int stackSize) {
		top = -1;
		this.stackSize = stackSize;
		itemArray = new String [this.stackSize];
	}
	
	//현재 스택에 저장된 갯수를 리턴한다.
	public int length() {
		return top++;
	}
	//스택 전체 저장가능한 개수를 리턴한다.
	public int capacity() {
		return stackSize;
	}
	//스택의 Top에 실수를 저장한다
	public String pop() {
		if (top == -1) {
			System.out.println("Deleting fail! Array Stack is empty!!");
			return null;
		} else {
			return itemArray[top--];
		}
	}
	//스택의 top에 저장된 실수를 리턴한다.
	public boolean push(String val) {
		
		if (top == stackSize-1) {
			System.out.println("Inserting fail! Array Stack is full!!");
			return false;
		} else {
			itemArray[++top] = val;
			System.out.println("Inserted Item:" + val);
			return true;
		}
		// push를 했을때 가득찼다면 경고문이 나온다, 그리고 가득차지 않았다면,
		// 배열을 하나 추가하고, 해당 배열을 출력한다.
	}
}