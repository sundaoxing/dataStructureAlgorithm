package com.graph;
/*ͼ��Graph
 * ������Ҫ��1.�ڵ�   	/   2.��
 * 		   1.����ͼ     /    2.����ͼ�����Կ�������ͼ��һ��������ʽ��
 * ���ֱ�����ʽ��
 * 1.�ڽӾ���------һ�㴦�����ͼ
 * 2.�ڽӱ�--------һ�㴦��ϡ��ͼ
 */

import java.util.ArrayList;

public class DenseGraph implements Graph{
	private int n, m;// ͼ�Ľڵ�������ͱ���
	private boolean directed;// �Ƿ�������ͼ
	private ArrayList<ArrayList<Integer>> g;// �ڽӾ���

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
			g.add(a);// ��ʼ��Ĭ��ȫΪfalse
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
        return new DenseIterator(v);
    }
	/*�������ڵ�֮���������
	 * ���������ͼ����ֻ��ҪҪ����g[v][w]������ ���������ͼ������Ҫ����g[v][w]��g[w][v],˫�� ����bug�����v
	 * ��w�Ѿ���������ˣ���ʱ���������һ�εĻ� �����m���ۼ�1�Σ�������� ������Ҫ�����ǰ�ж����Ƿ��Ѿ��������� 0--1 0 1 2 3 /| 0 0
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
				if (v!=w && !hasEdge(v, w)) {//v==w--���Ի��ߣ���Ҫȥ����
					g.get(v).set(w, 1);
					if (!directed) {// ����ͼ
						g.get(w).set(v, 1);
					}
					m++;// ����Ҫ����
				}
			}
		}
	}

	// �ж�v��w�Ƿ��Ѿ�����
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
	
	//��ӡ���
	@Override
	public void show() {
		for(int i=0;i<n;i++) {
			for(int j=0;j<g.get(i).size();j++) {
				System.out.print(g.get(i).get(j)+" ");
			}
			System.out.println();
		}
	}
	//����Ϊ�ڲ��ࡣ ������������һ�ڵ㿪ʼ����������ͼ�иýڵ����������Ľڵ�
	@SuppressWarnings("unused")
	private class DenseIterator implements GraphIterator{
		private int v;
		private int index=-1;//��ʼ��Ϊ-1����Ϊ��ά���������ŵ�Ϊ0����1

		public DenseIterator(int v) {
			this.v = v;
		}

		int getV() {
			return v;
		}
		@Override
		public int begin() {
			index=-1;
			/*����ֱ�ӽ���next��������
			 * ��Ϊ���Զ�ά������˵��g[v][0]�У�v��0��һ������
			 * ֱ�ӷ���g[v][0]�Ǵ����
			 * �����ȷ��ص������ӵĽڵ㼴g[v][index]==1
			 */
			return next();
		}
		@Override
		public int next() {
			//������ά�����е�һ��
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