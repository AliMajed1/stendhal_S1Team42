package games.stendhal.server.maps.nalwor.forest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static utilities.SpeakerNPCTestHelper.getReply;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.fsm.Engine;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class TrainerNPCTest extends ZonePlayerAndNPCTestImpl {

	private static final String ZONE_NAME = "dojo";
	private static final String npcName = "Omura Sumitada";
	private Dojo dojo;
	private static SpeakerNPC trainer;
	private int level=0;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
		
	}


	@Override
	@Before
	public void setUp() throws Exception {
		dojo=new Dojo();
		setZoneForPlayer(ZONE_NAME);
		setNpcNames(npcName);
		addZoneConfigurator(dojo, ZONE_NAME);

		super.setUp();
		trainer = getNPC("Omura Sumitada");
	}

	@Test
	public void initTest() {
		testEntities();
		testConversation();
	}

	private void testEntities() {
		assertNotNull(trainer);
		assertEquals(npcName, trainer.getName());
	}

	private void testConversation() {
		final Engine en = trainer.getEngine();
		final String greetReply = "This is the assassins' dojo.";
		final String trainReply = "At your level of experience, your attack strength is too high to train here at this time.";
		en.step(player, "hi");
		assertEquals(ConversationStates.ATTENDING, en.getCurrentState());
		assertEquals(greetReply, getReply(trainer));
		player.setLevel(level);
		en.step(player, "train");
		assertEquals(ConversationStates.ATTENDING, en.getCurrentState());
		assertEquals(trainReply, getReply(trainer));
		
	}

	
}
