package com.sort;

import org.junit.Test;

/* ��������ÿһ��������ÿһ��Ԫ���ҵ��Լ���λ��<v  v  >v
 * ʱ�临�Ӷȣ�nlog��n��
 * ���������н���������߱�������--���˻���O��n*n��
 * ÿһ�λ�ȡ��key�����ǵ�ǰ���е���Сֵ/���ֵ���ݹ���ֻ����������������������������Ⱥܴ��൱��һ������
 * �㷨�ȶ��ԣ����ȶ�
 * 
 * Ӧ�ã���ȡ�����У���n�����/��С��ֵ
 * ˼�룺�Ȼ�õ�һ��ѡ�е�key��λ��keyIndex���ж�n��keyIndex�Ĵ�С
 * keyIndex > n,��key����������м�������
 * keyIndex < n,��key���ұ������м������ң����ƶ��ֲ��ң�
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
		 * 1.�Ľ��� ������Ԫ�ر�Сʱ�����н�������ʹ�ò����������
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
	
	/*һ·��������
	 * ȱ�㣺���ڴ����ظ�Ԫ������ʱ�临�Ӷ��˻���O(n*n)
	 * ԭ���ظ�Ԫ�ع��࣬ʹ������ƽ�⣬�����ظ�Ԫ�ؾۼ���һ���������ݹ����ȱ�úܴ�
	 */
	private int getKeyIndex(int[] arr, int l, int r) {
		int random = (int) (Math.random() * (r - l) + l);
		sortHandler.Swap(arr, random, l);
		int key = arr[l];
		
	   /*arr[l..............................................r]
		*     arr[l+1.....j]<v      arr[j+1....i)>=v
		 *   v[       <v        ][      >=v           ]e[.....]
		 *   l			        j                     i(iΪѭ������,��ǰҪ�Ƚϵ�Ԫ��)
	* ��ʼ����[j][][i.........................................r]
	  */
		int j=l;//��ʼ[l,l+1]
		for(int i=l+1;i<=r;i++) {
			if(arr[i]<key) {//����������>=v�ǲ����
				/*�㷨���ȶ���ԭ��
				 * arr[i]==keyʱ��λ�ò���
				 * �����ʱarr[i+1]==key����arr[i+2]<key��
				 * arr[i+1]��arr[i]�����λ�÷����ı�
				 */
				sortHandler.Swap(arr, i, j+1);
				j++;
			}
		}
		sortHandler.Swap(arr, l, j);
		return j;
	}

	/*��·��������
	 * 3.�Ľ������ڴ����ظ�Ԫ������ʹ�ö�·����
	 * 		    �Լ����������У�ʹ�ö�·����
	 * 
	 * 
	 */
	private int getKeyIndexBetter(int[] arr, int l, int r) {
		/*2.�Ľ��������������У������StackoverFlow�쳣
		 * �ݹ���ֻ����������������������������Ⱥܴ��൱��һ������
		 * ��������������У����Ӷ��˻�-----�����������
		 * ���ܱ�֤ÿ�ζ�����ȷ�ģ�ֻ�кܴ�ĸ�������ȷ��
		 * ��������ÿһ��ȡ����Сֵ
		 * ÿһ��������Сֵ�ĸ���Ϊ1/n��1/n-1,1/n-2....
		 * n�ܴ�ʱ��������ŵ�ʱ�临�Ӷȵ���ѧ����Ϊ��nlog��n��
		 */
		int random = (int) (Math.random() * (r - l) + l);
		sortHandler.Swap(arr, random, l);
		
		int key = arr[l];
		while (l < r) {
			/*�㷨���ȶ���ԭ��
			 * ���arr[r]��arr[l]��ȶ�����key�����߻ᷢ������
			 * arr[l]arr[r]�����λ�÷����ı�
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

	/*��·��������
	 * ���Ը��õĴ�������ظ�Ԫ��
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
		 * ��ʼ�������
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
				/*�㷨���ȶ���ԭ��
				 * ��arr[gt-1]��==keyʱ��������arr[lt+1..i)����
				 * Ȼ��arr[lt+1]==key��������arr[lt+1...i)����
				 * arr[gt-1]��arr[lt+1]���λ�÷����ı�
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
		System.out.println("��ʱ��" + (end - start) + "ms");
	}

}
