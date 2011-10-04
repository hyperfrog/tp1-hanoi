package appHanoi.model;

import appHanoi.form.GameBoard;

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
	private Tower[] towers;

	/**
	 * 
	 */
	public Game()
	{
		this.towers = new Tower[3];
		
		for (int i = 0; i < 3; i++)
		{
			this.towers[i] = new Tower();
		}
		
		
		
		
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
		
		if ((fromTower >= 0 && fromTower < 3) && (toTower >= 0 && toTower < 3) && fromTower != toTower)
		{
			if (this.towers[fromTower].peek() != null)
			{
				Object o = this.towers[fromTower].peek();
				
				if (this.towers[toTower].push(o) != null)
				{
					this.towers[fromTower].pop();
					succeed = true;
				}
			}
		}
		
		return succeed;
	}

	/**
	 * @param gameBoard
	 */
	public void drawTowers(GameBoard gameBoard)
	{

	}
	
	/**
	 * @param nbDisks
	 */
	public void replay(int nbDisks)
	{
		
	}
	
}
