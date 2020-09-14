package doodleJump;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class monster_weapon {
	private ImageIcon[] monster_weapon;
	private double x, y, dx, dy;
	private int skin;
	private int width = 40;
	private int hieght = 40;
	private boolean visible;
	public monster_weapon(double x, double y, double dx, double dy, int skin)  {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy =dy;
		this.skin =skin;
		monster_weapon = new ImageIcon[3];
		monster_weapon[1] = new ImageIcon(monster_weapon.class.getResource("bullete1.png"));
		monster_weapon[2] = new ImageIcon(monster_weapon.class.getResource("bullete2.png"));
		visible =false;
	}
	public double getX() {
		return x;
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
	public int getSkin() {
		return skin;
	}
	public void setSkin(int skin) {
		this.skin = skin;
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
	public void move(double Edge,double start) {
		if (isVisible()) {
			setX(getX() + dx); // x = x + dx
			setY(getY() + dy);

			// check for left & right edge bounces
			if (getX() >= Edge ) // hits the right edge
			{
				setX(start);
				setVisible(false);
			}
			if (getX() <= 0 ) // hits the right edge
			{
				setX(start);
				setVisible(false);
			}
		}
	}
	public void draw(Graphics myBuffer) {
		if(visible)
			myBuffer.drawImage(	monster_weapon[skin].getImage(), (int) x, (int) y, (int) width, (int) hieght, null);
	}
}
