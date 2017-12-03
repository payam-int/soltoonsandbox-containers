package ir.pint.soltoon.soltoongame.shared.core.moreCore;

public class Point {
	
	private int x;
	private int y;
	
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}

	public int dist1(Point b) {
		return Math.abs(x-b.x) + Math.abs(y-b.y);
	}
	
	

}
