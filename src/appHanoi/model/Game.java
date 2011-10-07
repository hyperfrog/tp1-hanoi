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
	
	// Nombre de disques utilis�s pour la partie
	private int nbDisks;
	
	// Indique si la partie est termin�e
	private boolean isOver;
	

	/**
	 * Construit une nouvelle partie avec le nombre de disques sp�cifi�.
	 * 
	 * Si le nombre de disques sp�cifi� n'est pas compris dans l'intervalle [3, 64], 
	 * la partie est cr��e avec 3 disques. 
	 * 
	 * @param nbDisks le nombre de disques pour la partie
	 */
	public Game(int nbDisks)
	{
		this.towers = new Tower[Game.NB_TOWERS];
		
		this.nbDisks = (nbDisks >= 3 && nbDisks <=64) ? nbDisks : 3;
		
		this.isOver = false;
		
		// Cr�e les tours
		for (int i = 0; i < Game.NB_TOWERS; i++)
		{
			this.towers[i] = new Tower();
		}
		
		// Ajoute des disques sur la premi�re tour 
		for (int i = this.nbDisks; i > 0; i--)
		{
			towers[0].push(new Disk(i, diskNumToDiskColor(i)));
		}
		
//		this.replay(this.nbDisks); // TODO
	}
	
	// Retourne une couleur choisie en fonction du num�ro du disque 
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
	 * Retourne le nombre de disques utilis�s dans la partie
	 * 
	 * @return le nombre de disques utilis�s dans la partie
	 */
	public int getNbDisks()
	{
		return nbDisks;
	}

	/**
	 * Retourne le disque sur le dessus de la tour sp�cifi�e sans l'enlever.
	 * Retourne null si la tour est vide ou si le num�ro de la tour n'est pas valide.
	 * Le num�ro de la tour doit �tre compris dans l'intervalle [0, 2].
	 * 
	 * @param towerNum le num�ro de la tour du dessus de laquelle on veut conna�tre le disque 
	 * @return le disque sur le dessus de la tour sp�cifi�e s'il y en a un et si la tour existe, sinon null.
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
	 * D�place un disque d'une tour � l'autre.
	 * 
	 * Le disque sur le dessus de la tour d'origine est d�plac� sur la tour 
	 * de destination si celle-ci est vide ou si le disque sur le dessus 
	 * de celle-ci est plus grand que le disque � d�placer.
	 * 
	 *  Aucun d�placement n'est effectu� si un des indices des tours n'est
	 *  pas compris dans l'intervalle [0, 2].
	 * 
	 * @param fromTower la tour d'origine
	 * @param toTower la tour de destination
	 * @return vrai si le d�placement a �t� effectu�, sinon faux.
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
					
					// V�rifie si la partie est termin�e
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
	 * Dessine les tours dans le Graphics sp�cifi�
	 * 
	 * @param g le Graphics dans lequel les tours doivent �tre dessin�es
	 */
	public void redraw(Graphics g) // TODO : Peut-�tre laisser � drawTowers ?
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
	 * V�rifie si la partie est termin�e.
	 * 
	 * Une partie est termin�e quand tous les disques ont �t� empil�s 
	 * dans une tour autre que celle de d�part.
	 * 
	 * @return vrai si la partie est termin�e, sinon faux.
	 */
	public boolean isOver()
	{
		return this.isOver;
	}
}
