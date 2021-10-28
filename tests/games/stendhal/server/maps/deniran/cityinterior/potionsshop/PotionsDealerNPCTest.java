package games.stendhal.server.maps.deniran.cityinterior.potionsshop;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.mapstuff.sign.ShopSign;
import utilities.ZonePlayerAndNPCTestImpl;

public class PotionsDealerNPCTest extends ZonePlayerAndNPCTestImpl {
	
	private static PotionsDealerNPC dealer = new PotionsDealerNPC();
	
	private static final String ZONE_NAME = "testzone";
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		setupZone(ZONE_NAME, false);
	}
	
	public PotionsDealerNPCTest() {
		super(ZONE_NAME, "Wanda");
	}
	
	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		dealer.configureZone(zone, null);
	}
	
	@Test
	public void blackboardsInCorrectLocation() {
		buyBlackboardInCorrectLocation();
		sellBlackboardInCorrectLocation();
	}
	
	private void buyBlackboardInCorrectLocation() {
		final StendhalRPZone zone = player.getZone();
		
		final ShopSign buyBlackboard = (ShopSign) zone.getEntityAt(5, 6);
		
		final ShopSign buys = new ShopSign("deniranpotionsbuy", "Wanda's Shop (buying)", "You can sell these things to Wanda.", false);
		buys.setEntityClass("blackboard");
		buys.setPosition(5, 6);
		
		zone.add(buys);
		
		assertEquals(buys.getX(), buyBlackboard.getX());
		assertEquals(buys.getY(), buyBlackboard.getY());
		assertEquals(buys.getZone(), buyBlackboard.getZone());
		assertEquals(buys.getName(), buyBlackboard.getName());	
	}
	
	private void sellBlackboardInCorrectLocation() {
		final StendhalRPZone zone = player.getZone();
		
		final ShopSign sellBlackboard = (ShopSign) zone.getEntityAt(10, 6);
		
		final ShopSign sells = new ShopSign("deniranequipsell", "Wanda's Shop (selling)", "You can buy these things from Wanda.", false);
		sells.setEntityClass("blackboard");
		sells.setPosition(10, 6);
		
		zone.add(sells);
		
		assertEquals(sells.getX(), sellBlackboard.getX());
		assertEquals(sells.getY(), sellBlackboard.getY());
		assertEquals(sells.getZone(), sellBlackboard.getZone());
		assertEquals(sells.getName(), sellBlackboard.getName());
	}
	
}


