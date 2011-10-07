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
 * La classe GameBoard sert à l'interface du jeu des tours de Hanoi.
 * C'est elle qui gère les évènements de l'interface utilisateur.
 * 
 * @author Christian Lesage
 * @author Alexandre Tremblay
 *
 */
public class GameBoard extends JPanel implements ActionListener
{
	private Game currentGame = null;
	
	private JLabel message;
    private JPanel buttonPanel;
    private JPanel towerButtonPanel;	
    private JPanel otherButtonPanel;
    private JPanel gamePanel;
    private JPanel messagePanel;
//    private JButton cancelButton;
    private JButton replayButton;
    private JButton tower1Button;
    private JButton tower2Button;
    private JButton tower3Button;
    private JCheckBox autoSolveCheckBox;
    private JLabel nbDisksLabel;
    private JSpinner nbDisksSpinner;
    
	private boolean waitingForSelection = false;
	private int fromTower;

	private volatile Thread solverThread = null;
	
	/**
	 * Construit un plateau de jeu.
	 * Une partie initiale est aussi créée avec 3 disques. 
	 */
	public GameBoard()
	{
		super();

        this.buttonPanel = new JPanel();
        this.towerButtonPanel = new JPanel();
        this.otherButtonPanel = new JPanel();
        this.gamePanel = new JPanel();
		this.messagePanel = new JPanel();
        this.tower1Button = new JButton();
        this.tower2Button = new JButton();
        this.tower3Button = new JButton();
//        cancelButton = new JButton();
        this.replayButton = new JButton();
        SpinnerModel sm = new SpinnerNumberModel(5, 3, 64, 1);
        nbDisksSpinner = new javax.swing.JSpinner(sm);
        nbDisksLabel = new javax.swing.JLabel();
        autoSolveCheckBox = new javax.swing.JCheckBox();
        
        this.message = new JLabel();

        this.setLayout(new BorderLayout());
        this.add(gamePanel, BorderLayout.CENTER);
        this.gamePanel.setBackground(Color.WHITE);

		this.messagePanel.add(this.message);
		this.messagePanel.setBackground(Color.WHITE);
		this.message.setForeground(Color.red);
		this.add(messagePanel, BorderLayout.NORTH);

//        towerButtonPanel.setRequestFocusEnabled(false);

        this.towerButtonPanel.setLayout(new GridLayout(0, 3));

		this.tower1Button.addActionListener(this);
		this.tower2Button.addActionListener(this);
		this.tower3Button.addActionListener(this);

		this.towerButtonPanel.add(tower1Button);
		this.towerButtonPanel.add(tower2Button);
		this.towerButtonPanel.add(tower3Button);
		
//		this.cancelButton.setActionCommand("CANCEL");
//		this.cancelButton.addActionListener(this);
		
		this.replayButton.setActionCommand("REPLAY");
		this.replayButton.addActionListener(this);
		
        buttonPanel.setLayout(new BorderLayout());
        
        buttonPanel.add(towerButtonPanel, BorderLayout.PAGE_START);

//        cancelButton.setText("Annuler déplacement");
//        otherButtonPanel.add(cancelButton);

        replayButton.setText("Nouvelle partie");
        otherButtonPanel.add(replayButton);
        otherButtonPanel.add(nbDisksSpinner);
        nbDisksLabel.setText("disques");
        otherButtonPanel.add(nbDisksLabel);
        autoSolveCheckBox.setText("Résoudre");
        otherButtonPanel.add(autoSolveCheckBox);


        buttonPanel.add(otherButtonPanel, BorderLayout.PAGE_END);

        this.add(buttonPanel, BorderLayout.PAGE_END);
        
        this.replay();
	}

	// Déplace un disque de la tour from vers la tour to
	// Suppose que la première tour est la tour 1
	private boolean moveDisk(int from, int to)
	{
		boolean diskMoved = this.currentGame.moveDisk(from - 1, to - 1);
		
		if (diskMoved)
		{
			if (!this.currentGame.isOver())
			{
				this.message.setText(String.format("Disque déplacé de la tour %s vers la tour %s.", from , to));
			}
			else
			{
				this.message.setText("Partie terminée !");
				this.blockButtons();
			}
			
			this.redraw();
		}
		else
		{
			this.message.setText(String.format("Déplacement impossible de la tour %s vers la tour %s.", from , to));
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
		
		SpinnerNumberModel sm = (SpinnerNumberModel)nbDisksSpinner.getModel();
		int nbDisks = sm.getNumber().intValue();
		
		this.currentGame = new Game(nbDisks);
		this.redraw();
		this.message.setText("Prêt!");
		this.resetButtons();

		if (autoSolveCheckBox.isSelected())
		{
			final GameBoard gb = this;
			
			solverThread = new Thread(new Runnable(){
				public void run()
				{
					gb.solve(solverThread);
				}
			});
			
			solverThread.start();
			this.blockButtons();
		}
	}
	
	// Bloque les boutons des tours
	private void blockButtons()
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

	// Reçoit et traite les événements relatifs aux boutons
	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getActionCommand().equals("1") || evt.getActionCommand().equals("2") || evt.getActionCommand().equals("3"))
		{
			JButton bSrc = (JButton) evt.getSource();
			
			if (waitingForSelection)
			{
				this.moveDisk(this.fromTower, Integer.parseInt(evt.getActionCommand()));
				this.resetButtons();
			}
			else
			{
				this.fromTower = Integer.parseInt(evt.getActionCommand());
//				bSrc.setEnabled(false);
				bSrc.setText("Annuler");
				bSrc.setActionCommand("CANCEL");
				this.message.setText(String.format("Déplacement de la tour %s vers la tour...", evt.getActionCommand()));
			}
			
			waitingForSelection = !waitingForSelection;
		}
		else if (evt.getActionCommand().equals("CANCEL"))
		{
			this.waitingForSelection = false;
			this.resetButtons();
			this.message.setText("Déplacement annulé.");
		}
		else if (evt.getActionCommand().equals("REPLAY"))
		{
			int response = 0;
			
			if (!this.currentGame.isOver())
			{
				response = JOptionPane.showConfirmDialog(this, "Êtes-vous sûr de vouloir commencer une nouvelle partie ? \nLa partie courante sera perdue.",
					"Confirmation", JOptionPane.YES_NO_OPTION);
			}
			
			if (response == 0)
			{
				this.replay();
			}
		}
	}
	
	// Résout une partie
	private void solve(Thread t)
	{
		int smallDiskTowerNum = 1;
		
		sleep(500);
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
				int tower1 = smallDiskTowerNum == 3 ? 1 : smallDiskTowerNum + 1;
				int tower2 = smallDiskTowerNum == 1 ? 3 : smallDiskTowerNum - 1;
				
				if (!moveDisk(tower1, tower2))
				{
					moveDisk(tower2, tower1);
				}
			}
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