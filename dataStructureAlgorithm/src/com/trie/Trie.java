package com.trie;

import java.util.TreeMap;

/*	
 * 		Trie：（字典树）是一种很特别的树状信息检索数据结构，（多叉树）如同其名，它的构成就像一本字典，
 * 				 	  可以让你快速的进行字符插入、字符串搜索
 * 思想：字典树设计的核心思想是空间换时间，所以数据结构本身比较消耗空间。
 * 		但它利用了字符串的共同前缀（Common Prefix）作为存储依据，以此来节省存储空间，并加速搜索时间。
 * 时间复杂度：
 * 		1.添加时间复杂度O(m),m为添加字符串的长度
 * 		2.搜索时间复杂度为 O(m)，m为最长的字符串的长度
 * 		3.其查询性能与集合中的字符串的数量无关
 * 性质：
 * 		1.根节点（Root）不包含字符，除根节点外的每一个节点都仅包含一个字符；
 *		2.从根节点到某一节点路径上所经过的字符连接起来，即为该节点对应的字符串；
 *		3.任意节点的所有子节点所包含的字符都不相同；
 *								Root----------------------------根节点
 *						    /    |     \
 *						   c	 d	    p
 *						   |	/  \    |
 *				    	   a   o    e   a
 *						   |   |	|   |
 *		  isWord-----------t   g	a   n------------------------isWord
 *									|	|
 *									r	d
 *										|
 *										a-------------------------isWord
 *		应用： 1.文本搜索
 *			 2.词频统计
 *			 3.字符串最长公共前缀
 *			 4.字典序排序
 *			 5.构建其他数据结构：后缀树，AC自动机
 *
 */
public class Trie {
	/*
	 * 内部节点类：动态存储
	 *  因为在存储之前，我们就已经知道了单词，它其中的字符，以及字符之间的顺序，都已知道
	 * 	思想：不需要一个节点存储一个字符，只需要存储该字符和下一个节点的映射即可
	 *  利用map的键的唯一性，保证了字符不重复
	 */
	private class Node{
		/*
		 * 单靠叶子节点无法判断该节点是否是一个单词的词尾字符
		 * 所以，需要一个标识
		 */
		boolean isWord;//是否是一个单词
		TreeMap<Character,Node> next;//下一个字符
		public Node(boolean isWord) {
			this.isWord=isWord;
			next=new TreeMap<>();
		}
		public Node() {
			this(false);
		}
	}
	
