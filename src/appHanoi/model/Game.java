package appHanoi.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * 
 * 
 * @author Christian Lesage
 * @author Alexandre Tremblay
 * 
 */
public class Game
{
	// Nombre de tours dans le jeu
	private final static int NB_TOWERS = 3;
	
	// Tableau contenant les tours
	private Tower[] towers;
	
	// Nombre de disques utilisés pour la partie
	private int nbDisks;

	/**
	 * Construit une nouvelle partie
	 */
	public Game(int nbDisks)
	{
		this.towers = new Tower[Game.NB_TOWERS];
		this.nbDisks = nbDisks;
		
		// Crée les tours
		for (int i = 0; i < Game.NB_TOWERS; i++)
		{
			this.towers[i] = new Tower();
		}
		
		// Ajoute des disques sur la première tour 
		for (int i = this.nbDisks; i > 0; i--)
		{
			towers[0].push(new Disk(i, null));
		}
		
//		this.replay(this.nbDisks); // TODO
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
		boolean succeeded = false;
		
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
					succeeded = true;
				}
			}
		}
		
		return succeeded;
	}

	/**
	 * 
	 * 
	 * @param g
	 * @return g
	 */
	public void redraw(Graphics g) // TODO : Peut-être laisser à drawTowers ?
	{
		if (g != null)
		{
			Rectangle r = g.getClipBounds();
			//		g.setColor(Color.BLUE);
			//		g.drawRect(r.x, r.y, r.width - 1, r.height - 1);
			g.clearRect(r.x, r.y, r.width, r.height);

			float towerZoneWidth = (float)r.width / NB_TOWERS; 

			for (int i = 0; i < NB_TOWERS; i++)
			{
				towers[i].redraw(g.create(Math.round(i * towerZoneWidth), r.y, Math.round(towerZoneWidth), r.height), this.nbDisks);
			}
		}
	}
	
//	/**
//	 * 
//	 * 
//	 * @param nbDisks
//	 */
//	public void replay(int nbDisks)
//	{
//		
//	}
	
}
