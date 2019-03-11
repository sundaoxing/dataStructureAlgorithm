package com.heap_data;

import com.queue.Queue_Interface;		
/*	
 * 				优先队列
 * 		特点：
 * 			2.底层数据结构：堆---动态维护元素的优先级	
 * 			1.优先队列中的每个元素都有各自的优先级，优先级最高的元素最先得到服务
 * 		应用：
 * 			1.CPU任务调度
 * 			2.游戏中自动选择小怪攻击
 * 		优先级的定义：
 * 				根据存储数据类型E的compareTo（）方法中定义的优先级
 */
public class PriorityQueue<E extends Comparable<E>> implements Queue_Interface<E> {
	private MaxHeap<E> mh;//底层数据结构-堆
	public PriorityQueue() {
		mh=new MaxHeap<>();
	}
	//带初始化容量的优先队列构造方法
	public PriorityQueue(int capacity) {
		mh = new MaxHeap<>(capacity);
	}
	//返回优先队列中元素的个数
	@Override
	public int size() {
		return mh.size();
	}
	//判断优先是否为空
	@Override
	public boolean isEmpty() {
		return mh.isEmpty();
	}
	//入队-------------时间复杂度：O（log（n））
	@Override
	public void enqueue(E item) {
		mh.add(item);
	}
	//出队，返回并删除优先级最高的元素（实质：堆的对顶元素）---时间复杂度：O（log（n））
	@Override
	public E dequeue() {
		return mh.extractMax();
	}
	//返回优先级最高的元素
	@Override
	public E getFront() {
		return mh.lookMax();
	}
}
