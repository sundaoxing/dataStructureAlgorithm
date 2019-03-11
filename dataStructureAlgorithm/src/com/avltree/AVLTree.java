package com.avltree;
import java.util.ArrayList;
/*
 * 				ƽ���������AVLTree
 * 		����������������---�ص�
 * 		���壺����������+����һ���ڵ㣬�����������ĸ߶Ȳ����1
 * 		�߶ȣ�ÿ���ڵ�ĸ߶Ⱦ���1---null�����ڵ㣩�ĸ߶�Ϊ0
 * 		ƽ�����ӣ�һ���ڵ�����������ĸ߶Ȳleft.height - right.height 
 * 									12��4��1��----------------------------------���ڵ�
 * 						/							\
 * 						8��3��1��						18��2��1��
 * 				/				\			/				\
 * 				5��2��1��			11��1��0��	17��1��0��		null
 * 			/
 * 			4��1��0��----------------------------------��Ҷ�ӽڵ㣩�߶ȣ�1��ƽ�����ӣ�0
 * 		/		\
 * 	null		null	
 */
public class AVLTree<K extends Comparable<K>, V>{
	/*
	 * ��ƽ�������ʵ��Ϊӳ��Map��ʽ
	 */
	private class Node{
		public K key;
		public V value;
		public Node left , right;
		public int height;//�ڵ�߶�
		public Node(K key , V value) {
			this.key=key;
			this.value=value;
			left=right=null;
			height=1;//��ʼ�ڵ�߶�Ϊ1
		}
	}
	private Node root;//AVL���ĸ��ڵ�
	private int size;//AVL���нڵ�ĸ���
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
	//�ж�AVL�����Ƿ��а���ke���Ľڵ�-----�ݹ�ʵ��----ʱ�临�Ӷȣ�O��log��n����
	public boolean contains(K key) {
		return contains(root,key);
	}
	/*
	 * ���ʣ�����
	 * �ݹ鷽����boolean contains(Node node, K key)
	 * �ݹ���ֹ������
	 * 		�ҵ�Ҷ�ӽڵ����һ���ڵ㣺��ǰ�ڵ�==null
	 * �ݹ鹫ʽ��
	 * 		boolean contains(node.right, key)--������������
	 * 		boolean contains(node.left, key)---������������
	 */
	//�ж���nodeΪ���ڵ��AVL�����Ƿ������keyΪ���Ľڵ㣬�������жϽ��(�ݹ�ĺ������)
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
	//��AVL��������µĽڵ�----�ݹ�ʵ��----ʱ�临�Ӷȣ�O��log��n����
	public void add(K key, V value) {
		root = add(root,key,value);
	}
	/*���ʣ�����˼�룺���ҵ�λ�ã��ڰ��½ڵ���뵽��λ��
	 * �ݹ鷽����Node add(Node node, K key, V value)
	 * �ݹ���ֹ������
	 * 		�ҵ�Ҷ�ӽڵ����һ���ڵ㣺node==null
	 * �ݹ鹫ʽ��
	 * 		Node add(node.right, key, value)---�������в���
	 * 		Node add(node.left, key, value)----�������в���
	 */
	//����nodeΪ���ڵ��AVL���в����µĽڵ㣨key��value���������ز����������µĸ��ڵ�
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
		return keepBalanced(node);//���������µĸ��ڵ�
	}
	//���ֶ�����������ƽ��
	private Node keepBalanced(Node node) {
		/*
		 * ����nodeΪnull�����
		 * 		��ɾ�������У����ܻ����nodeΪnull�������Ҫ���⴦��
		 */
		if(node ==null) {
			return null;
		}
		//�����µĽڵ��Ҫά����ǰ�ڵ�ĸ߶ȣ������������ĸ߶����ֵ+��ǰ�ڵ㱾��ĸ߶�1��
		node.height=1+Math.max(getHeight(node.left), getHeight(node.right));
		//����ƽ������
		int balanceFactor = getBalanceFactor(node);
		/*
		 * ����ƽ�⣺
		 * ʲôʱ�򣺵����һ���ڵ�������ܻ�ʧ��
		 * Ϊʲô���������ĸ߶Ȳ�Ҫ̫�����������Ľڵ����൱����������������б����/��
		 * ��ô�ж�ʧ�⣺ƽ�����ӵľ���ֵ >1
		 * ��ô����ͨ����ת��ά������ƽ��
		 * ������ת�������������
		 * 		1.����Ľڵ��ڲ�ƽ��ڵ���������
		 * 		2.����Ľڵ��ڲ�ƽ��ڵ���Ҳ���Ҳ�
		 */
		/*
		 * ��һ�����������Ľڵ��ڲ�ƽ��ڵ��������ࣺLL
		 *   ά�����Բ�ƽ��ڵ�y������ת����ת��������µĸ��ڵ�Ϊx
		 *		              y								x
		 * 			  	 	 / \	˼�룺����		--->	  /   \
		 * 			 		x	T4	˼·��			     z     y
		 * 				   / \		1.x.right=y		   /  \  /   \
		 *�²���Ľڵ�------ z   T3	2.y.left=T3	      T1  T2 T3  T4
		 *|   		 	 / \
		 *������z��������--	T1 T2
		 */
		if(balanceFactor >1 && getBalanceFactor(node.left) >=0) {
			return rightRotate(node);
		}
		/*
		 * * �ڶ������������Ľڵ��ڲ�ƽ��ڵ���Ҳ���ҲࣺRR
		 * 		ά�����Բ�ƽ��ڵ�y������ת����ת��������µĸ��ڵ�Ϊx
		 *		y				˼�룺����		                x
		 * 	   / \				˼·��		  			  /   \ 	
		 * 	  T1  x				1.x.left=y			     y     z
		 * 		 / \			2.y.right=T3			/ \	  / \
		 *		T2  z ---�²���Ľڵ� 					   T1 T2 T3 T4
		 *   	   / \   |
		 *		  T3 T4--������z��������
		 */
		if(balanceFactor <-1 && getBalanceFactor(node.right) <=0) {
			return leftRotate(node);
		}
		/*
		 * * ���������������Ľڵ��ڲ�ƽ��ڵ�������ҲࣺLR
		 *   	ά�����ȶ�y�����ӽ�����ת��ת���LL�ͣ��ڶ�y������ת
		 *		y											y
		 * 	   / \	      	 ˼�룺x������y����		--->	   / \     -->��LL���ڴ���
		 * 	  x	  T4		       ˼·��			     	      z   T4
		 * 	 / \				   1.z.left=x		     / \
		 *  T1  z-----�²���Ľڵ�          2.x.right=T2	      	x  T3
		 *     / \    | 							   / \
		 *	  T2 T3---������z��������                                                    T1  T2
		 */
		if(balanceFactor >1 && getBalanceFactor(node.left) <0) {
			node.left=leftRotate(node.left);
			return rightRotate(node);
		}
		/*
		 * * ���������������Ľڵ��ڲ�ƽ��ڵ�������ҲࣺRL
		 *   	ά�����ȶ�y�����ӽ�����ת��ת���RR�ͣ��ڶ�y������ת
		 *		           y 							 y
		 * 	              / \	 ˼�룺x������y����	 --->	/ \     -->��RR���ڴ���
		 * 	             T1  x	 ˼·��			       T1  z   
		 * 	 	            / \	    1.z.right=x		      / \
		 *�²���Ľڵ�--- 	   z   T4   2.x.left=T3	      	 T2  x  
		 * |         	  / \     							/ \
		 *������z��������-- T2  T3                            T3  T4
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
	//��node�ڵ�����������������µĸ��ڵ�
	private Node leftRotate(Node node) {
		Node rightNode = node.right;
		Node rightleftNode = rightNode.left;
		//����
		rightNode.left=node;
		node.right=rightleftNode;
		//����height
		node.height=1+Math.max(getHeight(node.left),getHeight(node.right));
		rightNode.height=1+Math.max(getHeight(rightNode.left), getHeight(rightNode.right));
		//������ת���µĸ��ڵ�
		return rightNode;
	}
	//��node�ڵ�����������������µĸ��ڵ�
	private Node rightRotate(Node node) {
		Node leftNode = node.left;
		Node leftrigntNode=leftNode.right;
		//����
		leftNode.right=node;
		node.left=leftrigntNode;
		//����height
		node.height=1+Math.max(getHeight(node.left),getHeight(node.right));
		leftNode.height=1+Math.max(getHeight(leftNode.left), getHeight(leftNode.right));
		//�����µĸ��ڵ�
		return leftNode;
	}
	//����node�ڵ��ƽ������
	private int getBalanceFactor(Node node) {
		if(node == null) {
			return 0;//null�ڵ��ƽ������Ϊ0
		}
		return getHeight(node.left)-getHeight(node.right);
	}
	//����node�ڵ�ĸ߶�
	private int getHeight(Node node) {
		if(node ==null) {
			return 0;//null�ڵ�ĸ߶�Ϊ0
		}
		return node.height;
	}
	/*
	 * �жϸ�AVL���Ƿ���һ�Ŷ���������
	 * 	˼�룺���ö�������������������������������key��һ����������У�
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
	//�������------�ݹ�ʵ��
	private void inOrder(Node node, ArrayList<K> keys) {
		if(node == null) {
			return;
		}
		inOrder(node.left, keys);
		keys.add(node.key);
		inOrder(node.right, keys);
	}
	//�жϸ�AVL���Ƿ���һ��ƽ�������
	public boolean isBalanced() {
		return isBalanced(root);
	}
	/*
	 * ���ʣ�ǰ��������ڱ��������жԽڵ��ƽ�����������
	 * 		ÿ���ڵ��ƽ�����ӵľ���ֵ������С�ڵ���1��������������AVL��
	 */
	private boolean isBalanced(Node node) {
		if(node ==null) {
			return true;
		}
		//�ȼ����ڵ�
		int balanceFactor = getBalanceFactor(node);
		if(Math.abs(balanceFactor) >1) {
			return false;
		}
		//�ڼ������������������
		return isBalanced(node.left) && isBalanced(node.right);
	}
	//���ؼ�Ϊkey�Ľڵ��valueֵ��-----------�ݹ�ʵ��
	//key���ڣ�����value��key�����ڣ�����null
	public V get(K key) {
		Node newNode = getNode(root, key);
		return newNode==null?null:newNode.value;
	}
	/*
	 * ���ʣ�����
	 * �ݹ鷽����Node getNode(Node node, K key)
	 * �ݹ����ֹ������
	 * 		�ҵ�Ҷ�ӽڵ����һ���ڵ㣺node=null
	 * �ݹ鹫ʽ��
	 * 		Node getNode(node.right, key)---���������в�
	 * 		Node getNode(node.left, key)----���������в�
	 */
	//������nodeΪ���ڵ��AVL���еģ���Ϊkey�Ľڵ�
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
	//���¼�Ϊkey�Ľڵ��valueֵ-------�ݹ�ʵ��

	public void set(K key, V newValue) {
		Node newNode = getNode(root, key);
		if(newNode == null) {
			throw new RuntimeException("key doesn't exist");
		}
		newNode.value=newValue;
	}
	//ɾ��AVL���м�Ϊkey�Ľڵ㣬�����ؽڵ��valueֵ----�ݹ�ʵ��
	public V remove(K key) {
		/*
		 * ע��һ��С�߼����Ȼ�ȡ�ýڵ㣬�жϸýڵ��Ƿ�Ϊ�գ�����Ӧkey�Ҳ����������
		 */
		Node node = getNode(root, key);
		if(node !=null) {
			root=remove(root, key);
			return node.value;
		}
		return null;//�Ҳ�����Ϊkey�Ľڵ㣬����null
	}
	/*
	 * �ݹ鷽����Node remove(Node node, K key)
	 * �ݹ���ֹ������������Ҷ�ӽڵ����һ���ڵ㣬��node==null
	 * �ݹ鹫ʽ��
	 * 		node.right = remove(node.right, key)
	 * 		node.left = remove(node.left, key)	
	 * ɾ����˼·���Ͷ�����������ɾ������һ����ֻ��Ҫ��ÿһ��������µĸ��ڵ㱣������
	 * 			Ȼ��Ըýڵ���ƽ���������
	 * 	    ������������ҵ��˼�Ϊkey�Ľڵ�node��
	 * 			1.node��������Ϊ��
	 * 				���˼·������������node��Ϊ�µĸ��ڵ�
	 * 			2.node��������Ϊ��
	 * 				���˼·������������node��Ϊ�µĸ��ڵ�
	 * 			3.������������Ϊ��
	 * 				���˼·���ҵ�node�ڵ�ĺ�̽ڵ㣬����node�ڵ�
	 */
	//ɾ����nodeΪ���ڵ��AVL���м�Ϊkey�Ľڵ㣬�����������ĸ��ڵ�
	private Node remove(Node node, K key) {
		if(node ==null) {
			return null;//�Ҳ�����Ϊkey�Ľڵ㣬����null
		}
		/*
		 * ������Щ������ǻ��������ֻ��һ������ᷢ��
		 * ����ʱ��֪����������ᷢ��������Ҫ��ÿ������µ��µĸ��ڵ���ǰ����
		 * ��ɾ��������ɺ�Ҫ���µĸ��ڵ����ƽ�����
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
				 * ע�⣺��ɾ��node�ڵ�ĺ�̣�
				 * 		����ɾ��node�������е���С�ڵ㣨node�ĺ�̣���������removeMin��������
				 * ��ΪremoveMin��������û�жԽڵ��ƽ��������������Ӱ��node��������ƽ����
				 * ����취��
				 * 		����remove�����������������remove����������µĸ��ڵ�����ƽ�⴦��
				 * 		ֻ��Ҫ���ø÷�������
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
	//������NodeΪ���ڵ�Ķ���ƽ��������С�ڵ�--------�ݹ�ʵ��
	private Node minNode(Node node) {
		if(node.left == null) {
			return node;
		}
		return minNode(node.left);
	} 
}
