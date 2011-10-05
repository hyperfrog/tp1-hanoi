package util.collection.test;

import org.junit.Assert;
import org.junit.Test;

import util.collection.linkedStack.Node;

/**
 * Classe de test JUnit 4 pour la classe Node.java
 * 
 * @author Christian Lesage
 * @author Alexandre Tremblay
 *
 */

public class NodeTest
{
	/**
	 * 	Méthode de test pour {@link util.collection.linkedStack.Node#Node()}
	 */
	@Test
	public void testNode()
	{
		Node n = new Node();
		
		// Le constructeur produit un Node vide.
		Assert.assertNotNull(n);
		Assert.assertNull(n.getValue());
		Assert.assertNull(n.getNext());
	}

	/**
	 * Méthode de test pour {@link util.collection.linkedStack.Node#toString()}.
	 */
	@Test
	public void testToString()
	{
		Node n = new Node();
		String str = new String("Valeur");
		
		// Cas 1 : Le Node est un nouvel objet.
		Assert.assertEquals("", n.toString());
		
		// Cas 2 : La valeur n'est pas null.
		n.setValue(str);
		Assert.assertEquals(str, n.toString());
		
		// Cas 3 : La valeur est null.
		n.setValue(null);
		Assert.assertEquals("", n.toString());
	}

	/**
	 * Méthode de test pour {@link util.collection.linkedStack.Node#getNext()}.
	 */
	@Test
	public void testGetNext()
	{
		Node n1 = new Node();
		Node n2 = new Node();
		
		// Cas 1 : Le Node est un nouvel objet.
		Assert.assertNull(n1.getNext());
		
		// Cas 2 : Le Node suivant n'est pas null. 
		n1.setNext(n2);
		Assert.assertEquals(n2, n1.getNext());
		
		// Cas 3 : Le Node suivant null.
		n1.setNext(null);
		Assert.assertNull(n1.getNext());
	}

	/**
	 * Méthode de test pour {@link util.collection.linkedStack.Node#setNext(util.collection.linkedStack.Node)}.
	 */
	@Test
	public void testSetNext()
	{
		Node n1 = new Node();
		Node n2 = new Node();
		
		// Cas 1 : Le Node suivant est changé pour un objet qui n'est pas null. 
		n1.setNext(n2);
		Assert.assertEquals(n2, n1.getNext());
		
		// Cas 2 : Le Node suivant est changé pour un objet null.
		n1.setNext(null);
		Assert.assertNull(n1.getNext());
	}

	/**
	 * Méthode de test pour {@link util.collection.linkedStack.Node#getValue()}.
	 */
	@Test
	public void testGetValue()
	{
		Node n = new Node();
		Object obj1 = new Object();
		
		// Cas 1 : Le Node est un nouvel objet.
		Assert.assertNull(n.getValue());
		
		// Cas 2 : La valeur n'est pas null.
		n.setValue(obj1);
		Assert.assertEquals(obj1, n.getValue());
		
		// Cas 3 : La valeur est null.
		n.setValue(null);
		Assert.assertNull(n.getValue());
	}

	/**
	 * Méthode de test pour {@link util.collection.linkedStack.Node#setValue(java.lang.Object)}.
	 */
	@Test
	public void testSetValue()
	{
		Node n = new Node();
		Object obj1 = new Object();
		
		// Cas 2 : La valeur est changé pour un objet qui n'est pas null.
		n.setValue(obj1);
		Assert.assertEquals(obj1, n.getValue());
		
		// Cas 3 : La valeur est changé pour un objet null.
		n.setValue(null);
		Assert.assertNull(n.getValue());
	}

}
