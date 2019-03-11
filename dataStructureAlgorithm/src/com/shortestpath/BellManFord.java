package com.shortestpath;

import java.util.ArrayList;

import com.weightgraph.Edge;
import com.weightgraph.Graph;
import com.weightgraph.GraphIterator;

/*单源最短路径算法：Bellman-Frod
		 * 用于处理：带有负权值的图，图为有向图
		 * 不能处理带有负权值的环，但可以判断图中有没有负权环
		 * 时间复杂度：O（E*V）
		 * 
		 * 思想：如果一个图没有负权环，
		 * 		从节点v到节点w的最短路径，最多经过N个节点（图的所有节点），N-1条边
		 * 		如果节点w经过两次，则存在负权环
		 * 松弛操作：以前是从源节点开始到另一个节点的直连边使路径最小，现在尝试两条边，使路径和最小
		 */
public class BellManFord<W> {
	private Graph<W> g;
	private int s;
	private double[] destTo;//距离数组：destTo[v]表示0节点到v节点的当前最短路径
	private ArrayList<Edge<Double>> from;//路径集合：表示从0节点到每个节点的最短路径集合
	private boolean hasNegativeCycle;//是否含有负权环

	public BellManFord(Graph<W> g, int s) {
		this.g = g;
		this.s = s;
		destTo = new double[g.getN()];
		from = new ArrayList<Edge<Double>>();
		for (int i = 0; i < g.getN(); i++) {
			destTo[i] = 0;//初始化，距离全为0
			from.add(null);//初始化，没有最短路径
		}

		bellmanford();
	}

	/*bellman-ford 算法思想：
	 * 对所有的节点进行N-1次松弛操作，理论上就找到了从源节点到其他节点的最短路径
	 * 如果还可以进行松弛操作，则说明图中有负权环
	 * 松弛操作：
	 * 		1.从当前节点v出发，遍历所有与该节点相连的节点w,u...
	 * 		2.如果存在一条边e的权值满足destTo[v](源节点到v节点的距离)+e.getWeight < destTo[w](源节点到w节点的距离)
	 * 				则，找到一条源节点到w节点的当前最短路径
	 */
	private void bellmanford() {
		for (int pass = 1; pass < g.getN(); pass++) {
			for (int i = 0; i < g.getN(); i++) {//松弛操作
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
		//再进行一次松弛操作：如果可以，则说明有负权环，返回true
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
	//是否含有负权环
	public boolean hasNegativeCycle() {
		return hasNegativeCycle;
	}
	//返回0到w节点的最小距离
	public double shortestPathTo(int w) {
		return destTo[w];
	}
	//判断0节点有没有到w节点的路径
	public boolean hasPathTo(int w) {
		return from.get(w) != null;
	}
	//返回0节点到w节点的最短路径集合
	public void shortestPath(int w, ArrayList<Edge<Double>> a) {
		Edge<Double> e = from.get(w);
		while (e != null) {
			a.add(e);
			e = from.get(e.other(w));
		}
	}

	// 输出打印0节点到w节点的最短路径
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
