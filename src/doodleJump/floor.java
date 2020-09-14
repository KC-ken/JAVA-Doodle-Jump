package doodleJump;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class floor {

	private int WIDTH = 400;
	private int HIEGHT = 600;
	private static int width = 50;
	private static int hieght = 10;
	private static int springW = 10; // spring width
	private static int springH = 10; // spring hieght
	private ImageIcon green;
	private ImageIcon blue;
	private ImageIcon spring;
	private double probG; // probability of green floor
	private int c; // color:0->green, 1->blue
	private double xgn, ygn;// floor's position
	private double xsp, ysp;// spring's position
	private boolean visible;
	private double dx; // speed of blue floor
	private double probS; // probability of floor having spring
	public boolean haveSpring; // whether it having spring
	private boolean stretch; // stretch spring

	// items
	private ImageIcon rocket;
	private ImageIcon helicopter;
	private int widthI = 25;
	private int hieghtR = 30;
	private int hieghtH = 15;
	private int xr, yr, xh, yh;
	public boolean haveRocket; // whether it having Rocket
	public boolean haveHelicopter; // whether it having Helicopter

	public floor() {
		green = new ImageIcon(floor.class.getResource("greenFloor2.png"));
		blue = new ImageIcon(floor.class.getResource("blueFloor.png"));

		spring = new ImageIcon(floor.class.getResource("spring.png"));

		rocket = new ImageIcon(floor.class.getResource("rocket.png"));
		helicopter = new ImageIcon(floor.class.getResource("helicopter.png"));
	}

	public void initial(int x, int y) {
		xgn = x;
		ygn = y;
		c = 0;
		probG = 1;
		probS = 0.2;
		visible = true;
		haveSpring = false;
		stretch = false;
		haveRocket = false;
		haveHelicopter = false;
	}

	public int getx() {
		return (int) xgn;
	}

	public int gety() {
		return (int) ygn;
	}

	public int getSpringX() {
		return (int) xsp;
	}

	public int getSpringY() {
		return (int) ysp;
	}

	public int getXR() { // get x rocket
		return xr;
	}

	public int getYR() { // get y rocket
		return yr;
	}

	public int getXH() { // get x helicopter
		return xh;
	}

	public int getYH() { // get y helicopter
		return yh;
	}

	public int getWidth() {
		return width;
	}

	public int getHieght() {
		return hieght;
	}

	public int getWidthI() {
		return widthI;
	}

	public int getHieghtR() {
		return hieghtR;
	}

	public int getHieghtH() {
		return hieghtH;
	}

	public boolean getvisible() {
		return visible;
	}

	public double getprobG() {
		return probG;
	}

	public double getprobS() {
		return probS;
	}

	public int getSpringW() {
		return springW;
	}

	public int getSpringH() {
		return springH;
	}

	public void setx(int x1) {
		xgn = x1;
		visible = true;
	}

	public void sety(int y1) {
		ygn = y1;
		visible = true;
	}

	public void setprobG(double p) {
		probG = p;
	}

	public void setprobS(double p) {
		probS = p;
	}

	public void setdx(double max, double min) {
		dx = Math.random() * (max - min) + min;
	}

	public void setStretch(boolean s) {
		stretch = s;
	}

	public void setWIDTH(int w) {
		WIDTH = w;
	}

	public void setHIEGHT(int h) {
		HIEGHT = h;
	}

	public void mvdown(int y1) { // for mvScreen
		ygn += y1;
		if (ygn > HIEGHT) {
			visible = false;
			haveSpring = false;
			haveRocket = false;
			haveHelicopter = false;
		}
	}
	
	public void mvUp(int y1) { // for die
		ygn -= y1;
		if (ygn < -1 * hieght) {
			visible = false;
			haveSpring = false;
			haveRocket = false;
			haveHelicopter = false;
		}
	}

	public void changeC() { // change color randomly
		if (Math.random() < probG) {
			c = 0;
			if (Math.random() < probS) { // if the floor is green, then give it a spring randomly
				haveSpring = true;
				stretch = false;
				xsp = xgn + Math.random() * (width - springW);
			} else
				haveSpring = false;
		} else {
			c = 1;
		}
	}

	public void setItem(int mod) {
		// set item on the floor
		if (mod==1 && !haveSpring && Math.random() < probG / 40) {
			if (Math.random() > 0.6)
				haveRocket = true;
			else
				haveHelicopter = true;
		}
	}

	public void move(int xLeft, int xRight) { // move blue floors
		if (c == 1 && visible) {
			xgn += dx;

			if (xgn >= xRight - width) {
				xgn = xRight - width;
				dx *= -1;
			} else if (xgn < xLeft) {
				xgn = xLeft;
				dx *= -1;
			}
		}
	}

	public void draw(Graphics myBuffer) {
		if (visible) {
			if (c == 0)
				myBuffer.drawImage(green.getImage(), (int) xgn, (int) ygn, width, hieght, null);
			else
				myBuffer.drawImage(blue.getImage(), (int) xgn, (int) ygn, width, hieght, null);

			if (haveSpring) {
				if (!stretch)
					myBuffer.drawImage(spring.getImage(), (int) xsp, (int) ygn - springH, springW, springH, null);
				else // stretch spring
					myBuffer.drawImage(spring.getImage(), (int) xsp, (int) ygn - 2 * springH, springW, 2 * springH,
							null);
			}

			if (haveRocket) {
				xr = (int) xgn + width / 2 - widthI / 2;
				yr = (int) ygn - hieghtR;
				myBuffer.drawImage(rocket.getImage(), xr, yr, widthI, hieghtR, null);
			}

			if (haveHelicopter) {
				xh = (int) xgn + width / 2 - widthI / 2;
				yh = (int) ygn - hieghtH;
				myBuffer.drawImage(helicopter.getImage(), xh, yh, widthI, hieghtH, null);
			}
		}
	}
}
