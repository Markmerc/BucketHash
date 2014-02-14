package bucketHash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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
		for (int i = 0; i < numBuckets; i++) {
			buckets[i] = new Bucket();
		}
		
		putAll(map);
		
	}
	

	
	
	@Override
	public int size() {
		int size = 0;
		for (Object x : buckets) 
			size += ((Bucket)x).size();
	
		return size;
	}
	@Override
	public boolean isEmpty() {
		for (Object x : buckets) {
			if(((Bucket)x).size() != 0)
				return false;
		}
		
		return true;
	}
	@Override
	public boolean containsKey(Object key) {
		//TODO - casting key to K
	
		if (((Bucket)buckets[getIndex(key)]).get((K)key) == null)
			return false;
		else
			return true;
	}
	@Override
	public boolean containsValue(Object value) {
		for(int i = 0; i < numBuckets; i++) {
			if (((Bucket)buckets[i]).containsValue((V)value))
				return true;
		}
		
		return false;
	}
	
	@Override
	public V get(Object key) throws NullPointerException {
		if (key == null)
			throw new NullPointerException();
		
		return ((Bucket)buckets[getIndex(key)]).get((K)key);
	}
	
	@Override
	public V put(K key, V value) throws NullPointerException {
		return ((Bucket)buckets[getIndex(key)]).put(key, value);

	}
	
	@Override
	public V remove(Object key) {
		return ((Bucket)buckets[getIndex(key)]).remove((K)key);
	}
	
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		if (m == null)
			throw new NullPointerException();
		
		for (Map.Entry<? extends K, ? extends V> x : m.entrySet()) {
			K key = x.getKey();
			V value = x.getValue();
			
			((Bucket)buckets[getIndex(key)]).put(key, value);
		}
		
	}
	@Override
	public void clear() {
		for(int i = 0; i < numBuckets; i++) {
			buckets[i] = new Bucket();
		}
		
	}
	@Override
	public Set<K> keySet() {
		Set<K> keySet = new HashSet();
		for(int i = 0; i < numBuckets; i++) {
			keySet.addAll(((Bucket)buckets[i]).keySet());
		}
		
		return keySet;
	}
	@Override
	public Collection<V> values() {
		Collection<V> values = new ArrayList();
		for(int i = 0; i < numBuckets; i++) {
			values.addAll(((Bucket)buckets[i]).values());
		}
		
		return values;
	}
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
		
		if (!(object instanceof Map)) 
			return false;
		
		Map<K, V> that = (Map<K, V>) object;
		if (this.size() != that.size())
			return false;
		
		for (Map.Entry<K, V> x : that.entrySet()) {
			K key = x.getKey();
			V value = x.getValue();
			if (!((Bucket)buckets[getIndex(x.getKey())]).containsMap(key, value))
				return false;
		}
		
		return true;
	}
	
	@Override
	public int hashCode() {
		int hashCode = 0;
		
		for(int i = 0; i < numBuckets; i++) {
			hashCode += ((Bucket)buckets[i]).hashCode();
		}
		
		return hashCode;
	}
	
	Bucket getBucket(K key) {
		return (Bucket)buckets[getIndex(key)];
	}
	
	private int getIndex(Object o) {
		return o.hashCode() % numBuckets;
	}
	
	class Bucket {
		ListNode header;
		int size = 0;
		
		private class ListNode {
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
			if (key == null) 
				throw new NullPointerException();
			
			ListNode node = find(key);
			if (node == null) {
				return null;
			}
			else {
				return node.value;
			}
		}
		
		public int hashCode() {
			ListNode current = header;
			
			int hashCode = 0;
			while (current != null) {
				hashCode += (current.key == null ? 0 : current.key.hashCode()) ^
			     (current.value == null ? 0 : current.value.hashCode());
				current = current.next;
			}
			
			return hashCode;
		}
		
		public boolean containsMap(K key, V value) {
			ListNode result = find(key);
			if (result == null) 
				return false;
			else 
				return value.equals(result.value);
			
		}
		public boolean containsValue(V value) {
			if (value == null) 
				throw new NullPointerException();
			
			ListNode current = header;
			
			while (current != null) {
				if (current.value.equals(value))
					return true;
				current = current.next;
			}
			
			return false;
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
				throw new NullPointerException("neither key nor value can be null");


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
