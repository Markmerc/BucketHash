package bucketHash;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BucketHashTest {

	BucketHash<Integer, String> table;
	HashMap<Integer, String> table2;

	


	@Before
	public void setUp() throws Exception {
		table = new BucketHash<Integer, String>();
		table2 = new HashMap<Integer, String>();

	}

	
	
	@Test
	public void testPut() {
		assertNull(table.put(0, "ONE"));
		assertNull(table.put(101, "TWO"));
		assertNull(table.put(202, "THREE"));
		
		assertEquals(3, table.getBucket(0).size());
		
		assertEquals("THREE", table.put(202, "FOUR"));
		assertEquals(3, table.getBucket(0).size());
	}
	
    @Test (expected=NullPointerException.class)
    public void testPutWithNullValue() {
    	table.put(0, null);
    }
    
    @Test (expected=NullPointerException.class)
    public void testPutWithNullKey() {
    	table.put(null, "ONE");
    }

	@Test
	public void testGet() {
		table.put(0, "ONE");
		table.put(101, "TWO");

		assertTrue(table.get(0).equals("ONE"));
		assertTrue(table.get(101).equals("TWO"));
		assertNull(table.get(202));
	}
	
    @Test (expected=NullPointerException.class)
    public void testGetWithNullKey() {
    	table.get(null);
    }
    
	@Test
	public void testHashCode() {
		//TODO
		fail();
	}

	@Test
	public void testBucketHash() {
		fail("Not yet implemented");
	}

	@Test
	public void testBucketHashMapOfKV() {
		table2.put(50, "FOO");
		table2.put(80, "BAR");
		table2.put(90, "BAZ");
		
		BucketHash table3 = new BucketHash(table2);
		assertEquals(3, table3.size());
		assertTrue(table2.keySet().equals(table3.keySet()));
		assertTrue(table3.containsValue("FOO"));
		assertTrue(table3.containsValue("BAR"));
		assertTrue(table3.containsValue("BAZ"));

	}

	@Test
	public void testSize() {
		assertEquals(0, table.size());
		table.put(100, "DOG");
		assertEquals(1, table.size());
		table.put(150, "DOG");
		assertEquals(2, table.size());

	}

	@Test
	public void testIsEmpty() {
		assertTrue(table.isEmpty());
		table.put(100, "DOG");
		assertFalse(table.isEmpty());
	}

	@Test
	public void testContainsKey() {
		assertFalse(table.containsKey(new HashMap<Integer, String>()));
		table.put(200, "HELLO");
		table.put(300, "WHY");
		assertTrue(table.containsKey(200));
		assertTrue(table.containsKey(300));
		assertFalse(table.containsKey(301));
	}
	
    @Test (expected=NullPointerException.class)
	public void testContainsKeyWithNull() {
		table.containsKey(null);
	}

	@Test
	public void testContainsValue() {
		table.put(503045342, "FOO");
		assertTrue(table.containsValue("FOO"));
		assertFalse(table.containsValue("BAR"));
	}

	@Test
	public void testRemove() {
		table.put(1, "FOO");
		table.put(2, "BAR");
		table.put(3, "BAZ");
		
		table.remove(1);
		assertEquals(2, table.size());
		assertFalse(table.containsKey(1));
		assertFalse(table.containsValue("FOO"));
		assertTrue(table.containsKey(2));
		assertTrue(table.containsValue("BAR"));
		assertTrue(table.containsKey(3));
		assertTrue(table.containsValue("BAZ"));

	}

	@Test
	public void testPutAll() {
		table.put(50, "FOO");
		table.put(60, "BAR");
		table.put(70, "BAZ");
		
		table2.put(50, "REPLACE");
		table2.put(80, "HELLO");
		table2.put(90, "WORLD");
		
		table.putAll(table2);
		assertEquals(5, table.size());
		assertEquals("REPLACE", table.get(50));
		assertEquals("BAR", table.get(60));
		assertEquals("BAZ", table.get(70));
		assertEquals("HELLO", table.get(80));
		assertEquals("WORLD", table.get(90));		
	}
	
	@Test (expected=NullPointerException.class)
	public void testPutAllNull() {
		table.put(50, "FOO");
		table.put(60, "BAR");
		table.put(70, "BAZ");
		
		table.putAll(null);
	}

	@Test
	public void testClear() {
		table.put(50, "FOO");
		table.put(60, "BAR");
		table.put(70, "BAZ");
		
		assertEquals(3, table.size());
		table.clear();
		assertEquals(0, table.size());
		assertFalse(table.containsKey(50));
		assertFalse(table.containsKey(60));
		assertFalse(table.containsKey(70));
		assertFalse(table.containsValue("FOO"));
		assertFalse(table.containsValue("BAR"));
		assertFalse(table.containsValue("BAZ"));
	}

	@Test
	public void testKeySet() {
		table.put(50, "FOO");
		table.put(60, "BAR");
		table.put(70, "BAZ");
		
		Set<Integer> keySet = new HashSet<Integer>();
		keySet.add(50);
		keySet.add(60);
		keySet.add(70);
		
		assertTrue(keySet.equals(table.keySet()));

	}

	@Test
	public void testValues() {
		table.put(50, "FOO");
		table.put(60, "BAR");
		table.put(70, "BAZ");
		
		Collection<String> values = new ArrayList<String>();
		values.add("FOO");
		values.add("BAR");
		values.add("BAZ");
		
		assertTrue(values.equals(table.values()));
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testEntrySet() {
		table.entrySet();
	}

	@Test
	public void testEqualsObject() {
		table.put(0, "FOO");
		table.put(1, "FOO");
		table.put(2, "FOO");
		table.put(3, "FOO");
		table.put(4, "FOO");
		
		table2.put(0, "FOO");
		table2.put(1, "FOO");
		table2.put(2, "FOO");
		table2.put(3, "FOO");
		table2.put(4, "FOO");

		assertTrue(table.equals(table2));
		
		table2.put(5, "FOO");
		assertFalse(table.equals(table2));
		table.put(5, "FOO");
		table.put(6, "FOO");
		assertFalse(table.equals(table2));

	}

	@Test
	public void testGetBucket() {
		fail("Not yet implemented");
	}

}
