package application;

import java.util.ArrayList;
import javafx.animation.RotateTransition;
import javafx.scene.Node;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

//Controls the pieces
//Rotations
//Handles player input
//Stores offset data
public class PiecesController {
	static final int NUM_ROW = Tetris.NUM_ROW;
	static final int NUM_COL = Tetris.NUM_COL;
    static final int BLOCK_SIZE = Tetris.BLOCK_SIZE;
    static final int BOARD_WIDTH = Tetris.BOARD_WIDTH;
    static final int BOARD_HEIGHT = Tetris.BOARD_HEIGHT;
    static final int CELL_SIZE = Tetris.CELL_SIZE;

    void Rotate_Clockwise(Tetromino Tetromino ){
    	Tetromino.rotate();
    }
    
    void Move_Left(Tetromino Tetromino){
    	for(Rectangle rect : Tetromino.getPoints()) {
    		rect.setX(rect.getX() - BLOCK_SIZE);
    	}
    }
    
    void Move_Right(Tetromino Tetromino){
    	for(Rectangle rect : Tetromino.getPoints()) {
    		rect.setX(rect.getX() + BLOCK_SIZE);
    	}
    }
    
    void MOVE_UP(Tetromino Tetromino) {
    	for(Rectangle rect : Tetromino.getPoints()) {
    		rect.setY(rect.getY() - BLOCK_SIZE);
    	}
    }
    
    
    
    
}
