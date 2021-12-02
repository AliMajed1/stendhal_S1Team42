package games.stendhal.server.maps.denirancity;

import static org.junit.Assert.*;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import games.stendhal.server.maps.deniran.StallWorkerNPC;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.mapstuff.sign.ShopSign;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.ZonePlayerAndNPCTestImpl;

public class FleaMarketExistTest extends ZonePlayerAndNPCTestImpl {
	
	
	private static StallWorkerNPC worker = new StallWorkerNPC();
	
	private static final String ZONE_NAME = "testzone";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setupZone(ZONE_NAME, false);
	}
	
	public FleaMarketExistTest() {
		super(ZONE_NAME, "Stall Worker1");
	}
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		worker.configureZone(zone, null);
	}
	
	
	
	@Test
	public void workerChatTest() {
		SpeakerNPC worker = SingletonRepository.getNPCList().get("Stall Worker1");
		Engine en1 = worker.getEngine();
		
		en1.step(player, "hi");
		assertEquals("Welcome to The Flea Market.", getReply(worker));
		en1.step(player, "help");
		assertEquals("If you would like to buy something, ask me about my #prices and I will tell you what I #offer.", getReply(worker));
		en1.step(player, "job");
		assertEquals("I manage this Stall. you can see the prices in the sign, if you want to but anything just say.", getReply(worker));
		en1.step(player, "buy unicorn");
		assertEquals("Sorry, I don't sell unicorns.", getReply(worker));
	}
	@Test
	public void marketSignInCorrectLocation() {
		final StendhalRPZone zone = player.getZone();
		
		final ShopSign sellBlackboard = (ShopSign) zone.getEntityAt(33, 94);
		
		final ShopSign sells = new ShopSign("Player's Stall", "Rented Stall", "You can buy these things from the stall worker.", false);
		sells.setEntityClass("blackboard");
		sells.setPosition(33, 94);
		zone.add(sells);
		
		assertEquals(sells.getX(), sellBlackboard.getX());
		assertEquals(sells.getY(), sellBlackboard.getY());
		assertEquals(sells.getZone(), sellBlackboard.getZone());
		assertEquals(sells.getName(), sellBlackboard.getName());
	}
	
}
