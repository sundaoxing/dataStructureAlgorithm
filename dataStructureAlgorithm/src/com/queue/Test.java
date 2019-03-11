package com.queue;

import java.util.Random;

import com.list.LinkedListQueue;

public class Test {
	@org.junit.Test
	public void test1() {
		LoopQueue<Integer> q = new LoopQueue<>(5);
		for(int i=0;i<10;i++ ) {
			q.enqueue(i);
			System.out.println(q);
			if(i % 3 ==2) {
				q.dequeue();
				System.out.println(q);
			}
		}
	}
	
	@org.junit.Test
	public void test2() {
		int testCount=1000000;
		/*ArrayQueue<Integer> a= new ArrayQueue<>();
		double time1= testQueue(a,testCount);
		System.out.println("ArrayQueue: time :"+time1 +" s");*/
		
		LoopQueue<Integer> l= new LoopQueue<>();
		double time2= testQueue(l,testCount);
		System.out.println("LoopQueue: time :"+time2 +" s");
		
		LinkedListQueue<Integer> lq = new LinkedListQueue<>();
		double time3=testQueue(lq, testCount);
		System.out.println("LinkedListQueue: time :"+time3 +" s");
		
	}

	private double testQueue(Queue_Interface<Integer> q, int testCount) {
		long start = System.nanoTime();
		Random r = new Random();
		for(int i=0;i<testCount;i++) {
			q.enqueue(r.nextInt(Integer.MAX_VALUE));
		}
		for(int i=0;i<testCount;i++) {
			q.dequeue();
		}
		long end = System.nanoTime();
		return (end-start) / 1000000000.0;
	}
}
