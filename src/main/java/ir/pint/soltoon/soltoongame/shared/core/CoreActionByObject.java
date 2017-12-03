package ir.pint.soltoon.soltoongame.shared.core;

import ir.pint.soltoon.soltoongame.shared.core.moreCore.CoreObject;

public abstract class CoreActionByObject extends CoreAction {
	
	private CoreObject sourceObject;
	
	public CoreObject getSourceObject() {
		return sourceObject;
	}

	public void setSourceObject(CoreObject sourceObject) {
		this.sourceObject = sourceObject;
	}
	
}
