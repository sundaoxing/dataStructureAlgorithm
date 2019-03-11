package com.graph;

import java.util.ArrayList;
import java.util.Stack;

/*深度优先遍历
 * 时间复杂度：
 * 			稀疏图--邻接表：O（V+E）
 * 			稠密图--邻接矩阵：O（V^2）
 * 应用1.获取一条路径，但不一定是最短路径
 */
public class DFS_Path {
	private Graph g;// 将要遍历的图
	@SuppressWarnings("unused")
	private int src;// 源节点，代表从一个节点开始遍历
	private boolean[] visited;// 标记数组，代表已被访问的节点
	private int[] from;// 路径数组，代表从哪一个节点访问到当前节点的

	// 初始化
	public DFS_Path(Graph g, int src) {
		this.g = g;
		if (src >= 0 && src < g.getN()) {
			this.src = src;
		}
		visited = new boolean[g.getN()];
		from = new int[g.getN()];
		for (int i = 0; i < g.getN(); i++) {
			visited[i] = false;//默认全没被访问
			from[i] = -1;//默认为-1；便于下面循环终止，范围[0-g.getN())
		}
		dfs(src);//深度优先遍历，从源节点开始
	}
	
	/*深度优先遍历dfs------借助递归实现
	 * 从源节点开始，依次遍历与源节点相连的节点
	 * 然后在从该节点开始，依次遍历与该节点相连的节点（递归）
	 * 第一次访问节点，标记为visited[true]
	 * 后续不在遍历该节点
	 */
	private void dfs(int v) {
		visited[v]=true;
		GraphIterator it = g.getIterator(v);
		for(int i=it.begin();!it.end();i=it.next()) {
			if(!visited[i]) {
				from[i]=v;//表示从v到i节点的路径
				dfs(i);
			}
		}
	}

	/*判断某节点有无到src节点的路径
	 * 只需要查看visited[v]是否为true即可
	 * 如果visited[v]为true，则代表src节点已访问过v节点
	 * 则肯定有路径
	 */
	public boolean hasPath(int v) {
		if (v >= 0 && v < g.getN()) {
			return visited[v];
		}
		return false;
	}
	
	/*获取从src节点到v节点的一条路径
	 * 根据from路径数组，反向遍历from（可以借助栈，也可以反向遍历ArrayList）
	 */
	// 0 1 2 3 4 5 6                节点
	//-1 0 0 5 3 0 4      from数组：from[v]表示前一个与v相连的节点
	public void path(int v ,ArrayList<Integer>a) {
		Stack<Integer> s = new Stack<>();
		int p=v;
		while(p != -1) {//from[src]=-1，没有更改过，说明已经到了src节点
			s.push(p);//入栈
			p=from[p];//由v节点从from中获取上一个与v相连的节点
		}
		a.clear();//防止多次调用path方法，导致以前的元素存在
		while(!s.isEmpty()) {
			a.add(s.peek());//出栈，并加入集合
			s.pop();//删除栈顶元素
		}
	}

	//输出并打印从src节点到v节点的一条路径
	public void showPath(int v) {
		for(int i=0;i<from.length;i++) {
			System.out.print(from[i]+" ");
		}
		System.out.println("-------------");
		ArrayList<Integer> a = new ArrayList<>();
		path(v, a);
		System.out.print(src+"->"+v+": ");
		for(int i=0;i<a.size();i++) {
			if(i == a.size()-1) {
				System.out.print(a.get(i));
			}
			else {
				System.out.print(a.get(i)+"->");
			}
		}
	}
	
}
