package com.graph;

public class Test {
	@org.junit.Test
	public void test1() {
		int N=20;
		int M=100;
		SparseGraph sg = new SparseGraph(N, false);
		for(int i=0;i<M;i++) {
			int a=(int) (Math.random()*N);
			int b=(int) (Math.random()*N);
			sg.addEdge(a, b);
		}
		for(int v=0;v<N;v++) {
			System.out.print(v+":");
			GraphIterator it=sg.getIterator(v) ;
			for(int w=it.begin();!it.end();w=it.next()) {
				System.out.print(w+" ");
			}
			System.out.println();
		}
	}
	
	@org.junit.Test
	public void test2() {
		int N=20;
		int M=100;
		DenseGraph dg = new DenseGraph(N, false);
		for(int i=0;i<M;i++) {
			int a=(int) (Math.random()*N);
			int b=(int) (Math.random()*N);
			dg.addEdge(a, b);
		}
		for(int v=0;v<N;v++) {
			System.out.print(v+":");
			GraphIterator it=dg.getIterator(v) ;
			for(int w=it.begin();!it.end();w=it.next()) {
				System.out.print(w+" ");
			}
			System.out.println();
		}
	}
	
	@org.junit.Test
	public void test3() {
		String filename="Graph_test\\testG1.txt";
		SparseGraph sg = new SparseGraph(13, false);
		@SuppressWarnings("unused")
		ReadGraph rg = new ReadGraph(sg, filename);
		sg.show();
	}
	
	@org.junit.Test
	public void test4() {
		String filename="Graph_test\\testG1.txt";
		DenseGraph dg =new DenseGraph(13, false);
		@SuppressWarnings("unused")
		ReadGraph rg = new ReadGraph(dg, filename);
		dg.show();
	}
	
	@org.junit.Test
	public void test5() {
		String filename="Graph_test\\testG1.txt";
		SparseGraph sg = new SparseGraph(13,false);
		@SuppressWarnings("unused")
		ReadGraph rg = new ReadGraph(sg, filename);
		Component cp = new Component(sg);
		cp.iteratorDFS();
		System.out.println(cp.getCcount());
	}
	
	@org.junit.Test
	public void test6() {
		String filename="Graph_test\\testG2.txt";
		SparseGraph sg = new SparseGraph(7,false);
		@SuppressWarnings("unused")
		ReadGraph rg = new ReadGraph(sg, filename);
		sg.show();
		Component cp = new Component(sg);
		cp.iteratorDFS();
		System.out.println(cp.getCcount());
		System.out.println(cp.isConnected(0, 2));
		System.out.println(cp.isConnected(0, 3));
	}
	
	@org.junit.Test
	public void test7() {
		String filename="Graph_test\\testG2.txt";
		SparseGraph sg = new SparseGraph(7,false);
		@SuppressWarnings("unused")
		ReadGraph rg = new ReadGraph(sg, filename);
		sg.show();
		System.out.println();
		DFS_Path dfs=new DFS_Path(sg, 0);
		dfs.showPath(3);
 	}
	
	@org.junit.Test
	public void test8() {
		String filename="Graph_test\\testG2.txt";
		SparseGraph sg = new SparseGraph(7,false);
		@SuppressWarnings("unused")
		ReadGraph rg = new ReadGraph(sg, filename);
		sg.show();
		System.out.println();
		BFS_Path bfs = new BFS_Path(sg, 0);
		bfs.showPath(6);
 	}
}	
