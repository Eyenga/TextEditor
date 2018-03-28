package textgen;

import java.util.AbstractList;

/**
 * A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E>
 *            The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E>
{
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList()
	{
		this.head = null;
		this.tail = head;
		this.size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * 
	 * @param element
	 *            The element to add
	 */
	public boolean add(E element) throws NullPointerException, IndexOutOfBoundsException
	{
		this.add(size, element);
		return true;
	}

	/**
	 * Get the element at position index
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds.
	 */
	public E get(int index) throws IndexOutOfBoundsException
	{
		if (index < 0 || index >= size) { throw new IndexOutOfBoundsException(); }

		LLNode<E> ref_node = findNode(index);
		return ref_node.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * 
	 * @param The
	 *            index where the element should be added
	 * @param element
	 *            The element to add
	 */
	public void add(int index, E element)throws NullPointerException, IndexOutOfBoundsException
	{
		if (element == null) { throw new NullPointerException("Adding 'null' element to list is not allowed"); }
		if (index < 0 || index > size) { throw new IndexOutOfBoundsException(); }

		LLNode<E> newNode = new LLNode<E>(element);

		// If The list is empty: update head and tail
		if (size == 0)
		{
			head = newNode;
			tail = head;
		} else if (index == 0) // List is not empty: Inserting in the front, update head.
		{
			newNode.next = head;
			head.prev = newNode;
			head = newNode;
		} else if (index == size) // List is not empty: Inserting in the back, updated tail
		{
			newNode.prev = tail;
			tail.next = newNode;
			tail = newNode;
		} else // List is not empty: Inserting in within bounds, update relevant nodes
		{
			LLNode<E> ref_node = findNode(index);

			newNode.prev = ref_node.prev;
			newNode.next = ref_node;
			ref_node.prev.next = newNode;
			ref_node.prev = newNode;
		}

		size++;
	}

	/** Return the size of the list */
	public int size()
	{
		return this.size;
	}

	/**
	 * Remove a node at the specified index and return its data element.
	 * 
	 * @param index
	 *            The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException
	 *             If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) throws IndexOutOfBoundsException
	{
		if (index < 0 || index >= size) { throw new IndexOutOfBoundsException(); }
		
		E data;
		if (size == 1) // Remove only element: update head and tail
		{
			data = head.data;
			head = null;
			tail = head;
		} else if (index == 0) // Remove front element: update head
		{
			data = head.data;
			head = head.next;
			head.prev = null;
		} else if (index == size - 1) // Remove back element: update tail
		{
			data = tail.data;
			tail = tail.prev;
			tail.next = null;
		} else // Remove within bounds; update relevant nodes
		{
			LLNode<E> ref_node = findNode(index);
			
			data = ref_node.data;
			ref_node.prev.next = ref_node.next;
			ref_node.next.prev = ref_node.prev;
		}
		
		size--;
		return data;
	}

	/**
	 * Set an index position in the list to a new element
	 * 
	 * @param index
	 *            The index of the element to change
	 * @param element
	 *            The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds.
	 */
	public E set(int index, E element) throws NullPointerException, IndexOutOfBoundsException
	{
		if (element == null) { throw new NullPointerException("Adding 'null' element to list is not allowed"); }
		if (index < 0 || index >= size) { throw new IndexOutOfBoundsException(); }
		
		LLNode<E> ref_node = findNode(index);
		E old_data = ref_node.data;
		ref_node.data = element;
		
		return old_data;
	}

	public String toString()
	{
		StringBuilder output = new StringBuilder();

		for (LLNode<E> ref_node = head; ref_node != null; ref_node = ref_node.next)
		{
			output.append(ref_node.toString() + " ");
		}

		return output.toString();
	}

	private LLNode<E> findNode(int index)
	{
		LLNode<E> ref_node;
		int i, direction;
		if (index < size / 2)
		{
			ref_node = head;
			i = 0;
			direction = 1;
		} else
		{
			ref_node = tail;
			i = size - 1;
			direction = -1;
		}

		while (i != index)
		{
			ref_node = (direction == 1) ? ref_node.next : ref_node.prev;
			i += direction;
		}

		return ref_node;
	}
}

class LLNode<E>
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	public LLNode(E data)
	{
		this.data = data;
		this.prev = null;
		this.next = null;
	}

	public LLNode(E data, LLNode<E> prev, LLNode<E> next)
	{
		this.data = data;
		this.prev = prev;
		this.next = next;
	}

	@Override
	public String toString()
	{
		if (data != null) { return data.toString(); }

		throw new NullPointerException();
	}

}
