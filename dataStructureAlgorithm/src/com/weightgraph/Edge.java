package com.weightgraph;
		/*带权图的边
		 * 存储信息较多，需要单独封装成一个类
		 * 以后的邻接表或者邻接矩阵存储的都是类Edge
		 */
public class Edge<W>{
	private int a,b;//无向图，节点a，b-----有向图，节点a-》b
	private W weight;//权重
	
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
	
	//已知相连两个节点中的一个节点a,获取另一个节点b
	public int other(int x) {
		return x==a?b:a;
	}
}
