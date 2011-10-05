package appHanoi.form;

import appHanoi.model.Game;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * À MODIFIER ET COMPLÉTER: code, javadoc, standards (static/final),
 * commentaires
 */
public class GameBoard extends JPanel implements ActionListener
{
	public static final Point GAME_BOARD_LOCATION = new Point(10, 30);
	public static final Dimension GAME_BOARD_DIMENSION = new Dimension(775, 350);

	private Game currentGame = null;
	
	private JLabel message = new JLabel("Prêt!");
	private JButton tower1Button = new JButton("Tour 1");
	private JButton tower2Button = new JButton("Tour 2");
	private JButton tower3Button = new JButton("Tour 3");
	
	private JButton cancelButton = new JButton("Annuler");
	private JButton replayButton = new JButton("Nouvelle Partie...");
	
	private boolean waitingForSelection = false;
	private int fromTower;
	
	public GameBoard()
	{
		super();
		
		this.setLayout(new BorderLayout());

		JPanel messagePanel = new JPanel();
		messagePanel.setBackground(Color.LIGHT_GRAY);
		messagePanel.add(this.message);
		this.message.setForeground(java.awt.Color.red);
		this.add(messagePanel, BorderLayout.NORTH);

		this.currentGame = new Game(5); // TODO : Demander le nombre de disques au joueur
//		this.redraw();
//		this.repaint(1000);
		
		this.tower1Button.setActionCommand("1");
		this.tower1Button.addActionListener(this);
		
		this.tower2Button.setActionCommand("2");
		this.tower2Button.addActionListener(this);
		
		this.tower3Button.setActionCommand("3");
		this.tower3Button.addActionListener(this);
		
		this.cancelButton.setActionCommand("CANCEL");
		this.cancelButton.addActionListener(this);
		
		this.replayButton.setActionCommand("REPLAY");
		this.replayButton.addActionListener(this);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(Color.LIGHT_GRAY);
		buttonsPanel.add(this.tower1Button);
		buttonsPanel.add(this.tower2Button);
		buttonsPanel.add(this.tower3Button);
		buttonsPanel.add(this.cancelButton);
		buttonsPanel.add(this.replayButton);
		
		this.add(buttonsPanel, BorderLayout.SOUTH);
		
		this.setBackground(Color.WHITE);
		
	}

	private void moveDisk(int from, int to)
	{
		if (this.currentGame.moveDisk(from - 1, to - 1))
		{
			this.message.setText("Disque déplacé de : " + from + " à " + to);
		}
		else
		{
			this.message.setText("Déplacement impossible de : " + from + " à " + to);
		}

		this.resetButtons();
		this.redraw();
		// this.repaint();
	}
	
	private void redraw()
	{
		Graphics g = this.getGraphics();
		if (g != null)
		{
//			g.clearRect(GAME_BOARD_LOCATION.x, GAME_BOARD_LOCATION.y, GAME_BOARD_DIMENSION.width, GAME_BOARD_DIMENSION.height);
			this.currentGame.redraw(g.create(
					GAME_BOARD_LOCATION.x, 
					GAME_BOARD_LOCATION.y, 
					GAME_BOARD_DIMENSION.width, 
					GAME_BOARD_DIMENSION.height));
		}
	}

	private void replay()
	{
		this.message.setText("Prêt!");

//		this.currentGame.replay(3); // TODO : Utilisé pour tester uniquement.
		this.currentGame = new Game(10); // TODO : Demander le nombre de disques au joueur
	
		this.redraw();
		// this.repaint();
		
		this.resetButtons();
	}
	
	private void resetButtons()
	{
		this.tower1Button.setEnabled(true);
		this.tower2Button.setEnabled(true);
		this.tower3Button.setEnabled(true);
	}

	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getActionCommand().equals("1") || evt.getActionCommand().equals("2") || evt.getActionCommand().equals("3"))
		{
			JButton bSrc = (JButton) evt.getSource();
			
			if (waitingForSelection)
			{
				this.moveDisk(this.fromTower, Integer.parseInt(bSrc.getActionCommand()));
			}
			else
			{
				this.fromTower = Integer.parseInt(bSrc.getActionCommand());
				bSrc.setEnabled(false);
			}
			
			waitingForSelection = !waitingForSelection;
		}
		else if (evt.getActionCommand().equals("CANCEL"))
		{
			this.waitingForSelection = false;
			this.resetButtons();
		}
		else if (evt.getActionCommand().equals("REPLAY"))
		{
			this.replay();
		}
	}

	@Override
	public void paint(Graphics g)
	{
//		System.out.println("paint() appelé !");
		super.paint(g);
		this.redraw();
	}
	
//	@Override
//	public void update(Graphics g)
//	{
//		System.out.println("update() appelé !");
//	}
	
}