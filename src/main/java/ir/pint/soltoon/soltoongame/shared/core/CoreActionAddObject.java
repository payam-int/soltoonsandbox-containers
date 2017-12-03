package ir.pint.soltoon.soltoongame.shared.core;

import ir.pint.soltoon.soltoongame.shared.core.moreCore.CoreObject;
import ir.pint.soltoon.soltoongame.shared.core.moreCore.CoreObjectType;
import ir.pint.soltoon.soltoongame.shared.core.moreCore.Point;

public class CoreActionAddObject extends CoreAction {
	
	private CoreObjectType objectTypeToBeAdded;
	private Point location;

	@Override
	public void apply() {
		CoreObject object = new CoreObject(objectTypeToBeAdded, location);
		getWorld().getPlayerObjects().get(getPlayer()).add(object);
	}

	@Override
	public boolean isApplicable() {
		// TODO Auto-generated method stub
		//TODO check other rules ...

		return false;
	}

}
