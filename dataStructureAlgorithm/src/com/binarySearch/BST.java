package com.binarySearch;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
/*
 * 			��������������ָһ�ÿ������߾����������ʵġ���������
 * 	  ��������
 * 			1.ֻ��һ�����ڵ�
 * 			2.ÿ���ڵ��������������
 * 			3.ÿ���ڵ������һ�����ڵ�
 *    �����������ص㣺
 * 		1.������ڵ�����������գ��������������нڵ��ֵ��С�����ĸ��ڵ��ֵ��
 *		2.������ڵ�����������գ��������������нڵ��ֵ���������ĸ��ڵ��ֵ��
 *		3.����ڵ����������Ҳ�ֱ�Ϊ������������
 *		4.û�м�ֵ��ȵĽڵ㡣�����ﲻ����
 *							  5  ---------�����ڵ�
 *					    /          \ 
 *					   3            6
 *				    /     \           \
 *				   2	   4	       8 -------��Ҷ�ӽڵ�
 *				/   \    /   \        / \
 *           null null null null null   null
 *           
 *      5.�����������һ����������У�����ֱ��������ͶӰ��
 *      6.��̬���ݽṹ     
 *      Ӧ�ã�	1.�������Ӹ��ӵ����ݽṹ�����鼯����ջ�����У�Trie�ֵ�
 *      		2.ƽ���������AVL�������
 *      
 *      E extends Comparable<E>---------������E��Comparable�����࣬���ǿɱȽϵ�
 *      		  ��֤�˴洢��Ԫ���ǿɱȽϵģ���Ҫ����compareTo���������������䷵��ֵ��ȷ����С
 *      ʱ�临�Ӷȣ�
 *      		ƽ��ʱ�临�Ӷȣ�O��log��n����  n:�ڵ����
 *      		���������������ӽ������������У����˻���һ������ֻ���Һ��ӻ������ӣ���O��n��
 */
public class BST<E extends Comparable<E>> {
	//�����������нڵ㣨�ڲ��ࣩ
	private class Node {
		E item;//���屣�������нڵ��е�Ԫ��
		Node left;//����
		Node right;//�Һ���
		//��ʼ���ڵ�
		public Node(E item) {
			this.item = item;
			this.left = this.right = null;
		}
	}

	private Node root;//�����������ĸ��ڵ�
	private int size;//������������Ԫ�صĸ���

	//���췽������ʼ����
	public BST() {
		root = null;
		size = 0;
	}
	//���ض�����������Ԫ�صĸ���
	public int size() {
		return size;
	}
	//�ж϶����������Ƿ�Ϊ��
	public boolean isEmpty() {
		return size == 0;
	}

	/*
	 * 			�����������������µĽڵ㣺ʹ�õݹ�ʵ�� 
	 * ˼�룺 1.�жϸ��ڵ�root�Ƿ�Ϊ�� 
	 * 			�ǣ�ֱ�Ӱ��µĽڵ���ӵ����ڵ�root��
	 * 			�����Խڵ�rootΪ���Ķ����������У�����µĽڵ�
	 * 
	 * public void add(E item) { if (root == null) { root = new Node(item); size++;
	 * } else { add(root, item); } }
	 * 
	 * �ݹ�ʵ�֣� 
	 * �ݹ���ֹ������(��ʱ��node�ڵ���������) 
	 * 1.Ҫ��ӵĽڵ�item == node.item--�������������ͬԪ�ص���������Բ�����
	 * 2.Ҫ��ӵĽڵ�item > node.item ���Ҵ�ʱnode�ڵ���Һ���Ϊ�գ�ֱ�Ӱ��µĽڵ���node.right 
	 * 3.Ҫ��ӵĽڵ�item < node.item ���Ҵ�ʱnode�ڵ������Ϊ�գ�ֱ�Ӱ��µĽڵ���node.left
	 * 
	 * �ݹ鹫ʽ���ݹ�ĺ������ (��ʱ��node�ڵ㲻����������ֻ�ܴ�node�ڵ����һ���ڵ����������Ѱ��)
	 * 1.add(node.right,item)---node���������������� 2.add(node.left,item)----node����������������
	 * 
	 * //�� �Խڵ�nodeΪ���Ķ����������У�����µĽڵ㣨�ݹ�ĺ�����壩 private void add(Node node, E item) { if
	 * (item.equals(node.item)) { return; } else if ((item.compareTo(node.item)) > 0
	 * && node.right == null) { node.right = new Node(item); size++; return; } else
	 * if ((item.compareTo(node.item)) < 0 && node.left == null) { node.left = new
	 * Node(item); size++; return; }
	 * 
	 * if ((item.compareTo(node.item)) > 0) { add(node.right, item); } else {
	 * add(node.left, item); } }
	 * 
	 * �׶ˣ��ݹ���ֹ���������£���ʱ����ֹ������Ȼû�еݹ鵽�ף����һ�������rootΪ�յ����⴦�������û��ͳһ�߼� 
	 * �Ľ���
	 * ��ӵı��ʣ�������root�����ҵ�item�ڵ����ȷλ�ã�Ȼ���item�ڵ�������м���
	 * ���ԣ������µݹ�һ�㣬ֱ���ҵ�node����ǰ�ڵ㣩�����ӻ������Һ��ӣ� ������ӻ������Һ���==null��˵���ҵ��˸�λ�ã��ڲ��뼴��
	 * ��Ϊnull����˵�����нڵ㣬��item�뵱ǰ�ڵ�ȽϺ󣬼�������ݹ�
	 */
	// ����������������Ԫ��----�ݹ�ʵ��---ʱ�临�Ӷȣ�O��log��n����ƽ��
	public void add(E item) {
		root = add(root, item);// �Ӹ��ڵ㿪ʼ
	}

