package com.set;

import com.list.LinkedList;
/*
 * 						Set集合
 */
public class LinkedListSet<E> implements Set<E> {
	private LinkedList<E> list;//底层带虚拟头节点的链表实现
	
	public LinkedListSet() {
		list= new LinkedList<>();
	}
	//返回set集合中元素的个数
	@Override
	public int size() {
		return list.size();
	}
	//判断set集合是否为空
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	//添加元素---时间复杂度：O（n）平均
	@Override
	public void add(E item) {
		if(!contains(item)) {
			list.addFirst(item);
		}
	}
	//删除元素---时间复杂度：O（n）平均
	@Override
	public void remove(E item) {
		list.removeElement(item);
	}
	//判断set集合是否包含该元素---时间复杂度：O（n）平均
	@Override
	public boolean contains(E item) {
		return list.contains(item);
	}
	
 }
