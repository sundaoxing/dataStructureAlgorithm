package com.graph;

import java.util.ArrayList;
import java.util.LinkedList;
		/*广度优先遍历
		 * 时间复杂度：
		 * 			稀疏图--邻接表：O（V+E）
		 * 			稠密图--邻接矩阵：O（V^2）
		 * 应用1.获取一条路径，可以是最短路径
		 */
public class BFS_Path {
	private Graph g;//将要遍历的图
	private int s;//源节点，代表从这个节点开始遍历
	private boolean[] visited;//标记数组：代表已访问过的节点
	private int[] from;//路径数组：代表从哪一个节点到当前节点
	private int[] ord;//距离数组：代表当前节点到源节点s的距离（直连为1，经过一个节点再+1）

	//初始化
	public BFS_Path(Graph g, int s) {
		this.g = g;
		if(s>=0 && s<g.getN()) {
			this.s = s;
		}
		visited = new boolean[g.getN()];
		from = new int[g.getN()];
		ord = new int[g.getN()];
		for (int i = 0; i < g.getN(); i++) {
			visited[i] = false;//默认全没有被访问过
			from[i] = -1;//默认全为-1
			ord[i] = -1;//默认全为-1
		}
		bfs(s);//从源节点开始广度优先遍历
	}

	/*广度优先遍历-bfs----借助队列来实现
	 * 从源节点（标记为已访问过，距离为0）开始，依次遍历所有与源节点相连的节点，入队列，标记为已访问过，距离+1
	 * 然后从队列的队首开始，依次遍历所有与队首相连的节点，入队列，标记为已访问过，距离+1
	 * 直至队列为空，遍历结束
	 */
	private void bfs(int v) {
		LinkedList<Integer> queue= new LinkedList<>();
		queue.offer(v);
		visited[v] = true;//标记为已访问过
		ord[v] = 0;//初始源节点距离
		while (!queue.isEmpty()) {
			int temp = queue.poll();
			GraphIterator it = g.getIterator(temp);
			for (int i = it.begin(); !it.end(); i = it.next()) {
				if (!visited[i]) {
					queue.offer(i);
					visited[i] = true;
					from[i]=temp;//记录下从temp到i的路径
					ord[i] = ord[temp] + 1;//记录从源节点s到i节点的距离
				}
			}
		}
	}
	
	/*判断某节点有无到src节点的路径
	 * 只需要查看visited[v]是否为true即可
	 * 如果visited[v]为true，则代表src节点已访问过v节点
	 * 则肯定有路径
	 */
	public boolean hasPath(int v) {
		if(v>=0 && v<g.getN()) {
			return visited[v];
		}
		return false;
	}
	
	/*获取从src节点到v节点的一条路径（可以是最短路径）
	 * 根据from路径数组，反向遍历from（可以借助栈，也可以反向遍历ArrayList）
	 */
	// 0 1 2 3 4 5 6                节点
	//-1 0 0 5 5 0 0 		from路径数组 ;from[v]表示前一个与v相连的节点
	public void path(int v,ArrayList<Integer> a) {
		a.clear();//防止多次调用path方法，导致以前的元素存在
		int p=v;
		while(p!=-1) {
			a.add(p);//路径是反向保存的，输出时要反向输出
			p=from[p];//由v节点从from中获取上一个与v相连的节点
		}
	}
	//输出并打印从src节点到v节点的一条路径
	public void showPath(int v) {
		for(int i=0;i<from.length;i++) {
			System.out.print(from[i]+" ");
		}
		System.out.println();
		for(int i=0;i<ord.length;i++) {
			System.out.print(ord[i]+" ");
		}
		ArrayList<Integer> a = new ArrayList<>();
		path(v, a);
		System.out.print(s+"->"+v+": ");
		for(int i=a.size()-1;i>=0;i--) {//反向输出
			if(i == 0) {
				System.out.print(a.get(i));
			}
			else {
				System.out.print(a.get(i)+"->");
			}
		}
	}
	//返回节点v到源节点s的距离
	// 0 1 2 3 4 5 6                节点
	// 0 1 1 2 2 1 1	   ord距离数组
	public int length(int v) {
		return ord[v];
	}
}
