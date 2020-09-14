package doodleJump;

import java.awt.Color;
import java.awt.Graphics;

public class frog extends monster {
	public monster_weapon[] bullete;
	public int frogBullets = 5;

	public frog(double x, double y, double dx, double dy, int skin) {
		super(x, y, dx, dy, skin);
		// TODO Auto-generated constructor stub
	}
	public void getweapon() {
		if (isVisible() == true) {
			bullete = new monster_weapon[11];
			for (int i = 0; i < frogBullets; i++) {
				if (getSkin() == 2) {
					bullete[i] = new monster_weapon(getX() + 58,getY()+getHieght()/2-20,1,0,2);
					}
				else {
					bullete[i] = new monster_weapon(getX() +30,getY()+getHieght()/2-20,-1,0,1);
				}
			}
			bullete[0].setVisible(true);
		}

	}
	public void attack(Graphics myBuffer) {
		if (isVisible() == true) {
			for (int i = 0; i < frogBullets; i++) {
				if (Math.abs(bullete[i].getX() - bullete[4].getX()) > 300 && i == 0) {
					bullete[i].setVisible(true);
				} else if (i > 0) {
					if (Math.abs(bullete[i].getX() - bullete[i - 1].getX()) > 300)
						bullete[i].setVisible(true);
				}
				bullete[i].setY(getY()+getHieght()/2-20);
				if(getSkin()==1)			
					bullete[i].move(600,getX()+28);
				if(getSkin()==2)			
					bullete[i].move(600,58);
				bullete[i].draw(myBuffer);
			}
		}
	}
	public void initial() {
		setX(0);
		setY(0);
		setVisible(false);
	}
}
