package com.map;
/*	
 * 				����Map��ӳ�䣨�ֵ䣩��key��value��ֵ��
 * 		�ص㣺
 * 			1.���ܰ����ظ��ļ�K��Vֵ�����ظ���ÿ��K��ֻ�ܶ�Ӧһ��ֵV���ж�����������ͬ����ʹ��"=="����������Ǹ���equals����
 * 			2.�洢���Ǽ�ֵ�ԣ�K��V��
 * 			3.����Key����������Value
 * 		Ӧ�ã�
 * 			1.�ֵ�
 * 			2.���ݿ�
 * 			3.��Ƶͳ��
 */
public class BSTMap<K extends Comparable<K>,V> implements Map<K, V> {
	//�ײ��Ƕ���������ʵ�֣�ʵ���߼��Ͷ���������ʵ��һ����ֻ��node�ж���V���͵�value����
	private class Node{
		public K key;
		public V value;
		public Node left,right;//���Һ��ӽڵ�
		public Node(K key,V value) {
			this.key=key;
			this.value=value;
			this.left=this.right=null;//��ʼ��
		}
	}
	private Node root;//�����������ĸ��ڵ�
	private int size;//map������Ԫ�صĸ���
	
	public BSTMap() {
		this.root=null;//��ʼ��Ϊnull
		size=0;
	}
	//����map����Ԫ�صĸ���
	@Override
	public int size() {
		return size;
	}
	//�ж�map�����Ƿ�Ϊ��
	@Override
	public boolean isEmpty() {
		return size==0;
	}
	//�ж�map�����Ƿ����KֵΪkey�Ľڵ㣺�ݹ�ʵ��--ʱ�临�Ӷ�O��log��n����
	@Override
	public boolean contains(K key) {
		return getNode(root,key) !=null;
	}
	/*
	 * �ݹ麯����getNode(Node node,K key)
	 * �ݹ���ֹ������
	 * 		node ==null
	 * �ݹ鹫ʽ��
	 * 		getNode(node.right, key)
	 * 		getNode(node.left, key)
	 */
	//������nodeΪ���ڵ�Ķ�����������K��Ϊkey�Ľڵ㣨�ݹ������壩--ʱ�临�Ӷ�O��log��n����
	private Node getNode(Node node,K key) {
		if(node ==null) {
			return null;
		}
		if(key.compareTo(node.key) ==0) {
			return node;
		}
		else if(key.compareTo(node.key) >0){
			return getNode(node.right, key);
		}
		else {
			return getNode(node.left, key);
		}
	}
	//��ӽڵ�node��key��value���ݹ�ʵ��--ʱ�临�Ӷ�O��log��n����
	@Override
	public void add(K key, V value) {
		root = add(root,key,value);
	}
	
	//����ڵ�Ϊnode�Ķ���������������µĽڵ㣨key��value�����������µĸ��ڵ㣨�ݹ������壩--ʱ�临�Ӷ�O��log��n����
	private Node add(Node node, K key, V value) {
		if(node==null) {//�ҵ���Ҫ�����λ��
			size++;
			return new Node(key,value);//�����µĸ��ڵ�
		}
		if(key.compareTo(node.key) >0) {
			node.right=add(node.right,key,value);
		}else if(key.compareTo(node.key) <0) {
			node.left=add(node.left, key, value);
		}else {//��key���ʱ������value
			node.value=value;
		}
		return node;
	}
	//����K��Ϊkey�Ľڵ��Vֵ--ʱ�临�Ӷ�O��log��n����
	@Override
	public V get(K key) {
		Node node =getNode(root, key);
		return node ==null? null :node.value;
	}
	//����K��Ϊkey�Ľڵ��Vֵ--ʱ�临�Ӷ�O��log��n����
	@Override
	public void set(K key, V newValue) {
		Node node = getNode(root, key);
		if(node == null) {
			throw new RuntimeException("doesn't exist");
		}
		node.value=newValue;
	}
	//ɾ��K��Ϊkey�Ľڵ㣬�����ؽڵ��Vֵ���ݹ�ʵ��--ʱ�临�Ӷ�O��log��n����
	@Override
	public V remove(K key) {
		Node node = getNode(root, key);
		if(node !=null) {//���ж�K��Ϊkey�Ľڵ��Ƿ����
			root=remove(root, key);
			return node.value;
		}
		return null;//�����ڣ�����null
	}
	//������nodeΪ���ڵ�Ķ��������������ӣ��ݹ������壩
	private Node minNode(Node node) {
		if(node.left ==null) {
			return node;
		}
		return minNode(node.left);
	}
	//ɾ����nodeΪ���ڵ�Ķ�������������С�ڵ㣬�������µĸ��ڵ㣨�ݹ������壩
	private Node removeMin(Node node) {
		if(node.left ==null) {//node�ڵ���Һ��Ӵ���node�ڵ�
			Node rightNode = node.right;
			node.right=null;
			size--;
			return rightNode;
		}
		node.left=removeMin(node.left);
		return node;
	}
	//ɾ����nodeΪ���ڵ�Ķ�����������K��Ϊkey�Ľڵ㣬�������µĸ���������壩 
	private Node remove(Node node, K key) {
		if(node ==null) {//���ڵ�Ϊ�գ�ֱ�ӷ���null��˵������һ���յ�map
			return null;
		}
		if(key.compareTo(node.key) > 0 ) {
			node.right=remove(node.right, key);
		}
		else if(key.compareTo(node.key) <0) {
			node.left=remove(node.left, key);
		}
		else {//key��ȣ��ҵ�Ҫɾ���Ľڵ�
			if(node.left == null) {//����Ϊ�գ��Һ�����
				Node rightNode = node.right;
				node.right=null;
				size--;
				return rightNode;
			}
			if(node.right ==null) {//�Һ���Ϊ�գ�������
				Node leftNode = node.left;
				node.left=null;
				size--;
				return leftNode;
			}
			//���Һ��Ӷ���Ϊ�գ�node�ڵ�ĺ��s��
			Node s = minNode(node.right);
			s.right=removeMin(node.right);
			s.left=node.left;
			node.left=node.right=null;
			return s;
		}
		return node;
	}
	
}
