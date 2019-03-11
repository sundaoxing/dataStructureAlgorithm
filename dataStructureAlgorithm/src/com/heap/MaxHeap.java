package com.heap;
/*
 * �����󶥶�
 * �ײ�ṹ������
 * [[i][m][n].........]arr���飬�±��0��ʼ
 * [[] [i][m][n]......]data�ѣ��±��1��ʼ
 * iΪ��ǰ�ڵ�
 * parent �ڵ�= i/2;
 * ���� �ڵ�= i*2
 * �Һ��� �ڵ�= i*2+1
 * ���һ����Ҷ�ӽڵ�������count/2
 */

import com.sort.SortHandler;

public class MaxHeap {
	private int [] data;//����
	private int count;//���ݸ���
	private SortHandler sortHandler = new SortHandler();
	//��ʼ����
	public MaxHeap(int capacity) {
		data=new int[capacity+1];
		count=0;
	}
	
	//heapify,�Ż��������ѵķ���
	public MaxHeap(int [] arr) {
		data= new int [arr.length+1];
		for(int i=0;i<arr.length;i++) {
			data[i+1]=arr[i];
		}
		count=arr.length;
		//�ȸ�ֵ�����幹������
		for(int k=count/2;k>=1;k--) {
			shiftDown(k);
		}
	}
	
	//�������ѵ�Ԫ�ظ���
	public int getSize() {
		return count;
	}
	
	//�ж������Ƿ�Ϊ��
	public boolean isEmpty() {
		return count==0;
	}
	
	//������Ԫ�أ�β�壩
	public void insert(int item) {
		data[count+1]=item;
		count++;
		shiftUp(count);//���µ���Ϊ�󶥶�
	}

	private void shiftUp(int k) {
		//���ϵ����²���Ԫ�ص�λ�ã��ж���͸��ڵ�Ĵ�С
		while(k>1 && data[k/2] < data[k]) {
			sortHandler.Swap(data, k/2, k);
			k/=2;
		}
	}
	
	//��ȡ���ѵ����ֵ
	public int getMax() {
		if(!isEmpty()) {//�ж������Ƿ�Ϊ��
			int max=data[1];
			sortHandler.Swap(data, 1, count);
			count--;
			shiftDown(1);//���µ���Ϊ����
			return max;
		}else {
			return Integer.MAX_VALUE;
		}
	}
	
	private void shiftDown(int i) {
		while(i*2<=count) {//�ж��Ƿ�������
			int j=i*2;//����ָ�룺����i�ڵ����Һ��ӽϴ�ֵ���±꣬Ĭ�����ӽϴ�
			if(j+1<=count && data[j+1]>data[j]) {
				j=j+1;
			}
			if(data[i]>=data[j]) {
				break;//�ж�i�ڵ�����Һ��Ӽ�ϴ�ֵ�Ĵ�С
			}
			sortHandler.Swap(data, i, j);
			i=j;
		}
	}
}
