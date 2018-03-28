/**
 * 
 */
package textgen;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

/**
 * Implementation of a linked list tester for a linked list implemented WITHOUT
 * sentinel nodes. 
 * 
 * This tester leverages knowledge about how the linked list was
 * implemented to perform more thorough tests.
 * 
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTesterWhiteBox
{

	private static final int LONG_LIST_LENGTH = 10;

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		// Feel free to use these lists, or add your own
		shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);

	}

	/**
	 * Test if the get method is working correctly.
	 */
	/*
	 * You should not need to add much to this method. We provide it as an example
	 * of a thorough test.
	 */
	@Test
	public void testGet()
	{
		// test empty list, get should throw an exception
		try
		{
			emptyList.get(0);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e)
		{

		}

		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));

		try
		{
			shortList.get(-1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e)
		{

		}
		try
		{
			shortList.get(2);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e)
		{

		}
		// test longer list contents
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			assertEquals("Check " + i + " element", (Integer) i, longerList.get(i));
		}

		// test off the end of the longer array
		try
		{
			longerList.get(-1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e)
		{

		}
		try
		{
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e)
		{
		}

	}

	/**
	 * Test removing an element from the list. We've included the example from the
	 * concept challenge. You will want to add more tests.
	 */
	@Test
	public void testRemove()
	{
		try
		{
			emptyList.remove(0);
			fail("Check Bounds");
		} catch (IndexOutOfBoundsException e)
		{
		}

		try
		{
			shortList.remove(-1);
			fail("Chech Bounds");
		} catch (IndexOutOfBoundsException e)
		{
		}

		try
		{
			shortList.remove(shortList.size());
			fail("Chech Bounds");
		} catch (IndexOutOfBoundsException e)
		{
		}

		int next = list1.get(2), a = list1.get(1);
		assertEquals("Remove: check removed element is correct ", (Integer) a, list1.remove(1));
		assertEquals("Remove: check new element is correct ", (Integer) next, list1.get(1));
		assertEquals("Remove: check size is correct ", 2, list1.size());

		a = longerList.get(0);
		next = longerList.head.next.data;
		assertEquals("Check removing front element", (Integer) a, longerList.remove(0));
		assertEquals("Check head after removing front element", (Integer) next, longerList.head.data);

		String b = shortList.get(shortList.size - 1), prev = shortList.tail.prev.data;
		assertEquals("Check removing back element", b, shortList.remove(shortList.size - 1));
		assertEquals("Check tail after removing back element", prev, shortList.tail.data);
		assertEquals("Reduce list to 1 element. Check head and tail point to same node", shortList.head,
				shortList.tail);

		shortList.remove(0);
		assertEquals("Check size after removing last element", 0, shortList.size());
		assertEquals("Check head after removing last element", null, shortList.head);
		assertEquals("Check tail after removing last element", null, shortList.tail);
	}

	/**
	 * Test adding an element into the end of the list, specifically public boolean
	 * add(E element)
	 */
	@Test
	public void testAddEnd()
	{
		try
		{
			emptyList.add(null);
			fail("Inserted null object");
		} catch (NullPointerException e)
		{
		}

		assertEquals("Check size of empty list", 0, emptyList.size);
		assertTrue(emptyList.add(15));
		assertEquals("Check size after appending element to empty list", 1, emptyList.size);
		assertEquals("Check last == first element in size 1 list", emptyList.head.data, emptyList.tail.data);

		shortList.add("C");
		assertEquals("Check size after appending to non-empty list", 3, shortList.size);
		assertEquals("Check last element after appending to non-empty list", "C", shortList.get(shortList.size - 1));

	}

	/** Test the size of the list */
	@Test
	public void testSize()
	{
		assertEquals("Check size of empty list", 0, emptyList.size());
		assertEquals("Check size of non-empty list", LONG_LIST_LENGTH, longerList.size());

		emptyList.add(15);
		assertEquals("Check size after appending to empty list", 1, emptyList.size());

		longerList.remove(0);
		assertEquals("Check size after removing from non-empty list", LONG_LIST_LENGTH - 1, longerList.size());

		longerList.add(5, 5);
		assertEquals("Check size after inserting in non-empty list", LONG_LIST_LENGTH, longerList.size());

		longerList.remove(3);
		longerList.remove(4);
		longerList.add(3);
		longerList.remove(6);
		longerList.add(6, 3);
		longerList.add(3, 6);
		longerList.add(5, 9);
		assertEquals("Check size after variouls insertions and deletions", LONG_LIST_LENGTH + 1, longerList.size());

	}

	/**
	 * Test adding an element into the list at a specified index, specifically:
	 * public void add(int index, E element)
	 */
	@Test
	public void testAddAtIndex()
	{
		try
		{
			emptyList.add(1, 2);
			fail("Check bounds");
		} catch (IndexOutOfBoundsException e)
		{
		}

		try
		{
			longerList.add(-1, -1);
			fail("Check bounds");
		} catch (IndexOutOfBoundsException e)
		{
		}

		try
		{
			list1.add(2, null);
		} catch (NullPointerException e)
		{
		}

		emptyList.add(0, 2);
		assertEquals("Adding to empty list: head and tail should point to same node", emptyList.head, emptyList.tail);

		shortList.add(0, "1");
		assertEquals("Check head after adding to front of non-empty list", "1", shortList.head.data);

		list1.add(list1.size(), 4);
		assertEquals("Check tail after adding to end of non-empty list", (Integer) 4, list1.tail.data);

		int previous = longerList.get(6), next = longerList.get(7);
		longerList.add(7, 88);
		assertEquals("Check index after inserting there", (Integer) 88, longerList.get(7));
		assertEquals("Check prececessor of previous index", (Integer) previous, longerList.get(7 - 1));
		assertEquals("Check successor of previous index", (Integer) next, longerList.get(7 + 1));
		assertEquals("Check size after inserting", LONG_LIST_LENGTH + 1, longerList.size);
	}

	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
		try
		{
			emptyList.set(0, 2);
			fail("Check bounds");
		} catch (IndexOutOfBoundsException e)
		{
		}

		try
		{
			shortList.set(2, "C");
			fail("Check bounds");
		} catch (IndexOutOfBoundsException e)
		{
		}

		try
		{
			list1.set(1, null);
			fail("Check bounds");
		} catch (NullPointerException e)
		{
		}

		shortList.set(0, "1");
		assertEquals("Check setting front element", "1", shortList.head.data);

		list1.set(list1.size - 1, 88);
		assertEquals("Check setting back element", (Integer) 88, list1.tail.data);

		longerList.set(5, 3843);
		int prev = longerList.get(3);
		assertEquals("Check setting element at index 5", (Integer) 3843, longerList.get(5));
		assertEquals("Check return value after setting element", (Integer) prev, longerList.set(3, 45));
	}

	// TODO: Optionally add more test methods.

}
