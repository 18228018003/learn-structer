package cn.cast.list.tree;

import cn.cast.list.tree.file.Files;
import cn.cast.list.tree.printer.BinaryTreeInfo;
import cn.cast.list.tree.printer.BinaryTrees;
import org.junit.Test;

import java.util.Comparator;

@SuppressWarnings("unused")
public class Main {
	
	private static class PersonComparator implements Comparator<Person> {
		public int compare(Person e1, Person e2) {
			return e1.getAge() - e2.getAge();
		}
	}
	
	private static class PersonComparator2 implements Comparator<Person> {
		public int compare(Person e1, Person e2) {
			return e2.getAge() - e1.getAge();
		}
	}

	@Test
	public void test1() {
		Integer[] data = new Integer[] {
				7, 4, 9, 2, 5, 8, 11, 3, 12, 1
		};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (Integer datum : data) {
			bst.add(datum);
		}
		
		BinaryTrees.println(bst);
	}

	@Test
	public void test2() {
		Integer[] data = new Integer[] {
				7, 4, 9, 2, 5, 8, 11, 3, 12, 1
		};
		
		BinarySearchTree<Person> bst1 = new BinarySearchTree<>();
		for (Integer integer : data) {
			bst1.add(new Person(integer));
		}
		
		BinaryTrees.println(bst1);
		
		BinarySearchTree<Person> bst2 = new BinarySearchTree<>(new Comparator<Person>() {
			public int compare(Person o1, Person o2) {
				return o2.getAge() - o1.getAge();
			}
		});
		for (Integer datum : data) {
			bst2.add(new Person(datum));
		}
		BinaryTrees.println(bst2);
	}
	
	static void test3() {
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (int i = 0; i < 40; i++) {
			bst.add((int)(Math.random() * 100));
		}
		
		String str = BinaryTrees.printString(bst);
		str += "\n";
		Files.writeToFile("F:/1.txt", str, true);
		
		// BinaryTrees.println(bst);
	}
	
	static void test4() {
		BinaryTrees.println(new BinaryTreeInfo() {
			
			@Override
			public Object string(Object node) {
				return node.toString() + "_";
			}
			
			@Override
			public Object root() {
				return "A";
			}
			
			@Override
			public Object right(Object node) {
				if (node.equals("A")) return "C";
				if (node.equals("C")) return "E";
				return null;
			}
			
			@Override
			public Object left(Object node) {
				if (node.equals("A")) return "B";
				if (node.equals("C")) return "D";
				return null;
			}
		});
		
		// test3();
		
		
		/*
		 * Java的匿名类，类似于iOS中的Block、JS中的闭包（function）
		 */
		
//		BinarySearchTree<Person> bst1 = new BinarySearchTree<>(new Comparator<Person>() {
//			@Override
//			public int compare(Person o1, Person o2) {
//				return 0;
//			}
//		});
//		bst1.add(new Person(12));
//		bst1.add(new Person(15));
//		
//		BinarySearchTree<Person> bst2 = new BinarySearchTree<>(new PersonComparator());
//		bst2.add(new Person(12));
//		bst2.add(new Person(15));
//
		
		
//		BinarySearchTree<Car> bst4 = new BinarySearchTree<>(new Car);
//		
//		
//		java.util.Comparator<Integer> iComparator;
	}

	@Test
	public void test5() {
		BinarySearchTree<Person> bst = new BinarySearchTree<>();
		bst.add(new Person(10, "jack"));
		bst.add(new Person(12, "rose"));
		bst.add(new Person(6, "jim"));
		
		bst.add(new Person(10, "michael"));
		
		BinaryTrees.println(bst);
	}

	@Test
	public void test6() {
		Integer[] data = new Integer[] {
				7, 4, 9, 2, 5,8,10,11
		};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (Integer datum : data) {
			bst.add(datum);
		}
		
//		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
//		for (int i = 0; i < 10; i++) {
//			bst.add((int)(Math.random() * 100));
//		}
		BinaryTrees.println(bst);
		System.out.println(bst.isComplete());
		// bst.levelOrderTraversal();
		
		/*
		 *       7
		 *    4    9
		    2   5
		 */
		
		bst.postOrder(new BinarySearchTree.Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print(element + "->");
				return false;
			}
		});
		
//		bst.inorder(new Visitor<Integer>() {
//			public void visit(Integer element) {
//				System.out.print("_" + (element + 3) + "_ ");
//			}
//		});
		
		// System.out.println(bst.height());
	}

	@Test
	public void test7() {
		Integer[] data = new Integer[] {
				7, 4, 9, 2, 5, 8, 11, 3, 12, 1
		};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (Integer datum : data) {
			bst.add(datum);
		}
		
		BinaryTrees.println(bst);

//		System.out.println(bst.height());
		bst.remove(9);
		BinaryTrees.println(bst);
	}

	@Test
	public void test8() {
		Integer[] data = new Integer[] {
				7, 4, 9, 2, 1,5,8,10,6
		};
		
		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (Integer datum : data) {
			bst.add(datum);
		}
		BinaryTrees.println(bst);
		System.out.println(bst.isCBT());
	}
	
	static void test9() {
		Integer[] data = new Integer[] {
				7, 4, 9, 2, 1
		};

		BinarySearchTree<Integer> bst = new BinarySearchTree<>();
		for (Integer datum : data) {
			bst.add(datum);
		}
		BinaryTrees.println(bst);

		bst.preorder(new BinarySearchTree.Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 2;
			}
		});
		System.out.println();

		bst.inorder(new BinarySearchTree.Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 4;
			}
		});
		System.out.println();

		bst.postorder(new BinarySearchTree.Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 4;
			}
		});
		System.out.println();

		bst.levelOrder(new BinarySearchTree.Visitor<Integer>() {
			public boolean visit(Integer element) {
				System.out.print(element + " ");
				return element == 9;
			}
		});
		System.out.println();
	}
	
	public static void main(String[] args) {
		test9();
	}
}
