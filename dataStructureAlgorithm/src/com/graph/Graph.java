package com.graph;
/*
 * ����ͼ�������ڵ�֮��������ӣ�������ϡ��ͼ�ͳ���ͼ��
 */
public interface Graph {
	public void addEdge(int v, int w);
	public void show();
	public int getN();
	public GraphIterator getIterator(int v);
}
