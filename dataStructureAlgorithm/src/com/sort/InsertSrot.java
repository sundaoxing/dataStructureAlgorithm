package com.sort;

import org.junit.Test;

/*
 * ���������ص㣺�Խ�����������е�����Ч�ʺܸ�
 * ԭ�򣺲�������һ���ҵ��Լ���λ�ã��Ϳ��Բ��룬�������ڱȽ���ȥ��ѭ������ֱ������
 * ʱ�临�Ӷȣ�O��n*n��
 * �㷨�ȶ��ԣ��ȶ�
 */
public class InsertSrot {
	SortHandler sortHandler = new SortHandler();

	public void insert(int[] arr) {
		for (int i = 1; i < arr.length; i++) {// Ĭ�ϵ�һ��Ԫ�����򣬴ӵڶ���Ԫ�ؿ�ʼ
			for (int j = i; j > 0; j--) {// �ڶ���Ԫ�شӺ���ǰ���Ƚ�
				if (arr[j] < arr[j - 1]) {
					
					/*
					 * �ȶ�
					 */
					sortHandler.Swap(arr, j, j - 1);
				} else {// �ҵ���Ԫ�ص�λ����
					break;
				}
			}
		}
	}

	//�Ľ���:���뵽��ȷ��λ��ʱ��һ��ֻ����һ�Σ������Ը�ֵ���潻��
	//���ɣ�ʹ�ø�������t�����浱ǰҪ������ֵarr[i]
	public void insertBetter(int[] arr) {
		for (int i =0+1; i < arr.length; i++) {
			int t = arr[i];// ��ȡ����
			int j;// ��¼��Ԫ��Ӧ�ò����λ��
			for (j = i; j > 0; j--) {
				if (t<arr[j-1]) {
					arr[j] = arr[j - 1];// ���Ԫ����ǰ�ƶ�
				}else {
					break;
				}
			}
			arr[j] = t;// ѭ��������j��λ�þ���t��λ��
		}
	}
	
	//��arr[l,r]��Χ�ڵ����ݽ��в�������
	//˼��һ����ֻ��������ʼ�����±����[0,length-1]---[l,r]
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
		System.out.println("��ʱ��" + (end - start) + "ms");
	}
}
