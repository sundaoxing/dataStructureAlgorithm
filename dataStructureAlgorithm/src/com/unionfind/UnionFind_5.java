package com.unionfind;

/*·��ѹ�������ڵ����ӵ����ĸ��ڵ�
 * 
 */
public class UnionFind_5 {
	/*������ʵ�ַ�ʽ���߼��ṹ�����ͣ�����ṹ������
	 * �Ե����ֵ�һ���Ż�
	 * ÿһ��Ԫ�ؿ���һ���ڵ㣬ÿ���ڵ㶼��һ����ָ�룬�������Ԫ��p��q���������ʾp.parent->q
	 * ��ʼ��:ÿ���ڵ㶼ָ���Լ�
	 *    ������   0,1,2,3,4,5,6,7,8,9       ��=����������Ԫ��
	 * parent�� [0,1,2,3,4,5,6,7,8,9]	    ��=�������ʾ�����������������丸�ڵ��������ֵ��ͬ�����ʾԪ��֮�������ӵģ�
	 */
	private int [] parent;//��ָ������
	private int count;//Ԫ�ظ���
	private int []rank;//rank[i]������iΪ���ڵ�ļ��ϵ��������
	//��ʼ��
	public UnionFind_5(int n) {
		parent=new int [n];
		rank=new int [n];
		this.count=n;
		for(int i=0;i<n;i++) {
			parent[i]=i;
			rank[i]=1;//��ʼֵΪ1��ÿ��Ԫ�ض�ֻ�����������Ϊ1
		}
	}
	
	//���ҵ�ǰ�ڵ�p�ĸ��ڵ㣨��ǰ�ڵ�p == parent[p]ʱ��p���Ǹ��ڵ㣩
	public int findParent(int p) {
		if(p>=0 && p < count) {
			while(p != parent[p]) {
				/*·��ѹ��
				 * ��ǰ�ڵ�p�ĸ��ڵ�qָ��q�ĸ��ڵ㣨��һ������һ�㣩
				 */
				parent[p]=parent[parent[p]];
				p=parent[p];
			}
			return p;
			/*·��ѹ��--�����ϸ��ţ�ʵ���ϣ�û�����淽ʽ�Ż�
			 * ʹ�õݹ飬ѹ����2�����ͽṹ
			 */
			/*if(p != parent[p]) {
				parent[p]=findParent(parent[p]);
			}
			return parent[p];*/
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
		/*ֻ��Ҫ��p�ĸ��ڵ㣨����ָ��q�ĸ��ڵ㣨��������
		parent[pParent]=qParent;*/
		/*�Ż����������ַ�ʽ���򵥳���������������Ȼ����
		 * Ӧ���������Ӱ��֣���������������Ԫ�ظ����ٵ���һ������
		 * ָ��Ԫ�ض����һ������
		 */
		/*if(size[pParent] > size[qParent]) {
			parent[qParent]= pParent;
			size[pParent] +=size[qParent];
		}
		else {
			parent[pParent]= qParent;
			size[qParent] +=size[pParent];
		}*/
		/*��һ�������a����Ԫ�ظ�������b���ϣ�����b���ϵ�������ȴ���a���ϣ�����
		 * a��b���Ӻ�����������Ȼ�Ӵ���Ҫ���������ϵ�rank����ȣ����бȽ�
		 */
		if(rank[pParent] < rank[qParent]) {
			parent[pParent]=qParent;
		}
		else if (rank[pParent] > rank[qParent]) {
			parent[qParent] = pParent;
		}
		else{//rank[pParent] == rank[qParent]
			parent[pParent]=qParent;
			rank[qParent] +=1;
		}
	}
}
