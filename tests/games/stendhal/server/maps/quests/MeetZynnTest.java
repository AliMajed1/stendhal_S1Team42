package games.stendhal.server.maps.quests;

import static org.junit.Assert.assertEquals;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.semos.library.HistorianGeographerNPC;
import utilities.PlayerTestHelper;
import utilities.QuestHelper;

public class MeetZynnTest{
	private Player player = null;
	private SpeakerNPC npc = null;
	private Engine en = null;
	private AbstractQuest quest; 
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
	}
	
	@Before
	public void setUp() {
		final StendhalRPZone zone = new StendhalRPZone("admin_test");
		new HistorianGeographerNPC().configureZone(zone, null);
		npc = SingletonRepository.getNPCList().get("Zynn Iwuhos");
		
		en = npc.getEngine();
		
		quest = new MeetZynn();
		quest.addToWorld();
		
		player = PlayerTestHelper.createPlayer("Max");
	}
	
	@Test
	public void DialogueGivesXPTest() {
		player.setXP(100);
		
		en.step(player, "hello");
		en.step(player, "help");
		en.step(player, "geography");
		assertEquals("Let's talk about the different #places you can visit on Faiumoni! I can also help you #get and #use a map, or give you advice on using the psychic #SPS system.", getReply(npc));
		en.step(player, "help");
		en.step(player, "history");
		assertEquals("At present, there are two significant powers on Faiumoni; the Deniran Empire, and the dark legions of Blordrough. Blordrough has recently conquered the south of the island, seizing several steel mines and a large gold mine. At present, Deniran still controls the central and northern parts of Faiumoni, including several gold and mithril mines.", getReply(npc));
		en.step(player, "help");
		en.step(player, "news");
		assertEquals("The Deniran Empire is currently seeking adventurers to sign on as mercenaries with their army to retake southern Faiumoni from the forces of Blordrough. Unfortunately Blordrough is still holding out against everything the Empire can throw at him.", getReply(npc));
		
		assertEquals(115, player.getXP());
	}
}




