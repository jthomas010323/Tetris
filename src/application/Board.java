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
	private ArrayList<ArrayList<Rectangle>> Points_Collection;

	Board(int NUM_ROW, int NUM_COL){
		row = NUM_ROW;
		col = NUM_COL;
		
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
	void MOVE_DOWN(Tetromino Tetromino) {
		Rectangle a = Tetromino.getRectA();
		Rectangle b = Tetromino.getRectB();
		Rectangle c = Tetromino.getRectC();
		Rectangle d = Tetromino.getRectD();
		if((a.getY()+BLOCK_SIZE>BOARD_HEIGHT-BLOCK_SIZE)
			||(b.getY()+BLOCK_SIZE>BOARD_HEIGHT-BLOCK_SIZE)
			||(c.getY()+BLOCK_SIZE>BOARD_HEIGHT-BLOCK_SIZE)
			||(d.getY()+BLOCK_SIZE>BOARD_HEIGHT-BLOCK_SIZE))
		{
			this.Board[(int) (a.getY()/BLOCK_SIZE)][(int) (a.getX()/BLOCK_SIZE)]= 1;
			this.Board[(int) (b.getY()/BLOCK_SIZE)][(int) (b.getX()/BLOCK_SIZE)]= 1;
			this.Board[(int) (c.getY()/BLOCK_SIZE)][(int) (c.getX()/BLOCK_SIZE)]= 1;
			this.Board[(int) (d.getY()/BLOCK_SIZE)][(int) (d.getX()/BLOCK_SIZE)]= 1;
		}
		else {
			a.setY(a.getY() + BLOCK_SIZE);
			b.setY(b.getY() + BLOCK_SIZE);
			c.setY(c.getY() + BLOCK_SIZE);
			d.setY(d.getY() + BLOCK_SIZE);
			
		}
    		
    	
    }
	public int getBoard(double x, double y) {
		return Board[(int) x][(int) y];
	}
	public boolean placeShape(Tetromino tetromino) {
	    // get the x and y position of each rectangle in the tetromino
	    ArrayList<Rectangle> points = tetromino.getPoints();

	    return true;
	}
	public void setBoard(double x, double y, int value) {
		
	}

	/*
	 * 
	 */
	public boolean isClear() {
		for(int currentRow = 0; currentRow < row; currentRow++) {
			for(int currentCol = 0; currentCol < col; currentCol++) {
				if(Board[currentRow][currentCol] == 0) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	/*
	 * Checks if the piece movement is legal
	 */
	public boolean isCollision(Tetromino tetromino) {
		
		int converted_X;
		int converted_Y;
		
		for(Rectangle rect : tetromino.getPoints()) {
			
			converted_X = (int) (rect.getX()/Tetris.BLOCK_SIZE);
			converted_Y = (int) (rect.getY()/Tetris.BLOCK_SIZE);
			
			if(converted_X > Tetris.NUM_COL) {
				return true;
			}

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

	
}
