package appHanoi;

import appHanoi.form.AppFrame;

/**
 * La classe MainApp est le point d'entr� du programme.
 *  
 * @author Christian Lesage
 * @author Alexandre Tremblay
 *
 */

public class MainApp
{
	/**
	 * Point d'entr� du programme.
	 * Cr�er une fen�tre et la rend visible.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		AppFrame appFrame = new AppFrame();
		appFrame.setVisible(true);
	}

}
