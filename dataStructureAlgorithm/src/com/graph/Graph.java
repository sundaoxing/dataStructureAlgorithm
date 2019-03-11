package com.graph;
/*
 * 用于图中两个节点之间添加连接（包含了稀疏图和稠密图）
 */
public interface Graph {
	public void addEdge(int v, int w);
	public void show();
	public int getN();
	public GraphIterator getIterator(int v);
}
