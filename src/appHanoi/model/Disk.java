/**
 * 
 */
package appHanoi.model;

import java.awt.Color;
import java.awt.Graphics;
//import java.awt.Point;
import java.awt.Rectangle;

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
    
	/**
	 * Dessine le disque dans le Graphics sp�cifi�
	 *  
	 * @param g le Graphics dans lequel le disque doit se dessiner
	 * @param nbDisks le nombre total de disques pour cette partie
	 */
	public void redraw(Graphics g, int nbDisks)
	{
		if (g != null)
		{
			// Obtient le rectangle d�limitant la zone du disque
			Rectangle r = g.getClipBounds();

			// Calcule la largeur du plus petit disque
			int minDiskWidth = (int)(r.width * 0.2);
			
			// Calcule la position et la largeur du disque 
			float widthFactor = (nbDisks > 1) ? (float)(r.width - minDiskWidth) / (nbDisks - 1) : 1;
			int diskWidth = Math.round((this.diameter - 1) * widthFactor) + minDiskWidth;
			int diskX = (r.width - diskWidth) / 2;

			// Dessine le disque
			g.setColor(this.color);
			g.fillRect(diskX, r.y, diskWidth, r.height - 1);
		}
	}
    
    
}
