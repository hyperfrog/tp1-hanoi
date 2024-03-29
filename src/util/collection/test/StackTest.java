package util.collection.test;

import org.junit.Test;
import org.junit.Assert;
import util.collection.arrayStack.ImplStack;
//import util.collection.linkedStack.ImplStack;
import util.collection.Stack;

/**
 * 
 * Classe de test JUnit 4 pour la classe ImplStack.java
 * 
 * @author Alexandre Tremblay
 * @author Christian Lesage
 * 
 */

public class StackTest
{

	/**
	 * M�thode de test pour {@link util.collection.linkedStack.ImplStack#ImplStack()} et {@link util.collection.arrayStack.ImplStack#ImplStack()}.
	 */	
	@Test
	public void testStack()
	{
		Stack stk = new ImplStack();
		
		// Le constructeur produit une pile vide. 
		Assert.assertNotNull(stk);
		Assert.assertTrue(stk.isEmpty());
	}

	/**
	 * M�thode de test pour {@link util.collection.linkedStack.ImplStack#push(java.lang.Object)} et {@link util.collection.arrayStack.ImplStack#push(java.lang.Object)}.
	 */	
	@Test
	public void testPushObject()
	{
		Stack stk = new ImplStack();
		Object obj1 = new Object();
		Object obj2 = new Object();

		// Cas valide 1 : On empile un objet non null sur une pile vide.
		Assert.assertEquals(obj1, stk.push(obj1));
		Assert.assertEquals(obj1, stk.peek());
		Assert.assertEquals(1, stk.size());

		// Cas valide 2 : On empile un objet non null sur une pile non vide.
		Assert.assertEquals(obj2, stk.push(obj2));
		Assert.assertEquals(obj2, stk.peek());
		Assert.assertEquals(2, stk.size());

		// Cas invalide 1 : On empile un objet null sur une pile vide.
		stk.clear();
		Assert.assertNull(stk.push(null));
		Assert.assertNull(stk.peek());
		Assert.assertEquals(0, stk.size());

		// Cas invalide 2 : On empile un objet null sur une pile non vide.
		stk.push(obj1);
		Assert.assertNull(stk.push(null));
		Assert.assertEquals(obj1, stk.peek());
		Assert.assertEquals(1, stk.size());
	}

	/**
	 * M�thode de test pour {@link util.collection.linkedStack.ImplStack#pop()} et {@link util.collection.arrayStack.ImplStack#pop()}.
	 */	
	@Test
	public void testPop()
	{
		Stack stk = new ImplStack();
		Object obj1 = new Object();
		Object obj2 = new Object();

		stk.push(obj1); // 2
		stk.push(obj2); // 1

		// Cas valide 1 : La pile contient deux objets.
		Assert.assertEquals(obj2, stk.pop());
		Assert.assertEquals(1, stk.size());

		// Cas valide 2 : La pile contient un objet.
		Assert.assertEquals(obj1, stk.pop());
		Assert.assertEquals(0, stk.size());

		// Cas invalide : La pile est vide.
		Assert.assertNull(stk.pop());
		Assert.assertEquals(0, stk.size());

	}

	/**
	 * M�thode de test pour {@link util.collection.linkedStack.ImplStack#size()} et {@link util.collection.arrayStack.ImplStack#size()}.
	 */	
	@Test
	public void testSize()
	{
		Stack stk = new ImplStack();

		// Cas 1 : Aucun �l�ment dans la pile
		Assert.assertEquals(0, stk.size());

		// Cas 2 : Ajout d'un �l�ment (pile vide)
		stk.push(new Object());
		Assert.assertEquals(1, stk.size());

		// Cas 3 : Ajout d'un �l�ment (pile contient 1 �l�ment)
		stk.push(new Object());
		Assert.assertEquals(2, stk.size());
		
		// Cas 4 : suppression d'un �l�ment (pile contient 2 �l�ments)
		stk.pop();
		Assert.assertEquals(1, stk.size());

		// Cas 5 : suppression d'un �l�ment (pile contient 1 �l�ment)
		stk.pop();
		Assert.assertEquals(0, stk.size());

		// Cas 5 : suppression d'un �l�ment (pile vide)
		stk.pop();
		Assert.assertEquals(0, stk.size());
	}

