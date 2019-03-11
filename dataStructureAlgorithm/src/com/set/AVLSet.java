package com.set;

import com.avltree.AVLTree;
/*
 * 					����----set
 */
public class AVLSet<E extends Comparable<E>> implements Set<E> {
	//�ײ�ʵ�֣�ƽ�����������ȫ����ƽ������������ķ���
	//���ƽ���������V value ��Object���棬ֱ�Ӹ�ֵΪnull����
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
