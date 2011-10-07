package appHanoi.form;

import appHanoi.model.Game;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe GameBoard g�re l'interface du jeu des tours de Hanoi.
 * C'est elle qui g�re les �v�nements de l'interface utilisateur.
 * Elle contient aussi la logique du solutionneur automatis�.
 * 
 * @author Christian Lesage
 * @author Alexandre Tremblay
 *
 */
public class GameBoard extends JPanel implements ActionListener
{
	// Objet de la partie courante
	private Game currentGame = null;

	// Label pour l'affichage de messages  
	private JLabel messageLabel;

	// Panel contenant tous les panels contenant des boutons 
    private JPanel buttonPanel;
    
	// Panel contenant les boutons des tour
    private JPanel towerButtonsPanel;	

    // Panel contenant les �autres� contr�les 
    private JPanel otherControlsPanel;
    
    // Panel pour l'affichage des tours
    private JPanel gamePanel;
    
    // Panel contenant le label pour l'affichage
    private JPanel messagePanel;

    // Bouton pour commencer une nouvelle partie 
    private JButton replayButton;

    // Bouton de la tour 1 
    private JButton tower1Button;

    // Bouton de la tour 2 
    private JButton tower2Button;

    // Bouton de la tour 3 
    private JButton tower3Button;

    // Checkbox pour la r�solution automatique 
    private JCheckBox autoSolveCheckBox;

    // Label affich� � c�t� du spinner pour sp�cifier le nombre de disques  
    private JLabel nbDisksLabel;

    // Spinner pour sp�cifier le nombre de disques  
    private JSpinner nbDisksSpinner;
    
	// Indique qu'une premi�re tour a �t� s�lectionn�e 
    private boolean waitingForSelection = false;
    
    // Contient la tour de d�part d'un d�placement  
	private int fromTower;

	// R�f�rence au thread du solutionneur
	private volatile Thread solverThread = null;
	
	/**
	 * Construit un plateau de jeu.
	 * Une partie initiale est aussi cr��e avec 5 disques. 
	 */
	public GameBoard()
	{
		super();

        // Initialise les composantes
		this.buttonPanel = new JPanel();
        this.towerButtonsPanel = new JPanel();
        this.otherControlsPanel = new JPanel();
        this.gamePanel = new JPanel();
		this.messagePanel = new JPanel();
        this.tower1Button = new JButton();
        this.tower2Button = new JButton();
        this.tower3Button = new JButton();
        this.replayButton = new JButton();
        this.messageLabel = new JLabel();
        this.nbDisksLabel = new JLabel();
        this.autoSolveCheckBox = new JCheckBox();

        // Entre 3 et 64 disques, 5 par d�faut, incr�ment de 1
        SpinnerModel sm = new SpinnerNumberModel(5, 3, 64, 1);
        nbDisksSpinner = new JSpinner(sm);

        this.setLayout(new BorderLayout());
        this.add(gamePanel, BorderLayout.CENTER);
        this.gamePanel.setBackground(Color.WHITE);

		this.messagePanel.add(this.messageLabel);
		this.messagePanel.setBackground(Color.WHITE);
		this.messageLabel.setForeground(Color.red);
		this.add(messagePanel, BorderLayout.NORTH);

        // Layout � 3 colonnes pour les boutons des tours
		this.towerButtonsPanel.setLayout(new GridLayout(0, 3));

		this.towerButtonsPanel.add(tower1Button);
		this.towerButtonsPanel.add(tower2Button);
		this.towerButtonsPanel.add(tower3Button);
		
        buttonPanel.setLayout(new BorderLayout());
        
        buttonPanel.add(towerButtonsPanel, BorderLayout.PAGE_START);

		replayButton.setActionCommand("REPLAY");
        replayButton.setText("Nouvelle partie");
        otherControlsPanel.add(replayButton);
        otherControlsPanel.add(nbDisksSpinner);
        nbDisksLabel.setText("disques");
        otherControlsPanel.add(nbDisksLabel);
        autoSolveCheckBox.setText("R�soudre");
        otherControlsPanel.add(autoSolveCheckBox);

        buttonPanel.add(otherControlsPanel, BorderLayout.PAGE_END);

        this.add(buttonPanel, BorderLayout.PAGE_END);

		// Sp�cifie les �couteurs pour les boutons
        this.tower1Button.addActionListener(this);
		this.tower2Button.addActionListener(this);
		this.tower3Button.addActionListener(this);
		this.replayButton.addActionListener(this);

        // Replay va se charger de cr�er une nouvelle partie 
        this.replay();
	}