	/*
	 * �ݹ鷽����add(node,item) 
	 * �ݹ���ֹ������ 
	 * 		��ǰnode == null��˵����λ��Ϊ�գ����Բ��� 
	 * �ݹ鹫ʽ��
	 * 		node.right = add(node.right, item)
	 * 		node.left = add(node.left, item);
	 */
	// �� �Խڵ�nodeΪ���Ķ����������У�����µĽڵ㣬�����ش˶������������µĸ����ݹ�ĺ�����壩
	private Node add(Node node, E item) {
		if (node == null) {
			size++;
			return new Node(item);// ���ص�ǰ�������������µĸ�����Ϊ�˺�ԭ���Ķ�������������������
		}
		if (item.compareTo(node.item) > 0) {
			node.right = add(node.right, item);
		} else if (item.compareTo(node.item) < 0) {
			node.left = add(node.left, item);
		}
		return node;// ���ص�ǰ�����������ĸ�����Ϊ�˺�ԭ���Ķ�������������������
	}

	//�ж϶����������Ƿ������Ԫ��----�ݹ�ʵ��---ʱ�临�Ӷȣ�O��log��n����ƽ��
	public boolean contain(E item) {
		return contain(root, item);
	}
	/*
	 * �ݹ�˼�룺����˼��
	 * �ж���nodeΪ���ڵ�Ķ������������Ƿ����itemԪ�أ��������ж�ֵ���ݹ�ĺ�����壩
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

	//ǰ�����(������ȱ���)----�ݹ�ʵ��
	public void preOrder() {
		preOrder(root);
	}

	/*
	 * �ݹ���ֹ������
	 * 		node==null
	 * �ݹ鹫ʽ��
	 * 		preOrder(node.left)--preOrder(node.right)
	 * ����node�ڵ㣨�ݹ�ĺ�����壩
	 * 	���ڵ�-����-�Һ���
	 */
	private void preOrder(Node node) {
		if (node == null) {
			return;
		}
		System.out.println(node.item);
		preOrder(node.left);
		preOrder(node.right);
	}
	
	//ǰ�����---�ǵݹ�ʵ��--����ջʵ��
	/*
	 * ������ͨջģ�⺯������ϵͳջ
	 * 	1.���ڵ���ջ
	 * ѭ���жϣ�ջ�Ƿ�Ϊ��
	 *  ��Ϊ�գ�
	 * 		2.���ڵ��ջ�����ʸ��ڵ�
	 * 		3.�Һ��Ӳ�Ϊ�գ��Һ�����ջ��ע��˳���Ƚ������
	 * 		4.���Ӳ�Ϊ�գ�������ջ
	 *  Ϊ�գ�------��������������ѱ�������
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
	
	//����(������ȱ���)----�ݹ�ʵ��
	public void inOrder() {
		inOrder(root);
	}
	/*
	 * �ݹ���ֹ������
	 * 		node==null
	 * �ݹ鹫ʽ��
	 * 		preOrder(node.left)--preOrder(node.right)
	 * ����node�ڵ㣨�ݹ�ĺ�����壩
	 * 	����-���ڵ�-�Һ���
	 */
	private void inOrder(Node node) {
		if(node == null) {
			return;
		}
		inOrder(node.left);
		System.out.println(node.item);
		inOrder(node.right);
	}
	
	//�������(������ȱ���)----�ݹ�ʵ��
	public void postOrder() {
		postOrder(root);
	}
	/*
	 * �ݹ���ֹ������
	 * 		node==null
	 * �ݹ鹫ʽ��
	 * 		preOrder(node.left)--preOrder(node.right)
	 * ����node�ڵ㣨�ݹ�ĺ�����壩
	 * 	����-�Һ���-���ڵ�
	 */
	private void postOrder(Node node) {
		if(node ==null) {
			return;
		}
		postOrder(node.left);
		postOrder(node.right);
		System.out.println(node.item);
	}
	
