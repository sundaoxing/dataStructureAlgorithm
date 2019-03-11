package com.weightgraph;
/*
 * 用于图中两个节点之间添加连接（包含了稀疏图和稠密图）
 */
public interface Graph<W> {
	public void addEdge(int v, int w,W weight);
	public void show();
	public int getN();
	public int getM();
	public GraphIterator<W> getIterator(int v);
}
