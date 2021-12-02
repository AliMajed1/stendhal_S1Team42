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
	
	@Test
	public void testBuyFurniture() {
		final SpeakerNPC npc = getNPC("Max");
		final Engine en = npc.getEngine();

		assertTrue(en.step(player, "hi"));
		assertEquals("Hello, and welcome to the deniran weapon shop furniture section. Would you like to #buy any furniture today?", getReply(npc));

		assertTrue(en.step(player, "job"));
		assertEquals("I am the local furniture salesperson.", getReply(npc));

		assertTrue(en.step(player, "offer"));
		assertEquals("I sell chair and stool.", getReply(npc));

		assertFalse(en.step(player, "quest"));

		assertTrue(en.step(player, "buy"));
		assertEquals("Please tell me what you want to buy.", getReply(npc));

		assertTrue(en.step(player, "buy dog"));
		assertEquals("Sorry, I don't sell dogs.", getReply(npc));

		assertTrue(en.step(player, "buy house"));
		assertEquals("Sorry, I don't sell houses.", getReply(npc));


		assertTrue(en.step(player, "buy a bunch of socks"));
		assertEquals("Sorry, I don't sell bunches of socks.", getReply(npc));

		assertTrue(en.step(player, "buy 0 chair"));
		assertEquals("Sorry, how many chairs do you want to buy?!", getReply(npc));

		assertTrue(en.step(player, "buy chair"));
		assertEquals("A chair will cost 150. Do you want to buy it?", getReply(npc));

		assertTrue(en.step(player, "no"));
		assertEquals("Ok, how else may I help you?", getReply(npc));

		assertTrue(en.step(player, "buy stool"));
		assertEquals("A stool will cost 50. Do you want to buy it?", getReply(npc));
		
		assertTrue(en.step(player, "yes"));
		assertEquals("Sorry, you don't have enough money!", getReply(npc));
		
		assertTrue(equipWithMoney(player, 200));

		assertTrue(en.step(player, "buy two chair"));
		assertEquals("2 chairs will cost 300. Do you want to buy them?", getReply(npc));

		assertTrue(en.step(player, "yes"));
		assertEquals("Sorry, you don't have enough money!", getReply(npc));

		assertTrue(en.step(player, "buy chair"));
		assertEquals("A chair will cost 150. Do you want to buy it?", getReply(npc));

		assertTrue(en.step(player, "yes"));
		assertEquals("Congratulations! Here is your chair!", getReply(npc));

		assertTrue(en.step(player, "buy stool"));
		assertEquals("A stool will cost 50. Do you want to buy it?", getReply(npc));

		assertTrue(en.step(player, "yes"));
		assertEquals("Congratulations! Here is your stool!", getReply(npc)); 
	}
	
	
	
	
	
	
	
	
	
}
