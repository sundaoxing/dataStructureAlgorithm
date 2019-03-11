package com.set;

import java.util.ArrayList;

public class Test {
	@org.junit.Test
	public void test1() {
		System.out.println("Pride-and-Prejudice");
		ArrayList<String> a = new ArrayList<>();
		FileOperation.readFile("src/com/set/Pride-and-Prejudice.txt", a);
		System.out.println("word size :" + a.size());
		BSTSet<String> set = new BSTSet<>();
		for(String word :a) {
			set.add(word);
		}
		System.out.println("word different size :" +set.size());
	}
	
	@org.junit.Test
	public void test2() {
		System.out.println("Pride-and-Prejudice");
		ArrayList<String> a = new ArrayList<>();
		FileOperation.readFile("src/com/set/Pride-and-Prejudice.txt", a);
		System.out.println("word size :" + a.size());
		LinkedListSet<String> set = new LinkedListSet<>();
		for(String word :a) {
			set.add(word);
		}
		System.out.println("word different size :" +set.size());
	}
	
	@org.junit.Test
	public void test3() {
		String filename="src/com/set/Pride-and-Prejudice.txt";
		/*LinkedListSet<String> linkedlistset = new LinkedListSet<>();
		double time1 =testSet(linkedlistset, filename);
		System.out.println("共耗时："+time1);
		System.out.println("---------------------------------------");*/
		
		BSTSet<String> BSTset = new BSTSet<>();
		double time2 =testSet(BSTset, filename);
		System.out.println("共耗时："+time2);
		System.out.println("---------------------------------------");
		
		AVLSet<String> avlSet = new AVLSet<>();
		double time3 = testSet(avlSet, filename);
		System.out.println("共耗时："+time3);
	}
	
	public double testSet(Set<String> set,String filename ) {
		long start = System.nanoTime();
		System.out.println("Pride-and-Prejudice");
		ArrayList<String> a = new ArrayList<>();
		FileOperation.readFile(filename, a);
		System.out.println("word size :" + a.size());
		for(String word :a) {
			set.add(word);
		}
		System.out.println("word different size :" +set.size());
		long end = System.nanoTime();
		return (end-start) / 1000000000.0;
	}
}
