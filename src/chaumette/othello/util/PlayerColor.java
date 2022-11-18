package chaumette.othello.util;

import java.util.Map;

/**
 * Enum used to specify what a cell of the game grid currently holds
 */
public enum PlayerColor {

	/**
	 * Board cell is empty
	 */
	EMPTY,
	/**
	 * Board cell is black
	 */
	BLACK,
	/**
	 * Board cell is white
	 */
	WHITE,
	/**
	 * Used to display a possible move for player black
	 */
	POSSIBLE_BLACK,
	/**
	 * Used to display a possible move for player white
	 */
	POSSIBLE_WHITE;

	private static final Map<PlayerColor, PlayerColor> inverses = Map.of(
			EMPTY, EMPTY,
			BLACK, WHITE,
			WHITE, BLACK,
			POSSIBLE_BLACK, POSSIBLE_WHITE,
			POSSIBLE_WHITE, POSSIBLE_BLACK
	);

	public static PlayerColor invert(PlayerColor c) {
		return inverses.get(c);
	}
}
