package games.stendhal.server.entity.status;

import games.stendhal.server.core.events.TurnListener;
import games.stendhal.server.core.events.TurnNotifier;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;

public class SleepStatusTurnListener implements TurnListener{
	private StatusList statusList;
	
	public SleepStatusTurnListener(StatusList statusList) {
		this.statusList = statusList;
	}
	
	@Override
	public void onTurnReached(int currentTurn) {
	RPEntity entity = statusList.getEntity();
	SleepStatus status = statusList.getFirstStatusByClass(SleepStatus.class);

	// check that the entity exists and has this status
	if ((entity == null) || (status == null)) {
		return;
	}
		
	}

}
