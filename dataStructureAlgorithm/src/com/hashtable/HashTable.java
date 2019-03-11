package com.hashtable;

import java.util.TreeMap;
/*
 * 				��ϣ��:(HashTable)
 * 		��̬������ʽ��hash��ʱ�临�Ӷȣ���̯��O��1����
 * 				������˳���ԣ�Ԫ������
 * 		���ʣ����ݣ����� ---------ӳ��---------���֣����������˺�洢�������У�
 * 	�ѵ㣺ӳ�亯����hash��������ѡ��  �� hash��ͻ�Ľ��
 * 		�㷨����˼�룺�ռ任ʱ�䣬����˼��
 * 		hash��������ƣ�ת�ɴ�������Ȼ����ģһ������
 * 			ԭ�� 1.һ���ԣ�a==b �� hash��a��==hash��b����hashֵ��ȣ�
 * 				 2.��Ч�ԣ�hash��������򵥸�Ч
 * 				 3.�����ԣ�����ӳ����hashֵҪ���ȷֲ�
 * 
 * 		java��hahsCode����������
 * 			ͬһ�����϶�ε��� hashCode ����ʱ������һ�µط�����ͬ��������
 * 			ǰ���Ƕ����� equals �Ƚ������õ���Ϣû�б��޸ġ�
 *   		���Ǵ�ĳһӦ�ó����һ��ִ�е�ͬһӦ�ó������һ��ִ�У����������豣��һ�¡� 
 *   		��ֵ*Ȩ��+ƫ�ã����Ա任��
 *   
 *   	hash��ͻ�Ľ����
 *   1.����ַ����
 *   		˼�룺��ͬ��hashֵ�����������ͬһ��λ���ϣ���������������ṹ�����һ��
 *   		���ʣ������д�ŵ��Ǿ�������Ķ�������/���Ķ�������
 *  ��������----0[  ]-----------------------��������
 *  		  1[����]---->K1---->K2---->K3
 *   		  2[  ]
 *   		  3[����]---->K4---->K5
 *      java1.8ǰ��ʹ������
 *      java1.8�󣬵�hash��ͻ�ﵽһ���̶ȣ��Զ�תΪ�����
 *    
 *   2.���ŵ�ַ����������̽�ⷨ��ƽ��̽�ⷨ��
 *   3.�ٴ�hash����rehashing
 *   4.coalessced hashing�����������ַ���Ϳ��ŵ�ַ��
 * 
 */
public class HashTable<K,V> {
	/*
	 * hash��ײ�ṹ��TreeMap���͵�����
	 *       BUG��HashTable<K,V>��K���������ͣ�
	 *       ���ײ�ʵ��TreeMap<K,V>�У�K��Ҫ�̳�Comparable�ӿ�
	 *       ����ì�ܣ�ʹK�ķ�ΧҪ��С
	 */
	private TreeMap<K, V>[] hashTable;
	private int M;//���鳤�ȣ�������
	private int size;//HashTable��Ԫ�صĸ���
	private int capacityIndex;//�������������
	private static double upperTol =0.75;//Ԫ�ظ���/hash��ĳ��ȣ��������ӣ�
	
	//�������飺����������������֤Ԫ�صľ��ȷֲ�
	private final int[] capacity= {53,97,193,389,769,1543,3079,6151,12289,24593,
	49157,98317,196613,393241,786433,1572869,3145739,6291469,12582917,25165843,
	50331653,100663319,201326611,402653189,805306457,1610612741};
	
	@SuppressWarnings("unchecked")
	public HashTable() {
		capacityIndex=0;//��ʼ����Ϊ��������ĵ�һ��Ԫ��
		this.M=capacity[capacityIndex];
		hashTable = new TreeMap[M];
		for(int i=0;i<M;i++) {
			hashTable[i]=new TreeMap<>();//��ʼ����������ÿ��λ�ö���һ��TreeMap��
		}
		size=0;
	}
	//����HashTable��Ԫ�صĸ���
	public int size() {
		return size;
	}
	//�ж�hashTable�Ƿ�Ϊ��
	public boolean isEmpty() {
		return size==0;
	}
	//���ؼ�key��Ӧ��hashֵ���������е�����λ�ã���ʱ�临�Ӷȣ�O��1��
	public int hash(K key) {
		//& 0x7fffffff:��ʾ��λ�룬ȥ������λ
		return (key.hashCode() & 0x7fffffff) % M;
	}
	
