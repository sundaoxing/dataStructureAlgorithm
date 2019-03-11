package com.binarySearch;

/*
 * 二分查找：
 * 必要条件：有序序列
 */
public class BinarySearch {
	public int binarySearch(int [] arr,int target) {
		/* [0，1，2，3，4，5，6，7，8，9。。。arr.length]
		 * [l,......................r,       r+1]
		 */
		int l=0;
		int r=arr.length-1;
		while(l<=r) {
			//优化：l+r可能会出现int范围溢出
			//int mid=(l+r)/2;
			int mid = l+(r-l)/2;
			if(target > arr[mid]) {
				//在mid右半[mid+1..........r]
				l=mid+1;
			}
			else if(target < arr[mid]){
				//在mid左半[l..........mid-1]
				r=mid-1;
			}
			else {
				//就是mid，找到了
				return mid;
			}
		}
		return -1;//没有找到target
	}
}
