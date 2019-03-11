package com.weightgraph;
		/*��С������
		 * Kruskal�㷨
		 * ʹ����С��
		 */
import java.util.ArrayList;

import com.unionfind.UnionFind_5;

public class KruskalMST<W> {
	@SuppressWarnings("unused")
	private Graph<W> g;
	private ArrayList<Edge<Double>> mst;//��С������
	private Double mstWeight;//��С������������Ȩֵ��

	@SuppressWarnings("unchecked")
	public KruskalMST(Graph<W> g) {
		this.g = g;
		mst = new ArrayList<>(g.getN());
		MinHeap mh = new MinHeap(g.getM());
		
		/*Kruskal�㷨˼��
		 * ʱ�临�Ӷȣ�O�� Elog(E)��
		 * ��һ����ͨ����ͼ�бߵ�Ȩֵ��������ÿ��ȡ��СȨֵ�ıߣ�
		 * �����ܹ��ɱջ�����ȡ��ͼ�Ķ������-1���ߵ�ʱ��
		 * ��Щ�߱㹹�������ͼ����С������
		 * �������ѵ㣺1.�Աߵ�Ȩֵ���򣺽�����С�ѽ�������ÿ��ȡ�ѵĶ��ף���Сֵ��
		 * 			2.��α�֤�����ɱջ������ò��鼯
		 * 					����С��������ӱߵ�ʱ�򣬰������ߵ������˵���벢�鼯�кϲ����������й�ͬ�ĸ��ڵ�
		 * 					���´���ӱߵ�ʱ�����жϱߵ������˵��Ƿ��������Ƿ������ͬ�ĸ��ڵ㣩
		 */
		
		//��ͼ�����еı߷������
		for (int v = 0; v < g.getN(); v++) {
			GraphIterator<W> it = g.getIterator(v);
			for (Edge<W> w = it.begin(); !it.end(); w = it.next()) {
				if (w.getA() < w.getB()) {//��������ͼ��ÿ���߱��������Σ�����ֻҪһ�μ���
					mh.insert((Edge<Double>) w);
				}
			}
		}
		UnionFind_5 uf = new UnionFind_5(g.getN());
		while (!mh.isEmpty() && mst.size() < g.getN()-1) {
			Edge<Double> e = mh.getMin();//ȡ����СȨֵ�ı�
			if (uf.isConnected(e.getA(), e.getB())) {//�ж����������Ƿ�����
				continue;
			}
			mst.add(e);//���������������С������
			uf.unionElement(e.getA(), e.getB());//�ٰ��������ó�����
		}

		// ������С����������СȨֵ�ĺ�
		mstWeight = mst.get(0).getWeight();
		for (int i = 1; i < mst.size(); i++) {
			mstWeight += mst.get(i).getWeight();
		}
	}

	// ������С������
	public ArrayList<Edge<Double>> getMst() {
		return mst;
	}

	// ������С����������СȨֵ��
	public Double getMstWeight() {
		return mstWeight;
	}

}
