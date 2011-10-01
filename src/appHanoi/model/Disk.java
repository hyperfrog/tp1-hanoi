/**
 * 
 */
package appHanoi.model;

import java.awt.Color;

/**
 * La classe Disk permet des cr�er des disques utilisables par le jeu des tours de Hanoi.
 * 
 * @author Christian Lesage
 * @author Alexandre Tremblay
 *
 */
public class Disk
{
    // diam�tre du disque
    private int diameter;

    // couleur du disque
    private Color color;

    /**
     * Construit un disque ayant le diam�tre et la couleur sp�cifi�s. 
     * 
     * Si le diam�tre n'est pas compris dans l'intervalle [1, 64], un diam�tre de 1 est utilis�.
     * 
     * @param diameter le diam�tre du disque
     * @param color la couleur du disque
     */
    public Disk(int diameter, Color color)
    {
    	this.diameter = (diameter >= 1 && diameter <= 64) ? diameter : 1;
    	
    	this.color = color;
    }
    
	/**
	 * Retourne le diam�tre du disque.
	 * 
	 * @return le diam�tre du disque
	 */
	public int getDiameter()
	{
		return diameter;
	}

//	/**
//	 * �tablit le diam�tre du disque � la valeur sp�cifi�e.
//	 * @param diameter le nouveau diam�tre 
//	 */
//	public void setDiameter(int diameter)
//	{
//		this.diameter = diameter;
//	}

	/**
	 * Retourne la couleur du disque
	 * 
	 * @return la couleur du disque
	 */
	public Color getColor()
	{
		return color;
	}

//	/**
//	 * �tablit la couleur du disque.
//	 * 
//	 * @param color la nouvelle couleur du disque
//	 */
//	public void setColor(Color color)
//	{
//		this.color = color;
//	}
    
    
    
}
