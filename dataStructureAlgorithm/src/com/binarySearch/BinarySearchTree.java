package com.binarySearch;

import java.util.ArrayDeque;
import java.util.Queue;

/*二叉搜索树特点
		 * 根节点：root
		 * 每个节点的键 > 他的左孩子
		 * 每个节点的键 < 他的右孩子
		 * 以左右孩子为根节点的子树仍然为二叉搜索树
		 */
public class BinarySearchTree<V> {
	private BinarySearchNode<V> root;// 根节点
	private int count;// 二叉树中节点个数

	// 初始化
	public BinarySearchTree() {
		root = null;
		count = 0;
	}

	public int getSize() {
		return count;
	}

	public boolean isEmpty() {
		return root == null && count == 0;
	}

	// 根据键，插入值
	public void insert(int key, V value) {
		root = insert(root, key, value);// 初始化时，构建根节点（第一次插入时，确定了root节点的key和value）
	}

	/*
	 * 构建二叉搜索树----递归构建
	 * 	这里包括后面的root节点，第一次传递的都是根节点，后面再次传递的只是二叉搜索树中的一个节点
	 * 只是名字一样而已，不要混淆
	 */
	private BinarySearchNode<V> insert(BinarySearchNode<V> root, int key, V value) {
		// 节点为空时，不论是根节点，左孩子，右孩子，都新建一个节点，并返回他的引用
		if (root == null) {
			count++;
			return new BinarySearchNode<V>(key, value);
		}
		// 插入键>当前节点键，就和当前节点的右孩子比较
		if (key > root.getKey()) {
			root.setRight(insert(root.getRight(), key, value));
		}
		// 插入键<当前节点键，就和当前节点的左孩子比较
		else if (key < root.getKey()) {
			root.setLeft(insert(root.getLeft(), key, value));
		}
		// 插入键==当前节点键，更新当前节点的value
		else {
			root.setValue(value);
		}
		// 返回当前节点
		return root;
	}

	// 判断以root为节点的二叉搜索树是否包含键为key的节点
	public boolean contain(int key) {
		return contain(root, key);
	}

	private boolean contain(BinarySearchNode<V> root, int key) {
		if (root == null) {
			return false;
		}
		//根据二叉搜索树的特性
		if (key > root.getKey()) {
			return contain(root.getRight(), key);
		} else if (key < root.getKey()) {
			return contain(root.getLeft(), key);
		} else {// key == root.getKey()
			return true;
		}
	}

	// 在以root为节点的二叉搜索树中查找并返回键为key的节点的value对象
	public V search(int key) {
		return search(root, key);
	}

	private V search(BinarySearchNode<V> root, int key) {
		if (root == null) {
			return null;
		}
		if (key > root.getKey()) {
			return search(root.getRight(), key);
		} else if (key < root.getKey()) {
			return search(root.getLeft(), key);
		} else {// key == root.getKey()
			return root.getValue();
		}
	}

	// 前序遍历(深度优先遍历)
	public void preOrder() {
		preOrder(root);
	}

	private void preOrder(BinarySearchNode<V> root) {
		if (root != null) {
			System.out.println(root.getKey());
			preOrder(root.getLeft());
			preOrder(root.getRight());
		}
	}

	// 中序遍历(深度优先遍历)
	public void inOrder() {
		inOrder(root);
	}

	private void inOrder(BinarySearchNode<V> root) {
		if (root != null) {
			inOrder(root.getLeft());
			System.out.println(root.getKey());
			inOrder(root.getRight());
		}
	}

	// 后序遍历(深度优先遍历)
	public void postOrder() {
		postOrder(root);
	}

	private void postOrder(BinarySearchNode<V> root) {
		if (root != null) {
			postOrder(root.getLeft());
			postOrder(root.getRight());
			System.out.println(root.getKey());
		}
	}

	// 层序遍历(广度优先遍历)
	/*
	 * 思路：借助队列来实现 1.根节点入队列 循环判断队列是否为空 不为空： 取出队列的队首，拿到队首的value 判断队首的左孩子节点是否为空： 不为空：
	 * 加入队列 判断队首的左孩子节点是否为空： 不为空： 加入队列
	 */
	public void levelOrder() {
		Queue<BinarySearchNode<V>> q = new ArrayDeque<>();
		q.offer(root);
		while (!q.isEmpty()) {
			BinarySearchNode<V> temp = q.poll();
			if (temp.getLeft() != null) {
				q.offer(temp.getLeft());
			}
			if (temp.getRight() != null) {
				q.offer(temp.getRight());
			}
			System.out.println(temp.getValue());
		}
	}

