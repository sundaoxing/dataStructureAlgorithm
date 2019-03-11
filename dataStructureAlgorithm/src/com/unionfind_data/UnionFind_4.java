package com.unionfind_data;

public class UnionFind_4 implements UnionFind {
	/*
	 * 并查集：对第三版的改进
	 * 		原因：第三版会出现如下这种情况：
	 *  	1	1	1	1	1	1	1	6	3	1
	 * 		7	7	7	8	3	7	7	7	8	9----------父节点（初始时每个元素指向自己）
	 *  	0	1	2	3	4	5	6	7	8	9----------元素（数组索引）
	 *  	此时：对于根节点7来说，size[7]> size[8]的，但是，[7]的深度为2,[8]的深度为3
	 *  	按照第三版的节点数来划分，不够优化
	 *  优化：
	 *  	1.使用rank数组来替代size数组
	 *  	2.rank数组：rank[i]根节点为i的树的高度
	 */
	private int [] parent;//父节点
	private int []rank;//rank[i]:以i为根节点的树的深度
	
	public UnionFind_4(int size) {
		parent = new int [size];
		rank = new int [size];
		for(int i=0;i<size;i++) {
			parent[i]= i;
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
	 * 查：时间复杂度：O（h），h为树的高度
	 * 思想：迭代，迭代的终止条件：该元素是根节点
	 * 思路：
	 * 		1.判断元素和它指向的元素是否相等
	 * 			相等：就是根节点，直接返回元素值
	 * 			不相等：说明不是根节点，还有父节点
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
			i = parent[i];
		}
		return i;
	}
	/*
	 * 并：时间复杂度：O（h），h为树的高度
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
		int pRoot=find(p);
		int qRoot=find(q);
		if(pRoot == qRoot) {
			return;
		}
		if(rank[pRoot] < rank[qRoot]) {
			parent[pRoot] = qRoot;
		}else if(rank[pRoot] > rank[qRoot]) {
			parent[qRoot] = pRoot;
		}else {
			parent[pRoot] = qRoot;
			rank[qRoot]+=1;
		}
	}

}
