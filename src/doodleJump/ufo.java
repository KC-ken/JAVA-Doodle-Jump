package doodleJump;

public class ufo extends monster {
	private double theta;
	private double xcenter; 
	private double ycenter;  
	private double dis;
	
	public double getXcenter() {
		return xcenter;
	}
	public void setXcenter(double xcenter) {
		this.xcenter = xcenter;
	}
	public double getYcenter() {
		return ycenter;
	}
	public void setYcenter(double ycenter) {
		this.ycenter = ycenter;
	}
	public ufo(double x, double y, double dx, double dy, int skin) {
		super(x, y, dx, dy, skin);
		// TODO Auto-generated constructor stub
		theta=0;
	}
	public void Horizontalmove(double Xstart, double Xend) {
		if (getX() < Xstart || getX() > Xend) {
			setDx(getDx() * -1); 
		}
		setX(getX() + getDx() );
	}
	public void Circlelmove(double xcenter, double ycenter ,double distance) {
		dis = distance;
		if(theta>360)
			theta=0;
		else
			theta=theta+0.5;
		this.xcenter = xcenter;
		this.ycenter = ycenter;
		double stepx=distance*Math.cos(theta*Math.PI/180);
 	   	double stepy=distance*-1*Math.sin(theta*Math.PI/180);
 	   	setX(xcenter + stepx );
 	   	setY(ycenter+ stepy );
	}
	public void mvdown(int y1) { // for mvScreen
		ycenter += y1;
		if (getY()  > 600) {
			setVisible(false);
		}
	}
	
	public void mvUp(int y1) { // for die
		ycenter -= y1;
		if (getY() + getHieght() < 0) {
			setVisible(false);
		}
	}


}
