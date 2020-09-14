package doodleJump;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class gameover_2 {
	private static int WIDTH = 800;
	private static int HIEGHT = 600;
	private ImageIcon replay, menu,win,winner,loser;
	private int bwidth, bstartx;
	private int bhieght, bstarty;
	private int resultx, resulty;
	private int result_hieght, result_width;
	private int winner_hieght, winner_width;
	public gameover_2() {
		// set button
		bwidth = 150;
		bhieght = 75;
		bstartx = 0;
		bstarty = 0;
		winner_hieght=35;
		winner_width=35;		
		replay = new ImageIcon(gameover_2.class.getResource("replay.png"));
		menu = new ImageIcon(gameover_2.class.getResource("menu.png"));
		win = new ImageIcon(gameover_2.class.getResource("win.png"));
		winner = new ImageIcon(gameover_2.class.getResource("winner.png"));
		loser = new ImageIcon(gameover_2.class.getResource("loser.png"));
	}

	public int scan(int x, int y) {
		y += 25;	//bias
		if (x < bstartx + bwidth && x > bstartx && y > bstarty && y < bstarty + bhieght) {
			return 2;
		} else if (x < bstartx + 50 + bwidth && x > bstartx + 50 && y > bstarty + bhieght + 10
				&& y < bstarty + bhieght + 10 + bhieght) {
			return 0;

		} else
			return 5;
	}

	public void draw(Graphics myBuffer, int who ) {
		bstartx = WIDTH / 3;
		bstarty = HIEGHT / 2 +100;
		bwidth = 150;
		bhieght = 75;
		
		if(who==2)
		{
			resultx = 100;
			resulty = 150;
			result_hieght= 200;
			result_width= 200;
			myBuffer.drawImage(win.getImage(), resultx,resulty,result_width, result_hieght, null);
			myBuffer.drawImage(loser.getImage(), resultx+400, resulty+20,result_width,result_hieght, null);
		}
		if(who==1)
		{
			resultx = 500;
			resulty = 150;
			result_hieght= 200;
			result_width= 200;
			myBuffer.drawImage(win.getImage(),resultx, resulty, result_width, result_hieght, null);
			myBuffer.drawImage(loser.getImage(), resultx-400, resulty+20,result_width, result_hieght, null);
		}
		myBuffer.drawImage(replay.getImage(), bstartx, bstarty, bwidth, bhieght, null);
		myBuffer.drawImage(menu.getImage(), bstartx + 50, bstarty + bhieght + 10, bwidth, bhieght, null);

	}
	public void drawMedal(Graphics myBuffer, int who ,int winner1_x,int winner1_y,int winner2_x,int winner2_y) {
		if(who==2)
		{
			myBuffer.drawImage(winner.getImage(),winner1_x+5 ,winner1_y+20,winner_width,winner_hieght, null);
		}
		if(who==1)
		{
			myBuffer.drawImage(winner.getImage(), winner2_x+3, winner2_y+20,winner_width,winner_hieght, null);
		}
	}
}
