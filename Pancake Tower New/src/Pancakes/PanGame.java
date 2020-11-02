package Pancakes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;

public class PanGame {

	public static int FrameWidth = 1250;
	public static int FrameHeight = 768;
	// private static final int MAX_PAN_ADDED = 3;
	private static final int MAX_SKIP_COUNT = 300;
	public static List<Pancake> pans;
	private PanCatcher catcher;
	private int skipCnt;
	private static boolean pause;
	public static boolean start;
	private BufferedImage titlePage;
	private BufferedImage endPage;
	private boolean gameOver;
	private BufferedImage backdrop;
	private BufferedImage paused;
	// private boolean remOnce;

	public PanGame() {
		// Start the game with everything declared as False as none of it has happened
		gameOver = false;
		pause = false;
		start = false;
		// Create new objects for the Pancakes and Catcher
		pans = new LinkedList<Pancake>();
		catcher = new PanCatcher();
		addNewPancake();
		try {
			// Load in all the Backgrounds from src/Pancakes
			// Also scale it to the correct size to make all the backgrounds the same length
			// and width
			backdrop = ImageIO.read(new File("src/Pancakes/Backdrop.png"));
			backdrop = Pancake.scale(backdrop, FrameWidth, FrameHeight);
			titlePage = ImageIO.read(new File("src/Pancakes/TitlePancake.png"));
			titlePage = Pancake.scale(titlePage, FrameWidth, FrameHeight);
			endPage = ImageIO.read(new File("src/Pancakes/GameOver.png"));
			endPage = Pancake.scale(endPage, FrameWidth, FrameHeight);
			paused = ImageIO.read(new File("src/Pancakes/paused.png"));
			paused = Pancake.scale(paused, FrameWidth, FrameHeight);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// Gets the background for the game if the game is paused
	public BufferedImage getpaused() {
		return paused;
	}

	// Gets the background for the game when the game starts
	public BufferedImage getBackdrop() {
		return backdrop;
	}

	// Gets the background for the game when the game is not paused and has not
	// started
	public BufferedImage getTitlePage() {
		return titlePage;
	}

	// Gets the background for the game when the game is over
	public BufferedImage getEndPage() {
		return endPage;
	}

	// Check if the game has started or not
	public boolean isStarted() {
		return start;
	}

	// Check if the game is over or not
	public boolean isOver() {
		return gameOver;
	}

	// Unpause the game
	public void resumeGame() {
		pause = false;
	}

	// Pause the game
	public void pauseGame() {
		pause = true;
	}

	// Checkwhether or not the game is paused
	public boolean isPaused() {
		return pause;
	}

	// Adds new Pancakes
	private void addNewPancake() {
		// time interval
		skipCnt = MAX_SKIP_COUNT;
		// how many pancakes should be added per time interval
		int num = 1;
		for (int i = 0; i < num; ++i) {
			pans.add(new Pancake(catcher));
		}
	}

	public void refreshForAnimation() {
		if (pause || gameOver || !start) {
			return;
		}
		// we need to use iterator to go through pans, because while we iterate
		// through the list we want to delete element in the list.
		// other method such as for loop will cause problem.
		for (Iterator<Pancake> iterator = pans.iterator(); iterator.hasNext();) {
			Pancake p = iterator.next();
			p.updateState();
			if (p.outOfRange()) {
				// Remove the current element from the iterator and the list.
				iterator.remove();
				gameOver = true;
			}
		}

		if (--skipCnt <= 0) {
			addNewPancake();
		}
	}

	// returns list "pans"
	public List<Pancake> visiblePans() {
		return pans;
	}

	// returns catcher
	public PanCatcher getCatcher() {
		return catcher;
	}

}
