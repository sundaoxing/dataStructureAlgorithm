package com.map;

import com.avltree.AVLTree;
/*
 * 					集合Map：映射（字典）：key：value键值对
 */
public class AVLMap<K extends Comparable<K>,V> implements Map<K, V> {
	//底层是平衡二叉树实现，完全 复刻平衡二叉搜索树的方法
	private AVLTree<K, V> avltree;
	public AVLMap() {
		avltree = new AVLTree<>();
	}
	@Override
	public int size() {
		return avltree.size();
	}

	@Override
	public boolean isEmpty() {
		return avltree.isEmpty();
	}

	@Override
	public boolean contains(K key) {
		return avltree.contains(key);
	}

	@Override
	public void add(K key, V value) {
		avltree.add(key, value);
	}

	@Override
	public V get(K key) {
		return avltree.get(key);
	}

	@Override
	public void set(K key, V newValue) {
		avltree.set(key, newValue);
	}

	@Override
	public V remove(K key) {
		return avltree.remove(key);
	}
	
}
