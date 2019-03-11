package com.heap;
/*
 * 构建大顶堆
 * 底层结构：数组
 * [[i][m][n].........]arr数组，下标从0开始
 * [[] [i][m][n]......]data堆，下标从1开始
 * i为当前节点
 * parent 节点= i/2;
 * 左孩子 节点= i*2
 * 右孩子 节点= i*2+1
 * 最后一个非叶子节点索引：count/2
 */

import com.sort.SortHandler;

public class MaxHeap {
	private int [] data;//数据
	private int count;//数据个数
	private SortHandler sortHandler = new SortHandler();
	//初始化堆
	public MaxHeap(int capacity) {
		data=new int[capacity+1];
		count=0;
	}
	
	//heapify,优化构建最大堆的方法
	public MaxHeap(int [] arr) {
		data= new int [arr.length+1];
		for(int i=0;i<arr.length;i++) {
			data[i+1]=arr[i];
		}
		count=arr.length;
		//先赋值，整体构建最大堆
		for(int k=count/2;k>=1;k--) {
			shiftDown(k);
		}
	}
	
	//返回最大堆的元素个数
	public int getSize() {
		return count;
	}
	
	//判断最大堆是否为空
	public boolean isEmpty() {
		return count==0;
	}
	
	//插入新元素（尾插）
	public void insert(int item) {
		data[count+1]=item;
		count++;
		shiftUp(count);//重新调整为大顶堆
	}

	private void shiftUp(int k) {
		//不断调整新插入元素的位置，判断其和父节点的大小
		while(k>1 && data[k/2] < data[k]) {
			sortHandler.Swap(data, k/2, k);
			k/=2;
		}
	}
	
	//获取最大堆的最大值
	public int getMax() {
		if(!isEmpty()) {//判断最大堆是否为空
			int max=data[1];
			sortHandler.Swap(data, 1, count);
			count--;
			shiftDown(1);//重新调整为最大堆
			return max;
		}else {
			return Integer.MAX_VALUE;
		}
	}
	
	private void shiftDown(int i) {
		while(i*2<=count) {//判断是否有左孩子
			int j=i*2;//辅助指针：保存i节点左右孩子较大值的下标，默认左孩子较大
			if(j+1<=count && data[j+1]>data[j]) {
				j=j+1;
			}
			if(data[i]>=data[j]) {
				break;//判断i节点和左右孩子间较大值的大小
			}
			sortHandler.Swap(data, i, j);
			i=j;
		}
	}
}
