package com.array;

import java.util.Stack;

public class Test {
	@org.junit.Test
	public void test1() {
		MyArray<Integer> arr = new MyArray<>();
		for(int i=0;i<10;i++) {
			arr.addLast(i);
		}
		System.out.println(arr);
		
		arr.add(5, 100);
		//arr.removeElement(5);
		System.out.println(arr);
		/*arr.addFirst(-1);
		arr.remove(4);
		System.out.println(arr);*/
	}
	
	@org.junit.Test
	public void test2() {
		ArrayStack<Integer> s = new ArrayStack<>();
		for(int i=0;i<5;i++) {
			s.push(i);
		}
		
		System.out.println(s);
		System.out.println(s.pop());
	}
	@org.junit.Test
	public void test3() {
		System.out.println(isValid("12(2(3){s()s}[]"));
		System.out.println(isValid("({[]})"));
	}
	@org.junit.Test
	public void test4() {
		ArrayQueue<Integer> queue = new ArrayQueue<>();
		queue.enqueue(3);
		queue.enqueue(2);
		queue.enqueue(6);
		System.out.println(queue.getFront());
		System.out.println(queue.dequeue());
		System.out.println(queue.getFront());
		System.out.println(queue);
	}
	
	
	
	public boolean isValid(String s) {
		Stack<Character> st = new Stack<>();
		for(int i=0;i<s.length();i++) {
			char c=s.charAt(i);
			if( c== '(' || c == '{' || c == '[') {
				st.push(s.charAt(i));
			}
			else {
				if(c == ')' || c == '}' || c == ']') {
					if(st.isEmpty()) {
						return false;
					}
					char co= st.pop();
					if(co == ')' && c!=')') {
						return false;
					}if(co == '}' && c!='}') {
						return false;
					}if(co == ']' && c!=']') {
						return false;
					}
				}
			}
		}
		return st.isEmpty();
	}
}
