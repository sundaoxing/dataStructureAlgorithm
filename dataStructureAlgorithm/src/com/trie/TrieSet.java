package com.trie;

import com.set.Set;
/*
 * 			Set���ϣ�
 * 			�ײ�ṹ��ʹ��Trie��ʵ�֣���ֻ�ܴ����ַ��������ظ���
 */
public class TrieSet implements Set<String> {
	private Trie t ;//�ײ����ݽṹ��Trie��
	public TrieSet() {
		t= new Trie();
	}
	//����Set�������ַ����ĸ���
	@Override
	public int size() {
		return t.size();
	}
	//�ж�Set�����Ƿ�Ϊ��
	@Override
	public boolean isEmpty() {
		return t.isEmpty();
	}
	//���һ���ַ�����Set������
	@Override
	public void add(String item) {
		t.add(item);
	}

	@Override
	public void remove(String item) {
	}
	//�ж�Set�������Ƿ����һ���ַ���
	@Override
	public boolean contains(String item) {
		return t.contains(item);
	}

}
