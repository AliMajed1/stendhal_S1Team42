package games.stendhal.server.entity.status;

import games.stendhal.common.NotificationType;
import games.stendhal.server.core.events.TurnNotifier;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;



public class SleepStatusHandler implements StatusHandler<SleepStatus>{

	@Override
	public void inflict(SleepStatus status, StatusList statusList, Entity bed) {
		// TODO Auto-generated method stub
		if (statusList.hasStatus(status.getStatusType())) {
			return;
		}
		Player sleeper = (Player) statusList.getEntity();
		if(sleeper == null) {
			return;
		}
		sleeper.sendPrivateText(NotificationType.SCENE_SETTING, "You are sleeping.");
		statusList.activateStatusAttribute("status_" + status.getName());
		statusList.addInternal(status);
		PoisonStatus poisoned = statusList.getFirstStatusByClass(PoisonStatus.class);
		if(poisoned != null) {
			PoisonStatus poison = new PoisonStatus(poisoned.getAmount(), poisoned.getFrecuency(), Math.round(poisoned.getRegen()/2));
			statusList.remove(poisoned);
			statusList.addInternal(poison);
		}
		EatStatus eating = statusList.getFirstStatusByClass(EatStatus.class);
		if(eating != null) {
			PoisonStatus eating2 = new PoisonStatus(eating.getAmount(), eating.getFrecuency(), eating.getRegen()*2);
			statusList.remove(eating);
			statusList.addInternal(eating2);
		}
			
		TurnNotifier.get().notifyInSeconds(60, new StatusRemover(statusList, status));
	}

	@Override
	public void remove(SleepStatus status, StatusList statusList) {
		statusList.removeInternal(status);

		RPEntity entity = statusList.getEntity();
		if (entity == null) {
			return;
		}
		PoisonStatus poisoned = statusList.getFirstStatusByClass(PoisonStatus.class);
		if(poisoned != null) {
			PoisonStatus poison = new PoisonStatus(poisoned.getAmount(), poisoned.getFrecuency(), poisoned.getRegen()*2);
			statusList.remove(poisoned);
			statusList.addInternal(poison);		
		}
		EatStatus eating = statusList.getFirstStatusByClass(EatStatus.class);
		if(eating != null) {
			PoisonStatus eating2 = new PoisonStatus(eating.getAmount(), eating.getFrecuency(), eating.getRegen()/2);
			statusList.remove(eating);
			statusList.addInternal(eating2);
		}
		entity.sendPrivateText(NotificationType.SCENE_SETTING, "You are no longer sleeping.");
		entity.remove("status_" + status.getName());		
	}

}
