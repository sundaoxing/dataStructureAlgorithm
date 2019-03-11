package com.weightgraph;
			/*最小生成树
			 * 优化版：对Prime算法的优化
			 * 使用最小索引堆
			 */
import java.util.ArrayList;

public class PrimeMST<W> {
	private Graph<W> g;//图
	/*			最小索引堆：堆长：图中的节点数
	 * 索引index对应data中的最小边的权值
	 * 如果遇到更小的权值，则更新data即可，不必都加入堆中
	 */
	private IndexMinHeap imhEdge;//最小索引堆：只存放每个节点相连的横切边的权值
	private ArrayList<Edge<Double>> edgeTo;//存放与每个节点相连的权值最小的横切边
	//false:蓝色阵营；true：红色阵营
	private boolean[] marked;//标记数组：代表不同的切分阵营
	private ArrayList<Edge<Double>> mst;//最小生成树的边
	private double mstWeight;//最小生成树的所有权值和

	public PrimeMST(Graph<W> g, IndexMinHeap imhEdge) {
		this.g = g;
		this.imhEdge = imhEdge;
		edgeTo = new ArrayList<Edge<Double>>(g.getN());
		marked = new boolean[g.getN()];
		mst = new ArrayList<Edge<Double>>(g.getN());
		for (int i = 0; i < g.getN(); i++) {
			marked[i] = false;//初始全为蓝色阵营，每次都把一个节点划分进红色阵营
			edgeTo.add(null);//初始全为null,方便下面判断存不存在横切边
		}
		mst.clear();
		/*Prime算法：visit（）
		 * 时间复杂度：O（ Elog(V)）
		 * 1.以0节点为起始节点，加入红色阵营，并遍历所有与0节点相连的节点v,w,u...
		 * 2.判断v,w,u...节点是否已加入红色阵营
		 * 		不属于红色阵营：再判断横切边数组edgeTo中是否有这条边
		 * 					没有：把这条边的权值插入堆中，更新edgeTo中该节点的边
		 * 					有：判断当前边的权值是否小于已存在这条边的权值
		 * 							小于：则更新edgeTo中该节点的边，并更新堆中该节点的权值
		 */
		visit(0);

		/*针对最小索引堆：
		 * 1.获取最小索引堆的最小值的索引v（其实也就是图中的节点）
		 * 2.判断横切边数组edgeTo中是否有这条边
		 * 		有：则加入最小生成树mst中，在以此节点为起始节点，在此遍历与该节点相连的所有节点（visit(v)）
		 */
		while (!imhEdge.isEmpty()) {
			int v = imhEdge.getMinIndex();
			if (edgeTo.get(v) != null) {
				mst.add(edgeTo.get(v));
				visit(v);
			}
		}
		//计算最小生成树的最小权值的和
		mstWeight = mst.get(0).getWeight();
		for (int i = 1; i < mst.size(); i++) {
			mstWeight += mst.get(i).getWeight();
		}
	}

	@SuppressWarnings("unchecked")
	private void visit(int v) {
		if (!marked[v]) {//是否属于蓝色阵营
			marked[v] = true;//加入红色阵营
			GraphIterator<W> it = g.getIterator(v);//遍历与该节点相连的所有节点
			for (Edge<W> i = it.begin(); !it.end(); i = it.next()) {
				int w = i.other(v);//得到与该节点相连的一个节点
				Edge<Double> e = (Edge<Double>) i;
				if (!marked[w]) {//是否属于蓝色阵营
					if (edgeTo.get(w) == null) {//是否有这条边
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

	// 返回最小生成树
	public ArrayList<Edge<Double>> getMst() {
		return mst;
	}

	// 返回最小生成树的最小权值和
	public Double getMstWeight() {
		return mstWeight;
	}
}
