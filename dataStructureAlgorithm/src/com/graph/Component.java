package com.graph;
/*
 * 图的遍历
 * 1.深度优先遍历
 * 2.算出图的连通分量
 * 连通分量：连通分量和连通分量之间没有任何边相连，但是他们都属于一张图
 */
public class Component {
	private Graph g;//将要遍历的图
	private int ccount;//图中所含的连通分量
	private boolean visited[];//保存的是，是否已经遍历过的节点
	private int ids[];//保存的是，哪些节点是相连接的（ids[]中值一样，代表相连）
	
	public Component(Graph g) {
		this.g=g;
		this.ccount=0;
		this.visited=new boolean [g.getN()];
		this.ids=new int [g.getN()];
		for(int i=0;i<visited.length;i++) {
			visited[i]=false;//初始化为false，即全没有被访问过
			ids[i]=-1;
		}
	}
	
	/*（递归）深度优先遍历
	 * 思想:从一个节点出发，遍历他的所有相连接的节点，在一次遍历每一个相连接的节点
	 * 如果已被visited过了，就跳过
	 */
	public void iteratorDFS() {
		for(int i=0;i<g.getN();i++) {
			if(!visited[i]) {
				dfs(i);
				ccount++;//统计图中连通分量
			}
		}
	}

	private void dfs(int v) {
		ids[v]=ccount+1;//如果v,v'连接，则在ids[]中值是相同的
		visited[v]=true;//标记为已访问
		/*根据传入的g的类型，Spare/稀疏图,Dense/稠密图
		 * 选择相应的迭代器来进行遍历
		 */
		GraphIterator it =g.getIterator(v);
		for(int i=it.begin();!it.end();i=it.next()) {
			if(!visited[i]) {
				dfs(i);
			}
		}	
	}

	//返回该图的连通分量
	public int getCcount() {
		return ccount;
	}
	
	//判断两个节点是否相连
	public boolean isConnected(int v,int w) {
		if(v>=0 && v<g.getN()) {
			if(w>=0 && w<g.getN()) {
				return ids[v]==ids[w];
			}
		}
		return false;
	}
}
