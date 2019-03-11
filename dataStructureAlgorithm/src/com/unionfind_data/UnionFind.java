package com.unionfind_data;

public interface UnionFind {
	public int size();
	public boolean isConnected(int p,int q);
	public void unionElements(int p,int q);
}
