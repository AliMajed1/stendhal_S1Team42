package games.stendhal.server.entity.creature;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import games.stendhal.common.constants.Nature;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.maps.MockStendlRPWorld;
import static org.hamcrest.CoreMatchers.equalTo;


public class CentaurSusceptibilityTests {

	
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();

	}
	/**
	 * Test that the solar and glacier centaurs have the proper susceptibilties.
	 */
	
	@Test
	public void GetSolarCentaurSusceptibility() {
		final Creature cr = new Creature(SingletonRepository.getEntityManager().getCreature("solar centaur"));
		assertThat(cr.getSusceptibility(Nature.FIRE), equalTo(0.8));
		assertThat(cr.getSusceptibility(Nature.ICE), equalTo(1.2));
				
	}
	
	@Test
	public void GetGlacierCentaurSusceptibility() {
		final Creature cr = new Creature(SingletonRepository.getEntityManager().getCreature("glacier centaur"));
		assertThat(cr.getSusceptibility(Nature.ICE), equalTo(0.8));
		assertThat(cr.getSusceptibility(Nature.FIRE), equalTo(1.2));
	}
}
