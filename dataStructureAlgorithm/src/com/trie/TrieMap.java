package com.trie;
import java.util.TreeMap;
/*
 * 			Map���ϣ�
 * 		�ײ�ʵ�֣�Trie����˼��
 */
public class TrieMap{
	private class Node{
		TreeMap<Character, Node> next;//��һ���ַ�
		int value;//ÿһ���ַ�����Ȩ�أ�����Ȩ�س䵱isWord��ʶ�����ã�
		public Node(int value) {
			next=new TreeMap<>();
			this.value=value;
		}
		public Node() {
			this(0);//��ʼ�ַ���Ȩ�أ�Ĭ��Ϊ0
		}
	}
	private Node root;//���ڵ�
	private int size;//�ַ�������
	public TrieMap() {
		root =new Node();
	}
	//����map���ַ����ĸ���
	public int size() {
		return size;
	}
	//���һ����Ȩ�ص��ַ���
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
	//���һ����Ȩ�ص��ַ���---�ݹ�ʵ��
	public void insert_R(String word,int value) {
		insert_R(root,word,0,value);
	}
	/*
	 * �ݹ鷽����void insert_R(Node node,String word, int index, int value)
	 * �ݹ���ֹ������
	 * 		���ʱ������ˣ�index == word.length
	 * �ݹ鹫ʽ��
	 * 		void insert_R(node.next.get(c), word, index+1, value)
	 */
	//����nodeΪ���ڵ��Trie�������word���ʵĵ�indexλ�ַ���������Ȩ�أ��ݹ������壩
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
	//���а���prefixǰ׺�ĵ��ʵ�Ȩ�صĺ�
	/*
	 * ˼·��
	 * 		1.���ҵ�����prefixǰ׺�����һ���ڵ㣨prefixǰ׺�����һ���ַ����ڵĽڵ㣩
	 * 		2.������ڵ�Ϊ���ڵ㣬�����ýڵ��µ�����ӳ���
	 * 		3.��ÿһ��ӳ������������нڵ��valueֵ�ĺ�
	 * 		4.����������ĺͽ����ۼ�
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
		return sum(cur);//�ҵ�����prefixǰ׺�����һ���ڵ�
	}
	/*
	 * �ݹ鷽����int sum(Node node)
	 * �ݹ���ֹ������
	 * 		��ǰ�ڵ��е�ӳ���Ϊ0��node.next.size() == 0
	 * 		��ǰ�ĵݹ����ֹ��������ʡ�ԣ���Ϊ��forѭ��������ʱ�����node.next��û��ӳ��ԵĻ�
	 * 		ֱ������ѭ�������ص�ǰ�ڵ��ֵ���͵ݹ���ֹ����һ��
	 * �ݹ鹫ʽ��
	 * 		int sum(node.next.get(c))
	 */
	//����node�ڵ��valueֵ���ݹ�ĺ�����壩
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
