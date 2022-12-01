/**
 * @author scrooge
 */

package chaumette.othello.test;

import chaumette.othello.util.PlayerColor;
import chaumette.othello.util.board.improved.ImprovedOthelloBoard;
import chaumette.othello.util.board.improved.ImprovedOthelloOneDimArrayBoard;
import chaumette.othello.util.players.ai.MiniMaxAI;
import szte.mi.Move;

public class OthelloAITester {
	static ImprovedOthelloBoard othelloBoard = new ImprovedOthelloOneDimArrayBoard(
			new PlayerColor[]{
					PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.WHITE,
					PlayerColor.EMPTY, PlayerColor.WHITE, PlayerColor.BLACK, PlayerColor.WHITE, PlayerColor.BLACK, PlayerColor.BLACK, PlayerColor.WHITE, PlayerColor.WHITE,
					PlayerColor.WHITE, PlayerColor.BLACK, PlayerColor.BLACK, PlayerColor.BLACK, PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.BLACK, PlayerColor.WHITE,
					PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.BLACK, PlayerColor.BLACK, PlayerColor.BLACK, PlayerColor.BLACK, PlayerColor.WHITE, PlayerColor.WHITE,
					PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.BLACK, PlayerColor.BLACK, PlayerColor.BLACK, PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.WHITE,
					PlayerColor.WHITE, PlayerColor.BLACK, PlayerColor.BLACK, PlayerColor.BLACK, PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.WHITE,
					PlayerColor.WHITE, PlayerColor.BLACK, PlayerColor.BLACK, PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.WHITE,
					PlayerColor.WHITE, PlayerColor.BLACK, PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.WHITE, PlayerColor.EMPTY, PlayerColor.WHITE
			}
	);

	public static void main(String[] args) {
		MiniMaxAI ai = new MiniMaxAI();
		Move m = ai.debugNextMove(othelloBoard, PlayerColor.BLACK);
		System.out.println(m);
	}

}
