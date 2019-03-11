package com.heap_data;

import com.array.MyArray;
/*		
 * 						�ѣ����ѣ���С��Ҳ������˱�ʾ����С����Եģ����忴compareTo���������ж�������ȼ���С��
 * ��Ӧ���ṹ��---�߼��ṹ�������--����ȫ��������
 * 						3��0��--------------data����ĵ�һ��Ԫ�ش�����ڵ�
 * 					/		\
 * 	       ���ڵ�--------2��1��	 1��2��----------�������ʾ��������
 * 				 /	 \		
 * 	���ӽڵ�-----0��3�� 2��3��-----------------�Һ��ӽڵ�
 * 		
 * 	         �ص㣺1.�ײ����ݽṹ����̬����ģ����ȫ��������
 * 		  ��������
 * 			2.ֻ��һ�����ڵ�
 * 			3.ÿ���ڵ��������������
 * 			4.ÿ���ڵ������һ�����ڵ�
 * 		���ʣ�
 * 			5.��������ڵ�ģ����ȼ���ֵ�����ǲ������丸�ڵ��ֵ�����ڵ㣨���ȼ������ڵ��������Һ��ӽڵ㣩
 * 
 * 	 ��ʵ������һ�����ɣ����԰Ѷ���Ԫ��һ��һ��洢�����������У�������
 * 				0	1	2	3	4		-��������
 * 				3	2	1	0	2		-����data
 * 		Ӧ�ã�
 * 			1.����--������
 * 			2.���ȶ��е�ʵ��
 */
public class MaxHeap<E extends Comparable<E>> {
	private MyArray<E> data;//�ײ����ݽṹ-��̬����
	//����ʼ�����Ĺ���
	public MaxHeap(int capacity) {
		data = new MyArray<>(capacity);
	}
	//�޲ι���
	public MaxHeap() {
		data = new MyArray<>();
	}
	//��ת������Ϊ��ʼ�ѵĹ��췽����heapify��
	public MaxHeap(E[] arr) {
		data = new MyArray<>(arr);
		/*
		 * heapify����������������ɶ�
		 * ʱ�临�Ӷ�:O(n)
		 * 		˼�룺�����һ��Ԫ�ظ��ڵ㿪ʼ������shfitDown�����������ڵ�Ϊֹ
		 */
		for(int k = parent(data.size()-1); k>=0;k--) {
			siftDown(k);
		}
	}
	//���ض���Ԫ�صĸ���
	public int size() {
		return data.size();
	}
	//�ж϶��Ƿ�Ϊ��
	public boolean isEmpty() {
		return data.isEmpty();
	}
	//������ȫ�������������ʾ�У��ڵ�ĸ��ڵ�����
	private int parent(int index) {
		if(index ==0) {//������0�ڵ㿪ʼ������0�ڵ�Ϊ���ڵ㣬û�и��ڵ㣬�����⴦��
			throw new RuntimeException("index-0 no parent");
		}
		return (index-1) / 2;
	}
	//������ȫ�������������ʾ�У��ڵ�����ӽڵ�����
	private int leftChild(int index) {
		return index*2+1;
	}
	//������ȫ�������������ʾ�У��ڵ���Һ��ӽڵ�����
	/*private int rightChild(int index) {
		return index*2+2;
	}*/
	//���Ԫ�أ�ʵ�ʣ�����β��Ԫ�أ�------ʱ�临�Ӷ�O��lon��n����
	public void add(E item) {
		data.addLast(item);//β��
		siftUp(data.size()-1);//��Ԫ����Ӻ󣬿����ƻ��˶ѵ����ʣ���Ҫ���ά�������ϵ���
	}
	/*
	 * siftUp���̣�ʵ�ʣ�Ԫ�ز�����ѵ����ʣ����ڵ㣨���ȼ������ڵ����ӽڵ㣩
	 *					3��0��
	 * 					/		\
	 * 	               2��1��	 1��2��
	 * 				 /	 \		
	 * 				0��3�� 4��3��---------����ӵ�Ԫ��4��������ѵ����ʣ���Ҫ���ϵ���
	 * 		3	3	1	0	4           data
	 * 		0	1	2	3	4			����
	 * 	˼�룺�����Ԫ�����Ԫ�صĸ��ڵ�Ƚ�
	 * 			������򽻻���
	 * 			���С����������
	 * 		ѭ�����ϵ�����һֱ�Ƚϵ����ڵ�Ϊֹ
	 */
	private void siftUp(int index) {
		while(index >0 && data.get(index).compareTo(data.get(parent(index))) >0) {
			data.swap(index, parent(index));
			index=parent(index);//���ϵ���
		}
	}
	//���ز�ɾ�����еĸ��ڵ㣨���Ԫ�أ�------ʱ�临�Ӷ�O��lon��n����
	public E extractMax() {
		E maxItem = lookMax();//�����û�����ֵ
		data.swap(0, data.size()-1);//��βԪ�ؽ���
		data.removeLast();//ɾ������βԪ��
		siftDown(0);//����ĩβԪ�ص���������ͷ���󣬿����ƻ��ѵ����ʣ���Ҫ���µ���
		return maxItem;
	}
	//�鿴���Ƿ������ֵ
	public E lookMax() {
		if(data.isEmpty()) {
			throw new RuntimeException("Empty");
		}
		return data.get(0);
	}
	/*
	 * siftDown���̣�ʵ�ʣ�Ԫ�ز�����ѵ����ʣ����ڵ㣨���ȼ������ڵ����ӽڵ㣩
	 *		3��0��------1.ȡ��Ԫ��3				1��0��---------4.Ԫ��1����������ʣ���Ҫ���µ���
	 * 	 /		\							/		 \
	 * 	2��1��	 1��2��			-------->  2(1)		  1(2)	
	 * 	/	\							  /    \     
	 * 0��3�� 1��3��-----2.����Ԫ��1��3��λ��       0(3)   3(0)---------3.ɾ��Ԫ��3
	 * 		1	2	1	0           data
	 * 		0	1	2	3			����
	 * 	˼�룺���ڵ�Ԫ�����Ԫ�ص����Һ��ӽڵ��еĽϴ�ֵ�Ƚ�
	 * 			1.���Һ��ӱȽϣ�������Ľڵ������
	 * 			2.���ڵ��ںͽϴ�Ľڵ�Ƚ�
	 * 				�����򲻴�����������ʣ�����shiftDown����
	 * 				��С���򽻻�
	 * 		ѭ�����µ�����һֱ�����һ���ڵ�Ϊֹ
	 */
	private void siftDown(int k) {
		while(leftChild(k) < data.size()) {
			int j=leftChild(k);//����
			if(j+1 < data.size() && data.get(j).compareTo(data.get(j+1)) <0) {
				j++;//���Һ����нϴ���
			}
			if(data.get(k).compareTo(data.get(j)) >=0) {
				break;//�Ѿ���������ʣ�����Ҫ����
			}
			data.swap(k, j);
			k=j;//���µ���
		}
	}
	//ȡ�����Ԫ�غ����ڸ��ڵ�λ�÷����µ�Ԫ�أ��ٵ��������ѣ�����ѵ�����
	public E replace(E item) {
		E maxItem = lookMax();
		data.set(0, item);//�滻�Ѷ���Ԫ��
		siftDown(0);
		return maxItem;
	}
}
