package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Random;

public class Tetris extends Application {

    //define constants
	static final int NUM_ROW = 24;
	static final int NUM_COL = 12;
	
    static final int BLOCK_SIZE = 25;
    static final int BOARD_WIDTH = BLOCK_SIZE * NUM_COL;
    static final int BOARD_HEIGHT = BLOCK_SIZE * NUM_ROW;
    static final int CELL_SIZE = BOARD_WIDTH/(NUM_COL);
    
    private Group blocksGroup = new Group();
    private Pane gamePane = new Pane(blocksGroup);
    private Scene gameScene = new Scene(new BorderPane(gamePane), BOARD_WIDTH + 360, BOARD_HEIGHT);
    private AnimationTimer gameLoop;

    private Tetromino currentTetromino;
    private Tetromino nextShape;
    private Board gameBoard;
    private PiecesController Pieces_Controller = new PiecesController();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
        	
        	System.out.println("Dimensions: " + BOARD_WIDTH + "x" + BOARD_HEIGHT);
            //Spawn a new Tetromino
            currentTetromino = spawnTetromino();
            
            //Create a board
            gameBoard = createBoard();
            Pane boardPane = gameBoard.createBoardPane();
            gamePane.getChildren().add(boardPane);
            
            gameLoop = new AnimationTimer() {
            //start game loop
                private long lastUpdate = 0;

				@Override
				public void handle(long now) {
					   // Only update the game state at a fixed interval (in nanoseconds)
	                if (now - lastUpdate >= 500_000_000) {
						gameBoard.MOVE_DOWN(currentTetromino);
						Pieces_Controller.Rotate_Clockwise(currentTetromino);
						
					     for (Rectangle block : currentTetromino.getPoints()) {
				            	System.out.println("Block at " + block.getX()/BLOCK_SIZE + "," + block.getY()/BLOCK_SIZE + " " + currentTetromino.getColor());
				            }
				         gameBoard.PrintBoard();

	                    lastUpdate = now;
	                }
	                
	            }
				
            };
            
            gameLoop.start();
            
            //Add Tetromino to board
            for (Rectangle block : currentTetromino.getPoints()) {
            	System.out.println("Spawned block at " + block.getX() + "," + block.getY() + " " + currentTetromino.getColor());
                blocksGroup.getChildren().add(block);
            }
            
            //Print the board state to console
            gameBoard.PrintBoard();
         
            
            gamePane.setStyle("-fx-background-color: gray;");
            
            primaryStage.setScene(gameScene);
            primaryStage.setTitle("T E T R I S");
            primaryStage.show();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static Tetromino spawnTetromino() {
    	
    	/*
    	 * Index for shapes
    	 * 0 = I
    	 * 1 = J
    	 * 2 = L
    	 * 3 = O
    	 * 4 = S
    	 * 5 = T
    	 * 6 = Z
    	 */

        final Tetromino[] POSSIBLE_SHAPES = {
                new TetrominoI(Color.CYAN),
                new TetrominoJ(Color.BLUE),
                new TetrominoL(Color.ORANGE),
                new TetrominoO(Color.YELLOW),
                new TetrominoS(Color.LIMEGREEN),
                new TetrominoT(Color.PINK),
                new TetrominoZ(Color.RED)
        };

        Random random = new Random();

        int index = random.nextInt(POSSIBLE_SHAPES.length);

        switch(index) {
        case 0:
        	System.out.println("Spawned I-Shaped Block");
        	break;
        case 1:
        	System.out.println("Spawned J-Shaped Block");
        	break;
        case 2:
        	System.out.println("Spawned L-Shaped Block");
        	break;
        case 3:
        	System.out.println("Spawned O-Shaped Block");
        	break;
        case 4:
        	System.out.println("Spawned S-Shaped Block");
        	break;
        case 5:
        	System.out.println("Spawned T-Shaped Block");
        	break;
        case 6:
        	System.out.println("Spawned Z-Shaped Block");
        	break;
        }
        
        return POSSIBLE_SHAPES[index];

    }

    public static Board createBoard() {
        Board gameBoard = new Board(NUM_ROW, NUM_COL);
        
        return gameBoard;
    }

}
