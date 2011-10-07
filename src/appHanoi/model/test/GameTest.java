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
	 * 	Méthode de test pour {@link appHanoi.model.Game#Game()}
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
		
		// Cas invalide 1 : Le nombre de disque est inférieur à 3.
		g = new Game(2);
		Assert.assertNotNull(g);
		Assert.assertEquals(3, g.getNbDisks());
		Assert.assertFalse(g.isOver());
		
		// Cas invalide 1 : Le nombre de disque est supérieur à 64.
		g = new Game(65);
		Assert.assertNotNull(g);
		Assert.assertEquals(3, g.getNbDisks());
		Assert.assertFalse(g.isOver());
	}

	/**
	 * 	Méthode de test pour {@link appHanoi.model.Game#getNbDisks()}
	 */
	@Test
	public void testGetNbDisks()
	{
		// Cas valide 1 : Le nombre de disque est compris dans l'intervalle [3, 64].
		Game g = new Game(5);
		Assert.assertEquals(5, g.getNbDisks());
		
		// Cas limite 1 : Le nombre de disque est égal à 3.
		g = new Game(3);
		Assert.assertEquals(3, g.getNbDisks());
		
		// Cas limite 2 : Le nombre de disque est égal à 64.
		g = new Game(64);
		Assert.assertEquals(64, g.getNbDisks());
		
		// Cas invalide 1 : Le nombre de disque est inférieur à 3.
		g = new Game(2);
		Assert.assertEquals(3, g.getNbDisks());
		
		// Cas invalide 2 : Le nombre de disque est supérieur à 64.
		g = new Game(65);
		Assert.assertEquals(3, g.getNbDisks());
	}
	
	/**
	 * 	Méthode de test pour {@link appHanoi.model.Game#moveDisk(int, int)}
	 */
	@Test
	public void testMoveDiskIntInt()
	{
		// Cas valide 1 : La tour de départ est différente de celle d'arrivé,
		// la tour d'arrivé et de départ est dans l'intervalle [0, 2], la 
		// tour de départ n'est pas vide et le disque de la tour de départ
		// est plus petit que celui situé sur la tour d'arrivé.
		Game g = new Game(3);
		Assert.assertTrue(g.moveDisk(0, 2));
		
		// Cas invalide 1 : La tour de départ est identique à celle d'arrivé.
		g = new Game(3);
		Assert.assertFalse(g.moveDisk(0, 0));
		
		// Cas invalide 2 : La tour de départ est vide.
		g = new Game(3);
		Assert.assertFalse(g.moveDisk(2, 0));
		
		// Cas invalide 3 : La tour de départ n'est pas dans l'intervalle [0, 2].
		g = new Game(3);
		Assert.assertFalse(g.moveDisk(42, 0));
		
		// Cas invalide 4 : La tour d'arrivé n'est pas dans l'intervalle [0, 2].
		g = new Game(3);
		Assert.assertFalse(g.moveDisk(0, 3));
		
		// Cas invalide 5 : Le disque sur la tour de départ est plus grand que celui 
		// sur la tour d'arrivé
		g = new Game(3);
		g.moveDisk(0, 1);
		Assert.assertFalse(g.moveDisk(0, 1));
	}

	/**
	 * 	Méthode de test pour {@link appHanoi.model.Game#redraw(java.awt.Graphics)}
	 */
	@Test
	public void testRedraw() 
	{
		// Cas valide 1 : Si l'objet Graphics passé n'est pas null, alors le programme
		// 					déssine les tours sur celui-ci et continu.
		GameBoard gb = new GameBoard();
		Graphics gra = gb.getGraphics(); 
		Game g = new Game(3);
		g.redraw(gra);
		
		// Cas invalide 1 : Si l'objet Graphics passé est null, alors le programme
		// 					ne devrait pas planté et continuer.
		g = new Game(3);
		g.redraw(null);
	}

	/**
	 * 	Méthode de test pour {@link appHanoi.model.Game#isOver()}
	 */
	@Test
	public void testIsOver()
	{
		// Cas 1 : La partie vient d'être créer, elle n'est donc pas terminé.
		Game g = new Game(3);
		Assert.assertFalse(g.isOver());
		
		// Cas 2 : La partie est en cours.
		g = new Game(3);
		g.moveDisk(0, 2);
		g.moveDisk(0, 1);
		g.moveDisk(2, 1);
		Assert.assertFalse(g.isOver());
		
		// Cas 3 : Tous les disques sont sur la tour 3, la partie est donc terminé.
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
