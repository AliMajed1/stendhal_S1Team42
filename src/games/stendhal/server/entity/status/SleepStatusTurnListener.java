package games.stendhal.server.entity.status;

import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.core.events.TurnNotifier;
import games.stendhal.server.entity.player.Player;

public class SleepStatusTurnListener implements TurnListener{
	private StatusList statusList;
	
	public SleepStatusTurnListener(StatusList statusList) {
		this.statusList = statusList;
	}
	
	@Override
	public void onTurnReached(int currentTurn) {
	Player sleeper = (Player) statusList.getEntity();
	SleepStatus status = statusList.getFirstStatusByClass(SleepStatus.class);
	
	// check that the entity exists and has this status
	if ((sleeper == null) || (status == null)) {
		return;
	}
	
	sleeper.forceStop();
	
	EatStatus eating = statusList.getFirstStatusByClass(EatStatus.class);
	PoisonStatus poisoned = statusList.getFirstStatusByClass(PoisonStatus.class);
	
	if(eating == null) {
		sleeper.heal(5);
	} else {
		sleeper.heal(10);
	}
	
	if(poisoned != null) {
		PoisonStatus poison = new PoisonStatus(poisoned.getAmount(), poisoned.getFrecuency(), poisoned.getRegen()/2);
		statusList.remove(poisoned);
		statusList.addInternal(poison);
	}
	
	
	TurnNotifier.get().notifyInTurns(0, this);
		
	}
	
	
	

}
