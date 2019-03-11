package com.weightgraph;
			/*��С������
			 * �Ż��棺��Prime�㷨���Ż�
			 * ʹ����С������
			 */
import java.util.ArrayList;

public class PrimeMST<W> {
	private Graph<W> g;//ͼ
	/*			��С�����ѣ��ѳ���ͼ�еĽڵ���
	 * ����index��Ӧdata�е���С�ߵ�Ȩֵ
	 * ���������С��Ȩֵ�������data���ɣ����ض��������
	 */
	private IndexMinHeap imhEdge;//��С�����ѣ�ֻ���ÿ���ڵ������ĺ��бߵ�Ȩֵ
	private ArrayList<Edge<Double>> edgeTo;//�����ÿ���ڵ�������Ȩֵ��С�ĺ��б�
	//false:��ɫ��Ӫ��true����ɫ��Ӫ
	private boolean[] marked;//������飺����ͬ���з���Ӫ
	private ArrayList<Edge<Double>> mst;//��С�������ı�
	private double mstWeight;//��С������������Ȩֵ��

	public PrimeMST(Graph<W> g, IndexMinHeap imhEdge) {
		this.g = g;
		this.imhEdge = imhEdge;
		edgeTo = new ArrayList<Edge<Double>>(g.getN());
		marked = new boolean[g.getN()];
		mst = new ArrayList<Edge<Double>>(g.getN());
		for (int i = 0; i < g.getN(); i++) {
			marked[i] = false;//��ʼȫΪ��ɫ��Ӫ��ÿ�ζ���һ���ڵ㻮�ֽ���ɫ��Ӫ
			edgeTo.add(null);//��ʼȫΪnull,���������жϴ治���ں��б�
		}
		mst.clear();
		/*Prime�㷨��visit����
		 * ʱ�临�Ӷȣ�O�� Elog(V)��
		 * 1.��0�ڵ�Ϊ��ʼ�ڵ㣬�����ɫ��Ӫ��������������0�ڵ������Ľڵ�v,w,u...
		 * 2.�ж�v,w,u...�ڵ��Ƿ��Ѽ����ɫ��Ӫ
		 * 		�����ں�ɫ��Ӫ�����жϺ��б�����edgeTo���Ƿ���������
		 * 					û�У��������ߵ�Ȩֵ������У�����edgeTo�иýڵ�ı�
		 * 					�У��жϵ�ǰ�ߵ�Ȩֵ�Ƿ�С���Ѵ��������ߵ�Ȩֵ
		 * 							С�ڣ������edgeTo�иýڵ�ıߣ������¶��иýڵ��Ȩֵ
		 */
		visit(0);

		/*�����С�����ѣ�
		 * 1.��ȡ��С�����ѵ���Сֵ������v����ʵҲ����ͼ�еĽڵ㣩
		 * 2.�жϺ��б�����edgeTo���Ƿ���������
		 * 		�У��������С������mst�У����Դ˽ڵ�Ϊ��ʼ�ڵ㣬�ڴ˱�����ýڵ����������нڵ㣨visit(v)��
		 */
		while (!imhEdge.isEmpty()) {
			int v = imhEdge.getMinIndex();
			if (edgeTo.get(v) != null) {
				mst.add(edgeTo.get(v));
				visit(v);
			}
		}
		//������С����������СȨֵ�ĺ�
		mstWeight = mst.get(0).getWeight();
		for (int i = 1; i < mst.size(); i++) {
			mstWeight += mst.get(i).getWeight();
		}
	}

	@SuppressWarnings("unchecked")
	private void visit(int v) {
		if (!marked[v]) {//�Ƿ�������ɫ��Ӫ
			marked[v] = true;//�����ɫ��Ӫ
			GraphIterator<W> it = g.getIterator(v);//������ýڵ����������нڵ�
			for (Edge<W> i = it.begin(); !it.end(); i = it.next()) {
				int w = i.other(v);//�õ���ýڵ�������һ���ڵ�
				Edge<Double> e = (Edge<Double>) i;
				if (!marked[w]) {//�Ƿ�������ɫ��Ӫ
					if (edgeTo.get(w) == null) {//�Ƿ���������
						imhEdge.insert(w,e.getWeight());
						edgeTo.set(w, e);
					} else if (e.getWeight() < edgeTo.get(w).getWeight()) {
						edgeTo.set(w, e);
						imhEdge.change(w, e.getWeight());
					}
				}
			}
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
