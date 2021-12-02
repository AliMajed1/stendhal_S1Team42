package games.stendhal.server.entity.item;

import java.util.*;
import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.RPEntity;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.entity.status.SleepStatus;
import marauroa.common.game.RPObject;

public class SleepingBag extends StackableItem
{
	private String[] equipableSlotsText = {"ground", "bag", "lhand", "rhand"};
	private List<String> equipableSlots = Arrays.asList(equipableSlotsText);
	private boolean sleep = false;
	private SleepStatus sleepStatus = new SleepStatus();
	
	/**
	 * Creates a new Sleeping Bag.
	 *
	 * @param name
	 * @param clazz
	 * @param subclass
	 * @param attributes
	 */
	public SleepingBag(final Map<String, String> attributes) {
		super("sleeping bag", "sleep", "sleeping_bag", attributes);
	}
	
	/**
	 * Copy constructor.
	 *
	 * @param item
	 */
	public SleepingBag(final SleepingBag item)
	{
		super(item);
	}
	
	/**
	 * Method to check if the sleeping bag is used.  
	 *
	 * @param user
	 * @return boolean to indicate usage
	 */
	@Override
	public final boolean onUsed(final RPEntity user)
	{
		RPObject base = getBaseContainer();
		if(user.nextTo((Entity) base))
		{
			return useSleepingBag((Player) user);
		}
		else
		{
			user.sendPrivateText("You're too far away from the sleeping bag. Come closer.");
			return false;
		}
	}
	
	/**
	 * Helper method giving the sleep logic.  
	 *
	 * @param player
	 *            user of the game
	 * @return boolean to indicate usage me
	 */
	public boolean useSleepingBag(final Player player) 
	{
		if(this.isContained())
		{
			player.sendPrivateText("You're too far away from the sleeping bag. Come closer.");
			return false;
		}
		
		if(!this.sleep)
		{
			player.sendPrivateText("Zzzzzzzz...");
			player.setBaseSpeed(0);
			this.sleep = true;
			player.getStatusList().inflictStatus(sleepStatus, this);
			this.setEquipableSlots(null);
			return true;
		}
		
		else {
			player.sendPrivateText("You are awake.");
			this.sleep = false;
			player.getStatusList().remove(sleepStatus);
			this.setEquipableSlots(this.equipableSlots);
			player.setBaseSpeed(1);
			return true;
		}
	}
	
	/**
	 * Method for the description of the sleeping bag.  
	 *
	 * @return in-game dialogue option
	 */
	@Override
	public String describe()
	{
		return "A sleeping bag";
	}
	
}