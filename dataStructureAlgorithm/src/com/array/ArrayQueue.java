package com.array;
			/*			队列
			 * 底层结构：动态数组实现
			 * 因为底层结构不同，栈实现的细节也不同，未来方便后续的栈的实现，把栈的主要功能
			 * 抽象成一个接口
			 * 应用： 1.进程任务调度
			 * 		2.优先队列
			 * 		3.消息队列
			 * 特点： 1.线性结构
			 * 		2.操作的是数组的一部分（可以看作是子集）
			 * 		3.元素是先进先出（first in first out）FIFO
			 * 		4.从数组的一端（队尾）添加元素，另一端（队首）取出元素，
			 * 		   —————————————————
			 * 队首 。。。| 2 | 3 | 1 | 4 | 。。。队尾
			 *         —————————————————
			 *          ^-getFront(指向队首)
			 */
import com.queue.Queue_Interface;

public class ArrayQueue<E> implements Queue_Interface<E> {
	private MyArray<E> array;//自定义的动态数组
	//自定义容量的有参构造
	public ArrayQueue(int capacity) {
		array=new MyArray<>(capacity);
	}
	//初始容量为10的无参构造
	public ArrayQueue() {
		array=new MyArray<>();
	}
	//队列中元素的个数--O(1)
	@Override
	public int size() {
		return array.size();
	}
	//判断队列是否为空--O(1)
	@Override
	public boolean isEmpty() {
		return array.isEmpty();
	}
	//入队列--O(1)-均摊复杂度（包含扩容的O（n））
	@Override
	public void enqueue(E item) {
		array.addLast(item);
	}
	//出队列--O(n)（不包含缩容复杂度）----时间复杂度有点高，因为要移动n-1个数据
	@Override
	public E dequeue() {
		return array.removeFirst();
	}
	//获取队首的元素--O(1)
	@Override
	public E getFront() {
		return array.getFirst();
	}
	//获取队列当前的容量--O(1)
	public int getCapacity() {
		return array.getCapacity();
	}
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(String.format("Queue: size=%d , capacity=%d\n",array.size(),array.getCapacity()));
		s.append("front[");
		for(int i =0;i<size();i++) {
			s.append(array.get(i));
			if(i != size()-1) {
				s.append(", ");
			}
		}
		s.append("]tail");
		return s.toString();
	}
}
