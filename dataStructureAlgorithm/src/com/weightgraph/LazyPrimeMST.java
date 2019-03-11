package com.weightgraph;
			/*��Ȩ����ͼ
			 * V���ڵ�
			 * �㷨1����С����������
			 * ǰ����������ͨͼ��
			 * 		��һ������ͼG�У����Ӷ��� v������  w��·��,�Ӷ���w��vҲ��·��
			 * 		��v��w����ͨ�ġ����ͼ���������㶼����ͨ�ģ���ôͼ��������ͨͼ
			 *��С����������ͼ��,V���ڵ�ʹ��V-1�����������������ұ���Ȩֵ�����С
			 *Ӧ�ã����²�����ƣ�������ƣ���·���	
			 *���˼·���зֶ���
			 *�з֣���ͼ�еĽڵ��Ϊ�������֣�ÿһ�����ּ�Ϊ�з�
			 *���бߣ����һ���ߵ������˵㣬���ڲ�ͬ���з���Ӫ���������Ϊ���б�
			 *�зֶ���
			 *		���������з֣����б���Ȩֵ��С�ı߱�Ȼ������С������
			 */

import java.util.ArrayList;

public class LazyPrimeMST<W> {
	private Graph<W> g;
	//������С�ѣ��ҳ����к��б���Ȩֵ��С�ĺ��б�
	private MinHeap mhEdge;//������С�ѣ�������Ǻ��б�--Edge
	//false:��ɫ��Ӫ��true����ɫ��Ӫ
	private boolean [] marked;//������飺����ͬ���з���Ӫ
	ArrayList<Edge<Double>> mst;//��С�������ı�
	Double mstWeight;//��С������������Ȩֵ��
	
	public LazyPrimeMST(Graph<W> g , MinHeap mhEdge) {
		this.g=g;
		this.mhEdge=mhEdge;
		marked= new boolean[g.getN()];
		mst = new ArrayList<Edge<Double>>(g.getN());
		for(int i=0;i<g.getN();i++) {
			marked[i]=false;//��ʼȫΪ��ɫ��Ӫ��ÿ�ζ���һ���ڵ㻮�ֽ���ɫ��Ӫ
		}
		mst.clear();
		/*Lazy Prime�㷨��visit()��
		 * ʱ�临�Ӷȣ�O�� Elog(E)��
		 * 1.��0�ڵ�Ϊ��ʼ�ڵ㣬�����������ɫ��Ӫ��������0���������нڵ�v,w,u...
		 * 2.�жϽڵ�v,w,u....�������ں�ɫ��Ӫ
		 * 		�����ں�ɫ��Ӫ�������Ǻ��бߣ����������С����
		 */
		visit(0);
		
		/*�����С�ѣ�
		 * 		1.��ȡ��С�ѵĸ��ڵ㣨Ȩֵ��С�ıߣ�
		 * 		2.�ж������ߵ������˵��Ƿ����ڲ�ͬ����Ӫ
		 * 			����ͬһ����Ӫ��������continue���ɣ�
		 * 			�����ڣ�˵���������Ǻ��бߣ�������С��������
		 * 				 3.���ж������ߵ������˵��Ƿ����ں�ɫ��Ӫ
		 * 						�����ڣ��Ӹýڵ㿪ʼ���±�����ýڵ����������нڵ�v,w,u��Lazy Prime�㷨��visit()����
		 */
		
		while(!mhEdge.isEmpty()) {
			Edge<Double> e = mhEdge.getMin();//��ȡ����Ȩֵ��С�ı�
			if(marked[e.getA()] == marked[e.getB()]) {//�Ƿ����ں�ɫ��Ӫ
				continue;
			}
			mst.add(e);//�ҵ����бߣ�������С������
			if(!marked[e.getA()]) {
				visit(e.getA());//�Ӹýڵ㿪ʼ�������з�
			}
			if(!marked[e.getB()]) {
				visit(e.getB());//�Ӹýڵ㿪ʼ�������з�
			}
		}
		
		//������С����������СȨֵ�ĺ�
		mstWeight = mst.get(0).getWeight();
		for(int i=1;i<mst.size();i++) {
			mstWeight +=mst.get(i).getWeight();
		}
	}

	@SuppressWarnings("unchecked")
	private void visit(int v) {
		if(!marked[v]) {//�Ƿ�������ɫ��Ӫ
			marked[v]=true;//�����ɫ��Ӫ
			GraphIterator<W> it =  g.getIterator(v);
			for(Edge<W>i=it.begin();!it.end();i=it.next()) {
				if(!marked[i.other(v)]) {//�ڵ�v,w,u....�������ں�ɫ��Ӫ
					mhEdge.insert((Edge<Double>) i);
				}
			}
		}
		
	}

	//������С������
	public ArrayList<Edge<Double>> getMst() {
		return mst;
	}
	//������С����������СȨֵ��
	public Double getMstWeight() {
		return mstWeight;
	}	
}
