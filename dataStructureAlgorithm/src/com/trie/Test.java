package com.trie;

import java.util.ArrayList;

import com.set.BSTSet;
import com.set.FileOperation;
import com.set.Set;

public class Test {
	@org.junit.Test
	public void test1() {
		Trie t = new Trie();
		t.add("bad");
		t.add("dad");
		t.add("mad");
		System.out.println(t.search("pad"));
		System.out.println(t.search("bad"));
		System.out.println(t.search(".ad"));
		System.out.println(t.search("b.."));
	}
	
	@org.junit.Test
	public void test2() {
		String filename="src/com/set/Pride-and-Prejudice.txt";
		BSTSet<String> BSTset = new BSTSet<>();
		double time1 =testSet(BSTset, filename);
		System.out.println("共耗时："+time1);
		System.out.println("---------------------");
		
		TrieSet t = new TrieSet();
		double time2 = testSet(t, filename);
		System.out.println("共耗时："+time2);
	}
	
	@org.junit.Test
	public void test3() {
		TrieMap t = new TrieMap();
		t.insert("apple", 3);
		System.out.println(t.sum("ap"));
		t.insert("app", 2);
		System.out.println(t.sum("ap"));
		
	}
	
	public double testSet(Set<String> set,String filename ) {
		long start = System.nanoTime();
		System.out.println("Pride-and-Prejudice");
		ArrayList<String> a = new ArrayList<>();
		FileOperation.readFile(filename, a);
		System.out.println("word size :" + a.size());
		for(String word :a) {
			set.add(word);
		}
		for(String word :a) {
			set.contains(word);
		}
		System.out.println("word different size :" +set.size());
		long end = System.nanoTime();
		return (end-start) / 1000000000.0;
	}
}
