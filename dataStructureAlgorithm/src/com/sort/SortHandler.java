package com.sort;

/*
 * 算法稳定性：在排序前后，相同元素的相对位置不变------算法稳定
 * 否则：不稳定
 */
public class SortHandler {

	/*
	 * 随机生成个数为n，范围在L-R之间的数组
	 */
	public int[] randomArray(int n, int L, int R) {
		int[] arr = new int[n];
		if (L < R) {
			for (int i = 0; i < n; i++) {
				arr[i] = (int) (Math.random() * (R - L) + L);
			}
		}
		return arr;
	}

	public void Swap(int[] arr, int i, int j) {
		//注意：arr[i]和arr[j]不能相等，否则结果为0
		if (i != j) {
			arr[i] ^= arr[j];
			arr[j] ^= arr[i];
			arr[i] ^= arr[j];
		}
	}

	public void print(int[] arr) {
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
	}
	
	//判断该数组是否有序
	public boolean isSort(int [] arr) {
		for(int i=0;i<arr.length-1;i++) {
			if(arr[i]>arr[i+1]) {
				return false;
			}
		}
		return true;
	}
	
	//生成近乎有序的序列
	public int [] NearSortArray(int n,int m) {
		int [] arr = new int [n];
		for(int i=0;i<n;i++) {
			arr[i]=i;
		}
		for(int j=0;j<m;j++) {
			int x=(int) Math.random()*n;
			int y=(int)Math.random()*n;
			Swap(arr, x, y);
		}
		return arr;
	}
}
