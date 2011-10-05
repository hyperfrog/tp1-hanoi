/**
 * 
 */
package appHanoi.model.test;


import org.junit.Assert;
import org.junit.Test;
import java.awt.Color;
import appHanoi.model.*;

/**
 *  Classe de test JUnit 4 pour la classe Tower.java
 * 
 * @author Christian Lesage
 * @author Alexandre Tremblay
 *
 */
public class TowerTest
{

	/**
	 * Méthode de test pour {@link appHanoi.model.Tower#Tower()}.
	 */
	@Test
	public void testTower()
	{
		Tower t = new Tower();
		
		// Le constructeur produit une tour vide. 
		Assert.assertNotNull(t);
		Assert.assertTrue(t.isEmpty());
	}
	
	/**
	 * Méthode de test pour {@link appHanoi.model.Tower#push(java.lang.Object)}.
	 */
	@Test
	public void testPushObject()
	{
		Tower t = new Tower();
		Object o1 = new Disk(1, new Color(255, 0, 0));
		Object o2 = new Disk(2, new Color(0, 255, 0));
		Object o3 = new Disk(1, new Color(0, 0, 255));
		Object o4 = new Object();

		// Cas valide 1 : On empile un disque sur une tour vide.
		Assert.assertEquals(o2, t.push(o2));
		Assert.assertEquals(o2, t.peek());
		Assert.assertEquals(1, t.size());

		// Cas valide 2 : On empile un disque de plus petit diamètre sur une tour non vide.
		Assert.assertEquals(o1, t.push(o1));
		Assert.assertEquals(o1, t.peek());
		Assert.assertEquals(2, t.size());

		// Cas invalide 1 : On empile un objet null sur une tour vide.
		t.clear();
		Assert.assertNull(t.push(null));
		Assert.assertNull(t.peek());
		Assert.assertEquals(0, t.size());

		// Cas invalide 2 : On empile un objet qui n'est pas un disque sur une tour vide.
		Assert.assertNull(t.push(o4));
		Assert.assertNull(t.peek());
		Assert.assertEquals(0, t.size());
		
		// Cas invalide 3 : On empile un objet null sur une tour non vide.
		t.push(o1);
		Assert.assertNull(t.push(null));
		Assert.assertEquals(o1, t.peek());
		Assert.assertEquals(1, t.size());
		
		// Cas invalide 4 : On empile un object qui n'est pas un Disk sur une tour non vide.
		Assert.assertNull(t.push(o4));
		Assert.assertEquals(o1, t.peek());
		Assert.assertEquals(1, t.size());
		
		// Cas invalide 5 : On empile un disque de même diamètre sur une tour non vide.
		Assert.assertNull(t.push(o3));
		Assert.assertEquals(o1, t.peek());
		Assert.assertEquals(1, t.size());
		
		// Cas invalide 6 : On empile un disque de plus grand diamètre sur une tour non vide.
		Assert.assertNull(t.push(o2));
		Assert.assertEquals(o1, t.peek());
		Assert.assertEquals(1, t.size());
		
	}

	/**
	 * Méthode de test pour {@link appHanoi.model.Tower#push(appHanoi.model.Disk)}.
	 */
	@Test
	public void testPushDisk()
	{
		Tower t = new Tower();
		Disk d1 = new Disk(1, new Color(255, 0, 0));
		Disk d2 = new Disk(2, new Color(0, 255, 0));
		Disk d3 = new Disk(1, new Color(0, 0, 255));

		// Cas valide 1 : On empile un disque sur une tour vide.
		Assert.assertEquals(d2, t.push(d2));
		Assert.assertEquals(d2, t.peek());
		Assert.assertEquals(1, t.size());

		// Cas valide 2 : On empile un disque de plus petit diamètre sur une tour non vide.
		Assert.assertEquals(d1, t.push(d1));
		Assert.assertEquals(d1, t.peek());
		Assert.assertEquals(2, t.size());

		// Cas invalide 1 : On empile un disque null sur une tour vide.
		t.clear();
		Assert.assertNull(t.push(null));
		Assert.assertNull(t.peek());
		Assert.assertEquals(0, t.size());

		// Cas invalide 2 : On empile un disque null sur une tour non vide.
		t.push(d1);
		Assert.assertNull(t.push(null));
		Assert.assertEquals(d1, t.peek());
		Assert.assertEquals(1, t.size());
		
		// Cas invalide 3 : On empile un disque de même diamètre sur une tour non vide.
		Assert.assertNull(t.push(d3));
		Assert.assertEquals(d1, t.peek());
		Assert.assertEquals(1, t.size());

		// Cas invalide 4 : On empile un disque de plus grand diamètre sur une tour non vide.
		Assert.assertNull(t.push(d2));
		Assert.assertEquals(d1, t.peek());
		Assert.assertEquals(1, t.size());
		
	}

	/**
	 * Méthode de test pour {@link appHanoi.model.Tower#pop()}.
	 */
	@Test
	public void testPop()
	{
		Tower t = new Tower();
		Object d1 = new Disk(1, new Color(255, 0, 0));
		Object d2 = new Disk(2, new Color(255, 0, 0));

		t.push(d2); // position 2, diamètre 2
		t.push(d1); // position 1, diamètre 1

		// Cas valide 1 : La pile contient deux objets.
		Assert.assertEquals(d1, t.pop());
		Assert.assertEquals(1, t.size());

		// Cas valide 2 : La pile contient un objet.
		Assert.assertEquals(d2, t.pop());
		Assert.assertEquals(0, t.size());

		// Cas invalide : La pile est vide.
		Assert.assertNull(t.pop());
		Assert.assertEquals(0, t.size());		
	}
	
	/**
	 * Méthode de test pour {@link appHanoi.model.Tower#redraw(java.awt.Graphics, int)}.
	 * 
	 * Il n'y a rien à tester, car la méthode ne retourne rien et ne change pas l'état de l'objet.
	 */
	@Test
	public void testRedrawGraphicsInt()
	{
	}

}
