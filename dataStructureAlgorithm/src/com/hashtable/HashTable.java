package com.hashtable;

import java.util.TreeMap;
/*
 * 				哈希表:(HashTable)
 * 		动态数组形式的hash表：时间复杂度：均摊：O（1）：
 * 				牺牲了顺序性（元素无序）
 * 		本质：数据（键） ---------映射---------数字（索引）（人后存储到数组中）
 * 	难点：映射函数（hash函数）的选择  ； hash冲突的解决
 * 		算法经典思想：空间换时间，折中思想
 * 		hash函数的设计：转成大整数，然后在模一个素数
 * 			原则： 1.一致性：a==b 则 hash（a）==hash（b）（hash值相等）
 * 				 2.高效性：hash函数计算简单高效
 * 				 3.均匀性：经过映射后的hash值要均匀分布
 * 
 * 		java中hahsCode（）方法：
 * 			同一对象上多次调用 hashCode 方法时，必须一致地返回相同的整数。
 * 			前提是对象上 equals 比较中所用的信息没有被修改。
 *   		但是从某一应用程序的一次执行到同一应用程序的另一次执行，该整数无需保持一致。 
 *   		数值*权重+偏置（线性变换）
 *   
 *   	hash冲突的解决：
 *   1.链地址法：
 *   		思想：相同的hash值，放在数组的同一个位置上，采用链表或者树结构组合在一起
 *   		本质：数组中存放的是就是链表的对象引用/树的对象引用
 *  数组索引----0[  ]-----------------------链表（树）
 *  		  1[引用]---->K1---->K2---->K3
 *   		  2[  ]
 *   		  3[引用]---->K4---->K5
 *      java1.8前，使用链表
 *      java1.8后，当hash冲突达到一定程度，自动转为红黑树
 *    
 *   2.开放地址法：（线性探测法，平方探测法）
 *   3.再次hash法：rehashing
 *   4.coalessced hashing：结合了链地址法和开放地址法
 * 
 */
public class HashTable<K,V> {
	/*
	 * hash表底层结构：TreeMap类型的数组
	 *       BUG：HashTable<K,V>中K是任意类型，
	 *       而底层实现TreeMap<K,V>中，K是要继承Comparable接口
	 *       产生矛盾，使K的范围要缩小
	 */
	private TreeMap<K, V>[] hashTable;
	private int M;//数组长度（素数）
	private int size;//HashTable中元素的个数
	private int capacityIndex;//容量数组的索引
	private static double upperTol =0.75;//元素个数/hash表的长度（负载因子）
	
	//容量数组：（大素数）尽量保证元素的均匀分布
	private final int[] capacity= {53,97,193,389,769,1543,3079,6151,12289,24593,
	49157,98317,196613,393241,786433,1572869,3145739,6291469,12582917,25165843,
	50331653,100663319,201326611,402653189,805306457,1610612741};
	
	@SuppressWarnings("unchecked")
	public HashTable() {
		capacityIndex=0;//初始容量为容量数组的第一个元素
		this.M=capacity[capacityIndex];
		hashTable = new TreeMap[M];
		for(int i=0;i<M;i++) {
			hashTable[i]=new TreeMap<>();//初始化，数组中每个位置都是一颗TreeMap树
		}
		size=0;
	}
	//返回HashTable中元素的个数
	public int size() {
		return size;
	}
	//判断hashTable是否为空
	public boolean isEmpty() {
		return size==0;
	}
	//返回键key对应的hash值（在数组中的索引位置）：时间复杂度：O（1）
	public int hash(K key) {
		//& 0x7fffffff:表示按位与，去除符号位
		return (key.hashCode() & 0x7fffffff) % M;
	}
	
