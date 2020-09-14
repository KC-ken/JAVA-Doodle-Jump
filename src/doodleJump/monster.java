package doodleJump;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class monster {
	private ImageIcon[] monster;
	private double x, y, dx, dy;
	private int skin;
	private int width = 150;
	private int hieght = 150;
	private boolean visible;
	public monster(double x, double y, double dx, double dy, int skin) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
		this.skin = skin;
		monster = new ImageIcon[10];
		monster[0] = new ImageIcon(monster.class.getResource("ufo.png"));
		monster[1] = new ImageIcon(monster.class.getResource("frog1.png"));
		monster[2] = new ImageIcon(monster.class.getResource("frog2.png"));
		monster[3] = new ImageIcon(monster.class.getResource("monster1.png"));
		monster[4] = new ImageIcon(monster.class.getResource("monster2.png"));
		visible =false;
	}

	public double getX() {
		return x;
	}

	public int getSkin() {
		return skin;
	}

	public void setSkin(int skin) {
		this.skin = skin;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getDx() {
		return dx;
	}

	public void setDx(double dx) {
		this.dx = dx;
	}

	public double getDy() {
		return dy;
	}

	public void setDy(double dy) {
		this.dy = dy;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHieght() {
		return hieght;
	}

	public void setHieght(int hieght) {
		this.hieght = hieght;
	}
	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public void move() {
		setX(getX() + dx);
		setY(getY() + dy);
	}
	public void initial() {
		setX(0);
		setY(0);
		visible = false;
	}
	public void mvdown(int y1) { // for mvScreen
		y += y1;
		if (y > 600) {
			visible = false;
		}
	}
	public void mvUp(int y1) { // for die
		y -= y1;
		if (y + hieght < 0) {
			visible = false;
		}
	}
	public void draw(Graphics myBuffer) {
		if(visible)
			myBuffer.drawImage(monster[skin].getImage(), (int) x, (int) y, (int) width, (int) hieght, null);
	}

}
