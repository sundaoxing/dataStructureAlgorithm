package com.sort;

import org.junit.Test;

/*
 * ѡ������:�ȵ��������е���Сֵ���±꣬��¼ΪminIndex������arr[0]��arr[minIndex]
 * Ȼ����ʣ�µ�������������Сֵ���±꣬�ں�arr[1]����
 * ʱ�临�Ӷȣ�O(n*n)
 * �㷨�ȶ��ԣ����ȶ�
 */
public class SelectSort {
	SortHandler sortHandler = new SortHandler();
	public  void select(int [] arr) {
		for(int i=0;i<arr.length;i++) {//λ�á���������������
			int minIndex =i;//��¼��Сֵ���±�
			for(int j=i+1;j<arr.length;j++) {//����Сֵ
				if(arr[j] < arr[minIndex]) {
					minIndex=j;//�ҵ���
				}
			}
			/*
			 * ���ȶ���ԭ��
			 * �ڽ���Ԫ�ص�ʱ��minIndexǰ���Ԫ��arr[k]�п��ܺ͵�ǰԪ��arr[i]���
			 * �����ڽ�������,arr[k]��arr[i]�����λ�÷����˸ı�
			 a[0] 	a[1] 	a[2] 	a[3] 	a[4]
			  6 	  2 	  4 	  6 	  1
			������6��a[0]��a[3]���������������ֿ��ܣ�
			  1 	  2 	  4 	  6 	  6
			
			ԭa[4] 	ԭa[1] 	ԭa[2] 	ԭa[0] 	ԭa[3]
			ԭa[4] 	ԭa[1] 	ԭa[2] 	ԭa[3] 	ԭa[0]
			 */
			sortHandler.Swap(arr,i,minIndex);//����
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
		System.out.println("��ʱ��"+(end-start)+"ms");
	}
}
