package Tree;

class Heap {
	private int heapSize;
	private int itemHeap[];
	//왜 배열일까??
	public Heap() {
		heapSize = 0;
		itemHeap = new int[50];
	}
	// 나누기는 자식으로 간다 곱하기는 부모로 간다
	public void insertHeap(int item) {
		int i = ++heapSize;
		while ((i != 1) && (item > itemHeap[i/2])) {
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
