package appHanoi;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import appHanoi.form.AppFrame;

/**
 * La classe MainApp est le point d'entr�e du programme.
 * 
 * @author Christian Lesage
 * @author Alexandre Tremblay
 *
 */

public class MainApp
{
	/**
	 * Point d'entr�e du programme.
	 * Lance les tests, puis cr�e une fen�tre et la rend visible si tous les tests ont r�ussi.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		JUnitCore junit = new JUnitCore();
		
		boolean error = false;
		String errMsg = "";
		
		ArrayList<String> classesToTest = new ArrayList<String>();
		classesToTest.add("util.collection.test.NodeTest");
		classesToTest.add("util.collection.test.StackTest");
		classesToTest.add("appHanoi.model.test.TowerTest");
		classesToTest.add("appHanoi.model.test.GameTest");
		
		for(String className : classesToTest)
		{
			try
			{
				Result result = junit.run(Class.forName(className));

				if (result.getFailureCount() > 0)
				{
					errMsg += String.format("Les tests de la classe %s ont produit %d erreur(s).\n", className, result.getFailureCount());
					error = true;
				}
			}
			catch (ClassNotFoundException e1)
			{
				errMsg += String.format("Erreur: Aucune classe nomm�e �%s�.\n", className);
				error = true;
			}
		}
		
		if (!error)
		{
			AppFrame appFrame = new AppFrame();
			appFrame.setVisible(true);
		}
		else
		{
			JOptionPane.showMessageDialog(
					null, 
					errMsg + "\nL'application ne peut pas d�marrer. Vous ne devriez pas bl�mer les programmeurs, car vous ne les avez pas pay�s.\n", 
					"Erreur fatale", 
					JOptionPane.ERROR_MESSAGE);
		}
	}

}
