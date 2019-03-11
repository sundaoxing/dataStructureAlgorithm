package com.map;
/*	
 * 				集合Map：映射（字典）：key：value键值对
 * 		特点：
 * 			1.不能包含重复的键K，V值可以重复；每个K键只能对应一个值V，判断两个对象相同不是使用"=="运算符，而是根据equals方法
 * 			2.存储的是键值对（K，V）
 * 			3.根据Key键，来查找Value
 * 		应用：
 * 			1.字典
 * 			2.数据库
 * 			3.词频统计
 */
public class BSTMap<K extends Comparable<K>,V> implements Map<K, V> {
	//底层是二叉搜索树实现，实现逻辑和二叉搜索树实现一样，只是node中多了V类型的value属性
	private class Node{
		public K key;
		public V value;
		public Node left,right;//左右孩子节点
		public Node(K key,V value) {
			this.key=key;
			this.value=value;
			this.left=this.right=null;//初始化
		}
	}
	private Node root;//二叉搜索树的根节点
	private int size;//map集合中元素的个数
	
	public BSTMap() {
		this.root=null;//初始化为null
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
	//判断map集合是否包含K值为key的节点：递归实现--时间复杂度O（log（n））
	@Override
	public boolean contains(K key) {
		return getNode(root,key) !=null;
	}
	/*
	 * 递归函数：getNode(Node node,K key)
	 * 递归终止条件：
	 * 		node ==null
	 * 递归公式：
	 * 		getNode(node.right, key)
	 * 		getNode(node.left, key)
	 */
	//返回以node为根节点的二叉搜索树中K键为key的节点（递归宏观语义）--时间复杂度O（log（n））
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
	//添加节点node（key，value）递归实现--时间复杂度O（log（n））
	@Override
	public void add(K key, V value) {
		root = add(root,key,value);
	}
	
	//向根节点为node的二叉搜索树中添加新的节点（key，value），并返回新的根节点（递归宏观语义）--时间复杂度O（log（n））
	private Node add(Node node, K key, V value) {
		if(node==null) {//找到了要插入的位置
			size++;
			return new Node(key,value);//返回新的根节点
		}
		if(key.compareTo(node.key) >0) {
			node.right=add(node.right,key,value);
		}else if(key.compareTo(node.key) <0) {
			node.left=add(node.left, key, value);
		}else {//当key相等时，更新value
			node.value=value;
		}
		return node;
	}
	//返回K键为key的节点的V值--时间复杂度O（log（n））
	@Override
	public V get(K key) {
		Node node =getNode(root, key);
		return node ==null? null :node.value;
	}
	//更新K键为key的节点的V值--时间复杂度O（log（n））
	@Override
	public void set(K key, V newValue) {
		Node node = getNode(root, key);
		if(node == null) {
			throw new RuntimeException("doesn't exist");
		}
		node.value=newValue;
	}
	//删除K键为key的节点，并返回节点的V值，递归实现--时间复杂度O（log（n））
	@Override
	public V remove(K key) {
		Node node = getNode(root, key);
		if(node !=null) {//先判断K键为key的节点是否存在
			root=remove(root, key);
			return node.value;
		}
		return null;//不存在，返回null
	}
	//返回以node为根节点的二叉搜索树的左孩子（递归宏观语义）
	private Node minNode(Node node) {
		if(node.left ==null) {
			return node;
		}
		return minNode(node.left);
	}
	//删除以node为根节点的二叉搜索树的最小节点，并返回新的根节点（递归宏观语义）
	private Node removeMin(Node node) {
		if(node.left ==null) {//node节点的右孩子代替node节点
			Node rightNode = node.right;
			node.right=null;
			size--;
			return rightNode;
		}
		node.left=removeMin(node.left);
		return node;
	}
	//删除以node为根节点的二叉搜索树中K键为key的节点，并返回新的根（宏观语义） 
	private Node remove(Node node, K key) {
		if(node ==null) {//根节点为空，直接返回null，说明这是一个空的map
			return null;
		}
		if(key.compareTo(node.key) > 0 ) {
			node.right=remove(node.right, key);
		}
		else if(key.compareTo(node.key) <0) {
			node.left=remove(node.left, key);
		}
		else {//key相等，找到要删除的节点
			if(node.left == null) {//左孩子为空，右孩子上
				Node rightNode = node.right;
				node.right=null;
				size--;
				return rightNode;
			}
			if(node.right ==null) {//右孩子为空，左孩子上
				Node leftNode = node.left;
				node.left=null;
				size--;
				return leftNode;
			}
			//左右孩子都不为空，node节点的后继s上
			Node s = minNode(node.right);
			s.right=removeMin(node.right);
			s.left=node.left;
			node.left=node.right=null;
			return s;
		}
		return node;
	}
	
}
