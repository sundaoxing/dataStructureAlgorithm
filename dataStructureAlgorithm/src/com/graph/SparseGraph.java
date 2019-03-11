package com.graph;

import java.util.ArrayList;

//ϡ��ͼ----�ڽӱ�
public class SparseGraph implements Graph{
	private int n, m;// ͼ�еĽڵ����������
	private boolean directed;// �Ƿ�������ͼ
	private ArrayList<ArrayList<Integer>> g;// �ڽӱ�

	public SparseGraph(int n, boolean directed) {
		this.n = n;
		this.m = 0;
		g=new ArrayList<ArrayList<Integer>>(n);
		for (int i = 0; i < n; i++) {
			ArrayList<Integer> a = new ArrayList<>(n);
			g.add(a);// ��ʼ��,���ֿյ����鼴�ɣ�Ĭ��Ϊnull��
		}
	}

	//�ڵ����
	public int getN() {
		return n;
	}

	//����
	int getM() {
		return m;
	}

	boolean isDirected() {
		return directed;
	}

	//����һ����v�ڵ㿪ʼ�����ĵ�����
	@Override
	public GraphIterator getIterator(int v) {
		return new SparseIterator(v);
	}
	// �������ڵ�֮�佨������(n����null)
	// 0--1 	0  1 (һ��)
	//   /| 	1  0  2  3 	 
	// 	/ | 	2  1  3	
	// 3--2 	3  1  2
	@Override
	public void addEdge(int v, int w) {
		if (v >= 0 && v < n && v != w) {//v==w--���Ի��ߣ���Ҫȥ����
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

	// �ж�v��m�����ڵ��Ƿ�������ȥ��ƽ�бߣ�
	// ʱ�临�Ӷ�O��n��
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
	
	//��ӡ���
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
	// ����Ϊ�ڲ��࣬������������һ�ڵ㿪ʼ������ϡ��ͼ�иýڵ����������Ľڵ�
	@SuppressWarnings("unused")
	private class SparseIterator implements GraphIterator{
		private int v;// ��Ҫ�����Ľڵ�
		private int index=0;// �������������±�(��ʾ������������)

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
			//���ӽڵ�ȫ����һ�������У�ֱ�Ӱ���˳���������
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