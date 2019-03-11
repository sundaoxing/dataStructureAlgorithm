package com.sort;

import org.junit.Test;

/*
 * 归并排序：分治思想。把序列2分成每一个单一的元素为一个序列，此时该序列即有序，再依次合并相邻的序列
 * 最后的到两个有序序列，在对这两个有序序列合并
 * 辅助数组temp：两个有序序列依次比较，较小值放入temp,最后把temp复制个arr
 * 时间复杂度：O（nlogn）
 * 算法稳定性：稳定
 * 
 * 应用：判断一个序列的有序程度（逆序对的个数）
 * 思想：在合并两个有序子序列的时候，放入新的数组中的时候，可以加入判断
 * 如果--》arr[i]>arr[j]，则从arr[i---mid]，都大于arr[j]
 */
public class MergeSort {
	SortHandler sortHandler = new SortHandler();
	InsertSrot insertSrot = new InsertSrot();

	// 自顶向下的归并排序--基于递归
	public void mergeDown(int[] arr) {
		int[] temp = new int[arr.length];
		merge(arr, 0, arr.length - 1, temp);
	}

	// 分治思想，左右子序列
	private void merge(int[] arr, int L, int R, int[] temp) {
		// 递归终止条件
		/*
		 * 改进：使用插入排序 当递归到一定级数后，[L,R]区间几乎有序
		 */
		if (R - L <= 50) {
			insertSrot.insertRange(arr, L, R);
			return;
		}
		int mid = (L + R) / 2;
		merge(arr, L, mid, temp);
		merge(arr, mid + 1, R, temp);
		// 改进：只有当arr[mid] > arr[mid + 1]时才排序，否则它本身已经有序
		if (arr[mid] > arr[mid + 1]) {
			sort(arr, L, mid, R, temp);
		}
	}

	// [l...mid]----[mid+1...r]两个有序子序列排序（时间复杂度：O（N））
	private void sort(int[] arr, int l, int mid, int r, int[] temp) {
		int i = l;
		int j = mid + 1;
		for (int t = l; t <= r; t++) {// [l，r]左右都闭合的区间
			// 判断边界条件，满足之后才可以继续比较arr[i]<arr[j]
			if (i > mid) {
				temp[t] = arr[j];
				j++;
			} else if (j > r) {
				temp[t] = arr[i];
				i++;
			}
			// 比较---把较小值存入temp数组
			else if (arr[i] <= arr[j]) {
				temp[t] = arr[i];
				i++;
			} else {
				temp[t] = arr[j];
				j++;
			}
		}
		// 把已排好序的temp数组从新赋值给arr数组
		for (int k = l; k <= r; k++) {
			arr[k] = temp[k];
		}
	}

	// 自底向上的归并排序---基于循环，从左到右，每2个元素排序，再每4个元素排序。。。
	public void mergeUp(int[] arr) {
		int[] temp = new int[arr.length];
		// 分治：1个元素，2个元素，4个元素，从底向上（每一轮merge的元素个数）
		for (int size = 1; size <= arr.length; size += size) {

			/*合并两个有序的子区间（所以i要加上两个size）
			 * [i,size+size-1]和[size+size,i+size+size-1]闭区间，两个有序子序列排序
			 * 
			 * 考虑边界溢出，mid=(i+i+size+size)/2=i+size 可能会大于arr.length，即只有一个序列（它本身就有序） 没有必要合并排序
			 * for(int i=0;i<arr.length;i+=size+size) {
			 */

			for (int i = 0; i + size < arr.length; i += size + size) {
				// 改进：只有当arr[mid] > arr[mid + 1]时才排序，否则它本身已经有序
				if (arr[i + size - 1] > arr[i + size]) {
					sort(arr, i, i + size - 1, Math.min(i + size + size - 1, arr.length - 1), temp);
				}
				/*
				 * 考虑边界溢出，arr[i+size+size-1]会越界，因为最后两个序列比较时 有可能有一个序列的长度不足size的长度 sort(arr, i,
				 * (i+i+size+size-1)/2,i+size+size-1, temp);
				 */
			}
		}
	}

	@Test
	public void test() {
		int n = 1000000;
		int[] arr = sortHandler.randomArray(n, 0, 10);
		// sortHandler.print(arr);
		// System.out.println();
		//int[] arr = sortHandler.NearSortArray(n, 200);
		long start = System.currentTimeMillis();
		mergeDown(arr);
		//mergeUp(arr);
		long end = System.currentTimeMillis();
		// sortHandler.print(arr);
		System.out.println(sortHandler.isSort(arr));
		System.out.println("用时：" + (end - start) + "ms");
	}
}
