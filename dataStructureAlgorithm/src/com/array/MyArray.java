package com.array;

import java.util.Arrays;
		/*			动态数组
		 * 线性结构
		 * 由相同类型的元素（element）的集合所组成的数据结构，
		 * 分配一块连续的内存来存储。利用元素的索引（index）可以计算出该元素对应的存储地址
		 * 特点：随机访问（查找和修改）操作速度快
		 * 	        增加和删除（非尾部）操作速度较慢
		 *  ——————————————————
		 *  。。  | 1 | 2 | 3 | 。。。。数组
		 *  ——————————————————
		 *    0	  1   2	  3  ->索引
		 * 应用： 1.数学向量，矩阵
		 * 		2.数据库表
		 * 		3.其他数据结构：列表List，堆，散列表，双向队列，队列，循环队列，堆栈，字符串
		 * java中数组特性：
		 * 		1.数组是一个引用数据类型，数组的变量只是一个引用，数组元素和数组变量在内存里是分开存放的
		 * 		2.Java的数组要求所有的数组元素具有相同的数据类型。
		 * 		3.二维数组是一维数组，其数组元素是一维数组-》java中没有真正意义上的多维数组
		 */
public class MyArray<E> {
	private Object [] data;//数据容器：存储引用类型
	private int size;//数据个数
	//默认容量为10的无参构造
	public MyArray() {
		this(10);
	}
	//自定义容量的有参构造
	public MyArray(int capacity) {
		data=new Object [capacity];
		size=0;
	}
	//使用传入的数组替代底层数组
	public MyArray(E[] arr) {
		data = new Object[arr.length];
		for(int i=0;i<arr.length;i++) {
			data[i]=arr[i];
		}
		size=arr.length;
	}
	//返回数据的个数
	public int size() {
		return size;
	}
	//判断数组是否为空
	public boolean isEmpty() {
		return size==0;
	}
	//添加元素，向数组尾部添加，O(1)-均摊复杂度（包含扩容O（n））
	public void addLast(E item) {
		add(size,item);
	}
	//添加元素，向数组头部添加，O(n)
	public void addFirst(E item) {
		add(0,item);
	}
	//添加元素，向数组指定索引出添加元素，O(n/2)-平均
	public void add(int index,E item) {
		checkIndex(index);
		if(size == data.length) {
			resize(data.length+(data.length >> 1));
		}
		for(int i=size-1;i>=index;i--) {
			data[i+1]=data[i];
		}
		data[index]=item;
		size++;
	}
	//检查数组索引是否合法
	private void checkIndex(int index) {
		if(index<0 || index >size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
	}
	//扩容，新的容量：data.length+(data.length >> 1)，<O(n)-底层使用JVM优化
	private void resize(int newCapacity) {
		data=Arrays.copyOf(data, newCapacity);
	}
	//获取元素，获取指定索引位置上的元素，O(1)
	@SuppressWarnings("unchecked")
	public E get(int index) {
		checkIndex(index);
		return (E) data[index];
	}
	
	public E getLast() {
		return get(size-1);
	}
	public E getFirst() {
		return get(0);
	}
	//修改元素，修改指定索引位置上的元素，O(1)
	public void set(int index,E item) {
		checkIndex(index);
		data[index]=item;
	}
	//删除元素，删除指定索引位置上的元素，O(n)
	public E remove(int index) {
		if(index<0 || index >=size) {
			throw new ArrayIndexOutOfBoundsException(index);
		}
		@SuppressWarnings("unchecked")
		E item=(E) data[index];
		for(int i = index+1;i<size;i++) {
			data[i-1]=data[i];//这种方式，循环次数减少一次
		}
		/*这种方式，循环次数会比上面多一次一次
		 * for(int i=index;i<size;i++) {
			data[i]=data[i+1];
		}*/
		size--;//相对于用户来说，只能看到或者取到0-size-1范围内的值
		data[size]=null;//显示赋值为null，让gc回收
		/*缩小容量
		 * if(size < data.length/4 && data.length/2 !=0) {
		 *resize(data.length);
		}*/
		return item;
	}
	//删除数组中第一个元素，O(n)
	public E removeFirst() {
		return remove(0);
	}
	//删除数组中最后一个元素，O(1)
	public E removeLast() {
		return remove(size-1);
	}
	//判断数组是否包含该元素，O(n)
	public boolean contain(E item) {
		for(int i=0;i<size;i++) {
			if(item.equals(data[i])) {//避免了空指针异常
				return true;
			}
		}
		return false;
	}
	//查找该元素在数组中存储的索引，O(n)
	public int findIndex(E item) {
		for(int i=0;i<size;i++) {
			if(item.equals(data[i])) {//避免了空指针异常
				return i;
			}
		}
		return -1;
	}
	//删除元素，根据元素删除数组中该元素，O(n)+O(n)=O(n)
	public void removeElement(E item) {
		int index = findIndex(item);
		if(index != -1) {
			remove(index);
		}
	}
	
	public int getCapacity() {
		return data.length;
	}
	
	public void swap(int i,int j) {
		Object t = data[i];
		data[i]=data[j];
		data[j]=t;
	}
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(String.format("Array: size=%d , capacity=%d\n", size,data.length));
		s.append("[");
		for(int i =0;i<size;i++) {
			s.append(data[i]);
			if(i != size-1) {
				s.append(", ");
			}
		}
		s.append("]");
		return s.toString();
	}
}
