/**
 * 
 */
package appHanoi.model;

import java.awt.Graphics;

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
	 * @param element le disque � empiler
	 * @return le disque empil� si l'op�ration a r�ussi, sinon null 
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
	 * 
	 */
	public Graphics redraw(Graphics g)
	{
		return g;
	}

}
