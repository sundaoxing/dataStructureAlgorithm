package com.graph;
/*图论Graph
 * 两个重要：1.节点   	/   2.边
 * 		   1.有向图     /    2.无向图（可以看成有向图的一种特殊形式）
 * 两种表现形式：
 * 1.邻接矩阵：------一般处理稠密图
 * 2.邻接表：--------一般处理稀疏图
 */

import java.util.ArrayList;

public class DenseGraph implements Graph{
	private int n, m;// 图的节点个数，和边数
	private boolean directed;// 是否是有向图
	private ArrayList<ArrayList<Integer>> g;// 邻接矩阵

	public DenseGraph(int n, boolean directed) {
		this.n = n;
		this.m = 0;
		this.directed = directed;
		g=new ArrayList<ArrayList<Integer>>(n);
		for (int i = 0; i < n; i++) {
			ArrayList<Integer> a = new ArrayList<>(n);
			for (int j = 0; j < n; j++) {
				a.add(0);
			}
			g.add(a);// 初始化默认全为false
		}
	}

	//节点个数
	public int getN() {
		return n;
	}
	//边数
	int getM() {
		return m;
	}

	boolean isDirected() {
		return directed;
	}
	//返回一个从v节点开始遍历的迭代器
	@Override
	public GraphIterator getIterator(int v) {
        return new DenseIterator(v);
    }
	/*在两个节点之间添加连接
	 * 如果是有向图，则只需要要处理g[v][w]，单向 如果是无向图，则需要处理g[v][w]和g[w][v],双向 隐藏bug：如果v
	 * 和w已经添加连接了，这时有重新添加一次的话 会出现m有累加1次，产生虚边 所以需要在添加前判断其是否已经建立连接 0--1 0 1 2 3 /| 0 0
	 */ 
	// 0--1 		0	1	2	3 
	//   /|		0	0	1	0	0
	//	/ |		1	1	0	1	1
	// 3--2 	2	0	1	0	1
	//			3	0	1	1	0
	@Override
	public void addEdge(int v, int w) {
		if (v >= 0 && v < n) {
			if (w >= 0 && w < n) {
				if (v!=w && !hasEdge(v, w)) {//v==w--》自环边（需要去除）
					g.get(v).set(w, 1);
					if (!directed) {// 无向图
						g.get(w).set(v, 1);
					}
					m++;// 边数要增加
				}
			}
		}
	}

	// 判断v和w是否已经连接
	public boolean hasEdge(int v, int w) {
		if (v >= 0 && v < n) {
			if (w >= 0 && w < n) {
				if(g.get(v).get(w) == 1) {
					return true;
				}
			}
		}
		return false;
	}
	
	//打印输出
	@Override
	public void show() {
		for(int i=0;i<n;i++) {
			for(int j=0;j<g.get(i).size();j++) {
				System.out.print(g.get(i).get(j)+" ");
			}
			System.out.println();
		}
	}
	//跟改为内部类。 迭代器，从任一节点开始，遍历稠密图中该节点所有相连的节点
	@SuppressWarnings("unused")
	private class DenseIterator implements GraphIterator{
		private int v;
		private int index=-1;//初始化为-1，因为二维矩阵这里存放的为0或者1

		public DenseIterator(int v) {
			this.v = v;
		}

		int getV() {
			return v;
		}
		@Override
		public int begin() {
			index=-1;
			/*这里直接交给next（）方法
			 * 因为，对二维矩阵来说，g[v][0]中，v和0不一定相连
			 * 直接返回g[v][0]是错误的
			 * 必须先返回的相连接的节点即g[v][index]==1
			 */
			return next();
		}
		@Override
		public int next() {
			//遍历二维矩阵中的一行
			for(index+=1;index<g.get(v).size();index++) {
				if(g.get(v).get(index) ==1) {
					return index;
				}
			}
			return -1;
		}
		@Override
		public boolean end() {
			return index>=g.get(v).size();
		}
	}
}