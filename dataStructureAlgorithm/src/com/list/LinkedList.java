package com.list;
			/*		�������������ϵĶ�̬���ݽṹ��Ҳ����򵥵ģ�
			 * ���Խṹ���������ã�ָ�룩�����еݹ�ṹ����
			 * ���ݴ洢�ڽڵ㣨��-java����
			 * 		class Node{
			 * 			E item;//�洢����
			 * 			Node next;//����֮������ӵ�
			 * 		}
			 * ����ͬ���͵�Ԫ�أ�element���ļ�������ɵ����ݽṹ��
			 * ����ʹ�ò��������ڴ����洢�����ýڵ��next�����ã������ҵ���Ԫ�صĴ洢��ַ
			 * �ص㣺��Ӻ�ɾ�������ٶȿ죬�����Ķ�̬�ṹ������Ҫ�������������ݣ�������
			 * 		û��������ʵ����������Һ��޸Ĳ����ٶ���
			 * 		�����߱����õĿռ�ֲ��ԣ�������һ���ֲ����ṹ				
			 * ����������������������   ��������������������   ��������������������   �������������������� 
			 * |null|next|����>| 0 |next|����>| 1 |next|����>| 2 |next|����>null		
			 * ����������������������   ��������������������   ��������������������   ��������������������  
			 *   ^-dummyhead����ͷ��		   ^-item���� 		^-next����
			 * Ӧ�ã� 1.�����������ݽṹ��ջ������
			 * 
			 */
public class LinkedList<E> {
	//�ڲ��ࣺ����ڵ�
	private class Node {
		public E item;//����
		public Node next;//��һ���ڵ�
		//�вι��죬�ڵ�����ݣ��ڵ����һ���ڵ�
		public Node(E item, Node next) {
			this.item = item;
			this.next = next;
		}
		//�вι��죬�ڵ�����ݣ��ڵ����һ���ڵ�Ϊnull
		public Node(E item) {
			this(item, null);
		}
		//�޲ι��죬�ڵ�����ݣ��ڵ����һ���ڵ㶼Ϊ��
		public Node() {
			this(null);
		}
	}

	private Node dummyhead;//����ͷ�ڵ㣬ֻ������һ���ڵ㣬���������ݣ�null��
	private int size;//������Ԫ�ظ���
	//�޲ι��죺ֻ����һ������ͷ�ڵ�
	public LinkedList() {
		/*
		 * ʹ������ͷ�ڵ㣺������ͷԪ�ص������ԣ��Լ�����ɾ��ͷ�ڵ������������ 
		 * ����ͷ�ڵ㣺node.item=null,node.next->����ĵ�һ���ڵ�
		 */
		// head=null;
		dummyhead = new Node();
		size = 0;
	}
	//������Ԫ�صĸ���
	public int size() {
		return size;
	}
	//�ж������Ƿ�Ϊ��
	public boolean isEmpty() {
		return size == 0;
	}
	//������ͷ������½ڵ㣺ʱ�临�Ӷȣ�O��1�������������ϣ�����Ҫ��̯ʱ�临�Ӷȣ�
	public void addFirst(E item) {
		add(0, item);
	}
	/*
	 * �������е�index��λ������½ڵ㣺ʱ�临�Ӷȣ�O��n��
	 * ��0��λ�ã��������е�һ���ڵ��λ��
	 */
	public void add(int index, E item) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		/*
		 * ʹ������ͷ�ڵ㣬������ӽڵ�������� 
		 * if(index == 0) addFirst(item);
		 */
		//prev��Ҫ���λ�õ�ǰһ��λ�ã���ʼֵ�������һ���ڵ��ǰһ���ڵ㣨����ڵ㣩
		Node prev = dummyhead;
		for (int i = 0; i < index; i++) {
			prev = prev.next;
		}
		/*
		 * Node node=new Node(item); 
		 * node.next=pre.next; 
		 * pre.next=node;
		 */
		// ������һ�д������������3�д���
		prev.next = new Node(item, prev.next);
		size++;
	}
	//�������β������½ڵ㣺ʱ�临�Ӷȣ�O��n��
	public void addLast(E item) {
		add(size, item);
	}
	
	//��������ĺϷ���
	private void checkIndex(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
	}
	/*
	 * ��ȡ�����е�index��λ�ýڵ�����ݣ�ʱ�临�Ӷȣ�O��n��
	 * ��0��λ�ã��������е�һ���ڵ��λ��
	 */
	public E get(int index) {
		checkIndex(index);
		//cur:��ǰ�ڵ㣺��ʼֵΪ����ĵ�һ���ڵ�
		Node cur = dummyhead.next;
		for(int i=0;i<index;i++) {
			cur=cur.next;
		}
		return cur.item;
	}
	//��ȡ������ͷ�ڵ�����ݡ� ʱ�临�Ӷȣ�O��1��
	public E getFirst() {
		return get(0);
	}
	//��ȡ������β�ڵ�����ݡ� ʱ�临�Ӷȣ�O��n��
	public E getLast() {
		return get(size-1);
	}
	/*
	 * �޸������е�index��λ�ýڵ�����ݣ�ʱ�临�Ӷȣ�O��n��
	 * ��0��λ�ã��������е�һ���ڵ��λ��
	 */
	public void set(int index,E item) {
		checkIndex(index);
		//cur:��ǰ�ڵ㣺��ʼֵΪ����ĵ�һ���ڵ�
		Node cur = dummyhead.next;
		for(int i=0;i<index;i++) {
			cur=cur.next;
		}
		cur.item=item;
	}
	//�ж������Ƿ������Ԫ�أ�ʱ�临�Ӷȣ�O��n��
	public boolean contains(E item) {
		//cur:��ǰ�ڵ㣺��ʼֵΪ����ĵ�һ���ڵ�
		Node cur = dummyhead.next;
		while(cur != null) {
			if(item.equals(cur.item)) {
				return true;
			}
			cur=cur.next;
		}
		return false;
	}
	/*
	 * ɾ�������е�index��λ�ýڵ�����ݣ�ʱ�临�Ӷȣ�O��n��
	 * ��0��λ�ã��������е�һ���ڵ��λ��
	 */
	public E remove(int index) {
		if(isEmpty()) {
			throw new RuntimeException("Empty LinkedList"); 
		}
		checkIndex(index);
		//prev:��ǰ�ڵ��ǰһ���ڵ㣺��ʼֵΪ����ĵ�һ���ڵ��ǰһ���ڵ㣨����ͷ�ڵ㣩
		Node prev = dummyhead;
		for(int i=0;i<index;i++) {
			prev=prev.next;
		}
		Node delNode = prev.next;
		prev.next=delNode.next;
		delNode.next=null;
		size--;
		return delNode.item;
	}
	//ɾ��ͷ�ڵ㣬ʱ�临�Ӷȣ�O��1��
	public E removeFirst() {
		return remove(0);
	}
	//ɾ��β�ڵ㣺ʱ�临�Ӷȣ�O��n��
	public E removeLast() {
		return remove(size-1);
	}
	
	public void removeElement(E item) {
		Node prev = dummyhead;
		while(prev.next!=null) {
			if(item.equals(prev.next.item)) {
				break;
			}
			prev = prev.next;
		}
		if(prev.next != null) {
			Node delNode=prev.next;
			prev.next=delNode.next;
			delNode.next=null;
			size--;
		}
	}
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(String.format("LinkedList: size: %d\n", size));
		for(Node cur= dummyhead.next;cur !=null;cur=cur.next) {
			s.append(cur.item+"->");
		}
		s.append("NULL");
		return s.toString();
	}
}
