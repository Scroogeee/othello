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
 * Represents a move in the game. It is a constant class.
 */
public class Move {

	/**
	 * X coordinate on the board
	 */
	public final int x;

	/**
	 * Y coordinate on the board
	 */
	public final int y;

	/**
	 * Constructs the move. It does not perform any checks on the values.
	 *
	 * @param x X coordinate on the board. It is indexed from 0, that is,
	 *          its possible vales are 0, 1, etc.
	 * @param y Y coordinate on the board. It is indexed from 0, that is,
	 *          its possible vales are 0, 1, etc.
	 */
	public Move(int x, int y) {
		this.x = x;
		this.y = y;
	}

}

