package games.stendhal.server.maps.deniran.cityinterior.weaponsshop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class FurnitureSellerNpcTest extends ZonePlayerAndNPCTestImpl
{
	private static final String ZONE_NAME = "int_deniran_weapons_shop";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();

		setupZone(ZONE_NAME);
	}
	
	public FurnitureSellerNpcTest() {
		setNpcNames("Max");
		setZoneForPlayer(ZONE_NAME);
		addZoneConfigurator(new FurnitureSellerNPC(), ZONE_NAME);
	}
	
	@Test
	public void testHiAndBye() {
		final SpeakerNPC npc = getNPC("Max");
		assertNotNull(npc);
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hello"));
		assertEquals("Hello, and welcome to the deniran weapon shop furniture section. Would you like to #buy any furniture today?", getReply(npc));

		assertTrue(en.step(player, "bye"));
		assertEquals("Bye, enjoy your day!", getReply(npc));
	}
}
