package com.avltree;
import java.util.ArrayList;
/*
 * 				平衡二叉树：AVLTree
 * 		基础：二叉搜索树---重点
 * 		定义：二分搜索树+任意一个节点，其左右子树的高度差不超过1
 * 		高度：每个节点的高度就是1---null树（节点）的高度为0
 * 		平衡因子：一个节点的左右子树的高度差：left.height - right.height 
 * 									12（4，1）----------------------------------根节点
 * 						/							\
 * 						8（3，1）						18（2，1）
 * 				/				\			/				\
 * 				5（2，1）			11（1，0）	17（1，0）		null
 * 			/
 * 			4（1，0）----------------------------------（叶子节点）高度：1，平衡因子：0
 * 		/		\
 * 	null		null	
 */
public class AVLTree<K extends Comparable<K>, V>{
	/*
	 * 把平衡二叉树实现为映射Map形式
	 */
	private class Node{
		public K key;
		public V value;
		public Node left , right;
		public int height;//节点高度
		public Node(K key , V value) {
			this.key=key;
			this.value=value;
			left=right=null;
			height=1;//初始节点高度为1
		}
	}
	private Node root;//AVL树的根节点
	private int size;//AVL树中节点的个数
	public AVLTree() {
		root = null;
		size=0;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size==0;
	}
	//判断AVL树中是否有包含ke键的节点-----递归实现----时间复杂度：O（log（n））
	public boolean contains(K key) {
		return contains(root,key);
	}
	/*
	 * 本质：查找
	 * 递归方法：boolean contains(Node node, K key)
	 * 递归终止条件：
	 * 		找到叶子节点的下一个节点：当前节点==null
	 * 递归公式：
	 * 		boolean contains(node.right, key)--到右子树中找
	 * 		boolean contains(node.left, key)---到左子树中找
	 */
	//判断以node为根节点的AVL树中是否包含以key为键的节点，并返回判断结果(递归的宏观语义)
	private boolean contains(Node node, K key) {
		if(node == null) {
			return false;
		}
		if(key.compareTo(node.key) == 0) {
			return true;
		}
		else if(key.compareTo(node.key) >0) {
			return contains(node.right, key);
		}
		else {
			return contains(node.left, key);
		}
	}
	//向AVL树中添加新的节点----递归实现----时间复杂度：O（log（n））
	public void add(K key, V value) {
		root = add(root,key,value);
	}
	/*本质：查找思想：先找到位置，在把新节点插入到该位置
	 * 递归方法：Node add(Node node, K key, V value)
	 * 递归终止条件：
	 * 		找到叶子节点的下一个节点：node==null
	 * 递归公式：
	 * 		Node add(node.right, key, value)---右子树中插入
	 * 		Node add(node.left, key, value)----左子树中插入
	 */
	//向以node为根节点的AVL树中插入新的节点（key，value），并返回插入后的树的新的根节点
	private Node add(Node node, K key, V value) {
		if(node ==null) {
			size++;
			return new Node(key,value);
		}
		if(key.compareTo(node.key) >0) {
			node.right=add(node.right, key, value);
		}
		else if(key.compareTo(node.key) <0) {
			node.left=add(node.left, key, value);
		}
		else {
			node.value=value;
		}
		return keepBalanced(node);//返回树的新的根节点
	}
	//保持二叉搜索树的平衡
	private Node keepBalanced(Node node) {
		/*
		 * 处理node为null的情况
		 * 		在删除操作中，可能会出现node为null的情况，要特殊处理
		 */
		if(node ==null) {
			return null;
		}
		//插入新的节点后，要维护当前节点的高度：（左右子树的高度最大值+当前节点本身的高度1）
		node.height=1+Math.max(getHeight(node.left), getHeight(node.right));
		//计算平衡因子
		int balanceFactor = getBalanceFactor(node);
		/*
		 * 保持平衡：
		 * 什么时候：当添加一个节点后，树可能会失衡
		 * 为什么：保持树的高度不要太大，左右子树的节点数相当，不会让树过于倾斜向左/右
		 * 怎么判断失衡：平衡因子的绝对值 >1
		 * 怎么做：通过旋转，维护树的平衡
		 * 怎样旋转：分两种情况：
		 * 		1.插入的节点在不平衡节点的左侧的左侧
		 * 		2.插入的节点在不平衡节点的右侧的右侧
		 */
		/*
		 * 第一种情况：插入的节点在不平衡节点的左侧的左侧：LL
		 *   维护：对不平衡节点y进行旋转，旋转后的树的新的根节点为x
		 *		              y								x
		 * 			  	 	 / \	思想：右旋		--->	  /   \
		 * 			 		x	T4	思路：			     z     y
		 * 				   / \		1.x.right=y		   /  \  /   \
		 *新插入的节点------ z   T3	2.y.left=T3	      T1  T2 T3  T4
		 *|   		 	 / \
		 *或者是z的子树中--	T1 T2
		 */
		if(balanceFactor >1 && getBalanceFactor(node.left) >=0) {
			return rightRotate(node);
		}
		/*
		 * * 第二种情况：插入的节点在不平衡节点的右侧的右侧：RR
		 * 		维护：对不平衡节点y进行旋转，旋转后的树的新的根节点为x
		 *		y				思想：左旋		                x
		 * 	   / \				思路：		  			  /   \ 	
		 * 	  T1  x				1.x.left=y			     y     z
		 * 		 / \			2.y.right=T3			/ \	  / \
		 *		T2  z ---新插入的节点 					   T1 T2 T3 T4
		 *   	   / \   |
		 *		  T3 T4--或者是z的子树中
		 */
		if(balanceFactor <-1 && getBalanceFactor(node.right) <=0) {
			return leftRotate(node);
		}
		/*
		 * * 第三种情况：插入的节点在不平衡节点的左侧的右侧：LR
		 *   	维护：先对y的左孩子进行旋转，转变成LL型，在对y进行旋转
		 *		y											y
		 * 	   / \	      	 思想：x左旋，y右旋		--->	   / \     -->对LL型在处理
		 * 	  x	  T4		       思路：			     	      z   T4
		 * 	 / \				   1.z.left=x		     / \
		 *  T1  z-----新插入的节点          2.x.right=T2	      	x  T3
		 *     / \    | 							   / \
		 *	  T2 T3---或者是z的子树中                                                    T1  T2
		 */
		if(balanceFactor >1 && getBalanceFactor(node.left) <0) {
			node.left=leftRotate(node.left);
			return rightRotate(node);
		}
		/*
		 * * 第四种情况：插入的节点在不平衡节点的左侧的右侧：RL
		 *   	维护：先对y的左孩子进行旋转，转变成RR型，在对y进行旋转
		 *		           y 							 y
		 * 	              / \	 思想：x右旋，y左旋	 --->	/ \     -->对RR型在处理
		 * 	             T1  x	 思路：			       T1  z   
		 * 	 	            / \	    1.z.right=x		      / \
		 *新插入的节点--- 	   z   T4   2.x.left=T3	      	 T2  x  
		 * |         	  / \     							/ \
		 *或者是z的子树中-- T2  T3                            T3  T4
		 */
		if(balanceFactor <-1 && getBalanceFactor(node.right) >0) {
			node.right=rightRotate(node.right);
			return leftRotate(node);
		}
		if(Math.abs(balanceFactor) >1) {
			System.out.println(balanceFactor);
		}
		return node;
	}
	//对node节点进行左旋，并返回新的根节点
	private Node leftRotate(Node node) {
		Node rightNode = node.right;
		Node rightleftNode = rightNode.left;
		//左旋
		rightNode.left=node;
		node.right=rightleftNode;
		//更新height
		node.height=1+Math.max(getHeight(node.left),getHeight(node.right));
		rightNode.height=1+Math.max(getHeight(rightNode.left), getHeight(rightNode.right));
		//返回旋转后新的根节点
		return rightNode;
	}
	//对node节点进行右旋，并返回新的根节点
	private Node rightRotate(Node node) {
		Node leftNode = node.left;
		Node leftrigntNode=leftNode.right;
		//右旋
		leftNode.right=node;
		node.left=leftrigntNode;
		//更新height
		node.height=1+Math.max(getHeight(node.left),getHeight(node.right));
		leftNode.height=1+Math.max(getHeight(leftNode.left), getHeight(leftNode.right));
		//返回新的根节点
		return leftNode;
	}
	//返回node节点的平衡因子
	private int getBalanceFactor(Node node) {
		if(node == null) {
			return 0;//null节点的平衡因子为0
		}
		return getHeight(node.left)-getHeight(node.right);
	}
	//返回node节点的高度
	private int getHeight(Node node) {
		if(node ==null) {
			return 0;//null节点的高度为0
		}
		return node.height;
	}
	/*
	 * 判断该AVL树是否是一颗二叉搜索树
	 * 	思想：利用二叉搜索树的中序遍历（中序遍历：键key是一个排序的序列）
	 */
	public boolean isBST() {
		ArrayList<K> keys = new ArrayList<>(size);
		inOrder(root,keys);
		for(int i=1;i<keys.size();i++) {
			if(keys.get(i-1).compareTo(keys.get(i)) >0) {
				return false;
			}
		}
		return true;
	}
	//中序遍历------递归实现
	private void inOrder(Node node, ArrayList<K> keys) {
		if(node == null) {
			return;
		}
		inOrder(node.left, keys);
		keys.add(node.key);
		inOrder(node.right, keys);
	}
	//判断该AVL树是否是一颗平衡二叉树
	public boolean isBalanced() {
		return isBalanced(root);
	}
	/*
	 * 本质：前序遍历，在遍历过程中对节点的平衡因子做检查
	 * 		每个节点的平衡因子的绝对值都必须小于等于1，这样的树才是AVL树
	 */
	private boolean isBalanced(Node node) {
		if(node ==null) {
			return true;
		}
		//先检查根节点
		int balanceFactor = getBalanceFactor(node);
		if(Math.abs(balanceFactor) >1) {
			return false;
		}
		//在检查左子树，后右子树
		return isBalanced(node.left) && isBalanced(node.right);
	}
	//返回键为key的节点的value值，-----------递归实现
	//key存在，返回value，key不存在，返回null
	public V get(K key) {
		Node newNode = getNode(root, key);
		return newNode==null?null:newNode.value;
	}
	/*
	 * 本质：查找
	 * 递归方法：Node getNode(Node node, K key)
	 * 递归的终止条件：
	 * 		找到叶子节点的下一个节点：node=null
	 * 递归公式：
	 * 		Node getNode(node.right, key)---到右子树中查
	 * 		Node getNode(node.left, key)----到左子树中查
	 */
	//返回以node为根节点的AVL树中的，键为key的节点
	private Node getNode(Node node, K key) {
		if(node == null) {
			return null;
		}
		if(key.compareTo(node.key) == 0) {
			return node;
		}
		else if(key.compareTo(node.key) >0) {
			return getNode(node.right, key);
		}
		else {
			return getNode(node.left, key);
		}
	}
	//更新键为key的节点的value值-------递归实现

