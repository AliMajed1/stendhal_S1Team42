/* $Id$ */
/***************************************************************************
 *                   (C) Copyright 2003-2010 - Stendhal                    *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.status;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.entity.item.ConsumableItem;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;
import utilities.PlayerTestHelper;

public class SleepTest() {

	/**
	 * initialisation
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		MockStendlRPWorld.get();
	}

	/**
	 * Tests for sleep.
	 */
	@Test
	public void testSleeping() {
		/* Create an instance of a sleeping player */
		final Player player = PlayerTestHelper.createPlayer("bob");
		// set player to sleep
		assertTrue(player.hasStatus(StatusType.SLEEPING));
	}

	/**
	 * Tests for eating increasing healing while sleeping
	 */
	@Test
	public void testSleepingEating() {
		/* Create an instance of a sleeping player */
		final Player player = PlayerTestHelper.createPlayer("bob");
		final Player player2 = PlayerTestHelper.createPlayer("bob2");
		// set player to sleep
		// player2 eats
		// set player2 to sleep
		assertTrue(player.hasStatus(StatusType.SLEEPING));
		assertTrue(player2.hasStatus(StatusType.SLEEPING));
		// compare player vs player2 healing, player2 should be greater
	}
	
	/**
	 * Tests for poison slowing while sleeping
	 */
	@Test
	public void testSleepingPoisoned() {
		final String poisontype = "greater poison";
		final ConsumableItem poison = (ConsumableItem) SingletonRepository.getEntityManager().getItem(poisontype);

		final PoisonAttacker poisoner = new PoisonAttacker(100, poison);
		final Player player = PlayerTestHelper.createPlayer("bob");
		final Player player2 = PlayerTestHelper.createPlayer("bob2");
		poisoner.onAttackAttempt(player, SingletonRepository.getEntityManager().getCreature("snake"));
		poisoner.onAttackAttempt(player2, SingletonRepository.getEntityManager().getCreature("snake"));
		assertTrue(player.hasStatus(StatusType.POISONED));
		assertTrue(player2.hasStatus(StatusType.POISONED));
		// make the player go to sleep
		assertTrue(player.hasStatus(StatusType.SLEEPING));
		// check that the poison damage from poison, player2, is greater than the sleeping damage while poisoned, player
		
	}

}