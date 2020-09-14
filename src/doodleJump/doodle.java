package doodleJump;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public class doodle {
	private ImageIcon[] doodle;
	private ImageIcon[] doodleR;
	private ImageIcon[] doodleH;
	private ImageIcon[] shield;
	private int dir;
	private int pressDir;		//keyboard press direction 
	private double x, y, dx, dy;
	private static int width = 40;
	private static int hieght = 40;
	private static int shieldW = 20;
	private int shieltX, shieltY;
	private static int WIDTH = 400;
	private static int HIEGHT = 600;
	private boolean sl; // slow down
	private boolean shieldOn;
	public boolean drop;
	private boolean gameOver;
	private boolean invincible;
	public boolean haveRocket; // whether it having Rocket
	public boolean haveHelicopter; // whether it having Helicopter
	public int itemTime;
	
	public doodle() {
		initial();
		
		// 0:toward left, 1:toward right, 2:shooting action
		doodle = new ImageIcon[3]; 
		doodle[0] = new ImageIcon(doodle.class.getResource("doodle0.png"));
		doodle[1] = new ImageIcon(doodle.class.getResource("doodle1.png"));
		doodle[2] = new ImageIcon(doodle.class.getResource("doodle2.png"));
		

		doodleH = new ImageIcon[2]; 
		doodleH[0] = new ImageIcon(doodle.class.getResource("helicopterDoodle0.png"));
		doodleH[1] = new ImageIcon(doodle.class.getResource("helicopterDoodle1.png"));
		
		doodleR = new ImageIcon[2]; 
		doodleR[0] = new ImageIcon(doodle.class.getResource("rocketDoodle0.png"));
		doodleR[1] = new ImageIcon(doodle.class.getResource("rocketDoodle1.png"));
		
		// 0:toward left, 1:toward right
		shield = new ImageIcon[2];
		shield[0] = new ImageIcon(doodle.class.getResource("goldShield0.png"));
		shield[1] = new ImageIcon(doodle.class.getResource("goldShield1.png"));
	}

	public void initial() {
		dir = 0;
		pressDir = 0;
		x = 200;
		y = 500;
		dx = 0;
		dy = 0;
		sl = false;
		shieldOn = false;
		gameOver = false;
		invincible = false;
		haveRocket = false;
		haveHelicopter = false;
		drop = false;
		itemTime = 0;
	}
	
	public int getx() {
		return (int) x;
	}

	public int gety() {
		return (int) y;
	}

	public int getWidth() {
		return width;
	}

	public int getHieght() {
		return hieght;
	}

	public double getdy() {
		return dy;
	}
	
	public boolean getShield() {
		return shieldOn;
	}
	
	public int getShieltX() {
		return shieltX;
	}
	
	public int getShieltY() {
		return shieltY;
	}
	
	public int getShieltW() {
		return shieldW;
	}
	
	public boolean getGameOver() {
		return gameOver;
	}
	
	public boolean getInvincible() {
		return invincible;
	}

	public void setx(int x1) {
		x = x1;
	}
	public void sety(int y1) {
		y = y1;
	}
	
	public void setDir(int d) {
		dir = d;
	}

	public void setdy(double dy1) {
		dy = dy1;
	}
	
	public void setWIDTH(int w) {
		WIDTH = w;
	}

	public void setHIEGHT(int h) {
		HIEGHT = h;
	}
	
	public void setShield(boolean s) {
		shieldOn = s;
	}
	
	public void setGameOver(boolean g) {
		gameOver = g;
	}
	
	public void setInvincible(boolean v) {
		invincible = v;
	}
	
	public void setItemTime(int t) {
		itemTime = t;
		invincible = true;
	}
	
	public void mvRight() {
		sl = false;
		pressDir = 1;
	}

	public void mvLeft() {
		sl = false;
		pressDir = -1;
	}

	public void slow() { // dx slow down
		sl = true;
	}

	public void cheat() { // cheat
		dy = 10;
	}

	public void jump() {
		
		if(itemTime > 0) {
			itemTime--;
			if(haveRocket) {
				if(dy < 6)
					dy += 0.1;
				else 
					dy = 6;
			}
			else if(haveHelicopter) {
				if(dy < 3.5)
					dy += 0.01;
				else 
					dy = 3.5;
			}
		}
		else {
			dy -= 0.037;
			haveRocket = false;
			haveHelicopter = false;
			invincible = false;
		}
		
		y -= dy;
		x += dx;
		double xdir = dx / Math.abs(dx);
		
		if (y >= HIEGHT - hieght) { //touch to bottom
//			y = HIEGHT - hieght;
//			dy = 4;
			drop = true;
		}

		if (sl) { // dx slow down
			if (Math.abs(dx) < 0.1)
				dx = 0;
			else
				dx -= 0.02 * xdir;
		}
		else {	// dx increasing
			dx += (pressDir * 2 - dx) * 0.03;	//fastest is +-2
		}
	}
	
	public void draw(Graphics myBuffer) {	//for single player
		if (x >= WIDTH) // pass through the screen
			x -= WIDTH;
		else if (x < 0)
			x += WIDTH;

		if (x >= WIDTH - width + 1) { // draw doodle when it near the border
			if(dir == 2) {		//shooting action
				myBuffer.drawImage(doodle[dir].getImage(), (int) x, (int) y-20, width, hieght+20, null);
				myBuffer.drawImage(doodle[dir].getImage(), (int) x - WIDTH, (int) y-20, width, hieght+20, null);
			}
			else if(haveRocket) {
				if(dir == 0) {
					myBuffer.drawImage(doodleR[dir].getImage(), (int) x, (int) y, width+15, hieght+13, null);
					myBuffer.drawImage(doodleR[dir].getImage(), (int) x - WIDTH, (int) y, width+15, hieght+13, null);
				}
				else {
					myBuffer.drawImage(doodleR[dir].getImage(), (int) x-15, (int) y, width+15, hieght+13, null);
					myBuffer.drawImage(doodleR[dir].getImage(), (int) x-15 - WIDTH, (int) y, width+15, hieght+13, null);
					}
			}
			else if (haveHelicopter) {
				myBuffer.drawImage(doodleH[dir].getImage(), (int) x, (int) y-13, width, hieght+13, null);
				myBuffer.drawImage(doodleH[dir].getImage(), (int) x - WIDTH, (int) y-13, width, hieght+13, null);
			}
			else {
				myBuffer.drawImage(doodle[dir].getImage(), (int) x, (int) y, width, hieght, null);
				myBuffer.drawImage(doodle[dir].getImage(), (int) x - WIDTH, (int) y, width, hieght, null);
			}
		} 
		else {
			if(dir == 2)		//shooting action
				myBuffer.drawImage(doodle[dir].getImage(), (int) x, (int) y-20, width, hieght+20, null);
			else if(haveRocket) {
				if(dir == 0)
					myBuffer.drawImage(doodleR[dir].getImage(), (int) x, (int) y, width+15, hieght+13, null);
				else
					myBuffer.drawImage(doodleR[dir].getImage(), (int) x-15, (int) y, width+15, hieght+13, null);
			}
			else if (haveHelicopter)
				myBuffer.drawImage(doodleH[dir].getImage(), (int) x, (int) y-13, width, hieght+13, null);
			else
				myBuffer.drawImage(doodle[dir].getImage(), (int) x, (int) y, width, hieght, null);
		}
	}
	
	public void draw2(Graphics myBuffer, int xLeft, int xRight) {	//for 2 player
		if (x >= xRight) // pass through the screen
			x -= (xRight-xLeft);
		else if (x < xLeft)
			x += (xRight-xLeft);

		if (x >= xRight - width + 1) { // draw doodle when it near the border
			int xp = 352 * (xRight - (int)x)/width;
			int yp = 352;
			
			myBuffer.drawImage(doodle[dir].getImage(), (int) x, (int) y, xRight, (int)y + hieght, 0, 0, xp, yp, null);
			myBuffer.drawImage(doodle[dir].getImage(), xLeft, (int) y, (int)x - (xRight-xLeft) + width, (int)y + hieght, xp, 0, 352, yp, null);
		} 
		else
			myBuffer.drawImage(doodle[dir].getImage(), (int) x, (int) y, width, hieght, null);
		
		if(shieldOn) {	//draw a shield
			shieltX = (int) (x + dir*(width+shieldW) - shieldW);
			shieltY = (int) y;
			myBuffer.drawImage(shield[dir].getImage(),  shieltX, shieltY, shieldW, hieght, null);
		}
	}
}
