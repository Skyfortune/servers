package Tree;

class BinarySearchTree {
	private TreeNode root = new TreeNode();

	public TreeNode insertKey(TreeNode root, char x) {
		TreeNode p = root;
		TreeNode newNode = new TreeNode();
		newNode.data1 = x;
		newNode.left = null;
		newNode.right = null;
		if (p == null)
			return newNode;
		else if (newNode.data1 < p.data1) {
			p.left = insertKey(p.left, x);
			return p;
		}

		else if (newNode.data1 > p.data1) {
			p.right = insertKey(p.right, x);
			return p;
		}

		else
			return p;
	}

	public void insertBST(char x) {
		root = insertKey(root, x);
	}

	public TreeNode searchBST(char x) {
		TreeNode p = root;
		while (p != null) {
			if (x < p.data1)
				p = p.left;
			else if (x > p.data1)
				p = p.right;
			else
				return p;
		}
		return p;
	}
	//계속 도돌이하는 느낌
	public void inorder(TreeNode root) {
		if (root != null) {
			inorder(root.left);
			System.out.printf("%c", root.data1);
			inorder(root.right);
		}
	}

	public void printBST() {
		inorder(root);
		System.out.println();
	}

}