	/**
	 * M�thode de test pour {@link util.collection.linkedStack.ImplStack#isEmpty()} et {@link util.collection.arrayStack.ImplStack#isEmpty()}.
	 */	
	@Test
	public void testIsEmpty()
	{
		Stack stk = new ImplStack();

		// Cas 1 : La liste est vide.
		Assert.assertTrue(stk.isEmpty());

		// Cas 2 : La liste n'est pas vide.
		stk.push(new Object());
		Assert.assertFalse(stk.isEmpty());
	}

	/**
	 * M�thode de test pour {@link util.collection.linkedStack.ImplStack#clear()} et {@link util.collection.arrayStack.ImplStack#clear()}.
	 */	
	@Test
	public void testClear()
	{
		Stack stk = new ImplStack();

		// Une pile non vide est vid�e par un clear().
		stk.push(new Object());
		Assert.assertFalse(stk.isEmpty());
		stk.clear();
		Assert.assertTrue(stk.isEmpty());
	}

	/**
	 * M�thode de test pour {@link util.collection.linkedStack.ImplStack#peek()} et {@link util.collection.arrayStack.ImplStack#peek()}.
	 */	
	@Test
	public void testPeek()
	{
		Stack stk = new ImplStack();
		Object obj = new Object();

		// Cas 1 : La liste est vide.
		Assert.assertNull(stk.peek());

		stk.push(obj);
		// Cas 2 : La liste n'est pas vide. 
		// On v�rifie qu'elle n'est pas modifi�e par un peek() et que deux peek() cons�cutifs donnent le m�me r�sultat. 
		Assert.assertEquals(obj, stk.peek());
		Assert.assertEquals(obj, stk.peek());
		Assert.assertEquals(1, stk.size());
	}

	/**
	 * M�thode de test pour {@link util.collection.linkedStack.ImplStack#search(java.lang.Object)} et {@link util.collection.arrayStack.ImplStack#search(java.lang.Object)}.
	 */	
	@Test
	public void testSearchObject()
	{
		Stack stk = new ImplStack();
		Object obj1 = new Object();
		Object obj2 = new Object();

		// Cas 1 : L'objet n'existe pas dans la pile
		Assert.assertEquals(Stack.NULL_POSITION, stk.search(obj1));

		stk.push(obj1); // 3
		stk.push(obj2); // 2
		stk.push(obj1); // 1

		// Cas 2 : Il y a une seule instance de l'objet dans la pile (pos. 2)
		Assert.assertEquals(2, stk.search(obj2));

		// Cas 3 : Il y a plusieurs instances de l'objet dans la pile (pos. 1 et 3)
		// La m�thode retourne l'instance la plus proche du dessus de la pile.
		Assert.assertEquals(1, stk.search(obj1));
	}

	/**
	 * M�thode de test pour {@link util.collection.linkedStack.ImplStack#get(int)} e {@link util.collection.arrayStack.ImplStack#get(int)}.
	 */	
	@Test
	public void testGetInt()
	{
		Stack stk = new ImplStack();
		Object obj1 = new Object();
		Object obj2 = new Object();
		Object obj3 = new Object();

		stk.push(obj1); // 3
		stk.push(obj2); // 2
		stk.push(obj3); // 1

		// Cas standard : 1 < Position < Size
		Assert.assertEquals(obj2, stk.get(2));

		// Cas limite 1 : Position == Size
		Assert.assertEquals(obj1, stk.get(3));
		
		// Cas limite 2 : Position == 1
		Assert.assertEquals(obj3, stk.get(1));

		// Cas invalide 1 : Position < 1
		Assert.assertNull(stk.get(0));

		// Cas invalide 2 : Position > Size
		Assert.assertNull(stk.get(4));
	}

	/**
	 * M�thode de test pour {@link util.collection.linkedStack.ImplStack#toString()} et {@link util.collection.arrayStack.ImplStack#toString()}.
	 */	
	@Test
	public void testToString()
	{
		Stack stk = new ImplStack();
		
		// Cas 1 : La pile est vide.
		Assert.assertEquals("[]", stk.toString());
		
		// Cas 2 : La pile contient un �l�ment
		stk.push(new String("premier"));
		Assert.assertEquals("[premier]", stk.toString());
		
		// Cas 2 : La pile contient deux �l�ments
		stk.push(new String("deuxi�me"));
		Assert.assertEquals("[deuxi�me, premier]", stk.toString());
	}
}
