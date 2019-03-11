package com.heap_data;

import java.util.ArrayList;
import java.util.Random;
import java.util.TreeMap;

public class Test {
	@org.junit.Test
	public void test1() {
		long start = System.nanoTime();
		int n=5;
		Random r = new Random();
		MaxHeap<Integer> mh= new MaxHeap<>();
		for(int i=0;i<n;i++) {
			mh.add(r.nextInt(10));
		}
		int [] a=new int [n];
		for(int i=0;i<n;i++) {
			a[i]=mh.extractMax();
		}
		for(int i=1;i<n;i++) {
			if(a[i] > a[i-1]) {
				System.out.println("false");
			}
		}
		System.out.println("true");
		long end = System.nanoTime();
		System.out.println("time : " + (end-start)/ 1000000000.0);
	}
	
	@org.junit.Test
	public void test2() {
		int n = 1000000;
		Random r = new Random();
		Integer [] a = new Integer[n];
		for(int i=0;i<n;i++) {
			a[i]=r.nextInt(n);
		}
		double time1 = testHeap(a, false);
		System.out.println("time : " + time1+" s");
		
		double time2 = testHeap(a, true);
		System.out.println("time : " + time2+" s");
	}
	
	@org.junit.Test
	public void test3() {
		int [] nums= {1,1,1,2,2,3};
//		ArrayList<Integer> a = topKFrequent(nums, 2);
		ArrayList<Integer> a = topKFrequent_new(nums, 2);
		System.out.println(a);
	}
	
	public double testHeap(Integer [] a , boolean isHeapify) {
		long start = System.nanoTime();
		MaxHeap<Integer> mh;
		if(isHeapify) {
			mh = new MaxHeap<>(a);
		}else {
			mh=new MaxHeap<>(a.length);
			for(int i=0;i<a.length;i++) {
				mh.add(a[i]);
			}
		}
		int [] arr=new int [a.length];
		for(int i=0;i<a.length;i++) {
			arr[i]=mh.extractMax();
		}
		for(int i=1;i<a.length;i++) {
			if(arr[i] > arr[i-1]) {
				System.out.println("false");
			}
		}
		System.out.println("true");
		long end = System.nanoTime();
		return (end-start)/1000000000.0;
	}
	
	private class Freq implements Comparable<Freq>{
		int item;//元素
		int freq;//频率
		public Freq(int item,int freq) {
			this.item=item;
			this.freq=freq;
		}
		//优先级的定义：频率越高，优先级越低-----每次出队，都是过滤掉频率低的，
		@Override
		public int compareTo(Freq other) {
			if(this.freq > other.freq) {
				return -1;
			}
			else if(this.freq < other.freq) {
				return 1;
			}
			else{
				return 0;
			}
		}
	}
	/*
	 * 给定一个非空数组，返回其中出现频率前k高的元素
	 * 例如：[1,1,1,2,2,3] k=2----->返回[1,2]
	 * 解题思路：（有时候直接入手不好下手，可以创造一个辅助的类或者数据结构，或者指针，其他等等，不要太死板，要灵活运用所学知识）
	 * 		1.利用map中处理数组中的元素--元素：元素频率
	 * 		2.创造一个辅助的类---Freq类，存储数组中元素，和元素的频率。
	 * 		3.利用优先队列来处理Freq类，其中以频率作为优先级，维持堆中元素个数为k个，即频率前k高的元素
	 * 		4.遍历map，先直接放入前k个元素入队
	 * 		  后面的元素先和堆顶元素比较优先级高低
	 * 				若高：则堆顶元素出队，新元素入队
	 * 				若低：啥也不做
	 */
	public ArrayList<Integer> topKFrequent(int [] nums,int k){
		ArrayList<Integer> a = new ArrayList<>();
		TreeMap<Integer, Integer> map = new TreeMap<>();
		for(int i=0;i<nums.length;i++) {
			if(map.containsKey(nums[i])) {
				map.put(nums[i], map.get(nums[i])+1);
			}
			else {
				map.put(nums[i],1);
			}
		}
		PriorityQueue<Freq> q = new PriorityQueue<>();
		for(Integer key : map.keySet()) {
			if(q.size() <k) {
				q.enqueue(new Freq(key, map.get(key)));
			}
			else if(map.get(key) > q.getFront().freq) {//比较元素出现的频率
					q.dequeue();
					q.enqueue(new Freq(key, map.get(key)));
			}
		}
		while(!q.isEmpty()) {
			a.add(q.dequeue().item);
		}
		return a;
	}
	
	/*
	 * 第二种解题方法：
	 * 在优先队列中通过实现Comparator接口（匿名内部类（Lambda表达式替代））
	 * Lambda表达式：（a,b...）->{程序语句;}
	 * Lambda 允许把函数作为一个方法的参数（函数作为参数传递进方法中）。
	 * 重新定义Integer的>,<,=符号的比较
	 * 		根据元素的频率定义Integer的大小比较;频率越高，优先级越低
	 * 替代创建辅助Freq类
	 * 
	 */
	public ArrayList<Integer> topKFrequent_new(int [] nums,int k){
		TreeMap<Integer, Integer> map = new TreeMap<>();
		for(int n :nums) {
			if(map.containsKey(n)) {
				map.put(n, map.get(n)+1);
			}
			else {
				map.put(n, 1);
			}
		}
		//在这里，需要使用java提供的PriorityQueue，（底层是最小堆）它支持传入Comparator接口的实现
		java.util.PriorityQueue<Integer> p = new java.util.PriorityQueue<>(
				/*
				 * 大小是相对的，可以根据业务需求，随意更改优先级
				 */
				(a,b)->map.get(a)-map.get(b)
		);
		for(Integer key :map.keySet()) {
			if(p.size() <k) {
				p.offer(key);
			}
			else if(map.get(key) > map.get(p.peek())) {//比较元素出现的频率
				p.poll();
				p.offer(key);
			}
		}
		ArrayList<Integer> a = new ArrayList<>();
		while(!p.isEmpty()) {
			a.add(p.poll());
		}
		return a;
	}
}
