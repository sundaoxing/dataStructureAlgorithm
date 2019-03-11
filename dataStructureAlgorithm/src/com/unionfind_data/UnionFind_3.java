package com.unionfind_data;

public class UnionFind_3 implements UnionFind {
/*
 * 	并查集：改进版，在第二个版本上改进
 * 		原因：第二版的Union操作，会造成树的深度很大，甚至退化成链表，导致find操作时间复杂度过高，需要优化
 * 	优化:
 * 		1.加入一些人为限定条件，使其避免深度过大
 * 		2.需要辅助数组，来维护树的深度
 *  1	1	1	1	1	1	1	1	1	1----------以当前索引为根节点的集合的元素个数
 *  0	1	2	3	4	5	6	7	8	9----------父节点（初始时每个元素指向自己）
 *  0	1	2	3	4	5	6	7	8	9----------元素（数组索引）
 */
	private int [] parent;//父节点
	private int [] size;//size[i]表示：根节点为i的集合的元素个数
	public UnionFind_3(int siz) {
		parent= new int[siz];
		size = new int[siz];
		for(int i=0;i<siz;i++) {
			parent[i]=i;
			size[i]=1;//初始，默认每个元素就是一颗树，高度就是1
		}
	}
	@Override
	public int size() {
		return parent.length;
	}

	@Override
	public boolean isConnected(int p, int q) {
		return find(p)==find(q);
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
			i=parent[i];
		}
		return i;
	}

	/*
	 * 并：时间复杂度：O（h），h为树的高度
	 * 思想：size小的树的根节点指向size大的树的根节点（防止合并后，树的深度过大）
	 * 思路：
	 * 		1.查找元素p，q的根节点
	 *  	2.判断p，q根节点是否相同
	 *  		相同:直接返回
	 *  		不同：判断p，q根节点的size大小
	 *  			 若 p根<q根（size）
	 *  					p根指向q根，维护size数组
	 *  			若 p根>=q根（size）
	 *  					q根指向p根，维护size数组
	 *  1	1	1	1	1	2	1	1	3	1----------指向自己的其他元素个数
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
		if(size[pRoot] < size[qRoot]) {
			parent[pRoot] = qRoot;
			size[qRoot] +=size[pRoot];
		}else {
			parent[qRoot]=pRoot;
			size[pRoot]+=size[qRoot];
		}
	}
}
