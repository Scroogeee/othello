package chaumette.othello.test;

/**
 * Used to describe what kind of players (human or computer) play in a given game
 */
public enum TestMode {
	/**
	 * A Human vs Human game
	 */
	HVH,

	/**
	 * A Human vs Computer game. The human goes first.
	 */
	HVC,

	/**
	 * A Human vs Computer game. The computer goes first.
	 */
	CVH,

	/**
	 * A Computer vs Computer game
	 */
	CVC
}
