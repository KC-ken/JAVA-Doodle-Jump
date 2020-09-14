package doodleJump;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class doodleJumpPanel extends JPanel {
	// variables

	// frame
	private static int WIDTH = 400;
	private int midLine = 400;
	private static final int HIEGHT = 600;
	private static JFrame frame;
	private BufferedImage myImage;
	private Graphics myBuffer;
	private menu menu;
	private background bk;

	// doodle
	private doodle dd, dd2; // doodle
	private int lastDir, lastDir2; // last direction of doodle
	private int jHIEGHT = 210; // max hieght doodle jumping

	// monster
	private monster monster1;
	private ufo ufo;
	private frog frog;
	private int t1counter; // cacluate t1 time
	private int monster_prob; // set when monster show

	// floor
	private floor[] gs, gs2; // floor
	private int floorNum; // max floor number
	private int[] hMax = new int[2]; // floor's max distance
	private int[] hMin = new int[2]; // floor's min distance
	private double[] probG = new double[2]; // probability of green floor
	private double[] probS = new double[2]; // probability of floor having spring
	private double[] speedMax = new double[2]; // max speed of blue floor
	private double[] speedMin = new double[2]; // min speed of blue floor

	// shoot
	private shoot[] s, s2; // shoot
	private int shootNum; // number of bullets
	private int fireNum, fireNum2; // the number of bullets fired
	private int shootingTime; // the time doodle shoot
	private boolean shootLock, shootLock2; // lock shoot

	// timer
	// t0:menu, t1:single player, t2:contest, t3:pause, t4:game over for 1 player,
	// t5:game over for contest, t6:about
	private Timer t0, t1, t2, t3, t4, t5, t6;

	// state
	// mode
	// 0:menu, 1:single player, 2:contest, 3:pause, 4:game over for 1player,
	// 5:game over for contest, 6:about
	private int mod;
	private pause pause;
	private gameover_1 gameover_1;
	private gameover_2 gameover_2;
	private light_back light_back;
	private about aboutpane;
	private int dropping;

	// score
	private int[] score = new int[2]; // score
	private int[] history = new int[1]; // history
	private health h, h2; // health point
	private int winner;
	private int target = 10000; // contest target score

	

	public doodleJumpPanel() {
		myImage = new BufferedImage(WIDTH, HIEGHT, BufferedImage.TYPE_INT_RGB);
		myBuffer = myImage.getGraphics();

		menu = new menu(); // menu

		bk = new background(); // background

		bk.draw(myBuffer, 0, WIDTH); // set background

		// set doodle
		dd = new doodle();
		dd2 = new doodle();

		// set monster
		ufo = new ufo(0, 0, 0.25, 0, 0);
		ufo.setHieght(100);
		ufo.setWidth(100);
		monster1 = new monster(0, 0, 0, 0, 3);
		monster1.setWidth(100);
		monster1.setHieght(200);
		frog = new frog(325, 500, 0.25, 0, 1);
		frog.getweapon();
		t1counter = 0;
		monster_prob = 1200;

		// number of floor is 30
		floorNum = 30;
		gs = new floor[floorNum];
		gs2 = new floor[floorNum];

		// initial floors
		for (int i = 0; i < floorNum; i++) {
			gs[i] = new floor();
			gs2[i] = new floor();
		}

		// number of bullets is 20
		shootNum = 20;
		s = new shoot[shootNum];
		s2 = new shoot[shootNum];

		// initial shoot
		for (int i = 0; i < shootNum; i++) {
			s[i] = new shoot();
			s2[i] = new shoot();
		}

		// set health (for contest)
		h = new health();
		h2 = new health();

		initialGame(); // initial game

		// set state
		pause = new pause();
		gameover_1 = new gameover_1();
		gameover_2 = new gameover_2();
		light_back = new light_back();
		aboutpane = new about();
		mod = 0;// set mod 0,

		t0 = new Timer(4, new Listener0());
		t1 = new Timer(4, new Listener1());
		t2 = new Timer(4, new Listener2());
		t3 = new Timer(4, new Listener3());
		t4 = new Timer(4, new Listener4());
		t5 = new Timer(4, new Listener5());
		t6 = new Timer(4, new Listener6());
		
		// start menu
		dd.setx(65);
		dd.sety(300);
		t0.start(); 

		addMouseListener(new Mouse()); // mouse
		addKeyListener(new Key()); // key
		setFocusable(true);
	}

	public void paintComponent(Graphics g) {
		g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
	}

	// menu
	private class Listener0 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			bk.draw(myBuffer, 0, WIDTH); // set background
			menu.draw(myBuffer); // draw menu and button
			
			
			gs[0].setx(60);
			gs[0].sety(500);
			gs[0].draw(myBuffer);
			
			dd.jump(); // let doodle jump
			dd.setDir(0);
			dd.draw(myBuffer);
			
			jumping(dd, gs[0]); // jump when step on the floor or spring

			repaint();
		}
	}

	// single player
	private class Listener1 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			bk.draw(myBuffer, 0, WIDTH); // set background

			dd.jump(); // let doodle jump
			mvScreen(dd, gs, score, 0); // move screen upward
			setDifficulty(score, hMin, hMax, probG, probS, speedMin, speedMax, 0); // set difficulty
			reset(gs, hMin, hMax, probG, probS, speedMin, speedMax, 0, 0, WIDTH); // reset floors
			dd_vs_monster();

			// monster's action
			t1counter = t1counter + 1;
			if (t1counter > monster_prob) {
				chosemonster();
				t1counter = 0;
			}
			drowmonster();

			// floor
			for (int i = 0; i < floorNum; i++) {
				gs[i].move(0, WIDTH); // move blue floors
				gs[i].draw(myBuffer); // draw floors
				jumping(dd, gs[i]); // jump when step on the floor or spring
			}

			// shooting
			for (int i = 0; i < shootNum; i++) {
				s[i].move1(); // move bullets
				s[i].draw(myBuffer); // draw bullets
			}

			// doodle's action
			if (shootingTime > 0) {
				shootingTime--;
				dd.setDir(2); // shoot action
			} else
				dd.setDir(lastDir);

			dd.draw(myBuffer); // draw a doodle

			pause.drawPauseBottom(myBuffer);// draw pause button

			// draw score
			myBuffer.setColor(Color.BLACK);
			myBuffer.setFont(new Font("Monospaced", Font.ITALIC, 16));
			myBuffer.drawString("Score: " + score[0], WIDTH - 150, 25);

			// drop
			if (dd.drop)
				drop();

			// game over
			if (dd.getGameOver()) {
				dd.setdy(0);
				mod = 4;
				t1.stop();
				t4.start();
			}

			repaint();
		}
	}

	// contest
	private class Listener2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// set background
			bk.draw(myBuffer, 0, midLine);
			bk.draw(myBuffer, midLine, WIDTH);

			dd.jump(); // let doodle jump
			dd2.jump();
			mvScreen(dd, gs, score, 0); // move screen upward
			mvScreen(dd2, gs2, score, 1);
			setDifficulty(score, hMin, hMax, probG, probS, speedMin, speedMax, 0); // set difficulty
			setDifficulty(score, hMin, hMax, probG, probS, speedMin, speedMax, 1);
			reset(gs, hMin, hMax, probG, probS, speedMin, speedMax, 0, 0, midLine); // reset floors
			reset(gs2, hMin, hMax, probG, probS, speedMin, speedMax, 1, midLine, WIDTH);

			// contest mode shooting
			twoPshooting(dd2, s, h2);
			twoPshooting(dd, s2, h);

			// floor
			for (int i = 0; i < floorNum; i++) {
				gs[i].move(0, midLine); // move blue floors
				gs2[i].move(midLine, WIDTH);
				gs[i].draw(myBuffer); // draw floors
				gs2[i].draw(myBuffer);
				jumping(dd, gs[i]); // jump when step on the floor or spring
				jumping(dd2, gs2[i]);
			}

			// shooting
			for (int i = 0; i < shootNum; i++) {
				s[i].move2(0, midLine, midLine, WIDTH); // move bullets
				s2[i].move2(midLine, WIDTH, 0, midLine);
				s[i].draw(myBuffer); // draw bullets
				s2[i].draw(myBuffer);
			}

			// set doodle's direction
			dd.setDir(lastDir);
			dd2.setDir(lastDir2);

			dd.draw2(myBuffer, 0, midLine); // draw doodles
			dd2.draw2(myBuffer, midLine, WIDTH);

			// draw line to separate 2 player
			myBuffer.setColor(Color.black);
			myBuffer.drawLine(midLine, 0, midLine, HIEGHT);

			// draw scores
			myBuffer.setColor(Color.BLACK);
			myBuffer.setFont(new Font("Monospaced", Font.ITALIC, 16));
			myBuffer.drawString("Score: " + score[0], midLine - 150, 25);
			myBuffer.drawString("Score: " + score[1], WIDTH - 150, 25);

			// draw HPs
			h.draw(myBuffer, 20, 20);
			h2.draw(myBuffer, midLine + 20, 20);

			pause.drawPauseBottom2(myBuffer);// draw pause button

			// target achieve
			if (score[0] > target)
				dd2.setGameOver(true);
			if (score[1] > target)
				dd.setGameOver(true);

			// game over
			if (dd.getGameOver() || dd.drop) {
				winner = 1;
				mod = 5;
				t2.stop();
				light_back.draw(myBuffer, 0, WIDTH);
				repaint();
				t5.start();
			} else if (dd2.getGameOver() || dd2.drop) {
				winner = 2;
				mod = 5;
				t2.stop();
				light_back.draw(myBuffer, 0, WIDTH);
				repaint();
				t5.start();
			}

			repaint();
		}
	}

	// pause
	private class Listener3 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			pause.draw(myBuffer);
			repaint();
		}
	}

	// game over for 1 player
	private class Listener4 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			bk.draw(myBuffer, 0, WIDTH);
			gameover_1.draw(myBuffer, score[0], history, 0);
			repaint();
		}
	}

	// game over for contest
	private class Listener5 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			gameover_2.draw(myBuffer, winner);
			dd.draw2(myBuffer, 0, midLine); // draw doodles
			dd2.draw2(myBuffer, midLine, WIDTH);
			gameover_2.drawMedal(myBuffer, winner, dd.getx(), dd.gety(), dd2.getx(), dd2.gety());
			repaint();
		}
	}

	// about
	private class Listener6 implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			bk.draw(myBuffer, 0, WIDTH);
			aboutpane.draw(myBuffer);
			repaint();
		}
	}

	public class Mouse extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			// mode 0 (menu)
			if (mod == 0) {
				mod = menu.scan(e.getX(), e.getY());
				if (mod == 1) {
					t0.stop();
					initialGame(); // initial game
					t1.start();
				}

				else if (mod == 2) {
					t0.stop();
					resizeTo800();
					initialGame(); // initial game
					t2.start();
				}

				else if (mod == 6) {
					t0.stop();
					resizeTo800();
					t6.start();
				}
			}

			// mode 1 (single player)
			else if (mod == 1) { // for single player
				if (!dd.getInvincible()) {
					s[fireNum].fire(dd.getx(), dd.gety(), e.getX(), e.getY());
					shootingTime = 50; // shooting action

					fireNum++;
					if (fireNum >= shootNum)
						fireNum = 0;
				}
				mod = pause.scanInMode1(e.getX(), e.getY());
				if (mod == 3) {
					t1.stop();
					light_back.draw(myBuffer, 0, WIDTH);
					repaint();
					t3.start();
				}
			}

			// mode 2 (contest mode)
			else if (mod == 2) {
				mod = pause.scanInMode2(e.getX(), e.getY());
				if (mod == 3) {
					t2.stop();
					light_back.draw(myBuffer, 0, WIDTH);
					repaint();
					t3.start();
				}
			}

			// mode 3 (pause)
			else if (mod == 3) {
				mod = pause.scan(e.getX(), e.getY());
				if (mod == 0) {
					t3.stop();
					resizeTo400();
					dd.initial();
					gs[0].initial(60, 500);
					dd.setx(65);
					dd.sety(300);
					t0.start();
				} else if (mod == 1) {
					t3.stop();
					t1.start();
				} else if (mod == 2) {
					t3.stop();
					t2.start();
				}
			}

			// mode 4 (game over in single player mode)
			else if (mod == 4) {
				mod = gameover_1.scan(e.getX(), e.getY());
				if (mod == 0) {
					t4.stop();
					dd.initial();
					gs[0].initial(60, 500);
					dd.setx(65);
					dd.sety(300);
					t0.start();
				} else if (mod == 1) {
					t4.stop();
					initialGame(); // initial game
					t1.start();
				}
			}

			// mode 5 (game over in contest mode)
			else if (mod == 5) {
				mod = gameover_2.scan(e.getX(), e.getY());
				if (mod == 0) {
					t5.stop();
					resizeTo400();
					dd.initial();
					gs[0].initial(60, 500);
					dd.setx(65);
					dd.sety(300);
					t0.start();
				} else if (mod == 2) {
					t5.stop();
					initialGame(); // initial game
					t2.start();
				}
			}

			// mode 6 about
			else if (mod == 6) {
				mod = aboutpane.scan(e.getX(), e.getY());
				if (mod == 0) {
					t6.stop();
					resizeTo400();
					t0.start();
				}
			}