	public void set(K key, V newValue) {
		Node newNode = getNode(root, key);
		if(newNode == null) {
			throw new RuntimeException("key doesn't exist");
		}
		newNode.value=newValue;
	}
	//删除AVL树中键为key的节点，并返回节点的value值----递归实现
	public V remove(K key) {
		/*
		 * 注意一个小逻辑：先获取该节点，判断该节点是否为空（即对应key找不到的情况）
		 */
		Node node = getNode(root, key);
		if(node !=null) {
			root=remove(root, key);
			return node.value;
		}
		return null;//找不到键为key的节点，返回null
	}
	/*
	 * 递归方法：Node remove(Node node, K key)
	 * 递归终止条件：遍历到叶子节点的下一个节点，即node==null
	 * 递归公式：
	 * 		node.right = remove(node.right, key)
	 * 		node.left = remove(node.left, key)	
	 * 删除的思路：和二叉搜索树的删除操作一样，只是要把每一种情况的新的根节点保存下来
	 * 			然后对该节点做平衡操作即可
	 * 	    三种情况：（找到了键为key的节点node）
	 * 			1.node的左子树为空
	 * 				解决思路：右子树代替node成为新的根节点
	 * 			2.node的右子树为空
	 * 				解决思路：左子树代替node成为新的根节点
	 * 			3.左右子树都不为空
	 * 				解决思路：找到node节点的后继节点，代替node节点
	 */
	//删除以node为根节点的AVL树中键为key的节点，并返回新树的根节点
	private Node remove(Node node, K key) {
		if(node ==null) {
			return null;//找不到键为key的节点，返回null
		}
		/*
		 * 下面这些情况都是互斥情况，只有一种情况会发生
		 * 但暂时不知道那种情况会发生，所以要把每种情况下的新的根节点提前保存
		 * 在删除操作完成后，要对新的根节点进行平衡操作
		 */
		Node retNode;
		if(key.compareTo(node.key) >0) {
			node.right = remove(node.right, key);
			retNode =node;
		}else if(key.compareTo(node.key) <0) {
			node.left = remove(node.left, key);
			retNode = node;
		}else {
			if(node.left == null) {
				Node rightNode = node.right;
				node.right=null;
				retNode = rightNode;
			}else if (node.right == null) {
				Node leftNode = node.left;
				node.left=null;
				retNode = leftNode;
			}else {
				Node successor = minNode(node.right);
				/*
				 * 注意：（删除node节点的后继）
				 * 		这里删除node右子树中的最小节点（node的后继）不能再用removeMin（）方法
				 * 因为removeMin（）方法没有对节点的平衡做出调整，会影响node右子树的平衡性
				 * 解决办法：
				 * 		复用remove（）方法，我们这个remove（）对最后新的根节点做了平衡处理
				 * 		只需要调用该方法即可
				 */
				Node rightNode = remove(node.right,successor.key);
				successor.left=node.left;
				successor.right = rightNode;
				node.left=node.right=null;
				retNode= successor;
			}
		}
		return keepBalanced(retNode);
	}
	//返回以Node为根节点的二叉平衡树的最小节点--------递归实现
	private Node minNode(Node node) {
		if(node.left == null) {
			return node;
		}
		return minNode(node.left);
	} 
}
