package com.shortestpath;

import java.util.ArrayList;

import com.weightgraph.Edge;
import com.weightgraph.Graph;
import com.weightgraph.GraphIterator;
import com.weightgraph.IndexMinHeap;

		/*��Դ���·���㷨��Dijkstra�㷨
		 * ǰ�᣺ͼ�в����и�Ȩֵ�ı�
		 * ʱ�临�Ӷȣ�O��Elog��V����
		 */
public class Dijkstra<W> {
	private Graph<W> g;
	private int s;//Դ�ڵ�
	private double[] destTo;//�������飺destTo[v]��ʾ0�ڵ㣨s����v�ڵ�ľ��루��ǰ�ڵ�����̣�
	private boolean[] marked;//������飺marked[v]����0�ڵ��Ƿ�v�ڵ�ľ������
	private ArrayList<Edge<Double>> from;//·�����ϣ���ʾ��0�ڵ㵽ÿ���ڵ�����·������

	public Dijkstra(Graph<W> g, int s) {
		this.g = g;
		this.s = s;
		destTo = new double[g.getN()];
		marked = new boolean[g.getN()];
		from = new ArrayList<Edge<Double>>(g.getN());
		for (int i = 0; i < g.getN(); i++) {
			destTo[i] = 0;//��ʼ��������ȫΪ0
			marked[i] = false;//��ʼ����ȫû���ҵ�������̵�·��
			from.add(null);//��ʼ����û�����·��
		}
		dijkstra(s);
	}

	/*Dijkstra �㷨---̰�ķ�
	 * 1.��Դ�ڵ�0��ʼ�����������Ϊ0�����Ϊ�����·����������С��������
	 * 2.������С�����ѣ�ȡ�����·�����������ڵ㣩v����ʱdestTo[v]��ʾ��s��v����̾���
	 * 3.��v�ڵ㿪ʼ������������v�ڵ������Ľڵ�w------------������Ϊ���ɳڲ���3��4��5����
	 * 4.�ж�w�Ƿ�marked
	 * 		���ٴ��ж�from��������û��0�ڵ㵽w��·�������ߣ�destTo[v]�ľ���+v-w�ߵ�Ȩֵ��< dsetTo[w]
	 * 				1��.from������û�У�˵��0-w����һ���µ�·����destTo[w]����destTo[v]+v-w�ߵ�Ȩֵ
	 * 				2��.С�ڣ�˵���ҵ���һ��0-w�ĸ��̵�·����Ҫ����destTo[w]��ֵ
	 * 				5.ά����С�����ѣ�destTo[w]��ֵ�ı��ˣ���Ӧ����w�ڵ��dataֵҲҪ�ı�
	 * 						1��.���w�ڵ��ڶ���ԭ������ֵ�������
	 * 						2��.��������������		
	 */
	private void dijkstra(int s) {
		destTo[s]=0;//Դ�ڵ�s������ľ���Ϊ0
		marked[s]=true;//Դ�ڵ�s��s�����·�����ҵ�
		/*��С�����ѣ�ά����Դ�ڵ�s����С���루���ף����и��̵Ļ����
		 * data ��destTo[s]-����
		 * index��s,v....
		 */
		IndexMinHeap imh = new IndexMinHeap(g.getN());
		imh.insert(s, destTo[s]);
		while(!imh.isEmpty()) {
			int v= imh.getMinIndex();//ȡ��Դs�ڵ���������һ���ڵ㣨���ﻹ��s�ڵ㱾��
			marked[v]=true;////Դ�ڵ�s��s�����·�����ҵ�
			GraphIterator<W> it =g.getIterator(v);
			for(Edge<W>i=it.begin();!it.end();i=it.next()) {
				@SuppressWarnings("unchecked")
				Edge<Double> e = (Edge<Double>) i;
				int w = i.other(v);//�ҵ���Դ�ڵ�s��������һ���ڵ�
				if(!marked[w]) {
					if(from.get(w) ==null ||destTo[v]+e.getWeight()< destTo[w]) {
						destTo[w]=destTo[v]+e.getWeight();//����s�ڵ㵽w�ڵ�ľ���
						from.set(w, e);
						if(imh.contain(w)) {
							imh.change(w, destTo[w]);
						}
						else {
							imh.insert(w, destTo[w]);
						}
					}
				}
			}
		}
	}
	//����0��w�ڵ����С����
	public double shortestPathTo(int w) {
		return destTo[w];
	}
	
	//�ж�0�ڵ���û�е�w�ڵ��·��
	public boolean hasPathTo(int w) {
		return marked[w];
	}
	//����0�ڵ㵽w�ڵ�����·������
	public void shortestPath(int w,ArrayList<Edge<Double>> a) {
		a.clear();
		Edge<Double> e = from.get(w);
		while(e!=null) {
			a.add(e);
			e=from.get(e.other(w));
		}
	}
	//�����ӡ0�ڵ㵽w�ڵ�����·��
	public void showPath(int w) {
		ArrayList<Edge<Double>> a = new ArrayList<>();
		shortestPath(w, a);
		System.out.print(s+"->"+w+": "+s+"->");
		for(int i=a.size()-1;i>=0;i--) {
			if(i == 0) {
				System.out.print(a.get(i).getB());
			}
			else {
				System.out.print(a.get(i).getB()+"->");
			}
			
		}
		System.out.println();
	}
}
