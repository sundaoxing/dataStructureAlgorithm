package com.weightgraph;
/*
 * ��������������ϡ��ͼ�ͳ���ͼ��
 */
public interface GraphIterator<W> {
	public Edge<W> begin();
	public Edge<W> next();
	public boolean end();
}
