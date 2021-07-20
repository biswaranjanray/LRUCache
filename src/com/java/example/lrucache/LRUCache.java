package com.java.example.lrucache;

import java.util.HashMap;
import java.util.Map;

class Node {

	int key;
	int value;
	Node next;
	Node previous;
	
	public Node(int key, int value) {
		this.key = key;
		this.value = value;
	}
}

public class LRUCache {

	private Node head;
	private Node tail;
	private Map<Integer, Node> map;
	private int capacity;
	
	public LRUCache(int capacity) {
		map = new HashMap<>();
		this.capacity = capacity;
	}
	
	/**
	 * 
	 * @param key
	 * @return
	 */
	public int get(int key) {
		
		//If key is found in the map then move it to the front else return -1
		if(map.containsKey(key)) {
			Node node = map.get(key);
			removeNode(node);
			moveToFront(node);
			
			return node.value;
		}
		
		return -1;
	}
	
	public void put(int key, int value) {
		
		//if key is present then update the value and move the node to the head of the list
		if(map.containsKey(key)) {
			Node node = map.get(key);
			node.value = value;

			removeNode(node);
			moveToFront(node);
		} else {
			//if the capacity is reached then remove the least accessed item.
			if(map.size() >= capacity) {
				map.remove(tail.key);
				removeNode(tail);
			}
		}
		
		//creating a new node, adding it to the map and moving it to the head in the list
		Node newNode = new Node(key, value);
		map.put(key, newNode);
		moveToFront(newNode);
		
	}
	
	private void removeNode(Node node) {
		
		Node previousNode = node.previous;
		Node nextNode = node.next;
		
		if(previousNode != null) {
			previousNode.next = node.next;
		} else {
			head = nextNode;
		}
		
		if(nextNode != null) {
			nextNode.previous = node.previous;
		} else {
			tail = previousNode;
		}
	}
	
	private void moveToFront(Node node) {
		node.next = head;
		node.previous = null;
		
		if(head != null) {
			head.previous = node;
		}
		
		head = node;
		
		if(tail == null) {
			tail = node;
		}
	}
	
	public static void main(String[] args) {
		LRUCache cache = new LRUCache(3);
		
		cache.put(1, 3);
		cache.put(4, 2);
		
		System.out.println(cache.get(1));	//returns 3
		
		cache.put(5, 6);
		System.out.println(cache.get(7));	//returns -1
		System.out.println(cache.get(5));	//returns 6
		
		cache.put(7, 4);
		
		System.out.println(cache.get(4));
		
		System.out.println(cache.get(1));
		System.out.println(cache.get(5));
		
	}
}
