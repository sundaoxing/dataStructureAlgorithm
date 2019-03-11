package com.sort;

import org.junit.Test;

/*
 * 插入排序：特点：对近乎有序的序列的排序，效率很高
 * 原因：插入排序，一旦找到自己的位置，就可以插入，而不必在比较下去，循环可以直接跳过
 * 时间复杂度：O（n*n）
 * 算法稳定性：稳定
 */
public class InsertSrot {
	SortHandler sortHandler = new SortHandler();

	public void insert(int[] arr) {
		for (int i = 1; i < arr.length; i++) {// 默认第一个元素有序，从第二个元素开始
			for (int j = i; j > 0; j--) {// 第二个元素从后向前，比较
				if (arr[j] < arr[j - 1]) {
					
					/*
					 * 稳定
					 */
					sortHandler.Swap(arr, j, j - 1);
				} else {// 找到该元素的位置了
					break;
				}
			}
		}
	}

	//改进版:插入到正确的位置时，一轮只交换一次，其余以赋值代替交换
	//技巧：使用辅助变量t，保存当前要交换的值arr[i]
	public void insertBetter(int[] arr) {
		for (int i =0+1; i < arr.length; i++) {
			int t = arr[i];// 提取副本
			int j;// 记录该元素应该插入的位置
			for (j = i; j > 0; j--) {
				if (t<arr[j-1]) {
					arr[j] = arr[j - 1];// 大的元素向前移动
				}else {
					break;
				}
			}
			arr[j] = t;// 循环结束，j的位置就是t的位置
		}
	}
	
	//对arr[l,r]范围内的数据进行插入排序
	//思想一样，只是数组起始结束下标变了[0,length-1]---[l,r]
	public void insertRange(int [] arr ,int l,int r) {
		for(int i=l+1;i<=r;i++) {
			int t=arr[i];
			int j;
			for(j=i;j>l;j--) {
				if(t<arr[j-1]) {
					arr[j]=arr[j-1];
				}else {
					break;
				}
			}
			arr[j]=t;
		}
	}

	@Test
	public void test() {
		int n = 1000000;
		//int[] arr = sortHandler.randomArray(n, 0, n);
		int [] arr = sortHandler.NearSortArray(n, 200);
		long start = System.currentTimeMillis();
		insertBetter(arr);
		long end = System.currentTimeMillis();
		//sortHandler.print(arr);
		System.out.println(sortHandler.isSort(arr));
		System.out.println("用时：" + (end - start) + "ms");
	}
}
