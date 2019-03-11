package com.unionfind_data;

public class UnionFind_5 implements UnionFind {
	/*
	 * ���鼯���Ե��İ�ĸĽ���
	 * 		ԭ������Ԫ�ص����࣬������Ȳ��ɱ���ļ����ʱ����Ҫ·��ѹ��
	 * �Ż���
	 * 		1.·��ѹ������ǰ�ڵ�ָ��ǰ�ڵ�ĸ��ڵ�ĸ��ڵ㣨���ָ��
	 * 			parent[i]=parent[parent[i]];
	 * 
	 * 		rank���ȣ��򣩣�˵����ȣ�����׼ȷ����ʵ����ÿ���ڵ���򣬰�����Ĵ�С�ϲ�������
	 */
	private int [] parent;//���ڵ�
	private int [] rank;//rank[i]:��iΪ���ڵ��������ȣ�����ѹ��·���󣬲���׼ȷ����ȣ����Ǵ�ŵ���ȣ�
	
	public UnionFind_5(int size) {
		parent=new int [size];
		rank=new int[size];
		for(int i=0;i<size;i++) {
			parent[i]=i;
			rank[i]=1;
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
	 * �飺ʱ�临�Ӷȣ�O��log��*n����
	 * 				0					n<=1
	 * log��*n��={	
	 * 				1+log��*log��n����	n>1
	 * ����O��1������
	 * ˼�룺��������������ֹ��������Ԫ���Ǹ��ڵ�
	 * ˼·��
	 * 		1.�ж�Ԫ�غ���ָ���Ԫ���Ƿ����
	 * 			��ȣ����Ǹ��ڵ㣬ֱ�ӷ���Ԫ��ֵ
	 * 			����ȣ�˵�����Ǹ��ڵ㣬���и��ڵ�
	 * 				  ����·��ѹ��
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
			parent[i]=parent[parent[i]];//·��ѹ��
			i=parent[i];
		}
		return i;
	}
	/*
	 * ����ʱ�临�Ӷ� O��log��*n����
	 * ˼�룺rankС�����ĸ��ڵ�ָ��rank������ĸ��ڵ㣨��ֹ�ϲ���������ȹ���
	 * ˼·��
	 * 		1.����Ԫ��p��q�ĸ��ڵ�
	 *  	2.�ж�p��q���ڵ��Ƿ���ͬ
	 *  		��ͬ:ֱ�ӷ���
	 *  		��ͬ���ж�p��q���ڵ��rank��С
	 *  			 �� p��<q����rank��
	 *  					p��ָ��q��
	 *  			�� p��>q����rank��
	 *  					q��ָ��p��
	 *  			�� p��=q����rank��
	 *  					q��ָ��p�� / p��ָ��q�������ַ�ʽ�����ԣ�
	 *  					ά��rank����
	 *  1	1	1	1	1	2	1	1	3	1----------rank
	 *  0	1	2	8	3	5	5	7	8	9----------���ڵ㣨��ʼʱÿ��Ԫ��ָ���Լ���
 	 *  0	1	2	3	4	5	6	7	8	9----------Ԫ�أ�����������
	 */
	@Override
	public void unionElements(int p, int q) {
		int pRoot = find(p);
		int qRoot = find(q);
		if(pRoot == qRoot ) {
			return;
		}
		if(rank[pRoot] < rank[qRoot]) {
			parent[pRoot] = qRoot;
		}
		else if(rank[pRoot] > rank[qRoot]) {
			parent[qRoot] = pRoot;
		}
		else {
			parent[qRoot] = pRoot;
			rank[pRoot] +=1;
		}
	}
}
