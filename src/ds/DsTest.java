package ds;

import java.util.LinkedList;
import java.util.Stack;

import org.junit.jupiter.api.Test;

class DsTest {

	class SinglyLinkedListNode {
		int data;
		SinglyLinkedListNode next;

		public SinglyLinkedListNode(int data) {
			this.data = data;
			next = null;
		}
		
		public int getData() {
			return data;
		}

		public void setData(int data) {
			this.data = data;
		}

		public SinglyLinkedListNode getNext() {
			return next;
		}

		public void setNext(SinglyLinkedListNode next) {
			this.next = next;
		}
		
	}
	class DoublyLinkedListNode {
		int data;
		DoublyLinkedListNode prev;
		DoublyLinkedListNode next;
	}
	static void deleteNode(SinglyLinkedListNode node) {
		node.data = node.next.data;
		node.next = node.next.next; // what about NULL pointer 
	}
	
	// IN:  1->2->3->4->5->NULL, m=2, n=4
	// OUT: 1->4->3->2->5->NULL
	static SinglyLinkedListNode reverseBetween(SinglyLinkedListNode head, int m, int n) {
		return null;
	}
	static SinglyLinkedListNode reverse(SinglyLinkedListNode head) {
		return null;
	}
	// merge sort (split it)
	static SinglyLinkedListNode sortList(SinglyLinkedListNode head) {
		return null;
	}
	
	// IN:  1->4->3->2->5->2->NULL, x=3
	// OUT: 1->2->2->4->3->5
	static SinglyLinkedListNode partitionList(SinglyLinkedListNode head, int x) {
		return null;
	}
	// slow, fast --> reverse 2nd part, then compare the 2 list
	static boolean isPalindrome(SinglyLinkedListNode head) {
		return false;
	}
	
	// IN:  1->4->3->2->5->2->NULL, k=2
	// OUT: 1->4->3->2   ->2
	static SinglyLinkedListNode removeNthNodeFromEndOfList(SinglyLinkedListNode head, int k) {
		return null;
	}
	
	
	
	// BST
	static class Node {
		int data;
		Node left, right;
		Node (int data) {
			this.data = data;
		}
		
		// insert
		void insert(int d) 
		{
			if (d <= this.data) {
				if (left == null) {
					left = new Node(d);
				} else {
					left.insert(d);
				}
			} else {
				if (right == null) {
					right = new Node(d);
				} else {
					right.insert(d);
				}
			}
		}
		
		// contains
		boolean contains(int d) {
			if (d == this.data) {
				return true;
			} else if (d < this.data) {
				if (left == null) {
					return false;
				} else {
					return left.contains(d);
				}
			} else {
				if (right == null) {
					return false;
				} else {
					return right.contains(d);
				}
			}
		}
		
		// printInOrder
		void printInOrder() {
			if (left != null) {
				left.printInOrder();
			}
			System.out.println(this.data);
			
			if (right != null) {
				right.printInOrder();
			}
		}
		
		// printPreOrder
		
		// printPostOrder

	}
	class BST {
		Node root;
		
		void insert(int d) {
			if (root == null) root = new Node(d);
			else root.insert(d);
		}
		
		boolean contains(int d) {
			if (root != null) return root.contains(d);
			return false;
		}
		
		void printInOrder() {
			if (root != null) root.printInOrder();
			System.out.println();
		}
	}
	
	static void preOrderIt(Node root) {
		if (root == null) {
			return;
		}
		Stack<Node> s = new Stack();
		s.push(root);
		
		while (!s.isEmpty()) {
			// print root
			Node curNode = s.pop();
			System.out.println(curNode.data + " ");
			
			//push right 
			if (curNode.right != null) {
				s.push(curNode.right);
			}
			
			// push left 
			if (curNode.left != null) {
				s.push(curNode.left);
			}
		}
	}
	static void preOrder(Node root) {
		if (root == null) return;
		System.out.println(root.data + " ");
		preOrder(root.left);
		preOrder(root.right);
	}
	static void inOrder(Node root) {
		if (root == null) return;
		inOrder(root.left);
		System.out.println(root.data + " ");
		inOrder(root.right);
	}
	
	static Node insert(Node root, int d) {
		if (root == null) {
			return new Node(d);
		} else {
			Node cur;
			if (d <= root.data) {
				cur = insert(root.left, d);
				root.left = cur;
			} else {
				cur = insert(root.right, d);
				root.right = cur;
			}
		}
		return root;
	}

	static void levelOrder(Node root) {
		if (root == null) {
			return;
		}
		LinkedList <Node> queue = new LinkedList();
		queue.add(root);
		
		while (!queue.isEmpty()) {
			Node curNode = queue.poll();
			System.out.println(curNode.data + " ");
			
			// left
			if (curNode.left != null) queue.add(curNode.left);
			
			//right
			if (curNode.right != null) queue.add(curNode.right);
		}
	}
	static int getNodeFromTail( SinglyLinkedListNode head, int tailPos) {
		// use head to do loop run
		// use 2nd node runner to delay tailPos step to start
		// when head run to the end, the delayed pointer is at the correct pos
		SinglyLinkedListNode nodePointer = head;
		int count = 0;
		
		while (head != null ) {
			head = head.next;
			if (count < tailPos) {
				count++;
			} else {
				nodePointer = nodePointer.next;
			}
		}
		
		return nodePointer.getData();
	}
	
	static int findMergeNode(SinglyLinkedListNode head1, SinglyLinkedListNode head2) {
		// 1: 1 -> 2 -> 3 -> 7 -> 8
		// 2:      5 -> 6 -> 7 -> 8
		// 1: 1 -> 2 -> 3 -> 7 -> 8 -> 5 -> 6 -> 7 -> 8  (length 9)
		// 2: 5 -> 6 -> 7 -> 8 -> 1 -> 2 -> 3 -> 7 -> 8  (length 9)
		SinglyLinkedListNode head1_current = head1;
		SinglyLinkedListNode head2_current = head2;
		while (head1_current != head2_current) {
			if (head1_current == null) {
				head1_current = head2;
			} else {
				head1_current = head1_current.next;
			}
			if (head2_current == null) {
				head2_current = head1;
			} else {
				head2_current = head2_current.next;
			}
		}
		return head1_current.getData();
	}
	@Test
	void testGetNodeFromTail() {
		// 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10
		// tail position: 3  => result: 7
		
		
	}

}
