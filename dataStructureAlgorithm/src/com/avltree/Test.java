package com.avltree;

import java.util.ArrayList;
import com.set.FileOperation;

public class Test {
	
	@org.junit.Test
	public void test1() {
		String filename ="src/com/set/Pride-and-Prejudice.txt";
		AVLTree<String, Integer> map= new AVLTree<>();
		double time1 = testMap(map, filename);
		System.out.println("time : " + time1+" s");
		
//		System.out.println("isBST :" + map.isBST());
//		System.out.println("isBalanced :"+ map.isBalanced());
	}
	
	@org.junit.Test
	public void test2() {
		AVLTree<Integer, String> map = new AVLTree<>();
		map.add(12, "a");
		map.add(8, "b");
		map.add(18, "c");
		map.add(5, "d");
		map.add(11,"e");
		map.add(17, "g");
		map.add(3,"f");
		map.add(7, "h");
		map.add(4, "i");//不平衡，需要调整
	}
	
	
	public double testMap(AVLTree<String, Integer> map,String filename) {
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
			System.out.println("Frequency of Pride ："+map.get("pride"));
			System.out.println("Frequency of Prejudice ："+map.get("prejudice"));
//			for(String word :a) {
//				if(word.equals("prejudice")) {
//					System.out.println(word);
//				}
//				map.remove(word);
//				if(!map.isBST() || !map.isBalanced()) {
//					throw new RuntimeException("error");
//				}
//			}
		}
		long end = System.nanoTime();
		return (end-start)/ 1000000000.0;
	}
}
