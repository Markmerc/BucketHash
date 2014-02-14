package bucketHash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import tree.Tree;

public class BucketHash<K, V> implements Map<K, V> {
	private int numBuckets = 101;
	private Object[] buckets = new Object[numBuckets];
	
	//CONSTRUCTORS
	public BucketHash() {
		//TODO
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
		// TODO
	}
	
	private int getIndex(Object o) {
		// TODO - optional
	}
	
	class Bucket {
		ListNode header = null;
		
		private class ListNode {
			ListNode(K key, V vaue, ListNode next) {
				// TODO
			}
		}
		
		public Bucket() {
			// TODO
		}
		
		public V get(K key) {
			//TODO
		}
		
		private ListNode find(K key) {
			// TODO - OPTIONAL
		}
		
		public V put(K key, V value) {
			//TODO
		}
		
		public V remove(K key) {
			//TODO
		}
		
		public Set<K> keyset() {
			//TODO
		}
		
		public Collection<V> values() {
			//TODO
		}
		
		public String toString() {
			//TODO - OPTIONAL
		}
	}
	

}
