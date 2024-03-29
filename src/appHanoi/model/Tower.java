/**
 * 
 */
package appHanoi.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
//import java.awt.Point;

import util.collection.linkedStack.ImplStack;

/**
 * La classe Tower permet de cr�er des tours utilisables par le jeu des tours de Hanoi.
 * 
 * Une tour est une pile de disques ayant comme particularit� qu'un disque peut �tre empil�
 * seulement s'il est plus petit que le disque sur le dessus de la pile ou si la pile est vide.
 * 
 * @author Christian Lesage
 * @author Alexandre Tremblay
 *
 * @see util.collection.linkedStack.ImplStack
 *
 */
public class Tower extends ImplStack
{

	/**
	 * Construit une tour. 
	 */
	public Tower()
	{
		super();
	}

	/**
	 * Empile un disque sur la tour.
	 * 
	 * Un disque peut �tre empil� seulement s'il est plus petit que le disque sur le dessus 
	 * de la pile ou si la pile est vide.
	 * 
	 * Un �l�ment null ne peut pas �tre empil�.
	 * 
	 * @param element le disque � empiler
	 * @return le disque empil� si l'op�ration a r�ussi, sinon null 
	 */
	public Disk push(Disk element) 
	{
		Disk d = null;

		if (element != null)
		{
			// Si la tour est vide
			if (this.isEmpty())
			{
				// Empile le disque
				d = (Disk)super.push(element);
			}
			else if (this.peek() instanceof Disk)
			{
				// Empile le disque seulement si son diam�tre est inf�rieur 
				// � celui du disque sur le dessus de la pile
				int diskOnTopDiameter = ((Disk)this.peek()).getDiameter();
				if (element.getDiameter() < diskOnTopDiameter)
				{
					d = (Disk)super.push(element);
				}
			}
		}
		return d;
	}


	/** 
	 * Enl�ve le disque sur le dessus de la pile
	 * 
	 * @return le disque sur le dessus de la pile s'il y en a un, sinon null
	 * 
	 */
	public Disk pop() 
	{
		Disk d = null;

		Object o = super.pop();
		
		if (o instanceof Disk)
		{
			d = (Disk)o;
		}
		
		return d;
	}

	/**
	 * Empile un disque sur la tour.
	 * 
	 * Un disque peut �tre empil� seulement s'il est plus petit que le disque sur le dessus 
	 * de la pile ou si la pile est vide.
	 * 
	 * @param element le disque � empiler. Doit �tre un Disk, sinon l'op�ration �choue.
	 * @return le disque empil� si l'op�ration a r�ussi, sinon null 
	 *
	 */
	public Object push(Object element) 
	{
		Object o = null;
		
		if (element instanceof Disk)
		{
			o = this.push((Disk)element);
		}
		
		return o;
	}
	
	/**
	 * Dessine la tour dans le Graphics sp�cifi�
	 *  
	 * @param g le Graphics dans lequel la tour doit se dessiner
	 * @param nbDisks le nombre total de disques pour cette partie
	 */
	public void redraw(Graphics g, int nbDisks)
	{
		if (g != null)
		{
			// Obtient le rectangle d�limitant la zone de la tour
			Rectangle r = g.getClipBounds();
			
			// Calcule la largeur de la tour
			int towerWidth = (int)(r.width * 0.1);

			// D�termine la position de la tour dans son rectangle
			int towerX = (r.width - towerWidth) / 2;

			// Dessine la tour
			g.setColor(Color.GRAY);
			g.fillRect(towerX, r.y, towerWidth, r.height - 1);

			// D�termine la hauteur des disques
			float diskHeight = (nbDisks > 0) ? (float)r.height / nbDisks : 0;

			// Dessine chacun des disques de la tour
			for(int i = this.size(); i > 0; i--)
			{
				// Obtient le disque i
				Disk d = (Disk)this.get(i);
				
				// Cr�e un Graphics pour le disque
				Graphics g2 = g.create(
						r.x, 
						Math.round(diskHeight * (nbDisks - this.size() + i - 1)), 
						r.width, 
						Math.round(diskHeight));

				// Dessine le disque
				d.redraw(g2, nbDisks);
			}
		}
	}
}
