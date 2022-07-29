package data;

interface Stack {
	boolean isEmpty();

	void push(char item);

	char pop();

	void delete();

	char peek();
}

class ArrayStack implements Stack {
	private int top;
	private int stackSize;
	private char itemArray[];

	// top, stackSize, 문자로된 배열 선언
	public ArrayStack(int stackSize) {
		top = -1;
		this.stackSize = stackSize;
		itemArray = new char[this.stackSize];
	}
	// 스텍사이즈에 대한것, 탑을 하나 줄이고 스택사이즈 만큼 배열을 생성한다.

	public boolean isEmpty() {
		return (top == -1);
		//true를 반환하는것,boolean isEmpty=true; if(top==-1) | ture | return bEmpty
	}
	// 비운것 반환

	public boolean isFull() {
		return (top == this.stackSize - 1);
	}
	// 가득찬것 반환

	public void push(char item) {
		if (isFull()) {
			System.out.println("Inserting fail! Array Stack is full!!");
		} else {
			itemArray[++top] = item;
			System.out.println("Inserted Item:" + item);
		}
		// push를 했을때 가득찼다면 경고문이 나온다, 그리고 가득차지 않았다면,
		// 배열을 하나 추가하고, 해당 배열을 출력한다.
	}

	public char pop() {
		if (isEmpty()) {
			System.out.println("Deleting fail! Array Stack is empty!!");
			return 0;
		} else {
			return itemArray[top--];
		}
	}

	public void delete() {
		if (isEmpty()) {
			System.out.println("Deleting fail! Array Stack is empty!!");
		} else {
			top--;
		}
	}

	public char peek() {
		if (isEmpty()) {
			System.out.println("peeking fail! Array stack is empty!!");
			return 0;
		} else
			return itemArray[top];
	}

	public void printStack() {
		if (isEmpty())
			System.out.printf("Array Stack is Empty!! %n %n");
		else {
			System.out.printf("Array Stack>>");
			for (int i = 0; i < top; i++)
				System.out.printf("%c", itemArray[i]);
			System.out.println();
			System.out.println();
		}
	}
}