	private Node root;//根节点
	private int size;//Trie树中单词个数
	public Trie() {
		root=new Node();//初始根节点为一个空的映射，没有字符
		size=0;
	}
	//返回Trie树中单词个数
	public int size() {
		return size;
	}
	//判断Trie树中单词个数是否为0
	public boolean isEmpty() {
		return size==0;
	}
	/*
	 * 添加单词-------时间复杂度O（log（m））
	 * 	思想：新建节点，把单词中的每个字符存入节点中
	 * 	思路： 1.循环遍历单词，获取每一个字符
	 * 		 2.从根节点出发，判断当前节点的映射中是否有下一个节点
	 * 				若为null：新建一对映射关系（当前字符：新建节点），并存入当前节点的映射中
	 * 				若不为null：不做处理
	 * 		 3.节点移到下一个节点，重复上诉步骤
	 * 		 4.单词遍历完，说明单词的每个字符全部添加到树中，在当前节点中标记为是一个单词
	 */
	public void add(String word) {
		Node cur = root;
		for(int i=0;i<word.length();i++) {
			char c = word.charAt(i);
			if(cur.next.get(c) == null) {//单词中的字符还未完全添加到树中，继续添加
				cur.next.put(c, new Node());
			}
			cur=cur.next.get(c);
		}
		if(! cur.isWord) {
			cur.isWord=true;
			size++;
		}
	}
	//添加单词，递归实现-------时间复杂度O（log（m））
	public void add_R(String word) {
		add_R(root,word,0);
	}
	/*
	 * 递归方法：void add_R(Node node, String word,int index)
	 * 递归终止条件：
	 * 		单词遍历完了：index== word.length
	 * 递归公式：
	 * 		void add_R(node.next.get(c), word, index+1);
	 */
	//向以node为根节点的字典树中，添加单词word的第index位字符（递归的宏观语义）
	private void add_R(Node node, String word,int index) {
		if(index ==word.length()) {
			if(!node.isWord) {
				node.isWord=true;
				size++;
			}
			return;
		}
		char c = word.charAt(index);
		if(node.next.get(c) ==null) {
			node.next.put(c, new Node());
		}
		add_R(node.next.get(c), word, index+1);
	}
	/*
	 * 判断字典树中是否包含单词-------时间复杂度O（log（m））
	 * 	思想：遍历单词，找到单词词尾的节点，根据标识判断是否包含该单词
	 * 	思路： 1.循环遍历单词，获取每一个字符
	 * 		 2.从根节点出发，判断当前节点的映射中是否有下一个节点
	 * 				若为null：单词没有遍历完，字符序个单词中字符序不符，肯定没有该单词，返回false
	 * 				若不为null：不做处理
	 * 		 3.节点移到下一个节点，重复上诉步骤
	 * 		 4.单词遍历完，说明单词的每个字符都在字典树中，根根据标识判断是否是一个单词
	 */
	public boolean contains(String word) {
		Node cur = root;
		for(int i=0;i<word.length();i++) {
			char c = word.charAt(i);
			if(cur.next.get(c) == null) {
				return false;
			}
			cur = cur.next.get(c);
		}
		return cur.isWord;
	}
	//判断字典树中是否包含单词，递归实现-------时间复杂度O（log（m））
	public boolean contains_R(String word) {
		return contains_R(root,word,0);
	}
	/*
	 *递归方法 ;boolean contains_R(Node node, String word, int index)
	 *递归终止条件：
	 *		单词遍历完了：index== word.length
	 *递归公式：
	 *		boolean contains_R(node.next.get(c), word, index+1)
	 */
	//判断以node为根节点的字典树中，单词word的第index位字符是否在其中，并返回判断值（递归宏观语义）
	private boolean contains_R(Node node, String word, int index) {
		if(index == word.length()) {
			return node.isWord;
		}
		char c = word.charAt(index);
		if(node.next.get(c) == null) {
			return false;
		}
		return contains_R(node.next.get(c), word, index+1);
		
	}
	/*查询在Trie树中是否有单词以prefix为前缀（前缀查询）------时间复杂度O（log（m））
	 * 思想：与判断是否包含单词思想一样，只是最后不用根据标识来判断是否是一个单词
	 * 		因为，他只是前缀，不涉及一个完整的单词
	 */
	public boolean isPrefix(String prefix) {
		Node cur =root;
		for(int i=0;i<prefix.length();i++) {
			char c = prefix.charAt(i);
			if(cur.next.get(c) == null) {
				return false;
			}
			cur= cur.next.get(c);
		}
		return true;
	}
	//判断带有通配符（.）的字符串，是否在Trie树中，例如：.pp,递归实现
	public boolean search(String word) {
		return match(root,word,0);
	}
	/*
	 * 递归方法:boolean match(Node node, String word, int index)
	 * 递归终止条件：
	 * 		单词遍历完了：index ==word.length
	 * 递归公式：boolean match(node.next.get(c), word, index+1)
	 */
	//判断在以node为根节点的Trie树中，单词word的第index个字符是否在其中，并返回判断值（递归宏观语义）
	private boolean match(Node node, String word, int index) {
		if(index ==word.length()) {
			return node.isWord;
		}
		char c = word.charAt(index);
		/*
		 * 思路：1.判断当前字符c是否是通配符(.):
		 * 		   不是：正常查询，与contains（）方法逻辑一致
		 * 	 	   是：说明当前字符全都匹配
		 * 			1.遍历当前节点中所有的映射对
		 * 			2.依次判断其余下的字符是否匹配
		 * 				是：返回true，说明找到了
		 * 				不是：继续下一个映射对，直到遍历完所有映射对
		 * 			3。如果全部判断完，程序没返回，说明没有找到对应的字符，匹配失败，返回fasle	
		 */
		if(c != '.') {
			if(node.next.get(c) == null) {
				return false;
			}
			return match(node.next.get(c), word, index+1);
		}else {
			for(Character nextChar:node.next.keySet()) {
				if(match(node.next.get(nextChar), word, index+1)) {
					return true;
				}
			}
			return false;
		}
	}
}
