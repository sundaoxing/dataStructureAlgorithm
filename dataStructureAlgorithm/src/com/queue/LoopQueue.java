package com.queue;
		/*		ѭ������
		 * 			^-front						frontָ�룺ָ����ж���
		 * 		   ������������������������������������������
		 *		   |   |   |   |   |   |			data
		 *         ������������������������������������������
		 *        	0	 1	 2	 3	 4			����
		 *          ^-tail						tailָ�룺ָ����ж�β����һ��λ��
		 *   ����У�tailָ��++��Ϊ��ѭ��ʹ�ã�tail�´��ƶ�����tail+1��% data.length��
		 *   �����У�frontָ��++��Ϊ��ѭ��ʹ�ã�front�´��ƶ�����front+1��%data.length��
		 *   �涨��
		 *   ����front==tail----------------------������Ϊ��
		 *   ����front==��tail+1��%data.length ----������Ϊ��
		 *   	������������%data.length����ֹ������û�г����в���ʱ������к�
		 *   front��=tail+1����tail+1��%data.length==front������������ʱ�����ִ���
		 *   
		 *   ���⣺
		 *   	�������˳����в����󣬶���Ϊ��ʱ�������һ������ռ䣬�޷�ʹ��
		 *   ��Ϊ�����Ƕ�����front==��tail+1��%data.length����������һ������ռ�
		 */
public class LoopQueue<E> implements Queue_Interface<E> {
	private Object[] data;//�����������洢��������
	private int front;//����ָ��
	private int tail;//��βָ��
	private int size;//�������ݸ���
	
	//�Զ����������вι���
	public LoopQueue(int capacity) {
		data = new Object[capacity + 1];//���ԣ�����+1����¶���û������������û����������
		front = tail = size = 0;
	}
	//Ĭ������Ϊ10���޲ι���
	public LoopQueue() {
		this(10);
	}
	//��ȡ��������
	public int getCapacity() {
		return data.length - 1;
	}
	//��ȡ������Ԫ�ظ���
	@Override
	public int size() {
		return size;
	}
	//�ж϶����Ƿ�Ϊ��
	@Override
	public boolean isEmpty() {
		return front == tail;
	}
	//�ж϶����Ƿ�Ϊ��
	public boolean isFull() {
		return front == (tail + 1)%data.length;
	}
	//����У�O��1��--��̯ʱ�临�Ӷȣ��������ݵ�O��n����
	@Override
	public void enqueue(E item) {
		if (isFull()) {
			resize(getCapacity() + (getCapacity() >> 1));
		}
		data[tail] = item;
		tail = (tail + 1) % data.length;//ȡ�࣬ѭ��ʹ�ö��пռ�
		size++;
	}
	/*���ݣ�O��n��---ע���¾���������Ҫһһ��Ӧ
	 *i		 0		 1			2			3			4	������	size
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

	//�����У�O��1��--��̯ʱ�临�Ӷ�
	@Override
	public E dequeue() {
		if(isEmpty()) {
			throw new RuntimeException("Empty queue");
		}
		E item = get(front);
		data[front]=null;
		front = (front + 1) % data.length;//ȡ�࣬ѭ��ʹ�ö��пռ�
		size--;
		return item;
	}
	//��ȡ���׵�Ԫ�أ�O��1��
	@Override
	public E getFront() {
		return get(front);
	}
	//����������ȡ�����е�Ԫ��
	@SuppressWarnings("unchecked")
	private E get(int index) {
		return (E) data[index];
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(String.format("LoopQueue: size=%d , capacity=%d\n",size,getCapacity()));
		s.append("front[");
		/*	�����������±꣺��ʵ���Ƕ��˶�data����ĳ���ȡ��
		 * front -----------------tail
		 * ÿ��������(i+1)%data.length
		 * ��ֹ������(i+1)%data.length != tail
		 */
		for(int i =front;i != tail;i=(i+1)%data.length) {
			s.append(data[i]);
			if((i+1)%data.length != tail) {//��=tail���϶��������һ��Ԫ��
				s.append(", ");
			}
		}
		s.append("]tail");
		return s.toString();
	}
}
