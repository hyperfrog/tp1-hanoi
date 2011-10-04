package appHanoi.model;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 
 * 
 * @author Christian Lesage
 * @author Alexandre Tremblay
 * 
 */
public class Game
{
	//
	private final static int NB_TOWERS = 3;
	
	//
	private Tower[] towers;
	
	//
	private int nbDisks;

	/**
	 * 
	 */
	public Game()
	{
		this.towers = new Tower[Game.NB_TOWERS];
		
		for (int i = 0; i < Game.NB_TOWERS; i++)
		{
			this.towers[i] = new Tower();
		}
		
		this.replay(this.nbDisks); // TODO
	}
	
	/**
	 * 
	 * 
	 * @param fromTower
	 * @param toTower
	 * @return
	 */
	public boolean moveDisk(int fromTower, int toTower)
	{
		boolean succeed = false;
		
		if ((fromTower >= 0 && fromTower < Game.NB_TOWERS) 
				&& (toTower >= 0 && toTower < Game.NB_TOWERS) 
				&& fromTower != toTower)
		{
			if (this.towers[fromTower].peek() != null)
			{
				Disk d = (Disk) this.towers[fromTower].peek();
				
				if (this.towers[toTower].push(d) != null)
				{
					this.towers[fromTower].pop();
					succeed = true;
				}
			}
		}
		
		return succeed;
	}

	/**
	 * 
	 * 
	 * @param g
	 * @return g
	 */
	public void redraw(Graphics g) // TODO : Peut-être laisser à drawTowers ?
	{
		g.setColor(Color.BLUE);
		g.drawRect(10, 10, 100, 100);
	}
	
	/**
	 * 
	 * 
	 * @param nbDisks
	 */
	public void replay(int nbDisks)
	{
		
	}
	
}
