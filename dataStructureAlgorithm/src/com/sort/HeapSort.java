package com.sort;
import org.junit.Test;

import com.heap.MaxHeap;

/*
 * 堆排序：构建最大堆，每次获取最大值，依次赋值给数组
 * 时间复杂度：O（nlog（n））
 * 算法稳定性：不稳定
 */
public class HeapSort {
	SortHandler sortHandler = new SortHandler();
	public void heap(int []arr) {
		MaxHeap maxHeap = new MaxHeap(arr.length);
		for(int i=0;i<arr.length;i++) {
			maxHeap.insert(arr[i]);
		}
		/*算法不稳定性原因：
		 * 每次获取最大值，都会把data[arr.length-1]和data[1]交换
		 * 当data[（arr.length-1）*2]==data[（arr.length-1）*2+1]，左右孩子相等时
		 * 相等元素相对位置发生改变
		 */
		for(int i=arr.length-1;i>=0;i--) {
			arr[i]=maxHeap.getMax();
		}
	}
	
	//优化构建最大堆的堆排序，时间复杂度为O（N）
	public void heapipy(int []arr) {
		MaxHeap maxHeap = new MaxHeap(arr);
		for(int i=arr.length-1;i>=0;i--) {
			arr[i]=maxHeap.getMax();
		}
	}
	
	/*在原数组中构建最大堆，然后交换最大值和数组末尾元素即可，不需要额外开辟新的空间
	 * 此时根节点的下标从0开始，
	 * [[0][1][2]...................]arr
	 * i为当前节点
	 * parent 节点= （i-1）/2;
	 * 左孩子 节点= i*2+1
	 * 右孩子 节点= i*2+2
	 * 最后一个非叶子节点索引：(arr.length-1)/2
	 */
	
	public void heapBetter(int [] arr) {
		for(int i=(arr.length-1)/2;i>=0;i--) {
			shiftDown(arr,arr.length,i);
		}
		for(int j=arr.length-1;j>0;j--) {
			sortHandler.Swap(arr, 0, j);
			shiftDown(arr,j,0);
		}
	}
	/**
	 * @param arr 最大堆 
	 * @param n 堆中元素个数
	 * @param k 当前节点（非叶子节点）
	 */
	private void shiftDown(int[] arr,int n, int k) {
		while((2*k+1)<n) {
			int j=2*k+1;
			if(j+1<n && arr[j+1]>arr[j]) {
				j+=1;
			}
			if(arr[k]>=arr[j]) {
				break;
			}
			sortHandler.Swap(arr, k, j);
			k=j;
		}
	}

	@Test
	public void test() {
		int n = 1000000;
		int[] arr = sortHandler.randomArray(n, 0, n);
		//int [] arr = sortHandler.NearSortArray(n, 200);
		//sortHandler.print(arr);
		//System.out.println();
		long start = System.currentTimeMillis();
		//heap(arr);
		//heapipy(arr);
		heapBetter(arr);
		long end = System.currentTimeMillis();
		//sortHandler.print(arr);
		System.out.println(sortHandler.isSort(arr));
		System.out.println("用时：" + (end - start) + "ms");
	}
}
