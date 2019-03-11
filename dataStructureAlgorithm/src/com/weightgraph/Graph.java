package com.weightgraph;
/*
 * ����ͼ�������ڵ�֮��������ӣ�������ϡ��ͼ�ͳ���ͼ��
 */
public interface Graph<W> {
	public void addEdge(int v, int w,W weight);
	public void show();
	public int getN();
	public int getM();
	public GraphIterator<W> getIterator(int v);
}
