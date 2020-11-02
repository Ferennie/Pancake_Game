package Pancakes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Font;

public class AnimationPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private PanGame game;
	private Font scoreFont;

	public AnimationPanel() {
		game = new PanGame();
		// Initializes scoreFont as a specified font and size
		scoreFont = new Font("Comic Sans MS", Font.BOLD, 24);
		// Initializes the timer
		Timer timer = new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				game.refreshForAnimation();
				repaint();
			}
		});
		timer.setRepeats(true);
		timer.setCoalesce(true);
		timer.start();
	}

	public PanGame getPanGame() {
		return game;
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(PanGame.FrameWidth, PanGame.FrameHeight);
	}

	// Most to do with the graphics of the game
	// The drawing of backgrounds and fonts
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// If the game is not paused and it has not started, background should be
		// TitlePage
		if (!game.isPaused()) {
			g.drawImage(game.getTitlePage(), 0, 0, this);
		}
		// When the game starts, switch the background to the game play background
		if (game.isStarted()) {
			g.drawImage(game.getBackdrop(), 0, 0, this);
		}

		// draws out the pancakes
		for (Pancake p : game.visiblePans()) {
			g.drawImage(p.getImage(), p.getX(), (int) p.getY(), this);
		}
		// If the game is paused, show the pause screen
		// Because this code is after the drawing of the pancakes, the paused screen
		// goes over the pancakes
		if (game.isPaused()) {
			g.drawImage(game.getpaused(), 0, 0, this);
		}
		// draws out the plate at the desired X and Y which are constantly updating
		PanCatcher catcher = game.getCatcher();
		g.drawImage(catcher.getImage(), catcher.getX(), catcher.getY(), this);

		// When the game is over, switch the background to show Game Over
		if (game.isOver()) {
			g.drawImage(game.getEndPage(), 0, 0, this);
		}
		// sets the overall font and prints it to the top right of the screen
		g.setFont(scoreFont);
		g.setColor(Color.BLACK);
		// Shows the score and the speed in the top left corner of the screen
		g.drawString("Score: " + catcher.getScore(), 15, 30);
		g.drawString("Speed = " + Pancake.dropSpeed, 15, 60);

		// if the game isn't started, have the background be the TitlePage
		if (!game.isStarted()) {
			g.drawImage(game.getTitlePage(), 0, 0, this);
		}

	}

}
