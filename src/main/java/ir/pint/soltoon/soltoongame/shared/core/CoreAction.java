package ir.pint.soltoon.soltoongame.shared.core;

import ir.pint.soltoon.soltoongame.shared.core.moreCore.CorePlayer;
import ir.pint.soltoon.soltoongame.shared.core.moreCore.CoreWorld;

public abstract class CoreAction {
	
	private CorePlayer player;
	private CoreWorld world;
	
	
	public abstract boolean isApplicable();
	
	public CorePlayer getPlayer() {
		return player;
	}

	public void setPlayer(CorePlayer player) {
		this.player = player;
	}
	
	public CoreWorld getWorld() {
		return world;
	}

	public void setWorld(CoreWorld world) {
		this.world = world;
	}

	public abstract void apply();

}
