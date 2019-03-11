package com.shortestpath;

import java.util.ArrayList;

import com.weightgraph.Edge;
import com.weightgraph.Graph;
import com.weightgraph.GraphIterator;

/*��Դ���·���㷨��Bellman-Frod
		 * ���ڴ������и�Ȩֵ��ͼ��ͼΪ����ͼ
		 * ���ܴ�����и�Ȩֵ�Ļ����������ж�ͼ����û�и�Ȩ��
		 * ʱ�临�Ӷȣ�O��E*V��
		 * 
		 * ˼�룺���һ��ͼû�и�Ȩ����
		 * 		�ӽڵ�v���ڵ�w�����·������ྭ��N���ڵ㣨ͼ�����нڵ㣩��N-1����
		 * 		����ڵ�w�������Σ�����ڸ�Ȩ��
		 * �ɳڲ�������ǰ�Ǵ�Դ�ڵ㿪ʼ����һ���ڵ��ֱ����ʹ·����С�����ڳ��������ߣ�ʹ·������С
		 */
public class BellManFord<W> {
	private Graph<W> g;
	private int s;
	private double[] destTo;//�������飺destTo[v]��ʾ0�ڵ㵽v�ڵ�ĵ�ǰ���·��
	private ArrayList<Edge<Double>> from;//·�����ϣ���ʾ��0�ڵ㵽ÿ���ڵ�����·������
	private boolean hasNegativeCycle;//�Ƿ��и�Ȩ��

	public BellManFord(Graph<W> g, int s) {
		this.g = g;
		this.s = s;
		destTo = new double[g.getN()];
		from = new ArrayList<Edge<Double>>();
		for (int i = 0; i < g.getN(); i++) {
			destTo[i] = 0;//��ʼ��������ȫΪ0
			from.add(null);//��ʼ����û�����·��
		}

		bellmanford();
	}

	/*bellman-ford �㷨˼�룺
	 * �����еĽڵ����N-1���ɳڲ����������Ͼ��ҵ��˴�Դ�ڵ㵽�����ڵ�����·��
	 * ��������Խ����ɳڲ�������˵��ͼ���и�Ȩ��
	 * �ɳڲ�����
	 * 		1.�ӵ�ǰ�ڵ�v����������������ýڵ������Ľڵ�w,u...
	 * 		2.�������һ����e��Ȩֵ����destTo[v](Դ�ڵ㵽v�ڵ�ľ���)+e.getWeight < destTo[w](Դ�ڵ㵽w�ڵ�ľ���)
	 * 				���ҵ�һ��Դ�ڵ㵽w�ڵ�ĵ�ǰ���·��
	 */
	private void bellmanford() {
		for (int pass = 1; pass < g.getN(); pass++) {
			for (int i = 0; i < g.getN(); i++) {//�ɳڲ���
				GraphIterator<W> it = g.getIterator(i);
				for (Edge<W> v = it.begin(); !it.end(); v = it.next()) {
					@SuppressWarnings("unchecked")
					Edge<Double> e = (Edge<Double>) v;
					if ((from.get(e.getB()) == null) || (destTo[e.getA()] + e.getWeight() < destTo[e.getB()])) {
						destTo[e.getB()] = destTo[e.getA()] + e.getWeight();
						from.set(e.getB(), e);
					}
				}
			}
		}
		//�ٽ���һ���ɳڲ�����������ԣ���˵���и�Ȩ��������true
		hasNegativeCycle = detectNegetiveCycle();
	}

	private boolean detectNegetiveCycle() {
		for (int i = 0; i < g.getN(); i++) {
			GraphIterator<W> it = g.getIterator(i);
			for (Edge<W> v = it.begin(); !it.end(); v = it.next()) {
				@SuppressWarnings("unchecked")
				Edge<Double> e = (Edge<Double>) v;
				if ((from.get(e.getB()) == null) || (destTo[e.getA()] + e.getWeight() < destTo[e.getB()])) {
					return true;
				}
			}
		}
		return false;
	}
	//�Ƿ��и�Ȩ��
	public boolean hasNegativeCycle() {
		return hasNegativeCycle;
	}
	//����0��w�ڵ����С����
	public double shortestPathTo(int w) {
		return destTo[w];
	}
	//�ж�0�ڵ���û�е�w�ڵ��·��
	public boolean hasPathTo(int w) {
		return from.get(w) != null;
	}
	//����0�ڵ㵽w�ڵ�����·������
	public void shortestPath(int w, ArrayList<Edge<Double>> a) {
		Edge<Double> e = from.get(w);
		while (e != null) {
			a.add(e);
			e = from.get(e.other(w));
		}
	}

	// �����ӡ0�ڵ㵽w�ڵ�����·��
	public void showPath(int w) {
		ArrayList<Edge<Double>> a = new ArrayList<>();
		shortestPath(w, a);
		System.out.print(s + "->" + w + ": " + s + "->");
		for (int i = a.size() - 1; i >= 0; i--) {
			if (i == 0) {
				System.out.print(a.get(i).getB());
			} else {
				System.out.print(a.get(i).getB() + "->");
			}

		}
		System.out.println();
	}
}
