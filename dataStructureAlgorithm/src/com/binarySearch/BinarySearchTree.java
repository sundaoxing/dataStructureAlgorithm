package com.binarySearch;

import java.util.ArrayDeque;
import java.util.Queue;

/*�����������ص�
		 * ���ڵ㣺root
		 * ÿ���ڵ�ļ� > ��������
		 * ÿ���ڵ�ļ� < �����Һ���
		 * �����Һ���Ϊ���ڵ��������ȻΪ����������
		 */
public class BinarySearchTree<V> {
	private BinarySearchNode<V> root;// ���ڵ�
	private int count;// �������нڵ����

	// ��ʼ��
	public BinarySearchTree() {
		root = null;
		count = 0;
	}

	public int getSize() {
		return count;
	}

	public boolean isEmpty() {
		return root == null && count == 0;
	}

	// ���ݼ�������ֵ
	public void insert(int key, V value) {
		root = insert(root, key, value);// ��ʼ��ʱ���������ڵ㣨��һ�β���ʱ��ȷ����root�ڵ��key��value��
	}

	/*
	 * ��������������----�ݹ鹹��
	 * 	������������root�ڵ㣬��һ�δ��ݵĶ��Ǹ��ڵ㣬�����ٴδ��ݵ�ֻ�Ƕ����������е�һ���ڵ�
	 * ֻ������һ�����ѣ���Ҫ����
	 */
	private BinarySearchNode<V> insert(BinarySearchNode<V> root, int key, V value) {
		// �ڵ�Ϊ��ʱ�������Ǹ��ڵ㣬���ӣ��Һ��ӣ����½�һ���ڵ㣬��������������
		if (root == null) {
			count++;
			return new BinarySearchNode<V>(key, value);
		}
		// �����>��ǰ�ڵ�����ͺ͵�ǰ�ڵ���Һ��ӱȽ�
		if (key > root.getKey()) {
			root.setRight(insert(root.getRight(), key, value));
		}
		// �����<��ǰ�ڵ�����ͺ͵�ǰ�ڵ�����ӱȽ�
		else if (key < root.getKey()) {
			root.setLeft(insert(root.getLeft(), key, value));
		}
		// �����==��ǰ�ڵ�������µ�ǰ�ڵ��value
		else {
			root.setValue(value);
		}
		// ���ص�ǰ�ڵ�
		return root;
	}

	// �ж���rootΪ�ڵ�Ķ����������Ƿ������Ϊkey�Ľڵ�
	public boolean contain(int key) {
		return contain(root, key);
	}

	private boolean contain(BinarySearchNode<V> root, int key) {
		if (root == null) {
			return false;
		}
		//���ݶ���������������
		if (key > root.getKey()) {
			return contain(root.getRight(), key);
		} else if (key < root.getKey()) {
			return contain(root.getLeft(), key);
		} else {// key == root.getKey()
			return true;
		}
	}

	// ����rootΪ�ڵ�Ķ����������в��Ҳ����ؼ�Ϊkey�Ľڵ��value����
	public V search(int key) {
		return search(root, key);
	}

	private V search(BinarySearchNode<V> root, int key) {
		if (root == null) {
			return null;
		}
		if (key > root.getKey()) {
			return search(root.getRight(), key);
		} else if (key < root.getKey()) {
			return search(root.getLeft(), key);
		} else {// key == root.getKey()
			return root.getValue();
		}
	}

	// ǰ�����(������ȱ���)
	public void preOrder() {
		preOrder(root);
	}

	private void preOrder(BinarySearchNode<V> root) {
		if (root != null) {
			System.out.println(root.getKey());
			preOrder(root.getLeft());
			preOrder(root.getRight());
		}
	}

	// �������(������ȱ���)
	public void inOrder() {
		inOrder(root);
	}

	private void inOrder(BinarySearchNode<V> root) {
		if (root != null) {
			inOrder(root.getLeft());
			System.out.println(root.getKey());
			inOrder(root.getRight());
		}
	}

	// �������(������ȱ���)
	public void postOrder() {
		postOrder(root);
	}

	private void postOrder(BinarySearchNode<V> root) {
		if (root != null) {
			postOrder(root.getLeft());
			postOrder(root.getRight());
			System.out.println(root.getKey());
		}
	}

	// �������(������ȱ���)
	/*
	 * ˼·������������ʵ�� 1.���ڵ������ ѭ���ж϶����Ƿ�Ϊ�� ��Ϊ�գ� ȡ�����еĶ��ף��õ����׵�value �ж϶��׵����ӽڵ��Ƿ�Ϊ�գ� ��Ϊ�գ�
	 * ������� �ж϶��׵����ӽڵ��Ƿ�Ϊ�գ� ��Ϊ�գ� �������
	 */
	public void levelOrder() {
		Queue<BinarySearchNode<V>> q = new ArrayDeque<>();
		q.offer(root);
		while (!q.isEmpty()) {
			BinarySearchNode<V> temp = q.poll();
			if (temp.getLeft() != null) {
				q.offer(temp.getLeft());
			}
			if (temp.getRight() != null) {
				q.offer(temp.getRight());
			}
			System.out.println(temp.getValue());
		}
	}

