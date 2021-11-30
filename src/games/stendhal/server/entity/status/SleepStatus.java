package games.stendhal.server.entity.status;

public class SleepStatus extends Status{

	public SleepStatus() {
		super("sleep");
	}

	@Override
	public StatusType getStatusType() {
		return StatusType.SLEEPING;
	}

}
