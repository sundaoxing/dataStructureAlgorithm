package com.unionfind_data;

public class UnionFind_6 implements UnionFind {
	private int [] parent;
	private int [] rank;
	
	public UnionFind_6(int size) {
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
	 * �ڵ�����ϸı䣺
	 * 		findʹ�õݹ�ķ���,ʹ�ã�ÿһ���ڵ㶼ֻ��һ�����ڵ�
	 * 		�������Ϊ2
	 * 	��������ʱ�������ϲ�һ�����ڵ���棬��Ϊ�ݹ������Ҫ��ʱ����ܸ���
	 * 
	 * �ݹ鷽����int find(int i)
	 * �ݹ���ֹ������
	 * 		�ҵ����ڵ㣺i==parent[i]
	 * �ݹ鹫ʽ��
	 * 		int find(parent[i])
	 */
	//����Ԫ��i�ĸ��ڵ㣨�ݹ�ĺ�����壩
	private int find(int i) {
		if(i<0 || i>=parent.length) {
			throw new IndexOutOfBoundsException();
		}
		/*while(i != parent[i]) {
			parent[i]=parent[parent[i]];
			i=parent[i];
		}
		return i;*/
		if(i != parent[i]) {
			parent[i] = find(parent[i]);
		}
		return parent[i];
	}
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
