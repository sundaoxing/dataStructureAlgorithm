package com.sort;
import org.junit.Test;

import com.heap.MaxHeap;

/*
 * �����򣺹������ѣ�ÿ�λ�ȡ���ֵ�����θ�ֵ������
 * ʱ�临�Ӷȣ�O��nlog��n����
 * �㷨�ȶ��ԣ����ȶ�
 */
public class HeapSort {
	SortHandler sortHandler = new SortHandler();
	public void heap(int []arr) {
		MaxHeap maxHeap = new MaxHeap(arr.length);
		for(int i=0;i<arr.length;i++) {
			maxHeap.insert(arr[i]);
		}
		/*�㷨���ȶ���ԭ��
		 * ÿ�λ�ȡ���ֵ�������data[arr.length-1]��data[1]����
		 * ��data[��arr.length-1��*2]==data[��arr.length-1��*2+1]�����Һ������ʱ
		 * ���Ԫ�����λ�÷����ı�
		 */
		for(int i=arr.length-1;i>=0;i--) {
			arr[i]=maxHeap.getMax();
		}
	}
	
	//�Ż��������ѵĶ�����ʱ�临�Ӷ�ΪO��N��
	public void heapipy(int []arr) {
		MaxHeap maxHeap = new MaxHeap(arr);
		for(int i=arr.length-1;i>=0;i--) {
			arr[i]=maxHeap.getMax();
		}
	}
	
	/*��ԭ�����й������ѣ�Ȼ�󽻻����ֵ������ĩβԪ�ؼ��ɣ�����Ҫ���⿪���µĿռ�
	 * ��ʱ���ڵ���±��0��ʼ��
	 * [[0][1][2]...................]arr
	 * iΪ��ǰ�ڵ�
	 * parent �ڵ�= ��i-1��/2;
	 * ���� �ڵ�= i*2+1
	 * �Һ��� �ڵ�= i*2+2
	 * ���һ����Ҷ�ӽڵ�������(arr.length-1)/2
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
	 * @param arr ���� 
	 * @param n ����Ԫ�ظ���
	 * @param k ��ǰ�ڵ㣨��Ҷ�ӽڵ㣩
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
		System.out.println("��ʱ��" + (end - start) + "ms");
	}
}
