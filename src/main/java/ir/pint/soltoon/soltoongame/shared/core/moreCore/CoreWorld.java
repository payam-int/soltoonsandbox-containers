package ir.pint.soltoon.soltoongame.shared.core.moreCore;

import ir.pint.soltoon.soltoongame.shared.core.CoreAction;

import java.util.Deque;
import java.util.List;
import java.util.Map;

public class CoreWorld {
	
	private Map<CorePlayer, List<CoreObject>> playerObjects;
	private List<CorePlayer> players;
	private Deque<CoreAction> actionQueue;
	
	
	
	public Map<CorePlayer, List<CoreObject>> getPlayerObjects() {
		return playerObjects;
	}

	public void setPlayerObjects(Map<CorePlayer, List<CoreObject>> playerObjects) {
		this.playerObjects = playerObjects;
	}

	public List<CorePlayer> getPlayers() {
		return players;
	}

	public void setPlayers(List<CorePlayer> players) {
		this.players = players;
	}

	public void draw() {
		
	}

	public void addActionAndImmediatelyApply(CoreAction action) {
		actionQueue.push(action);
	}

	public void addActionAtEnd(CoreAction action) {
		actionQueue.add(action);
	}

	public void iterate() {
		// ToDo
	}
}
