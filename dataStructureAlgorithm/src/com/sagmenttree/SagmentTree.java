package com.sagmenttree;
/*
 * 				线段树：
 * 	为什么要使用：待解决的问题是一个区间，并不是单个的节点，由多个相邻节点形成的区间
 * 			特点： 1.不是满二叉树，但可以使用null填充，来实现满二叉树的结构
 * 				 2.底层数据结构使用【数组】模拟满二叉树结构
 * 				 3.是一颗平衡二叉树（堆也是）
 * 				 4.区间树不考虑添加，区间长度固定（一般情况）
 * 				 5.根节点存储的信息==它的左右孩子存储信息的综合
 * 				 6.线段树是一种平衡二叉查找树，它将一个区间划分成一些单元区间
 * 				       每个单元区间对应线段树中的一个叶子结点。主要的处理思想是基于分治的思想
 * 		应用场景： 1.区间染色
 * 				2.区间查询
 * 				3.区间统计查询
 * 
 * 	 区间（二分）---------------------[0-9]:0----------------------------根节点：数组索引
 * 						/							\
 * 					[0-4]:1					 	   [5-9]:2
 * 				/		     \			   		/		 	 \
 * 			[0-2]:3		   [3-4]:4			[5-7]:5		 	[8-9]:6
 * 			/	  \		   /	 \      	/	   \     	/	   \
 * 		[0-1]:7	[2]:8	[3]:9	[4]:10	[5-6]:11  [7]:12  [8]:13  [9]:14
 *     /	\						   /	\					  /	  \
 * [0]:15 [1]:16					[5]:23 [6]:24				null  null---以null填充，构造完全二叉树	
 * 	^-[0][1][2][3][4][5]...[7]:代表data数组中的元素
 * 	   ^-7,8,9,10.........14 :代表tree数组中索引，元素和元素之间融合后的新元素是存放在tree数组中的
 * 对于tree数组空间大小的由来：
 * 				0			第一层：1个节点
 *			/       \	
 *		   1		 2		第二层：2个节点
 *		/     \	  /     \
 *	   3	   4 5		 6	第三层：4个节点
 *	....................	。。。。。。。。 第n层：2^(n-1)个节点
 *	如果区间元素个数为n，n=2^k-------->ze tree数组要分配2n个节点
 *			最后一层的节点数==前面所有节点数之和
 *		最坏情况：如果区间节点个数为n+1，n>2^k-------->则 tree数组要分配2n+2n个节点（再往下开一层空间）
 */
public class SagmentTree<E> {
	private Object[] data;//区间元素
	private Object[] tree;//完全二叉树结构
	private Merger<E> merger;//规定怎样融合两个区间的元素（求和，求最大/最小值，求平均等等）
	//构造方法：传入区间数组，融合规则
	public SagmentTree(E[] arr,Merger<E> merger) {
		data = new Object[arr.length];
		for(int i=0;i<arr.length;i++) {
			data[i]=arr[i];
		}
		tree = new Object[4*arr.length];
		this.merger=merger;
		//构建区间树：递归实现---------时间复杂度：O（log（n））
		buildSagmentTree(0,0,data.length-1);
	}
	/*
	 * 递归方法：void buildSagmentTree(int treeIndex, int l, int r)
	 * 递归终止条件：（递归到叶子节点）
	 * 		区间长度为0：l==r-----代表递归到了单个元素，可以创造区间，只是这个区间是一个元素而已
	 * 递归公式：（把[l-r]二分）
	 * 		buildSagmentTree(leftTreeIndex, l, mid);------左子树区间
	 * 		buildSagmentTree(rightTreeIndex, mid+1, r);---右子树区间
	 */
	//在满二叉树数组表示中，创造以treeIndex为根节点索引，[l-r]为区间的区间树（递归的宏观语义）
	@SuppressWarnings("unchecked")
	private void buildSagmentTree(int treeIndex, int l, int r) {
		if(l==r) {
			tree[treeIndex]=data[l];
			return;
		}
		int leftTreeIndex=leftChild(treeIndex);
		int rightTreeIndex=rightChild(treeIndex);
		int mid=l+(r-l)/2;
		buildSagmentTree(leftTreeIndex, l, mid);
		buildSagmentTree(rightTreeIndex, mid+1, r);
		//自用自定义的区间融合的方法（treeIndex的左右孩子合并方法）
		tree[treeIndex] = merger.merger((E)tree[leftTreeIndex], (E)tree[rightTreeIndex]);
	}
	//返回区间中元素的个数
	public int size() {
		return data.length;
	}
	//返回区间中索引为index的元素
	@SuppressWarnings("unchecked")
	public E get(int index) {
		if(index<0 || index >=data.length) {
			throw new RuntimeException("index illegal");
		}
		return (E) data[index];
	}
	//返回满二叉树数组表示中，以index为根节点的左孩子节点的索引
	public int leftChild(int index) {
		return 2*index+1;
	}
	//返回满二叉树数组表示中，以index为根节点的右孩子节点的索引
	public int rightChild(int index) {
		return 2*index+2;
	}
	