	// ������Сֵ�ļ�
	public int getMinimum() {
		if (count != 0) {
			BinarySearchNode<V>node = minimum(root);//�Ӹ��ڵ㿪ʼ���²���
			return node.getKey();
		}
		return Integer.MIN_VALUE;
	}

	private BinarySearchNode<V> minimum(BinarySearchNode<V> root) {
		/*���root�ڵ������Ϊ��
		 * 		��˵��root�ڵ�Ϊ��ǰ������С�ڵ㣬ֱ�ӷ�����key�� 
		 */
		if (root.getLeft() == null) {
			return root;
		}
		/*���root�ڵ�����Ӳ�Ϊ��
		 * 		��˵��root�ڵ㲻�ǵ�ǰ������С�ڵ㣬���������Ӽ������²��� 
		 */
		return minimum(root.getLeft());
	}

	// �������ֵ�ļ�
	public int getMaxmum() {
		if (count != 0) {
			BinarySearchNode<V> node = maxmum(root);
			return node.getKey();
		}
		return Integer.MAX_VALUE;
	}

	private BinarySearchNode<V> maxmum(BinarySearchNode<V> root) {
		if (root.getRight() == null) {
			return root;
		}
		return maxmum(root.getRight());
	}

	// ɾ����С�ڵ�,�������µĸ��ڵ�
	public void removeMin() {
		if (root != null) {
			root = removeMin(root);
		}
	}
	// ɾ����С�ڵ�,�������µĸ��ڵ�
	private BinarySearchNode<V> removeMin(BinarySearchNode<V> root) {
		if (root.getLeft() == null) {//û�����ӣ�������root�ڵ�Ϊ��ǰ������С�ڵ�
			/*�ж�root��û���Һ���
			 * 		�У������Һ��ӵ�����
			 * 		û�У�����null��������������Ϊ��ʼ��Ϊnull
			 */
			BinarySearchNode<V> rightNode = root.getRight();
			count--;//ɾ���ýڵ�
			return rightNode;//�����Һ��ӽڵ㣬���Һ��ӽڵ㵱���µ��������ڵ�
		}
		/*�ݹ飺һֱ���ҵ�ǰ�������ӽڵ�
		 * �����ӣ�������root�ڵ㲻�ǵ�ǰ������С�ڵ㣬����������������
		 * ���ҵ������ӵĽڵ�ɾ������root�ڵ������ָ���������ӵ��Һ��ӽڵ�
		 */
		root.setLeft(removeMin(root.getLeft()));
		return root;//����ɾ��������������ĸ��ڵ�
	}

	// ɾ�����ڵ㣬�������µĸ��ڵ�
	public void removeMax() {
		if(root !=null) {
			root =removeMax(root);
		}
	}
	// ɾ�����ڵ㣬�������µĸ��ڵ�
	private BinarySearchNode<V> removeMax(BinarySearchNode<V> root) {
		if(root.getRight() == null) {
			BinarySearchNode<V> leftNode=root.getLeft();
			count--;
			return leftNode;
		}
		root.setRight(removeMax(root.getRight()));
		return root;
	}
	
	//ɾ������ڵ㣨����key�������������µĸ��ڵ�
	public void remove(int key) {
		root = remove(root,key);
	}

	private BinarySearchNode<V> remove(BinarySearchNode<V> root, int key) {
		if(root == null) {
			return null;
		}
		if(key > root.getKey()) {
			root.setRight(remove(root.getRight(),key));
			return root;
		}
		else if(key < root.getKey()) {
			root.setLeft(remove(root.getLeft(),key));
			return root;
		}
		else {//key == root.getKey()�ҵ��ýڵ�
			if(root.getLeft() == null) {
				BinarySearchNode<V> rightNode = root.getRight();
				count--;
				return rightNode;
			}
			if(root.getRight() == null) {
				BinarySearchNode<V> leftNode = root.getLeft();
				count--;
				return leftNode;
			}
			/*Hubbard Deletion����ɾ������������������һ�������Һ��ӽڵ�Ľڵ�
			 * ��ǰ�ڵ�--node    
			 * �ؼ����ҵ�node�ĺ�̽ڵ㣬����node��key����ĵ�һ���ڵ� successor
			 * sucessorһ����node���������е���С�ڵ�
			 * ��successor���node�ڵ�
			 * successor.leftnode->node.leftnode
			 * successor.rightnode->minimun(node.rightnode)
			 */
			//root.getRight() != null && root.getLeft() != null
			BinarySearchNode<V>successor =new BinarySearchNode<>(minimum(root.getRight()));
			count++;
			BinarySearchNode<V>node = removeMin(root.getRight()); 
			successor.setRight(node);
			successor.setLeft(root.getLeft());
			count--;
			return successor;
		}
	}
	
}
