package com.stack;
import java.util.Stack;

/*		逆序栈
		 * 	要求：使用递归思想和栈操作实现
		 */
public class ReverseStack<E> {
	protected Stack<E> s;
	public ReverseStack(Stack<E> s) {
		this.s=s;
	}
	public void reverse() {
		if(s.isEmpty()) {
			return;
		}
		E temp = getBottomElement();
		reverse();
		s.push(temp);
	}
	
	private E getBottomElement() {
		E temp = s.pop();
		if(s.isEmpty()) {
			return temp;
		}else {
			E last = getBottomElement();
			s.push(temp);
			return last;
		}
	}
}
