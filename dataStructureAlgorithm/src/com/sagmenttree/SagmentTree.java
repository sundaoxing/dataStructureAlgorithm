package com.sagmenttree;
/*
 * 				�߶�����
 * 	ΪʲôҪʹ�ã��������������һ�����䣬�����ǵ����Ľڵ㣬�ɶ�����ڽڵ��γɵ�����
 * 			�ص㣺 1.��������������������ʹ��null��䣬��ʵ�����������Ľṹ
 * 				 2.�ײ����ݽṹʹ�á����顿ģ�����������ṹ
 * 				 3.��һ��ƽ�����������Ҳ�ǣ�
 * 				 4.��������������ӣ����䳤�ȹ̶���һ�������
 * 				 5.���ڵ�洢����Ϣ==�������Һ��Ӵ洢��Ϣ���ۺ�
 * 				 6.�߶�����һ��ƽ����������������һ�����仮�ֳ�һЩ��Ԫ����
 * 				       ÿ����Ԫ�����Ӧ�߶����е�һ��Ҷ�ӽ�㡣��Ҫ�Ĵ���˼���ǻ��ڷ��ε�˼��
 * 		Ӧ�ó����� 1.����Ⱦɫ
 * 				2.�����ѯ
 * 				3.����ͳ�Ʋ�ѯ
 * 
 * 	 ���䣨���֣�---------------------[0-9]:0----------------------------���ڵ㣺��������
 * 						/							\
 * 					[0-4]:1					 	   [5-9]:2
 * 				/		     \			   		/		 	 \
 * 			[0-2]:3		   [3-4]:4			[5-7]:5		 	[8-9]:6
 * 			/	  \		   /	 \      	/	   \     	/	   \
 * 		[0-1]:7	[2]:8	[3]:9	[4]:10	[5-6]:11  [7]:12  [8]:13  [9]:14
 *     /	\						   /	\					  /	  \
 * [0]:15 [1]:16					[5]:23 [6]:24				null  null---��null��䣬������ȫ������	
 * 	^-[0][1][2][3][4][5]...[7]:����data�����е�Ԫ��
 * 	   ^-7,8,9,10.........14 :����tree������������Ԫ�غ�Ԫ��֮���ںϺ����Ԫ���Ǵ����tree�����е�
 * ����tree����ռ��С��������
 * 				0			��һ�㣺1���ڵ�
 *			/       \	
 *		   1		 2		�ڶ��㣺2���ڵ�
 *		/     \	  /     \
 *	   3	   4 5		 6	�����㣺4���ڵ�
 *	....................	���������������� ��n�㣺2^(n-1)���ڵ�
 *	�������Ԫ�ظ���Ϊn��n=2^k-------->ze tree����Ҫ����2n���ڵ�
 *			���һ��Ľڵ���==ǰ�����нڵ���֮��
 *		�������������ڵ����Ϊn+1��n>2^k-------->�� tree����Ҫ����2n+2n���ڵ㣨�����¿�һ��ռ䣩
 */
public class SagmentTree<E> {
	private Object[] data;//����Ԫ��
	private Object[] tree;//��ȫ�������ṹ
	private Merger<E> merger;//�涨�����ں����������Ԫ�أ���ͣ������/��Сֵ����ƽ���ȵȣ�
	//���췽���������������飬�ںϹ���
	public SagmentTree(E[] arr,Merger<E> merger) {
		data = new Object[arr.length];
		for(int i=0;i<arr.length;i++) {
			data[i]=arr[i];
		}
		tree = new Object[4*arr.length];
		this.merger=merger;
		//�������������ݹ�ʵ��---------ʱ�临�Ӷȣ�O��log��n����
		buildSagmentTree(0,0,data.length-1);
	}
	/*
	 * �ݹ鷽����void buildSagmentTree(int treeIndex, int l, int r)
	 * �ݹ���ֹ���������ݹ鵽Ҷ�ӽڵ㣩
	 * 		���䳤��Ϊ0��l==r-----����ݹ鵽�˵���Ԫ�أ����Դ������䣬ֻ�����������һ��Ԫ�ض���
	 * �ݹ鹫ʽ������[l-r]���֣�
	 * 		buildSagmentTree(leftTreeIndex, l, mid);------����������
	 * 		buildSagmentTree(rightTreeIndex, mid+1, r);---����������
	 */
	//���������������ʾ�У�������treeIndexΪ���ڵ�������[l-r]Ϊ��������������ݹ�ĺ�����壩
	@SuppressWarnings("unchecked")
	private void buildSagmentTree(int treeIndex, int l, int r) {
		if(l==r) {
			tree[treeIndex]=data[l];
			return;
		}
		int leftTreeIndex=leftChild(treeIndex);
		int rightTreeIndex=rightChild(treeIndex);
		int mid=l+(r-l)/2;
		buildSagmentTree(leftTreeIndex, l, mid);
		buildSagmentTree(rightTreeIndex, mid+1, r);
		//�����Զ���������ںϵķ�����treeIndex�����Һ��Ӻϲ�������
		tree[treeIndex] = merger.merger((E)tree[leftTreeIndex], (E)tree[rightTreeIndex]);
	}
	//����������Ԫ�صĸ���
	public int size() {
		return data.length;
	}
	//��������������Ϊindex��Ԫ��
	@SuppressWarnings("unchecked")
	public E get(int index) {
		if(index<0 || index >=data.length) {
			throw new RuntimeException("index illegal");
		}
		return (E) data[index];
	}
	//�����������������ʾ�У���indexΪ���ڵ�����ӽڵ������
	public int leftChild(int index) {
		return 2*index+1;
	}
	//�����������������ʾ�У���indexΪ���ڵ���Һ��ӽڵ������
	public int rightChild(int index) {
		return 2*index+2;
	}
	
