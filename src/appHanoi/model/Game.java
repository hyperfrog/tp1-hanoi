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
		
		this.nbDisks = (nbDisks >= 3 && nbDisks <= 64) ? nbDisks : 3;
		
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
		
		// Si les param�tres d'entr�e sont valides
		if ((fromTower >= 0 && fromTower < Game.NB_TOWERS) 
				&& (toTower >= 0 && toTower < Game.NB_TOWERS) 
				&& fromTower != toTower)
		{
			// Si la tour d'origine n'est pas vide
			if (this.towers[fromTower].peek() != null)
			{
				Disk d = (Disk) this.towers[fromTower].peek();
				
				// Si le disque de la tour d'origine peut �tre empil� sur la tour de destination
				if (this.towers[toTower].push(d) != null)
				{
					// Enl�ve le disque de la tour d'origine
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
	public void redraw(Graphics g)
	{
		if (g != null)
		{
			Rectangle r = g.getClipBounds();

			// Efface toute la surface
			g.setColor(Color.WHITE);
			g.fillRect(r.x, r.y, r.width, r.height);

			// D�termine la largeur de la zone de chaque tour
			float towerZoneWidth = (float)r.width / Game.NB_TOWERS; 

			// Dessine chacune des tours
			for (int i = 0; i < Game.NB_TOWERS; i++)
			{
				// Cr�e un �sous graphics� pour la tour
				Graphics g2 = g.create(Math.round(i * towerZoneWidth), r.y, Math.round(towerZoneWidth), r.height);
				towers[i].redraw(g2, this.nbDisks);
			}
		}
	}
	
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
