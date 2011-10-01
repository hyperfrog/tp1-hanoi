/**
 * 
 */
package appHanoi.model;

import util.collection.arrayStack.ImplStack;

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
		// TODO Auto-generated constructor stub
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
    	return null;
    }

    /* (non-Javadoc)
     * @see util.collection.arrayStack.ImplStack#pop()
     */
    public Disk pop() 
    {
    	return null;
    }

	//
    public Object push (Object element) 
    {
    	return null;
    }

}
