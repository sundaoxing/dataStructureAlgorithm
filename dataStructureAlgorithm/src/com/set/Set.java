package com.set;

public interface Set<E> {
	public int size();
	public boolean isEmpty();
	public void add(E item);
	public void remove(E item);
	public boolean contains(E item);
}