	/*
	 * 	添加元素：均摊时间复杂度：O（1）
	 * 	   思路： 1.先计算键key对应的hash值（数组的索引）
	 * 		  2.根据hash值获取数组中对应位置（索引）的TreeMap树的引用
	 * 		  3.判断键key是否在树中
	 * 				是：表示树中已有键key，直接更新value值即可
	 * 				否：把（key，value）存储到树中
	 */
	public void add(K key,V value) {
		TreeMap<K, V> map = get(hash(key));
		if(map.containsKey(key)) {
			map.put(key, value);
		}
		else {
			map.put(key, value);
			size++;
			/*
			 * 判断负载因子是否大于0.75
			 * 		大于：进行扩容操作（注意容量数组的边界0-capacity.length）
			 */
			if(size > M * upperTol  && capacityIndex+1 < capacity.length) {
				capacityIndex++;
				resize(capacity[capacityIndex]);
			}
		}
	}
	/*
	 * 扩容：实现hash表的动态增长：----时间复杂度：O（n）
	 * 		思路：静态数组扩容思路
	 * 			1.创建一个新的容量的hash表，并初始化
	 * 			2.遍历旧的hash表，获取每一个数组位置的TreeMap引用
	 * 			3.对每一个TreeMap中所有键key重新计算hash值
	 * 			4.然后根据新的hash值，将key，value放入新的hash表
	 */
	private void resize(int newCapacity) {
		@SuppressWarnings("unchecked")
		TreeMap<K,V> []newHashTable= new TreeMap[newCapacity];
		for(int i=0;i<newHashTable.length;i++) {
			newHashTable[i]=new TreeMap<>();
		}
		/*
		 * 注意：
		 * 		1.hash（）方法的计算是依据M（hash表的容量），新旧hash表的容量不同
		 * 		2.把旧的hash表中TreeMap引用显示赋为null，让gc来回收
		 * 		3.重新指向新的hash表
		 */
		int oldM=M;
		this.M=newCapacity;
		for(int i=0;i<oldM;i++) {
			TreeMap<K, V> map=hashTable[i];
			for(K key : map.keySet()) {
				newHashTable[hash(key)].put(key, map.get(key));
			}
			map=null;
		}
		this.hashTable=newHashTable;
		
	}
	/*
	 *	删除键key代表的节点---------：时间复杂度：O（1）
	 *		思路： 1.先计算键key对应的hash值（数组的索引）
	 * 		 	 2.根据hash值获取数组中对应位置（索引）的TreeMap树的引用
	 * 		 	 3.判断键key是否在树中
	 * 				是：表示树中已有键key，直接删除键key代表的节点
	 * 			 4.返回节点的value值
	 */
	public V remove(K key) {
		TreeMap<K, V> map = get(hash(key));
		V oldValue = null;
		if(map.containsKey(key)) {
			oldValue = map.remove(key);
			size--;
		}
		return oldValue;
	}
	
	/*
	 *  更新键key对应的节点的value：----时间复杂度：O（1）
	 *  	思路： 1.先计算键key对应的hash值（数组的索引）
	 * 		  	 2.根据hash值获取数组中对应位置（索引）的TreeMap树的引用
	 * 		 	 3.判断键key是否在树中
	 * 				是：表示树中已有键key，直接更新value值即可
	 * 				否：抛出异常，没有找到对应键key的节点
	 */
	public void set(K key ,V newValue) {
		TreeMap<K, V> map = get(hash(key));
		if(!map.containsKey(key)) {
			throw new RuntimeException("key doesn't exist");
		}
		map.put(key, newValue);
	}
	//判断hashTable中是否包含键key：时间复杂度：O（1）
	public boolean contains(K key) {
		return get(hash(key)).containsKey(key);
	}
	//根据键key返回对应的value值：时间复杂度：O（1）
	public V get(K key) {
		return get(hash(key)).get(key);
	}
	//根据hash值返回数组中对应索引处的TreeMap树的引用：时间复杂度：O（1）
	private TreeMap<K,V> get(int hash) {
		return hashTable[hash];
	}
}
