package data;

public class EX6_1 {
	public static void main(String arg[]) {
		LinkedList L = new LinkedList();

		System.out.println("(1) 공백 리스트에 노드 3개 삽입하기");
		L.insertLastNode("월");
		L.insertLastNode("수");
		L.insertLastNode("일");
		L.printList();
		
		System.out.println("(2)수 노드 뒤에 금 노드 삽입하기");
		ListNode pre = L.searchNode("수");
		if(pre ==null)
			System.out.println("검색실패>>찾는 데이터가 없습니다.");
		else {
			L.insertMiddleNode(pre,"금");
			L.printList();
		}
		System.out.println("(3)리스트의 노드를 역순으로 바꾸기");
		L.reverseList();
		L.printList();
		
		System.out.println("(4)리스트의 마지막 노드 삭제하기");
		L.deleteLastNode();
		L.printList();
	}
}
class LinkedList {
	private ListNode head;
	public LinkedList() {
		head = null;
	}

	public void insertMiddleNode(ListNode pre, String data) {
		ListNode newNode = new ListNode(data);
		newNode.link = pre.link;
		pre.link = newNode;
	}

	public void insertLastNode(String data) {
		//노드 삽입
		ListNode newNode = new ListNode(data);
		if (head == null) {
			this.head = newNode;
			//헤드가 비어있다면 이 뉴노드를 넣는다
		} else {
			ListNode temp = head;
			//입력한 값은 tmep가 된다(임시변수)
			while (temp.link != null)
				//템프.링크가 빈값이 아니라면,
				temp = temp.link;
			//템프는 템프.링크가 된다. 제일중요, 자기 위치를 잡는것에 대한 개념을 찾아야한다.
			temp.link = newNode;
			//그리고 템프링크는 뉴노드
			//헤드부터 해서 계속 찾는것
		}
	}

	public void deleteLastNode() {
		ListNode pre, temp;
		if (head == null)
			return;
		if (head.link == null) {
			head = null;
		} else {
			pre = head;
			temp = head.link;
			while (temp.link != null) {
				pre = temp;
				temp = temp.link;
			}
			pre.link = null;
		}
	}

	public ListNode searchNode(String data) {
		ListNode temp = this.head;
		while (temp != null) {
			if (data == temp.getData())
				return temp;
			else
				temp = temp.link;
		}
		return temp;
	}

	public void reverseList() {
		ListNode next = head;
		ListNode current = null;
		ListNode pre = null;
		//next는 헤드, 나머지는 null
		while (next != null) {
			pre = current;
			current = next;
			next = next.link;
			current.link = pre;
		}
		head = current;
	}

	public void printList() {
		ListNode temp = this.head;
		System.out.print("L=(");
		while (temp != null) {
			System.out.printf(temp.getData());
			temp = temp.link;
			if (temp != null) {
				System.out.printf(".");
			}
		}
		System.out.println(")");
	}
}

