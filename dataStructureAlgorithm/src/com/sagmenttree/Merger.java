package com.sagmenttree;
/*
 * 			融合器：	
 * 		用户自定义：线段树合并逻辑
 */
public interface Merger<E> {
	public E merger(E a,E b);
}
