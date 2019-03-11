package com.array;
		/*			ջ
		 * �ײ�ṹ����̬����ʵ��
		 * ��Ϊ�ײ�ṹ��ͬ��ջʵ�ֵ�ϸ��Ҳ��ͬ��δ�����������ջ��ʵ�֣���ջ����Ҫ����
		 * �����һ���ӿ�
		 * Ӧ�ã� 1.����������Undo���༭���еĳ���������
		 * 		2.ϵͳ����ջ���������ӷ�������
		 * 		3.���ŵ�ƥ��
		 * �ص㣺 1.���Խṹ
		 * 		2.�������������һ���֣����Կ������Ӽ���
		 * 		3.Ԫ���Ǻ���ȳ���last in first out��LIFO
		 * 		4.�������һ�ˣ�ջ�������Ԫ�أ�ͬһ�ˣ�ջ����ȡ��Ԫ�أ�
		 * 	������������������������������������
		 * 	| 1 | 2 | 3 | ��������ջ��
		 *  ������������������������������������
		 *  		  ^-peek(ָ��ջ��)
		 */

import java.util.Stack;

import com.stack.Stack_Interface;

public class ArrayStack<E> implements Stack_Interface<E> {
	MyArray<E> array;//�Զ���Ķ�̬����
	//�Զ����������вι���
	public ArrayStack(int capacity) {
		array = new MyArray<>(capacity);
	}
	//��ʼ����Ϊ10���޲ι���
	public ArrayStack() {
		array =new MyArray<>();
	}
	//ջ��Ԫ�صĸ���--O(1)
	@Override
	public int size() {
		return array.size();
	}
	//�ж�ջ�Ƿ�Ϊ��--O(1)
	@Override
	public boolean isEmpty() {
		return array.isEmpty();
	}
	//��ջ--O(1)-��̯���Ӷȣ��������ݵ�O��n����
	@Override
	public void push(E item) {
		array.addLast(item);
	}
	//��ջ--O(1)�����������ݸ��Ӷȣ�
	@Override
	public E pop() {
		return array.removeLast();
	}
	//��ȡջ����Ԫ��--O(1)
	@Override
	public E peek() {
		return array.getLast();
	}
	//��ȡջ��ǰ������--O(1)
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
	/*ջ��Ӧ��֮һ���ַ�(��{,[���ŵ�ƥ��
	 * ˼�룺 1.��һ���ַ����������������������(,{,[������ջ
	 * 		2.�������������],},)���ͺ�ջ����Ԫ�ؽ��бȽ�
	 * 				ȫ����ȣ�������ƥ����ȷ�����򣬲���ȷ
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
		return st.isEmpty();//�����ǶԳƵģ������������ƥ����ȷ����ô���ջһ���ǿյ�
	}
}
