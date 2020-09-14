package doodleJump;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class gameover_1 {
	private static int WIDTH = 400;
	private static int HIEGHT = 600;
	private ImageIcon replay, menu;
	private int bwidth, bstartx;
	private int bhieght, bstarty;

	public gameover_1() {
		// set button
		bwidth = 150;
		bhieght = 75;
		bstartx = 40;
		bstarty = HIEGHT / 2 +100;
		replay = new ImageIcon(gameover_1.class.getResource("replay.png"));
		menu = new ImageIcon(gameover_1.class.getResource("menu.png"));
	}

	public int scan(int x, int y) {
		y += 20;	//bias
		if (x < bstartx + bwidth && x > bstartx && y > bstarty && y < bstarty + bhieght) {
			return 1;
		} else if (x < bstartx + 50 + bwidth && x > bstartx + 50 && y > bstarty + bhieght + 10
				&& y < bstarty + bhieght + 10 + bhieght) {
			return 0;
		} else
			return 4;
	}

	public void draw(Graphics myBuffer, int score, int[] hightscore, int ybias) {
		if(score > hightscore[0])
			hightscore[0] = score;
		
		myBuffer.setColor(Color.black);
		myBuffer.setFont(new Font("²Ó©úÅé", Font.BOLD, 30));
		myBuffer.drawString("score : " + score, bstartx, bstarty-75 + ybias);
		myBuffer.drawString("History score : " +  hightscore[0], bstartx, bstarty-25 + ybias);
		myBuffer.drawImage(replay.getImage(), bstartx, bstarty + ybias, bwidth, bhieght, null);
		myBuffer.drawImage(menu.getImage(), bstartx + 50, bstarty + bhieght + 10 + ybias, bwidth, bhieght, null);

	}
}
