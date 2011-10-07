package appHanoi.model.test;

import java.awt.Graphics;

import org.junit.Test;
import org.junit.Assert;

import appHanoi.form.GameBoard;
import appHanoi.model.Game;

/**
 * La classe de test JUnit 4 de la classe Game.java
 * 
 * @author Christian Lesage
 * @author Alexandre Tremblay
 *
 */
public class GameTest
{
	/**
	 * 	M�thode de test pour {@link appHanoi.model.Game#Game()}
	 */
	@Test
	public void testGame()
	{
		// Cas valide 1 : Le nombre de disque est entre 3 et 64.
		Game g = new Game(5);
		Assert.assertNotNull(g);
		Assert.assertEquals(5, g.getNbDisks());
		Assert.assertFalse(g.isOver());
		
		// Cas limite 1 : Le nombre de disque est de 3.
		g = new Game(3);
		Assert.assertNotNull(g);
		Assert.assertEquals(3, g.getNbDisks());
		Assert.assertFalse(g.isOver());
		
		// Cas limite 2 : Le nombre de disque est de 64.
		g = new Game(64);
		Assert.assertNotNull(g);
		Assert.assertEquals(64, g.getNbDisks());
		Assert.assertFalse(g.isOver());
		
		// Cas invalide 1 : Le nombre de disque est inf�rieur � 3.
		g = new Game(2);
		Assert.assertNotNull(g);
		Assert.assertEquals(3, g.getNbDisks());
		Assert.assertFalse(g.isOver());
		
		// Cas invalide 1 : Le nombre de disque est sup�rieur � 64.
		g = new Game(65);
		Assert.assertNotNull(g);
		Assert.assertEquals(3, g.getNbDisks());
		Assert.assertFalse(g.isOver());
	}

	/**
	 * 	M�thode de test pour {@link appHanoi.model.Game#getNbDisks()}
	 */
	@Test
	public void testGetNbDisks()
	{
		// Cas valide 1 : Le nombre de disque est compris dans l'intervalle [3, 64].
		Game g = new Game(5);
		Assert.assertEquals(5, g.getNbDisks());
		
		// Cas limite 1 : Le nombre de disque est �gal � 3.
		g = new Game(3);
		Assert.assertEquals(3, g.getNbDisks());
		
		// Cas limite 2 : Le nombre de disque est �gal � 64.
		g = new Game(64);
		Assert.assertEquals(64, g.getNbDisks());
		
		// Cas invalide 1 : Le nombre de disque est inf�rieur � 3.
		g = new Game(2);
		Assert.assertEquals(3, g.getNbDisks());
		
		// Cas invalide 2 : Le nombre de disque est sup�rieur � 64.
		g = new Game(65);
		Assert.assertEquals(3, g.getNbDisks());
	}
	
	/**
	 * 	M�thode de test pour {@link appHanoi.model.Game#moveDisk(int, int)}
	 */
	@Test
	public void testMoveDiskIntInt()
	{
		// Cas valide 1 : La tour de d�part est diff�rente de celle d'arriv�,
		// la tour d'arriv� et de d�part est dans l'intervalle [0, 2], la 
		// tour de d�part n'est pas vide et le disque de la tour de d�part
		// est plus petit que celui situ� sur la tour d'arriv�.
		Game g = new Game(3);
		Assert.assertTrue(g.moveDisk(0, 2));
		
		// Cas invalide 1 : La tour de d�part est identique � celle d'arriv�.
		g = new Game(3);
		Assert.assertFalse(g.moveDisk(0, 0));
		
		// Cas invalide 2 : La tour de d�part est vide.
		g = new Game(3);
		Assert.assertFalse(g.moveDisk(2, 0));
		
		// Cas invalide 3 : La tour de d�part n'est pas dans l'intervalle [0, 2].
		g = new Game(3);
		Assert.assertFalse(g.moveDisk(42, 0));
		
		// Cas invalide 4 : La tour d'arriv� n'est pas dans l'intervalle [0, 2].
		g = new Game(3);
		Assert.assertFalse(g.moveDisk(0, 3));
		
		// Cas invalide 5 : Le disque sur la tour de d�part est plus grand que celui 
		// sur la tour d'arriv�
		g = new Game(3);
		g.moveDisk(0, 1);
		Assert.assertFalse(g.moveDisk(0, 1));
	}

	/**
	 * 	M�thode de test pour {@link appHanoi.model.Game#redraw(java.awt.Graphics)}
	 */
	@Test
	public void testRedraw() 
	{
		// Cas valide 1 : Si l'objet Graphics pass� n'est pas null, alors le programme
		// 					d�ssine les tours sur celui-ci et continu.
		GameBoard gb = new GameBoard();
		Graphics gra = gb.getGraphics(); 
		Game g = new Game(3);
		g.redraw(gra);
		
		// Cas invalide 1 : Si l'objet Graphics pass� est null, alors le programme
		// 					ne devrait pas plant� et continuer.
		g = new Game(3);
		g.redraw(null);
	}

	/**
	 * 	M�thode de test pour {@link appHanoi.model.Game#isOver()}
	 */
	@Test
	public void testIsOver()
	{
		// Cas 1 : La partie vient d'�tre cr�er, elle n'est donc pas termin�.
		Game g = new Game(3);
		Assert.assertFalse(g.isOver());
		
		// Cas 2 : La partie est en cours.
		g = new Game(3);
		g.moveDisk(0, 2);
		g.moveDisk(0, 1);
		g.moveDisk(2, 1);
		Assert.assertFalse(g.isOver());
		
		// Cas 3 : Tous les disques sont sur la tour 3, la partie est donc termin�.
		g = new Game(3);
		g.moveDisk(0, 2);
		g.moveDisk(0, 1);
		g.moveDisk(2, 1);
		g.moveDisk(0, 2);
		g.moveDisk(1, 0);
		g.moveDisk(1, 2);
		g.moveDisk(0, 2);
		Assert.assertTrue(g.isOver());
	}
}
