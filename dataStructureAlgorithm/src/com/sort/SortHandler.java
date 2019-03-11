package com.sort;

/*
 * �㷨�ȶ��ԣ�������ǰ����ͬԪ�ص����λ�ò���------�㷨�ȶ�
 * ���򣺲��ȶ�
 */
public class SortHandler {

	/*
	 * ������ɸ���Ϊn����Χ��L-R֮�������
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
		//ע�⣺arr[i]��arr[j]������ȣ�������Ϊ0
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
	
	//�жϸ������Ƿ�����
	public boolean isSort(int [] arr) {
		for(int i=0;i<arr.length-1;i++) {
			if(arr[i]>arr[i+1]) {
				return false;
			}
		}
		return true;
	}
	
	//���ɽ������������
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
