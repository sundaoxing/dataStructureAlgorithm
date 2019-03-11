package com.set;

import com.list.LinkedList;
/*
 * 						Set����
 */
public class LinkedListSet<E> implements Set<E> {
	private LinkedList<E> list;//�ײ������ͷ�ڵ������ʵ��
	
	public LinkedListSet() {
		list= new LinkedList<>();
	}
	//����set������Ԫ�صĸ���
	@Override
	public int size() {
		return list.size();
	}
	//�ж�set�����Ƿ�Ϊ��
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	//���Ԫ��---ʱ�临�Ӷȣ�O��n��ƽ��
	@Override
	public void add(E item) {
		if(!contains(item)) {
			list.addFirst(item);
		}
	}
	//ɾ��Ԫ��---ʱ�临�Ӷȣ�O��n��ƽ��
	@Override
	public void remove(E item) {
		list.removeElement(item);
	}
	//�ж�set�����Ƿ������Ԫ��---ʱ�临�Ӷȣ�O��n��ƽ��
	@Override
	public boolean contains(E item) {
		return list.contains(item);
	}
	
 }
