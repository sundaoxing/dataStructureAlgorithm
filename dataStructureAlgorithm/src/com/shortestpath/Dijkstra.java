package com.shortestpath;

import java.util.ArrayList;

import com.weightgraph.Edge;
import com.weightgraph.Graph;
import com.weightgraph.GraphIterator;
import com.weightgraph.IndexMinHeap;

		/*单源最短路径算法：Dijkstra算法
		 * 前提：图中不能有负权值的边
		 * 时间复杂度：O（Elog（V））
		 */
public class Dijkstra<W> {
	private Graph<W> g;
	private int s;//源节点
	private double[] destTo;//距离数组：destTo[v]表示0节点（s）到v节点的距离（当前节点下最短）
	private boolean[] marked;//标记数组：marked[v]代表0节点是否到v节点的距离最短
	private ArrayList<Edge<Double>> from;//路径集合：表示从0节点到每个节点的最短路径集合

	public Dijkstra(Graph<W> g, int s) {
		this.g = g;
		this.s = s;
		destTo = new double[g.getN()];
		marked = new boolean[g.getN()];
		from = new ArrayList<Edge<Double>>(g.getN());
		for (int i = 0; i < g.getN(); i++) {
			destTo[i] = 0;//初始化，距离全为0
			marked[i] = false;//初始化，全没有找到距离最短的路径
			from.add(null);//初始化，没有最短路径
		}
		dijkstra(s);
	}

	/*Dijkstra 算法---贪心法
	 * 1.从源节点0开始，到自身距离为0，标记为是最短路径，插入最小索引堆中
	 * 2.遍历最小索引堆，取出最短路径的索引（节点）v，此时destTo[v]表示：s到v的最短距离
	 * 3.从v节点开始，遍历所有与v节点相连的节点w------------可以认为是松弛操作3，4，5步骤
	 * 4.判断w是否被marked
	 * 		否：再次判断from集合中有没有0节点到w的路径，或者（destTo[v]的距离+v-w边的权值）< dsetTo[w]
	 * 				1）.from集合中没有：说明0-w这是一条新的路径，destTo[w]就是destTo[v]+v-w边的权值
	 * 				2）.小于：说明找到了一条0-w的更短的路径，要更新destTo[w]的值
	 * 				5.维护最小索引堆：destTo[w]的值改变了，对应堆中w节点的data值也要改变
	 * 						1）.如果w节点在堆中原本就有值，则更新
	 * 						2）.否则，则插入进堆中		
	 */
	private void dijkstra(int s) {
		destTo[s]=0;//源节点s到自身的距离为0
		marked[s]=true;//源节点s到s的最短路径已找到
		/*最小索引堆：维护到源节点s的最小距离（堆首），有更短的会更新
		 * data ：destTo[s]-距离
		 * index：s,v....
		 */
		IndexMinHeap imh = new IndexMinHeap(g.getN());
		imh.insert(s, destTo[s]);
		while(!imh.isEmpty()) {
			int v= imh.getMinIndex();//取出源s节点相连的另一个节点（这里还是s节点本身）
			marked[v]=true;////源节点s到s的最短路径已找到
			GraphIterator<W> it =g.getIterator(v);
			for(Edge<W>i=it.begin();!it.end();i=it.next()) {
				@SuppressWarnings("unchecked")
				Edge<Double> e = (Edge<Double>) i;
				int w = i.other(v);//找到与源节点s相连的另一个节点
				if(!marked[w]) {
					if(from.get(w) ==null ||destTo[v]+e.getWeight()< destTo[w]) {
						destTo[w]=destTo[v]+e.getWeight();//更新s节点到w节点的距离
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
	//返回0到w节点的最小距离
	public double shortestPathTo(int w) {
		return destTo[w];
	}
	
	//判断0节点有没有到w节点的路径
	public boolean hasPathTo(int w) {
		return marked[w];
	}
	//返回0节点到w节点的最短路径集合
	public void shortestPath(int w,ArrayList<Edge<Double>> a) {
		a.clear();
		Edge<Double> e = from.get(w);
		while(e!=null) {
			a.add(e);
			e=from.get(e.other(w));
		}
	}
	//输出打印0节点到w节点的最短路径
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
