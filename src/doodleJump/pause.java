package doodleJump;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class pause {
	private static int WIDTH = 400;
	private static int HIEGHT = 600;
	private int score;
	private int mod;
	private ImageIcon pause, resume, menu;
	private int bwidth, bstartx;
	private int bhieght, bstarty;

	public pause() {
		// set button
		bwidth = 150;
		bhieght = 75;
		bstartx = 0;
		bstarty = 0;
		pause = new ImageIcon(pause.class.getResource("pause.png"));
		resume = new ImageIcon(pause.class.getResource("resume.png"));
		menu = new ImageIcon(pause.class.getResource("menu.png"));
	}

	public int scanInMode1(int x, int y) {
		if (x < bstartx + bwidth && x > bstartx && y > bstarty && y < bstarty + bhieght) {
			mod = 1;
			return 3;
		} else {
			return 1;
		}
	}

	public int scanInMode2(int x, int y) {
		if (x < bstartx + bwidth && x > bstartx && y > bstarty && y < bstarty + bhieght) {
			mod = 2;
			return 3;
		} else {
			return 2;
		}
	}

	public int scan(int x, int y) {
		y += 20;	//bias
		if (mod == 1) {
			if (x < bstartx + bwidth && x > bstartx && y > bstarty && y < bstarty + bhieght) {
				mod = 1;
				return 1;
			} else if (x < bstartx + 50 + bwidth && x > bstartx + 50 && y > bstarty + bhieght + 10
					&& y < bstarty + bhieght + 10 + bhieght) {
				return 0;
			}
		} else if (mod == 2) {
			if (x < bstartx + bwidth && x > bstartx && y > bstarty && y < bstarty + bhieght) {
				mod = 2;
				return 2;
			} else if (x < bstartx + 50 + bwidth && x > bstartx + 50 && y > bstarty + bhieght + 10
					&& y < bstarty + bhieght + 10 + bhieght) {
				return 0;
			}
		}
		return 3;
	}

	public void drawPauseBottom(Graphics myBuffer) {
		bhieght = 75;
		bwidth = 75;
		bstartx = WIDTH - bwidth;
		bstarty = 30;
		myBuffer.drawImage(pause.getImage(), bstartx, bstarty, bwidth, bhieght, null);

	}
	
	public void drawPauseBottom2(Graphics myBuffer) {
		bhieght = 75;
		bwidth = 75;
		bstartx = WIDTH - bwidth/2;
		bstarty = 30;
		myBuffer.drawImage(pause.getImage(), bstartx, bstarty, bwidth, bhieght, null);

	}

	public void draw(Graphics myBuffer) {
		if (mod == 1) {
			bstartx = WIDTH / 3;
			bstarty = HIEGHT / 2 + 50;
			bwidth = 150;
			bhieght = 75;
			myBuffer.drawImage(resume.getImage(), bstartx, bstarty, bwidth, bhieght, null);
			myBuffer.drawImage(menu.getImage(), bstartx + 50, bstarty + bhieght + 10, bwidth, bhieght, null);
		}
		if (mod == 2) {
			bstartx = WIDTH*2 / 3;
			bstarty = HIEGHT / 2 + 50;
			bwidth = 150;
			bhieght = 75;
			myBuffer.drawImage(resume.getImage(), bstartx, bstarty, bwidth, bhieght, null);
			myBuffer.drawImage(menu.getImage(), bstartx + 50, bstarty + bhieght + 10, bwidth, bhieght, null);
		}
	}
}