	// 返回最小值的键
	public int getMinimum() {
		if (count != 0) {
			BinarySearchNode<V>node = minimum(root);//从根节点开始向下查找
			return node.getKey();
		}
		return Integer.MIN_VALUE;
	}

	private BinarySearchNode<V> minimum(BinarySearchNode<V> root) {
		/*如果root节点的左孩子为空
		 * 		则说明root节点为当前树的最小节点，直接返回其key键 
		 */
		if (root.getLeft() == null) {
			return root;
		}
		/*如果root节点的左孩子不为空
		 * 		则说明root节点不是当前树的最小节点，从他的左孩子继续向下查找 
		 */
		return minimum(root.getLeft());
	}

	// 返回最大值的键
	public int getMaxmum() {
		if (count != 0) {
			BinarySearchNode<V> node = maxmum(root);
			return node.getKey();
		}
		return Integer.MAX_VALUE;
	}

	private BinarySearchNode<V> maxmum(BinarySearchNode<V> root) {
		if (root.getRight() == null) {
			return root;
		}
		return maxmum(root.getRight());
	}

	// 删除最小节点,并返回新的根节点
	public void removeMin() {
		if (root != null) {
			root = removeMin(root);
		}
	}
	// 删除最小节点,并返回新的根节点
	private BinarySearchNode<V> removeMin(BinarySearchNode<V> root) {
		if (root.getLeft() == null) {//没有左孩子，即代表root节点为当前树的最小节点
			/*判断root有没有右孩子
			 * 		有：返回右孩子的引用
			 * 		没有：返回null，还是它本身，因为初始化为null
			 */
			BinarySearchNode<V> rightNode = root.getRight();
			count--;//删除该节点
			return rightNode;//返回右孩子节点，把右孩子节点当成新的子树根节点
		}
		/*递归：一直查找当前树的左孩子节点
		 * 有左孩子，即代表root节点不是当前树的最小节点，继续查找他的左孩子
		 * 把找到的左孩子的节点删除，把root节点的左孩子指向他的左孩子的右孩子节点
		 */
		root.setLeft(removeMin(root.getLeft()));
		return root;//返回删除后二叉搜索树的根节点
	}

	// 删除最大节点，并返回新的根节点
	public void removeMax() {
		if(root !=null) {
			root =removeMax(root);
		}
	}
	// 删除最大节点，并返回新的根节点
	private BinarySearchNode<V> removeMax(BinarySearchNode<V> root) {
		if(root.getRight() == null) {
			BinarySearchNode<V> leftNode=root.getLeft();
			count--;
			return leftNode;
		}
		root.setRight(removeMax(root.getRight()));
		return root;
	}
	
	//删除任意节点（根据key键），并返回新的根节点
	public void remove(int key) {
		root = remove(root,key);
	}

	private BinarySearchNode<V> remove(BinarySearchNode<V> root, int key) {
		if(root == null) {
			return null;
		}
		if(key > root.getKey()) {
			root.setRight(remove(root.getRight(),key));
			return root;
		}
		else if(key < root.getKey()) {
			root.setLeft(remove(root.getLeft(),key));
			return root;
		}
		else {//key == root.getKey()找到该节点
			if(root.getLeft() == null) {
				BinarySearchNode<V> rightNode = root.getRight();
				count--;
				return rightNode;
			}
			if(root.getRight() == null) {
				BinarySearchNode<V> leftNode = root.getLeft();
				count--;
				return leftNode;
			}
			/*Hubbard Deletion法，删除二叉搜索树中任意一个有左右孩子节点的节点
			 * 当前节点--node    
			 * 关键：找到node的后继节点，即比node的key键大的第一个节点 successor
			 * sucessor一般是node的右子树中的最小节点
			 * 让successor替代node节点
			 * successor.leftnode->node.leftnode
			 * successor.rightnode->minimun(node.rightnode)
			 */
			//root.getRight() != null && root.getLeft() != null
			BinarySearchNode<V>successor =new BinarySearchNode<>(minimum(root.getRight()));
			count++;
			BinarySearchNode<V>node = removeMin(root.getRight()); 
			successor.setRight(node);
			successor.setLeft(root.getLeft());
			count--;
			return successor;
		}
	}
	
}
