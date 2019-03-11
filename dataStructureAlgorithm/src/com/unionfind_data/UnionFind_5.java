package com.unionfind_data;

public class UnionFind_5 implements UnionFind {
	/*
	 * 并查集：对第四版的改进：
	 * 		原因：随着元素的增多，树的深度不可避免的加深，这时，需要路径压缩
	 * 优化：
	 * 		1.路径压缩：当前节点指向当前节点的父节点的父节点（跨代指向）
	 * 			parent[i]=parent[parent[i]];
	 * 
	 * 		rank：秩（序）：说是深度，并不准确，其实就是每个节点的序，按照序的大小合并两棵树
	 */
	private int [] parent;//父节点
	private int [] rank;//rank[i]:以i为根节点的树的深度（加入压缩路径后，不是准确的深度，而是大概的深度）
	
	public UnionFind_5(int size) {
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
	 * 查：时间复杂度：O（log（*n））
	 * 				0					n<=1
	 * log（*n）={	
	 * 				1+log（*log（n））	n>1
	 * 近乎O（1）级别
	 * 思想：迭代，迭代的终止条件：该元素是根节点
	 * 思路：
	 * 		1.判断元素和它指向的元素是否相等
	 * 			相等：就是根节点，直接返回元素值
	 * 			不相等：说明不是根节点，还有父节点
	 * 				  进行路径压缩
	 * 				  从父节点出发，向上找父节点的父节点
	 *  0	1	1	8	3	0	5	1	8	8----------父节点
	 *  -------------------------------------
	 *  0	1	2	3	4	5	6	7	8	9----------元素（数组索引）
	 */
	private int find(int i) {
		if(i<0 || i>=parent.length) {
			throw new IndexOutOfBoundsException();
		}
		while(i != parent[i]) {
			parent[i]=parent[parent[i]];//路径压缩
			i=parent[i];
		}
		return i;
	}
	/*
	 * 并：时间复杂度 O（log（*n））
	 * 思想：rank小的树的根节点指向rank大的树的根节点（防止合并后，树的深度过大）
	 * 思路：
	 * 		1.查找元素p，q的根节点
	 *  	2.判断p，q根节点是否相同
	 *  		相同:直接返回
	 *  		不同：判断p，q根节点的rank大小
	 *  			 若 p根<q根（rank）
	 *  					p根指向q根
	 *  			若 p根>q根（rank）
	 *  					q根指向p根
	 *  			若 p根=q根（rank）
	 *  					q根指向p根 / p根指向q根（两种方式都可以）
	 *  					维护rank数组
	 *  1	1	1	1	1	2	1	1	3	1----------rank
	 *  0	1	2	8	3	5	5	7	8	9----------父节点（初始时每个元素指向自己）
 	 *  0	1	2	3	4	5	6	7	8	9----------元素（数组索引）
	 */
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
