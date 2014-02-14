package bucketHash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BucketHash<K, V> implements Map<K, V> {
	private int numBuckets = 101;
	private Object[] buckets = new Object[numBuckets];
	
	//CONSTRUCTORS
	public BucketHash() {
		for (int i = 0; i < numBuckets; i++) {
			buckets[i] = new Bucket();
		}
	}
	
	public BucketHash(Map<K, V> map) {
		//TODO
	}
	

	
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public V get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public V put(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean equals(Object object) {
		// TODO
	}
	
	public int hashCode() {
		// TODO
	}
	
	Bucket getBucket(K key) {
		return (Bucket)buckets[key.hashCode() % numBuckets];
	}
	
	private int getIndex(Object o) {
		// TODO - optional
	}
	
	class Bucket {
		ListNode header;
		int size = 0;
		
		class ListNode {
			K key;
			V value;
			ListNode next;
			
			ListNode(K key, V value, ListNode next) {
				this.key = key;
				this.value = value;
				this.next = next;
			}
			
		}
		
		public Bucket() {
			header = null;
		}
		
		public V get(K key) {
			ListNode node = find(key);
			if (node == null) {
				return null;
			}
			else {
				return node.value;
			}
		}
		
		public int size() {
			return size;
		}
		
		private ListNode find(K key) {
			ListNode current = header;
			
			while (current != null) {
				if (key.equals(current.key)) {
					return current;
				}
				current = current.next;
			}
			
			return null;
		}
		
		public V put(K key, V value) {

			if (key == null || value == null)
				throw new IllegalArgumentException("neither key nor value can be null");


			ListNode node = find(key);
			
			if (node == null) {
				size++;
				ListNode temp = header;
				header = new ListNode(key, value, temp);
				return null;
			}
			else {
				V oldValue = node.value;
				node.value = value;
				return oldValue;
			}
		}
		
		public V remove(K key) {
			
			//check if it is first element in list
			if (header.key.equals(key)) {
				size--;
				V value = header.value;
				header = header.next;
				return value;
			}
			
			ListNode previous = null;
			ListNode current = header;
			
			//check remaining elements
			while (current != null) {
				if (current.key.equals(key)) {
					size--;
					V value = current.value;
					previous.next = current.next;
					return value;
				}
				previous = current;
				current = current.next;
			}
			
			return null;
		}
		
		public Set<K> keySet() {
			Set<K> keySet = new HashSet<K>();
			ListNode current = header;
			
			while (current != null) {
				keySet.add(current.key);
				current = current.next;
			}
			
			return keySet;
			
		}
		
		public Collection<V> values() {
			Collection<V> values = new ArrayList<V>();
			
			ListNode current = header;
			while (current != null) {
				values.add(current.value);
				current = current.next;
			}
			
			return values;
		}
		
		public String toString() {
			StringBuffer s = new StringBuffer();
			
			ListNode current = header;
			while (current != null) {
				String t = "(" + current.key + " " + current.value + ")" + "-->";
				s.append(t);
				current = current.next;
			}
			return s.append("END\n").toString();
		}
	}
	
}
