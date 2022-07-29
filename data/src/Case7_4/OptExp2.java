package Case7_4;


public class OptExp2 {
	private String exp;
	
	public int evalPostfix(String postfix) {
		
		LinkedStack S = new LinkedStack();
		
		exp = postfix;
		int opr1, opr2, value;
		//항을 넣는다
		char testCh;
		for(int i=0; i<7; i++) {
			testCh = exp.charAt(i);
			//문자를 꺼내면서 연산, 연산자가 아니면 fasle
			if(testCh != '+'
				&& testCh != '-' 
				&& testCh!='*' 
				&&testCh != '/') {
				value = testCh - '0';
				S.push(value);

			}
			else {
				opr2 = S.pop();
				opr1 = S.pop();
				switch(testCh) {
				case '+' : S.push(opr1 + opr2); break;
				//더하기를 만나면 둘다 더한뒤에 푸쉬.
				case '-' : S.push(opr1 - opr2); break;
				case '*' : S.push(opr1 * opr2); break;
				case '/' : S.push(opr1 / opr2); break;

				}
			}
		}
		return S.pop();
	}
}
