package data;

class OptExp {

	private String exp;
	private int expSize;
	private char testCh, openPair;
//괄호를 열고 닫는것을 하기 위해서 만든 블룸
	public boolean testPair(String exp) {
		this.exp = exp;
		LinkedStack S = new LinkedStack();
		expSize = this.exp.length(); //스트링으로 받았음 
		for (int i = 0; i < expSize; i++) {
			testCh = this.exp.charAt(i);
			switch (testCh) {
			case '(':
			case '{':
			case '[':
				S.push(testCh);
				break;
			case ')':
			case '}':
			case ']':
				
				if (S.isEmpty())
					return false;
				//괄호가 잘 닫지히 않을때 사용할 if문
				else {
					openPair = S.pop();
					if ((openPair == '(' && testCh != ')') || (openPair == '{' && testCh != '}')
							|| (openPair == '[' && testCh != ']'))
						return false;
					else
						break;

				}

			}
		}
		if (S.isEmpty())
			return true;
		else
			return false;
	}

	public char[] toPostfix(String infix) {
		char testCh;
		exp = infix;
		int expSize = 10;
		int j = 0;
		char postfix[] = new char[expSize];
		LinkedStack S = new LinkedStack();

		for (int i = 0; i <= expSize; i++) {
			testCh = this.exp.charAt(i);
			switch (testCh) {
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				//베열에 넣는다
				postfix[j++] = testCh;
				break;

			case '+':
			case '-':
			case '*':
			case '/':
				//연산자가 나오면 킵
				S.push(testCh);
				break;
				//괄호가 다시 나오면 연산자를 꺼내온다.
				//대신 중괄호와 대괄호는 포함하고 있지 않음
			case ')':
				postfix[j++] = S.pop();
				break;

			default:

			}
		}
		postfix[j] = S.pop();
		return postfix;
	}
}
