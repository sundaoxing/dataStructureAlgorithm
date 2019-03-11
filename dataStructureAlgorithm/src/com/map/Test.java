package com.map;

import java.util.ArrayList;

import com.set.FileOperation;

public class Test {
	@org.junit.Test
	public void test1() {
		String filename ="src/com/set/Pride-and-Prejudice.txt";
//		LinkedListMap<String, Integer> map1 = new LinkedListMap<>();
//		double time1 = testMap(map1,filename);
//		System.out.println("time : " +time1 +" s");
		
		BSTMap<String, Integer>map2 =new BSTMap<>();
		double time2 = testMap(map2,filename);
		System.out.println("time : " +time2 +" s");
		
		AVLMap<String, Integer> map3 = new AVLMap<>();
		double time3 = testMap(map3, filename);
		System.out.println("time : " +time3 +" s");
	}
	
	public double testMap(Map<String, Integer> map,String filename) {
		long start = System.nanoTime();
		System.out.println("Pride-and-Prejudice");
		ArrayList<String> a = new ArrayList<>();
		if(FileOperation.readFile(filename, a)) {
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
