package games.stendhal.server.events;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendhalRPRuleProcessor;
import games.stendhal.server.maps.MockStendlRPWorld;
import marauroa.common.Log4J;
import marauroa.server.game.db.DatabaseFactory;
import utilities.PlayerTestHelper;

public class BestiaryEventTest {

	@BeforeClass
	public static void setUpBeforeClass () throws Exception {
		Log4J.init();
		MockStendlRPWorld.get();
		MockStendhalRPRuleProcessor.get();
		new DatabaseFactory().initializeDatabase();
		SingletonRepository.getEntityManager().populateCreatureList();
		
	}
	@After
	public void tearDown() throws Exception {
		MockStendhalRPRuleProcessor.get().clearPlayers();
	}
	@Test
	public void testCheckQuestionMarks() {
		final Player player = PlayerTestHelper.createPlayer("player");
		
		final BestiaryEvent event = new BestiaryEvent(player);
		
		final List<String> enemies = Arrays.asList(event.get("enemies").split(","));
		
		assertEquals(1, enemies.size());
		
		
		
	}

}
