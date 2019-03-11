package com.list;
			/*		链表（真正意义上的动态数据结构，也是最简单的）
			 * 线性结构：基于引用（指针），具有递归结构性质
			 * 数据存储在节点（类-java）中
			 * 		class Node{
			 * 			E item;//存储数据
			 * 			Node next;//数据之间的连接点
			 * 		}
			 * 由相同类型的元素（element）的集合所组成的数据结构，
			 * 可以使用不连续的内存来存储。利用节点的next（引用）可以找到该元素的存储地址
			 * 特点：添加和删除操作速度快，真正的动态结构，不需要考虑容量（扩容）的问题
			 * 		没有随机访问的能力：查找和修改操作速度慢
			 * 		链表不具备良好的空间局部性，但它是一个局部化结构				
			 * ———————————   ——————————   ——————————   —————————— 
			 * |null|next|——>| 0 |next|——>| 1 |next|——>| 2 |next|——>null		
			 * ———————————   ——————————   ——————————   ——————————  
			 *   ^-dummyhead虚拟头部		   ^-item数据 		^-next引用
			 * 应用： 1.构建其他数据结构堆栈，队列
			 * 
			 */
public class LinkedList<E> {
	//内部类：链表节点
	private class Node {
		public E item;//数据
		public Node next;//下一个节点
		//有参构造，节点的数据，节点的下一个节点
		public Node(E item, Node next) {
			this.item = item;
			this.next = next;
		}
		//有参构造，节点的数据，节点的下一个节点为null
		public Node(E item) {
			this(item, null);
		}
		//无参构造，节点的数据，节点的下一个节点都为空
		public Node() {
			this(null);
		}
	}

	private Node dummyhead;//虚拟头节点，只保存下一个节点，不保存数据（null）
	private int size;//链表中元素个数
	//无参构造：只生成一个虚拟头节点
	public LinkedList() {
		/*
		 * 使用虚拟头节点：解决添加头元素的特殊性，以及后续删除头节点操作的特殊性 
		 * 虚拟头节点：node.item=null,node.next->链表的第一个节点
		 */
		// head=null;
		dummyhead = new Node();
		size = 0;
	}
	//链表中元素的个数
	public int size() {
		return size;
	}
	//判断链表是否为空
	public boolean isEmpty() {
		return size == 0;
	}
	//在链表头部添加新节点：时间复杂度：O（1）（真正意义上，不需要均摊时间复杂度）
	public void addFirst(E item) {
		add(0, item);
	}
	/*
	 * 向链表中第index个位置添加新节点：时间复杂度：O（n）
	 * 第0个位置：是链表中第一个节点的位置
	 */
	public void add(int index, E item) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		/*
		 * 使用虚拟头节点，屏蔽添加节点的特殊性 
		 * if(index == 0) addFirst(item);
		 */
		//prev：要添加位置的前一个位置，初始值：链表第一个节点的前一个节点（虚拟节点）
		Node prev = dummyhead;
		for (int i = 0; i < index; i++) {
			prev = prev.next;
		}
		/*
		 * Node node=new Node(item); 
		 * node.next=pre.next; 
		 * pre.next=node;
		 */
		// 下面这一行代码替代了上面3行代码
		prev.next = new Node(item, prev.next);
		size++;
	}
	//向链表的尾部添加新节点：时间复杂度：O（n）
	public void addLast(E item) {
		add(size, item);
	}
	
	//检查索引的合法性
	private void checkIndex(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
	}
	/*
	 * 获取链表中第index个位置节点的数据：时间复杂度：O（n）
	 * 第0个位置：是链表中第一个节点的位置
	 */
	public E get(int index) {
		checkIndex(index);
		//cur:当前节点：初始值为链表的第一个节点
		Node cur = dummyhead.next;
		for(int i=0;i<index;i++) {
			cur=cur.next;
		}
		return cur.item;
	}
	//获取链表中头节点的数据。 时间复杂度：O（1）
	public E getFirst() {
		return get(0);
	}
	//获取链表中尾节点的数据。 时间复杂度：O（n）
	public E getLast() {
		return get(size-1);
	}
	/*
	 * 修改链表中第index个位置节点的数据：时间复杂度：O（n）
	 * 第0个位置：是链表中第一个节点的位置
	 */
	public void set(int index,E item) {
		checkIndex(index);
		//cur:当前节点：初始值为链表的第一个节点
		Node cur = dummyhead.next;
		for(int i=0;i<index;i++) {
			cur=cur.next;
		}
		cur.item=item;
	}
	//判断链表是否包含该元素，时间复杂度：O（n）
	public boolean contains(E item) {
		//cur:当前节点：初始值为链表的第一个节点
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
	 * 删除链表中第index个位置节点的数据：时间复杂度：O（n）
	 * 第0个位置：是链表中第一个节点的位置
	 */
	public E remove(int index) {
		if(isEmpty()) {
			throw new RuntimeException("Empty LinkedList"); 
		}
		checkIndex(index);
		//prev:当前节点的前一个节点：初始值为链表的第一个节点的前一个节点（虚拟头节点）
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
	//删除头节点，时间复杂度：O（1）
	public E removeFirst() {
		return remove(0);
	}
	//删除尾节点：时间复杂度：O（n）
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
