package util.collection.arrayStack;

import util.collection.Stack;

/**
 * Implémentation de la classe Stack par un tableau.
 * 
 * Il s'agit d'une pile d'objets de type « dernier entré, premier sorti » (LIFO). 
 * Elle n'accepte pas d'éléments marqués « null ».
 * 
 * @author Christian Lesage
 * @author Alexandre Tremblay
 * 
 */
public class ImplStack extends Stack
{
	// Taille par défaut du tableau et valeur utilisée lors d'un agrandissement.
	private final static int TABLE_SIZE = 5;
	
	// Taille de la pile.
	private int size;
	
	// Tableau d'objet
	private Object[] table;
	
	/**
	 * Construit une pile vide.
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
		this.table = new Object[ImplStack.TABLE_SIZE];
		this.size = 0;
	}

	/* (non-Javadoc)
	 * @see util.collection.Stack#get(int)
	 */
	@Override
	public Object get(int position)
	{
		Object o = null;
		
		if (position >= 1 && position <= this.size())
		{
			o = this.table[this.size() - position];
		}
		
		return o;
	}

	/* (non-Javadoc)
	 * @see util.collection.Stack#isEmpty()
	 */
	@Override
	public boolean isEmpty()
	{
		return (this.size() == 0);
	}

	/*
	 * Agrandit le tableau de la pile de « TABLE_SIZE » unités.
	 */
	private void resize() {
		Object[] newTable = new Object[this.table.length + ImplStack.TABLE_SIZE];
		
		for (int i = 0; i < this.size(); i++)
		{
			newTable[i] = this.table[i];
		}
		
		this.table = newTable;
	}
	
	/*
	 * Retourne true si le tableau de la pile est plein, sinon false.
	 */
	private boolean isFull() {
		return (this.size() >= this.table.length);
	}
	
	/* (non-Javadoc)
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
		
		if (!this.isEmpty())
		{
			o = this.peek();
			this.size--;
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
			//Si le tableau est plein, on redimensionne
			if (isFull())
			{
				resize();
			}
			
			//Ajoute l'élément au tableau
			this.table[this.size] = obj;
			this.size++;
		}
			
		return obj;
	}
	
	/* (non-Javadoc)
	 * @see util.collection.Stack#search(java.lang.Object)
	 */
	@Override
	public int search(Object obj)
	{
		int pos = Stack.NULL_POSITION;

		if (obj != null) 
		{
			// Vérifie chacun des éléments de la pile.
			for (int i = 1; i <= this.size; i++)
			{
				// Si l'objet recherché est égal à celui courant en i
				if (obj.equals(this.get(i)))
				{
					pos = i;
					break;
				}
			}
		}

		return pos;
	}

	/* (non-Javadoc)
	 * @see util.collection.Stack#size()
	 */
	@Override
	public int size()
	{
		return this.size;
	}

	/* (non-Javadoc)
	 * @see util.collection.Stack#toString()
	 */
	@Override
	public String toString()
	{
		String s = new String();

		for (int i = this.size - 1; i >= 0; i--)
		{
			s += this.table[i].toString();

			if (i > 0)
			{
				s += ", ";
			}
		}

		return "[" + s + "]";
	}

}