	//��ѯ��������������[queryL-queryR]�����ֵ------�ݹ�ʵ��---------ʱ�临�Ӷȣ�O��log��n����
	public E query(int queryL,int queryR) {
		if(queryL <0 || queryL>tree.length
			|| queryR<0 || queryR >tree.length|| queryL >queryR) {
			throw new RuntimeException("query index illegal");
		}
		return query(0,0,data.length-1,queryL,queryR);
	}
	/*
	 * �ݹ鷽����E query(int treeIndex, int l, int r, int queryL, int queryR)
	 * �ݹ���ֹ������
	 * 		��ѯ����==��ǰ���䣺queryL=l && queryR=r---��ֱ�ӷ��ص�ǰ�����ֵ
	 * �ݹ鹫ʽ��
	 * 		E query(leftTreeIndex, l, mid, queryL, mid)--------����������
	 * 		E query(rightTreeIndex, mid+1, r, mid+1, queryR)---����������
	 */
	//���������������ʾ�У�������treeIndexΪ���ڵ��[l,r]�����ڵ�,��ѯ[queryL,queryR]�����ֵ���ݹ�ĺ�����壩
	@SuppressWarnings("unchecked")
	private E query(int treeIndex, int l, int r, int queryL, int queryR) {
		if(queryL == l && queryR==r) {
			return (E) tree[treeIndex];
		}
		int leftTreeIndex = leftChild(treeIndex);
		int rightTreeIndex=rightChild(treeIndex);
		int mid = l+(r-l)/2;
		/*
		 * �����ѯ��Ϊ3�������
		 * 	1.��ѯ�����ڵ�ǰ�������ࣺqueryR <= mid
		 * 		ֱ�ӵ���ǰ����������������ѯ
		 * 	2.��ѯ�����ڵ�ǰ������Ҳ�ࣺqueryL >= (mid+1)
		 * 		ֱ�ӵ���ǰ����������������ѯ
		 *  3.��ѯ�����ڵ�ǰ������������ࣺ
		 * 		�ֱ𵽵�ǰ������������������ѯ�������ظ��ԵĽ�������ϲ����������Ľ������
		 */
		if(queryR <= mid) {
			return query(leftTreeIndex, l, mid, queryL, queryR);
		}
		else if(queryL >= (mid+1)) {
			return query(rightTreeIndex, mid+1, r, queryL, queryR);
		}
		E leftResult = query(leftTreeIndex, l, mid, queryL, mid);
		E rightRrsult=query(rightTreeIndex, mid+1, r, mid+1, queryR);
		return merger.merger(leftResult, rightRrsult);
	}
	//����index�ڵ��ֵ-----�ݹ�ʵ��---------ʱ�临�Ӷȣ�O��log��n����
	public void update(int index,E e) {
		if(index <0 || index >=data.length) {
			throw new RuntimeException("index illegal");
		}
		data[index]=e;
		//����tree���飨������������index�ڵ��ֵ
		set(0,0,data.length-1,index,e);
	}
	/*
	 * �ݹ鷽����void set(int treeIndex,int l,int r,int index,E e)
	 * �ݹ���ֹ������
	 * 		�ҵ�index��������ֵ��l==r
	 * �ݹ鹫ʽ��
	 * 		set(leftTreeIndex, l, mid, index, e)---------��������
	 * 		set(rightTreeIndex, mid+1, r, index, e)------��������
	 */
	//�����������������ʾ�У�������treeIndexΪ���ڵ��[l-r]����������Ϊindex��ֵ���ݹ�ĺ�����壩
	@SuppressWarnings("unchecked")
	public void set(int treeIndex,int l,int r,int index,E e) {
		if(l==r) {
			tree[treeIndex]=e;
			return;
		}
		int leftTreeIndex=leftChild(treeIndex);
		int rightTreeIndex = rightChild(treeIndex);
		int mid = l + (r-l)/2;
		if(index <= mid) {
			set(leftTreeIndex, l, mid, index, e);
		}
		else {
			set(rightTreeIndex, mid+1, r, index, e);
		}
		/*
		 * index������������֮�󣬸���ҵ���߼����丸�ڵ�ҲҪ�����ں�
		 */
		tree[treeIndex]=merger.merger((E)tree[leftTreeIndex], (E)tree[rightTreeIndex]);
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		for(int i=0;i<tree.length;i++) {
			if(tree[i] !=null) {
				s.append(tree[i]+" ");
			}else {
				s.append(" null ");
			}
		}
		return s.toString();
	}
}
