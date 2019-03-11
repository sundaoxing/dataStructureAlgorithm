package com.binarySearch;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
/*
 * 			二分搜索树：是指一棵空树或者具有下列性质的【二叉树】
 * 	  二叉树：
 * 			1.只有一个根节点
 * 			2.每个节点最多有两个孩子
 * 			3.每个节点最多有一个父节点
 *    二分搜索树特点：
 * 		1.若任意节点的左子树不空，则左子树上所有节点的值均小于它的根节点的值；
 *		2.若任意节点的右子树不空，则右子树上所有节点的值均大于它的根节点的值；
 *		3.任意节点的左、右子树也分别为二叉搜索树；
 *		4.没有键值相等的节点。（这里不处理）
 *							  5  ---------》根节点
 *					    /          \ 
 *					   3            6
 *				    /     \           \
 *				   2	   4	       8 -------》叶子节点
 *				/   \    /   \        / \
 *           null null null null null   null
 *           
 *      5.中序遍历后：是一个有序的序列（从竖直方向向下投影）
 *      6.动态数据结构     
 *      应用：	1.构建更加复杂的数据结构：并查集，堆栈，队列，Trie字典
 *      		2.平衡二叉树：AVL，红黑树
 *      
 *      E extends Comparable<E>---------代表泛型E是Comparable的子类，即是可比较的
 *      		  保证了存储的元素是可比较的，需要调用compareTo（）方法，根据其返回值来确定大小
 *      时间复杂度：
 *      		平均时间复杂度：O（log（n））  n:节点个数
 *      		如果序列是有序添加进二叉搜索树中，则退化成一个链表（只有右孩子或者左孩子）：O（n）
 */
public class BST<E extends Comparable<E>> {
	//二叉搜索树中节点（内部类）
	private class Node {
		E item;//具体保存在树中节点中的元素
		Node left;//左孩子
		Node right;//右孩子
		//初始化节点
		public Node(E item) {
			this.item = item;
			this.left = this.right = null;
		}
	}

	private Node root;//二叉搜索树的根节点
	private int size;//二叉搜索树中元素的个数

	//构造方法（初始化）
	public BST() {
		root = null;
		size = 0;
	}
	//返回二叉搜索树中元素的个数
	public int size() {
		return size;
	}
	//判断二叉搜索树是否为空
	public boolean isEmpty() {
		return size == 0;
	}

	/*
	 * 			向二叉搜索树中添加新的节点：使用递归实现 
	 * 思想： 1.判断根节点root是否为空 
	 * 			是：直接把新的节点添加到根节点root后
	 * 			否：向以节点root为根的二叉搜索树中，添加新的节点
	 * 
	 * public void add(E item) { if (root == null) { root = new Node(item); size++;
	 * } else { add(root, item); } }
	 * 
	 * 递归实现： 
	 * 递归终止条件：(此时，node节点满足条件) 
	 * 1.要添加的节点item == node.item--在这里，不考虑相同元素的情况，所以不处理
	 * 2.要添加的节点item > node.item 并且此时node节点的右孩子为空，直接把新的节点变成node.right 
	 * 3.要添加的节点item < node.item 并且此时node节点的左孩子为空，直接把新的节点变成node.left
	 * 
	 * 递归公式：递归的宏观语义 (此时，node节点不满足条件，只能从node节点的下一个节点出发，继续寻找)
	 * 1.add(node.right,item)---node的右子树满不满足 2.add(node.left,item)----node的左子树满不满足
	 * 
	 * //向 以节点node为根的二叉搜索树中，添加新的节点（递归的宏观语义） private void add(Node node, E item) { if
	 * (item.equals(node.item)) { return; } else if ((item.compareTo(node.item)) > 0
	 * && node.right == null) { node.right = new Node(item); size++; return; } else
	 * if ((item.compareTo(node.item)) < 0 && node.left == null) { node.left = new
	 * Node(item); size++; return; }
	 * 
	 * if ((item.compareTo(node.item)) > 0) { add(node.right, item); } else {
	 * add(node.left, item); } }
	 * 
	 * 弊端：递归终止条件：啰嗦，此时的终止条件仍然没有递归到底，而且还包含了root为空的特殊处理情况，没有统一逻辑 
	 * 改进：
	 * 添加的本质：就是在root树中找到item节点的正确位置，然后把item节点插入其中即可
	 * 所以，在向下递归一层，直接找到node（当前节点）的左孩子或者是右孩子， 如果左孩子或者是右孩子==null，说明找到了该位置，在插入即可
	 * 不为null，则说明还有节点，让item与当前节点比较后，继续深入递归
	 */
	// 向二叉搜索树中添加元素----递归实现---时间复杂度：O（log（n））平均
	public void add(E item) {
		root = add(root, item);// 从根节点开始
	}

