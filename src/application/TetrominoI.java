package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TetrominoI extends Tetromino {
	
	public static final int BLOCK_SIZE = Tetris.BLOCK_SIZE;
	public static int BOARD_WIDTH = Tetris.BOARD_WIDTH;
	public static int BOARD_HEIGHT = Tetris.BOARD_HEIGHT;

	TetrominoI(Color color) {
		super(color);
		//abcd
		//calculated spawn position
		super.getRectA().setX(BOARD_WIDTH / 2 - BLOCK_SIZE * 2);
		super.getRectB().setX(BOARD_WIDTH / 2 - BLOCK_SIZE);
		super.getRectC().setX(BOARD_WIDTH / 2);
		super.getRectD().setX(BOARD_WIDTH / 2 + BLOCK_SIZE);

  	}

	@Override
	public void rotate() {
	    Rectangle center = super.getRectB(); // get the center block, which is the second block
	    
	    switch (super.Rotation_Index) {
	        case 0:
	            // current orientation: vertical, rotate to horizontal
	            super.getRectA().setX(center.getX() - BLOCK_SIZE);
	            super.getRectA().setY(center.getY());
	            super.getRectC().setX(center.getX() + BLOCK_SIZE);
	            super.getRectC().setY(center.getY());
	            super.getRectD().setX(center.getX() + 2 * BLOCK_SIZE);
	            super.getRectD().setY(center.getY());
	            break;
	        case 1:
	            // current orientation: horizontal, rotate to vertical
	            super.getRectA().setX(center.getX());
	            super.getRectA().setY(center.getY() - BLOCK_SIZE);
	            super.getRectC().setX(center.getX());
	            super.getRectC().setY(center.getY() + BLOCK_SIZE);
	            super.getRectD().setX(center.getX());
	            super.getRectD().setY(center.getY() + 2 * BLOCK_SIZE);
	            break;
	        case 2:
	            // current orientation: vertical, rotate to horizontal
	            super.getRectA().setX(center.getX() + BLOCK_SIZE);
	            super.getRectA().setY(center.getY());
	            super.getRectC().setX(center.getX() - BLOCK_SIZE);
	            super.getRectC().setY(center.getY());
	            super.getRectD().setX(center.getX() - 2 * BLOCK_SIZE);
	            super.getRectD().setY(center.getY());
	            break;
	        case 3:
	            // current orientation: horizontal, rotate to vertical
	            super.getRectA().setX(center.getX());
	            super.getRectA().setY(center.getY() + BLOCK_SIZE);
	            super.getRectC().setX(center.getX());
	            super.getRectC().setY(center.getY() - BLOCK_SIZE);
	            super.getRectD().setX(center.getX());
	            super.getRectD().setY(center.getY() - 2 * BLOCK_SIZE);
	            break;
	    }
	    
	    super.Rotation_Index = ((Rotation_Index + 1) % 4); // update the rotation index
	}

}
