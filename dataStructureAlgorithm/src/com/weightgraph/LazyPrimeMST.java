package com.weightgraph;
			/*带权无向图
			 * V个节点
			 * 算法1：最小生成树问题
			 * 前提条件：连通图：
			 * 		在一个无向图G中，若从顶点 v到顶点  w有路径,从顶点w到v也有路径
			 * 		则v和w是联通的。如果图中任意两点都是连通的，那么图被称作连通图
			 *最小生成树：在图中,V个节点使用V-1条边连接起来，并且边上权值相加最小
			 *应用：电缆布线设计，网络设计，电路设计	
			 *解决思路：切分定理
			 *切分：把图中的节点分为两个部分，每一个部分即为切分
			 *横切边：如果一个边的两个端点，属于不同的切分阵营，则这个边为横切边
			 *切分定理：
			 *		给定任意切分，横切边中权值最小的边必然属于最小生成树
			 */

import java.util.ArrayList;

public class LazyPrimeMST<W> {
	private Graph<W> g;
	//借助最小堆，找出所有横切边中权值最小的横切边
	private MinHeap mhEdge;//优先最小堆：保存的是横切边--Edge
	//false:蓝色阵营；true：红色阵营
	private boolean [] marked;//标记数组：代表不同的切分阵营
	ArrayList<Edge<Double>> mst;//最小生成树的边
	Double mstWeight;//最小生成树的所有权值和
	
	public LazyPrimeMST(Graph<W> g , MinHeap mhEdge) {
		this.g=g;
		this.mhEdge=mhEdge;
		marked= new boolean[g.getN()];
		mst = new ArrayList<Edge<Double>>(g.getN());
		for(int i=0;i<g.getN();i++) {
			marked[i]=false;//初始全为蓝色阵营，每次都把一个节点划分进红色阵营
		}
		mst.clear();
		/*Lazy Prime算法（visit()）
		 * 时间复杂度：O（ Elog(E)）
		 * 1.以0节点为初始节点，并把它加入红色阵营，遍历和0相连的所有节点v,w,u...
		 * 2.判断节点v,w,u....属不属于红色阵营
		 * 		不属于红色阵营（可能是横切边）：则插入最小堆中
		 */
		visit(0);
		
		/*针对最小堆：
		 * 		1.获取最小堆的根节点（权值最小的边）
		 * 		2.判断这条边的两个端点是否属于不同的阵营
		 * 			属于同一个阵营：丢弃（continue即可）
		 * 			不属于：说明这条边是横切边，加入最小生成树中
		 * 				 3.在判断这条边的两个端点是否属于红色阵营
		 * 						不属于：从该节点开始从新遍历与该节点相连的所有节点v,w,u【Lazy Prime算法（visit()）】
		 */
		
		while(!mhEdge.isEmpty()) {
			Edge<Double> e = mhEdge.getMin();//获取堆中权值最小的边
			if(marked[e.getA()] == marked[e.getB()]) {//是否都属于红色阵营
				continue;
			}
			mst.add(e);//找到横切边，加入最小生成树
			if(!marked[e.getA()]) {
				visit(e.getA());//从该节点开始，重新切分
			}
			if(!marked[e.getB()]) {
				visit(e.getB());//从该节点开始，重新切分
			}
		}
		
		//计算最小生成树的最小权值的和
		mstWeight = mst.get(0).getWeight();
		for(int i=1;i<mst.size();i++) {
			mstWeight +=mst.get(i).getWeight();
		}
	}

	@SuppressWarnings("unchecked")
	private void visit(int v) {
		if(!marked[v]) {//是否属于蓝色阵营
			marked[v]=true;//加入红色阵营
			GraphIterator<W> it =  g.getIterator(v);
			for(Edge<W>i=it.begin();!it.end();i=it.next()) {
				if(!marked[i.other(v)]) {//节点v,w,u....属不属于红色阵营
					mhEdge.insert((Edge<Double>) i);
				}
			}
		}
		
	}

	//返回最小生成树
	public ArrayList<Edge<Double>> getMst() {
		return mst;
	}
	//返回最小生成树的最小权值和
	public Double getMstWeight() {
		return mstWeight;
	}	
}
