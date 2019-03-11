package com.list;

/*
 * 		ɾ��������ָ��ֵΪval�����нڵ�
 * ���ʣ��������нڵ㣬�ҵ����������Ľڵ㣬Ȼ��ɾ��
 * ɾ���ڵ㱾�ʣ��ҵ��ýڵ��ǰһ���ڵ㣬�øýڵ��ǰǰ�ڵ����ڵ���������
 */
class ListNode<T> {
	T val;
	ListNode<T> next;

	public ListNode(T x) {
		this.val = x;
	}

	//������ת��������
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
	 * 1.ʹ��headͷ�ڵ� ע�⣺head�ڵ㱾��û��ǰ�̣�Ҫ�����ؽڵ㴦��
	 */
	public <T> ListNode<T> removeElements(ListNode<T> head, T val) {
		/*
		 * ��ͷ�ڵ����������ʱ��ʹ��ѭ������Ϊ���������нڵ�ֵ��Ϊval ������������ ������������ ������������ | val |-> | |-> | |
		 * ������������ ������������ ������������ ^-head
		 * 
		 */
		while (head != null && head.val == val) {
			ListNode<T> delNode = head;
			head = head.next;
			delNode.next = null;
		}
		// ɾ���ڵ���������Ϊ�գ�Ҫ�����ж�
		if (head == null) {
			return null;
		}
		/*
		 * ^-prev��Ҫɾ���ڵ��ǰһ���ڵ㣩 ������������ ������������ ������������ | 1 |-> | val |-> | | ������������ ������������
		 * ������������ ^-head ^-delNode
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
	 * ʹ������ͷ�ڵ�dummyhead: �����head�ڵ㼴Ŀ��ڵ�����������ͳһ��ɾ��˼��
	 */
	public <T> ListNode<T> removeElements_(ListNode<T> head, T val) {
		ListNode<T> dummyhead = new ListNode<T>(val);// ����ͷ�ڵ㣬��ʵ������
		dummyhead.next = head;// ���ӵ������ϣ��䵱ͷ�ڵ�
		ListNode<T> prev = dummyhead;
		/*
		 * ^-prev��Ҫɾ���ڵ��ǰһ���ڵ㣩 ������������ ������������ ������������ | -1 |----> | val |---->| | ������������
		 * ������������ ������������ ^-dummyhead ^-head
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
	//ʹ�õݹ飬ɾ��������ֵΪval�����нڵ�
	/*
	 * �ݹ飺
	 * ������壺����������
	 * 	removeElements_R(head,val)��ɾ����headΪͷ�ڵ������ֵΪvalue�Ľڵ�
	 * 	removeElements_R(head.next,val)ɾ����head.nextΪͷ�ڵ������ֵΪvalue�Ľڵ�
	 * 	....
	 * 	removeElements_R(null,val)ɾ����������ֵΪval�Ľڵ㣬û�У�ֱ�ӷ���null
	 * ΢�����У������ĵ���
	 * 	��removeElements_R(head,val)�����У������Լ��Ĺ��̣�ֻ��ÿ�δ���Ĳ�����ͬ	
	 */
	public <T> ListNode<T> removeElements_R(ListNode<T> head, T val) {
		if(head == null) {
			return null;
		}
		head.next = removeElements_R(head.next, val);
		return head.val==val ? head.next:head;
	}
}
