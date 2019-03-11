package com.hashtable;

import java.util.ArrayList;

import com.set.FileOperation;

public class Test {
	@org.junit.Test
	public void test1() {
		String filename ="src/com/set/Pride-and-Prejudice.txt";
		HashTable<String, Integer>map = new HashTable<>();
		double time1 = testMap(map, filename);
		System.out.println("time : " + time1+" s");
	}
	
	public double testMap(HashTable<String, Integer> map,String filename) {
		long start = 0;
		System.out.println("Pride-and-Prejudice");
		ArrayList<String> a = new ArrayList<>();
		if(FileOperation.readFile(filename, a)) {
			start = System.nanoTime();
			for(String word : a) {
				if(map.contains(word)) {
					map.set(word, map.get(word)+1);
				}
				else {
					map.add(word, 1);
				}
			}
			System.out.println("word different size :" +map.size());
			System.out.println("Frequency of Pride £º"+map.get("pride"));
			System.out.println("Frequency of Prejudice £º"+map.get("prejudice"));
		}
		long end = System.nanoTime();
		return (end-start)/ 1000000000.0;
	}
}