	/*
	 * 递归方法：add(node,item) 
	 * 递归终止条件： 
	 * 		当前node == null：说明此位置为空，可以插入 
	 * 递归公式：
	 * 		node.right = add(node.right, item)
	 * 		node.left = add(node.left, item);
	 */
	// 向 以节点node为根的二叉搜索树中，添加新的节点，并返回此二叉搜索树的新的根（递归的宏观语义）
	private Node add(Node node, E item) {
		if (node == null) {
			size++;
			return new Node(item);// 返回当前二叉搜索树的新的根：是为了和原来的二叉搜索树连接起来，
		}
		if (item.compareTo(node.item) > 0) {
			node.right = add(node.right, item);
		} else if (item.compareTo(node.item) < 0) {
			node.left = add(node.left, item);
		}
		return node;// 返回当前二叉搜索树的根：是为了和原来的二叉搜索树连接起来，
	}

	//判断二分搜索树是否包含该元素----递归实现---时间复杂度：O（log（n））平均
	public boolean contain(E item) {
		return contain(root, item);
	}
	/*
	 * 递归思想：查找思想
	 * 判断以node为根节点的二叉搜索树中是否包含item元素，并返回判断值（递归的宏观语义）
	 */
	private boolean contain(Node node, E item) {
		if (node == null) {
			return false;
		}
		if ((item.compareTo(node.item)) == 0) {
			return true;
		} else if ((item.compareTo(node.item)) > 0) {
			return contain(node.right, item);
		} else // item.compareTo(node.item) <0
			return contain(node.left, item);
	}

	//前序遍历(深度优先遍历)----递归实现
	public void preOrder() {
		preOrder(root);
	}

	/*
	 * 递归终止条件：
	 * 		node==null
	 * 递归公式：
	 * 		preOrder(node.left)--preOrder(node.right)
	 * 访问node节点（递归的宏观语义）
	 * 	根节点-左孩子-右孩子
	 */
	private void preOrder(Node node) {
		if (node == null) {
			return;
		}
		System.out.println(node.item);
		preOrder(node.left);
		preOrder(node.right);
	}
	
	//前序遍历---非递归实现--借助栈实现
	/*
	 * 利用普通栈模拟函数调用系统栈
	 * 	1.根节点入栈
	 * 循环判断，栈是否为空
	 *  不为空：
	 * 		2.根节点出栈，访问根节点
	 * 		3.右孩子不为空，右孩子入栈（注意顺序，先进后出）
	 * 		4.左孩子不为空，左孩子入栈
	 *  为空：------代表二叉搜索树已遍历完了
	 */
	public void preOrder_() {
		Stack<Node> s = new Stack<>();
		if(root ==null) {
			return;
		}
		s.push(root);
		while(!s.isEmpty()) {
			Node node = s.pop();
			System.out.println(node.item);
			if(node.right !=null) {
				s.push(node.right);
			}
			if(node.left !=null) {
				s.push(node.left);
			}
		}
	}
	
	//中序(深度优先遍历)----递归实现
	public void inOrder() {
		inOrder(root);
	}
	/*
	 * 递归终止条件：
	 * 		node==null
	 * 递归公式：
	 * 		preOrder(node.left)--preOrder(node.right)
	 * 访问node节点（递归的宏观语义）
	 * 	左孩子-根节点-右孩子
	 */
	private void inOrder(Node node) {
		if(node == null) {
			return;
		}
		inOrder(node.left);
		System.out.println(node.item);
		inOrder(node.right);
	}
	
	//后序遍历(深度优先遍历)----递归实现
	public void postOrder() {
		postOrder(root);
	}
	/*
	 * 递归终止条件：
	 * 		node==null
	 * 递归公式：
	 * 		preOrder(node.left)--preOrder(node.right)
	 * 访问node节点（递归的宏观语义）
	 * 	左孩子-右孩子-根节点
	 */
	private void postOrder(Node node) {
		if(node ==null) {
			return;
		}
		postOrder(node.left);
		postOrder(node.right);
		System.out.println(node.item);
	}
	
	//层次遍历（广度优先遍历）--非递归实现
	/*
	 * 借助队列实现，排排队，一个一个访问（思想和非递归前序遍历相同）
	 * 	1.根节点入队列
	 * 循环判断队列是否为空
	 * 	不为空：
	 * 		2.根节点出队列，访问根节点
	 * 		3.左孩子不为空，左孩子入队列（注意顺序，先进先出）
	 * 		4.右孩子不为空，右孩子入队列
	 *  为空：---------代表二叉搜索树已遍历完了
	 */
	public void levelOrder() {
		Queue<Node> q = new LinkedList<>();
		if(root ==null) {
			return;
		}
		q.add(root);
		while(!q.isEmpty()) {
			Node node = q.poll();
			System.out.println(node.item);
			if(node.left !=null) {
				q.add(node.left);
			}
			if(node.right !=null) {
				q.add(node.right);
			}
		}
	}
	
