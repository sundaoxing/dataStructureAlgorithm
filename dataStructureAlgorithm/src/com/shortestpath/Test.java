package com.shortestpath;

import com.weightgraph.ReadGraph;
import com.weightgraph.SparseGraph;

public class Test {
	@org.junit.Test
	public void test1() {
		String filename = "Graph_test\\testG4.txt";
		SparseGraph<Double> sg = new SparseGraph<Double>(5, true);
		@SuppressWarnings("unused")
		ReadGraph<Double> rg = new ReadGraph<Double>(sg, filename);
		// sg.show();
		System.out.println();
		Dijkstra<Double> d = new Dijkstra<>(sg, 0);
		for (int i = 1; i < 5; i++) {
			System.out.println(i + ":" + d.shortestPathTo(i));
			d.showPath(i);
		}
	}

	@org.junit.Test
	public void test2() {
		String filename = "Graph_test\\testG5.txt";
		SparseGraph<Double> sg = new SparseGraph<Double>(5, true);
		@SuppressWarnings("unused")
		ReadGraph<Double> rg = new ReadGraph<Double>(sg, filename);
		sg.show();
		System.out.println();
		BellManFord<Double> b = new BellManFord<>(sg, 0);
		if (b.hasNegativeCycle()) {
			System.out.println("有负权环");
		} else {
			for (int i = 1; i < 5; i++) {
				System.out.println(i + ":" + b.shortestPathTo(i));
				b.showPath(i);
			}
		}
	}
}
