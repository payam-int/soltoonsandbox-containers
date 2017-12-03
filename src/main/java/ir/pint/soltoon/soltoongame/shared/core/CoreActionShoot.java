package ir.pint.soltoon.soltoongame.shared.core;

import ir.pint.soltoon.soltoongame.shared.core.moreCore.CoreObject;

public class CoreActionShoot extends CoreActionByObject {
	
	private CoreObject shootTarget;
	
	@Override
	public boolean isApplicable() {
		if (!getSourceObject().getType().isShootable())
			return false;
		
		if (getSourceObject().getType().getShootDistance() < getSourceObject().getLocation().dist1(shootTarget.getLocation()))
			return false;
		
		return true;
	}

	@Override
	public void apply() {
		shootTarget.setJoon(Math.max(0, shootTarget.getJoon() - getSourceObject().getType().getShootPower()));
		if (shootTarget.isDead()) {
			getWorld().addActionAndImmediatelyApply(new CoreActionRemoveObject());
		}
	}
}
