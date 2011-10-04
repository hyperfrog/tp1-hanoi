package appHanoi.form;

import appHanoi.model.Game;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.BorderLayout;
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
	private JButton tower1Button = new JButton("Tour 1");
	private JButton tower2Button = new JButton("Tour 2");
	private JButton tower3Button = new JButton("Tour 3");
	
	private JButton cancelButton = new JButton("Annuler");
	private JButton replayButton = new JButton("Nouvelle Partie...");
	
	private boolean waitingForSelection = false;
	private int lastButton;
	
	public GameBoard()
	{
		super();
		
		this.setLayout(new BorderLayout());

		JPanel messagePanel = new JPanel();
		messagePanel.add(this.message);
		this.message.setForeground(java.awt.Color.red);
		this.add(messagePanel, BorderLayout.NORTH);

		this.currentGame = new Game();
		
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
		buttonsPanel.add(this.tower1Button);
		buttonsPanel.add(this.tower2Button);
		buttonsPanel.add(this.tower3Button);
		buttonsPanel.add(this.cancelButton);
		buttonsPanel.add(this.replayButton);
		
		this.add(buttonsPanel, BorderLayout.SOUTH);
	}

	private void moveDisk(int from, int to)
	{
		this.message.setText("déplacement réussi de : " + from + " à " + to);
		
		this.currentGame.moveDisk(from, to);
		this.resetButtons();
		
		// this.repaint();
	}

	private void replay()
	{
		this.message.setText("nouvelle partie...");

		this.currentGame.replay(3); // TODO : Utilisé pour tester uniquement.
		this.currentGame.redraw(this.getGraphics());
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
				this.moveDisk(this.lastButton, Integer.parseInt(bSrc.getActionCommand()));
			}
			else
			{
				this.lastButton = Integer.parseInt(bSrc.getActionCommand());
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
}