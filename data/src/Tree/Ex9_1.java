package Tree;

class Ex9_1 {
	public static void main(String args[]){
		LinkedTree T = new LinkedTree();
		
		TreeNode n7 = T.makeBT(null, 'D', null);
		TreeNode n6 = T.makeBT(null, 'C', null);
		TreeNode n5 = T.makeBT(null, 'B', null);
		TreeNode n4 = T.makeBT(null, 'A', null);
		TreeNode n3 = T.makeBT(n6, '/', n7);
		TreeNode n2 = T.makeBT(n4, '*', n5);
		TreeNode n1 = T.makeBT(n2, '-', n3);
		
		System.out.printf("\n Preorder: ");
		T.preorder(n1);
		//위에서 좌측 순서대로 나온다.
		System.out.printf("\n Inorder: ");
		T.inorder(n1);
		//아래부터 좌측 순서대로 나온다
		System.out.printf("\n Postorder: ");
		T.postorder(n1);
		//아래 다 표현뒤 부모 표현 하는 방식
	}

}



//모양은 대충 이러하다
//				-
//		*				/
//	A		B		C		D
