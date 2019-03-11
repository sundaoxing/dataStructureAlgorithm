package com.list;

/*
 * 		删除链表中指定值为val的所有节点
 * 本质：遍历所有节点，找到符合条件的节点，然后删除
 * 删除节点本质：找到该节点的前一个节点，让该节点的前前节点与后节点相连即可
 */
class ListNode<T> {
	T val;
	ListNode<T> next;

	public ListNode(T x) {
		this.val = x;
	}

	//把数组转换成链表
	public ListNode(T[] arr) {
		this.val = arr[0];
		ListNode<T> cur = this;
		for (int i = 1; i < arr.length; i++) {
			cur.next = new ListNode<T>(arr[i]);
			cur = cur.next;
		}
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		ListNode<T> cur = this;
		while(cur != null) {
			s.append(cur.val+"->");
			cur=cur.next;
		}
		s.append("NULL");
		return s.toString();
	}
}

public class Leetcode_203 {
	/*
	 * 1.使用head头节点 注意：head节点本身没有前继，要当作特节点处理
	 */
	public <T> ListNode<T> removeElements(ListNode<T> head, T val) {
		/*
		 * 当头节点满足情况的时候：使用循环是因为：可能所有节点值都为val —————— —————— —————— | val |-> | |-> | |
		 * —————— —————— —————— ^-head
		 * 
		 */
		while (head != null && head.val == val) {
			ListNode<T> delNode = head;
			head = head.next;
			delNode.next = null;
		}
		// 删除节点后，链表可能为空，要单独判断
		if (head == null) {
			return null;
		}
		/*
		 * ^-prev（要删除节点的前一个节点） —————— —————— —————— | 1 |-> | val |-> | | —————— ——————
		 * —————— ^-head ^-delNode
		 */

		ListNode<T> prev = head;
		while (prev.next != null) {
			if (prev.next.val == val) {
				ListNode<T> delNode = prev.next;
				prev.next = delNode.next;
				delNode.next = null;
			} else {
				prev = prev.next;
			}
		}
		return head;
	}

	/*
	 * 使用虚拟头节点dummyhead: 规避了head节点即目标节点的特殊情况：统一了删除思想
	 */
	public <T> ListNode<T> removeElements_(ListNode<T> head, T val) {
		ListNode<T> dummyhead = new ListNode<T>(val);// 虚拟头节点，无实际意义
		dummyhead.next = head;// 连接到链表上，充当头节点
		ListNode<T> prev = dummyhead;
		/*
		 * ^-prev（要删除节点的前一个节点） —————— —————— —————— | -1 |----> | val |---->| | ——————
		 * —————— —————— ^-dummyhead ^-head
		 */
		while (prev.next != null) {
			if (prev.next.val == val) {
				ListNode<T> delNode = prev.next;
				prev.next = delNode.next;
				delNode.next = null;
			} else {
				prev = prev.next;
			}
		}
		return dummyhead.next;
	}
	//使用递归，删除链表中值为val的所有节点
	/*
	 * 递归：
	 * 宏观语义：函数的作用
	 * 	removeElements_R(head,val)：删除以head为头节点链表的值为value的节点
	 * 	removeElements_R(head.next,val)删除以head.next为头节点链表的值为value的节点
	 * 	....
	 * 	removeElements_R(null,val)删除空链表中值为val的节点，没有，直接返回null
	 * 微观运行：函数的调用
	 * 	在removeElements_R(head,val)方法中，调用自己的过程，只是每次传入的参数不同	
	 */
	public <T> ListNode<T> removeElements_R(ListNode<T> head, T val) {
		if(head == null) {
			return null;
		}
		head.next = removeElements_R(head.next, val);
		return head.val==val ? head.next:head;
	}
}
