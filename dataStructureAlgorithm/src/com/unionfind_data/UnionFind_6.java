package com.unionfind_data;

public class UnionFind_6 implements UnionFind {
	private int [] parent;
	private int [] rank;
	
	public UnionFind_6(int size) {
		parent=new int [size];
		rank=new int[size];
		for(int i=0;i<size;i++) {
			parent[i]=i;
			rank[i]=1;
		}
	}
	@Override
	public int size() {
		return parent.length;
	}

	@Override
	public boolean isConnected(int p, int q) {
		return find(p) == find(q);
	}
	/*
	 * 在第五版上改变：
	 * 		find使用递归的方法,使得，每一个节点都只有一个根节点
	 * 		树的深度为2
	 * 	这样做，时间性能上不一定优于第五版，因为递归调用需要的时间可能更多
	 * 
	 * 递归方法：int find(int i)
	 * 递归终止条件：
	 * 		找到根节点：i==parent[i]
	 * 递归公式：
	 * 		int find(parent[i])
	 */
	//返回元素i的父节点（递归的宏观语义）
	private int find(int i) {
		if(i<0 || i>=parent.length) {
			throw new IndexOutOfBoundsException();
		}
		/*while(i != parent[i]) {
			parent[i]=parent[parent[i]];
			i=parent[i];
		}
		return i;*/
		if(i != parent[i]) {
			parent[i] = find(parent[i]);
		}
		return parent[i];
	}
	@Override
	public void unionElements(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);
		if(pRoot == qRoot ) {
			return;
		}
		if(rank[pRoot] < rank[qRoot]) {
			parent[pRoot] = qRoot;
		}
		else if(rank[pRoot] > rank[qRoot]) {
			parent[qRoot] = pRoot;
		}
		else {
			parent[qRoot] = pRoot;
			rank[pRoot] +=1;
		}
	}
}
