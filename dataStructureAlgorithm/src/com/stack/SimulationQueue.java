package com.stack;
	/*
	 * 使用两个栈构建队列
	 * 要求：实现队列的add，poll，peek操作
	 */

import java.util.Stack;
			/*		思想
			 * 队列：先进先出
			 * 栈：先进后出
			 * 两个栈，栈a负责进，等到出的时候，把栈a全pop到栈b中，栈b负责出
			 */
public class SimulationQueue<E> {
	protected Stack<E> s_push;//进
	protected Stack<E> s_pop;//出
	
	public SimulationQueue() {
		s_push=new Stack<>();
		s_pop = new Stack<>();
	}
	
	//入队列
	public E add(E item) {
		s_push.push(item);
		return item;
	}
	
	//出队列
	public E poll() {
		if(s_push.isEmpty()) {
			throw new RuntimeException("Empty Queue");
		}
		/*		注意点
		 * 栈b为空时，才把栈a的元素pop进zhanb，然后在出栈
		 * 栈b不为空，优先栈b出栈
		 */
		if(s_pop.isEmpty()) {
			while(!s_push.isEmpty()) {
				s_pop.push(s_push.pop());
			}
		}
		return s_pop.pop();
	}
	
	public E peek() {
		/*		注意点
		 * 栈b为空时，才把栈a的元素pop进栈b，然后在指向栈b栈顶
		 * 栈b不为空，优先指向栈b栈顶
		 */
		if(s_pop.isEmpty()) {
			while(!s_push.isEmpty()) {
				s_pop.push(s_push.pop());
			}
		}
		return s_pop.peek();
	}
}
