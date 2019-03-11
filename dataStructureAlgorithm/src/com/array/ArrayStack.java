package com.array;
		/*			栈
		 * 底层结构：动态数组实现
		 * 因为底层结构不同，栈实现的细节也不同，未来方便后续的栈的实现，把栈的主要功能
		 * 抽象成一个接口
		 * 应用： 1.撤销操作：Undo（编辑器中的撤销操作）
		 * 		2.系统程序栈：方法的子方法调用
		 * 		3.括号的匹配
		 * 特点： 1.线性结构
		 * 		2.操作的是数组的一部分（可以看作是子集）
		 * 		3.元素是后进先出（last in first out）LIFO
		 * 		4.从数组的一端（栈顶）添加元素，同一端（栈顶）取出元素，
		 * 	——————————————————
		 * 	| 1 | 2 | 3 | 。。。。栈顶
		 *  ——————————————————
		 *  		  ^-peek(指向栈顶)
		 */

import java.util.Stack;

import com.stack.Stack_Interface;

public class ArrayStack<E> implements Stack_Interface<E> {
	MyArray<E> array;//自定义的动态数组
	//自定义容量的有参构造
	public ArrayStack(int capacity) {
		array = new MyArray<>(capacity);
	}
	//初始容量为10的无参构造
	public ArrayStack() {
		array =new MyArray<>();
	}
	//栈中元素的个数--O(1)
	@Override
	public int size() {
		return array.size();
	}
	//判断栈是否为空--O(1)
	@Override
	public boolean isEmpty() {
		return array.isEmpty();
	}
	//入栈--O(1)-均摊复杂度（包含扩容的O（n））
	@Override
	public void push(E item) {
		array.addLast(item);
	}
	//出栈--O(1)（不包含缩容复杂度）
	@Override
	public E pop() {
		return array.removeLast();
	}
	//获取栈顶的元素--O(1)
	@Override
	public E peek() {
		return array.getLast();
	}
	//获取栈当前的容量--O(1)
	public int getCapacity() {
		return array.getCapacity();
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(String.format("Stack: size=%d , capacity=%d\n",array.size(),array.getCapacity()));
		s.append("[");
		for(int i =0;i<size();i++) {
			s.append(array.get(i));
			if(i != size()-1) {
				s.append(", ");
			}
		}
		s.append("]top");
		return s.toString();
	}
	/*栈的应用之一：字符(，{,[括号的匹配
	 * 思想： 1.对一个字符串遍历，如果遇到左括号(,{,[，就入栈
	 * 		2.如果遇到右括号],},)，就和栈顶的元素进行比较
	 * 				全部相等，则括号匹配正确，否则，不正确
	 */
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
		return st.isEmpty();//括号是对称的，所以如果括号匹配正确，那么最后栈一定是空的
	}
}
