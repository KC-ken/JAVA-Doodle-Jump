package doodleJump;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class about {
	private static int WIDTH = 800;
	private static int HIEGHT = 600;
	private ImageIcon about, menu;
	private int bwidth, bstartx;
	private int bhieght, bstarty;
	public about() {
		menu = new ImageIcon(about.class.getResource("menu.png"));
		about = new ImageIcon(about.class.getResource("about.png"));
		bwidth=150;
		bhieght=75;
		bstartx=WIDTH - 200;
		bstarty=HIEGHT -100;		
	}

	public int scan(int x, int y) {
		y += 25;
		if (x < bstartx + bwidth && x > bstartx && y > bstarty && y < bstarty + bhieght)
		{
			return 0;
		} else
			return 6;
	}
	public void draw(Graphics myBuffer) {
		
		myBuffer.drawImage(about.getImage(),0, 0, WIDTH, HIEGHT, null);
		myBuffer.drawImage(menu.getImage(), bstartx, bstarty, bwidth, bhieght, null);

	}
}
