package com.example;

public class LRUDemo {
	public static void main(String[] args) {
		LRUCache<Integer,Integer> cache = new LRUCache<>(5);
		cache.put(1, 10);
		cache.put(2, 45);
		cache.put(3, 2);
		cache.put(4,7);
		cache.put(5, 9);
		System.out.println(cache.get(4));
		System.out.println(cache);
		System.out.println(cache.get(3));
		System.out.println(cache);
		cache.put(18, 544);
		System.out.println(cache);
	}
}
