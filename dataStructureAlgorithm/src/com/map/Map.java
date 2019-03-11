package com.map;

public interface Map<K,V> {
	public int size();
	public boolean isEmpty();
	public boolean contains(K key);
	public void add(K key , V value);
	public V get(K key);
	public void set(K key,V newValue);
	public V remove(K key);
}
