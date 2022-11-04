package chaumette.othello.util;

/**
 * Enum used to specify what a cell of the game grid currently holds
 */
public enum PlayerColor {
	EMPTY,
	BLACK,
	WHITE,
	POSSIBLE_BLACK,
	POSSIBLE_WHITE;

	public static PlayerColor invert(PlayerColor c) {
		switch (c) {
			case BLACK -> {
				return WHITE;
			}
			case WHITE -> {
				return BLACK;
			}
		}
		return EMPTY;
	}
}
