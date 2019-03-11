package com.heap_data;

import com.array.MyArray;
/*		
 * 						堆：最大堆（最小堆也可以如此表示，大小是相对的，具体看compareTo（）方法中定义的优先级大小）
 * 对应树结构：---逻辑结构：二叉堆--【完全二叉树】
 * 						3（0）--------------data数组的第一个元素代表根节点
 * 					/		\
 * 	       根节点--------2（1）	 1（2）----------（）里表示数组索引
 * 				 /	 \		
 * 	左孩子节点-----0（3） 2（3）-----------------右孩子节点
 * 		
 * 	         特点：1.底层数据结构：动态数组模拟完全二叉树的
 * 		  二叉树：
 * 			2.只有一个根节点
 * 			3.每个节点最多有两个孩子
 * 			4.每个节点最多有一个父节点
 * 		性质：
 * 			5.堆中任意节点的（优先级）值，总是不大于其父节点的值（父节点（优先级）大于等于其左右孩子节点）
 * 
 * 	 在实现上有一个技巧，可以把堆中元素一层一层存储，放入数组中，即如下
 * 				0	1	2	3	4		-数组索引
 * 				3	2	1	0	2		-数据data
 * 		应用：
 * 			1.排序--堆排序
 * 			2.优先队列的实现
 */
public class MaxHeap<E extends Comparable<E>> {
	private MyArray<E> data;//底层数据结构-动态数组
	//带初始容量的构造
	public MaxHeap(int capacity) {
		data = new MyArray<>(capacity);
	}
	//无参构造
	public MaxHeap() {
		data = new MyArray<>();
	}
	//把转化数组为初始堆的构造方法（heapify）
	public MaxHeap(E[] arr) {
		data = new MyArray<>(arr);
		/*
		 * heapify：将任意数组整理成堆
		 * 时间复杂度:O(n)
		 * 		思想：从最后一个元素父节点开始，进行shfitDown操作，到根节点为止
		 */
		for(int k = parent(data.size()-1); k>=0;k--) {
			siftDown(k);
		}
	}
	//返回堆中元素的个数
	public int size() {
		return data.size();
	}
	//判断堆是否为空
	public boolean isEmpty() {
		return data.isEmpty();
	}
	//返回完全二叉树的数组表示中，节点的父节点索引
	private int parent(int index) {
		if(index ==0) {//从索引0节点开始，索引0节点为根节点，没有父节点，需特殊处理
			throw new RuntimeException("index-0 no parent");
		}
		return (index-1) / 2;
	}
	//返回完全二叉树的数组表示中，节点的左孩子节点索引
	private int leftChild(int index) {
		return index*2+1;
	}
	//返回完全二叉树的数组表示中，节点的右孩子节点索引
	/*private int rightChild(int index) {
		return index*2+2;
	}*/
	//添加元素（实质：数组尾插元素）------时间复杂度O（lon（n））
	public void add(E item) {
		data.addLast(item);//尾插
		siftUp(data.size()-1);//新元素添加后，可能破坏了堆的性质，需要检查维护，向上调整
	}
	/*
	 * siftUp过程：实质：元素不满足堆的性质（父节点（优先级）大于等于子节点）
	 *					3（0）
	 * 					/		\
	 * 	               2（1）	 1（2）
	 * 				 /	 \		
	 * 				0（3） 4（3）---------新添加的元素4。不满足堆的性质，需要向上调整
	 * 		3	3	1	0	4           data
	 * 		0	1	2	3	4			索引
	 * 	思想：新添加元素与该元素的父节点比较
	 * 			如果大：则交换，
	 * 			如果小：则不做处理
	 * 		循环向上迭代，一直比较到根节点为止
	 */
	private void siftUp(int index) {
		while(index >0 && data.get(index).compareTo(data.get(parent(index))) >0) {
			data.swap(index, parent(index));
			index=parent(index);//向上迭代
		}
	}
	//返回并删除堆中的根节点（最大元素）------时间复杂度O（lon（n））
	public E extractMax() {
		E maxItem = lookMax();//检查有没有最大值
		data.swap(0, data.size()-1);//首尾元素交换
		data.removeLast();//删除数组尾元素
		siftDown(0);//数组末尾元素调整到数组头部后，可能破环堆的性质，需要向下调整
		return maxItem;
	}
	//查看堆是否有最大值
	public E lookMax() {
		if(data.isEmpty()) {
			throw new RuntimeException("Empty");
		}
		return data.get(0);
	}
	/*
	 * siftDown过程：实质：元素不满足堆的性质（父节点（优先级）大于等于子节点）
	 *		3（0）------1.取出元素3				1（0）---------4.元素1不满足堆性质，需要向下调整
	 * 	 /		\							/		 \
	 * 	2（1）	 1（2）			-------->  2(1)		  1(2)	
	 * 	/	\							  /    \     
	 * 0（3） 1（3）-----2.交换元素1和3的位置       0(3)   3(0)---------3.删除元素3
	 * 		1	2	1	0           data
	 * 		0	1	2	3			索引
	 * 	思想：根节点元素与该元素的左，右孩子节点中的较大值比较
	 * 			1.左右孩子比较，保留大的节点的索引
	 * 			2.根节点在和较大的节点比较
	 * 				若大：则不处理，满足堆性质，结束shiftDown过程
	 * 				若小：则交换
	 * 		循环向下迭代，一直到最后一个节点为止
	 */
	private void siftDown(int k) {
		while(leftChild(k) < data.size()) {
			int j=leftChild(k);//左孩子
			if(j+1 < data.size() && data.get(j).compareTo(data.get(j+1)) <0) {
				j++;//左右孩子中较大者
			}
			if(data.get(k).compareTo(data.get(j)) >=0) {
				break;//已经满足堆性质，不需要调整
			}
			data.swap(k, j);
			k=j;//向下迭代
		}
	}
	//取出最大元素后，再在根节点位置放入新的元素，再调向下整堆，满足堆的性质
	public E replace(E item) {
		E maxItem = lookMax();
		data.set(0, item);//替换堆顶的元素
		siftDown(0);
		return maxItem;
	}
}