	//查询并返回区间树中[queryL-queryR]区间的值------递归实现---------时间复杂度：O（log（n））
	public E query(int queryL,int queryR) {
		if(queryL <0 || queryL>tree.length
			|| queryR<0 || queryR >tree.length|| queryL >queryR) {
			throw new RuntimeException("query index illegal");
		}
		return query(0,0,data.length-1,queryL,queryR);
	}
	/*
	 * 递归方法：E query(int treeIndex, int l, int r, int queryL, int queryR)
	 * 递归终止条件：
	 * 		查询区间==当前区间：queryL=l && queryR=r---》直接返回当前区间的值
	 * 递归公式：
	 * 		E query(leftTreeIndex, l, mid, queryL, mid)--------左子树区间
	 * 		E query(rightTreeIndex, mid+1, r, mid+1, queryR)---右子树区间
	 */
	//在满二叉树数组表示中，返回以treeIndex为根节点的[l,r]区间内的,查询[queryL,queryR]区间的值（递归的宏观语义）
	@SuppressWarnings("unchecked")
	private E query(int treeIndex, int l, int r, int queryL, int queryR) {
		if(queryL == l && queryR==r) {
			return (E) tree[treeIndex];
		}
		int leftTreeIndex = leftChild(treeIndex);
		int rightTreeIndex=rightChild(treeIndex);
		int mid = l+(r-l)/2;
		/*
		 * 区间查询分为3种情况：
		 * 	1.查询区间在当前区间的左侧：queryR <= mid
		 * 		直接到当前区间的左子树区间查询
		 * 	2.查询区间在当前区间的右侧侧：queryL >= (mid+1)
		 * 		直接到当前区间的右子树区间查询
		 *  3.查询区间在当前区间的左右两侧：
		 * 		分别到当前区间的左，右子树区间查询，并返回各自的结果，最后合并左右子树的结果即可
		 */
		if(queryR <= mid) {
			return query(leftTreeIndex, l, mid, queryL, queryR);
		}
		else if(queryL >= (mid+1)) {
			return query(rightTreeIndex, mid+1, r, queryL, queryR);
		}
		E leftResult = query(leftTreeIndex, l, mid, queryL, mid);
		E rightRrsult=query(rightTreeIndex, mid+1, r, mid+1, queryR);
		return merger.merger(leftResult, rightRrsult);
	}
	//更新index节点的值-----递归实现---------时间复杂度：O（log（n））
	public void update(int index,E e) {
		if(index <0 || index >=data.length) {
			throw new RuntimeException("index illegal");
		}
		data[index]=e;
		//更新tree数组（满二叉树）中index节点的值
		set(0,0,data.length-1,index,e);
	}
	/*
	 * 递归方法：void set(int treeIndex,int l,int r,int index,E e)
	 * 递归终止条件：
	 * 		找到index索引处的值：l==r
	 * 递归公式：
	 * 		set(leftTreeIndex, l, mid, index, e)---------左子树中
	 * 		set(rightTreeIndex, mid+1, r, index, e)------右子树中
	 */
	//在满二叉树的数组表示中，更新以treeIndex为根节点的[l-r]区间中索引为index的值（递归的宏观语义）
	@SuppressWarnings("unchecked")
	public void set(int treeIndex,int l,int r,int index,E e) {
		if(l==r) {
			tree[treeIndex]=e;
			return;
		}
		int leftTreeIndex=leftChild(treeIndex);
		int rightTreeIndex = rightChild(treeIndex);
		int mid = l + (r-l)/2;
		if(index <= mid) {
			set(leftTreeIndex, l, mid, index, e);
		}
		else {
			set(rightTreeIndex, mid+1, r, index, e);
		}
		/*
		 * index索引处更新完之后，根据业务逻辑，其父节点也要重新融合
		 */
		tree[treeIndex]=merger.merger((E)tree[leftTreeIndex], (E)tree[rightTreeIndex]);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(int i=0;i<tree.length;i++) {
			if(tree[i] !=null) {
				s.append(tree[i]+" ");
			}else {
				s.append(" null ");
			}
		}
		return s.toString();
	}
}
