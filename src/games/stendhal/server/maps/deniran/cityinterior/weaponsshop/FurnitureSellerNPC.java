package games.stendhal.server.maps.deniran.cityinterior.weaponsshop;

import java.util.Map;

import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.npc.ShopList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.behaviour.adder.SellerAdder;
import games.stendhal.server.entity.npc.behaviour.impl.SellerBehaviour;

public class FurnitureSellerNPC implements ZoneConfigurator
{
	private final ShopList shops = SingletonRepository.getShopList();
	
	@Override
	public void configureZone(final StendhalRPZone zone,final Map<String, String> attributes) {
		buildNPC(zone);
	}
	
	private void buildNPC(final StendhalRPZone zone) 
	{
		final SpeakerNPC npc = new SpeakerNPC("Max") 
		{
			@Override
			protected void createDialog() {
				addGreeting("Hello, and welcome to the deniran weapon shop furniture section. Would you like to #buy any furniture today?");
				addJob("I am the local furniture salesperson.");
				addGoodbye("Bye, enjoy your day!");
				new SellerAdder().addSeller(this, new SellerBehaviour(shops.get("deniranFurnitureStore")));
				
			}
			
			@Override
			protected void createPath() {
				setPath(null);
			}
		};
		npc.setEntityClass("orcbuyernpc");
		npc.setDescription("Max is a tall handsome man selling furniture in a peculiar location");
		npc.setPosition(3, 17);
		npc.initHP(100);
		npc.setDescription("Max sells furniture to all the people in Deniran");
		zone.add(npc);
	}
}