package chaumette.othello.util;

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
	POSSIBLE_WHITE
}
