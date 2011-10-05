package util.collection.linkedStack;

/**
 * @author Christian Lesage
 * @author Alexandre Tremblay
 * 
 */
public class Node
{
	// Le noeud suivant du noeud courant
	private Node next;

	// Le contenu du noeud courant
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
		return this.value == null ? "" : value.toString();
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
	 * Établit le noeud suivant du noeud courant.
	 * 
	 * @param next le nouveau noeud suivant du noeud courant
	 */
	public void setNext(Node next)
	{
		this.next = next;
	}

	/**
	 * Retourne le contenu du noeud.
	 * 
	 * @return le contenu du noeud
	 */
	public Object getValue()
	{
		return value;
	}

	/**
	 * Établit le contenu du noeud.
	 * 
	 * @param value le nouveau contenu du noeud
	 */
	public void setValue(Object value)
	{
		this.value = value;
	}
	
}