	//获取二叉搜索树中最小的元素----递归实现
	public E minItem() {
		if(isEmpty()) {
			throw new RuntimeException("Empty");
		}
		return minNode(root).item;
	}
	/*
	 * 递归终止条件：
	 * 			node.left==null------代表node节点是二叉搜索树中的最左的节点（最小节点）
	 * 递归公式：
	 * 			minNode（node.left）
	 */
	//返回以node为根节点的二叉搜索树的左孩子（递归宏观语义）
	private Node minNode(Node node) {
		if(node.left == null) {
			return node;
		}
		return minNode(node.left);
	}
	//获取二叉搜索树中最大的元素----递归实现
	public E maxItem() {
		if(isEmpty()) {
			throw new RuntimeException("Empty");
		}
		return maxNode(root).item;
	}
	/*
	 * 递归终止条件：
	 * 			node.right==null------代表node节点是二叉搜索树中的最右的节点（最大节点）
	 * 递归公式：
	 * 			maxNode（node.right）
	 */
	//返回以node为根节点的二叉搜索树的右孩子（递归宏观语义）
	private Node maxNode(Node node) {
		if(node.right == null) {
			return node;
		}
		return maxNode(node.right);
	}
	//删除二叉搜索树中的最小元素，并返回---递归实现
	public E removeMin() {
		E item = minItem();
		root =removeMin(root);
		return item;
	}
	/*
	 * 递归终止条件：
	 * 			node.left==null------代表node节点是二叉搜索树中的最左的节点（最小节点）
	 * 递归公式：
	 * 			node.left=removeNode（node.left）		
	 */
	//删除以node为根节点的二叉搜索树的最小节点，并返回新的根节点（宏观语义）
	private Node removeMin(Node node) {
		if(node.left ==null) {//找到了最小节点，开始删除操作
			/*
			 * 删除操作：用node节点的右子树替换node节点即可
			 */
			Node rightNode = node.right;
			node.right=null;
			size--;
			return rightNode;
		}
		node.left=removeMin(node.left);
		return node;
	}
	//删除二叉搜索树中的最大元素，并返回---递归实现
	public E removeMax() {
		E item = maxItem();
		root=removeMax(root);
		return item;
	}
	/*
	 * 递归终止条件：
	 * 			node.right==null------代表node节点是二叉搜索树中的最右的节点（最大节点）
	 * 递归公式：
	 * 			node.right=removeNode（node.right）		
	 */
	//删除以node为根节点的二叉搜索树的最大节点，并返回新的根节点（宏观语义）
	private Node removeMax(Node node) {
		if(node.right == null) {//找到了最大节点，开始删除操作
			/*
			 * 删除操作：用node节点的左子树替换node节点即可
			 */
			Node leftNode = node.left;
			node.left=null;
			size--;
			return leftNode;
		}
		node.right=removeMax(node.right);
		return node;
	}
	//删除元素值为item的节点---递归实现---时间复杂度：O（log（n））平均
	public void remove(E item) {
		root=remove(root,item);
	}
	/*
	 * 递归终止条件：
	 * 			item.compareTo(node.item)==0---node.item=item，找到了要删除的元素节点
	 * 递归公式：
	 * 			node.right=remove(node.right, item);
	 * 			node.left=remove(node.left, item);
	 */
	//删除以node为根节点的二叉搜索树中元素值为item的节点，并返回新的根（宏观语义） 
	private Node remove(Node node, E item) {
		if(node ==null) {
			return null;
		}
		if(item.compareTo(node.item) == 0) {//找到了值为item的节点，开始删除操作
			/*
			 * 删除操作：分3种情况
			 * 1.节点没有左子树（包含了既没有左子树，也没有右子树这种特殊情况）
			 * 		右子树来代替节点
			 * 2.节点没有右子树
			 * 		左子树来代替节点
			 * 3.节点既有左子树也有右子树
			 * 		节点后继来代替节点（后继：比节点值大的最小节点，即节点右子树中的最小节点）
			 * 		节点后继的左子树 = 节点的左子树
			 * 		节点后继的右子树= 节点的右子树（右子树中要删除节点后继）
			 */
			if(node.left == null) {
				Node rightNode=node.right;
				node.right=null;
				size--;
				return rightNode;
			}
			if(node.right==null) {
				Node leftNode=node.left;
				node.left=null;
				size--;
				return leftNode;
			}
			Node s =minNode(node.right);
			s.right=removeMin(node.right);
			s.left=node.left;
			node.left=node.right=null;
			return s;
		}
		else if(item.compareTo(node.item) >0) {
			node.right=remove(node.right, item);
		}
		else {//item.compareTo(node.item) <0
			node.left=remove(node.left, item);
		}
		return node;
	}
}
