package Pancakes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class PanCatcher {
	public static int DEFAULT_CATCHER_WIDTH = 200;
	public static int DEFAULT_CATCHER_HEIGHT = 50;
	private static final int DEFAULT_SPEED = 30;
	private BufferedImage catcher;
	private int x;
	private int y;
	private int validWidth;
	private int comparableY;
	private int comparableX;
	public int score;
	public static boolean remOnce = true;

	private int speed;

	public PanCatcher() {
		speed = DEFAULT_SPEED;
		validWidth = DEFAULT_CATCHER_WIDTH;
		score = 0;
		x = (PanGame.FrameWidth - DEFAULT_CATCHER_WIDTH) / 2;
		y = PanGame.FrameHeight - DEFAULT_CATCHER_HEIGHT - 15;
		comparableY = y;
		comparableX = x;
		try {
			// This is taking in the image of the plate
			catcher = ImageIO.read(new File("src/Pancakes/plate.png"));
			// Scales the plate to the desired size in case the file is too large or too
			// small
			catcher = Pancake.scale(catcher, DEFAULT_CATCHER_WIDTH, DEFAULT_CATCHER_HEIGHT);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	// get Width of top pancake
	public int getValidWidth() {
		return validWidth;
	}

	// set Width of new top pancake
	public void setValidWidth(int width) {
		validWidth = width;
	}

	// get x coordinate of top
	public int getComparableX() {
		return comparableX;
	}

	// set x coordinate of new top
	public void setComparableX(int x) {
		comparableX = x;
	}

	// get y coordinate of top
	public int getComparableY() {
		return comparableY;
	}

	// set y coordinate of new top
	public void setComparableY(int y) {
		comparableY = y;
	}

	// moveLeft() to be implemented in GameFrame
	// Moves the plate to the left
	public void moveLeft() {
		x -= speed;
		if (x < 0) {
			x = 0;
		}
		comparableX -= speed;
		if (comparableX < 0) {
			comparableX = 0;
		}
	}

	// moveRight() to be implemented in GameFrame
	// Moves the plate to the right
	public void moveRight() {
		x += speed;
		if (x > PanGame.FrameWidth - DEFAULT_CATCHER_WIDTH - 2)
			x = PanGame.FrameWidth - DEFAULT_CATCHER_WIDTH - 2;
		comparableX += speed;
		if (comparableX > PanGame.FrameWidth - DEFAULT_CATCHER_WIDTH - 2)
			comparableX = PanGame.FrameWidth - DEFAULT_CATCHER_WIDTH - 2;
	}

	public void updatePosition() {
		// do nothing
	}

	public BufferedImage getImage() {
		return catcher;
	}

	// x coordinate of original Catcher
	public int getX() {
		return x;
	}

	// y coordinate of original Catcher
	public int getY() {
		return y;
	}

	// score of game: how many pancakes have been stacked
	public int getScore() {
		return score;
	}

	// add to score and change y coordinate of top
	public void addScore() {
		score++;

	}

	// Removes 5 pancakes whenever the score is divisible by 5
	public boolean remCheck() {
		if (getScore() % 5 == 0 && !remOnce) {
			remOnce = true;
			// removes all panckaes in the list
			for (int i = 0; i < 6; i++) {
				PanGame.pans.remove(PanGame.pans.size() - 1);
			}
			return true;
			// else, set the remOnce back to false and prep for next remove
		} else if (getScore() % 5 == 1) {
			remOnce = false;
			return false;
		} else {
			return false;
		}
	}
}
