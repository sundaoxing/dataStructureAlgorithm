package com.unionfind;
/*
 * ���鼯��
 * 		1��find(q)����
 * 		2:union(p,q)�ϲ�
 * 			=����isConnected��p��q���ж�p��q�Ƿ�����
 * ���ر�����ͽṹ
 * ���ã�����������⣺
 * 		1����������ĸ����ڵ������״̬���û�����/·������/��·����/�������硣������
 * 		2��ʵ����ѧ�еļ���
 */
public class UnionFind_1 {
	/*��һ��ʵ�ַ�ʽ������
	 * ��ʼ��
	 * ������ 0,1,2,3,4,5,6,7,8,9       ��=��������Ԫ��
	 * id�� [0,1,2,3,4,5,6,7,8,9]	    ��=�����ʾ���������ֵ��ͬ�����ʾԪ��֮�������ӵģ�
	 */
	private int[] id;//Ԫ������
	private int count;//Ԫ�ظ���

	//��ʼ��
	public UnionFind_1(int n) {
		id = new int[n];
		this.count = n;
		for (int i = 0; i < n; i++) {
			id[i] = i;
		}
	}

	//quick find������Ԫ��p�����ӽڵ�
	//ʱ�临�Ӷȣ�O��1��
	public int find(int p) {
		if (p >= 0 && p < count) {
			return id[p];
		}else {
			throw new ArrayIndexOutOfBoundsException();
		}
		
	}
	
	//�ж�����Ԫ���Ƿ����ӣ�ֵ��ͬ�����ʾԪ��֮�������ӵģ�
	public boolean isConnected(int p,int q) {
		return find(p) == find(q);
	}
	
	
	/*��������Ԫ�أ�ʹ���ǵ�id[]��ͬ��
	 * ʱ�临�Ӷȣ�O��n��
	 * ������ 0,1,2,3,4,5,6,7,8,9       ��=��������Ԫ��
	 * id�� [0,1,0,1,0,1,0,1,0,1]	
	 */
	public void unionElement(int p,int q) {
		int pId=find(p);//id[p]
		int qId=find(q);//id[q]
		if(pId == qId) {
			return;
		}
		//����id[]���飬�����е�id[p]����Ϊid[q]���ɴ���p��q����
		for(int i=0;i<count;i++) {
			if(id[i]==pId) {
				id[i]=qId;
			}
		}
	}
}
