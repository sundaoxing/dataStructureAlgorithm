package com.trie;
import java.util.TreeMap;
/*
 * 			Map集合：
 * 		底层实现：Trie树的思想
 */
public class TrieMap{
	private class Node{
		TreeMap<Character, Node> next;//下一个字符
		int value;//每一个字符串的权重（利用权重充当isWord标识的作用）
		public Node(int value) {
			next=new TreeMap<>();
			this.value=value;
		}
		public Node() {
			this(0);//初始字符的权重，默认为0
		}
	}
	private Node root;//根节点
	private int size;//字符串个数
	public TrieMap() {
		root =new Node();
	}
	//返回map中字符串的个数
	public int size() {
		return size;
	}
	//添加一个带权重的字符串
	public void insert(String word,int value) {
		Node cur = root;
		for(int i=0;i<word.length();i++) {
			char c = word.charAt(i);
			if(cur.next.get(c) == null) {
				cur.next.put(c, new Node());
			}
			cur = cur.next.get(c);
		}
		cur.value=value;
		size++;
	}
	//添加一个带权重的字符串---递归实现
	public void insert_R(String word,int value) {
		insert_R(root,word,0,value);
	}
	/*
	 * 递归方法：void insert_R(Node node,String word, int index, int value)
	 * 递归终止条件：
	 * 		单词遍历完了：index == word.length
	 * 递归公式：
	 * 		void insert_R(node.next.get(c), word, index+1, value)
	 */
	//向以node为根节点的Trie树中添加word单词的第index位字符，并设置权重（递归宏观语义）
	private void insert_R(Node node,String word, int index, int value) {
		if(index == word.length()) {
			node.value=value;
			size++;
			return;
		}
		char c = word.charAt(index);
		if(node.next.get(c) ==null) {
			node.next.put(c, new Node());
		}
		insert_R(node.next.get(c), word, index+1, value);
	}
	//所有包含prefix前缀的单词的权重的和
	/*
	 * 思路：
	 * 		1.先找到包含prefix前缀的最后一个节点（prefix前缀的最后一个字符所在的节点）
	 * 		2.以这个节点为根节点，遍历该节点下的所有映射对
	 * 		3.对每一个映射对求其中所有节点的value值的和
	 * 		4.对上诉所求的和进行累加
	 */
	public int sum(String prefix) {
		Node cur =root;
		for(int i=0;i<prefix.length();i++) {
			char c = prefix.charAt(i);
			if(cur.next.get(c) == null) {
				return 0;
			}
			cur=cur.next.get(c);
		}
		return sum(cur);//找到包含prefix前缀的最后一个节点
	}
	/*
	 * 递归方法：int sum(Node node)
	 * 递归终止条件：
	 * 		当前节点中的映射对为0：node.next.size() == 0
	 * 		当前的递归的终止条件可以省略，因为在for循环遍历的时候，如果node.next中没有映射对的话
	 * 		直接跳过循环，返回当前节点的值，和递归终止条件一样
	 * 递归公式：
	 * 		int sum(node.next.get(c))
	 */
	//返回node节点的value值（递归的宏观语义）
	private int sum(Node node) {
		if(node.next.size() == 0) {
			return node.value;
		}
		int res=node.value;
		for(char c : node.next.keySet()) {
			res +=sum(node.next.get(c));
		}
		return res;
	}
}
