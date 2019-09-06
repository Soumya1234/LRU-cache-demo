package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LRUCache<K, V> {
	

	private int capacity;
	private Entry<K, V> head;
	private Entry<K, V> tail;
	private Map<K, Entry<K, V>> map;

	public LRUCache(int capacity) {
		super();
		this.capacity = capacity;
		this.map = new HashMap<>();
	}

	public void put(K key, V value) {
		// if there is a mapping with the given key then we have a cache-hit and we just
		// replace the old value with new one and move the entry from the existing
		// location to the MRU location
		if (map.containsKey(key)) {
			Entry<K, V> existingEntry = map.get(key);
			existingEntry.value = value;
			remove(existingEntry);
			addToHead(existingEntry);
		}
		// If there is no mapping with the given key then we have a cache-miss and have
		// to add the item to the cache ( i.e add the entry to the LinkedList and
		// mapping to the map )
		else {
			Entry<K, V> entry = new Entry<>(key, value);
			// If the cache is full then the LRU entry will be removed from the LinkedList
			// and mapping removed from the map.Then the new
			// entry will be added to the MRU location.
			if (map.size() == capacity) {
				map.remove(tail.key);
				remove(tail);
				addToHead(entry);
			}
			// If the cache is not full then simply add the new entry to the MRU location
			else {
				addToHead(entry);
			}
			// finally add the mapping to the map
			map.put(key, entry);
		}
	}

	public V get(K key) {
		// If the there is a mapping with the given key, then we have cache-hit. We just
		// remove the entry from the current location in LinkedList and add it to the
		// MRU location. The we return the "entry.value"
		if (map.containsKey(key)) {
			Entry<K, V> entry = map.get(key);
			remove(entry);
			addToHead(entry);
			return entry.value;
		}
		// return null if we have a cache-miss
		return null;
	}

	private void addToHead(Entry<K, V> entry) {
		entry.next = head;
		if (head != null) {
			head.prev = entry;
		}
		head = entry;
		if (tail == null) {
			tail = head;
		}
	}

	private void remove(Entry<K, V> entry) {
		if (entry.prev == null) {
			head = entry.next;
			head.prev = null;
		} else if (entry.next == null) {
			entry.prev.next = null;
			tail = entry.prev;
		} else {
			entry.prev.next = entry.next;
			entry.next.prev = entry.prev;
		}
	}
	
	@Override
	public String toString() {
		if (map.isEmpty()) {
			return "Cache={}";
		}
		StringBuilder str = new StringBuilder("{");
		int i = 0;
		Entry<K, V> temp = head;
		while (i < map.size()) {
			if (i < map.size() - 1)
				str.append(temp.key + ":" + temp.value+",");
			else
				str.append(temp.key + ":" + temp.value);
			i++;
			temp = temp.next;
		}
		str.append("}");
		return str.toString();
	}

	static class Entry<K, V> {
		private K key;
		private V value;
		Entry<K, V> prev;
		Entry<K, V> next;

		public Entry(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public Entry<K, V> getNext() {
			return next;
		}
	}
}
