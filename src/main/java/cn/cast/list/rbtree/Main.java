package cn.cast.list.rbtree;

import cn.cast.list.rbtree.printer.BinaryTrees;
import cn.cast.list.rbtree.tree.AVLTree;
import cn.cast.list.rbtree.tree.BST;
import cn.cast.list.rbtree.tree.RBTree;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("unused")
public class Main {

	@Test
	public void test1() {
		Integer data[] = new Integer[] {
				67, 52, 92, 96, 53, 95, 13, 63, 34, 82, 76, 54, 9, 68, 39
		};
		
		AVLTree<Integer> avl = new AVLTree<>();
		for (int i = 0; i < data.length; i++) {
			avl.add(data[i]);
//			System.out.println("【" + data[i] + "】");
//			BinaryTrees.println(avl);
//			System.out.println("---------------------------------------");
		}
		
//		for (int i = 0; i < data.length; i++) {
//			avl.remove(data[i]);
//			System.out.println("【" + data[i] + "】");
//			BinaryTrees.println(avl);
//			System.out.println("---------------------------------------");
//		}
		
		
		BinaryTrees.println(avl);
	}

	@Test
	public void test2() {
		List<Integer> data = new ArrayList<>();
		for (int i = 0; i < 100_0000; i++) {
			data.add((int)(Math.random() * 100_0000));
		}
		
		BST<Integer> bst = new BST<>();
		for (int i = 0; i < data.size(); i++) {
			bst.add(data.get(i));
		}
		for (int i = 0; i < data.size(); i++) {
			bst.contains(data.get(i));
		}
		for (int i = 0; i < data.size(); i++) {
			bst.remove(data.get(i));
		}
		
		AVLTree<Integer> avl = new AVLTree<>();
		for (int i = 0; i < data.size(); i++) {
			avl.add(data.get(i));
		}
		for (int i = 0; i < data.size(); i++) {
			avl.contains(data.get(i));
		}
		for (int i = 0; i < data.size(); i++) {
			avl.remove(data.get(i));
		}
	}

	@Test
	public void test3() {
		Integer data[] = new Integer[] {
				55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50
		};
		
		RBTree<Integer> rb = new RBTree<>();
		for (int i = 0; i < data.length; i++) {
			rb.add(data[i]);
			System.out.println("【" + data[i] + "】");
			BinaryTrees.println(rb);
			System.out.println("---------------------------------------");
		}
	}

	@Test
	public void test4() {
		Integer data[] = new Integer[] {
				55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50
		};
		
		RBTree<Integer> rb = new RBTree<>();
		for (int i = 0; i < data.length; i++) {
			rb.add(data[i]);
		}

		BinaryTrees.println(rb);

		for (int i = 0; i < data.length; i++) {
			rb.remove(data[i]);
			System.out.println("---------------------------------------");
			System.out.println("【" + data[i] + "】");
			BinaryTrees.println(rb);
		}
	}
	
}
