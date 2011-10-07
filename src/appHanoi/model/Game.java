package appHanoi.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * La classe Game encapsule la logique du jeu des tours de Hanoi.
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
	
	// Indique si la partie est terminée
	private boolean isOver;
	

	/**
	 * Construit une nouvelle partie avec le nombre de disques spécifié.
	 * 
	 * Si le nombre de disques spécifié n'est pas compris dans l'intervalle [3, 64], 
	 * la partie est créée avec 3 disques. 
	 * 
	 * @param nbDisks le nombre de disques pour la partie
	 */
	public Game(int nbDisks)
	{
		this.towers = new Tower[Game.NB_TOWERS];
		
		this.nbDisks = (nbDisks >= 3 && nbDisks <=64) ? nbDisks : 3;
		
		this.isOver = false;
		
		// Crée les tours
		for (int i = 0; i < Game.NB_TOWERS; i++)
		{
			this.towers[i] = new Tower();
		}
		
		// Ajoute des disques sur la première tour 
		for (int i = this.nbDisks; i > 0; i--)
		{
			towers[0].push(new Disk(i, diskNumToDiskColor(i)));
		}
		
//		this.replay(this.nbDisks); // TODO
	}
	
	// Retourne une couleur choisie en fonction du numéro du disque 
	private Color diskNumToDiskColor(int diskNum)
	{
		Color c = null;
		switch ((diskNum - 1) % 5)
		{
			case 0:
				c = Color.RED;
				break;
			case 1:
				c = Color.GREEN;
				break;
			case 2:
				c = Color.BLUE;
				break;
			case 3:
				c = Color.BLACK;
				break;
			case 4:
				c = Color.YELLOW;
				break;
		}
		
		return c;
	}
	
	/**
	 * Retourne le nombre de disques utilisés dans la partie
	 * 
	 * @return le nombre de disques utilisés dans la partie
	 */
	public int getNbDisks()
	{
		return nbDisks;
	}

	/**
	 * Retourne le disque sur le dessus de la tour spécifiée sans l'enlever.
	 * Retourne null si la tour est vide ou si le numéro de la tour n'est pas valide.
	 * Le numéro de la tour doit être compris dans l'intervalle [0, 2].
	 * 
	 * @param towerNum le numéro de la tour du dessus de laquelle on veut connaître le disque 
	 * @return le disque sur le dessus de la tour spécifiée s'il y en a un et si la tour existe, sinon null.
	 */
	public Disk peekTower(int towerNum)
	{
		Disk d = null;
		if (towerNum >= 0 && towerNum < Game.NB_TOWERS)
		{
			d = (Disk)towers[towerNum].peek();
		}
		return d;
	}
	
	/**
	 * Déplace un disque d'une tour à l'autre.
	 * 
	 * Le disque sur le dessus de la tour d'origine est déplacé sur la tour 
	 * de destination si celle-ci est vide ou si le disque sur le dessus 
	 * de celle-ci est plus grand que le disque à déplacer.
	 * 
	 *  Aucun déplacement n'est effectué si un des indices des tours n'est
	 *  pas compris dans l'intervalle [0, 2].
	 * 
	 * @param fromTower la tour d'origine
	 * @param toTower la tour de destination
	 * @return vrai si le déplacement a été effectué, sinon faux.
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
					
					// Vérifie si la partie est terminée
					if (toTower > 0)
					{
						this.isOver = this.towers[toTower].size() == this.nbDisks;
					}
				}
			}
		}
		
		return succeeded;
	}

	/**
	 * Dessine les tours dans le Graphics spécifié
	 * 
	 * @param g le Graphics dans lequel les tours doivent être dessinées
	 */
	public void redraw(Graphics g) // TODO : Peut-être laisser à drawTowers ?
	{
		if (g != null)
		{
			Rectangle r = g.getClipBounds();

			g.setColor(Color.WHITE);
			g.fillRect(r.x, r.y, r.width, r.height);

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
	
	/**
	 * Vérifie si la partie est terminée.
	 * 
	 * Une partie est terminée quand tous les disques ont été empilés 
	 * dans une tour autre que celle de départ.
	 * 
	 * @return vrai si la partie est terminée, sinon faux.
	 */
	public boolean isOver()
	{
		return this.isOver;
	}
}
