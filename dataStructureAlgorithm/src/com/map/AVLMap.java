package com.map;

import com.avltree.AVLTree;
/*
 * 					����Map��ӳ�䣨�ֵ䣩��key��value��ֵ��
 */
public class AVLMap<K extends Comparable<K>,V> implements Map<K, V> {
	//�ײ���ƽ�������ʵ�֣���ȫ ����ƽ������������ķ���
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
