package util.collection.test;

import org.junit.Test;
import org.junit.Assert;
import util.collection.linkedStack.ImplStack;
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

	@Test
	public void testStack()
	{
		Stack stk = new ImplStack();
		
		// Le constructeur produit une pile vide. 
		Assert.assertNotNull(stk);
		Assert.assertTrue(stk.isEmpty());
	}

	@Test
	public void testPushObject()
	{
		Stack stk = new ImplStack();
		Object obj1 = new Object();
		Object obj2 = new Object();

		// Cas valide 1 : On empile un objet non null sur une pile vide.
		Assert.assertEquals(obj1, stk.push(obj1));
		Assert.assertEquals(obj1, stk.peek());

		// Cas valide 2 : On empile un objet non null sur une pile non vide.
		Assert.assertEquals(obj2, stk.push(obj2));
		Assert.assertEquals(obj2, stk.peek());

		// Cas invalide 1 : On empile un objet null sur une pile vide.
		stk.clear();
		Assert.assertNull(stk.push(null));
		Assert.assertNull(stk.peek());

		// Cas invalide 2 : On empile un objet null sur une pile non vide.
		stk.push(obj1);
		Assert.assertNull(stk.push(null));
		Assert.assertEquals(obj1, stk.peek());
	}

	@Test
	public void testPop()
	{
		Stack stk = new ImplStack();
		Object obj1 = new Object();
		Object obj2 = new Object();

		stk.push(obj1); // 2
		stk.push(obj2); // 1

		// Cas valide 1 : La pile contient plusieurs objets.
		Assert.assertEquals(obj2, stk.pop());

		// Cas valide 2 : La pile contient un objet.
		Assert.assertEquals(obj1, stk.pop());

		// Cas invalide : La pile est vide.
		Assert.assertNull(stk.pop());

	}

	@Test
	public void testSize()
	{
		Stack stk = new ImplStack();

		// Cas 1 : Aucun élément dans la pile
		Assert.assertEquals(0, stk.size());

		// Cas 2 : 1 élément dans la pile
		stk.push(new Object());
		Assert.assertEquals(1, stk.size());

		// Cas 3 : 2 éléments dans la pile
		stk.push(new Object());
		Assert.assertEquals(2, stk.size());

	}

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

	@Test
	public void testClear()
	{
		Stack stk = new ImplStack();

		// Une pile non vide est vidée par un clear().
		stk.push(new Object());
		Assert.assertFalse(stk.isEmpty());
		stk.clear();
		Assert.assertTrue(stk.isEmpty());
	}

	@Test
	public void testPeek()
	{
		Stack stk = new ImplStack();
		Object obj = new Object();

		// Cas 1 : La liste est vide.
		Assert.assertNull(stk.peek());

		stk.push(obj);
		// Cas 2 : La liste n'est pas vide. 
		// On vérifie qu'elle n'est pas modifiée par un peek() et que deux peek() consécutifs donnent le même résultat. 
		Assert.assertEquals(obj, stk.peek());
		Assert.assertEquals(obj, stk.peek());
		Assert.assertEquals(1, stk.size());
	}

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
		// La méthode retourne l'instance la plus proche du dessus de la pile.
		Assert.assertEquals(1, stk.search(obj1));
	}

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

}
