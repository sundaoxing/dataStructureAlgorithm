package com.sort;

import org.junit.Test;

/*
 * �鲢���򣺷���˼�롣������2�ֳ�ÿһ����һ��Ԫ��Ϊһ�����У���ʱ�����м����������κϲ����ڵ�����
 * ���ĵ������������У��ڶ��������������кϲ�
 * ��������temp�����������������αȽϣ���Сֵ����temp,����temp���Ƹ�arr
 * ʱ�临�Ӷȣ�O��nlogn��
 * �㷨�ȶ��ԣ��ȶ�
 * 
 * Ӧ�ã��ж�һ�����е�����̶ȣ�����Եĸ�����
 * ˼�룺�ںϲ��������������е�ʱ�򣬷����µ������е�ʱ�򣬿��Լ����ж�
 * ���--��arr[i]>arr[j]�����arr[i---mid]��������arr[j]
 */
public class MergeSort {
	SortHandler sortHandler = new SortHandler();
	InsertSrot insertSrot = new InsertSrot();

	// �Զ����µĹ鲢����--���ڵݹ�
	public void mergeDown(int[] arr) {
		int[] temp = new int[arr.length];
		merge(arr, 0, arr.length - 1, temp);
	}

	// ����˼�룬����������
	private void merge(int[] arr, int L, int R, int[] temp) {
		// �ݹ���ֹ����
		/*
		 * �Ľ���ʹ�ò������� ���ݹ鵽һ��������[L,R]���伸������
		 */
		if (R - L <= 50) {
			insertSrot.insertRange(arr, L, R);
			return;
		}
		int mid = (L + R) / 2;
		merge(arr, L, mid, temp);
		merge(arr, mid + 1, R, temp);
		// �Ľ���ֻ�е�arr[mid] > arr[mid + 1]ʱ�����򣬷����������Ѿ�����
		if (arr[mid] > arr[mid + 1]) {
			sort(arr, L, mid, R, temp);
		}
	}

	// [l...mid]----[mid+1...r]������������������ʱ�临�Ӷȣ�O��N����
	private void sort(int[] arr, int l, int mid, int r, int[] temp) {
		int i = l;
		int j = mid + 1;
		for (int t = l; t <= r; t++) {// [l��r]���Ҷ��պϵ�����
			// �жϱ߽�����������֮��ſ��Լ����Ƚ�arr[i]<arr[j]
			if (i > mid) {
				temp[t] = arr[j];
				j++;
			} else if (j > r) {
				temp[t] = arr[i];
				i++;
			}
			// �Ƚ�---�ѽ�Сֵ����temp����
			else if (arr[i] <= arr[j]) {
				temp[t] = arr[i];
				i++;
			} else {
				temp[t] = arr[j];
				j++;
			}
		}
		// �����ź����temp������¸�ֵ��arr����
		for (int k = l; k <= r; k++) {
			arr[k] = temp[k];
		}
	}

	// �Ե����ϵĹ鲢����---����ѭ���������ң�ÿ2��Ԫ��������ÿ4��Ԫ�����򡣡���
	public void mergeUp(int[] arr) {
		int[] temp = new int[arr.length];
		// ���Σ�1��Ԫ�أ�2��Ԫ�أ�4��Ԫ�أ��ӵ����ϣ�ÿһ��merge��Ԫ�ظ�����
		for (int size = 1; size <= arr.length; size += size) {

			/*�ϲ���������������䣨����iҪ��������size��
			 * [i,size+size-1]��[size+size,i+size+size-1]�����䣬������������������
			 * 
			 * ���Ǳ߽������mid=(i+i+size+size)/2=i+size ���ܻ����arr.length����ֻ��һ�����У������������ û�б�Ҫ�ϲ�����
			 * for(int i=0;i<arr.length;i+=size+size) {
			 */

			for (int i = 0; i + size < arr.length; i += size + size) {
				// �Ľ���ֻ�е�arr[mid] > arr[mid + 1]ʱ�����򣬷����������Ѿ�����
				if (arr[i + size - 1] > arr[i + size]) {
					sort(arr, i, i + size - 1, Math.min(i + size + size - 1, arr.length - 1), temp);
				}
				/*
				 * ���Ǳ߽������arr[i+size+size-1]��Խ�磬��Ϊ����������бȽ�ʱ �п�����һ�����еĳ��Ȳ���size�ĳ��� sort(arr, i,
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
		System.out.println("��ʱ��" + (end - start) + "ms");
	}
}
