package com.trie;

import java.util.TreeMap;

/*	
 * 		Trie�����ֵ�������һ�ֺ��ر����״��Ϣ�������ݽṹ�������������ͬ���������Ĺ��ɾ���һ���ֵ䣬
 * 				 	  ����������ٵĽ����ַ����롢�ַ�������
 * ˼�룺�ֵ�����Ƶĺ���˼���ǿռ任ʱ�䣬�������ݽṹ����Ƚ����Ŀռ䡣
 * 		�����������ַ����Ĺ�ͬǰ׺��Common Prefix����Ϊ�洢���ݣ��Դ�����ʡ�洢�ռ䣬����������ʱ�䡣
 * ʱ�临�Ӷȣ�
 * 		1.���ʱ�临�Ӷ�O(m),mΪ����ַ����ĳ���
 * 		2.����ʱ�临�Ӷ�Ϊ O(m)��mΪ����ַ����ĳ���
 * 		3.���ѯ�����뼯���е��ַ����������޹�
 * ���ʣ�
 * 		1.���ڵ㣨Root���������ַ��������ڵ����ÿһ���ڵ㶼������һ���ַ���
 *		2.�Ӹ��ڵ㵽ĳһ�ڵ�·�������������ַ�������������Ϊ�ýڵ��Ӧ���ַ�����
 *		3.����ڵ�������ӽڵ����������ַ�������ͬ��
 *								Root----------------------------���ڵ�
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
 *		Ӧ�ã� 1.�ı�����
 *			 2.��Ƶͳ��
 *			 3.�ַ��������ǰ׺
 *			 4.�ֵ�������
 *			 5.�����������ݽṹ����׺����AC�Զ���
 *
 */
public class Trie {
	/*
	 * �ڲ��ڵ��ࣺ��̬�洢
	 *  ��Ϊ�ڴ洢֮ǰ�����Ǿ��Ѿ�֪���˵��ʣ������е��ַ����Լ��ַ�֮���˳�򣬶���֪��
	 * 	˼�룺����Ҫһ���ڵ�洢һ���ַ���ֻ��Ҫ�洢���ַ�����һ���ڵ��ӳ�伴��
	 *  ����map�ļ���Ψһ�ԣ���֤���ַ����ظ�
	 */
	private class Node{
		/*
		 * ����Ҷ�ӽڵ��޷��жϸýڵ��Ƿ���һ�����ʵĴ�β�ַ�
		 * ���ԣ���Ҫһ����ʶ
		 */
		boolean isWord;//�Ƿ���һ������
		TreeMap<Character,Node> next;//��һ���ַ�
		public Node(boolean isWord) {
			this.isWord=isWord;
			next=new TreeMap<>();
		}
		public Node() {
			this(false);
		}
	}
	
