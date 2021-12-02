package games.stendhal.server.maps.deniran;

import java.util.Arrays;

import java.util.Map;

import games.stendhal.common.grammar.Grammar;
import games.stendhal.common.parser.Sentence;
import games.stendhal.server.core.config.ZoneConfigurator;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;

import games.stendhal.server.entity.CollisionAction;
import games.stendhal.server.entity.Outfit;
import games.stendhal.server.entity.mapstuff.sign.ShopSign;
import games.stendhal.server.entity.npc.ChatAction;
import games.stendhal.server.entity.npc.ConversationPhrases;
import games.stendhal.server.entity.npc.ConversationStates;
import games.stendhal.server.entity.npc.EventRaiser;
import games.stendhal.server.entity.npc.ShopList;
import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.entity.npc.action.MultipleActions;
import games.stendhal.server.entity.npc.action.NPCEmoteAction;
import games.stendhal.server.entity.npc.action.PlaySoundAction;
import games.stendhal.server.entity.npc.action.SayTextAction;
import games.stendhal.server.entity.npc.behaviour.adder.SellerAdder;
import games.stendhal.server.entity.npc.behaviour.impl.SellerBehaviour;
import games.stendhal.server.entity.npc.condition.PlayerNextToCondition;
import games.stendhal.server.entity.player.Player;

public class StallWorkerNPC implements ZoneConfigurator {

	@Override
	public void configureZone(final StendhalRPZone zone, final Map<String, String> attributes) {
		buildNPC(zone);
		buildSigns(zone);
	}

	private void buildNPC(final StendhalRPZone zone) {
		final SpeakerNPC npc = new SpeakerNPC("Stall Worker1");

		

		final ShopList shops = SingletonRepository.getShopList();
	
		final Map<String, Integer> pricesSell = shops.get("Player's Stall");


		new SellerAdder().addSeller(npc, new SellerBehaviour(pricesSell));

		npc.addGreeting("Welcome to The Flea Market.");
		npc.addJob("I manage this Stall. you can see the prices in the sign, if you want to but anything just say.");
		npc.addHelp("If you would like to buy something, ask me about my #prices and I will tell you what I #offer.");
		npc.addQuest("I don't have anything for you to do. But I am trying to stock my inventory. If you want to help,"
				+ " just ask and I'll tell you the #prices I pay for potions and poisons.");

		npc.add(ConversationStates.ANY,
				Arrays.asList("price", "prices"),
				null,
				ConversationStates.ATTENDING,
				null,
				new ChatAction() {
					@Override
					public void fire(final Player player, final Sentence sentence, final EventRaiser raiser) {
						
						final int sellCount = pricesSell.size();

						final StringBuilder sb = new StringBuilder("I sell");
						int idx = 0;
						for (final String itemName: pricesSell.keySet()) {
							if (sellCount > 1 && idx == sellCount - 1) {
								sb.append(" and");
							}
							sb.append(" " + Grammar.plural(itemName) + " for " + Integer.toString(pricesSell.get(itemName)));
							if (sellCount > 1 && idx < sellCount - 1) {
								sb.append(",");
							}
							idx++;
						}

						raiser.say(sb.toString());
					}
				});

		npc.add(ConversationStates.ANY,
				ConversationPhrases.GOODBYE_MESSAGES,
				null,
				ConversationStates.IDLE,
				null,
				new MultipleActions(
						new PlaySoundAction("kiss-female-01"),
						new SayTextAction("Please come again.")
				)
		);

		npc.add(ConversationStates.IDLE,
				"kiss",
				new PlayerNextToCondition(),
				ConversationStates.IDLE,
				null,
				new MultipleActions(
						new PlaySoundAction("kiss-female-01"),
						new NPCEmoteAction("kisses", "on the cheek")
				));

		npc.setPosition(32, 93);
		npc.setDescription("You see a terrifying implementation of a stall worker that haven't been completed yet.");
		npc.setCollisionAction(CollisionAction.STOP);
		npc.setOutfit(new Outfit("body=0,head=6,mouth=1,eyes=1,dress=22,mask=1,hair=2"));

		zone.add(npc);
	}
	
	private void buildSigns(final StendhalRPZone zone) {
		
		final ShopSign sells = new ShopSign("Player's Stall", "Rented Stall", "You can buy these things from the stall worker.", false);
		sells.setEntityClass("blackboard");
		sells.setPosition(33, 94);
		zone.add(sells);
	}
	
	
}
