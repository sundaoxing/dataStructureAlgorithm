package com.unionfind_data;
public class UnionFind_2 implements UnionFind {
	/*
	 * 	���鼯��ʵ�ֵĵڶ����汾���Ż�Union����
	 *  	ԭ�򣺵�һ�棺Union����ʱ�临�Ӷ�ΪO��n������Ҫ�Ż�
	 *  �Ż���
	 * 		1.��̬����ʵ�֣������д洢��ֵ���ǵ�ǰԪ��ָ����һ��Ԫ�ص�����
	 * 		2.ÿ��Ԫ�ؿ���һ���ڵ㣬�ϲ�����Ԫ�ؾ���������һ���ڵ�ָ����һ���ڵ�
	 * 		3.���ڵ㣺��Ӧ�����У����� == �������´洢��ֵ
	 * 		4.����ͬһ�����ϣ�ӵ����ͬ�ĸ��ڵ�
	 * 
	 *  0	1	2	3	4	5	6	7	8	9----------���ڵ㣨��ʼʱÿ��Ԫ��ָ���Լ���
	 *  0	1	2	3	4	5	6	7	8	9----------Ԫ�أ�����������
	 */
	private int [] parent;//���ڵ�����
	
	public UnionFind_2(int size) {
		parent = new int[size];
		for(int i=0;i<size;i++) {
			parent[i]=i;
		}
	}
	@Override
	public int size() {
		return parent.length;
	}

	@Override
	public boolean isConnected(int p, int q) {
		return find(p) == find(q);
	}
	/*
	 * �飺ʱ�临�Ӷȣ�O��h����hΪ���ĸ߶�
	 * ˼�룺��������������ֹ��������Ԫ���Ǹ��ڵ�
	 * ˼·��
	 * 		1.�ж�Ԫ�غ���ָ���Ԫ���Ƿ����
	 * 			��ȣ����Ǹ��ڵ㣬ֱ�ӷ���Ԫ��ֵ
	 * 			����ȣ�˵�����Ǹ��ڵ㣬���и��ڵ�
	 * 				  �Ӹ��ڵ�����������Ҹ��ڵ�ĸ��ڵ�
	 *  0	1	1	8	3	0	5	1	8	8----------���ڵ�
	 *  -------------------------------------
	 *  0	1	2	3	4	5	6	7	8	9----------Ԫ�أ�����������
	 */
	private int find(int i) {
		if(i<0 || i>=parent.length) {
			throw new IndexOutOfBoundsException();
		}
		//���ϲ��������Ĺ���
		while(i != parent[i]) {
			i=parent[i];
		}
		return i;
	}
	/*	����
	 *  ˼�룺�ϲ�p��q��--��Ԫ��pָ��Ԫ��q--��[p] == q
	 *  ˼·��
	 *  	1.����Ԫ��p��q�ĸ��ڵ�
	 *  	2.�ж�p��q���ڵ��Ƿ���ͬ
	 *  		��ͬ:ֱ�ӷ���
	 *  		��ͬ��Ԫ��qָ��Ԫ��p / Ԫ��qָ��Ԫ��p�����ַ�ʽ���ԣ�
	 *  0	1	1	8	3	0	5	1	8	8----------���ڵ�
	 *  -------------------------------------
	 *  0	1	2	3	4	5	6	7	8	9----------Ԫ�أ�����������
	 */
	@Override
	public void unionElements(int p, int q) {
		int pRoot=find(p);
		int qRoot=find(q);
		if(pRoot == qRoot) {
			return;
		}
		parent[qRoot] = pRoot;
	}

}
