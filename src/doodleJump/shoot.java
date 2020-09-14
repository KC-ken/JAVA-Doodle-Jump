package doodleJump;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class shoot {
	private ImageIcon[] ntust;
	private int WIDTH = 400;
	private int HIEGHT = 600;
	private static int width = 15;
	private static int hieght = 15;
	private double x, y, dx, dy;
	private boolean visible;
	private static double speed = 8;
	private boolean inMyScreen;
	private int c;	//0:blue, 1:red
	
	public shoot() {
		initial();
		
		ntust = new ImageIcon[2];
		ntust[0] = new ImageIcon(shoot.class.getResource("NTUST.png"));
		ntust[1] = new ImageIcon(shoot.class.getResource("NTUST2.png"));
	}
	
	public void initial() {
		inMyScreen = true;
		visible = false;
		c = 0;
	}
	
	public int getx() {
		return (int) x;
	}

	public int gety() {
		return (int) y;
	}
	
	public int getW() {
		return width;
	}

	public int getH() {
		return hieght;
	}
	
	public boolean getVisible() {
		return visible;
	}
	
	public void setx(double x1) {
		x = x1;
	}
	
	public void sety(double y1) {
		y = y1;
	}
	
	public void setVisible(boolean v) {
		visible = v;
	}
	
	public void setColor(int c1) {
		c = c1;
	}
	
	public void fire(int xDoodle, int yDoodle, int xCursor, int yCursor) {	//for single player
		x = xDoodle + 35 - width;
		y = yDoodle - hieght;
		visible = true;
		
		if(x > WIDTH)
			x -= WIDTH;
		
		double r = Math.sqrt(Math.pow((xCursor - x),2 ) + Math.pow((yCursor - y),2 ));
		dx = speed * (xCursor - x)/r;
		dy = speed * (yCursor - y)/r;
	}
	
	public void fire2(int xDoodle, int yDoodle, int dir, double dyDoodle) {	//for multi-player
		x = xDoodle + dir*(40 + width) - width;
		y = yDoodle + 10;
		visible = true;
		inMyScreen = true;
				
		dx = (dir*2 - 1) * speed/3;
		dy = -1 * dyDoodle/2;//oppose to doodle
	}
	
	public void move1() {	//move bullet for single player
		if(visible) {
			x += dx;
			y += dy;
			
			if(x > WIDTH)
				visible = false;
			else if(x < 0 - width)
				visible = false;
			if(y > HIEGHT)
				visible = false;
			else if(y < 0 - hieght)
				visible = false;
		}
		
	}
	
	public void move2(int xLeft1, int xRight1, int xLeft2, int xRight2) {	//move bullet for multi-player
		//xLeft1, xRight1 :for my screen
		//xLeft2, xRight2 :for anemy screen
		
		if(visible) {
			x += dx;
			y += dy;
			
			if(inMyScreen) {
				if(x > xRight1) {
					inMyScreen = false;
					x = xLeft2;
				}
				else if(x < xLeft1) {
					inMyScreen = false;
					x = xRight2;
				}
			}
			else {
				if(x > xRight2)
					visible = false;
				else if(x < xLeft2)
					visible = false;
			}
			
			if(y > HIEGHT)
				visible = false;
			else if(y < 0 - hieght)
				visible = false;
		}
		
	}
	
	public void draw(Graphics myBuffer) {
		if (visible)
				myBuffer.drawImage(ntust[c].getImage(), (int) x, (int) y, width, hieght, null);
	}
}
