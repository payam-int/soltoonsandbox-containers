package ir.pint.soltoon.soltoongame.shared.core.moreCore;

public class CoreObjectType {
	
	private String name;
	
	private boolean movable;
	private int moveSpeed;
	
	private boolean rotatable;
	private int rotationSpeed;
	
	private boolean shootable;
	private int shootPower;
	private int shootDistance;
	private int joon;
	
	private Point size;
	
	public void load() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isMovable() {
		return movable;
	}

	public void setMovable(boolean movable) {
		this.movable = movable;
	}

	public int getMoveSpeed() {
		return moveSpeed;
	}

	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}

	public boolean isRotatable() {
		return rotatable;
	}

	public void setRotatable(boolean rotatable) {
		this.rotatable = rotatable;
	}

	public int getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(int rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public boolean isShootable() {
		return shootable;
	}

	public void setShootable(boolean shootable) {
		this.shootable = shootable;
	}

	public int getShootPower() {
		return shootPower;
	}

	public void setShootPower(int shootPower) {
		this.shootPower = shootPower;
	}

	public int getShootDistance() {
		return shootDistance;
	}

	public void setShootDistance(int shootDistance) {
		this.shootDistance = shootDistance;
	}

	public int getJoon() {
		return joon;
	}

	public void setJoon(int joon) {
		this.joon = joon;
	}

	public Point getSize() {
		return size;
	}

	public void setSize(Point size) {
		this.size = size;
	}
	
	
	
	
	

}
