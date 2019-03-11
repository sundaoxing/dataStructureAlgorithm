package com.heap;
/*
 * 构建索引大顶堆
 * 底层结构：数组
 * 0, 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 10 	下标
 * 
 *   10 , 9 , 7 , 8 , 5 , 6 , 3 , 1 , 4 , 2		indexs堆（保存data的索引）
 *   
 *   15 , 17, 19, 13, 22, 16, 28, 30, 41, 62	data数据（保存data数据）
 *   
 *    8 , 10, 7 , 9 , 5 , 6 , 3 , 4 , 2 , 1		reverse索引（反向查找索引，保存的是indexs的索引）
 *    
 *    reverse[i] =>索引i在indexs[]中的位置
 *    indexs[i]=j <-> reverse[j]=i
 *   
 *    indexs[reverse[i]]==i  	reverse[1]==8 -> indexs[8]==1
 *   
 *    reverse[indexs[i]]==i 	indexs[1]==10 -> reverse[10]==1  
 * i为当前节点
 * parent 节点= i/2;
 * 左孩子 节点= i*2
 * 右孩子 节点= i*2+1
 * 最后一个非叶子节点索引：count/2
 */

import com.sort.SortHandler;

public class IndexMaxHeap {
	private int[] data;// 数据
	private int[] indexs;// data索引
	private int[] reverse;// indexs索引
	private int count;// 数据个数
	private int capacity;// 初始化data数组容量
	private SortHandler sortHandler = new SortHandler();

	// 初始化堆
	public IndexMaxHeap(int capacity) {
		data = new int[capacity + 1];
		indexs = new int[capacity + 1];
		reverse = new int[capacity + 1];
		for (int i = 0; i < capacity + 1; i++) {
			reverse[i] = 0;// 初始化，没有indexs索引(从1开始，0访问不到)
		}
		count = 0;
		this.capacity = capacity;
	}

	// heapify,优化构建最大堆的方法
	public IndexMaxHeap(int[] arr) {
		data = new int[arr.length + 1];
		for (int i = 0; i < arr.length; i++) {
			data[i + 1] = arr[i];
		}
		count = arr.length;
		// 先赋值，整体构建最大堆
		for (int k = count / 2; k >= 1; k--) {
			shiftDown(k);
		}
	}

	// 返回最大堆的元素个数
	public int getSize() {
		return count;
	}

	// 判断最大堆是否为空
	public boolean isEmpty() {
		return count == 0;
	}

	// 插入新元素（尾插）
	// 传入的i对用户而言，是从0索引开始的
	public void insert(int i, int item) {
		i += 1;
		data[i] = item;// 保存数据
		indexs[count + 1] = i;// 保存索引
		reverse[i] = count + 1;
		count++;
		shiftUp(count);// 重新调整为大顶堆
	}

	private void shiftUp(int k) {
		// 不断调整新插入元素的位置，判断其和父节点的大小
		while (k > 1 && data[indexs[k / 2]] < data[indexs[k]]) {
			sortHandler.Swap(indexs, k / 2, k);// 只交换索引，数据值不交换，只用于比较
			reverse[indexs[k / 2]] = k / 2;
			reverse[indexs[k]] = k;
			k /= 2;
		}
	}

	// 获取最大堆的最大值,并逻辑上删除该元素
	public int getMax() {
		if (!isEmpty()) {// 判断最大堆是否为空
			int max = data[indexs[1]];
			sortHandler.Swap(indexs, 1, count);
			reverse[indexs[1]] = 1;
			reverse[indexs[count]] = 0;// 逻辑上删除该元素
			count--;
			shiftDown(1);// 重新调整为最大堆
			return max;
		} else {
			return Integer.MAX_VALUE;
		}
	}

	private void shiftDown(int i) {
		while (i * 2 <= count) {// 判断是否有左孩子
			int j = i * 2;// 辅助指针：保存i节点左右孩子较大值的下标，默认左孩子较大
			if (j + 1 <= count && data[indexs[j + 1]] > data[indexs[j]]) {
				j = j + 1;
			}
			if (data[indexs[i]] >= data[indexs[j]]) {
				break;// 判断i节点和左右孩子间较大值的大小
			}
			sortHandler.Swap(indexs, i, j);
			reverse[indexs[i]] = i;
			reverse[indexs[j]] = j;
			i = j;
		}
	}

	// 获取最大堆的最大元素的索引（索引堆的新功能）
	public int getMaxIndex() {
		if (!isEmpty()) {// 判断最大堆是否为空
			int maxIndex = indexs[1] - 1;
			sortHandler.Swap(indexs, 1, count);
			count--;
			shiftDown(1);// 重新调整为最大堆
			return maxIndex;
		} else {
			return Integer.MAX_VALUE;
		}
	}

	// 根据索引直接获取data数据中数据（索引堆的新功能）
	public int getData(int i) {
		if (contain(i)) {
			return data[i + 1];
		}else {
			return Integer.MAX_VALUE;
		}
	}

	// 根据索引，修改data中该索引表示的元素，首先要判断i在堆中有没有对应的元素
	public void change(int i, int newItem) {
		if (contain(i)) {
			i += 1;
			data[i] = newItem;
			// 找到indexs[j]==i,j表示data[i]在堆中的位置
			int j = indexs[i];
			shiftUp(j);
			shiftDown(j);
			/*
			 * 因为我们使用了反向查找数组reverse，保存了indexs索引的位置，所以不用在遍历indexs
			 * 需要维护最大堆的性质(要在堆中找到newItem的位置) 找到indexs[j]==i,j表示data[i]在堆中的位置 for(int
			 * j=1;j<=count;j++) { if(indexs[j]==i) { shiftUp(j); shiftDown(j); return; } }
			 */
		}
	}

	//判断i在堆中有没有对应的元素
	private boolean contain(int i) {
		if (i + 1 >= 1 && i + 1 <= capacity) {
			return reverse[i + 1] != 0;
		} else {
			return false;
		}
	}
}
