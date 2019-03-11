package com.stack;
	/*
	 * ʹ������ջ��������
	 * Ҫ��ʵ�ֶ��е�add��poll��peek����
	 */

import java.util.Stack;
			/*		˼��
			 * ���У��Ƚ��ȳ�
			 * ջ���Ƚ����
			 * ����ջ��ջa��������ȵ�����ʱ�򣬰�ջaȫpop��ջb�У�ջb�����
			 */
public class SimulationQueue<E> {
	protected Stack<E> s_push;//��
	protected Stack<E> s_pop;//��
	
	public SimulationQueue() {
		s_push=new Stack<>();
		s_pop = new Stack<>();
	}
	
	//�����
	public E add(E item) {
		s_push.push(item);
		return item;
	}
	
	//������
	public E poll() {
		if(s_push.isEmpty()) {
			throw new RuntimeException("Empty Queue");
		}
		/*		ע���
		 * ջbΪ��ʱ���Ű�ջa��Ԫ��pop��zhanb��Ȼ���ڳ�ջ
		 * ջb��Ϊ�գ�����ջb��ջ
		 */
		if(s_pop.isEmpty()) {
			while(!s_push.isEmpty()) {
				s_pop.push(s_push.pop());
			}
		}
		return s_pop.pop();
	}
	
	public E peek() {
		/*		ע���
		 * ջbΪ��ʱ���Ű�ջa��Ԫ��pop��ջb��Ȼ����ָ��ջbջ��
		 * ջb��Ϊ�գ�����ָ��ջbջ��
		 */
		if(s_pop.isEmpty()) {
			while(!s_push.isEmpty()) {
				s_pop.push(s_push.pop());
			}
		}
		return s_pop.peek();
	}
}
