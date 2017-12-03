package ir.pint.soltoon.soltoongame.shared.core.moreCore;

public class CoreObject {
	
	private CoreObjectType type;
	private Point location;
	private int joon;
	
	
	public CoreObject(CoreObjectType type, Point location) {
		this.location = location;
		this.type = type;
		setJoon(type.getJoon());
	}
	
	
	public CoreObjectType getType() {
		return type;
	}
	public void setType(CoreObjectType type) {
		this.type = type;
	}
	public Point getLocation() {
		return location;
	}
	public void setLocation(Point location) {
		this.location = location;
	}
	public Point getSize() {
		return type.getSize();
	}


	public int getJoon() {
		return joon;
	}


	public void setJoon(int joon) {
		this.joon = joon;
	}


	public boolean isDead() {
		return joon <= 0;
	}



}
