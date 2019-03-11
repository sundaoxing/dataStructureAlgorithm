package com.unionfind;

public class UnionFind_2 {
	/*�ڶ���ʵ�ַ�ʽ���߼��ṹ�����ͣ�����ṹ������
	 * ÿһ��Ԫ�ؿ���һ���ڵ㣬ÿ���ڵ㶼��һ����ָ�룬�������Ԫ��p��q���������ʾp.parent->q
	 * ��ʼ��:ÿ���ڵ㶼ָ���Լ�
	 *    ������   0,1,2,3,4,5,6,7,8,9       ��=����������Ԫ��
	 * parent�� [0,1,2,3,4,5,6,7,8,9]	    ��=�������ʾ�����������������丸�ڵ��������ֵ��ͬ�����ʾԪ��֮�������ӵģ�
	 */
	private int [] parent;//��ָ������
	private int count;//Ԫ�ظ���
	//��ʼ��
	public UnionFind_2(int n) {
		parent=new int [n];
		this.count=n;
		for(int i=0;i<n;i++) {
			parent[i]=i;
		}
	}
	
	//���ҵ�ǰ�ڵ�p�ĸ��ڵ㣨��ǰ�ڵ�p == parent[p]ʱ��p���Ǹ��ڵ㣩
	public int findParent(int p) {
		if(p>=0 && p < count) {
			while(p != parent[p]) {
				p=parent[p];
			}
			return p;
		}else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	//�ж������ڵ��Ƿ������ӵ�
	public boolean isConnected(int p,int q) {
		return findParent(p) == findParent(q);
	}
	
	//���������ڵ�
	/*quick union��
	 * ʱ�临�Ӷȣ�O��log��n����
	 *    ������   0,1,2,3,4,5,6,7,8,9       ��=��������Ԫ��
	 * parent�� [1,1,1,8,3,0,5,1,8,8]	
	 */
	public void unionElement(int p,int q) {
		int pParent=findParent(p);//parent[p]
		int qParent=findParent(q);//parent[q]
		if(pParent == qParent) {
			return;
		}
		//ֻ��Ҫ��p�ĸ��ڵ㣨����ָ��q�ĸ��ڵ㣨��������
		parent[pParent]=qParent;
	}
}
