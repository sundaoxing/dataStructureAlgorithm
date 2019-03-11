package com.sort;

import org.junit.Test;

/* 快速排序：每一轮排序，让每一个元素找到自己的位置<v  v  >v
 * 时间复杂度：nlog（n）
 * 最坏情况：序列近乎有序或者本身有序--》退化成O（n*n）
 * 每一次获取的key，都是当前序列的最小值/最大值，递归树只有左子树，或者右子树，树的深度很大（相当于一个链表）
 * 算法稳定性：不稳定
 * 
 * 应用：获取序列中，第n个最大/最小的值
 * 思想：先获得第一次选中的key的位置keyIndex，判断n和keyIndex的大小
 * keyIndex > n,在key的左边序列中继续查找
 * keyIndex < n,在key的右边序列中继续查找（类似二分查找）
 */
public class QuickSort {
	SortHandler sortHandler = new SortHandler();
	InsertSrot insertSrot = new InsertSrot();

	public void quick(int[] arr) {
		if (arr.length > 0) {
			quick(arr, 0, arr.length - 1);
		}
	}

	private void quick(int[] arr, int l, int r) {
		/*
		 * 1.改进： 当序列元素变小时，序列近乎有序，使用插入排序代替
		 * if(l>=r) {
			return;}
		 */
		if (r - l <= 15) {
			insertSrot.insertRange(arr, l, r);
			return;
		}
		int keyIndex = getKeyIndexBetter(arr, l, r);
		quick(arr, l, keyIndex - 1);
		quick(arr, keyIndex + 1, r);
	}
	
	/*一路快速排序
	 * 缺点：对于大量重复元素排序，时间复杂度退化成O(n*n)
	 * 原因：重复元素过多，使分区不平衡，大量重复元素聚集在一个分区，递归的深度变得很大
	 */
	private int getKeyIndex(int[] arr, int l, int r) {
		int random = (int) (Math.random() * (r - l) + l);
		sortHandler.Swap(arr, random, l);
		int key = arr[l];
		
	   /*arr[l..............................................r]
		*     arr[l+1.....j]<v      arr[j+1....i)>=v
		 *   v[       <v        ][      >=v           ]e[.....]
		 *   l			        j                     i(i为循环变量,当前要比较的元素)
	* 初始化：[j][][i.........................................r]
	  */
		int j=l;//初始[l,l+1]
		for(int i=l+1;i<=r;i++) {
			if(arr[i]<key) {//隐含条件：>=v是不变的
				/*算法不稳定：原因：
				 * arr[i]==key时，位置不动
				 * 如果此时arr[i+1]==key，而arr[i+2]<key，
				 * arr[i+1]和arr[i]的相对位置发生改变
				 */
				sortHandler.Swap(arr, i, j+1);
				j++;
			}
		}
		sortHandler.Swap(arr, l, j);
		return j;
	}

	/*二路快速排序
	 * 3.改进：对于大量重复元素排序，使用二路排序
	 * 		    对几乎有序序列，使用二路排序
	 * 
	 * 
	 */
	private int getKeyIndexBetter(int[] arr, int l, int r) {
		/*2.改进：几乎有序序列，会出现StackoverFlow异常
		 * 递归树只有左子树，或者右子树，树的深度很大（相当于一个链表）
		 * 解决几乎有序序列，复杂度退化-----》随机化方法
		 * 不能保证每次都是正确的，只有很大的概率是正确的
		 * 尽量避免每一次取到最小值
		 * 每一次渠道最小值的概率为1/n，1/n-1,1/n-2....
		 * n很大时，随机快排的时间复杂度的数学期望为：nlog（n）
		 */
		int random = (int) (Math.random() * (r - l) + l);
		sortHandler.Swap(arr, random, l);
		
		int key = arr[l];
		while (l < r) {
			/*算法不稳定：原因：
			 * 如果arr[r]和arr[l]相等都等于key，两者会发生交换
			 * arr[l]arr[r]的相对位置发生改变
			 */
			if (arr[r] >= key) {
				r--;
			}
			arr[l] = arr[r];
			if (arr[l] <= key) {
				l++;
			}
			arr[r] = arr[l];
		}
		arr[l] = key;
		return l;
	}

	/*三路快速排序：
	 * 可以更好的处理大量重复元素
	 */
	public void quick3Way(int[] arr) {
		if (arr.length > 0) {
			quick3Way(arr, 0, arr.length - 1);
		}
	}
	
	public void quick3Way(int [] arr,int l,int r) {
		if(r-l <=15) {
			insertSrot.insertRange(arr, l, r);
			return;
		}
		
		int random = (int) (Math.random() * (r - l) + l);
		sortHandler.Swap(arr, random, l);
		int key = arr[l];
		/*   arr[l+1...lt]<v  arr[lt+1...i-1]=v   	   arr[gt....r]>v
		 *v[		<v		][		=v		  ]e[   ][			>v		]
		 * l			  lt				   i      gt				r
		 * 
		 * 初始化情况：
		 * v [l+1]..................[ ]
		 * l  						 r  r+1
		 * lt  i					    gt
		 */    
		int lt=l;//arr[l+1...lt]<v
		int gt=r+1;//arr[gt...r]>v
		int i=l+1;//arr[lt+1..i)=v
		while(i<gt) {
			if(arr[i]<key) {
				sortHandler.Swap(arr,i, lt+1);
				i++;
				lt++;
			}
			else if(arr[i]>key) {
				sortHandler.Swap(arr, i, gt-1);
				gt--;
			}
			else {//arr[i]==key
				i++;
				/*算法不稳定：原因：
				 * 当arr[gt-1]先==key时，放在了arr[lt+1..i)这里
				 * 然后arr[lt+1]==key，放在了arr[lt+1...i)这里
				 * arr[gt-1]和arr[lt+1]相对位置发生改变
				 */
			}
		}
		sortHandler.Swap(arr, lt, l);
		lt--;
		quick3Way(arr, l, lt);
		quick3Way(arr, gt, r);
	}
	
	@Test
	public void test() {
		int n = 1000000;
		int[] arr = sortHandler.randomArray(n, 0, n);
		//sortHandler.print(arr);
		//System.out.println();
		//int[] arr = sortHandler.NearSortArray(n, 100);
		long start = System.currentTimeMillis();
		//quick(arr);
		quick3Way(arr);
		long end = System.currentTimeMillis();
		//sortHandler.print(arr);
		System.out.println(sortHandler.isSort(arr));
		System.out.println("用时：" + (end - start) + "ms");
	}

}
