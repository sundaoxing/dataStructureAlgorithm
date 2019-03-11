package com.set;
import com.binarySearch.BST;

/*
 *  			����----set
 *  	�ص㣺
 *  		1.�������ظ�Ԫ��
 *  		2.�ײ����ݽṹ����������������̬���ݽṹ��
 *  		3.Set�ж�����������ͬ����ʹ��"=="����������Ǹ���equals����
 *  		4.Set������Ķ������֮��û�����Ե�˳��ȡ��˳���ǰ������ʱ��˳��
 *  	Ӧ�ã��ͻ���ͳ��
 *  		��Ƶͳ��
 */
public class BSTSet<E extends Comparable<E>> implements Set<E> {
	private BST<E> s;//�ײ�ʵ�ֽṹ-����������
	
	public BSTSet() {
		s=new BST<>();
	}
	//����set��ϵ�Ԫ�ظ���
 	@Override
	public int size() {
		return s.size();
	}
 	//�ж�set����Ƿ�Ϊ��
	@Override
	public boolean isEmpty() {
		return s.isEmpty();
	}
	//��set���������Ԫ��---ʱ�临�Ӷȣ�O��log��n����ƽ��
	@Override
	public void add(E item) {
		s.add(item);//��������ظ�Ԫ�أ���Ϊ����û�ж�key��ȵ�Ԫ�ؽ��д���
	}
	//��set������ɾ��Ԫ��---ʱ�临�Ӷȣ�O��log��n����ƽ��
	@Override
	public void remove(E item) {
		s.remove(item);
	}
	//�ж�set�����Ƿ������Ԫ��---ʱ�临�Ӷȣ�O��log��n����ƽ��
	@Override
	public boolean contains(E item) {
		return s.contain(item);
	}

}
