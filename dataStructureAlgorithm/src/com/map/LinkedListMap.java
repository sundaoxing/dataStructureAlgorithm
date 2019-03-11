package com.map;
			/*	
			 * 	ӳ�䣨�ֵ䣩��key��value��ֵ��	
			 */
public class LinkedListMap<K,V> implements Map<K, V> {
	//�ײ��Ǵ�����ͷ�ڵ������ʵ�֣�ʵ���߼�������ʵ��һ����ֻ��Node�ڵ����һ��V���͵�value����
	private class Node{
		public K key;
		public V value;
		public Node next;//��һ���ڵ�
		
		public Node(K key,V value,Node next) {
			this.key=key;
			this.value=value;
			this.next=next;
		}
		@SuppressWarnings("unused")
		public Node(K key,V value) {
			this(key,value,null);
		}
		public Node() {
			this(null,null,null);
		}
	}
	private Node dummyhead;//����ͷ�ڵ�
	private int size;//map����Ԫ�ظ���
	
	public LinkedListMap() {
		dummyhead=new Node();//����ͷ�ڵ㣬��ʼ��Ϊһ���յĽڵ�
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
	//�ж�map�����Ƿ����KֵΪkey�Ľڵ�--ʱ�临�Ӷ�O��n��
	@Override
	public boolean contains(K key) {
		Node node = getNode(key);
		if(node ==null) {
			return false;
		}
		return true;
	}
	//����K��Ϊkey�Ľڵ㣨ѭ��������--ʱ�临�Ӷ�O��n��
	private Node getNode(K key) {
		Node cur = dummyhead.next;
		while(cur !=null) {
			if(key.equals(cur.key)) {
				return cur;
			}
			cur=cur.next;
		}
		return null;
	}
	//��ӽڵ�node��key��value��--ʱ�临�Ӷ�O��n��������ͷ������ڵ�
	@Override
	public void add(K key, V value) {
		Node node= getNode(key);//--ʱ�临�Ӷ�O��n��
		if(node !=null) {
			node.value=value;
		}else {
			//��Ӳ��������½ڵ��������ͷ�ڵ����
			dummyhead.next= new Node(key, value, dummyhead.next);//--ʱ�临�Ӷ�O��1��
			size++;
		}
	}
	//����K��Ϊkey�Ľڵ��Vֵ--ʱ�临�Ӷ�O��n��
	@Override
	public V get(K key) {
		Node node =getNode(key);
		return node== null ? null : node.value;
	}
	//����K��Ϊkey�Ľڵ��Vֵ--ʱ�临�Ӷ�O��n��
	@Override
	public void set(K key, V newValue) {
		Node node = getNode(key);
		if(node == null) {
			throw new RuntimeException("doesn't exist");
		}else {
			node.value=newValue;
		}
	}
	//ɾ��K��Ϊkey�Ľڵ㣬�����ؽڵ��Vֵ--ʱ�临�Ӷ�O��n��
	@Override
	public V remove(K key) {
		Node prev = getPrevNode(key);
		if(prev.next != null) {
			// ɾ���������ҵ�Ҫɾ���ڵ�� ǰһ���ڵ�
			Node delNode= prev.next;
			prev.next=delNode.next;
			delNode.next=null;
			return delNode.value;
		}
		return null;
	}
	//����K��Ϊkey�Ľڵ��ǰһ���ڵ�--ʱ�临�Ӷ�O��n��
	private Node getPrevNode(K key) {
		Node prev = dummyhead;
		while(prev.next !=null) {
			if(key.equals(prev.next.key)) {
				return prev;
			}
			prev=prev.next;
		}
		return null;
	}
}
