package com.unionfind;

public class UnionFind_2 {
	/*第二种实现方式：逻辑结构：树型；物理结构：数组
	 * 每一个元素看成一个节点，每个节点都有一个父指针，如果两个元素p，q相连，则表示p.parent->q
	 * 初始化:每个节点都指向自己
	 *    索引：   0,1,2,3,4,5,6,7,8,9       《=》索引代表元素
	 * parent： [0,1,2,3,4,5,6,7,8,9]	    《=》数组表示连接情况（保存的是其父节点的索引，值相同，则表示元素之间是连接的）
	 */
	private int [] parent;//父指针数组
	private int count;//元素个数
	//初始化
	public UnionFind_2(int n) {
		parent=new int [n];
		this.count=n;
		for(int i=0;i<n;i++) {
			parent[i]=i;
		}
	}
	
	//查找当前节点p的父节点（当前节点p == parent[p]时，p就是根节点）
	public int findParent(int p) {
		if(p>=0 && p < count) {
			while(p != parent[p]) {
				p=parent[p];
			}
			return p;
		}else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	//判断两个节点是否是连接的
	public boolean isConnected(int p,int q) {
		return findParent(p) == findParent(q);
	}
	
	//连接两个节点
	/*quick union：
	 * 时间复杂度：O（log（n））
	 *    索引：   0,1,2,3,4,5,6,7,8,9       《=索引代表元素
	 * parent： [1,1,1,8,3,0,5,1,8,8]	
	 */
	public void unionElement(int p,int q) {
		int pParent=findParent(p);//parent[p]
		int qParent=findParent(q);//parent[q]
		if(pParent == qParent) {
			return;
		}
		//只需要把p的父节点（根）指向q的父节点（根）即可
		parent[pParent]=qParent;
	}
}
