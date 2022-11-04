/**
 * @author scrooge
 */

package chaumette.othello.gui;

import chaumette.othello.util.Constants;
import chaumette.othello.util.PlayerColor;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Buttons used as the cells of the TicTacToeBoard
 */
public class GameGridButton extends Button {

	public GameGridButton() {
		super();
		this.setBackground(Background.fill(Color.GREEN));
	}

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
			circle.setFill(null);
			circle.setStroke(null);
			switch (c) {
				case WHITE -> circle.setFill(Color.WHITE);
				case BLACK -> circle.setFill(Color.BLACK);
				case POSSIBLE_BLACK -> circle.setStroke(Color.BLACK);
				case POSSIBLE_WHITE -> circle.setStroke(Color.WHITE);
			}
		}
	}

	/**
	 * Creates and adds the Shapes for the button symbols to the children list
	 */
	private void createComponents() {
		circle = new Circle(getWidth() / 2, getHeight() / 2, 20);
		circle.setStrokeWidth(Constants.DEFAULT_STROKE_SIZE);
		getChildren().add(circle);
	}

}
