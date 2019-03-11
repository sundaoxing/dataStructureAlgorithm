package com.unionfind_data;
public class UnionFind_2 implements UnionFind {
	/*
	 * 	并查集：实现的第二个版本，优化Union操作
	 *  	原因：第一版：Union操作时间复杂度为O（n），需要优化
	 *  优化：
	 * 		1.静态数组实现，数组中存储的值就是当前元素指向下一个元素的索引
	 * 		2.每个元素看成一个节点，合并两个元素就是让其中一个节点指向另一个节点
	 * 		3.根节点：对应数组中，索引 == 该索引下存储的值
	 * 		4.属于同一个集合：拥有相同的根节点
	 * 
	 *  0	1	2	3	4	5	6	7	8	9----------父节点（初始时每个元素指向自己）
	 *  0	1	2	3	4	5	6	7	8	9----------元素（数组索引）
	 */
	private int [] parent;//父节点数组
	
	public UnionFind_2(int size) {
		parent = new int[size];
		for(int i=0;i<size;i++) {
			parent[i]=i;
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
		//不断查找索引的过程
		while(i != parent[i]) {
			i=parent[i];
		}
		return i;
	}
	/*	并：
	 *  思想：合并p，q，--》元素p指向元素q--》[p] == q
	 *  思路：
	 *  	1.查找元素p，q的根节点
	 *  	2.判断p，q根节点是否相同
	 *  		相同:直接返回
	 *  		不同：元素q指向元素p / 元素q指向元素p（两种方式可以）
	 *  0	1	1	8	3	0	5	1	8	8----------父节点
	 *  -------------------------------------
	 *  0	1	2	3	4	5	6	7	8	9----------元素（数组索引）
	 */
	@Override
	public void unionElements(int p, int q) {
		int pRoot=find(p);
		int qRoot=find(q);
		if(pRoot == qRoot) {
			return;
		}
		parent[qRoot] = pRoot;
	}

}
