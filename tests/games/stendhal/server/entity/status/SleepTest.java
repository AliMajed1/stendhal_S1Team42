package games.stendhal.server.entity.status;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.ConsumableItem;
import games.stendhal.server.entity.item.SleepingBag;
import games.stendhal.server.entity.item.consumption.Feeder;
import games.stendhal.server.entity.item.consumption.FeederFactory;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;
import utilities.RPClass.ConsumableTestHelper;

public class SleepTest {

	/**
	 * initialisation
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		MockStendlRPWorld.get();
	}

	/**
	 * Tests for sleep.
	 */
	@Test
	public void testSleeping() {
		final Player player = PlayerTestHelper.createPlayer("bob");
		final SleepingBag bag = new SleepingBag(new HashMap<String, String>());
		bag.onUsed(player);
		assertTrue(player.hasStatus(StatusType.SLEEPING));
	}

	/**
	 * Tests for healing while sleeping being greater while sleeping
	 */
	@Test
	public void testSleepingEating() {
		final Player player = PlayerTestHelper.createPlayer("bob");
		final Player player2 = PlayerTestHelper.createPlayer("bob2");
		Player attacker = PlayerTestHelper.createPlayer("killer");
		player.damage(50, attacker);
		player2.damage(50, attacker);
		ConsumableItem eater = ConsumableTestHelper.createEater("consume");
		Feeder feeder = FeederFactory.get(eater);
		feeder.feed(eater, player2);
		final SleepingBag bag = new SleepingBag(new HashMap<String, String>());
		final SleepingBag bag2 = new SleepingBag(new HashMap<String, String>());
		bag.onUsed(player);
		bag2.onUsed(player2);
		assertTrue(player2.hasStatus(StatusType.EATING));
		assertTrue(player.hasStatus(StatusType.SLEEPING));
		assertTrue(player2.hasStatus(StatusType.SLEEPING));
		assertThat("timestamp", player2.getHP(), greaterThan(player.getHP()));
		}
	
	/**
	 * Tests poison damage is reduced while sleeping
	 */
	@Test
	public void testSleepingPoisoned() {
		final String poisontype = "greater poison";
		final ConsumableItem poison = (ConsumableItem) SingletonRepository.getEntityManager().getItem(poisontype);
		final PoisonAttacker poisoner = new PoisonAttacker(100, poison);
		final Player player = PlayerTestHelper.createPlayer("bob");
		final Player player2 = PlayerTestHelper.createPlayer("bob2");
		poisoner.onAttackAttempt(player, SingletonRepository.getEntityManager().getCreature("snake"));
		poisoner.onAttackAttempt(player2, SingletonRepository.getEntityManager().getCreature("snake"));
		assertTrue(player.hasStatus(StatusType.POISONED));
		assertTrue(player2.hasStatus(StatusType.POISONED));
		final SleepingBag bag = new SleepingBag(new HashMap<String, String>());
		bag.onUsed(player);
		assertTrue(player.hasStatus(StatusType.SLEEPING));
		assertFalse(player2.hasStatus(StatusType.SLEEPING));
		assertThat("timestamp", player.getStatusList().getFirstStatusByClass(PoisonStatus.class).getRegen(), greaterThan(player2.getStatusList().getFirstStatusByClass(PoisonStatus.class).getRegen()));
	}
}
