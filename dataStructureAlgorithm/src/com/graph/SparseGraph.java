package com.graph;

import java.util.ArrayList;

//稀疏图----邻接表
public class SparseGraph implements Graph{
	private int n, m;// 图中的节点个数，边数
	private boolean directed;// 是否是有向图
	private ArrayList<ArrayList<Integer>> g;// 邻接表

	public SparseGraph(int n, boolean directed) {
		this.n = n;
		this.m = 0;
		g=new ArrayList<ArrayList<Integer>>(n);
		for (int i = 0; i < n; i++) {
			ArrayList<Integer> a = new ArrayList<>(n);
			g.add(a);// 初始化,保持空的数组即可（默认为null）
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
		return new SparseIterator(v);
	}
	// 在两个节点之间建立连接(n代替null)
	// 0--1 	0  1 (一行)
	//   /| 	1  0  2  3 	 
	// 	/ | 	2  1  3	
	// 3--2 	3  1  2
	@Override
	public void addEdge(int v, int w) {
		if (v >= 0 && v < n && v != w) {//v==w--》自环边（需要去除）
			if (w >= 0 && w < n) {
				// if (!hasEdge(v, w)) {
				g.get(v).add(w);
				if (!directed) {
					g.get(w).add(v);
				}
				m++;
				// }
			}
		}
	}

	// 判断v和m两个节点是否相连（去除平行边）
	// 时间复杂度O（n）
	public boolean hasEdge(int v, int w) {
		if (v >= 0 && v < n) {
			if (w >= 0 && w < n) {
				for (int i = 0; i < g.size(); i++) {
					if (g.get(v).get(i) == w) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	//打印输出
	@Override
	public void show() {
		for(int i=0;i<n;i++) {
			System.out.print(i+":");
			for(int j=0;j<g.get(i).size();j++) {
				System.out.print(g.get(i).get(j)+" ");
			}
			System.out.println();
		}
		
	}
	// 跟改为内部类，迭代器，从任一节点开始，遍历稀疏图中该节点所有相连的节点
	@SuppressWarnings("unused")
	private class SparseIterator implements GraphIterator{
		private int v;// 将要遍历的节点
		private int index=0;// 迭代器的索引下标(表示迭代到哪里了)

		public SparseIterator(int v) {
			this.v = v;
		}

		int getV() {
			return v;
		}

		@Override
		public int begin() {
			index = 0;
			if(g.get(v).size() > 0) {
				return g.get(v).get(index);
			}
			return  -1;
		}

		@Override
		public int next() {
			index++;
			//连接节点全存在一个数组中，直接按照顺序遍历即可
			if(index < g.get(v).size()) {
				return g.get(v).get(index);
			}
			return -1;
		} 

		@Override
		public boolean end() {
			return index >= g.get(v).size();
		}
	}
}