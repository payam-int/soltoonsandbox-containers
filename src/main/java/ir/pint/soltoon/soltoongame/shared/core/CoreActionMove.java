package ir.pint.soltoon.soltoongame.shared.core;

import ir.pint.soltoon.soltoongame.shared.core.moreCore.Point;

public class CoreActionMove extends CoreActionByObject {
	
	private Point moveTo;

	@Override
	public boolean isApplicable() {
		if (!getSourceObject().getType().isMovable())
			return false;
		
		if (getSourceObject().getType().getMoveSpeed() < getSourceObject().getLocation().dist1(moveTo))
			return false;
		
		return true;
	}

	@Override
	public void apply() {
		getSourceObject().setLocation(moveTo);
	}
	
	

}
