package appHanoi.form;

import java.awt.Dimension;
import javax.swing.JFrame;

/**
 * La classe AppFrame permet de créer une fenêtre qui sert de conteneur
 * pour le plateau de jeu des tours de Hanoi.
 * 
 * @author Christian Lesage
 * @author Alexandre Tremblay
 * 
 */

public class AppFrame extends JFrame
{
	public static final Dimension INIT_SIZE = new Dimension(800, 450);
	public static final String INIT_TITLE = "Tours de Hanoi";

	private GameBoard gameBoard = new GameBoard();
	
	/**
	 * Créer une nouvelle fenêtre contenant un nouveau plateau de jeu.
	 */
	public AppFrame()
	{
		super();
		
		this.setTitle(AppFrame.INIT_TITLE);
		this.setSize(AppFrame.INIT_SIZE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(this.gameBoard);
	}

}
