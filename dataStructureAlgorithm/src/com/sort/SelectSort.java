package com.sort;

import org.junit.Test;

/*
 * 选择排序:先到到序列中的最小值的下标，记录为minIndex，交换arr[0]和arr[minIndex]
 * 然后在剩下的序列中再找最小值的下标，在和arr[1]交换
 * 时间复杂度：O(n*n)
 * 算法稳定性：不稳定
 */
public class SelectSort {
	SortHandler sortHandler = new SortHandler();
	public  void select(int [] arr) {
		for(int i=0;i<arr.length;i++) {//位置【】【】【】【】
			int minIndex =i;//记录最小值的下标
			for(int j=i+1;j<arr.length;j++) {//找最小值
				if(arr[j] < arr[minIndex]) {
					minIndex=j;//找到了
				}
			}
			/*
			 * 不稳定的原因：
			 * 在交换元素的时候，minIndex前面的元素arr[k]有可能和当前元素arr[i]相等
			 * 这样在交换过后,arr[k]和arr[i]的相对位置发生了改变
			 a[0] 	a[1] 	a[2] 	a[3] 	a[4]
			  6 	  2 	  4 	  6 	  1
			有两个6，a[0]和a[3]。排序结果就有两种可能：
			  1 	  2 	  4 	  6 	  6
			
			原a[4] 	原a[1] 	原a[2] 	原a[0] 	原a[3]
			原a[4] 	原a[1] 	原a[2] 	原a[3] 	原a[0]
			 */
			sortHandler.Swap(arr,i,minIndex);//交换
		}
	}
	@Test
	public void test() {
		int n=10000;
		//int []arr=sortHandler.randomArray(n, 0, n);
		int [] arr = sortHandler.NearSortArray(n, 200);
		long start=System.currentTimeMillis();
		select(arr);
		long end=System.currentTimeMillis();
		System.out.println(sortHandler.isSort(arr));
		System.out.println("用时："+(end-start)+"ms");
	}
}