//			System.out.println(mod);
		}
	}

	private class Key extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_D) {
				if(mod == 1 || mod == 2) {
					dd.mvRight();
					lastDir = 1;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_A) {
				if(mod == 1 || mod == 2) {
					dd.mvLeft();
					lastDir = 0;
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (mod == 2) {
					dd2.mvRight();
					lastDir2 = 1;
				} else if(mod == 1){
					dd.mvRight();
					lastDir = 1;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (mod == 2) {
					dd2.mvLeft();
					lastDir2 = 0;
				} else if(mod == 1){
					dd.mvLeft();
					lastDir = 0;
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_V) // P1 shoot
			{
				if (mod == 2 && !shootLock) { // for multi player
					s[fireNum].fire2(dd.getx(), dd.gety(), lastDir, dd.getdy());
					shootLock = true;

					fireNum++;
					if (fireNum >= shootNum)
						fireNum = 0;
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_SLASH) // P2 shoot
			{
				if (mod == 2 && !shootLock2) { // for multi player
					s2[fireNum2].fire2(dd2.getx(), dd2.gety(), lastDir2, dd2.getdy());
					shootLock2 = true;

					fireNum2++;
					if (fireNum2 >= shootNum)
						fireNum2 = 0;
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_C) // P1 shield on
			{
				if (mod == 2) {
					dd.setShield(true);
					shootLock = true;
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_PERIOD) // P2 shield on
			{
				if (mod == 2) {
					dd2.setShield(true);
					shootLock2 = true;
				}
			}
		}

		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_D)
				dd.slow(); // doodle's dx slow down
			if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (mod == 2)
					dd2.slow();
				else
					dd.slow();
			}

			if (e.getKeyCode() == KeyEvent.VK_C) // P1 shield off
			{
				if (mod == 2) {
					dd.setShield(false);
					shootLock = false;
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_PERIOD) // P2 shield off
			{
				if (mod == 2) {
					dd2.setShield(false);
					shootLock2 = false;
				}
			}

			if (e.getKeyCode() == KeyEvent.VK_V) // P1 shoot unlock
			{
				if (mod == 2 && !dd.getShield()) // for multi player
					shootLock = false;
			}

			if (e.getKeyCode() == KeyEvent.VK_SLASH) // P2 shoot unlock
			{
				if (mod == 2 && !dd2.getShield()) // for multi player
					shootLock2 = false;
			}
		}
	}

	// initial game
	private void initialGame() {
		// initial variable
		for (int i = 0; i < 2; i++) {
			hMax[i] = 80;
			hMin[i] = 10;
			probG[i] = 1;
			probS[i] = 0.2;
			speedMax[i] = 0.6;
			speedMin[i] = 0.2;
			score[i] = 0;
		}

		dropping = 0;
		shootingTime = 0;
		shootLock = false;
		shootLock2 = false;

		t1counter = 0;

		// initial doodle
		dd.initial();
		dd2.initial();
		dd2.setx(dd2.getx() + midLine);

		// initial floor on bottom
		for (int i = 0; i < 8; i++) {
			gs[i].initial(i * 50, HIEGHT - 20);
			gs2[i].initial(i * 50 + midLine, HIEGHT - 20);
		}

		// set floor randomly
		for (int i = 8; i < floorNum; i++) {
			int xPos, yPos;
			xPos = (int) (Math.random() * (midLine - 50)); // gs.getWidth() = 50
			yPos = gs[i - 1].gety() - (int) (Math.random() * (hMax[0] - hMin[0]) + hMin[0]); // gs.getWidth() = 10
			gs[i].initial(xPos, yPos);
		}
		// set floor randomly
		for (int i = 8; i < floorNum; i++) {
			int xPos, yPos;
			xPos = (int) (Math.random() * (midLine - 50) + midLine); // gs.getWidth() = 50
			yPos = gs2[i - 1].gety() - (int) (Math.random() * (hMax[1] - hMin[1]) + hMin[1]); // gs.getWidth() = 10
			gs2[i].initial(xPos, yPos);
		}

		// initial shoot
		for (int i = 0; i < shootNum; i++) {
			s[i].initial();
			s2[i].initial();
			s2[i].setColor(1);
		}

		// initial HP
		h.initial();
		h2.initial();

		// monster
		ufo.initial();
		monster1.initial();
		frog.initial();
	}

	// jump when step on floor or spring
	private void jumping(doodle d, floor g) {
		if (bounce(d, g)) { // step on spring
			g.setStretch(true);
			d.setdy(7);
		}

		if (!d.haveHelicopter && !d.haveRocket) {
			// get rocket
			if (getRocket(d, g)) {
				g.haveRocket = false;
				d.setDir(lastDir);
				d.haveRocket = true;
				d.setItemTime(500);
			}
			// get Helicopter
			if (getHelicopter(d, g)) {
				g.haveHelicopter = false;
				d.setDir(lastDir);
				d.haveHelicopter = true;
				d.setItemTime(500);
			}
		}
		// step on floor
		if (step(d, g))
			d.setdy(4);
	}

	private boolean collide(int x1, int y1, int w1, int h1, int x2, int y2, int w2, int h2) {
		if (x1 < x2 + w2 && x1 > x2 - w1 && y1 < y2 + h2 && y1 > y2 - h1)
			return true;
		else
			return false;
	}

	// to detect whether doodle step on floor
	private boolean step(doodle d, floor g) {
		if (d.getdy() < 0) {
			if (collide(d.getx(), d.gety() + d.getHieght(), d.getWidth(), 0, g.getx(), g.gety(), g.getWidth(),
					g.getHieght()))
				return true;
			else if (collide(d.getx() - WIDTH, d.gety() + d.getHieght(), d.getWidth(), 0, g.getx(), g.gety(),
					g.getWidth(), g.getHieght()))
				return true;
		}
		return false;
	}

	// to detect whether doodle step on spring
	private boolean bounce(doodle d, floor g) {
		if (g.haveSpring) {
			if (d.getdy() < 0) {
				if (collide(d.getx(), d.gety() + d.getHieght(), d.getWidth(), 0, g.getSpringX(), g.gety(),
						g.getSpringW(), g.getSpringH()))
					return true;
				else if (collide(d.getx() - WIDTH, d.gety() + d.getHieght(), d.getWidth(), 0, g.getSpringX(), g.gety(),
						g.getSpringW(), g.getSpringH()))
					return true;
			}
		}
		return false;
	}

	// to detect whether doodle get the rocket
	private boolean getRocket(doodle d, floor g) {
		if (g.haveRocket) {
			if (collide(d.getx(), d.gety(), d.getWidth(), d.getHieght(), g.getXR(), g.getYR(), g.getWidthI(),
					g.getHieghtR()))
				return true;
			else if (collide(d.getx() - WIDTH, d.gety(), d.getWidth(), d.getHieght(), g.getXR(), g.getYR(),
					g.getWidthI(), g.getHieghtR()))
				return true;
		}
		return false;
	}

	// to detect whether doodle get the helicopter
	private boolean getHelicopter(doodle d, floor g) {
		if (g.haveHelicopter) {
			if (collide(d.getx(), d.gety(), d.getWidth(), d.getHieght(), g.getXH(), g.getYH(), g.getWidthI(),
					g.getHieghtH()))
				return true;
			else if (collide(d.getx() - WIDTH, d.gety(), d.getWidth(), d.getHieght(), g.getXH(), g.getYH(),
					g.getWidthI(), g.getHieghtH()))
				return true;
		}
		return false;
	}

	// move screen downward
	private void mvScreen(doodle d, floor[] g, int[] s, int offset) {
		int ymv = HIEGHT / 2 - d.gety();
		if (ymv > 0) {
			s[offset] += ymv; // increase score

			for (int i = 0; i < floorNum; i++) { // floors move down
				g[i].mvdown(ymv);
			}

			// monster move down
			ufo.mvdown(ymv);
			monster1.mvdown(ymv);
			frog.mvdown(ymv);
			d.sety(HIEGHT / 2); // reset dd's y
		}
	}

	// move screen upward
	private void drop() {
		int ymv = dd.gety() - HIEGHT + dd.getHieght();
		for (int i = 0; i < floorNum; i++) { // floors move down
			gs[i].mvUp(ymv);
		}

		// monster move down
		ufo.mvUp(ymv);
		monster1.mvUp(ymv);
		frog.mvUp(ymv);
		dd.sety(HIEGHT - dd.getHieght()); // reset dd's y

		dropping += ymv;
		gameover_1.draw(myBuffer, score[0], history, 800 - dropping);
		if (dropping >= 800)
			dd.setGameOver(true);
	}

	// set difficulty
	private void setDifficulty(int[] s, int[] hMin, int[] hMax, double[] G, double[] S, double[] speedMin,
			double[] speedMax, int offset) { // difficulty depend on score
		if (s[offset] > 1000 && s[offset] < 3000) { // score > 1000
			hMin[offset] = 20;
			hMax[offset] = 105;
			G[offset] = 0.95;
		} else if (s[offset] < 5000) { // score > 3000
			hMin[offset] = 50;
			hMax[offset] = 140;
			G[offset] = 0.9;
			speedMax[offset] = 0.8;
		} else if (s[offset] < 10000) { // score > 5000
			hMin[offset] = 90;
			hMax[offset] = 200;
			G[offset] = 0.8;
			S[offset] = 0.1;
			speedMax[offset] = 1;
		} else if (s[offset] < 20000) { // score > 10000
			hMin[offset] = 150;
			hMax[offset] = jHIEGHT;
			G[offset] = 0.7;
			speedMax[offset] = 1.2;
		} else if (s[offset] < 30000) { // score > 20000
			hMin[offset] = 200;
			G[offset] = 0.5;
			speedMin[offset] = 0.4;
		} else { // score > 30000
			G[offset] = 0.3;
			speedMin[offset] = 0.5;
		}
	}

	// reset floors
	private void reset(floor[] g, int[] hMax, int[] hMin, double[] G, double[] S, double[] speedMin, double[] speedMax,
			int offset, int xLeft, int xRight) { // when floors are disappeared, reset the floor
		for (int i = 0; i < floorNum; i++) {
			if (!g[i].getvisible()) {
				g[i].setx((int) (Math.random() * ((xRight - xLeft) - g[i].getWidth()) + xLeft));

				if (i == 0)
					g[i].sety(g[floorNum - 1].gety()
							- (int) (Math.random() * (hMax[offset] - hMin[offset]) + hMin[offset]));
				else
					g[i].sety(g[i - 1].gety() - (int) (Math.random() * (hMax[offset] - hMin[offset]) + hMin[offset]));
				// the new floor will higher then the last floor

				g[i].setprobG(G[offset]); // set floor's probG
				g[i].setprobS(S[offset]); // set floor's probS
				g[i].changeC(); // reset floor's color
				g[i].setItem(mod);
				g[i].setdx(speedMax[offset], speedMin[offset]); // set floor's speedMax and speedMin
			}
		}
	}

	// shooting in contest mode
	public void twoPshooting(doodle d, shoot[] s, health h0) {
		for (int i = 0; i < shootNum; i++) {
			if (s[i].getVisible()) {
				// shield defense
				if (d.getShield()) {
					if (collide(s[i].getx(), s[i].gety(), s[i].getW(), s[i].getH(), d.getShieltX(), d.getShieltY(),
							d.getShieltW(), d.getHieght()))
						s[i].setVisible(false);
				}

				// attack doodle
				if (collide(s[i].getx(), s[i].gety(), s[i].getW(), s[i].getH(), d.getx(), d.gety(), d.getWidth(),
						d.getHieght())) {
					s[i].setVisible(false);
					h0.setHP(h0.getHP() - 1);
				}

				// doodle die
				if (h0.getHP() == 0) {
					d.setGameOver(true);
				}
			}
		}

	}

	// resize to 800
	public void resizeTo800() {
		for (; WIDTH < 800; WIDTH += 10) {
			frame.setSize(WIDTH, HIEGHT);
			myImage = new BufferedImage(WIDTH, HIEGHT, BufferedImage.TYPE_INT_RGB);
			myBuffer = myImage.getGraphics();
		}
		frame.setSize(WIDTH, HIEGHT);
		myImage = new BufferedImage(WIDTH, HIEGHT, BufferedImage.TYPE_INT_RGB);
		myBuffer = myImage.getGraphics();
	}

	// resize to 400
	public void resizeTo400() {
		for (; WIDTH > 400; WIDTH -= 10) {
			frame.setSize(WIDTH, HIEGHT);
			myImage = new BufferedImage(WIDTH, HIEGHT, BufferedImage.TYPE_INT_RGB);
			myBuffer = myImage.getGraphics();
		}
		frame.setSize(WIDTH, HIEGHT);
		myImage = new BufferedImage(WIDTH, HIEGHT, BufferedImage.TYPE_INT_RGB);
		myBuffer = myImage.getGraphics();
	}

	// chose the monster
	public void chosemonster() {
		int skin = (int) (Math.random() * 5);
		if (skin == 0 && !ufo.isVisible()) {
			ufo.setXcenter(Math.random() * 200 + 100);
			ufo.setYcenter(-1 * ufo.getHieght());
			ufo.setVisible(true);
		}
		if ((skin == 1 || skin == 2) && !frog.isVisible()) {
			frog.setVisible(true);
			frog.setSkin(skin);
			frog.setHieght(75);
			frog.setWidth(75);
			if (frog.getSkin() == 1)
				frog.setX(WIDTH - frog.getWidth());
			else
				frog.setX(0);
			frog.setY(-1 * frog.getHieght());
			frog.getweapon();
		}
		if (skin == 3 && !monster1.isVisible()) {
			monster1.setVisible(true);
			monster1.setHieght(100);
			monster1.setWidth(65);
			monster1.setX(Math.random() * (WIDTH - monster1.getWidth()));
			monster1.setY(-1 * monster1.getHieght());
		}
//		System.out.println(skin);
	}

	// draw the monster
	public void drowmonster() {
		if (ufo.isVisible()) {
			ufo.Circlelmove(ufo.getXcenter(), ufo.getYcenter(), 50);
			ufo.draw(myBuffer);
		}
		if (frog.isVisible()) {
			frog.attack(myBuffer);
			frog.draw(myBuffer);
		}
		if (monster1.isVisible()) {

			monster1.draw(myBuffer);
		}
	}

	// fight
	public void dd_vs_monster() {
		if (!dd.getInvincible()) {
			if (frog.isVisible() && collide(dd.getx(), dd.gety(), dd.getWidth(), dd.getHieght(), (int) frog.getX(),
					(int) frog.getY(), frog.getWidth(), frog.getHieght())) {
				if (dd.getdy() < 0 && (dd.gety() + dd.getHieght()) < (frog.getY() + frog.getHieght() / 3)) {
					frog.initial();
					dd.setdy(5.5);
				} else
					dd.setGameOver(true);
			}
			if (monster1.isVisible() && collide(dd.getx(), dd.gety(), dd.getWidth(), dd.getHieght(),
					(int) monster1.getX(), (int) monster1.getY(), monster1.getWidth(), monster1.getHieght())) {
				if (dd.getdy() < 0 && (dd.gety() + dd.getHieght()) < (monster1.getY() + monster1.getHieght() / 3)) {
					monster1.initial();
					dd.setdy(5.5);
				} else
					dd.setGameOver(true);
			}
			if (ufo.isVisible() && collide(dd.getx(), dd.gety(), dd.getWidth(), dd.getHieght(), (int) ufo.getX(),
					(int) ufo.getY(), ufo.getWidth(), ufo.getHieght())) {
				if (dd.getdy() < 0 && (dd.gety() + dd.getHieght()) < (ufo.getY() + ufo.getHieght() / 3)) {
					ufo.initial();
					dd.setdy(5.5);
				} else
					dd.setGameOver(true);
			}
			// frog's bullets
			for (int i = 0; i < frog.frogBullets; i++) {
				if (frog.isVisible() && frog.bullete[i].isVisible()) {
					if (collide(dd.getx(), dd.gety(), dd.getWidth(), dd.getHieght(), (int) frog.bullete[i].getX(),
							(int) frog.bullete[i].getY(), frog.bullete[i].getWidth(), frog.bullete[i].getHieght()))
						dd.setGameOver(true);
				}
			}
		}

		for (int i = 0; i < shootNum; i++) {
			if (s[i].getVisible()) {
				if (frog.isVisible() && collide(s[i].getx(), s[i].gety(), s[i].getW(), s[i].getH(), (int) frog.getX(),
						(int) frog.getY(), frog.getWidth(), frog.getHieght())) {
					frog.initial();
					s[i].setVisible(false);
				}
				if (monster1.isVisible() && collide(s[i].getx(), s[i].gety(), s[i].getW(), s[i].getH(),
						(int) monster1.getX(), (int) monster1.getY(), monster1.getWidth(), monster1.getHieght())) {
					monster1.initial();
					s[i].setVisible(false);
				}
				if (ufo.isVisible() && collide(s[i].getx(), s[i].gety(), s[i].getW(), s[i].getH(), (int) ufo.getX(),
						(int) ufo.getY(), ufo.getWidth(), ufo.getHieght())) {
					ufo.initial();
					s[i].setVisible(false);
				}

			}

		}
	}

	// Driver
	public static void main(String[] args) {
		frame = new JFrame("Doodle Jump!!!");
		frame.setSize(WIDTH, HIEGHT);
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(new doodleJumpPanel());
		frame.setVisible(true);

	}
}
