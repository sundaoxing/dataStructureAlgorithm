package com.stack;

import java.util.Stack;

public class Test {
	@org.junit.Test
	public void test1() {
		Min_Stack s = new Min_Stack();
		s.push(6);
		System.out.println(s.getMin());
		s.push(4);
		System.out.println(s.getMin());
		s.push(5);
		System.out.println(s.getMin());
		s.push(1);
		System.out.println(s.getMin());
		s.push(2);
		System.out.println(s.getMin());
		s.push(1);
		System.out.println(s.getMin());
	}
	@org.junit.Test
	public void test2() {
		SimulationQueue<Integer> queue= new SimulationQueue<>();
		queue.add(1);
		queue.add(2);
		queue.add(3);
		System.out.println(queue.poll());
		System.out.println(queue.peek());
	}
	@org.junit.Test
	public void test3() {
		Stack<Integer> s = new Stack<>();
		s.push(1);
		s.push(2);
		s.push(3);
		ReverseStack<Integer> rs = new ReverseStack<>(s);
		rs.reverse();
		System.out.println(s.peek());
	}
	
}
