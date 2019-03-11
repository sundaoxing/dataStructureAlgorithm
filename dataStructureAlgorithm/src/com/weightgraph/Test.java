package com.weightgraph;

import java.util.ArrayList;

public class Test {
	@org.junit.Test
	public void test1() {
		String filename="Graph_test\\testG3.txt";
		int V=8;
		DenseGraph<Double> dg = new DenseGraph<>(V, false);
		@SuppressWarnings("unused")
		ReadGraph<Double> rg = new ReadGraph<>(dg, filename);
		dg.show();
	}
	
	@org.junit.Test
	public void test2() {
		String filename="Graph_test\\testG3.txt";
		int V=8;
		SparseGraph<Double> sg = new SparseGraph<>(V, false);
		@SuppressWarnings("unused")
		ReadGraph<Double> rg = new ReadGraph<>(sg, filename);
		sg.show();
	}
	
	@org.junit.Test
	public void test3() {
		MinHeap mh = new MinHeap(10);
		Edge<Double> e1=new Edge<Double>(0, 0, 0.4);
		Edge<Double> e2=new Edge<Double>(0, 0, 0.3);
		Edge<Double> e3=new Edge<Double>(0, 0, 0.2);
		Edge<Double> e4=new Edge<Double>(0, 0, 0.1);
		mh.insert(e1);
		mh.insert(e2);
		mh.insert(e3);
		mh.insert(e4);
		
		while(!mh.isEmpty()) {
			System.out.println(mh.getMin().getWeight());
		}
	}
	
	@org.junit.Test
	public void test4() {
		String filename="Graph_test\\testG3.txt";
		int V=8;
		SparseGraph<Double> sg = new SparseGraph<>(V, false);
		@SuppressWarnings("unused")
		ReadGraph<Double> rg = new ReadGraph<>(sg, filename);
		//sg.show();
		MinHeap mhEdge = new MinHeap(sg.getM()+1);
		LazyPrimeMST<Double> lp = new LazyPrimeMST<>(sg, mhEdge);
		ArrayList<Edge<Double>> a = lp.getMst();
		for(int i=0;i<a.size();i++) {
			System.out.println(a.get(i).getA()+"->"+a.get(i).getB()
					+": "+a.get(i).getWeight());
		}
		System.out.println("MST weight: "+lp.getMstWeight());
	}
	
	
	@org.junit.Test
	public void test5() {
		String filename="Graph_test\\testG3.txt";
		int V=8;
		SparseGraph<Double> sg = new SparseGraph<>(V, false);
		@SuppressWarnings("unused")
		ReadGraph<Double> rg = new ReadGraph<>(sg, filename);
		//sg.show();
		IndexMinHeap imh = new IndexMinHeap(V);
		PrimeMST<Double> p = new PrimeMST<>(sg, imh);
		ArrayList<Edge<Double>> a = p.getMst();
		for(int i=0;i<a.size();i++) {
			System.out.println(a.get(i).getA()+"->"+a.get(i).getB()
					+": "+a.get(i).getWeight());
		}
		System.out.println("MST weight: "+p.getMstWeight());
	}
	
	@org.junit.Test
	public void test6() {
		String filename="Graph_test\\testG3.txt";
		int V=8;
		SparseGraph<Double> sg = new SparseGraph<>(V, false);
		@SuppressWarnings("unused")
		ReadGraph<Double> rg = new ReadGraph<>(sg, filename);
		KruskalMST<Double> k = new KruskalMST<>(sg);
		ArrayList<Edge<Double>> a = k.getMst();
		for(int i=0;i<a.size();i++) {
			System.out.println(a.get(i).getA()+"->"+a.get(i).getB()
					+": "+a.get(i).getWeight());
		}
		System.out.println("MST weight: "+k.getMstWeight());
	}
}
