package com.array;
			/*			����
			 * �ײ�ṹ����̬����ʵ��
			 * ��Ϊ�ײ�ṹ��ͬ��ջʵ�ֵ�ϸ��Ҳ��ͬ��δ�����������ջ��ʵ�֣���ջ����Ҫ����
			 * �����һ���ӿ�
			 * Ӧ�ã� 1.�����������
			 * 		2.���ȶ���
			 * 		3.��Ϣ����
			 * �ص㣺 1.���Խṹ
			 * 		2.�������������һ���֣����Կ������Ӽ���
			 * 		3.Ԫ�����Ƚ��ȳ���first in first out��FIFO
			 * 		4.�������һ�ˣ���β�����Ԫ�أ���һ�ˣ����ף�ȡ��Ԫ�أ�
			 * 		   ����������������������������������
			 * ���� ������| 2 | 3 | 1 | 4 | ��������β
			 *         ����������������������������������
			 *          ^-getFront(ָ�����)
			 */
import com.queue.Queue_Interface;

public class ArrayQueue<E> implements Queue_Interface<E> {
	private MyArray<E> array;//�Զ���Ķ�̬����
	//�Զ����������вι���
	public ArrayQueue(int capacity) {
		array=new MyArray<>(capacity);
	}
	//��ʼ����Ϊ10���޲ι���
	public ArrayQueue() {
		array=new MyArray<>();
	}
	//������Ԫ�صĸ���--O(1)
	@Override
	public int size() {
		return array.size();
	}
	//�ж϶����Ƿ�Ϊ��--O(1)
	@Override
	public boolean isEmpty() {
		return array.isEmpty();
	}
	//�����--O(1)-��̯���Ӷȣ��������ݵ�O��n����
	@Override
	public void enqueue(E item) {
		array.addLast(item);
	}
	//������--O(n)�����������ݸ��Ӷȣ�----ʱ�临�Ӷ��е�ߣ���ΪҪ�ƶ�n-1������
	@Override
	public E dequeue() {
		return array.removeFirst();
	}
	//��ȡ���׵�Ԫ��--O(1)
	@Override
	public E getFront() {
		return array.getFirst();
	}
	//��ȡ���е�ǰ������--O(1)
	public int getCapacity() {
		return array.getCapacity();
	}
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(String.format("Queue: size=%d , capacity=%d\n",array.size(),array.getCapacity()));
		s.append("front[");
		for(int i =0;i<size();i++) {
			s.append(array.get(i));
			if(i != size()-1) {
				s.append(", ");
			}
		}
		s.append("]tail");
		return s.toString();
	}
}
