/**
 * 
 */
package util.collection.linkedStack;

import util.collection.Stack;

/**
 * @author Christian
 * 
 */
public class ImplStack extends Stack
{

	// Taille de la pile
	private int size;

	// Noeud tête de la pile
	private Node head;

	/**
	 * Construit une pile.
	 */
	public ImplStack()
	{
		this.clear();
	}
	
    /* (non-Javadoc)
	 * @see util.collection.Stack#clear()
	 */
	@Override
	public void clear()
	{
		this.head = null;
		this.size = 0;
	}

	//
	private Node getNode(int position)
	{
		Node n = null;
		if (position >= 1 && position <= this.size)
		{
			n = this.head;

			for (int i = 1; i < position; i++)
			{
				n = n.getNext();
			}
		}
		
		return n;
	}

	/* (non-Javadoc)
	 * @see util.collection.Stack#get(int)
	 */
	@Override
	public Object get(int position)
	{
		Object o = null;

		Node n = getNode(position);
		
		if (n != null)
		{
			o = n.getValue();
		}
		return o;
	}

    /* (non-Javadoc)
	 * @see util.collection.Stack#isEmpty()
	 */
	@Override
	public boolean isEmpty()
	{
		return this.size == 0;
	}

	/**
	 * 
	 * @see util.collection.Stack#peek()
	 */
	@Override
	public Object peek()
	{
		return this.get(1);
	}

    /* (non-Javadoc)
	 * @see util.collection.Stack#pop()
	 */
	@Override
	public Object pop()
	{
		Object o = null;
		
		if (this.size > 0 && this.head != null)
		{
			o = head.getValue();
			head = head.getNext();
			size--;
		}
		
		return o;
	}

    /* (non-Javadoc)
	 * @see util.collection.Stack#push(java.lang.Object)
	 */
	@Override
	public Object push(Object obj)
	{
		if (obj != null)
		{
			Node n = new Node();
			n.setValue(obj);

			if (this.size > 0)
			{
				n.setNext(this.head);
			}

			this.head = n;
			this.size++;
		}

		return obj;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see util.collection.Stack#search(java.lang.Object)
	 */
	@Override
	public int search(Object obj)
	{
		int position = Stack.NULL_POSITION;

		if (obj != null)
		{
			for (int i = 1; i <= this.size; i++)
			{
				if (obj.equals(this.get(i)))
				{
					position = i;
					break;
				}
			}
		}
		return position;
	}

    /* 
     * (non-Javadoc)
	 * @see util.collection.Stack#size()
	 */
	@Override
	public int size()
	{
		return this.size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see util.collection.Stack#toString()
	 */
	@Override
	public String toString()
	{
		Node n = head;
		
		String s = "[";

		for (int i = 1; i <= this.size; i++)
		{
			s += n.toString();
			if (i <= size - 1)
			{
				s += ", ";
			}
			n = n.getNext();
		}

		s += "]";

		return s;
	}

}