	/*
	 * 	���Ԫ�أ���̯ʱ�临�Ӷȣ�O��1��
	 * 	   ˼·�� 1.�ȼ����key��Ӧ��hashֵ�������������
	 * 		  2.����hashֵ��ȡ�����ж�Ӧλ�ã���������TreeMap��������
	 * 		  3.�жϼ�key�Ƿ�������
	 * 				�ǣ���ʾ�������м�key��ֱ�Ӹ���valueֵ����
	 * 				�񣺰ѣ�key��value���洢������
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
			 * �жϸ��������Ƿ����0.75
			 * 		���ڣ��������ݲ�����ע����������ı߽�0-capacity.length��
			 */
			if(size > M * upperTol  && capacityIndex+1 < capacity.length) {
				capacityIndex++;
				resize(capacity[capacityIndex]);
			}
		}
	}
	/*
	 * ���ݣ�ʵ��hash��Ķ�̬������----ʱ�临�Ӷȣ�O��n��
	 * 		˼·����̬��������˼·
	 * 			1.����һ���µ�������hash������ʼ��
	 * 			2.�����ɵ�hash����ȡÿһ������λ�õ�TreeMap����
	 * 			3.��ÿһ��TreeMap�����м�key���¼���hashֵ
	 * 			4.Ȼ������µ�hashֵ����key��value�����µ�hash��
	 */
	private void resize(int newCapacity) {
		@SuppressWarnings("unchecked")
		TreeMap<K,V> []newHashTable= new TreeMap[newCapacity];
		for(int i=0;i<newHashTable.length;i++) {
			newHashTable[i]=new TreeMap<>();
		}
		/*
		 * ע�⣺
		 * 		1.hash���������ļ���������M��hash������������¾�hash���������ͬ
		 * 		2.�Ѿɵ�hash����TreeMap������ʾ��Ϊnull����gc������
		 * 		3.����ָ���µ�hash��
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
	 *	ɾ����key����Ľڵ�---------��ʱ�临�Ӷȣ�O��1��
	 *		˼·�� 1.�ȼ����key��Ӧ��hashֵ�������������
	 * 		 	 2.����hashֵ��ȡ�����ж�Ӧλ�ã���������TreeMap��������
	 * 		 	 3.�жϼ�key�Ƿ�������
	 * 				�ǣ���ʾ�������м�key��ֱ��ɾ����key����Ľڵ�
	 * 			 4.���ؽڵ��valueֵ
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
	 *  ���¼�key��Ӧ�Ľڵ��value��----ʱ�临�Ӷȣ�O��1��
	 *  	˼·�� 1.�ȼ����key��Ӧ��hashֵ�������������
	 * 		  	 2.����hashֵ��ȡ�����ж�Ӧλ�ã���������TreeMap��������
	 * 		 	 3.�жϼ�key�Ƿ�������
	 * 				�ǣ���ʾ�������м�key��ֱ�Ӹ���valueֵ����
	 * 				���׳��쳣��û���ҵ���Ӧ��key�Ľڵ�
	 */
	public void set(K key ,V newValue) {
		TreeMap<K, V> map = get(hash(key));
		if(!map.containsKey(key)) {
			throw new RuntimeException("key doesn't exist");
		}
		map.put(key, newValue);
	}
	//�ж�hashTable���Ƿ������key��ʱ�临�Ӷȣ�O��1��
	public boolean contains(K key) {
		return get(hash(key)).containsKey(key);
	}
	//���ݼ�key���ض�Ӧ��valueֵ��ʱ�临�Ӷȣ�O��1��
	public V get(K key) {
		return get(hash(key)).get(key);
	}
	//����hashֵ���������ж�Ӧ��������TreeMap�������ã�ʱ�临�Ӷȣ�O��1��
	private TreeMap<K,V> get(int hash) {
		return hashTable[hash];
	}
}
