package games.stendhal.server.entity.status;

import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.player.Player;

public class SleepStatusHandler implements StatusHandler<SleepStatus>{

	@Override
	public void inflict(SleepStatus status, StatusList statusList, Entity bed) {
		// TODO Auto-generated method stub
		Player sleeper = (Player) statusList.getEntity();
		sleeper.setSpeed(0);
	}

	@Override
	public void remove(SleepStatus status, StatusList statusList) {
		// TODO Auto-generated method stub
		
	}

}
