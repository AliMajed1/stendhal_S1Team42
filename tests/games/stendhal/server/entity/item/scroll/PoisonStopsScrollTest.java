package games.stendhal.server.entity.item.scroll;

import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPWorld;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.ConsumableItem;
import games.stendhal.server.entity.item.consumption.Poisoner;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import utilities.PlayerTestHelper;
import utilities.RPClass.ItemTestHelper;

public class PoisonStopsScrollTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		Log4J.init();
	}


	/**
	 * Tests for feed.
	 */
	@Test
	public final void testFeed() {
		SingletonRepository.getEntityManager();
		ItemTestHelper.generateRPClasses();
		PlayerTestHelper.generatePlayerRPClasses();
		final Map<String, String> attributes = new HashMap<String, String>();
		attributes.put("amount", "1000");
		attributes.put("regen", "200");
		attributes.put("frequency", "1");
		attributes.put("id", "1");
		final StendhalRPWorld world = SingletonRepository.getRPWorld();
		final StendhalRPZone zone = new StendhalRPZone("test", 20, 20);
		world.addRPZone(zone);
		final StendhalRPZone zone2 = new StendhalRPZone("test", 20, 20);
		world.addRPZone(zone2);
		final ConsumableItem c200_1 = new ConsumableItem("cheese", "", "", attributes);
		zone.add(c200_1);
		final Poisoner poisoner = new Poisoner();
		final Player bob = PlayerTestHelper.createPlayer("player");
		zone.add(bob);
		final Map<String, String> scrollAttributes = new HashMap<String, String>();
		scrollAttributes.put("menu", "Use|Use");
		scrollAttributes.put("quantity", "1");
		MarkedScroll scroll = new MarkedScroll("marked scroll","scroll","marked", scrollAttributes);
		bob.equip("bag", scroll);
		poisoner.feed(c200_1, bob);
		assertFalse(scroll.useScroll(bob));
	}

}
