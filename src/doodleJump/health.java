package doodleJump;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class health {
	private static int maxHP= 5;
	private int HP;
	private int width, hieght;
	private int redW, redH;
	private ImageIcon hpBar;
	public health() {
		initial();

		hpBar = new ImageIcon(health.class.getResource("HP.png"));
	}

	public void initial() {
		HP = maxHP;
		width = 150;
		hieght = 30;
		redW = 125;
		redH = 20;
	}
	
	public int getHP() {
		return HP;
	}
	
	public void setHP(int hp) {
		HP = hp;
		redW = 125 * HP/maxHP;
	}
	
	public void draw(Graphics myBuffer, int x, int y) {	//for 2 player
		myBuffer.setColor(Color.red);
		myBuffer.fillRect(x+20, y+5, redW, redH);
		myBuffer.drawImage(hpBar.getImage(), x, y, width, hieght, null);
	}
}
