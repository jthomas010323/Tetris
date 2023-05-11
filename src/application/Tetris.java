package application;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
    private Board gameBoard;
    private PiecesController Pieces_Controller = new PiecesController();

    private boolean paused = false;
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
            gamePane.getChildren().add(gameBoard.getBoardPane());
            
            //Handle keyboard inputs
            gameScene.setOnKeyPressed(event -> {
                KeyCode keyCode = event.getCode();
                if (keyCode == KeyCode.LEFT || keyCode == KeyCode.A) {
                	Pieces_Controller.Move_Left(currentTetromino, gameBoard);
                } else if (keyCode == KeyCode.RIGHT || keyCode == KeyCode.D) {
                	Pieces_Controller.Move_Right(currentTetromino, gameBoard);
                } else if (keyCode == KeyCode.DOWN || keyCode == KeyCode.SPACE || keyCode == KeyCode.S) {
                	Pieces_Controller.Move_Down(currentTetromino, gameBoard);
                } else if (keyCode == KeyCode.UP || keyCode == KeyCode.Z || keyCode == KeyCode.W){
                	Pieces_Controller.Rotate_Clockwise(currentTetromino, gameBoard);
                }else if(keyCode == KeyCode.ESCAPE) {
                	paused = !paused;
                }
            });
            
            gameLoop = new AnimationTimer() {
            //start game loop
                private long lastUpdate = 0;

				@Override
				public void handle(long now) {
				    // Only update the game state at a fixed interval (in nanoseconds)
				    if (now - lastUpdate >= 500_000_000) {
				        
				    	if(paused) {
				    		return;
				    	}
				    	
				    	if (Pieces_Controller.Move_Down(currentTetromino, gameBoard)) {
				            // Tetromino can move down, so move it down

				    	} else {
				            // Tetromino cannot move down, so place it on the board and spawn a new one
				            gameBoard.placeShape(currentTetromino); //Add shape to the ArrayList of placed shapes
				            
				            System.out.println("Before calling clear");
					        gameBoard.PrintBoard();

				            gameBoard.clearFullRows(blocksGroup);// Check and clear full rows
				            System.out.println("After calling clear");
					        gameBoard.PrintBoard();
					        
					        gameBoard.checkGameOver();
				            if(gameBoard.isGameOver()) {
				            	gameLoop.stop();
				            }
				            
				            //Tetromino is assigned to a new block
				            currentTetromino = spawnTetromino();
				            
				            //Add new tetromino to blockGroups
				            for (Rectangle block : currentTetromino.getPoints()) {
				                blocksGroup.getChildren().add(block);
				            }
				        }
				        
				        lastUpdate = now;
				    }
				}

            };
            
            gameLoop.start();

            //Add first piece to board
            for (Rectangle block : currentTetromino.getPoints()) {
                blocksGroup.getChildren().add(block);
            }
       
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
        gameBoard.createBoardPane();
        
        
        return gameBoard;
    }

}
