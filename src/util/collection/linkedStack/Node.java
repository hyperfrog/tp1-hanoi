package util.collection.linkedStack;

/**
 * @author Christian
 * 
 */
public class Node
{
	//
	private Node next;

	//
	private Object value;

	/**
	 * Construit un noeud.
	 */
	public Node()
	{
		this.next = null;
		this.value = null;
	}

	/**
	 * Retourne une représentation textuelle du noeud.
	 * 
	 * @return représentation textuelle du noeud
	 */
	public String toString()
	{
		if (this.value == null)
			return "";
		else
			return value.toString();
	}


	/**
	 * Retourne le noeud suivant s'il existe, sinon null.
	 * @return le noeud suivant s'il existe, sinon null 
	 */
	public Node getNext()
	{
		return next;
	}

	/**
	 * Établit le noeud suivant au noeud spécifié
	 * 
	 * @param next the next to set
	 */
	public void setNext(Node next)
	{
		this.next = next;
	}

	/**
	 * @return the value
	 */
	public Object getValue()
	{
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value)
	{
		this.value = value;
	}
	
}
