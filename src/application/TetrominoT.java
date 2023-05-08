package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TetrominoT extends Tetromino {
	
	public static final int BLOCK_SIZE = Tetris.BLOCK_SIZE;
	public static int BOARD_WIDTH = Tetris.BOARD_WIDTH;
	public static int BOARD_HEIGHT = Tetris.BOARD_HEIGHT;

	TetrominoT(Color color) {
		super(color);
		
		//Calculated spawn location
		super.getRectA().setX(BOARD_WIDTH / 2);
		super.getRectB().setX(BOARD_WIDTH / 2);
		super.getRectC().setX(BOARD_WIDTH / 2 + BLOCK_SIZE);
		super.getRectD().setX(BOARD_WIDTH / 2 - BLOCK_SIZE);

		super.getRectB().setY(BLOCK_SIZE);
		super.getRectC().setY(BLOCK_SIZE);
		super.getRectD().setY(BLOCK_SIZE);
		
	}

    public void rotate() {
        // We can rotate a T-shaped block by rotating the 3 rectangles
        // around the center rectangle, which is RectD in this case.
        Rectangle center = super.getRectB();
        
        for (Rectangle r : super.getPoints()) {
            if (r != center) {
                // Calculate the new position of the rectangle after rotation
                double xDiff = r.getX() - center.getX();
                double yDiff = r.getY() - center.getY();
                r.setX(center.getX() - yDiff);
                r.setY(center.getY() + xDiff);
            }
        }
        
	    super.Rotation_Index = (super.Rotation_Index + 1) % 4;

    }


}
