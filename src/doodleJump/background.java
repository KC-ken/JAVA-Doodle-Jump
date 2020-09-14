package doodleJump;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class background {
	private static int HIEGHT = 600;

	private ImageIcon background;

	public background() {
		// set background
		background = new ImageIcon(background.class.getResource("background.png"));
	}
	
	public void draw(Graphics myBuffer, int xLeft, int xRight) {
		myBuffer.drawImage(background.getImage(), xLeft, 0, xRight-xLeft, HIEGHT, null);
	}
}
