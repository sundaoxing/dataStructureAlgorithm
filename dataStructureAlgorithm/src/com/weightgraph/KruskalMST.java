package com.weightgraph;
		/*最小生成树
		 * Kruskal算法
		 * 使用最小堆
		 */
import java.util.ArrayList;

import com.unionfind.UnionFind_5;

public class KruskalMST<W> {
	@SuppressWarnings("unused")
	private Graph<W> g;
	private ArrayList<Edge<Double>> mst;//最小生成树
	private Double mstWeight;//最小生成树的所有权值和

	@SuppressWarnings("unchecked")
	public KruskalMST(Graph<W> g) {
		this.g = g;
		mst = new ArrayList<>(g.getN());
		MinHeap mh = new MinHeap(g.getM());
		
		/*Kruskal算法思想
		 * 时间复杂度：O（ Elog(E)）
		 * 对一个连通无向图中边的权值进行排序，每次取最小权值的边，
		 * 但不能构成闭环，等取到图的顶点个数-1条边的时候，
		 * 这些边便构成了这个图的最小生成树
		 * 有两个难点：1.对边的权值排序：借助最小堆进行排序，每次取堆的堆首（最小值）
		 * 			2.如何保证不构成闭环：采用并查集
		 * 					在最小生成树添加边的时候，把这条边的两个端点放入并查集中合并，让他们有共同的根节点
		 * 					在下次添加边的时，再判断边的两个端点是否相连（是否具有相同的根节点）
		 */
		
		//把图中所有的边放入堆中
		for (int v = 0; v < g.getN(); v++) {
			GraphIterator<W> it = g.getIterator(v);
			for (Edge<W> w = it.begin(); !it.end(); w = it.next()) {
				if (w.getA() < w.getB()) {//对于无向图，每条边被存了两次，这里只要一次即可
					mh.insert((Edge<Double>) w);
				}
			}
		}
		UnionFind_5 uf = new UnionFind_5(g.getN());
		while (!mh.isEmpty() && mst.size() < g.getN()-1) {
			Edge<Double> e = mh.getMin();//取出最小权值的边
			if (uf.isConnected(e.getA(), e.getB())) {//判断两个顶点是否相连
				continue;
			}
			mst.add(e);//不相连，则加入最小生成树
			uf.unionElement(e.getA(), e.getB());//再把他们设置成相连
		}

		// 计算最小生成树的最小权值的和
		mstWeight = mst.get(0).getWeight();
		for (int i = 1; i < mst.size(); i++) {
			mstWeight += mst.get(i).getWeight();
		}
	}

	// 返回最小生成树
	public ArrayList<Edge<Double>> getMst() {
		return mst;
	}

	// 返回最小生成树的最小权值和
	public Double getMstWeight() {
		return mstWeight;
	}

}
