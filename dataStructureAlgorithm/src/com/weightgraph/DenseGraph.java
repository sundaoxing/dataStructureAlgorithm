package com.weightgraph;
/*ͼ��Graph
 * ������Ҫ��1.�ڵ�   	/   2.��
 * 		   1.����ͼ     /    2.����ͼ�����Կ�������ͼ��һ��������ʽ��
 * ���ֱ�����ʽ��
 * 1.�ڽӾ���------һ�㴦�����ͼ
 * 2.�ڽӱ�--------һ�㴦��ϡ��ͼ
 */

import java.util.ArrayList;

//				��Ȩͼ
public class DenseGraph<W> implements Graph<W> {
	private int n, m;// ͼ�Ľڵ�������ͱ���
	private boolean directed;// �Ƿ�������ͼ
	private ArrayList<ArrayList<Edge<W>>> g;// �ڽӾ���

	public DenseGraph(int n, boolean directed) {
		this.n = n;
		this.m = 0;
		this.directed = directed;
		g = new ArrayList<ArrayList<Edge<W>>>(n);
		for (int i = 0; i < n; i++) {
			ArrayList<Edge<W>> a = new ArrayList<>(n);
			for (int j = 0; j < n; j++) {
				a.add(null);
			}
			g.add(a);// ��ʼ��Ĭ��ȫΪnull
		}
	}

	// �ڵ����
	public int getN() {
		return n;
	}

	// ����
	public int getM() {
		return m;
	}

	boolean isDirected() {
		return directed;
	}

	// ����һ����v�ڵ㿪ʼ�����ĵ�����
	@Override
	public GraphIterator<W> getIterator(int v) {
		return new DenseIterator(v);
	}

	/*
	 * �������ڵ�֮��������� ���������ͼ����ֻ��ҪҪ����g[v][w]������ ���������ͼ������Ҫ����g[v][w]��g[w][v],˫�� ����bug�����v
	 * ��w�Ѿ���������ˣ���ʱ���������һ�εĻ� �����m���ۼ�1�Σ�������� ������Ҫ�����ǰ�ж����Ƿ��Ѿ��������� 0--1 0 1 2 3 /| 0 0
	 */
	// 0--1 0 1 2 3
	// /| 0 0 1 0 0
	// / | 1 1 0 1 1
	// 3--2 2 0 1 0 1
	// 3 0 1 1 0
	@Override
	public void addEdge(int v, int w, W weight) {
		if (v >= 0 && v < n) {
			if (w >= 0 && w < n) {
				if (hasEdge(v, w)) {// v==w--���Ի��ߣ���Ҫȥ����
					g.get(v).set(w, null);
					if (!directed) {// ����ͼ
						g.get(w).set(v, null);
					}
					m--;// ����Ҫ����
				}
				g.get(v).set(w, new Edge<W>(v, w, weight));
				if (!directed) {// ����ͼ
					g.get(w).set(v, new Edge<W>(w, v, weight));
				}
				m++;// ����Ҫ����
			}
		}
	}

	// �ж�v��w�Ƿ��Ѿ�����
	public boolean hasEdge(int v, int w) {
		if (v >= 0 && v < n) {
			if (w >= 0 && w < n) {
				return g.get(v).get(w) != null;
			}
		}
		return false;
	}

	// ��ӡ���
	@Override
	public void show() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < g.get(i).size(); j++) {
				if (g.get(i).get(j) != null) {
					System.out.print(g.get(i).get(j).getWeight() + " ");
				}
				else {
					System.out.print("NULL"+" ");
				}
			}
			System.out.println();
		}
	}

	// ����Ϊ�ڲ��ࡣ ������������һ�ڵ㿪ʼ����������ͼ�иýڵ����������Ľڵ�
	@SuppressWarnings("unused")
	private class DenseIterator implements GraphIterator<W> {
		private int v;
		private int index = -1;// ��ʼ��Ϊ-1����Ϊ��ά���������ŵ�Ϊ0����1

		public DenseIterator(int v) {
			this.v = v;
		}

		int getV() {
			return v;
		}

		@Override
		public Edge<W> begin() {
			index = -1;
			/*
			 * ����ֱ�ӽ���next�������� ��Ϊ���Զ�ά������˵��g[v][0]�У�v��0��һ������ ֱ�ӷ���g[v][0]�Ǵ����
			 * �����ȷ��ص������ӵĽڵ㼴g[v][index]==1
			 */
			return next();
		}

		@Override
		public Edge<W> next() {
			// ������ά�����е�һ��
			for (index += 1; index < g.get(v).size(); index++) {
				if (g.get(v).get(index) != null) {
					return g.get(v).get(index);
				}
			}
			return null;
		}

		@Override
		public boolean end() {
			return index >= g.get(v).size();
		}
	}
}