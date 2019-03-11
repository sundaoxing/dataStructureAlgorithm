package com.unionfind_data;

public class UnionFind_3 implements UnionFind {
/*
 * 	���鼯���Ľ��棬�ڵڶ����汾�ϸĽ�
 * 		ԭ�򣺵ڶ����Union�����������������Ⱥܴ������˻�����������find����ʱ�临�Ӷȹ��ߣ���Ҫ�Ż�
 * 	�Ż�:
 * 		1.����һЩ��Ϊ�޶�������ʹ�������ȹ���
 * 		2.��Ҫ�������飬��ά���������
 *  1	1	1	1	1	1	1	1	1	1----------�Ե�ǰ����Ϊ���ڵ�ļ��ϵ�Ԫ�ظ���
 *  0	1	2	3	4	5	6	7	8	9----------���ڵ㣨��ʼʱÿ��Ԫ��ָ���Լ���
 *  0	1	2	3	4	5	6	7	8	9----------Ԫ�أ�����������
 */
	private int [] parent;//���ڵ�
	private int [] size;//size[i]��ʾ�����ڵ�Ϊi�ļ��ϵ�Ԫ�ظ���
	public UnionFind_3(int siz) {
		parent= new int[siz];
		size = new int[siz];
		for(int i=0;i<siz;i++) {
			parent[i]=i;
			size[i]=1;//��ʼ��Ĭ��ÿ��Ԫ�ؾ���һ�������߶Ⱦ���1
		}
	}
	@Override
	public int size() {
		return parent.length;
	}

	@Override
	public boolean isConnected(int p, int q) {
		return find(p)==find(q);
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
		while(i != parent[i]) {
			i=parent[i];
		}
		return i;
	}

	/*
	 * ����ʱ�临�Ӷȣ�O��h����hΪ���ĸ߶�
	 * ˼�룺sizeС�����ĸ��ڵ�ָ��size������ĸ��ڵ㣨��ֹ�ϲ���������ȹ���
	 * ˼·��
	 * 		1.����Ԫ��p��q�ĸ��ڵ�
	 *  	2.�ж�p��q���ڵ��Ƿ���ͬ
	 *  		��ͬ:ֱ�ӷ���
	 *  		��ͬ���ж�p��q���ڵ��size��С
	 *  			 �� p��<q����size��
	 *  					p��ָ��q����ά��size����
	 *  			�� p��>=q����size��
	 *  					q��ָ��p����ά��size����
	 *  1	1	1	1	1	2	1	1	3	1----------ָ���Լ�������Ԫ�ظ���
	 *  0	1	2	8	3	5	5	7	8	9----------���ڵ㣨��ʼʱÿ��Ԫ��ָ���Լ���
 	 *  0	1	2	3	4	5	6	7	8	9----------Ԫ�أ�����������
	 */
	@Override
	public void unionElements(int p, int q) {
		int pRoot=find(p);
		int qRoot=find(q);
		if(pRoot == qRoot) {
			return;
		}
		if(size[pRoot] < size[qRoot]) {
			parent[pRoot] = qRoot;
			size[qRoot] +=size[pRoot];
		}else {
			parent[qRoot]=pRoot;
			size[pRoot]+=size[qRoot];
		}
	}
}
