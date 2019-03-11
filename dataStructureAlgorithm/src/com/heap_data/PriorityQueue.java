package com.heap_data;

import com.queue.Queue_Interface;		
/*	
 * 				���ȶ���
 * 		�ص㣺
 * 			2.�ײ����ݽṹ����---��̬ά��Ԫ�ص����ȼ�	
 * 			1.���ȶ����е�ÿ��Ԫ�ض��и��Ե����ȼ������ȼ���ߵ�Ԫ�����ȵõ�����
 * 		Ӧ�ã�
 * 			1.CPU�������
 * 			2.��Ϸ���Զ�ѡ��С�ֹ���
 * 		���ȼ��Ķ��壺
 * 				���ݴ洢��������E��compareTo���������ж�������ȼ�
 */
public class PriorityQueue<E extends Comparable<E>> implements Queue_Interface<E> {
	private MaxHeap<E> mh;//�ײ����ݽṹ-��
	public PriorityQueue() {
		mh=new MaxHeap<>();
	}
	//����ʼ�����������ȶ��й��췽��
	public PriorityQueue(int capacity) {
		mh = new MaxHeap<>(capacity);
	}
	//�������ȶ�����Ԫ�صĸ���
	@Override
	public int size() {
		return mh.size();
	}
	//�ж������Ƿ�Ϊ��
	@Override
	public boolean isEmpty() {
		return mh.isEmpty();
	}
	//���-------------ʱ�临�Ӷȣ�O��log��n����
	@Override
	public void enqueue(E item) {
		mh.add(item);
	}
	//���ӣ����ز�ɾ�����ȼ���ߵ�Ԫ�أ�ʵ�ʣ��ѵĶԶ�Ԫ�أ�---ʱ�临�Ӷȣ�O��log��n����
	@Override
	public E dequeue() {
		return mh.extractMax();
	}
	//�������ȼ���ߵ�Ԫ��
	@Override
	public E getFront() {
		return mh.lookMax();
	}
}
