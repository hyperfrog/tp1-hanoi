package appHanoi.form;

import appHanoi.model.Game;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
//import java.awt.Dimension;
import java.awt.Graphics;
//import java.awt.Point;
//import java.awt.Rectangle;
//import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * À MODIFIER ET COMPLÉTER: code, javadoc, standards (static/final),
 * commentaires
 */
public class GameBoard extends JPanel implements ActionListener
{
	private Game currentGame = null;
	
	private JLabel message = new JLabel("Prêt!");

    private JPanel buttonPanel;
    private JPanel towerButtonPanel;	
    private JPanel otherButtonPanel;
    private JPanel gamePanel;
//    private JButton cancelButton;
    private JButton replayButton;
    private JButton tower1Button;
    private JButton tower2Button;
    private JButton tower3Button;
	
	private boolean waitingForSelection = false;
	private int fromTower;
	
	public GameBoard()
	{
		super();
		
        buttonPanel = new JPanel();
        towerButtonPanel = new JPanel();
        otherButtonPanel = new JPanel();
		gamePanel = new JPanel();
        tower1Button = new JButton();
        tower2Button = new JButton();
        tower3Button = new JButton();
//        cancelButton = new JButton();
        replayButton = new JButton();

        this.setLayout(new BorderLayout());
        
        this.add(gamePanel, BorderLayout.CENTER);
        gamePanel.setBackground(Color.WHITE);

		JPanel messagePanel = new JPanel();
		messagePanel.add(this.message);
		this.message.setForeground(Color.red);
		this.add(messagePanel, BorderLayout.NORTH);

//        towerButtonPanel.setRequestFocusEnabled(false);
        towerButtonPanel.setLayout(new GridLayout(0, 3));

		this.tower1Button.addActionListener(this);
		this.tower2Button.addActionListener(this);
		this.tower3Button.addActionListener(this);

		this.towerButtonPanel.add(tower1Button);
		this.towerButtonPanel.add(tower2Button);
		this.towerButtonPanel.add(tower3Button);
		
		this.resetButtons();
		
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

        buttonPanel.add(otherButtonPanel, BorderLayout.PAGE_END);

        this.add(buttonPanel, BorderLayout.PAGE_END);

        this.currentGame = new Game(3); // TODO : Demander le nombre de disques au joueur
		
	}

	private void moveDisk(int from, int to)
	{
		if (this.currentGame.moveDisk(from - 1, to - 1))
		{
			if (!this.currentGame.isOver())
			{
				this.message.setText(String.format("Disque déplacé de la tour %s vers la tour %s.", from , to));
			}
			else
			{
				this.message.setText("Partie terminée !");
			}
		}
		else
		{
			this.message.setText(String.format("Déplacement impossible de la tour %s vers la tour %s.", from , to));
		}

		this.resetButtons();
		this.redraw();
		// this.repaint();
	}
	
	public void redraw()
	{
		Graphics g = this.gamePanel.getGraphics();
		if (g != null)
		{
			g.setClip(0, 0, this.gamePanel.getWidth(), this.gamePanel.getHeight());
			this.currentGame.redraw(g);
		}		
	}

	private void replay()
	{
		this.message.setText("Prêt!");

		this.currentGame = new Game(10); // TODO : Demander le nombre de disques au joueur
	
		this.redraw();
		
		this.resetButtons();
	}
	
	private void resetButtons()
	{
//		this.tower1Button.setEnabled(true);
//		this.tower2Button.setEnabled(true);
//		this.tower3Button.setEnabled(true);
		
        this.tower1Button.setActionCommand("1");
		this.tower1Button.setText("Tour 1");
		
		this.tower2Button.setActionCommand("2");
		this.tower2Button.setText("Tour 2");
		
		this.tower3Button.setActionCommand("3");
		this.tower3Button.setText("Tour 3");
	}

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
			this.message.setText("Prêt!");
		}
		else if (evt.getActionCommand().equals("REPLAY"))
		{
			this.replay();
		}
	}
	
}