	// D�place un disque de la tour �from� vers la tour �to�.
	// Suppose que la premi�re tour est la tour 1.
	// Retourne vrai si le d�placement est valide, faux sinon.
	private boolean moveDisk(int from, int to)
	{
		boolean diskMoved = this.currentGame.moveDisk(from - 1, to - 1);
		
		if (diskMoved)
		{
			if (!this.currentGame.isOver())
			{
				this.messageLabel.setText(String.format("Disque d�plac� de la tour %s vers la tour %s.", from , to));
			}
			else
			{
				this.messageLabel.setText("Partie termin�e !");
				// Emp�che le joueur de faire un d�placement subs�quent
				this.disableButtons();
			}
			
			this.redraw();
		}
		else
		{
			this.messageLabel.setText(String.format("D�placement impossible de la tour %s vers la tour %s.", from , to));
		}

		return diskMoved;
	}
	
	/**
	 * Redessine le plateau de jeu.
	 * Vous ne devriez pas � appeler cette m�thode directement.
	 * Sa visibilit� est � �package� pour que AppFrame puisse l'appeler.   
	 */
	void redraw()
	{
		Graphics g = this.gamePanel.getGraphics();
		if (g != null)
		{
			g.setClip(0, 0, this.gamePanel.getWidth(), this.gamePanel.getHeight());
			this.currentGame.redraw(g);
		}		
	}
	
	// R�initialise la partie
	private void replay()
	{
		solverThread = null;
		
		// Obtient le nombre de disques souhait� pour la nouvelle partie
		SpinnerNumberModel sm = (SpinnerNumberModel)nbDisksSpinner.getModel();
		int nbDisks = sm.getNumber().intValue();
		
		// Cr�e une nouvelle partie
		this.currentGame = new Game(nbDisks);
		this.redraw();
		this.messageLabel.setText("Pr�t!");
		this.resetButtons();

		// Si la r�solution automatique est souhait�e
		if (autoSolveCheckBox.isSelected())
		{
			final GameBoard gb = this;
			
			// Lance le solutionneur dans un nouveau thread
			solverThread = new Thread(new Runnable(){
				public void run()
				{
					gb.solve(solverThread);
				}
			});
			
			solverThread.start();
			this.disableButtons();
		}
	}
	
	// Bloque les boutons des tours
	private void disableButtons()
	{
		this.tower1Button.setEnabled(false);
		this.tower2Button.setEnabled(false);
		this.tower3Button.setEnabled(false);
	}
	
	// R�initialise les boutons des tours 
	private void resetButtons()
	{
		this.tower1Button.setEnabled(!this.currentGame.isOver());
		this.tower2Button.setEnabled(!this.currentGame.isOver());
		this.tower3Button.setEnabled(!this.currentGame.isOver());
		
		this.tower1Button.setActionCommand("1");
		this.tower1Button.setText("Tour 1");
		
		this.tower2Button.setActionCommand("2");
		this.tower2Button.setText("Tour 2");
		
		this.tower3Button.setActionCommand("3");
		this.tower3Button.setText("Tour 3");
	}

