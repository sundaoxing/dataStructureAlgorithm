package com.weightgraph;

/*
 * ����������С��
 * �ײ�ṹ������
 * 0, 1 , 2 , 3 , 4 , 5 , 6 , 7 , 8 , 9 , 10 	�±�
 * 
 *   10 , 9 , 7 , 8 , 5 , 6 , 3 , 1 , 4 , 2		indexs�ѣ�����data��������
 *   
 *   15 , 17, 19, 13, 22, 16, 28, 30, 41, 62	data���ݣ�����data���ݣ�
 *   
 *    8 , 10, 7 , 9 , 5 , 6 , 3 , 4 , 2 , 1		reverse��������������������������indexs��������
 *    
 *    reverse[i] =>����i��indexs[]�е�λ��
 *    indexs[i]=j <-> reverse[j]=i
 *   
 *    indexs[reverse[i]]==i  	reverse[1]==8 -> indexs[8]==1
 *   
 *    reverse[indexs[i]]==i 	indexs[1]==10 -> reverse[10]==1  
 * iΪ��ǰ�ڵ�
 * parent �ڵ�= i/2;
 * ���� �ڵ�= i*2
 * �Һ��� �ڵ�= i*2+1
 * ���һ����Ҷ�ӽڵ�������count/2
 */


public class IndexMinHeap {
	private double[] data;// ����
	private int[] indexs;// data����
	private int[] reverse;// indexs����
	private int count;// ���ݸ���
	private int capacity;// ��ʼ��data��������

	// ��ʼ����
	public IndexMinHeap(int capacity) {
		data =new double[capacity+1];
		indexs = new int[capacity + 1];
		reverse = new int[capacity + 1];
		for (int i = 0; i < capacity + 1; i++) {
			reverse[i] = 0;// ��ʼ����û��indexs����(��1��ʼ��0���ʲ���)
		}
		count = 0;
		this.capacity = capacity;
	}

	// heapify,�Ż��������ѵķ���
	public IndexMinHeap(double[] arr) {
		data =new double[capacity+1];
		for (int i = 0; i < arr.length; i++) {
			data[i + 1] = arr[i];
		}
		count = arr.length;
		// �ȸ�ֵ�����幹������
		for (int k = count / 2; k >= 1; k--) {
			shiftDown(k);
		}
	}

	// ������С�ѵ�Ԫ�ظ���
	public int getSize() {
		return count;
	}

	// �ж���С���Ƿ�Ϊ��
	public boolean isEmpty() {
		return count == 0;
	}

	// ������Ԫ�أ�β�壩
	// �����i���û����ԣ��Ǵ�0������ʼ��
	public void insert(int i, double item) {
		i += 1;
		data[i]=item;// ��������
		indexs[count + 1] = i;// ��������
		reverse[i] = count + 1;
		count++;
		shiftUp(count);// ���µ���ΪС����
	}

	private void shiftUp(int k) {
		// ���ϵ����²���Ԫ�ص�λ�ã��ж���͸��ڵ�Ĵ�С
		while (k > 1 && data[indexs[k / 2]] > data[indexs[k]]) {
			Swap(indexs, k / 2, k);// ֻ��������������ֵ��������ֻ���ڱȽ�
			reverse[indexs[k / 2]] = k / 2;
			reverse[indexs[k]] = k;
			k /= 2;
		}
	}

	private void Swap(int[] indexs, int i, int k) {
		int t = indexs[i];
		indexs[i]= indexs[k];
		indexs[k]=t;
	}

	// ��ȡ��С�ѵ���Сֵ,���߼���ɾ����Ԫ��
	public double getMin() {
		if (!isEmpty()) {// �ж���С���Ƿ�Ϊ��
			double max = data[indexs[1]];
			Swap(indexs, 1, count);
			reverse[indexs[1]] = 1;
			reverse[indexs[count]] = 0;// �߼���ɾ����Ԫ��
			count--;
			shiftDown(1);// ���µ���Ϊ��С��
			return max;
		} else {
			return Double.MIN_VALUE;
		}
	}

	private void shiftDown(int i) {
		while (i * 2 <= count) {// �ж��Ƿ�������
			int j = i * 2;// ����ָ�룺����i�ڵ����Һ��ӽϴ�ֵ���±꣬Ĭ�����ӽϴ�
			if (j + 1 <= count && data[indexs[j + 1]] < data[indexs[j]]) {
				j = j + 1;
			}
			if (data[indexs[i]]<= data[indexs[j]]) {
				break;// �ж�i�ڵ�����Һ��Ӽ�ϴ�ֵ�Ĵ�С
			}
			Swap(indexs, i, j);
			reverse[indexs[i]] = i;
			reverse[indexs[j]] = j;
			i = j;
		}
	}

	// ��ȡ��С�ѵ���СԪ�ص������������ѵ��¹��ܣ�
	public int getMinIndex() {
		if (!isEmpty()) {// �ж���С���Ƿ�Ϊ��
			int maxIndex = indexs[1] - 1;
			Swap(indexs, 1, count);
			count--;
			shiftDown(1);// ���µ���Ϊ��С��
			return maxIndex;
		} else {
			return Integer.MAX_VALUE;
		}
	}

	// ��������ֱ�ӻ�ȡdata���������ݣ������ѵ��¹��ܣ�
	public double getData(int i) {
		if (contain(i)) {
			return data[i + 1];
		}else {
			return Double.MAX_VALUE;
		}
	}

	// �����������޸�data�и�������ʾ��Ԫ�أ�����Ҫ�ж�i�ڶ�����û�ж�Ӧ��Ԫ��
	public void change(int i, double newItem) {
		if (contain(i)) {
			i += 1;
			data[i]=newItem;
			// �ҵ�indexs[j]==i,j��ʾdata[i]�ڶ��е�λ��
			int j = reverse[i];
			shiftUp(j);
			shiftDown(j);
			/*
			 * ��Ϊ����ʹ���˷����������reverse��������indexs������λ�ã����Բ����ڱ���indexs
			 * ��Ҫά�����ѵ�����(Ҫ�ڶ����ҵ�newItem��λ��) �ҵ�indexs[j]==i,j��ʾdata[i]�ڶ��е�λ�� for(int
			 * j=1;j<=count;j++) { if(indexs[j]==i) { shiftUp(j); shiftDown(j); return; } }
			 */
		}
	}

	//�ж�i�ڶ�����û�ж�Ӧ��Ԫ��
	public boolean contain(int i) {
		if (i + 1 >= 1 && i + 1 <= capacity) {
			return reverse[i + 1] != 0;
		} else {
			return false;
		}
	}
}
