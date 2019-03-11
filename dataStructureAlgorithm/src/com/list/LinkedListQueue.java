package com.list;

import com.queue.Queue_Interface;
		/*		����
		 * ������ͷβָ�������ʵ��
		 * �������β����ӣ��������ͷ������
		 * ע�⣺
		 * 		���ʱ�����tail����ע��ն��е��������Ϊ��ʼʱhead=tail=null��head��Ҫά��
		 * 		����ʱ�����head����ע��ֻʣһ��Ԫ�س��ӵ��������Ϊtail=���һ���ڵ㣬tail��Ҫά��
		 */
public class LinkedListQueue<E> implements Queue_Interface<E> {
	//����ڵ�
	private class Node {
		public E item;//����
		public Node next;//��һ���ڵ�
		//�вι��죬�ڵ�����ݣ��ڵ����һ���ڵ�
		public Node(E item, Node next) {
			this.item = item;
			this.next = next;
		}
		//�вι��죬�ڵ�����ݣ��ڵ����һ���ڵ�Ϊnull
		public Node(E item) {
			this(item, null);
		}
	}
	private Node head,tail;
	private int size;//������Ԫ�ظ���
	//�޲ι���
	public LinkedListQueue() {
		head=tail=null;
		size=0;
	}
	//��ȡ������Ԫ�ظ���
	@Override
	public int size() {
		return size;
	}
	//�ж϶����Ƿ�Ϊ��
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	//��ӣ�ʱ�临�Ӷȣ�O��1��
	@Override
	public void enqueue(E item) {
		/*
		 * ������� :������Ϊ��ʱ������У�headָ����Ҫ����ά��
		 * 	       ������������	 |	������������
		 * 		   |null|	 |	|itme|
		 * 		   ������������	 |	������������
		 *    head-^  ^-tail | head-^  ^-tail����Ҫָ������еĵ�һ��Ԫ�أ���������ӣ�head�Ͳ�Ҫ�ƶ��ˣ�
		 */
		if(tail == null) {
			tail=new Node(item);
			head=tail;
		}
		 /* 
		 * һ�����     ������������	����������
		 * ���	   |  1 |->	| 2	|->null
		 * 		   ������������   ����������
		 *      head-^        ^-tail
		 */   
		else {
			tail.next=new Node(item);
			tail=tail.next;
		}
		size++;
	}
	//���ӣ�ʱ�临�Ӷȣ�O��1��
	@Override
	public E dequeue() {
		if(isEmpty()) {
			throw new RuntimeException("Empty Queue");
		}
		/* 
		 * 	һ�����   ������������	����������
		 * 	����	   |  1 |->	| 2	|->null
		 * 		   ������������   ����������
		 *      head-^        ^-tail
		 */ 
		Node delNode = head;
		head=head.next;
		delNode.next=null;
		/* 
		 * ����������������к󣺶���Ϊ��ʱ��tailָ����Ҫ����ά��һ��	 
		 *   	   ������������			����������
		 * 		   |null | 			| 1	|
		 * 		   ������������   		����������
		 *      head-^ ^-tail��ʵ�ʣ�      ^-tail(����Ϊ�գ���Ȼָ��һ���ڵ㣬ʵ��Ӧ��Ϊ��)
		 */ 
		if(head == null) {
			tail=null;
		}
		size--;
		return delNode.item;
	}
	
	//��ȡ����Ԫ�أ�ʱ�临�Ӷȣ�O��1��
	@Override
	public E getFront() {
		if(isEmpty()) {
			throw new RuntimeException("Empty Queue");
		}
		return head.item;
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(String.format("LinkedListQueue : size= %d\n", size));
		s.append("front[");
		Node cur = head;
		while(cur !=null) {
			s.append(cur.item+"->");
			cur=cur.next;
		}
		s.append("NULL]tail");
		return s.toString();
	}
}
