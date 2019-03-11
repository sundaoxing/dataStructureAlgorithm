package com.queue;

public interface Queue_Interface<E> {
	public int size();
	public boolean isEmpty();
	public void enqueue(E item);
	public E dequeue();
	public E getFront();
}
