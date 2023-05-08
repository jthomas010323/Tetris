package application;

import javafx.scene.paint.Color;

public class TetrominoZ extends Tetromino {
	
	public static final int BLOCK_SIZE = Tetris.BLOCK_SIZE;
	public static int BOARD_WIDTH = Tetris.BOARD_WIDTH;
	public static int BOARD_HEIGHT = Tetris.BOARD_HEIGHT;

	TetrominoZ(Color color) {
		super(color);
		
		//Calculated spawn position
        super.getRectA().setX(BOARD_WIDTH / 2 - BLOCK_SIZE * 2);
        super.getRectB().setX(BOARD_WIDTH / 2 - BLOCK_SIZE);
        super.getRectC().setX(BOARD_WIDTH / 2 - BLOCK_SIZE);
        super.getRectD().setX(BOARD_WIDTH / 2);

        super.getRectA().setY(0);
        super.getRectB().setY(0);
        super.getRectC().setY(BLOCK_SIZE);
        super.getRectD().setY(BLOCK_SIZE);
	
	}

    public void rotate() {
    	
    	//
        if (Rotation_Index == 0 || Rotation_Index == 2) {
            super.getRectA().setX(super.getRectA().getX() + BLOCK_SIZE);
            super.getRectA().setY(super.getRectA().getY() + BLOCK_SIZE);
            super.getRectC().setX(super.getRectC().getX() - BLOCK_SIZE);
            super.getRectC().setY(super.getRectC().getY() + BLOCK_SIZE);
            super.getRectD().setX(super.getRectD().getX() - BLOCK_SIZE * 2);
            super.getRectD().setY(super.getRectD().getY());
        } else if (Rotation_Index == 1 || Rotation_Index == 3) {
            super.getRectA().setX(super.getRectA().getX() - BLOCK_SIZE);
            super.getRectA().setY(super.getRectA().getY() - BLOCK_SIZE);
            super.getRectC().setX(super.getRectC().getX() + BLOCK_SIZE);
            super.getRectC().setY(super.getRectC().getY() - BLOCK_SIZE);
            super.getRectD().setX(super.getRectD().getX() + BLOCK_SIZE * 2);
            super.getRectD().setY(super.getRectD().getY());
        }
        
        super.Rotation_Index = (super.Rotation_Index + 1 ) % 4;
    }



}
