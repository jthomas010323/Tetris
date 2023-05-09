package application;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

//The Board class has several jobs including:
//Creates grid
//Tracks the location of all existing pieces
//checks for line clears
//clears lines


public class Board {
	static final int NUM_ROW = 24;
	static final int NUM_COL = 12;
	
    static final int BLOCK_SIZE = 25;
    static final int BOARD_WIDTH = BLOCK_SIZE * NUM_COL;
    static final int BOARD_HEIGHT = BLOCK_SIZE * NUM_ROW;
 
	public static final int CELL_SIZE = Tetris.CELL_SIZE;

	private int row;
	private int col;
	private int score;
	private int[][] Board;
	private boolean gameOver;
	private ArrayList<Tetromino> Points_Collection;

	Board(int NUM_ROW, int NUM_COL){
		row = NUM_ROW;
		col = NUM_COL;
		
		Points_Collection = new ArrayList<Tetromino>();
		this.Board = new int[row][col];
		score = 0;
		gameOver = false;
	}
	
	/*
	 * The board should only place a block when it is at the bottom of the board
	 * or if the block directly under it is another block
	 * 
	 * The console will mark the position of the placed block
	 * 1 = occupied
	 * 0 = unoccupied
	 */
	public boolean placeShape(Tetromino tetromino) {
		
		if(tetromino == null)
			return false;
		
	    Points_Collection.add(tetromino);
		setBoard();
		
	    return true;
	}
	
	/*
	 * 
	 */
	public void setBoard() {
		
		for(Tetromino shape : Points_Collection) {
			for(Rectangle rect : shape.getPoints()) {
				int converted_X = (int) (rect.getX()/Tetris.BLOCK_SIZE);
				int converted_Y = (int) (rect.getY()/Tetris.BLOCK_SIZE);
				
				System.out.println("Setting at " + converted_Y + ","+ converted_X);
				Board[converted_Y][converted_X] = 1;
			}
		}
		
	}

	/*
	 * Checks if an entire row is filled up 
	 * if the row is filled up, clear the row by setting the entire row to 0 and shifting everything down 
	 */
	public int clearFullRows() {
	    int numRowsCleared = 0;
	    
	    for (int row = 0; row < Tetris.NUM_ROW; row++) {
	        boolean isRowFull = true;
	        for (int col = 0; col < Tetris.NUM_COL; col++) {
	            if (Board[row][col] == 0) {
	            	// if there is a 0 in a line, the row is not full
	                isRowFull = false;
	                break;
	            }
	        }
	        
	        if (isRowFull) {
	            // Clear the row 
	            for (int col = 0; col < Tetris.NUM_COL; col++) {
	                Board[row][col] = 0;
	            }
	            
	            // Move all rows above this row down by one
	            for (int r = row - 1; r >= 0; r--) {
	                for (int col = 0; col < Tetris.NUM_COL; col++) {
	                    Board[r+1][col] = Board[r][col];
	                    Board[r][col] = 0;
	                }
	            }
	            
	            numRowsCleared++;
	        }
	    }
	    
	    return numRowsCleared;
	}

	/*
	 * Checks if the piece movement is legal
	 * returns true if the operation is allowed 
	 */
	public boolean isInBounds(Rectangle newRect) {

	    int convertedX = (int) (newRect.getX() / Tetris.BLOCK_SIZE);
	    int convertedY = (int) (newRect.getY() / Tetris.BLOCK_SIZE);

	    // Check if block is outside the grid
	    if (convertedX < 0 || convertedX >= Tetris.NUM_COL || convertedY >= Tetris.NUM_ROW) {
	        return false;
	    }

	    // Check if block collides with existing block on board
	    if (Board[convertedY][convertedX] == 1) {
	        return false;
	    }

	    return true;
	}

	
	/*
	 * The PrintBoard() function will print the current board state to the console
	 * if there is a block at the location
	 */
	public void PrintBoard() {
		System.out.println(col + "x" + row);
				
		for(int i = 0; i < Tetris.NUM_ROW; i++) {
			for(int j = 0; j < Tetris.NUM_COL; j++) {
				if(Board[i][j] == 1) {
					System.out.print("X ");
				}else {
					System.out.print("O ");
				}
				
			}
			
			System.out.println();
		}
	}
	
	/*
	 * Creates a Pane object to be painted on the 
	 */
	
	public Pane createBoardPane() {
	    Pane boardPane = new Pane();
	    
	    for (int currentRow = 0; currentRow < row; currentRow++) {
	        for (int currentCol = 0; currentCol < col; currentCol++) {
	            Rectangle rect = new Rectangle(currentCol * CELL_SIZE, currentRow * CELL_SIZE, CELL_SIZE, CELL_SIZE);
	            rect.setFill(Color.TRANSPARENT);
	            rect.setStroke(Color.BLACK);
	            boardPane.getChildren().add(rect);
	        }
	    }
	    return boardPane;
	}

	public boolean isOccupied(Rectangle newPos) {
		
        int converted_X = (int) (newPos.getX() / Tetris.BLOCK_SIZE);
        int converted_Y = (int) (newPos.getY() / Tetris.BLOCK_SIZE);

		if(Board[converted_Y][converted_X] == 1) {
			return true;
		}
		
		return false;
	}
	
}
