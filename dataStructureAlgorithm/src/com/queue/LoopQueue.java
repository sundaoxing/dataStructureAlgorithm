package com.queue;
		/*		循环队列
		 * 			^-front						front指针：指向队列队首
		 * 		   —————————————————————
		 *		   |   |   |   |   |   |			data
		 *         —————————————————————
		 *        	0	 1	 2	 3	 4			索引
		 *          ^-tail						tail指针：指向队列队尾的下一个位置
		 *   入队列：tail指针++（为了循环使用：tail下次移动：（tail+1）% data.length）
		 *   出队列：front指针++（为了循环使用：front下次移动：（front+1）%data.length）
		 *   规定：
		 *   当：front==tail----------------------》队列为空
		 *   当：front==（tail+1）%data.length ----》队列为满
		 *   	在这里，必须加上%data.length，防止，队列没有出队列操作时，入队列后
		 *   front！=tail+1而（tail+1）%data.length==front，导致在判满时，出现错误
		 *   
		 *   问题：
		 *   	当进行了出队列操作后，队列为满时，会出现一个数组空间，无法使用
		 *   因为：我们定义了front==（tail+1）%data.length，会牺牲掉一个数组空间
		 */
public class LoopQueue<E> implements Queue_Interface<E> {
	private Object[] data;//数据容器：存储引用类型
	private int front;//队首指针
	private int tail;//队尾指针
	private int size;//队列数据个数
	
	//自定义容量的有参构造
	public LoopQueue(int capacity) {
		data = new Object[capacity + 1];//所以，这里+1，暴露给用户的容量才是用户传入的容量
		front = tail = size = 0;
	}
	//默认容量为10的无参构造
	public LoopQueue() {
		this(10);
	}
	//获取队列容量
	public int getCapacity() {
		return data.length - 1;
	}
	//获取队列中元素个数
	@Override
	public int size() {
		return size;
	}
	//判断队列是否为空
	@Override
	public boolean isEmpty() {
		return front == tail;
	}
	//判断队列是否为满
	public boolean isFull() {
		return front == (tail + 1)%data.length;
	}
	//入队列：O（1）--均摊时间复杂度（包含扩容的O（n））
	@Override
	public void enqueue(E item) {
		if (isFull()) {
			resize(getCapacity() + (getCapacity() >> 1));
		}
		data[tail] = item;
		tail = (tail + 1) % data.length;//取余，循环使用队列空间
		size++;
	}
	/*扩容：O（n）---注意新旧数组索引要一一对应
	 *i		 0		 1			2			3			4	。。。	size
	 *front  front	 front+1 	front+2 	front+3 	front+4  ...%data.length
	 */
	private void resize(int capacity) {
		Object [] newdata=new Object[capacity+1];
		for(int i=0;i<size;i++) {
			newdata[i]=data[(i+front)%data.length];
		}
		data=newdata;
		front=0;
		tail=size;
	}

	//出队列：O（1）--均摊时间复杂度
	@Override
	public E dequeue() {
		if(isEmpty()) {
			throw new RuntimeException("Empty queue");
		}
		E item = get(front);
		data[front]=null;
		front = (front + 1) % data.length;//取余，循环使用队列空间
		size--;
		return item;
	}
	//获取队首的元素：O（1）
	@Override
	public E getFront() {
		return get(front);
	}
	//根据索引获取队列中的元素
	@SuppressWarnings("unchecked")
	private E get(int index) {
		return (E) data[index];
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(String.format("LoopQueue: size=%d , capacity=%d\n",size,getCapacity()));
		s.append("front[");
		/*	遍历：索引下标：其实就是多了对data数组的长度取余
		 * front -----------------tail
		 * 每次增量：(i+1)%data.length
		 * 终止条件：(i+1)%data.length != tail
		 */
		for(int i =front;i != tail;i=(i+1)%data.length) {
			s.append(data[i]);
			if((i+1)%data.length != tail) {//！=tail，肯定不是最后一个元素
				s.append(", ");
			}
		}
		s.append("]tail");
		return s.toString();
	}
}
