package com.weightgraph;

import java.lang.reflect.Array;

/*
 * ������С��
 * �ײ�ṹ������
 * [[i][m][n].........]arr���飬�±��0��ʼ
 * [[] [i][m][n]......]data�ѣ��±��1��ʼ
 * iΪ��ǰ�ڵ�
 * parent �ڵ�= i/2;
 * ���� �ڵ�= i*2
 * �Һ��� �ڵ�= i*2+1
 * ���һ����Ҷ�ӽڵ�������count/2
 */

public class MinHeap {
	private Edge<Double> [] data;//����
	private int count;//���ݸ���
	//��ʼ����
	@SuppressWarnings("unchecked")
	public MinHeap(int capacity) {
		//���巺�͵�����
		data=(Edge<Double>[]) Array.newInstance(Edge.class, capacity+1);
		count=0;
	}
	
	//heapify,�Ż�������С�ѵķ���
	@SuppressWarnings("unchecked")
	public MinHeap(Edge<Double> [] arr) {
		data= (Edge<Double>[]) Array.newInstance(Edge.class, arr.length+1);
		for(int i=0;i<arr.length;i++) {
			data[i+1]=arr[i];
		}
		count=arr.length;
		//�ȸ�ֵ�����幹����С��
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
	public void insert(Edge<Double> item) {
		data[count+1]=item;
		count++;
		shiftUp(count);//���µ���ΪС����
	}

	private void shiftUp(int k) {
		//���ϵ����²���Ԫ�ص�λ�ã��ж���͸��ڵ�Ĵ�С
		while(k>1 && (data[k/2]).getWeight() > (data[k]).getWeight()) {
			Swap(data, k/2, k);
			k/=2;
		}
	}
	
	private void Swap(Edge<Double>[] data, int i, int j) {
		Edge<Double> t= data[i];
		data[i]=data[j];
		data[j]=t;
	}

	//��ȡ��С�ѵ���Сֵ
	public  Edge<Double> getMin() {
		if(!isEmpty()) {//�ж���С���Ƿ�Ϊ��
			Edge<Double> max=data[1];
			Swap(data, 1, count);
			count--;
			shiftDown(1);//���µ���Ϊ��С��
			return max;
		}else {
			return null;
		}
	}
	
	private void shiftDown(int i) {
		while(i*2<=count) {//�ж��Ƿ�������
			int j=i*2;//����ָ�룺����i�ڵ����Һ��ӽ�Сֵ���±꣬Ĭ�����ӽ�С
			if(j+1<=count && data[j+1].getWeight()<data[j].getWeight()) {
				j=j+1;
			}
			if(data[i].getWeight()<=data[j].getWeight()) {
				break;//�ж�i�ڵ�����Һ��Ӽ�ϴ�ֵ�Ĵ�С
			}
			Swap(data, i, j);
			i=j;
		}
	}
}
