package application;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TetrominoJ extends Tetromino {
	
	public static final int BLOCK_SIZE = Tetris.BLOCK_SIZE;
	public static int BOARD_WIDTH = Tetris.BOARD_WIDTH;
	public static int BOARD_HEIGHT = Tetris.BOARD_HEIGHT;

	TetrominoJ(Color color) {
		super(color);
		
		/*
		 * - - a - -
		 * - - b - -
		 * - d c - -
		 */
		
		super.getRectA().setX(BOARD_WIDTH / 2);
		super.getRectB().setX(BOARD_WIDTH / 2);
		super.getRectC().setX(BOARD_WIDTH / 2);
		super.getRectD().setX(BOARD_WIDTH / 2 - BLOCK_SIZE);

		super.getRectB().setY(BLOCK_SIZE);
		super.getRectC().setY(2 * BLOCK_SIZE);
		super.getRectD().setY(2 * BLOCK_SIZE);
	}

	@Override
	public void rotate() {
		// TODO Auto-generated method stub
		Rectangle center = super.getRectB(); // get the center block, which is the second block
		 switch(super.Rotation_Index) {
		 /*   a 
		  *   b
		  * d c
		  *
		  * to
		  * d
		  * c b a
		  *  
		  */
		 case 0:
			 super.getRectA().setX(center.getX() + BLOCK_SIZE);
			 super.getRectA().setY(center.getY());
			 super.getRectC().setX(center.getX() - BLOCK_SIZE);
			 super.getRectC().setY(center.getY());
			 super.getRectD().setX(center.getX() -  BLOCK_SIZE);
			 super.getRectD().setY(center.getY() - BLOCK_SIZE);
			 break;
			 /* d
			  * c b a 
			  * 
			  * to
			  * 
			  *   c d
 			  *   b
			  *   a
			  */
		 case 1:
			 super.getRectA().setX(center.getX());
			 super.getRectA().setY(center.getY() + BLOCK_SIZE);
			 super.getRectC().setX(center.getX());
			 super.getRectC().setY(center.getY() - BLOCK_SIZE);
			 super.getRectD().setX(center.getX() + BLOCK_SIZE);
			 super.getRectD().setY(center.getY() - BLOCK_SIZE);
			 break;
			 /*   c d
			  *   b
			  *   a
			  *   
			  * to
			  * 
			  * a b c
			  *     d  
			  */
		 case 2:
			 super.getRectA().setX(center.getX() - BLOCK_SIZE);
			 super.getRectA().setY(center.getY());
			 super.getRectC().setX(center.getX() + BLOCK_SIZE);
			 super.getRectC().setY(center.getY());
			 super.getRectD().setX(center.getX() + BLOCK_SIZE);
			 super.getRectD().setY(center.getY() + BLOCK_SIZE);
			 break;
			 /* 	
			  * a b c
			  *     d
			  * to
			  * 
			  *   a 	
			  *   b 
			  * d c   
			  */
		 case 3:
			 super.getRectA().setX(center.getX());
			 super.getRectA().setY(center.getY() - BLOCK_SIZE);
			 super.getRectC().setX(center.getX());
			 super.getRectC().setY(center.getY() + BLOCK_SIZE);
			 super.getRectD().setX(center.getX() - BLOCK_SIZE);
			 super.getRectD().setY(center.getY() + BLOCK_SIZE);
			 break;
			 
		 }
		 super.Rotation_Index = ((Rotation_Index + 1) % 4);

	}

	@Override
	protected void resetPosition() {
		
		super.getRectA().setX(BOARD_WIDTH / 2);
		super.getRectB().setX(BOARD_WIDTH / 2);
		super.getRectC().setX(BOARD_WIDTH / 2);
		super.getRectD().setX(BOARD_WIDTH / 2 - BLOCK_SIZE);

		super.getRectA().setY(0);
		super.getRectB().setY(BLOCK_SIZE);
		super.getRectC().setY(2 * BLOCK_SIZE);
		super.getRectD().setY(2 * BLOCK_SIZE);
		
		super.Rotation_Index = 0;

	}

}
