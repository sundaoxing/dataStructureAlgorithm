package com.list;

import com.queue.Queue_Interface;
		/*		队列
		 * 基于有头尾指针的链表实现
		 * 在链表的尾部入队，在链表的头部出队
		 * 注意：
		 * 		入队时（针对tail）：注意空队列的情况，因为初始时head=tail=null，head需要维护
		 * 		出队时（针对head）：注意只剩一个元素出队的情况，因为tail=最后一个节点，tail需要维护
		 */
public class LinkedListQueue<E> implements Queue_Interface<E> {
	//链表节点
	private class Node {
		public E item;//数据
		public Node next;//下一个节点
		//有参构造，节点的数据，节点的下一个节点
		public Node(E item, Node next) {
			this.item = item;
			this.next = next;
		}
		//有参构造，节点的数据，节点的下一个节点为null
		public Node(E item) {
			this(item, null);
		}
	}
	private Node head,tail;
	private int size;//队列中元素个数
	//无参构造
	public LinkedListQueue() {
		head=tail=null;
		size=0;
	}
	//获取队列中元素个数
	@Override
	public int size() {
		return size;
	}
	//判断队列是否为满
	@Override
	public boolean isEmpty() {
		return size == 0;
	}
	//入队：时间复杂度：O（1）
	@Override
	public void enqueue(E item) {
		/*
		 * 特殊情况 :当队列为空时，入队列，head指针需要重新维护
		 * 	       ——————	 |	——————
		 * 		   |null|	 |	|itme|
		 * 		   ——————	 |	——————
		 *    head-^  ^-tail | head-^  ^-tail（都要指向队列中的第一个元素，后面再添加，head就不要移动了）
		 */
		if(tail == null) {
			tail=new Node(item);
			head=tail;
		}
		 /* 
		 * 一般情况     ——————	—————
		 * 入队	   |  1 |->	| 2	|->null
		 * 		   ——————   —————
		 *      head-^        ^-tail
		 */   
		else {
			tail.next=new Node(item);
			tail=tail.next;
		}
		size++;
	}
	//出队：时间复杂度：O（1）
	@Override
	public E dequeue() {
		if(isEmpty()) {
			throw new RuntimeException("Empty Queue");
		}
		/* 
		 * 	一般情况   ——————	—————
		 * 	出队	   |  1 |->	| 2	|->null
		 * 		   ——————   —————
		 *      head-^        ^-tail
		 */ 
		Node delNode = head;
		head=head.next;
		delNode.next=null;
		/* 
		 * 特殊情况：当出队列后：队列为空时，tail指针需要重新维护一下	 
		 *   	   ——————			—————
		 * 		   |null | 			| 1	|
		 * 		   ——————   		—————
		 *      head-^ ^-tail（实际）      ^-tail(队列为空，仍然指向一个节点，实际应该为空)
		 */ 
		if(head == null) {
			tail=null;
		}
		size--;
		return delNode.item;
	}
	
	//获取队首元素：时间复杂度：O（1）
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
