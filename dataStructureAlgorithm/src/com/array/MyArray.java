package com.array;

import java.util.Arrays;
		/*			��̬����
		 * ���Խṹ
		 * ����ͬ���͵�Ԫ�أ�element���ļ�������ɵ����ݽṹ��
		 * ����һ���������ڴ����洢������Ԫ�ص�������index�����Լ������Ԫ�ض�Ӧ�Ĵ洢��ַ
		 * �ص㣺������ʣ����Һ��޸ģ������ٶȿ�
		 * 	        ���Ӻ�ɾ������β���������ٶȽ���
		 *  ������������������������������������
		 *  ����  | 1 | 2 | 3 | ������������
		 *  ������������������������������������
		 *    0	  1   2	  3  ->����
		 * Ӧ�ã� 1.��ѧ����������
		 * 		2.���ݿ��
		 * 		3.�������ݽṹ���б�List���ѣ�ɢ�б�˫����У����У�ѭ�����У���ջ���ַ���
		 * java���������ԣ�
		 * 		1.������һ�������������ͣ�����ı���ֻ��һ�����ã�����Ԫ�غ�����������ڴ����Ƿֿ���ŵ�
		 * 		2.Java������Ҫ�����е�����Ԫ�ؾ�����ͬ���������͡�
		 * 		3.��ά������һά���飬������Ԫ����һά����-��java��û�����������ϵĶ�ά����
		 */
public class MyArray<E> {
	private Object [] data;//�����������洢��������
	private int size;//���ݸ���
	//Ĭ������Ϊ10���޲ι���
	public MyArray() {
		this(10);
	}
	//�Զ����������вι���
	public MyArray(int capacity) {
		data=new Object [capacity];
		size=0;
	}
	//ʹ�ô������������ײ�����
	public MyArray(E[] arr) {
		data = new Object[arr.length];
		for(int i=0;i<arr.length;i++) {
			data[i]=arr[i];
		}
		size=arr.length;
	}
	//�������ݵĸ���
	public int size() {
		return size;
	}
	//�ж������Ƿ�Ϊ��
	public boolean isEmpty() {
		return size==0;
	}
	//���Ԫ�أ�������β����ӣ�O(1)-��̯���Ӷȣ���������O��n����
	public void addLast(E item) {
		add(size,item);
	}
	//���Ԫ�أ�������ͷ����ӣ�O(n)
	public void addFirst(E item) {
		add(0,item);
	}
	//���Ԫ�أ�������ָ�����������Ԫ�أ�O(n/2)-ƽ��
	public void add(int index,E item) {
		checkIndex(index);
		if(size == data.length) {
			resize(data.length+(data.length >> 1));
		}
		for(int i=size-1;i>=index;i--) {
			data[i+1]=data[i];
		}
		data[index]=item;
		size++;
	}
	//������������Ƿ�Ϸ�
	private void checkIndex(int index) {
		if(index<0 || index >size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
	}
	//���ݣ��µ�������data.length+(data.length >> 1)��<O(n)-�ײ�ʹ��JVM�Ż�
	private void resize(int newCapacity) {
		data=Arrays.copyOf(data, newCapacity);
	}
	//��ȡԪ�أ���ȡָ������λ���ϵ�Ԫ�أ�O(1)
	@SuppressWarnings("unchecked")
	public E get(int index) {
		checkIndex(index);
		return (E) data[index];
	}
	
	public E getLast() {
		return get(size-1);
	}
	public E getFirst() {
		return get(0);
	}
	//�޸�Ԫ�أ��޸�ָ������λ���ϵ�Ԫ�أ�O(1)
	public void set(int index,E item) {
		checkIndex(index);
		data[index]=item;
	}
	//ɾ��Ԫ�أ�ɾ��ָ������λ���ϵ�Ԫ�أ�O(n)
	public E remove(int index) {
		if(index<0 || index >=size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		@SuppressWarnings("unchecked")
		E item=(E) data[index];
		for(int i = index+1;i<size;i++) {
			data[i-1]=data[i];//���ַ�ʽ��ѭ����������һ��
		}
		/*���ַ�ʽ��ѭ��������������һ��һ��
		 * for(int i=index;i<size;i++) {
			data[i]=data[i+1];
		}*/
		size--;//������û���˵��ֻ�ܿ�������ȡ��0-size-1��Χ�ڵ�ֵ
		data[size]=null;//��ʾ��ֵΪnull����gc����
		/*��С����
		 * if(size < data.length/4 && data.length/2 !=0) {
		 *resize(data.length);
		}*/
		return item;
	}
	//ɾ�������е�һ��Ԫ�أ�O(n)
	public E removeFirst() {
		return remove(0);
	}
	//ɾ�����������һ��Ԫ�أ�O(1)
	public E removeLast() {
		return remove(size-1);
	}
	//�ж������Ƿ������Ԫ�أ�O(n)
	public boolean contain(E item) {
		for(int i=0;i<size;i++) {
			if(item.equals(data[i])) {//�����˿�ָ���쳣
				return true;
			}
		}
		return false;
	}
	//���Ҹ�Ԫ���������д洢��������O(n)
	public int findIndex(E item) {
		for(int i=0;i<size;i++) {
			if(item.equals(data[i])) {//�����˿�ָ���쳣
				return i;
			}
		}
		return -1;
	}
	//ɾ��Ԫ�أ�����Ԫ��ɾ�������и�Ԫ�أ�O(n)+O(n)=O(n)
	public void removeElement(E item) {
		int index = findIndex(item);
		if(index != -1) {
			remove(index);
		}
	}
	
	public int getCapacity() {
		return data.length;
	}
	
	public void swap(int i,int j) {
		Object t = data[i];
		data[i]=data[j];
		data[j]=t;
	}
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(String.format("Array: size=%d , capacity=%d\n", size,data.length));
		s.append("[");
		for(int i =0;i<size;i++) {
			s.append(data[i]);
			if(i != size-1) {
				s.append(", ");
			}
		}
		s.append("]");
		return s.toString();
	}
}
