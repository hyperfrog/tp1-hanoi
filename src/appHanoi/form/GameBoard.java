package appHanoi.form;

import appHanoi.model.Disk;
import appHanoi.model.Game;

import javax.swing.JCheckBox;
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * � MODIFIER ET COMPL�TER: code, javadoc, standards (static/final),
 * commentaires
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
	 * Une partie initiale est aussi cr��e avec 3 disques. 
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

//        cancelButton.setText("Annuler d�placement");
//        otherButtonPanel.add(cancelButton);

        replayButton.setText("Nouvelle partie");
        otherButtonPanel.add(replayButton);
        otherButtonPanel.add(nbDisksSpinner);
        nbDisksLabel.setText("disques");
        otherButtonPanel.add(nbDisksLabel);
        autoSolveCheckBox.setText("R�soudre");
        otherButtonPanel.add(autoSolveCheckBox);


        buttonPanel.add(otherButtonPanel, BorderLayout.PAGE_END);

        this.add(buttonPanel, BorderLayout.PAGE_END);
        
        this.replay();
	}

	// D�place un disque de la tour from vers la tour to
	// Suppose que la premi�re tour est la tour 1
	private boolean moveDisk(int from, int to)
	{
		boolean diskMoved = this.currentGame.moveDisk(from - 1, to - 1);
		
		if (diskMoved)
		{
			if (!this.currentGame.isOver())
			{
				this.message.setText(String.format("Disque d�plac� de la tour %s vers la tour %s.", from , to));
			}
			else
			{
				this.message.setText("Partie termin�e !");
			}
		}
		else
		{
			this.message.setText(String.format("D�placement impossible de la tour %s vers la tour %s.", from , to));
		}

		this.redraw();
		
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
		
		SpinnerNumberModel sm = (SpinnerNumberModel)nbDisksSpinner.getModel();
		int nbDisks = sm.getNumber().intValue();
		
		this.currentGame = new Game(nbDisks);
		this.redraw();
		this.message.setText("Pr�t!");
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
		}

		
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

	// Re�oit et traite les �v�nements relatifs aux boutons
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
				this.message.setText(String.format("D�placement de la tour %s vers la tour...", evt.getActionCommand()));
			}
			
			waitingForSelection = !waitingForSelection;
		}
		else if (evt.getActionCommand().equals("CANCEL"))
		{
			this.waitingForSelection = false;
			this.resetButtons();
			this.message.setText("D�placement annul�.");
		}
		else if (evt.getActionCommand().equals("REPLAY"))
		{
			this.replay();
		}
	}

	// R�sout une partie
	private void solve(Thread t)
	{
		// Direction du d�placement du disque de diam�tre 1
		final int direction = (this.currentGame.getNbDisks() % 2 == 0) ? 1 : -1;
		
		// S�quence des disques � d�placer (identifi�s par leur diam�tre)
		ArrayList<Integer> sequence = new ArrayList<Integer> (Arrays.asList(1, 2, 1, 3, 1, 2, 1, 0));
		
		while (!this.currentGame.isOver() && t == solverThread)
		{
			for (int diskNum : sequence)
			{
				if (t != this.solverThread)
				{
					break;
				}
				
				if (diskNum == 0 && !this.currentGame.isOver()) // D�place un �gros� disque (diam�tre >= 4)
				{
					Disk diskToMove = null;
					
					for(int i = 0; i <= 2; i++)
					{
						Disk otherDisk = this.currentGame.peekTower(i);
						
						if (otherDisk != null && (diskToMove == null || otherDisk.getDiameter() > diskToMove.getDiameter()))
						{
							if (findTowerWichCanReceiveDisk(otherDisk.getDiameter()) > -1)
							{
								diskToMove = otherDisk;
							}
						}
					}
					diskNum = diskToMove.getDiameter();
				}

				moveDiskNum(diskNum, (diskNum == 1) ? direction : 0);
				sleep(500);
			}
		}
	}
	
	// Trouve la tour dont le disque du dessus est le disque sp�cifi�
	private int findTowerWithDisk(int diskNum)
	{
		int towerNum = -1;
		
		// Trouve la tour dont le disque sur le dessus est le disque diskNum
		for(int i = 0; i <= 2; i++)
		{
			Disk d = this.currentGame.peekTower(i);
			if (d != null && d.getDiameter() == diskNum)
			{
				towerNum = i;
				break;
			}
		}
		
		return towerNum;
	}
	
	// Trouve la tour qui peut recevoir le disque sp�cifi�
	private int findTowerWichCanReceiveDisk(int diskNum)
	{
		int towerNum = -1;
		
		// Trouve la tour qui peut recevoir le disque
		for(int i = 0; i <= 2; i++)
		{
			Disk d = this.currentGame.peekTower(i);
			if (d == null || d.getDiameter() > diskNum)
			{
				towerNum = i;
				break;
			}
		}
		return towerNum;
	}
	
	// D�place le disque ayant le diam�tre sp�cifi� dans la direction sp�cifi�e 
	// Direction n�gative -> d�placement vers la gauche 
	// Direction positive -> d�placement vers la droite 
	// Direction nulle -> d�placement vers la tour qui peut recevoir le disque 
	private void moveDiskNum(int diskNum, int direction)
	{
		int fromTower = findTowerWithDisk(diskNum);
		
		if (fromTower > -1)
		{
			int toTower = -1;

			if (direction < 0) // D�placement � gauche
			{
				toTower = fromTower == 0 ? 2 : fromTower - 1;  
			}
			else if (direction > 0) // D�placement � droite
			{
				toTower = (fromTower + 1) % 3;
			}
			else // Direction non sp�cifi�s
			{
				// Trouve la tour qui peut recevoir le disque
				toTower = findTowerWichCanReceiveDisk(diskNum);
			}
			if (toTower > -1)
			{
				moveDisk(fromTower + 1, toTower + 1);
			}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}