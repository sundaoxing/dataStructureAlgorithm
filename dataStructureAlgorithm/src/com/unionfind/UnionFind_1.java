package com.unionfind;
/*
 * 并查集：
 * 		1：find(q)查找
 * 		2:union(p,q)合并
 * 			=》》isConnected（p，q）判断p和q是否连接
 * 很特别的树型结构
 * 作用：解决连接问题：
 * 		1：抽象网络的各个节点的连接状态（用户网络/路由网络/道路网络/航班网络。。。）
 * 		2：实现数学中的集合
 */
public class UnionFind_1 {
	/*第一种实现方式：数组
	 * 初始化
	 * 索引： 0,1,2,3,4,5,6,7,8,9       《=索引代表元素
	 * id： [0,1,2,3,4,5,6,7,8,9]	    《=数组表示连接情况（值相同，则表示元素之间是连接的）
	 */
	private int[] id;//元素数组
	private int count;//元素个数

	//初始化
	public UnionFind_1(int n) {
		id = new int[n];
		this.count = n;
		for (int i = 0; i < n; i++) {
			id[i] = i;
		}
	}

	//quick find：查找元素p的连接节点
	//时间复杂度：O（1）
	public int find(int p) {
		if (p >= 0 && p < count) {
			return id[p];
		}else {
			throw new ArrayIndexOutOfBoundsException();
		}
		
	}
	
	//判断两个元素是否连接（值相同，则表示元素之间是连接的）
	public boolean isConnected(int p,int q) {
		return find(p) == find(q);
	}
	
	
	/*连接两个元素（使他们的id[]相同）
	 * 时间复杂度：O（n）
	 * 索引： 0,1,2,3,4,5,6,7,8,9       《=索引代表元素
	 * id： [0,1,0,1,0,1,0,1,0,1]	
	 */
	public void unionElement(int p,int q) {
		int pId=find(p);//id[p]
		int qId=find(q);//id[q]
		if(pId == qId) {
			return;
		}
		//遍历id[]数组，把所有的id[p]都改为id[q]即可代表p，q相连
		for(int i=0;i<count;i++) {
			if(id[i]==pId) {
				id[i]=qId;
			}
		}
	}
}
