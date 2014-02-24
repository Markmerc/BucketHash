package bucketHash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


/**
 * Implements a map. The map does not accept null values or keys.
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 * @author Mark Mercurio
 * @version 1.0
 */
public class BucketHash<K, V> implements Map<K, V> {
	private int numBuckets = 101;
	private Object[] buckets = new Object[numBuckets];
	
	/**
	 * Constructs an empty BucketHash with the capacity (101) 
	 */
	public BucketHash() {
		for (int i = 0; i < numBuckets; i++) {
			buckets[i] = new Bucket();
		}
	}
	
	/**
	 * Constructs a new BucketHash with the same mappings as the specified Map.
	 * @param map the map to take the key-value pairs from
	 * @throws NullPointerException if the given map is null.
	 */
	public BucketHash(Map<? extends K,? extends V> map) {
		if (map == null) 
			throw new NullPointerException();
		
		for (int i = 0; i < numBuckets; i++) {
			buckets[i] = new Bucket();
		}
		
		putAll(map);
		
	}
	

	/**
	 * Returns the number of key-value mappings in this map. 
	 * If the map contains more than Integer.MAX_VALUE elements, 
	 * returns Integer.MAX_VALUE.
	 * @return the number of key-value mappings in this map
	 */
	@Override
	public int size() {
		int size = 0;
		for (Object x : buckets) {
			int bucketSize = ((Bucket)x).size();
			
			if ((size + bucketSize) < size)
				size = Integer.MAX_VALUE;
			else
				size += bucketSize;
		}
		return size;
	}

	/**
	 * Returns true if this map contains no key-value mappings.
	 * @return true if this map contains no key-value mappings.
	 */
	@Override
	public boolean isEmpty() {
		for (Object x : buckets) {
			if(((Bucket)x).size() != 0)
				return false;
		}
		
		return true;
	}
	
	/**
	 * Returns true if this map contains the given key.
	 * @param key The key whose presence in this map is to be tested.
	 * @throws NullPointerException if the key is null.
	 */
	@Override
	public boolean containsKey(Object key) {
		if (key == null)
			throw new NullPointerException();
	
		if (((Bucket)buckets[getIndex(key)]).get((K)key) == null)
			return false;
		else
			return true;
	}
	
	/**
	 * Returns true if this map contains the given value.
	 * @param value The value whose presence in this map is to be tested.
	 * @throws NullPointerException if the value is null.
	 */
	@Override
	public boolean containsValue(Object value) {
		for(int i = 0; i < numBuckets; i++) {
			if (((Bucket)buckets[i]).containsValue((V)value))
				return true;
		}
		
		return false;
	}
	
	/**
	 * Returns the value associated with the given key in this map. If
	 * this key does not exist, returns null.
	 * @param key the key whose associated value is to be returned
	 * @throws NullPointerException if the given key is null.
	 */
	@Override
	public V get(Object key) {
		if (key == null)
			throw new NullPointerException();
		
		return ((Bucket)buckets[getIndex(key)]).get((K)key);
	}
	
	/**
	 * Puts the given (key, value) pair in the map. 
	 * @param key the key with which the specified value is to be associated
	 * @param value the value to be associated with the specified key
	 * @return If the key already exists in the maps, returns the
	 * value previous associated with the key, else returns null.
	 */
	@Override
	public V put(K key, V value) {
		return ((Bucket)buckets[getIndex(key)]).put(key, value);

	}
	
	/**
	 * Removes the key-value pair associated with the key from this map.
	 * @param key the key whose key-value mapping should be removed
	 * @return the previous value associated with key, or null if there was no mapping for key.
	 * @throws NullPointerException if key is null.
	 */
	@Override
	public V remove(Object key) {
		if (key == null)
			throw new NullPointerException();
		
		return ((Bucket)buckets[getIndex(key)]).remove((K)key);
	}
	
	/**
	 * Copies all of the mappings from the specified map to this map. 
	 * These mappings will replace any mappings that this map had for any 
	 * of the keys currently in the specified map.
	 * @param m mappings to be stored in this map.
	 * @throws NullPointerException if the map is null or contains null keys or values.
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		if (m == null)
			throw new NullPointerException();
		
		for(K key : m.keySet()) {
			if (key == null)
				throw new NullPointerException();
			
			this.put(key, m.get(key));
		}		
	}
	
	/**
	 * Removes all of the mappings from this map. 
	 * The map will be empty after this call returns.
	 */
	@Override
	public void clear() {
		for(int i = 0; i < numBuckets; i++) {
			buckets[i] = new Bucket();
		}
		
	}

	/**
	 * Returns a Set of the keys contained in this map.
	 * Contrary to the map interface, this map does <b>not</b> return a view.
	 * @return the Set of keys in this map.
	 */
	@Override
	public Set<K> keySet() {
		Set<K> keySet = new HashSet<K>();
		for(int i = 0; i < numBuckets; i++) {
			keySet.addAll(((Bucket)buckets[i]).keySet());
		}
		
		return keySet;
	}

	/**
	 * Returns a Collection of the values contained in this map.
	 * Contrary to the map interface, this map does <b>not</b> return a view.
	 * @return the Collection of values in this map.
	 */
	@Override
	public Collection<V> values() {
		Collection<V> values = new ArrayList<V>();
		for(int i = 0; i < numBuckets; i++) {
			values.addAll(((Bucket)buckets[i]).values());
		}
		
		return values;
	}
	/**
	 * Unimplemented method.
	 * @throws UnsupportedOperationException
	 */
	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Compares the specified object with this map for equality. 
	 * Returns true if the given object is also a map and the two maps 
	 * represent the same mappings.
	 * @return true if the specified object is equal to this map
	 */
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
		
