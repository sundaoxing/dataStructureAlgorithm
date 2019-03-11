package com.weightgraph;
		/*��Ȩͼ�ı�
		 * �洢��Ϣ�϶࣬��Ҫ������װ��һ����
		 * �Ժ���ڽӱ�����ڽӾ���洢�Ķ�����Edge
		 */
public class Edge<W>{
	private int a,b;//����ͼ���ڵ�a��b-----����ͼ���ڵ�a-��b
	private W weight;//Ȩ��
	
	public Edge() {
	}

	public Edge(int a, int b, W weight) {
		this.a = a;
		this.b = b;
		this.weight = weight;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public W getWeight() {
		return weight;
	}

	public void setWeight(W weight) {
		this.weight = weight;
	}
	
	//��֪���������ڵ��е�һ���ڵ�a,��ȡ��һ���ڵ�b
	public int other(int x) {
		return x==a?b:a;
	}
}
