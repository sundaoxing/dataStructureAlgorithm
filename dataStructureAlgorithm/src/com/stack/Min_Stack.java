package com.stack;

import java.util.Arrays;
import java.util.EmptyStackException;

class MyStack<E>{
	protected Object [] elementData;//Ԫ��
	protected int elementCount;//Ԫ�ظ���
	
	public MyStack() {
		this(10);
	}
	
	public MyStack(int initCapacity) {
		elementData=new Object[initCapacity];
	}
	
	//��ջ
	public E push(E item) {
		addElement(item);
		return item;
	}

	private void addElement(E item) {
		ensureCapacityHelper(elementCount+1);//�������
		elementData[elementCount++]=item;
	}

	private void ensureCapacityHelper(int minCapacity) {
		if(minCapacity-elementData.length >0) {
			grow(minCapacity);//����
		}
	}

	private void grow(int minCapacity) {
		int newCapacity=elementData.length*2;
		elementData = Arrays.copyOf(elementData, newCapacity);//����
	}
	
	//��ջ������ջ��Ԫ�ز�ɾ����
	public E pop() {
		int len=size();
		E obj =peek();
		removeElement(len-1);
		return obj;
	}

	//�Ƴ�ָ��������Ԫ��
	private void removeElement(int index) {
		if(index >= elementCount) {
			throw new ArrayIndexOutOfBoundsException(index + " >= " +
                    elementCount);
		}
		if(index <0) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		int j=elementCount-index-1;//������j=0
		if(j>0) {
			System.arraycopy(elementData, index+1, elementData, index, j);
		}
		elementCount--;
		elementData[elementCount]=null;
	}

	//����ջ����Ԫ�أ���ɾ����
	@SuppressWarnings("unchecked")
	public E peek() {
		int len=size();
		if(len == 0) {
			throw new EmptyStackException();
		}
		if(len-1 >= elementCount) {
			throw new ArrayIndexOutOfBoundsException((len-1) + " >= " + elementCount);
		}
		return (E) elementData[len-1];
	}

	public int size() {
		return elementCount;
	}
	
	public boolean isEmpty() {
		return size()==0;
	}
}

			/*ջ
			* Ҫ�󣺴���getMin���ܵ�ջ
			* pop(),push(),getMin()������ʱ�临�Ӷ�ΪO(1)���ռ临�Ӷ�ΪO(n)
			*/
public class Min_Stack{
	private MyStack<Integer> data;//����ջ�������������
	private MyStack<Integer> min;//��Сֵջ�����data�е���Сֵ
	
	public Min_Stack() {
		this.data = new MyStack<Integer>();
		this.min=new MyStack<Integer>();
	}
	
	/*��ջ				��һ�ַ�ʽ		|			�ڶ��ַ�ʽ			
	 * 		data	--	min			|		--	min
	 * 		4			����ջ		|	�ظ���ջ	1	<-peek
	 * 		2			����ջ		|	�ظ���ջ	1	
	 * 		1			1	<-peek	|			1
	 * 		3			3			|			3
	 */
	public void push(int item) {
		if(min.isEmpty()) {
			min.push(item);
		}else if(item < min.peek()){
			min.push(item);
		}
		data.push(item);
		
		/*�ڶ��з�ʽ
		 * if(min.isEmpty()) {
			min.push(item);
		}
		else if(item < min.peek()) {
			min.push(item);
		}
		else {
			min.push(min.peek());
		}
		data.push(item);*/
		
	}
	
	/*��ջ				��һ��			|				�ڶ���
	 * 		data	--	min				|			--	min
	 * 		4 <-peek    				|				1	<-peek
	 * 		2							|				1
	 * 		1			1	<-peek		|				1
	 * 		3			3				|				3
	 */
	public int pop() {
		if(data.isEmpty()) {
			throw new EmptyStackException();
		}
		int value = data.pop();
		if(value == getMin()) {
			min.pop();
		}
		return value;
		
		
	/*	�ڶ��ַ�ʽ
	 * 	min.pop();
		return data.pop();*/
	}
	
	//����dataջ�е���Сֵ
	public int getMin() {
		if(min.isEmpty()) {
			throw new EmptyStackException();
		}
		return min.peek();
	}
}