		if (!(object instanceof Map)) 
			return false;
		
		Map<K, V> that = (Map<K, V>) object;
		if (this.size() != that.size())
			return false;
		
		for (K key : that.keySet()) {
			if (key == null)
				return false;
			
			if (!(this.containsKey(key)))
				return false;
			
			V thatValue = that.get(key);
			V thisValue = this.get(key);
			
			
			if (thatValue == null || thisValue == null)
				return false;
			
			if (!(thatValue.equals(thisValue))) 
				return false;
		}
		return true;
	}
	

	/**
	 * Returns the hash code value for this map. 
	 * @return the hash code value for this map
	 */
	@Override
	public int hashCode() {
		int hashCode = 0;
		
		for(int i = 0; i < numBuckets; i++) {
			hashCode += ((Bucket)buckets[i]).hashCode();
		}
		
		return hashCode;
	}
	
	/**
	 * Returns a reference to the bucket the specified key is located in.
	 * @param key the key whose bucket is returned
	 * @return the Bucket
	 */
	Bucket getBucket(K key) {
		return (Bucket)buckets[getIndex(key)];
	}
	
	/**
	 * Determines the bucket the given object should hash to.
	 * @param o the object to hash
	 * @return the bucket number
	 */
	private int getIndex(Object o) {
		return o.hashCode() % numBuckets;
	}
	
	/**
	 * Implements a bucket in a hash table.
	 * @author Mark Mercurio
	 */
	class Bucket {
		/**
		 * The head of the linked list in this Bucket
		 */
		ListNode header;
		
		private int size = 0;
		
		/**
		 * Represents a node of a linked list
		 * @author Mark Mercurio
		 */
		private class ListNode {
			K key;
			V value;
			ListNode next;
			
			/**
			 * Creates a new node
			 */
			ListNode(K key, V value, ListNode next) {
				this.key = key;
				this.value = value;
				this.next = next;
			}
			
		}
		
		/**
		 * Creates a Bucket with no linked list.
		 */
		public Bucket() {
			header = null;
		}
		
		/**
		 * Returns the value associated with this key, or null if the key is not present
		 * @param key the key whose value is to be returned.
		 * @return the value associated with the given key.
		 * @throws NullPointerException if the key is null.
		 */
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
		
		/**
		 * Returns the hash code of the given bucket.
		 * @return the hash code
		 */
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
		
		/**
		 * Returns true if the map contains the given key-value mapping
		 * @param key the key to search
		 * @param value the expected value associated with that key
		 * @return true if the map contains the key-value mapping else false
		 */
		public boolean containsMap(K key, V value) {
			ListNode result = find(key);
			if (result == null) 
				return false;
			else 
				return value.equals(result.value);
			
		}
		
		/**
		 * Returns true if the map contains the given value mapping
		 * @param value to search for
		 * @return true if the map contains the value else false
		 * @throws NullPointerException if the given value is null
		 */
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
		
		/**
		 * Returns the number of elements in this Bucket
		 * @return the number of elements in this Bucket
		 */
		public int size() {
			return size;
		}
		
		/**
		 * Returns the ListNode containing the key, or null. 
		 * @param key the key to search for
		 * @return the node with the given key, if the key exists, else null
		 */
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
		
		/**
		 * Puts the key-value map into the Bucket. 
		 * If the key is already in this list, replaces its 
		 * value with the new value, and returns the value 
		 * previously associated with the key.
		 * @param key the key for the given mapping
		 * @param value the value associated with the given key
		 * @return returns null if the key was previously not in 
		 * the list, otherwise returns the value previously associated with the given key
		 * @throws NullPointerException if either the key or value is null
		 */
		public V put(K key, V value) {
			if (key == null || value == null)
				throw new NullPointerException("neither key nor value can be null");


			ListNode node = find(key);
			
			if (node == null) {
				if (size < Integer.MAX_VALUE)
					size++ ;
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
		
		/**
		 * If the key is in this list, deletes the key/value entry, 
		 * and returns the value that was associated with the key.
		 * If the key is not present in this list, returns null.
		 * @param key the key whose key-value mapping is to be removed.
		 * @return null if the key was not in the map, 
		 * else the value that was associated with that key
		 * @throws NullPointerException if the given key is null
		 */
		public V remove(K key) {
			if (key == null)
				throw new NullPointerException();
			
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
		
		/**
		 * Returns a Set of the keys found in this Bucket.
		 * @return the Set of keys.
		 */
		public Set<K> keySet() {
			Set<K> keySet = new HashSet<K>();
			ListNode current = header;
			
			while (current != null) {
				keySet.add(current.key);
				current = current.next;
			}
			
			return keySet;
			
		}
		
		/**
		 * Returns a Collection of the values found in this Bucket.
		 * @return the Collection of values.
		 */
		public Collection<V> values() {
			Collection<V> values = new ArrayList<V>();
			
			ListNode current = header;
			while (current != null) {
				values.add(current.value);
				current = current.next;
			}
			
			return values;
		}
		
		/**
		 * Returns a String representation of this Bucket
		 */
		@Override
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
