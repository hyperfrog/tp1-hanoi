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
 * La classe Tower permet de créer des tours utilisables par le jeu des tours de Hanoi.
 * 
 * Une tour est une pile de disques ayant comme particularité qu'un disque peut être empilé
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

	private static final int TOWER_WIDTH = 10;
	
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
	 * Un disque peut être empilé seulement s'il est plus petit que le disque sur le dessus 
	 * de la pile ou si la pile est vide.
	 * 
	 * @param element le disque à empiler
	 * @return le disque empilé si l'opération a réussi, sinon null 
	 */
	public Disk push(Disk element) 
	{
		Disk d = null;

		if (element != null)
		{
			if (this.isEmpty())
			{
				d = (Disk)super.push(element);
			}
			else if (this.peek() instanceof Disk)
			{
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
	 * Enlève le disque sur le dessus de la pile
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
	 * Un disque peut être empilé seulement s'il est plus petit que le disque sur le dessus 
	 * de la pile ou si la pile est vide.
	 * 
	 * @param element le disque à empiler. Doit être un Disk, sinon l'opération échoue.
	 * @return le disque empilé si l'opération a réussi, sinon null 
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
	 * Dessine la tour dans le Graphics spécifié 
	 */
	public void redraw(Graphics g, int nbDisks)
	{
		if (g != null)
		{
			Rectangle r = g.getClipBounds();

			g.setColor(Color.BLACK);
			//		g.drawRect(r.x, r.y, r.width - 1, r.height - 1);
			int towerX = (r.width - TOWER_WIDTH) / 2;
			g.drawRect(towerX, r.y, TOWER_WIDTH, r.height - 1);

			float diskHeight = (float)r.height / nbDisks;

			for(int i = this.size(); i > 0; i--)
			{
				Disk d = (Disk)this.get(i);

				d.redraw(g.create(r.x, Math.round(diskHeight * (nbDisks - this.size() + i - 1)), r.width, Math.round(diskHeight)), nbDisks);
			}
		}
	}
}
