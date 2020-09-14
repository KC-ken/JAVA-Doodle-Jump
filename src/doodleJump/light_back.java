package doodleJump;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class light_back {
	private static int HIEGHT = 750;

	private ImageIcon background;
	public light_back() {
		// set background
		background = new ImageIcon(light_back.class.getResource("stop.png"));
	}
	
	public void draw(Graphics myBuffer, int xLeft, int xRight) {
		myBuffer.drawImage(background.getImage(), xLeft, 0, xRight-xLeft, HIEGHT, null);
	}
}

