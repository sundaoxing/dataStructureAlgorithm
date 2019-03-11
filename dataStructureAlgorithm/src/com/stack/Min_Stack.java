package com.stack;

import java.util.Arrays;
import java.util.EmptyStackException;

class MyStack<E>{
	protected Object [] elementData;//元素
	protected int elementCount;//元素个数
	
	public MyStack() {
		this(10);
	}
	
	public MyStack(int initCapacity) {
		elementData=new Object[initCapacity];
	}
	
	//入栈
	public E push(E item) {
		addElement(item);
		return item;
	}

	private void addElement(E item) {
		ensureCapacityHelper(elementCount+1);//检查容量
		elementData[elementCount++]=item;
	}

	private void ensureCapacityHelper(int minCapacity) {
		if(minCapacity-elementData.length >0) {
			grow(minCapacity);//扩容
		}
	}

	private void grow(int minCapacity) {
		int newCapacity=elementData.length*2;
		elementData = Arrays.copyOf(elementData, newCapacity);//复制
	}
	
	//出栈（返回栈顶元素并删除）
	public E pop() {
		int len=size();
		E obj =peek();
		removeElement(len-1);
		return obj;
	}

	//移除指定索引的元素
	private void removeElement(int index) {
		if(index >= elementCount) {
			throw new ArrayIndexOutOfBoundsException(index + " >= " +
                    elementCount);
		}
		if(index <0) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		int j=elementCount-index-1;//不出错，j=0
		if(j>0) {
			System.arraycopy(elementData, index+1, elementData, index, j);
		}
		elementCount--;
		elementData[elementCount]=null;
	}

	//返回栈顶的元素（不删除）
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

			/*栈
			* 要求：带有getMin功能的栈
			* pop(),push(),getMin()操作的时间复杂度为O(1)，空间复杂度为O(n)
			*/
public class Min_Stack{
	private MyStack<Integer> data;//数据栈：存放正常数据
	private MyStack<Integer> min;//最小值栈：存放data中的最小值
	
	public Min_Stack() {
		this.data = new MyStack<Integer>();
		this.min=new MyStack<Integer>();
	}
	
	/*入栈				第一种方式		|			第二种方式			
	 * 		data	--	min			|		--	min
	 * 		4			不入栈		|	重复入栈	1	<-peek
	 * 		2			不入栈		|	重复入栈	1	
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
		
		/*第二中方式
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
	
	/*出栈				第一种			|				第二种
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
		
		
	/*	第二种方式
	 * 	min.pop();
		return data.pop();*/
	}
	
	//返回data栈中的最小值
	public int getMin() {
		if(min.isEmpty()) {
			throw new EmptyStackException();
		}
		return min.peek();
	}
}
