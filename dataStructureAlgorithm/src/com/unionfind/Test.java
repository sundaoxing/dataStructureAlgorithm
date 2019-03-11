package com.unionfind;


public class Test {
	@org.junit.Test
	public void test1() {
		int n=10000;
		UnionFind_1 u = new UnionFind_1(n);
		long start = System.currentTimeMillis();
		for(int i=0;i<n;i++) {
			int a= (int) (Math.random()*n);
			int b= (int) (Math.random()*n);
			u.unionElement(a, b);
		}
		for(int i=0;i<n;i++) {
			int a= (int) (Math.random()*n);
			int b= (int) (Math.random()*n);
			u.isConnected(a, b);
		}
		long end= System.currentTimeMillis();
		System.out.println(end-start+"ms");
	}
	
	@org.junit.Test
	public void test2() {
		int n=100000;
		UnionFind_2 u = new UnionFind_2(n);
		long start = System.currentTimeMillis();
		for(int i=0;i<n;i++) {
			int a= (int) (Math.random()*n);
			int b= (int) (Math.random()*n);
			u.unionElement(a, b);
		}
		for(int i=0;i<n;i++) {
			int a= (int) (Math.random()*n);
			int b= (int) (Math.random()*n);
			u.isConnected(a, b);
		}
		long end= System.currentTimeMillis();
		System.out.println(end-start+"ms");
	}
	
	@org.junit.Test
	public void test3() {
		int n=1000000;
		UnionFind_3 u = new UnionFind_3(n);
		long start = System.currentTimeMillis();
		for(int i=0;i<n;i++) {
			int a= (int) (Math.random()*n);
			int b= (int) (Math.random()*n);
			u.unionElement(a, b);
		}
		for(int i=0;i<n;i++) {
			int a= (int) (Math.random()*n);
			int b= (int) (Math.random()*n);
			u.isConnected(a, b);
		}
		long end= System.currentTimeMillis();
		System.out.println(end-start+"ms");
	}
	
	@org.junit.Test
	public void test4() {
		int n=1000000;
		UnionFind_4 u = new UnionFind_4(n);
		long start = System.currentTimeMillis();
		for(int i=0;i<n;i++) {
			int a= (int) (Math.random()*n);
			int b= (int) (Math.random()*n);
			u.unionElement(a, b);
		}
		for(int i=0;i<n;i++) {
			int a= (int) (Math.random()*n);
			int b= (int) (Math.random()*n);
			u.isConnected(a, b);
		}
		long end= System.currentTimeMillis();
		System.out.println(end-start+"ms");
	}
	@org.junit.Test
	public void test5() {
		int n=1000000;
		UnionFind_5 u = new UnionFind_5(n);
		long start = System.currentTimeMillis();
		for(int i=0;i<n;i++) {
			int a= (int) (Math.random()*n);
			int b= (int) (Math.random()*n);
			u.unionElement(a, b);
		}
		for(int i=0;i<n;i++) {
			int a= (int) (Math.random()*n);
			int b= (int) (Math.random()*n);
			u.isConnected(a, b);
		}
		long end= System.currentTimeMillis();
		System.out.println(end-start+"ms");
	}
}
