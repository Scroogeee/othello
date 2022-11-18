/*
 * Copyright (c) 2008 University of Szeged
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 2 as
 * published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 *
 */

package szte.mi;

/**
 * The interface the player has to implement.
 */

public interface Player {

	/**
	 * Performs initialization depending on the parameters.
	 *
	 * @param order Defines the order of the players. Value 0 means
	 *              this is the first player to move, 1 means second and so on.
	 *              For two-player games only values 0 and 1 are possible.
	 * @param t     Gives the remaining overall running time of the player in
	 *              ms. Initialization is also counted as running time.
	 * @param rnd   source of randomness to be used wherever random
	 *              numbers are needed
	 */
	void init(int order, long t, java.util.Random rnd);

	/**
	 * Calculates the next move of the player in a two player game.
	 * It is assumed that the player is stateful and the game is
	 * deterministic, so the parameters only
	 * give the previous move of the other player and remaining times.
	 *
	 * @param prevMove  the previous move of the opponent. It can be null,
	 *                  which means the opponent has not moved (or this is the first move).
	 * @param tOpponent remaining time of the opponent
	 * @param t         remaining time for this player
	 * @return the move selected by the player
	 */
	Move nextMove(Move prevMove, long tOpponent, long t);
}

