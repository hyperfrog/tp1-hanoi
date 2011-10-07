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
 * La classe GameBoard gère l'interface du jeu des tours de Hanoi.
 * C'est elle qui gère les évènements de l'interface utilisateur.
 * Elle contient aussi la logique du solutionneur automatisé.
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

    // Panel contenant les «autres» contrôles 
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

    // Checkbox pour la résolution automatique 
    private JCheckBox autoSolveCheckBox;

    // Label affiché à côté du spinner pour spécifier le nombre de disques  
    private JLabel nbDisksLabel;

    // Spinner pour spécifier le nombre de disques  
    private JSpinner nbDisksSpinner;
    
	// Indique qu'une première tour a été sélectionnée 
    private boolean waitingForSelection = false;
    
    // Contient la tour de départ d'un déplacement  
	private int fromTower;

	// Référence au thread du solutionneur
	private volatile Thread solverThread = null;
	
	/**
	 * Construit un plateau de jeu.
	 * Une partie initiale est aussi créée avec 5 disques. 
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

        // Entre 3 et 64 disques, 5 par défaut, incrément de 1
        SpinnerModel sm = new SpinnerNumberModel(5, 3, 64, 1);
        nbDisksSpinner = new JSpinner(sm);

        this.setLayout(new BorderLayout());
        this.add(gamePanel, BorderLayout.CENTER);
        this.gamePanel.setBackground(Color.WHITE);

		this.messagePanel.add(this.messageLabel);
		this.messagePanel.setBackground(Color.WHITE);
		this.messageLabel.setForeground(Color.red);
		this.add(messagePanel, BorderLayout.NORTH);

        // Layout à 3 colonnes pour les boutons des tours
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
        autoSolveCheckBox.setText("Résoudre");
        otherControlsPanel.add(autoSolveCheckBox);

        buttonPanel.add(otherControlsPanel, BorderLayout.PAGE_END);

        this.add(buttonPanel, BorderLayout.PAGE_END);

		// Spécifie les écouteurs pour les boutons
        this.tower1Button.addActionListener(this);
		this.tower2Button.addActionListener(this);
		this.tower3Button.addActionListener(this);
		this.replayButton.addActionListener(this);

        // Replay va se charger de créer une nouvelle partie 
        this.replay();
	}

	// Déplace un disque de la tour «from» vers la tour «to».
	// Suppose que la première tour est la tour 1.
	// Retourne vrai si le déplacement est valide, faux sinon.
	private boolean moveDisk(int from, int to)
	{
		boolean diskMoved = this.currentGame.moveDisk(from - 1, to - 1);
		
		if (diskMoved)
		{
			if (!this.currentGame.isOver())
			{
				this.messageLabel.setText(String.format("Disque déplacé de la tour %s vers la tour %s.", from , to));
			}
			else
			{
				this.messageLabel.setText("Partie terminée !");
				// Empêche le joueur de faire un déplacement subséquent
				this.disableButtons();
			}
			
			this.redraw();
		}
		else
		{
			this.messageLabel.setText(String.format("Déplacement impossible de la tour %s vers la tour %s.", from , to));
		}

		return diskMoved;
	}
	
	/**
	 * Redessine le plateau de jeu.
	 * Vous ne devriez pas à appeler cette méthode directement.
	 * Sa visibilité est à «package» pour que AppFrame puisse l'appeler.   
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
	
	// Réinitialise la partie
	private void replay()
	{
		solverThread = null;
		
		// Obtient le nombre de disques souhaité pour la nouvelle partie
		SpinnerNumberModel sm = (SpinnerNumberModel)nbDisksSpinner.getModel();
		int nbDisks = sm.getNumber().intValue();
		
		// Crée une nouvelle partie
		this.currentGame = new Game(nbDisks);
		this.redraw();
		this.messageLabel.setText("Prêt!");
		this.resetButtons();

		// Si la résolution automatique est souhaitée
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
	
	// Réinitialise les boutons des tours 
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
	 * Reçoit et traite les événements relatifs aux boutons
	 * Cette méthode doit être publique mais ne devrait pas être appelée directement.
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * @param evt événement déclencheur
	 */
	public void actionPerformed(ActionEvent evt)
	{
		// Si c'est le bouton d'une tour pour indiquer la tour d'origine d'un déplacement
		if (evt.getActionCommand().equals("1") || evt.getActionCommand().equals("2") || evt.getActionCommand().equals("3"))
		{
			JButton bSrc = (JButton) evt.getSource();
			
			// Si une première tour a déjà été sélectionnée
			if (waitingForSelection)
			{
				// Tente d'effectuer le déplacement
				this.moveDisk(this.fromTower, Integer.parseInt(evt.getActionCommand()));
				this.resetButtons();
			}
			else
			{
				// Mémorise la tour d'origine du déplacement
				this.fromTower = Integer.parseInt(evt.getActionCommand());
				this.messageLabel.setText(String.format("Déplacement de la tour %s vers la tour...", evt.getActionCommand()));
				// Transforme le bouton en bouton d'annulation du déplacement
				bSrc.setText("Annuler");
				bSrc.setActionCommand("CANCEL");
			}
			
			waitingForSelection = !waitingForSelection;
		}
		// Si c'est le bouton d'une tour pour indiquer l'annulation d'un déplacement
		else if (evt.getActionCommand().equals("CANCEL")) 
		{
			this.waitingForSelection = false;
			this.resetButtons();
			this.messageLabel.setText("Déplacement annulé.");
		}
		// Si c'est le bouton pour commencer une nouvelle partie
		else if (evt.getActionCommand().equals("REPLAY"))
		{
			int response = 0;
			
			// Demande une confirmation si une partie est en cours
			if (!this.currentGame.isOver())
			{
				response = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir commencer une nouvelle partie ? \nLa partie courante sera perdue.",
					"Confirmation", JOptionPane.YES_NO_OPTION);
			}
			// Si le joueur est certain de vouloir recommencer 
			if (response == 0)
			{
				this.replay();
			}
		}
	}
	
	// Résout une partie
	private void solve(Thread t)
	{
		// Mémorise la tour contenant le petit disque
		int smallDiskTowerNum = 1;
		
		sleep(500);
		// Alterne entre le déplacement du petit disque et celui d'un autre disque
		// jusqu'à ce que la partie soit terminée
		for (int n = 1; !this.currentGame.isOver() && t == solverThread; n++)		
		{
			if (n % 2 == 1) // Déplace le petit disque 
			{
				int toTower;

				// Déplacement à gauche si nombre impair de disques
				if (this.currentGame.getNbDisks() % 2 == 1) 
				{
					toTower = smallDiskTowerNum == 1 ? 3 : smallDiskTowerNum - 1;  
				}
				else // Déplacement à droite
				{
					toTower = smallDiskTowerNum == 3 ? 1 : smallDiskTowerNum + 1;
				}
				moveDisk(smallDiskTowerNum, toTower);
				smallDiskTowerNum = toTower;
			}
			else // Déplace un «gros» disque (diamètre > 1)
			{
				// Le déplacement concerne les des deux tours différentes de celle du petit disque  
				int tower1 = smallDiskTowerNum == 3 ? 1 : smallDiskTowerNum + 1;
				int tower2 = smallDiskTowerNum == 1 ? 3 : smallDiskTowerNum - 1;
				
				// Tente un déplacement
				if (!moveDisk(tower1, tower2))
				{
					// Mauvais choix... Essaie dans l'autre sens.
					moveDisk(tower2, tower1);
				}
			}
			// Donne la chance au joueur de voir ce qui s'est passé.
			sleep(500);
		}
	}
	
	// Suspend le thread pendant le nombre de millisecondes spécifié
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