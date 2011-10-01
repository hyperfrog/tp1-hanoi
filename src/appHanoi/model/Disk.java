/**
 * 
 */
package appHanoi.model;

import java.awt.Color;

/**
 * La classe Disk permet des créer des disques utilisables par le jeu des tours de Hanoi.
 * 
 * @author Christian Lesage
 * @author Alexandre Tremblay
 *
 */
public class Disk
{
    // diamètre du disque
    private int diameter;

    // couleur du disque
    private Color color;

    /**
     * Construit un disque ayant le diamètre et la couleur spécifiés. 
     * 
     * Si le diamètre n'est pas compris dans l'intervalle [1, 64], un diamètre de 1 est utilisé.
     * 
     * @param diameter le diamètre du disque
     * @param color la couleur du disque
     */
    public Disk(int diameter, Color color)
    {
    	this.diameter = (diameter >= 1 && diameter <= 64) ? diameter : 1;
    	
    	this.color = color;
    }
    
	/**
	 * Retourne le diamètre du disque.
	 * 
	 * @return le diamètre du disque
	 */
	public int getDiameter()
	{
		return diameter;
	}

//	/**
//	 * Établit le diamètre du disque à la valeur spécifiée.
//	 * @param diameter le nouveau diamètre 
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
//	 * Établit la couleur du disque.
//	 * 
//	 * @param color la nouvelle couleur du disque
//	 */
//	public void setColor(Color color)
//	{
//		this.color = color;
//	}
    
    
    
}
