package com.map;
			/*	
			 * 	映射（字典）：key：value键值对	
			 */
public class LinkedListMap<K,V> implements Map<K, V> {
	//底层是带虚拟头节点的链表实现，实现逻辑和链表实现一样，只是Node节点多了一个V类型的value属性
	private class Node{
		public K key;
		public V value;
		public Node next;//下一个节点
		
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
	private Node dummyhead;//虚拟头节点
	private int size;//map集合元素个数
	
	public LinkedListMap() {
		dummyhead=new Node();//虚拟头节点，初始化为一个空的节点
		size=0;
	}
	//返回map集合元素的个数
	@Override
	public int size() {
		return size;
	}
	//判断map集合是否为空
	@Override
	public boolean isEmpty() {
		return size==0;
	}
	//判断map集合是否包含K值为key的节点--时间复杂度O（n）
	@Override
	public boolean contains(K key) {
		Node node = getNode(key);
		if(node ==null) {
			return false;
		}
		return true;
	}
	//返回K键为key的节点（循环遍历）--时间复杂度O（n）
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
	//添加节点node（key，value）--时间复杂度O（n）向链表头部插入节点
	@Override
	public void add(K key, V value) {
		Node node= getNode(key);//--时间复杂度O（n）
		if(node !=null) {
			node.value=value;
		}else {
			//添加操作：把新节点挂在虚拟头节点后面
			dummyhead.next= new Node(key, value, dummyhead.next);//--时间复杂度O（1）
			size++;
		}
	}
	//返回K键为key的节点的V值--时间复杂度O（n）
	@Override
	public V get(K key) {
		Node node =getNode(key);
		return node== null ? null : node.value;
	}
	//更新K键为key的节点的V值--时间复杂度O（n）
	@Override
	public void set(K key, V newValue) {
		Node node = getNode(key);
		if(node == null) {
			throw new RuntimeException("doesn't exist");
		}else {
			node.value=newValue;
		}
	}
	//删除K键为key的节点，并返回节点的V值--时间复杂度O（n）
	@Override
	public V remove(K key) {
		Node prev = getPrevNode(key);
		if(prev.next != null) {
			// 删除操作：找到要删除节点的 前一个节点
			Node delNode= prev.next;
			prev.next=delNode.next;
			delNode.next=null;
			return delNode.value;
		}
		return null;
	}
	//返回K键为key的节点的前一个节点--时间复杂度O（n）
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
