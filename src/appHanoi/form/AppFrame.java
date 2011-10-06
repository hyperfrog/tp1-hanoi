package appHanoi.form;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFrame;

/**
 * La classe AppFrame permet de cr�er une fen�tre qui sert de contenant
 * pour le plateau de jeu des tours de Hanoi.
 * 
 * @author Christian Lesage
 * @author Alexandre Tremblay
 * 
 */

public class AppFrame extends JFrame implements ComponentListener
{
	public static final Dimension INIT_SIZE = new Dimension(800, 450);
	public static final int MIN_WIDTH = 320;
	public static final int MIN_HEIGHT = 240;
	
	public static final String INIT_TITLE = "Tours de Hanoi par Alexandre Tremblay et Christian Lesage";

	private GameBoard gameBoard;
	
	/**
	 * Cr�e une nouvelle fen�tre contenant un nouveau plateau de jeu.
	 */
	public AppFrame()
	{
		super();
		
		this.setTitle(AppFrame.INIT_TITLE);
		this.setSize(AppFrame.INIT_SIZE);
		this.setLocationRelativeTo(null);
//		this.setResizable(false);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameBoard = new GameBoard();
		this.getContentPane().add(this.gameBoard);
		this.addComponentListener(this);
//		this.pack();
		
	}
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		this.gameBoard.redraw();
	}

	@Override
	public void componentHidden(ComponentEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void componentMoved(ComponentEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void componentResized(ComponentEvent e)
	{
		int width = getWidth();
		int height = getHeight();
		// V�rifie si la largeur et la hauteur sont inf�rieures 
		// � la valeur minimale permise pour chacune
		boolean resize = false;
		if (width < MIN_WIDTH)
		{
			resize = true;
			width = MIN_WIDTH;
		}
		if (height < MIN_HEIGHT)
		{
			resize = true;
			height = MIN_HEIGHT;
		}
		if (resize)
		{
			setSize(width, height);
		}
	}

	@Override
	public void componentShown(ComponentEvent e)
	{
		// TODO Auto-generated method stub
	}
}
