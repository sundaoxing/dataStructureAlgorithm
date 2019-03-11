package com.heap;

public class Test {
	
	@org.junit.Test
	public void test() {
		MaxHeap mh = new MaxHeap(20);
		for(int i=0;i<20;i++) {
			int item = (int) (Math.random()*100);
			mh.insert(item);
		}
		while(!mh.isEmpty()) {
			System.out.print(mh.getMax()+" ");
		}
		/*System.out.println();
		for(int i =1;i<=mh.getSize();i++) {
			System.out.print(mh.getData()[i]+" ");
		}
		System.out.println();
		System.out.println(mh.getMax());
		System.out.println(mh.getMax());
		for(int i =1;i<=mh.getSize();i++) {
			System.out.print(mh.getData()[i]+" ");
		}*/
	}
	
	@org.junit.Test
	public void test1() {
		IndexMaxHeap imh = new IndexMaxHeap(10);
		imh.insert(0, 1);
		imh.insert(1, 2);
		imh.insert(2, 3);
		imh.insert(3, 4);
		
		/*for(int i=0;i<imh.getSize();i++) {
			System.out.println(imh.getData(i));
		}*/
		while(!imh.isEmpty()) {
			System.out.println(imh.getMax());
		}
	}
}
