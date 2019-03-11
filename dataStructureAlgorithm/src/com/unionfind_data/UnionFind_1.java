package com.unionfind_data;
/*
 * 					并查集：UnionFind
 * 		特点：
 * 			1.底层实现：静态数组（逻辑上是一颗特殊的树：多叉树组成的森林，由孩子节点指向父节点）
 * 		应用：
 * 			1.查找节点所在的集合编号
 * 			2.解决连接问题：（判断两个节点是否相连）
 * 			3.数学中集合问题（合并两个集合）
 * 			4.Kruskal算法的优化（使用并查集优化最小生成树）
 */
public class UnionFind_1 implements UnionFind {
	/*
	 * 并查集实现的第一版本：静态数组实现--QuickFind版本
	 * 		思想：利用数组索引的特性。因为数组索引是从0开始，到数组长度-1
	 * 是固定的，可以把数组索引抽象成一个个元素，而数组存储的值就可以表示该元素在哪个集合中
	 * 属于同一个集合：集合编号相同
	 * 
	 * 0	1	2	3	4	5	6	7	8	9----------集合编号（初始时每个元素都在自己的集合中）
	 * 0	1	2	3	4	5	6	7	8	9----------元素（数组索引）
	 */
	private int [] id;
	public UnionFind_1(int size) {
		id=new int [size];
		for(int i=0;i<size;i++) {
			id[i]=i;//初始化
		}
	}
	//返回元素的个数
	@Override
	public int size() {
		return id.length;
	}
	
	/*
	 *   0	1	0	1	0	1	0	1	0	1----------集合编号
	 *  -------------------------------------
	 *   0	1	2	3	4	5	6	7	8	9----------元素（数组索引）
	 */
	//判断两个元素是否是相连的（是否属于同一个集合---时间复杂度：O（1）
	@Override
	public boolean isConnected(int p, int q) {
		return find(p) == find(q);
	}
	//返回元素i所在的集合编号-----QuickFind---时间复杂度：O（1）
	private int find(int i) {
		if(i<0 || i>=id.length) {
			throw new IndexOutOfBoundsException();
		}
		return id[i];
	}
	
	/*
	 *		并：时间复杂度：O（n）
	 *	思想：将元素p所在集合的所有元素，并到元素q所在的集合下
	 * 	思路： 1.获取p，q元素所对应的集合编号
	 * 		 2.判读p，q元素是否已经是属于同一个集合中：
	 * 			是：直接结束
	 * 			否：遍历数组，将所有属于p元素所在集合的元素，全部改成q元素所在的集合
	 * 
	 *  0	1	0	1	0	1	0	1	0	1----------集合编号
	 *  -------------------------------------
	 *  0	1	2	3	4	5	6	7	8	9----------元素（数组索引）
	 */
	@Override
	public void unionElements(int p, int q) {
		int pId=find(p);
		int qId=find(q);
		if(pId == qId) {
			return;
		}
		for(int i=0;i<id.length;i++) {
			if(id[i] == pId) {
				id[i]=qId;
			}
		}
	}

}
