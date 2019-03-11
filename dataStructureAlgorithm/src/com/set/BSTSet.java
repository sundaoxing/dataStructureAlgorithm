package com.set;
import com.binarySearch.BST;

/*
 *  			集合----set
 *  	特点：
 *  		1.不包含重复元素
 *  		2.底层数据结构：二叉搜索树（动态数据结构）
 *  		3.Set判断两个对象相同不是使用"=="运算符，而是根据equals方法
 *  		4.Set集合里的多个对象之间没有明显的顺序（取出顺序不是按照添加时的顺序）
 *  	应用：客户量统计
 *  		词频统计
 */
public class BSTSet<E extends Comparable<E>> implements Set<E> {
	private BST<E> s;//底层实现结构-二叉搜索树
	
	public BSTSet() {
		s=new BST<>();
	}
	//返回set结合的元素个数
 	@Override
	public int size() {
		return s.size();
	}
 	//判断set结合是否为空
	@Override
	public boolean isEmpty() {
		return s.isEmpty();
	}
	//向set集合中添加元素---时间复杂度：O（log（n））平均
	@Override
	public void add(E item) {
		s.add(item);//不能添加重复元素，因为这里没有对key相等的元素进行处理
	}
	//在set集合中删除元素---时间复杂度：O（log（n））平均
	@Override
	public void remove(E item) {
		s.remove(item);
	}
	//判断set集合是否包含该元素---时间复杂度：O（log（n））平均
	@Override
	public boolean contains(E item) {
		return s.contain(item);
	}

}
