package com.binarySearch;

/*����������---ÿ���ڵ�
 */
public class BinarySearchNode<V> {
	private int key;//��
	private V value;//ֵ
	private BinarySearchNode<V> left;//����
	private BinarySearchNode<V> right;//�Һ���
	
	//��ʼ��
	public BinarySearchNode(int key,V value) {
		this.key=key;
		this.value=value;
		this.left=this.right=null;
	}
	
	public BinarySearchNode(BinarySearchNode<V> node) {
		this.key=node.key;
		this.value=node.value;
		this.left=node.left;
		this.right=node.right;
	}
	
	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public BinarySearchNode<V> getLeft() {
		return left;
	}

	public void setLeft(BinarySearchNode<V> left) {
		this.left = left;
	}

	public BinarySearchNode<V> getRight() {
		return right;
	}

	public void setRight(BinarySearchNode<V> right) {
		this.right = right;
	}
	
}
