package com.stack;
import java.util.Stack;

/*		����ջ
		 * 	Ҫ��ʹ�õݹ�˼���ջ����ʵ��
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
