package com.sagmenttree;

public class Test {
	@org.junit.Test
	public void test1() {
		Integer [] arr = {-2,0,3,-5,2,-1};
		SagmentTree<Integer> s = new SagmentTree<>(arr, (a,b)->a+b);
		System.out.println(s);
		System.out.println(s.query(0, 2));
		System.out.println(s.query(2,5));
		System.out.println(s.query(0, 5));
		s.update(0, 2);
		System.out.println(s);
		System.out.println(s.query(0, 2));
		System.out.println(s.query(2,5));
		System.out.println(s.query(0, 5));
		
	}
}
