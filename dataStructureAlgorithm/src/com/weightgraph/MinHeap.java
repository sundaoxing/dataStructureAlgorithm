package com.weightgraph;

import java.lang.reflect.Array;

/*
 * 构建最小堆
 * 底层结构：数组
 * [[i][m][n].........]arr数组，下标从0开始
 * [[] [i][m][n]......]data堆，下标从1开始
 * i为当前节点
 * parent 节点= i/2;
 * 左孩子 节点= i*2
 * 右孩子 节点= i*2+1
 * 最后一个非叶子节点索引：count/2
 */

public class MinHeap {
	private Edge<Double> [] data;//数据
	private int count;//数据个数
	//初始化堆
	@SuppressWarnings("unchecked")
	public MinHeap(int capacity) {
		//具体泛型的数组
		data=(Edge<Double>[]) Array.newInstance(Edge.class, capacity+1);
		count=0;
	}
	
	//heapify,优化构建最小堆的方法
	@SuppressWarnings("unchecked")
	public MinHeap(Edge<Double> [] arr) {
		data= (Edge<Double>[]) Array.newInstance(Edge.class, arr.length+1);
		for(int i=0;i<arr.length;i++) {
			data[i+1]=arr[i];
		}
		count=arr.length;
		//先赋值，整体构建最小堆
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
	public void insert(Edge<Double> item) {
		data[count+1]=item;
		count++;
		shiftUp(count);//重新调整为小顶堆
	}

	private void shiftUp(int k) {
		//不断调整新插入元素的位置，判断其和父节点的大小
		while(k>1 && (data[k/2]).getWeight() > (data[k]).getWeight()) {
			Swap(data, k/2, k);
			k/=2;
		}
	}
	
	private void Swap(Edge<Double>[] data, int i, int j) {
		Edge<Double> t= data[i];
		data[i]=data[j];
		data[j]=t;
	}

	//获取最小堆的最小值
	public  Edge<Double> getMin() {
		if(!isEmpty()) {//判断最小堆是否为空
			Edge<Double> max=data[1];
			Swap(data, 1, count);
			count--;
			shiftDown(1);//重新调整为最小堆
			return max;
		}else {
			return null;
		}
	}
	
	private void shiftDown(int i) {
		while(i*2<=count) {//判断是否有左孩子
			int j=i*2;//辅助指针：保存i节点左右孩子较小值的下标，默认左孩子较小
			if(j+1<=count && data[j+1].getWeight()<data[j].getWeight()) {
				j=j+1;
			}
			if(data[i].getWeight()<=data[j].getWeight()) {
				break;//判断i节点和左右孩子间较大值的大小
			}
			Swap(data, i, j);
			i=j;
		}
	}
}
