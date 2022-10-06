package Tree;

class Heap {
	private int heapSize;
	private int itemHeap[];
	//왜 배열일까??
	//이전에도 배열을 이용해서, 크기 순서대로 나열한 적이 있다.
	public Heap() {
		heapSize = 0;
		itemHeap = new int[50];
	}
	// 나누기는 자식으로 간다 곱하기는 부모로 간다
	
	public void insertHeap(int item) {
		int i = ++heapSize;
		//히프에서 삽입 연산은 완전 이진트리 유지를 위해서 노드를 확장.
		while ((i != 1) && (item > itemHeap[i/2])) {
			//현제 위치에서 부모노드와 비교하여 크기 관계를 확인, 
			//관계가 성립하지 않으면, 부모와 삽인 원소의 위치 바꿈
			itemHeap[i] = itemHeap[i/2];
			i /= 2;
		}
		itemHeap[i] = item;
	}

	public int getHeapSize() {
		return this.heapSize;
	}

	public int deleteHeap() {
		int parent, child;
		int item, temp;
		item = itemHeap[1];
		temp = itemHeap[heapSize--];
		parent = 1;
		child = 2;

		while (child <= heapSize) {
			if ((child < heapSize) && (itemHeap[child] < itemHeap[child + 1]))
				child++;
			if (temp >= itemHeap[child])
				break;

			itemHeap[parent] = itemHeap[child];
			parent = child;
			child *= 2;
		}
		itemHeap[parent] = temp;
		return item;

	}

	public void printHeap() {
		System.out.printf("\nHeap>>>");
		for (int i = 1; i <= heapSize; i++)
			System.out.printf("[%d]", itemHeap[i]);
	}

}
