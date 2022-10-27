/**
 * @author scrooge
 */

package chaumette.othello.gui;

import chaumette.othello.util.Constants;
import chaumette.othello.util.PlayerColor;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Buttons used as the cells of the TicTacToeBoard
 */
public class GameGridButton extends Button {

	/**
	 * Circle shape
	 */
	private Circle circle;

	/**
	 * Sets the symbol displayed on the button according to the given parameter
	 */
	public void setDrawing(PlayerColor c) {
		if (circle == null) {
			createComponents();
		}
		if (c == PlayerColor.EMPTY) {
			circle.setVisible(false);
		} else {
			circle.setVisible(true);
			switch (c) {
				case WHITE -> circle.setFill(Color.WHITE);
				case BLACK -> circle.setFill(Color.BLACK);
			}
		}
	}

	/**
	 * Creates and adds the Shapes for the button symbols to the children list
	 */
	private void createComponents() {
		circle = new Circle(getWidth() / 2, getHeight() / 2, 20);
		circle.setStroke(Color.BLACK);
		circle.setStrokeWidth(Constants.DEFAULT_STROKE_SIZE);
		circle.setFill(Color.BLACK);
		getChildren().add(circle);
	}

}
