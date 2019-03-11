package com.unionfind_data;

import java.util.Random;

public class Test {
	@org.junit.Test
	public void test1() {
		int size =10000000;
		int m = 10000000;
		/*UnionFind_1 u1 = new UnionFind_1(size);
		double time1 = testUF(u1, m);
		System.out.println("UnionFind_1: " +time1+" s");
		
		UnionFind_2 u2 = new UnionFind_2(size);
		double time2 = testUF(u2, m);
		System.out.println("UnionFind_2: " +time2+" s");*/
		
		UnionFind_3 u3 = new UnionFind_3(size);
		double time3 = testUF(u3, m);
		System.out.println("UnionFind_3: " +time3+" s");
		
		UnionFind_4 u4 = new UnionFind_4(size);
		double time4 = testUF(u4, m);
		System.out.println("UnionFind_4: " +time4+" s");
		
		UnionFind_5 u5 = new UnionFind_5(size);
		double time5 = testUF(u5, m);
		System.out.println("UnionFind_5: " +time5+" s");
		
		UnionFind_6 u6 = new UnionFind_6(size);
		double time6 = testUF(u6, m);
		System.out.println("UnionFind_6: " +time6+" s");
	}
	public double testUF(UnionFind uf , int m) {
		long start = System.nanoTime();
		Random r = new Random();
		for(int i=0;i<m;i++){
			int a = r.nextInt(uf.size());
			int b = r.nextInt(uf.size());
			uf.unionElements(a, b);
		}
		for(int i=0;i<m;i++){
			int a = r.nextInt(uf.size());
			int b = r.nextInt(uf.size());
			uf.isConnected(a, b);
		}
		long end = System.nanoTime();
		return (end-start) / 1000000000.0;
	}
}
