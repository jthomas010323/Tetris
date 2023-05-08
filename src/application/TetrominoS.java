package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TetrominoS extends Tetromino {
	
	public static final int BLOCK_SIZE = Tetris.BLOCK_SIZE;
	public static int BOARD_WIDTH = Tetris.BOARD_WIDTH;
	public static int BOARD_HEIGHT = Tetris.BOARD_HEIGHT;

	TetrominoS(Color color) {
		super(color);
		
		super.getRectA().setX(BOARD_WIDTH / 2);
		super.getRectB().setX(BOARD_WIDTH / 2);
		super.getRectB().setX(BOARD_WIDTH / 2 + BLOCK_SIZE);
		super.getRectC().setY(BLOCK_SIZE);
		super.getRectC().setX(BOARD_WIDTH / 2);
		super.getRectC().setY(BLOCK_SIZE);
		super.getRectD().setX(BOARD_WIDTH / 2 - BLOCK_SIZE);
		super.getRectD().setY(BLOCK_SIZE);
	
	}

	@Override
	public void rotate() {
		// TODO Auto-generated method stub
		if (Rotation_Index == 0 || Rotation_Index == 2) {
            super.getRectB().setX(super.getRectB().getX() - BLOCK_SIZE);
            super.getRectB().setY(super.getRectB().getY() - BLOCK_SIZE);
            super.getRectC().setX(super.getRectC().getX() + BLOCK_SIZE);
            super.getRectC().setY(super.getRectC().getY() - BLOCK_SIZE);
            super.getRectD().setX(super.getRectD().getX() + BLOCK_SIZE * 2);
            super.getRectD().setY(super.getRectD().getY());
        } else if (Rotation_Index == 1 || Rotation_Index == 3) {
            super.getRectB().setX(super.getRectB().getX() + BLOCK_SIZE);
            super.getRectB().setY(super.getRectB().getY() + BLOCK_SIZE);
            super.getRectC().setX(super.getRectC().getX() - BLOCK_SIZE);
            super.getRectC().setY(super.getRectC().getY() + BLOCK_SIZE);
            super.getRectD().setX(super.getRectD().getX() - BLOCK_SIZE * 2);
            super.getRectD().setY(super.getRectD().getY());
        }
		super.Rotation_Index = (super.Rotation_Index + 1 ) % 4;
	}

}
