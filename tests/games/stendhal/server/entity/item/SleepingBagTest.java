package games.stendhal.server.entity.item;

import org.junit.BeforeClass;
import org.junit.Test;
import java.util.*;

import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import utilities.RPClass.ItemTestHelper;
import static org.junit.Assert.*;
import utilities.PlayerTestHelper;
import games.stendhal.server.entity.player.Player;

public class SleepingBagTest 
{
	@BeforeClass
	public static void setUpBeforeClass() throws Exception 
	{
		Log4J.init();
		MockStendlRPWorld.get();
		ItemTestHelper.generateRPClasses();
	}
	
	/**
	 * Test for describe, checks dialogue within the game.
	 */
	@Test
	public void DescribeTest()
	{
		final SleepingBag bag = new SleepingBag(new HashMap<String, String>());
		assertEquals(bag.describe(), "A sleeping bag");
	}
	
	/**
	 * Test for onUsed, when the sleeping bag is used in the correct distance.
	 */
	@Test
	public void onUsedTest() 
	{
		final Player bob = PlayerTestHelper.createPlayer("bob");
		final SleepingBag bag = new SleepingBag(new HashMap<String, String>());
		assertTrue(bob.nextTo(bag));
		assertTrue(bag.onUsed(bob));
	}
	
	/**
	 * Test for onUsed, the player cannot move while sleeping.
	 */
	@Test
	public void onUsedSpeedTest() 
	{
		final Player bob = PlayerTestHelper.createPlayer("bob");
		final SleepingBag bag = new SleepingBag(new HashMap<String, String>());
		assertTrue(bag.onUsed(bob));
		assertTrue(bob.getBaseSpeed() == 0);
	}
	
}
