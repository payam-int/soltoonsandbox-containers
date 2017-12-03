package ir.pint.soltoon.soltoongame.shared.core;

import ir.pint.soltoon.soltoongame.shared.core.moreCore.CoreObject;

public class CoreActionRemoveObject extends CoreAction {
	
	private CoreObject objectToBeRemoved;

	@Override
	public void apply() {
		getWorld().getPlayerObjects().get(getPlayer()).remove(objectToBeRemoved);
	}

	@Override
	public boolean isApplicable() {
		return true;
	}

}
