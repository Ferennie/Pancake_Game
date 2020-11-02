package Pancakes;

import javax.swing.*;

public class PanGUI {


	public static void main(String[] args) {

	
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//creates a new frame
				JFrame frame = new GameFrame("Pancake Tower");
				frame.setVisible(true);
			}
		});
		
	}
}