	//��α�����������ȱ�����--�ǵݹ�ʵ��
	/*
	 * ��������ʵ�֣����Ŷӣ�һ��һ�����ʣ�˼��ͷǵݹ�ǰ�������ͬ��
	 * 	1.���ڵ������
	 * ѭ���ж϶����Ƿ�Ϊ��
	 * 	��Ϊ�գ�
	 * 		2.���ڵ�����У����ʸ��ڵ�
	 * 		3.���Ӳ�Ϊ�գ���������У�ע��˳���Ƚ��ȳ���
	 * 		4.�Һ��Ӳ�Ϊ�գ��Һ��������
	 *  Ϊ�գ�---------��������������ѱ�������
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
	
	//��ȡ��������������С��Ԫ��----�ݹ�ʵ��
	public E minItem() {
		if(isEmpty()) {
			throw new RuntimeException("Empty");
		}
		return minNode(root).item;
	}
	/*
	 * �ݹ���ֹ������
	 * 			node.left==null------����node�ڵ��Ƕ����������е�����Ľڵ㣨��С�ڵ㣩
	 * �ݹ鹫ʽ��
	 * 			minNode��node.left��
	 */
	//������nodeΪ���ڵ�Ķ��������������ӣ��ݹ������壩
	private Node minNode(Node node) {
		if(node.left == null) {
			return node;
		}
		return minNode(node.left);
	}
	//��ȡ����������������Ԫ��----�ݹ�ʵ��
	public E maxItem() {
		if(isEmpty()) {
			throw new RuntimeException("Empty");
		}
		return maxNode(root).item;
	}
	/*
	 * �ݹ���ֹ������
	 * 			node.right==null------����node�ڵ��Ƕ����������е����ҵĽڵ㣨���ڵ㣩
	 * �ݹ鹫ʽ��
	 * 			maxNode��node.right��
	 */
	//������nodeΪ���ڵ�Ķ������������Һ��ӣ��ݹ������壩
	private Node maxNode(Node node) {
		if(node.right == null) {
			return node;
		}
		return maxNode(node.right);
	}
	//ɾ�������������е���СԪ�أ�������---�ݹ�ʵ��
	public E removeMin() {
		E item = minItem();
		root =removeMin(root);
		return item;
	}
	/*
	 * �ݹ���ֹ������
	 * 			node.left==null------����node�ڵ��Ƕ����������е�����Ľڵ㣨��С�ڵ㣩
	 * �ݹ鹫ʽ��
	 * 			node.left=removeNode��node.left��		
	 */
	//ɾ����nodeΪ���ڵ�Ķ�������������С�ڵ㣬�������µĸ��ڵ㣨������壩
	private Node removeMin(Node node) {
		if(node.left ==null) {//�ҵ�����С�ڵ㣬��ʼɾ������
			/*
			 * ɾ����������node�ڵ���������滻node�ڵ㼴��
			 */
			Node rightNode = node.right;
			node.right=null;
			size--;
			return rightNode;
		}
		node.left=removeMin(node.left);
		return node;
	}
	//ɾ�������������е����Ԫ�أ�������---�ݹ�ʵ��
	public E removeMax() {
		E item = maxItem();
		root=removeMax(root);
		return item;
	}
	/*
	 * �ݹ���ֹ������
	 * 			node.right==null------����node�ڵ��Ƕ����������е����ҵĽڵ㣨���ڵ㣩
	 * �ݹ鹫ʽ��
	 * 			node.right=removeNode��node.right��		
	 */
	//ɾ����nodeΪ���ڵ�Ķ��������������ڵ㣬�������µĸ��ڵ㣨������壩
	private Node removeMax(Node node) {
		if(node.right == null) {//�ҵ������ڵ㣬��ʼɾ������
			/*
			 * ɾ����������node�ڵ���������滻node�ڵ㼴��
			 */
			Node leftNode = node.left;
			node.left=null;
			size--;
			return leftNode;
		}
		node.right=removeMax(node.right);
		return node;
	}
	//ɾ��Ԫ��ֵΪitem�Ľڵ�---�ݹ�ʵ��---ʱ�临�Ӷȣ�O��log��n����ƽ��
	public void remove(E item) {
		root=remove(root,item);
	}
	/*
	 * �ݹ���ֹ������
	 * 			item.compareTo(node.item)==0---node.item=item���ҵ���Ҫɾ����Ԫ�ؽڵ�
	 * �ݹ鹫ʽ��
	 * 			node.right=remove(node.right, item);
	 * 			node.left=remove(node.left, item);
	 */
	//ɾ����nodeΪ���ڵ�Ķ�����������Ԫ��ֵΪitem�Ľڵ㣬�������µĸ���������壩 
	private Node remove(Node node, E item) {
		if(node ==null) {
			return null;
		}
		if(item.compareTo(node.item) == 0) {//�ҵ���ֵΪitem�Ľڵ㣬��ʼɾ������
			/*
			 * ɾ����������3�����
			 * 1.�ڵ�û���������������˼�û����������Ҳû���������������������
			 * 		������������ڵ�
			 * 2.�ڵ�û��������
			 * 		������������ڵ�
			 * 3.�ڵ����������Ҳ��������
			 * 		�ڵ���������ڵ㣨��̣��Ƚڵ�ֵ�����С�ڵ㣬���ڵ��������е���С�ڵ㣩
			 * 		�ڵ��̵������� = �ڵ��������
			 * 		�ڵ��̵�������= �ڵ������������������Ҫɾ���ڵ��̣�
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
