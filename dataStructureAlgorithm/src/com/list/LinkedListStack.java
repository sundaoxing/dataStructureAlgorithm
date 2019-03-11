package com.list;

import com.stack.Stack_Interface;
		/*		栈
		 *  基于链表实现
		 * 所有操作时间复杂度：都是O（1），不是均摊时间复杂度
		 * 但是，相比于ArrayStack，时间性能相差不大
		 * 因为：		每次入栈，都需要new一个新的节点，这个操作也很耗时
		 * （因为他需要在堆空间中寻找空间来创造数据）
		 */
public class LinkedListStack<E> implements Stack_Interface<E> {
	private LinkedList<E> list;//自定义的链表
	//无参构造
	public LinkedListStack() {
		list=new LinkedList<>();
	}
	//栈中元素的个数--O(1)
	@Override
	public int size() {
		return list.size();
	}
	//判断栈是否为空--O(1)
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	//入栈--O(1)
	@Override
	public void push(E item) {
		list.addFirst(item);
	}
	//出栈--O(1)
	@Override
	public E pop() {
		if(isEmpty()) {
			throw new RuntimeException("Empty Stack");
		}
		return list.removeFirst();
	}
	//获取栈顶的元素--O(1)
	@Override
	public E peek() {
		return list.getFirst();
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(String.format("LinkedListStack ：  size= %d\n", size()));
		s.append("top[");
		for(int i=0;i<size();i++) {
			s.append(list.get(i)+"->");
		}
		s.append("NULL]");
		return s.toString();
	}
}
