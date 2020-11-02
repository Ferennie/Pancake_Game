package Pancakes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Pancake {
	private static final int DEFAULT_PANCAKE_HEIGHT = 25;
	// private static final int MAX_DROPING_SIZE = 3;
	private BufferedImage pancake;
	private int x;
	private double y;
	public static double dropSpeed = 2.0;
	private boolean bumpCatcher;
	private PanCatcher catcher;
	private String bumpPosition = "";
	private int fallingWidth;
	public static boolean alrC;
	static public boolean incCount = true;

	public Pancake(PanCatcher catcher) {
		this.catcher = catcher;
		bumpCatcher = false;
		fallingWidth = catcher.getValidWidth();
		x = (int) (Math.random() * (PanGame.FrameWidth - fallingWidth));
		y = 0;
		try {
			// Import the Pancake image and scale it to the desired size
			pancake = ImageIO.read(new File("src/Pancakes/pan.png"));
			pancake = scale(pancake, fallingWidth, DEFAULT_PANCAKE_HEIGHT);

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private boolean checkBumpToCatcher() {

		// if pancake hits catcher
		if (catcher.getComparableY() < y + DEFAULT_PANCAKE_HEIGHT) {
			// if left edge of pancake is on catcher
			if ((x >= catcher.getComparableX() && x <= catcher.getComparableX() + catcher.getValidWidth())) {
				bumpCatcher = true;
				bumpPosition = "left";
				x = catcher.getX();
				// pancake = pancake.getSubimage(0, 0, (catcher.getComparableX()
				// +
				// catcher.getValidWidth() - x),
				// DEFAULT_PANCAKE_HEIGHT);
				// catcher.setComparableX(x);
				catcher.setComparableY(catcher.getComparableY() - pancake.getHeight());
				// catcher.setValidWidth(pancake.getWidth());
				catcher.addScore();

			} // if right edge of pancake is on catcher
			else if ((x + fallingWidth >= catcher.getComparableX()
					&& x + fallingWidth <= catcher.getComparableX() + catcher.getValidWidth())) {
				bumpCatcher = true;
				bumpPosition = "right";
				x = catcher.getX();
				catcher.setComparableY(catcher.getComparableY() - pancake.getHeight());
				catcher.addScore();
			}
		}

		return bumpCatcher;

	}

	// Increases the speed of dropping as well as moving the comparable height down
	// Only does if addSpeedCheck is true
	public void incSpeed() {
		if (addSpeedCheck()) {
			dropSpeed += 1.0;
			y = y + dropSpeed;
			catcher.setComparableY(catcher.getComparableY() + 5 * DEFAULT_PANCAKE_HEIGHT);
			catcher.remCheck();
		} else {
			// speed is the same and moves the pancake down by original speed
			y = y + dropSpeed;
		}
	}

	// checks when speed needs to be added
	public boolean addSpeedCheck() {
		// if score is divisible by 5
		if (catcher.getScore() % 5 == 0 && !incCount) {
			incCount = true;
			return true;
		} else if (catcher.getScore() % 5 == 1) {
			incCount = false;
			return false;
		} else {
			return false;
		}
	}

	// Makes sure the pancake can not go off the screen
	public boolean outOfRange() {
		if (!bumpCatcher) {
			if (y + DEFAULT_PANCAKE_HEIGHT >= catcher.getComparableY() + PanCatcher.DEFAULT_CATCHER_HEIGHT / 2)
				return true;
		}
		return false;
	}

	public void updateState() {
		// check if previous already bump catcher
		if (bumpCatcher) {
			if (bumpPosition.equals("right")) {
				x = catcher.getX();
			} else if (bumpPosition.equals("left")) {
				x = catcher.getComparableX() + catcher.getValidWidth() - pancake.getWidth();

			}

			return;
		}

		if (checkBumpToCatcher()) {
			return;
		}

		// not bump to catcher yet, keep moving
		incSpeed();
		catcher.remCheck();
	}

	// returns image of the pancake
	public BufferedImage getImage() {
		return pancake;
	}

	// returns x
	public int getX() {
		return x;
	}

	// returns y
	public double getY() {
		return y;
	}

	// Scales the picture
	public static BufferedImage scale(BufferedImage src, int w, int h) {
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		int x, y;
		int ww = src.getWidth();
		int hh = src.getHeight();
		int[] ys = new int[h];
		for (y = 0; y < h; y++)
			ys[y] = y * hh / h;
		for (x = 0; x < w; x++) {
			int newX = x * ww / w;
			for (y = 0; y < h; y++) {
				int col = src.getRGB(newX, ys[y]);
				img.setRGB(x, y, col);
			}
		}
		return img;
	}

}