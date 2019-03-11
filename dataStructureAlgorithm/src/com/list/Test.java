package com.list;

import java.util.Random;

import com.array.ArrayStack;
import com.stack.Stack_Interface;

public class Test {
	@org.junit.Test
	public void test1() {
		LinkedList<Integer> l = new LinkedList<>();
		for(int i=0;i<5;i++) {
			l.addFirst(i);
			System.out.println(l);
		}
		
		l.add(3, 666);
		System.out.println(l);
		System.out.println(l.get(3));
		l.set(3, 888);
		System.out.println(l);
		System.out.println(l.contains(888));
	}
	
	@org.junit.Test
	public void test2() {
		LinkedListStack<Integer> s = new LinkedListStack<>();
		s.push(1);
		s.push(2);
		s.push(3);
		System.out.println(s);
		while(!s.isEmpty()) {
			System.out.println(s.pop());
		}
	}
	
	@org.junit.Test
	public void test3() {
		int testCount=10000000;
		ArrayStack<Integer> as = new ArrayStack<>();
		double time1 = testStack(as, testCount);
		System.out.println(time1);
		
		LinkedListStack<Integer> ls= new LinkedListStack<>();
		double time2=testStack(ls, testCount);
		System.out.println(time2);
	}
	@org.junit.Test
	public void teat4() {
		LinkedListQueue<Integer> q = new LinkedListQueue<>();
		for(int i=0;i<10;i++) {
			q.enqueue(i);
			System.out.println(q);
			if(i % 3 ==2) {
				q.dequeue();
				System.out.println(q);
			}
		}
	}
	
	private double testStack(Stack_Interface<Integer> s, int testCount) {
		long start = System.nanoTime();
		Random r = new Random();
		for(int i=0;i<testCount;i++) {
			s.push(r.nextInt(Integer.MAX_VALUE));
		}
		for(int i=0;i<testCount;i++) {
			s.pop();
		}
		long end = System.nanoTime();
		return (end-start) / 1000000000.0;
	}
	
	@org.junit.Test
	public void test5() {
		Leetcode_203 l = new Leetcode_203();
		Integer [] arr = {1,2,6,3,4,6};
		ListNode<Integer> head = new ListNode<>(arr);
		System.out.println(head);
		l.removeElements_R(head, 6);
		System.out.println(head);
	}
}
