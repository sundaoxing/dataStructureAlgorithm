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
		int item;//Ԫ��
		int freq;//Ƶ��
		public Freq(int item,int freq) {
			this.item=item;
			this.freq=freq;
		}
		//���ȼ��Ķ��壺Ƶ��Խ�ߣ����ȼ�Խ��-----ÿ�γ��ӣ����ǹ��˵�Ƶ�ʵ͵ģ�
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
	 * ����һ���ǿ����飬�������г���Ƶ��ǰk�ߵ�Ԫ��
	 * ���磺[1,1,1,2,2,3] k=2----->����[1,2]
	 * ����˼·������ʱ��ֱ�����ֲ������֣����Դ���һ����������������ݽṹ������ָ�룬�����ȵȣ���Ҫ̫���壬Ҫ���������ѧ֪ʶ��
	 * 		1.����map�д��������е�Ԫ��--Ԫ�أ�Ԫ��Ƶ��
	 * 		2.����һ����������---Freq�࣬�洢������Ԫ�أ���Ԫ�ص�Ƶ�ʡ�
	 * 		3.�������ȶ���������Freq�࣬������Ƶ����Ϊ���ȼ���ά�ֶ���Ԫ�ظ���Ϊk������Ƶ��ǰk�ߵ�Ԫ��
	 * 		4.����map����ֱ�ӷ���ǰk��Ԫ�����
	 * 		  �����Ԫ���ȺͶѶ�Ԫ�رȽ����ȼ��ߵ�
	 * 				���ߣ���Ѷ�Ԫ�س��ӣ���Ԫ�����
	 * 				���ͣ�ɶҲ����
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
			else if(map.get(key) > q.getFront().freq) {//�Ƚ�Ԫ�س��ֵ�Ƶ��
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
	 * �ڶ��ֽ��ⷽ����
	 * �����ȶ�����ͨ��ʵ��Comparator�ӿڣ������ڲ��ࣨLambda���ʽ�������
	 * Lambda���ʽ����a,b...��->{�������;}
	 * Lambda ����Ѻ�����Ϊһ�������Ĳ�����������Ϊ�������ݽ������У���
	 * ���¶���Integer��>,<,=���ŵıȽ�
	 * 		����Ԫ�ص�Ƶ�ʶ���Integer�Ĵ�С�Ƚ�;Ƶ��Խ�ߣ����ȼ�Խ��
	 * �����������Freq��
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
		//�������Ҫʹ��java�ṩ��PriorityQueue�����ײ�����С�ѣ���֧�ִ���Comparator�ӿڵ�ʵ��
		java.util.PriorityQueue<Integer> p = new java.util.PriorityQueue<>(
				/*
				 * ��С����Եģ����Ը���ҵ����������������ȼ�
				 */
				(a,b)->map.get(a)-map.get(b)
		);
		for(Integer key :map.keySet()) {
			if(p.size() <k) {
				p.offer(key);
			}
			else if(map.get(key) > map.get(p.peek())) {//�Ƚ�Ԫ�س��ֵ�Ƶ��
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
