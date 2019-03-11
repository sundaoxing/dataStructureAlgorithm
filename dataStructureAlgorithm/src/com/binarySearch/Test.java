package com.binarySearch;

import java.util.ArrayList;
import java.util.Random;

public class Test {
	@org.junit.Test
	public void test() {
		BinarySearchTree<String> bst = new BinarySearchTree<>();
		bst.insert(5, "sun");
		bst.insert(2, "xing");
		bst.insert(3, "qiqi");
		bst.insert(8, "ziwang");
		bst.insert(7, "gifty");
		System.out.println(bst.contain(2));
		System.out.println(bst.search(8));
		/*bst.preOrder();
		bst.inOrder();
		bst.postOrder();*/
		//bst.levelOrder();
		/*System.out.println(bst.getMinimum());
		System.out.println(bst.getMaxmum());
		bst.removeMin();
		bst.levelOrder();*/
		bst.remove(5);
		bst.levelOrder();
	}

	@org.junit.Test
	public void test2() {
		BST<Integer> b= new BST<>();
		int [] num = {5,3,6,8,4,2};
		for(int i=0;i<num.length;i++) {
			b.add(num[i]);
		}
//		b.preOrder_();
//		b.preOrder();
		//b.inOrder();
		//b.postOrder();
//		b.levelOrder();
//		System.out.println(b.minItem());
//		System.out.println(b.maxItem());
//		System.out.println(b.removeMin());
//		System.out.println(b.removeMin());
		b.remove(3);
		b.preOrder();
	}
	@org.junit.Test
	public void test3() {
		BST<Integer> b = new BST<>();
		int n=1000;
		Random r = new Random();
		for(int i=0;i<n;i++) {
			b.add(r.nextInt(10000));
		}
		ArrayList<Integer> a = new ArrayList<>();
		while(!b.isEmpty()) {
			a.add(b.removeMin());
		}
		System.out.println(a);
		for(int i=1;i<a.size();i++) {
			if(a.get(i-1) > a.get(i)) {
				System.out.println("false");
			}
		}
		System.out.println("true");
	}
	
	@org.junit.Test
	public void test4() {
		BST<Integer> b = new BST<>();
		int n=1000;
		Random r = new Random();
		for(int i=0;i<n;i++) {
			b.add(r.nextInt(10000));
		}
		ArrayList<Integer> a = new ArrayList<>();
		while(!b.isEmpty()) {
			a.add(b.removeMax());
		}
		System.out.println(a);
		for(int i=1;i<a.size();i++) {
			if(a.get(i-1) < a.get(i)) {
				System.out.println("false");
			}
		}
		System.out.println("true");
	}
}
