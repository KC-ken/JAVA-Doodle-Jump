package doodleJump;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class menu {
	private static int WIDTH = 400;
	private static int HIEGHT = 600;
	private ImageIcon start, about, word, contest;
	private int bwidth, bstartx;
	private int bhieght, bstarty;

	private ufo ufo;

	public menu() {
		// set button
		bwidth = 150;
		bhieght = 75;
		bstartx = 200;
		bstarty = 300;
		start = new ImageIcon(menu.class.getResource("play1.png"));
		about = new ImageIcon(menu.class.getResource("about2.png"));
		contest = new ImageIcon(menu.class.getResource("contest.png"));

		// set word
		word = new ImageIcon(menu.class.getResource("word2.png"));

		// set monster
		ufo = new ufo(bstartx, bstarty - 300, 0.25, 0, 0);
		ufo.setHieght(100);
		ufo.setWidth(100);
		ufo.setVisible(true);
	}

	public int scan(int x, int y) {
		y += 20;	//bias
		
		if (x < bstartx + bwidth && x > bstartx && y > bstarty && y < bstarty + bhieght)
			return 1; // mod change to 1
		else if (x < bstartx + bwidth && x > bstartx && y > bstarty + 2 * (bhieght + 10)
				&& y < bstarty + 2 * (bhieght + 10) + bhieght)
			return 2; // mod change to 2
		else if (x < bstartx + bwidth && x > bstartx && y > bstarty + bhieght + 10
				&& y < bstarty + bhieght + 10 + bhieght)
			return 6; // mod change to 6
		else
			return 0;

	}

	public void draw(Graphics myBuffer) {
		ufo.Circlelmove(200, 200, 50);
		ufo.draw(myBuffer);

		myBuffer.drawImage(start.getImage(), bstartx, bstarty, bwidth, bhieght, null);
		myBuffer.drawImage(about.getImage(), bstartx, bstarty + bhieght + 10, bwidth, bhieght, null);
		myBuffer.drawImage(contest.getImage(), bstartx, bstarty + 2 * (bhieght + 10), bwidth, bhieght, null);
		myBuffer.setColor(Color.black);
		myBuffer.drawImage(word.getImage(), 40, 0, 346, 204, null);

	}
}
