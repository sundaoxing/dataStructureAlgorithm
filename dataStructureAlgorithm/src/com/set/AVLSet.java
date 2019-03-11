package com.set;

import com.avltree.AVLTree;
/*
 * 					集合----set
 */
public class AVLSet<E extends Comparable<E>> implements Set<E> {
	//底层实现：平衡二叉树，完全复刻平衡二叉搜索树的方法
	//这里：平衡二叉树的V value 以Object代替，直接赋值为null即可
	private AVLTree<E, Object> avltree;
	public AVLSet() {
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
	public void add(E item) {
		avltree.add(item, null);
	}

	@Override
	public void remove(E item) {
		avltree.remove(item);
	}

	@Override
	public boolean contains(E item) {
		return avltree.contains(item);
	}

}
