package bucketHash;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;


import org.junit.Before;
import org.junit.Test;

public class BucketTest {

	BucketHash<Integer, String> table;

	@Before
	public void setUp() throws Exception {
		table = new BucketHash<Integer, String>();
	}

	
	
    @Test (expected=NullPointerException.class)
    public void testPutWithNullValue() {
    	table.getBucket(0).put(0, null);
    }
    
    @Test (expected=NullPointerException.class)
    public void testPutWithNullKey() {
    	table.getBucket(0).put(null, "ONE");
    }

	
    
    
    
	/**
	 * Tests the put method of the inner Bucket class.
	 * All commented tests passed while ListNode was protected.
	 */
	@Test
	public void testPut() {
		table.getBucket(0).put(0, "ONE");
		table.getBucket(0).put(101, "TWO");
		assertTrue(table.getBucket(0).header != null);
		assertTrue(table.getBucket(0).size == 2);
		
		//made ListNode protected for these tests
//		assertTrue(table.getBucket(0).header.value.equals("TWO"));
//		assertTrue(table.getBucket(0).header.next.value.equals("ONE"));
//		assertNull(table.getBucket(0).header.next.next);
		
		table.getBucket(0).put(101, "THREE");
//		assertTrue(table.getBucket(0).header.value.equals("THREE"));
		assertTrue(table.getBucket(0).size == 2);
		
		assertNull(table.getBucket(0).put(202, "FOUR"));
		assertTrue(table.getBucket(0).put(202, "FIVE").equals("FOUR"));

	}
	
	@Test
	public void testContainsMap() {
		table.getBucket(0).put(0, "ONE");
		table.getBucket(0).put(101, "TWO");

		assertTrue(table.getBucket(0).containsMap(0, "ONE"));
		assertTrue(table.getBucket(0).containsMap(101, "TWO"));
		assertFalse(table.getBucket(0).containsMap(0, "FOO"));
		assertFalse(table.getBucket(0).containsMap(101, "BAR"));
		assertFalse(table.getBucket(0).containsMap(9, "ONE"));
		assertFalse(table.getBucket(0).containsMap(9, "TWO"));
		assertFalse(table.getBucket(0).containsMap(9, "FOO"));


	}
	
	@Test
	public void testGet() {
		table.getBucket(0).put(0, "ONE");
		table.getBucket(0).put(101, "TWO");

		assertTrue(table.getBucket(0).get(0).equals("ONE"));
		assertTrue(table.getBucket(0).get(101).equals("TWO"));
		assertNull(table.getBucket(0).get(202));
	}
	
	@Test
	public void containsValue() {
		table.getBucket(0).put(0, "ONE");
		table.getBucket(0).put(101, "TWO");

		assertFalse(table.getBucket(0).containsValue("FOUR"));
		assertTrue(table.getBucket(0).containsValue("ONE"));
	}
	
    @Test (expected=NullPointerException.class)
    public void containsValueWithNull() {
    	table.getBucket(0).containsValue(null);
    }
	
	/**
	 * Tests the remove method of the inner Bucket class.
	 * All commented tests passed while ListNode was protected.
	 */
	@Test
	public void testRemove() {
		table.getBucket(0).put(0, "ONE");
		table.getBucket(0).put(101, "TWO");
		table.getBucket(0).put(202, "THREE");
		
		/* ListNode was made protected for these tests */
		
		//Removes head
		assertTrue(table.getBucket(0).remove(202).equals("THREE"));
		assertTrue(table.getBucket(0).size == 2);
//		assertTrue(table.getBucket(0).header.value.equals("TWO"));
//		assertTrue(table.getBucket(0).header.next.value.equals("ONE"));
//		assertNull(table.getBucket(0).header.next.next);		

		
		//Remove tail
		table.getBucket(0).put(202, "THREE");
		assertTrue(table.getBucket(0).remove(0).equals("ONE"));
		assertTrue(table.getBucket(0).size == 2);
//		assertTrue(table.getBucket(0).header.value.equals("THREE"));
//		assertTrue(table.getBucket(0).header.next.value.equals("TWO"));	
//		assertNull(table.getBucket(0).header.next.next);	
		
		//Remove middle
		table.getBucket(0).put(0, "FOUR");
		assertTrue(table.getBucket(0).remove(202).equals("THREE"));
		assertTrue(table.getBucket(0).size == 2);
//		assertTrue(table.getBucket(0).header.value.equals("FOUR"));
//		assertTrue(table.getBucket(0).header.next.value.equals("TWO"));	
//		assertNull(table.getBucket(0).header.next.next);

		//Remove non existing key
		assertNull(table.getBucket(0).remove(505));
		assertTrue(table.getBucket(0).size == 2);
//		assertTrue(table.getBucket(0).header.value.equals("FOUR"));
//		assertTrue(table.getBucket(0).header.next.value.equals("TWO"));	
//		assertNull(table.getBucket(0).header.next.next);
	}
	
	@Test
	public void testSize() {
		assertTrue(table.getBucket(0).size() == 0);
		
		table.getBucket(0).put(0, "ONE");
		assertTrue(table.getBucket(0).size() == 1);
		
		table.getBucket(0).put(0, "TWO");
		assertTrue(table.getBucket(0).size() == 1);
		
		table.getBucket(0).put(101, "ONE");
		assertTrue(table.getBucket(0).size() == 2);
		
		table.getBucket(0).remove(101);
		assertTrue(table.getBucket(0).size() == 1);
		
		table.getBucket(0).remove(505);
		assertTrue(table.getBucket(0).size() == 1);
	}

	@Test
	public void testKeySet() {
		table.getBucket(0).put(0, "ONE");
		table.getBucket(0).put(101, "TWO");
		table.getBucket(0).put(202, "THREE");
		
		Set<Integer> keys = new HashSet<Integer>();
		keys.add(0);
		keys.add(101);
		keys.add(202);
		
		assertTrue(table.getBucket(0).keySet().containsAll(keys));
	}
	
	@Test
	public void testValues() {
		table.getBucket(0).put(0, "ONE");
		table.getBucket(0).put(101, "TWO");
		table.getBucket(0).put(202, "THREE");
		
		Set<String> values = new HashSet<String>();
		values.add("ONE");
		values.add("TWO");
		values.add("THREE");
		
		assertTrue(table.getBucket(0).values().containsAll(values));
	}
	
	@Test
	public void testToString() {
		table.getBucket(0).put(0, "ONE");
		table.getBucket(0).put(101, "TWO");		
		assertEquals("(101 TWO)-->(0 ONE)-->END\n", table.getBucket(0).toString());
	}
	
	@Test
	public void testHashCode() {
		//TODO
		fail();
	}

}