	private Node root;//���ڵ�
	private int size;//Trie���е��ʸ���
	public Trie() {
		root=new Node();//��ʼ���ڵ�Ϊһ���յ�ӳ�䣬û���ַ�
		size=0;
	}
	//����Trie���е��ʸ���
	public int size() {
		return size;
	}
	//�ж�Trie���е��ʸ����Ƿ�Ϊ0
	public boolean isEmpty() {
		return size==0;
	}
	/*
	 * ��ӵ���-------ʱ�临�Ӷ�O��log��m����
	 * 	˼�룺�½��ڵ㣬�ѵ����е�ÿ���ַ�����ڵ���
	 * 	˼·�� 1.ѭ���������ʣ���ȡÿһ���ַ�
	 * 		 2.�Ӹ��ڵ�������жϵ�ǰ�ڵ��ӳ�����Ƿ�����һ���ڵ�
	 * 				��Ϊnull���½�һ��ӳ���ϵ����ǰ�ַ����½��ڵ㣩�������뵱ǰ�ڵ��ӳ����
	 * 				����Ϊnull����������
	 * 		 3.�ڵ��Ƶ���һ���ڵ㣬�ظ����߲���
	 * 		 4.���ʱ����꣬˵�����ʵ�ÿ���ַ�ȫ����ӵ����У��ڵ�ǰ�ڵ��б��Ϊ��һ������
	 */
	public void add(String word) {
		Node cur = root;
		for(int i=0;i<word.length();i++) {
			char c = word.charAt(i);
			if(cur.next.get(c) == null) {//�����е��ַ���δ��ȫ��ӵ����У��������
				cur.next.put(c, new Node());
			}
			cur=cur.next.get(c);
		}
		if(! cur.isWord) {
			cur.isWord=true;
			size++;
		}
	}
	//��ӵ��ʣ��ݹ�ʵ��-------ʱ�临�Ӷ�O��log��m����
	public void add_R(String word) {
		add_R(root,word,0);
	}
	/*
	 * �ݹ鷽����void add_R(Node node, String word,int index)
	 * �ݹ���ֹ������
	 * 		���ʱ������ˣ�index== word.length
	 * �ݹ鹫ʽ��
	 * 		void add_R(node.next.get(c), word, index+1);
	 */
	//����nodeΪ���ڵ���ֵ����У���ӵ���word�ĵ�indexλ�ַ����ݹ�ĺ�����壩
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
	 * �ж��ֵ������Ƿ��������-------ʱ�临�Ӷ�O��log��m����
	 * 	˼�룺�������ʣ��ҵ����ʴ�β�Ľڵ㣬���ݱ�ʶ�ж��Ƿ�����õ���
	 * 	˼·�� 1.ѭ���������ʣ���ȡÿһ���ַ�
	 * 		 2.�Ӹ��ڵ�������жϵ�ǰ�ڵ��ӳ�����Ƿ�����һ���ڵ�
	 * 				��Ϊnull������û�б����꣬�ַ�����������ַ��򲻷����϶�û�иõ��ʣ�����false
	 * 				����Ϊnull����������
	 * 		 3.�ڵ��Ƶ���һ���ڵ㣬�ظ����߲���
	 * 		 4.���ʱ����꣬˵�����ʵ�ÿ���ַ������ֵ����У������ݱ�ʶ�ж��Ƿ���һ������
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
	//�ж��ֵ������Ƿ�������ʣ��ݹ�ʵ��-------ʱ�临�Ӷ�O��log��m����
	public boolean contains_R(String word) {
		return contains_R(root,word,0);
	}
	/*
	 *�ݹ鷽�� ;boolean contains_R(Node node, String word, int index)
	 *�ݹ���ֹ������
	 *		���ʱ������ˣ�index== word.length
	 *�ݹ鹫ʽ��
	 *		boolean contains_R(node.next.get(c), word, index+1)
	 */
	//�ж���nodeΪ���ڵ���ֵ����У�����word�ĵ�indexλ�ַ��Ƿ������У��������ж�ֵ���ݹ������壩
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
	/*��ѯ��Trie�����Ƿ��е�����prefixΪǰ׺��ǰ׺��ѯ��------ʱ�临�Ӷ�O��log��m����
	 * ˼�룺���ж��Ƿ��������˼��һ����ֻ������ø��ݱ�ʶ���ж��Ƿ���һ������
	 * 		��Ϊ����ֻ��ǰ׺�����漰һ�������ĵ���
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
	//�жϴ���ͨ�����.�����ַ������Ƿ���Trie���У����磺.pp,�ݹ�ʵ��
	public boolean search(String word) {
		return match(root,word,0);
	}
	/*
	 * �ݹ鷽��:boolean match(Node node, String word, int index)
	 * �ݹ���ֹ������
	 * 		���ʱ������ˣ�index ==word.length
	 * �ݹ鹫ʽ��boolean match(node.next.get(c), word, index+1)
	 */
	//�ж�����nodeΪ���ڵ��Trie���У�����word�ĵ�index���ַ��Ƿ������У��������ж�ֵ���ݹ������壩
	private boolean match(Node node, String word, int index) {
		if(index ==word.length()) {
			return node.isWord;
		}
		char c = word.charAt(index);
		/*
		 * ˼·��1.�жϵ�ǰ�ַ�c�Ƿ���ͨ���(.):
		 * 		   ���ǣ�������ѯ����contains���������߼�һ��
		 * 	 	   �ǣ�˵����ǰ�ַ�ȫ��ƥ��
		 * 			1.������ǰ�ڵ������е�ӳ���
		 * 			2.�����ж������µ��ַ��Ƿ�ƥ��
		 * 				�ǣ�����true��˵���ҵ���
		 * 				���ǣ�������һ��ӳ��ԣ�ֱ������������ӳ���
		 * 			3�����ȫ���ж��꣬����û���أ�˵��û���ҵ���Ӧ���ַ���ƥ��ʧ�ܣ�����fasle	
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
