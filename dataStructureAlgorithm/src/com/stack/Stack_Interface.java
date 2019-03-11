package com.stack;

public interface Stack_Interface<E> {
	public int size();
	public boolean isEmpty();
	public void push(E item);
	public E pop();
	public E peek();
}
