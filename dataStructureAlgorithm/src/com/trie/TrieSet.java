package com.trie;

import com.set.Set;
/*
 * 			Set集合：
 * 			底层结构：使用Trie树实现，但只能处理字符串（不重复）
 */
public class TrieSet implements Set<String> {
	private Trie t ;//底层数据结构：Trie树
	public TrieSet() {
		t= new Trie();
	}
	//返回Set集合中字符串的个数
	@Override
	public int size() {
		return t.size();
	}
	//判断Set集合是否为空
	@Override
	public boolean isEmpty() {
		return t.isEmpty();
	}
	//添加一个字符串到Set集合中
	@Override
	public void add(String item) {
		t.add(item);
	}

	@Override
	public void remove(String item) {
	}
	//判断Set集合中是否包含一个字符串
	@Override
	public boolean contains(String item) {
		return t.contains(item);
	}

}
