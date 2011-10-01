package appHanoi.form;

import appHanoi.model.Game;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;

/**
 * À MODIFIER ET COMPLÉTER: code, javadoc, standards (static/final),
 * commentaires
 */
public class GameBoard extends JPanel implements ActionListener
{

	private Game currentGame = null;

	private JLabel message = new JLabel("Prêt!");
	private JButton moveButton = new JButton("Déplacement...");
	private JButton replayButton = new JButton("Nouvelle Partie...");

	public GameBoard()
	{
		super();
		this.setLayout(new BorderLayout());

		JPanel messagePanel = new JPanel();
		messagePanel.add(this.message);
		this.message.setForeground(java.awt.Color.red);
		this.add(messagePanel, BorderLayout.NORTH);

		this.currentGame = new Game();
//		this.add(this.currentGame, BorderLayout.CENTER);

		this.moveButton.setActionCommand("MOVE_DISK");
		this.moveButton.addActionListener(this);
		this.replayButton.setActionCommand("REPLAY");
		this.replayButton.addActionListener(this);
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.add(this.moveButton);
		buttonsPanel.add(this.replayButton);
		this.add(buttonsPanel, BorderLayout.SOUTH);
	}

	private void moveDisk()
	{
		this.message.setText("déplacement réussi...");
		// this.currentGame.moveDisk(1,2);
//		this.currentGame.repaint();
	}

	private void replay()
	{
		this.message.setText("nouvelle partie...");
//		this.currentGame.repaint();
	}

	public void actionPerformed(ActionEvent evt)
	{
		if (evt.getActionCommand().equals("MOVE_DISK"))
		{
			this.moveDisk();
		}
		else if (evt.getActionCommand().equals("REPLAY"))
		{
			this.replay();
		}
	}
}