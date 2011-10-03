/**
 * 
 */
package util.collection.linkedStack;

import util.collection.Stack;

/**
 * Impl�mentation de la classe Stack par une liste cha�n�e.
 * 
 * Il s'agit d'une pile d'objets de type �dernier entr�, premier sorti) (LIFO) 
 * Elle n'accpete pas d'�l�ments marqu�s �null�.
 * 
 * @author Christian Lesage
 * @author Alexandre Tremblay
 * 
 */
public class ImplStack extends Stack
{

	// Taille de la pile
	private int size;

	// Noeud t�te de la pile
	private Node head;

	/**
	 * Construit une pile vide.
	 */
	public ImplStack()
	{
		this.clear();
	}
	
    /* (non-Javadoc)
     * 
	 * @see util.collection.Stack#clear()
	 */
	@Override
	public void clear()
	{
		this.head = null;
		this.size = 0;
	}

	// Retourne le noeud � la position sp�cifi�e ou null si la position n'est pas valide
	// Pour �tre valide, la position doit �tre comprise dans l'intervalle [1, size] 
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
	 * 
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
     * 
	 * @see util.collection.Stack#isEmpty()
	 */
	@Override
	public boolean isEmpty()
	{
		return this.size == 0;
	}

    /* (non-Javadoc)
     * 
	 * @see util.collection.Stack#peek()
	 */
	@Override
	public Object peek()
	{
		return this.get(1);
	}

    /* (non-Javadoc)
     * 
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
     * 
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

	/*(non-Javadoc)
	 * 
	 * @see util.collection.Stack#search(java.lang.Object)
	 */
	@Override
	public int search(Object obj)
	{
		int position = Stack.NULL_POSITION;
		
		if (obj != null) 
		{
			Node n = this.head;
			
			// Scrute chacun des �l�ments de la pile en commen�ant par le dessus
			for (int i = 1; i <= this.size && n != null; i++)
			{
				// Si l'objet recherch� est �gal � celui � la position i 
				if (obj.equals(n.getValue()))
				{
					position = i;
					break;
				}
				n = n.getNext();
			}
		}
		return position;
	}

    /* (non-Javadoc)
     * 
	 * @see util.collection.Stack#size()
	 */
	@Override
	public int size()
	{
		return this.size;
	}

	/* (non-Javadoc)
	 * 
	 * @see util.collection.Stack#toString()
	 */
	@Override
	public String toString()
	{
		Node n = head;
		
		String s = new String();

		for (int i = 1; i <= this.size; i++)
		{
			s += n.toString();
			if (i <= size - 1)
			{
				s += ", ";
			}
			n = n.getNext();
		}

		return "[" + s + "]";
	}

}