	/**
	 * Re�oit et traite les �v�nements relatifs aux boutons
	 * Cette m�thode doit �tre publique mais ne devrait pas �tre appel�e directement.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * @param evt �v�nement d�clencheur
	 */
	public void actionPerformed(ActionEvent evt)
	{
		// Si c'est le bouton d'une tour pour indiquer la tour d'origine d'un d�placement
		if (evt.getActionCommand().equals("1") || evt.getActionCommand().equals("2") || evt.getActionCommand().equals("3"))
		{
			JButton bSrc = (JButton) evt.getSource();
			
			// Si une premi�re tour a d�j� �t� s�lectionn�e
			if (waitingForSelection)
			{
				// Tente d'effectuer le d�placement
				this.moveDisk(this.fromTower, Integer.parseInt(evt.getActionCommand()));
				this.resetButtons();
			}
			else
			{
				// M�morise la tour d'origine du d�placement
				this.fromTower = Integer.parseInt(evt.getActionCommand());
				this.messageLabel.setText(String.format("D�placement de la tour %s vers la tour...", evt.getActionCommand()));
				// Transforme le bouton en bouton d'annulation du d�placement
				bSrc.setText("Annuler");
				bSrc.setActionCommand("CANCEL");
			}
			
			waitingForSelection = !waitingForSelection;
		}
		// Si c'est le bouton d'une tour pour indiquer l'annulation d'un d�placement
		else if (evt.getActionCommand().equals("CANCEL")) 
		{
			this.waitingForSelection = false;
			this.resetButtons();
			this.messageLabel.setText("D�placement annul�.");
		}
		// Si c'est le bouton pour commencer une nouvelle partie
		else if (evt.getActionCommand().equals("REPLAY"))
		{
			int response = 0;
			
			// Demande une confirmation si une partie est en cours
			if (!this.currentGame.isOver())
			{
				response = JOptionPane.showConfirmDialog(this, "�tes-vous s�r de vouloir commencer une nouvelle partie ? \nLa partie courante sera perdue.",
					"Confirmation", JOptionPane.YES_NO_OPTION);
			}
			// Si le joueur est certain de vouloir recommencer 
			if (response == 0)
			{
				this.replay();
			}
		}
	}
	
	// R�sout une partie
	private void solve(Thread t)
	{
		// M�morise la tour contenant le petit disque
		int smallDiskTowerNum = 1;
		
		sleep(500);
		// Alterne entre le d�placement du petit disque et celui d'un autre disque
		// jusqu'� ce que la partie soit termin�e
		for (int n = 1; !this.currentGame.isOver() && t == solverThread; n++)		
		{
			if (n % 2 == 1) // D�place le petit disque 
			{
				int toTower;

				// D�placement � gauche si nombre impair de disques
				if (this.currentGame.getNbDisks() % 2 == 1) 
				{
					toTower = smallDiskTowerNum == 1 ? 3 : smallDiskTowerNum - 1;  
				}
				else // D�placement � droite
				{
					toTower = smallDiskTowerNum == 3 ? 1 : smallDiskTowerNum + 1;
				}
				moveDisk(smallDiskTowerNum, toTower);
				smallDiskTowerNum = toTower;
			}
			else // D�place un �gros� disque (diam�tre > 1)
			{
				// Le d�placement concerne les des deux tours diff�rentes de celle du petit disque  
				int tower1 = smallDiskTowerNum == 3 ? 1 : smallDiskTowerNum + 1;
				int tower2 = smallDiskTowerNum == 1 ? 3 : smallDiskTowerNum - 1;
				
				// Tente un d�placement
				if (!moveDisk(tower1, tower2))
				{
					// Mauvais choix... Essaie dans l'autre sens.
					moveDisk(tower2, tower1);
				}
			}
			// Donne la chance au joueur de voir ce qui s'est pass�.
			sleep(500);
		}
	}
	
	// Suspend le thread pendant le nombre de millisecondes sp�cifi�
	private void sleep(int milliseconds)
	{
		try
		{
			Thread.sleep(milliseconds);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}