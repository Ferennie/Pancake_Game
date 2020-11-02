package Pancakes;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private AnimationPanel ap;
	public static boolean stop = true;

	public GameFrame(String title) {
		super(title);

		// set layout manager
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		ap = new AnimationPanel();
		add(ap);
		pack();
		setLocationRelativeTo(null);

		// All the responses to keys pressed during the game
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// If the game is over
				if (ap.getPanGame().isOver()) {
					// If the button pressed is [r]
					if (e.getKeyCode() == KeyEvent.VK_R) {
						// Confirm with the user if they want to play again
						int run = JOptionPane.showConfirmDialog(null, "Are you sure you want to play again?", "Broski",
								// Provide a Yes or No Option
								JOptionPane.YES_NO_OPTION);
						// If Yes is clicked
						if (run == 0) {
							// restart game
							remove(ap);
							ap = new AnimationPanel();
							add(ap);
							pack();
							setLocationRelativeTo(null);
							// resets static variables
							Pancake.dropSpeed = 2.0;
							Pancake.incCount = true;
							PanCatcher.remOnce = true;

						} else {
							// close window
							System.exit(run);
						}
					}
				}
				// If the game is not paused and the game HAS started
				if (!ap.getPanGame().isPaused() && ap.getPanGame().isStarted()) {
					// If the user pressed the LEFT arrow key
					if (e.getKeyCode() == KeyEvent.VK_LEFT) {
						// move left
						ap.getPanGame().getCatcher().moveLeft();
					}
					// if the user pressed the RIGHT arrow key
					else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
						// move right
						ap.getPanGame().getCatcher().moveRight();
					}
				}
				// If the user pressed [s]
				if (e.getKeyCode() == KeyEvent.VK_S) {
					// If the game HAS NOT started
					if (!ap.getPanGame().isStarted()) {
						// Unpause the game
						ap.getPanGame().resumeGame();
						ap.getPanGame();
						// Set start equal to true
						// Start never gets set back to false
						PanGame.start = true;
						ap.getPanGame().resumeGame();
					}

				}
				// If user presses [i] for instructions
				if (e.getKeyCode() == KeyEvent.VK_I) {
					if (!ap.getPanGame().isStarted()) {
						JOptionPane.showMessageDialog(null,
								"Use the arrow keys to move " + " \nPress [Space] anytime during the game to pause "
										+ " \nScore will be displayed in the top left"
										+ " \nIf any pancakes drop past the top of your stack, you lose."
										+ " \nSpeed will increase every 5 pancakes"
										+ " \nPancake stack will be reset whenever speed increases"
										+ " \nHighest score wins. Enjoy!");
					}
				}
				// If the user pressed [space]
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					// If the game IS paused
					if (ap.getPanGame().isPaused()) {
						// resume the game
						ap.getPanGame().resumeGame();
					}
					// If the game IS NOT paused
					else {
						// Pause the game
						ap.getPanGame().pauseGame();
					}
				}

			}
		});

	}
}