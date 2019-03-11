package com.list;

import com.stack.Stack_Interface;
		/*		ջ
		 *  ��������ʵ��
		 * ���в���ʱ�临�Ӷȣ�����O��1�������Ǿ�̯ʱ�临�Ӷ�
		 * ���ǣ������ArrayStack��ʱ����������
		 * ��Ϊ��		ÿ����ջ������Ҫnewһ���µĽڵ㣬�������Ҳ�ܺ�ʱ
		 * ����Ϊ����Ҫ�ڶѿռ���Ѱ�ҿռ����������ݣ�
		 */
public class LinkedListStack<E> implements Stack_Interface<E> {
	private LinkedList<E> list;//�Զ��������
	//�޲ι���
	public LinkedListStack() {
		list=new LinkedList<>();
	}
	//ջ��Ԫ�صĸ���--O(1)
	@Override
	public int size() {
		return list.size();
	}
	//�ж�ջ�Ƿ�Ϊ��--O(1)
	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	//��ջ--O(1)
	@Override
	public void push(E item) {
		list.addFirst(item);
	}
	//��ջ--O(1)
	@Override
	public E pop() {
		if(isEmpty()) {
			throw new RuntimeException("Empty Stack");
		}
		return list.removeFirst();
	}
	//��ȡջ����Ԫ��--O(1)
	@Override
	public E peek() {
		return list.getFirst();
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(String.format("LinkedListStack ��  size= %d\n", size()));
		s.append("top[");
		for(int i=0;i<size();i++) {
			s.append(list.get(i)+"->");
		}
		s.append("NULL]");
		return s.toString();
	}
}
