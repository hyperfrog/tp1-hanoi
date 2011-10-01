/**
 * 
 */
package appHanoi.form;

//import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;

/**
 * @author Christian
 * 
 */
public class AppFrame extends JFrame
{
	public static final Dimension INIT_SIZE = new Dimension(400, 400);
	public static final Point INIT_LOCATION = new Point(150, 50);
	public static final String INIT_TITLE = "Tours de Hanoi";

	private GameBoard gameBoard = new GameBoard();

	public AppFrame()
	{
		super();
		this.setTitle(AppFrame.INIT_TITLE);
		this.setSize(AppFrame.INIT_SIZE);
		this.setLocation(AppFrame.INIT_LOCATION);
		// this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		this.getContentPane().add(this.gameBoard);
	}

}
