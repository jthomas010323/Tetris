package application;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import static javafx.scene.transform.Rotate.Y_AXIS;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

public class Tetris extends Application {

    //define constants
	static final int NUM_ROW = 24; //row size
	static final int NUM_COL = 12; //col size
	
    static final int BLOCK_SIZE = 25; //size of block
    static final int BOARD_WIDTH = BLOCK_SIZE * NUM_COL; //width of board
    static final int BOARD_HEIGHT = BLOCK_SIZE * NUM_ROW; //length of board
    static final int CELL_SIZE = BOARD_WIDTH/(NUM_COL);	//size of each cell
    
    BorderPane node = new BorderPane();	//Node object
	Scene mainMenuScene = new Scene(node,500,575); //creating scene
    private Group blocksGroup = new Group(); 
    private Pane nextBlockPane = new Pane(blocksGroup);
    private Pane gamePane = new Pane(blocksGroup); //pane for game
    
    private Scene gameScene = new Scene(new BorderPane(gamePane), BOARD_WIDTH + 250, BOARD_HEIGHT);
    private AnimationTimer gameLoop;
    private Rectangle nextBlockA;  //hosting the  rectangle A for next block 
    private Rectangle nextBlockB; //hosting the  rectangle B for next block 
    private Rectangle nextBlockC; //hosting the  rectangle C for next block 
    private Rectangle nextBlockD; //hosting the  rectangle D for next block 
 
    private Tetromino currentTetromino; //current block
    private Tetromino nextTetromino; //next block
    private Tetromino nextNextTetromino; //block after next block
    private Tetromino holdBlock; //pause game
    
    ArrayList<Rectangle> rectangleList = new ArrayList<>(); //contains all blocks
    
    private static Board gameBoard;  //gameboard
    private PiecesController Pieces_Controller = new PiecesController();  //piece controller object

    private boolean paused = false;
    private boolean isMainMenu = true;
    HashMap<Color, Rectangle> mainMap = new HashMap<Color, Rectangle>(); //to find colors and rectangles accociateion. 
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
        	
        	Media BGM = new Media(new File("assets/SFX/Brilliant_Wings.mp3").toURI().toString());  //media
        	MediaPlayer BGM_Player = new MediaPlayer(BGM);
        	Media soundmenu = new Media(new File("assets/SFX/Title_Screen.mp3").toURI().toString());
            MediaPlayer mediaPlayerMenu = new MediaPlayer(soundmenu);
        	Media sound2 = new Media(new File("assets/SFX/bubble.mp3").toURI().toString());
        	Media sound3 = new Media(new File("assets/SFX/button.mp3").toURI().toString());
        	gameScene.getStylesheets().add(getClass().getResource("tetrisapplication.css").toExternalForm());
        	Label gameOverLabel = new Label("GAME OVER");
        	Media soundFile_GO = new Media(new File("assets/SFX/gameoversound.mp3").toURI().toString());
        	MediaPlayer sound_GO = new MediaPlayer(soundFile_GO);
        	Font gameOverFont = Font.loadFont("file:./assets/fonts/tetrisyde.ttf", 95);
        	Font scoreLabelFont = Font.loadFont("file:./assets/fonts/tetrisyde.ttf", 45);
        	Font nextShapeLabelFont = Font.loadFont("file:./assets/fonts/tetrisyde.ttf", 45);
        	Font scoreFont = Font.loadFont("file:./assets/fonts/tetrisyde.ttf", 45);
        	gameOverLabel.setFont(gameOverFont);
        	gameOverLabel.setTextFill(Color.GHOSTWHITE);
        	gameOverLabel.setLayoutX(BLOCK_SIZE);
        	gameOverLabel.setLayoutY(BLOCK_SIZE*10);
        	Glow glowGO = new Glow();
        	glowGO.setLevel(1.5);
        	gameOverLabel.setEffect(glowGO);
        	
        	Label score = new Label("0");
        	Label scoreLabel = new Label("SCORE");
        	Label nextShapeLabel = new Label("NEXT SHAPE");
        	
        	RotateTransition scoreLabelRotation = new RotateTransition(Duration.seconds(1), scoreLabel);
        	scoreLabelRotation.setAxis(Y_AXIS);
        	scoreLabelRotation.setByAngle(360);
	        scoreLabel.setOnMouseClicked(e -> scoreLabelRotation.play());
	        
	        RotateTransition nextShapeLabelRotation = new RotateTransition(Duration.seconds(1), nextShapeLabel);
	        nextShapeLabelRotation.setAxis(Y_AXIS);
	        nextShapeLabelRotation.setByAngle(360);
	        nextShapeLabel.setOnMouseClicked(e -> nextShapeLabelRotation.play());
        	
	        score.setFont(scoreFont);
        	scoreLabel.setFont(scoreLabelFont);
        	nextShapeLabel.setFont(nextShapeLabelFont);
        	
        	score.setTextFill(Color.GHOSTWHITE);
        	scoreLabel.setTextFill(Color.GHOSTWHITE);
        	nextShapeLabel.setTextFill(Color.GHOSTWHITE);
        	
        	scoreLabel.setLayoutX(BLOCK_SIZE*14.5);
        	scoreLabel.setLayoutY(BLOCK_SIZE*4);
        	
        	nextShapeLabel.setLayoutX(BLOCK_SIZE*12.5);
        	nextShapeLabel.setLayoutY(BLOCK_SIZE*13);
        	
        	gamePane.getChildren().add(scoreLabel);
        	gamePane.getChildren().add(nextShapeLabel);
        	
        	
        	VBox scoreBox = new VBox();
            scoreBox.setPrefSize(180, 90);
            scoreBox.setLayoutX(BLOCK_SIZE*13.5);
            scoreBox.setLayoutY(BLOCK_SIZE*5.5);
            scoreBox.setAlignment(Pos.CENTER);
            scoreBox.setBorder(new Border(new BorderStroke(Color.GHOSTWHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
            scoreBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            gamePane.getChildren().add(scoreBox);
            
            VBox nextShapeBox = new VBox();
            nextShapeBox.setPrefSize(180, 140);
            nextShapeBox.setLayoutX(BLOCK_SIZE*13.5);
            nextShapeBox.setLayoutY(BLOCK_SIZE*14.5);
            nextShapeBox.setAlignment(Pos.CENTER);
            nextShapeBox.setBorder(new Border(new BorderStroke(Color.GHOSTWHITE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
            nextShapeBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));
            gamePane.getChildren().add(nextShapeBox);
        	
            scoreBox.getChildren().add(score);
            
            
            Timeline gameFxTimeline = new Timeline(
	                new KeyFrame(Duration.ZERO, event -> {
	                    Glow glowE = new Glow();
	                    glowE.setLevel(1.0);
	                    score.setEffect(glowE);
	                    scoreLabel.setEffect(glowE);
	                    nextShapeLabel.setEffect(glowE);
	                    scoreBox.setEffect(glowE);
	                    nextShapeBox.setEffect(glowE);
	                }),
	                new KeyFrame(Duration.seconds(1), event -> {
	                	score.setEffect(null);
	                    scoreLabel.setEffect(null);
	                    nextShapeLabel.setEffect(null);
	                    scoreBox.setEffect(null);
	                    nextShapeBox.setEffect(null);
	                })
	        );
        	
     
        	System.out.println("Dimensions: " + BOARD_WIDTH + "x" + BOARD_HEIGHT);
            //Spawn a new Tetromino
            currentTetromino = spawnTetromino();
            
            //Create a board
            gameBoard = createBoard();
           // gamePane.getChildren().add(gameBoard.getBoardPane());
            //Handle keyboard inputs
            gameScene.setOnKeyPressed(event -> {
                KeyCode keyCode = event.getCode();
                if (keyCode == KeyCode.LEFT || keyCode == KeyCode.A) {
                	Pieces_Controller.Move_Left(currentTetromino, gameBoard);
                } else if (keyCode == KeyCode.RIGHT || keyCode == KeyCode.D) {
                	Pieces_Controller.Move_Right(currentTetromino, gameBoard);
                } else if (keyCode == KeyCode.DOWN || keyCode == KeyCode.SPACE || keyCode == KeyCode.S) {
                	Pieces_Controller.Move_Down(currentTetromino, gameBoard);
                	gameBoard.setScore(gameBoard.getScore()+2);
                } else if (keyCode == KeyCode.UP || keyCode == KeyCode.Z || keyCode == KeyCode.W){
                	Pieces_Controller.Rotate_Clockwise(currentTetromino, gameBoard);
                }else if(keyCode == KeyCode.ESCAPE) {
                	paused = !paused;
                }else if(keyCode == KeyCode.E) {
                	holdBlock();
                }
            });
            currentTetromino = spawnTetromino();
            nextTetromino = spawnTetromino();
            nextNextTetromino = spawnTetromino();
            
            gameLoop = new AnimationTimer() {
            //start game loop
                private long lastUpdate = 0;
       
				@Override
				public void handle(long now) {
				    // Only update the game state at a fixed interval (in nanoseconds)
					
				    if (now - lastUpdate >= 500_000_000) {
				    	BGM_Player.setCycleCount(MediaPlayer.INDEFINITE);
				    	BGM_Player.setVolume(0.2);
				    	BGM_Player.play();
				        
				    	if(paused) {
				    		return;
				    	}
				    	
				    if (Pieces_Controller.Move_Down(currentTetromino, gameBoard)) {
				            // Tetromino can move down, so move it down
 
				    }else {
				            // Tetromino cannot move down, so place it on the board and spawn a new one
				    	
				    	
				    	
				    	
				            if(gameBoard.placeShape(currentTetromino)) {
				            	
				            	gamePane.getChildren().remove(nextBlockA);
					            gamePane.getChildren().remove(nextBlockB);
					            gamePane.getChildren().remove(nextBlockC);
					            gamePane.getChildren().remove(nextBlockD);
				            	nextBlockA = new Rectangle(nextNextTetromino.getRectA().getX()+BLOCK_SIZE*11,nextNextTetromino.getRectA().getY()+BLOCK_SIZE*16,nextNextTetromino.getRectA().getWidth(),nextNextTetromino.getRectA().getHeight());
				            	nextBlockB = new Rectangle(nextNextTetromino.getRectB().getX()+BLOCK_SIZE*11,nextNextTetromino.getRectB().getY()+BLOCK_SIZE*16,nextNextTetromino.getRectB().getWidth(),nextNextTetromino.getRectB().getHeight());
				            	nextBlockC = new Rectangle(nextNextTetromino.getRectC().getX()+BLOCK_SIZE*11,nextNextTetromino.getRectC().getY()+BLOCK_SIZE*16,nextNextTetromino.getRectC().getWidth(),nextNextTetromino.getRectC().getHeight());
				            	nextBlockD = new Rectangle(nextNextTetromino.getRectD().getX()+BLOCK_SIZE*11,nextNextTetromino.getRectD().getY()+BLOCK_SIZE*16,nextNextTetromino.getRectD().getWidth(),nextNextTetromino.getRectD().getHeight());
				            	nextBlockA.setFill(nextNextTetromino.getColor());
				            	nextBlockB.setFill(nextNextTetromino.getColor());
				            	nextBlockC.setFill(nextNextTetromino.getColor());
				            	nextBlockD.setFill(nextNextTetromino.getColor());
				            	nextBlockA.setStroke(Color.BLACK);
				            	nextBlockB.setStroke(Color.BLACK);
				            	nextBlockC.setStroke(Color.BLACK);
				            	nextBlockD.setStroke(Color.BLACK);
				    	        
				            	nextBlockA.setStrokeType(StrokeType.INSIDE);
				            	nextBlockB.setStrokeType(StrokeType.INSIDE);
				            	nextBlockC.setStrokeType(StrokeType.INSIDE);
				            	nextBlockD.setStrokeType(StrokeType.INSIDE);
				    	      
				            	nextBlockA.setStrokeWidth(2);
				            	nextBlockB.setStrokeWidth(2);
				            	nextBlockC.setStrokeWidth(2);
				            	nextBlockD.setStrokeWidth(2);
				            	gamePane.getChildren().addAll(nextBlockA,nextBlockB,nextBlockC,nextBlockD);
				            	currentTetromino = nextTetromino;
				            	nextTetromino = nextNextTetromino;
				            	nextNextTetromino = spawnTetromino();
				            	
				            } 
				            //Add shape to the ArrayList of placed shapes
				          
				            
				            //System.out.println("Before calling clear");
					        gameBoard.PrintBoard();
					       
				            int rowsCleared = gameBoard.clearFullRows(blocksGroup);// Check and clear full rows
				            if(rowsCleared > 0) {
				            	System.out.println("ROW CLEARED");
				            	String scoreAsString = Integer.toString(gameBoard.getScore());
				            	score.setText(scoreAsString);
				            	gameFxTimeline.play();
				            	//update the score in the game scene
				            	
					        	
				            }
				           // System.out.println("After calling clear");
					        gameBoard.PrintBoard();
					     //end the game
					       // currentTetromino = spawnTetromino();
					        for (Rectangle block : currentTetromino.getPoints()) {
					        	
					        	blocksGroup.getChildren().add(block);
					        	if(gameBoard.getBoard(block.getX()/BLOCK_SIZE, block.getY()/BLOCK_SIZE)==1) {
					        		Timeline backToMain_timeline = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
					        			mediaPlayerMenu.play();
					        			gamePane.getChildren().clear();
					        			isMainMenu = true;
					        			primaryStage.close();
					        		}));
					        		sound_GO.play();
					        		BGM_Player.stop();
					        		gameLoop.stop();
					        		gamePane.getChildren().add(gameOverLabel);
					        		gameBoard.resetBoard();
					        		backToMain_timeline.play();
					        		System.out.println("GAME OVER");
					        		break;
					        	}
					        }
				        }
				        
				        lastUpdate = now;
				    }
				    
				}

            };
            
            
            //gameLoop.start();

            //Add first piece to board
            for (Rectangle block : currentTetromino.getPoints()) {
                blocksGroup.getChildren().add(block);
               
                if(block.getY()>=Board.BOARD_HEIGHT){
                	
                    System.exit(0);
                }
           }
            
         /*   Image gameImage = new Image("C:\\Users\\danie\\eclipse-workspace\\MainForProject\\src\\application\\backgroundImage1.png");
            BackgroundImage gameSceneBGI = new BackgroundImage(gameImage,
        	        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
        	        BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        	        Background gameSceneBG = new Background(gameSceneBGI);
            gamePane.setBackground(gameSceneBG);*/
            
           
            gamePane.setStyle("-fx-background-color: black;");
            
            
            
            Button pauseButton = new Button("| |");
            pauseButton.setLayoutX(480);
            pauseButton.setLayoutY(10);
            pauseButton.setOnMousePressed(e ->{
	        	MediaPlayer mp7 = new MediaPlayer(sound3);
	        	mp7.play();
            	
	        });
            pauseButton.setOnMouseEntered(e ->{
	        	MediaPlayer mediaPlayer7 = new MediaPlayer(sound2);
            	mediaPlayer7.play();
	        });

            pauseButton.setOnMouseClicked(event -> {
                // Pause the game here
                
                // Create a popup window
                Stage popupWindow = new Stage();
                popupWindow.initModality(Modality.APPLICATION_MODAL);
                popupWindow.setTitle("Save and Quit?");
                Image icon2 = new Image("images/dog_tetrisShape.png");
                popupWindow.getIcons().add(icon2);
                gameLoop.stop();
                Label messageLabel = new Label("Do you want to save and quit the game?");
                
                Button yesButton = new Button("Yes");
                yesButton.setOnMouseClicked(saveAndQuitEvent -> {
                	GameData gameData = new GameData(gameBoard.getPoints_Collection(),gameBoard.getScore());

                    // Save game progress here
                	gameData.saveGameProgress();
                	primaryStage.close();
                	BGM_Player.stop();
                	mediaPlayerMenu.play();
                	isMainMenu = true;
                    // Close popup window and quit game
                    popupWindow.close();
                    // Call method to quit game
                });
                
                Button noButton = new Button("No");
                noButton.setOnMouseClicked(resumeEvent -> {
                    // Close popup window and resume game
                    popupWindow.close();
                    gameLoop.start();
                    // Call method to resume game
                });
                popupWindow.setOnCloseRequest(resumeEvent -> {
                    // resume game when popup window is closed
                    gameLoop.start();
                });
                
                HBox buttonBox = new HBox(yesButton, noButton);
                buttonBox.setSpacing(10);
                buttonBox.setAlignment(Pos.CENTER);
                
                VBox layout = new VBox(messageLabel, buttonBox);
                layout.setSpacing(20);
                layout.setAlignment(Pos.CENTER);
                
                Scene popupScene = new Scene(layout, 300, 150);
                popupWindow.setScene(popupScene);
                popupWindow.showAndWait();
            });

            //gamePane.getChildren().add(pauseButton);
            Image icon = new Image("File:assets/Images/dog(Opp)_tetrisShape.png");
            primaryStage.getIcons().add(icon);
           
          
			mainMenuScene.getStylesheets().add(getClass().getResource("tetrisapplication.css").toExternalForm());
			
			Image BGI = new Image("file:./assets/Images/tetris1bg.png");
			Image icon1 = new Image("file:./assets/Images/dog(Opp)_tetrisShape.png");
			
	        BackgroundImage backgroundImage = new BackgroundImage(BGI,
	        BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
	        BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
	        Background background = new Background(backgroundImage);
	        
	        DropShadow dropShadow = new DropShadow();
	        dropShadow.setColor(Color.DARKGRAY);
	        dropShadow.setOffsetX(5.0);
	        dropShadow.setOffsetY(5.0);
	        
	        

	        Label titleLabel = new Label("TETRIS");
	        Font titleFont = Font.loadFont("file:./assets/fonts/tetrisyde.ttf", 115);
	        
	        titleLabel.setFont(titleFont);
	        titleLabel.setTextFill(Color.GHOSTWHITE);
	        titleLabel.setEffect(dropShadow);
	        node.setTop(titleLabel);
	        node.setPadding(new Insets(50, 0, 0, 0));
	        BorderPane.setAlignment(titleLabel, Pos.CENTER);
	        
	        Glow glow = new Glow();
	        Glow glowCD = new Glow();
	        glowCD.setLevel(1.5);
	        
	        KeyValue glowOn = new KeyValue(glow.levelProperty(), 1.5);
	        KeyValue glowOff = new KeyValue(glow.levelProperty(), 0);
	        KeyValue glowOnAgain = new KeyValue(glow.levelProperty(), 1.5);
	     
	        Timeline timeline = new Timeline(
	        	    new KeyFrame(Duration.ZERO, glowOn),
	        	    new KeyFrame(Duration.seconds(3), glowOff),
	        	    new KeyFrame(Duration.seconds(4), glowOnAgain)
	        	);

	        
	        TranslateTransition translateOut = new TranslateTransition(Duration.seconds(5), titleLabel);
	        translateOut.setToX(420);
	        PauseTransition pause = new PauseTransition(Duration.seconds(7));
	        TranslateTransition translateIn = new TranslateTransition(Duration.seconds(2), titleLabel);
	        translateIn.setFromX(-400);
	        translateIn.setToX(0);
	      
	        
	        RotateTransition rotation = new RotateTransition(Duration.seconds(1), titleLabel);
	        rotation.setAxis(Y_AXIS);
	        rotation.setByAngle(360);
	        
	        SequentialTransition sequence = new SequentialTransition();
	        sequence.getChildren().addAll(translateIn,pause,translateOut);
	        titleLabel.setOnMouseClicked(e -> rotation.play());
	        sequence.setCycleCount(Animation.INDEFINITE);

	        timeline.setCycleCount(Timeline.INDEFINITE);
	        timeline.play();
	        titleLabel.setEffect(glow);
	        
	        // Play the sequential transition
	        sequence.play();
	        
	        Media sound = new Media(new File("assets/SFX/bubble.mp3").toURI().toString());
           // MediaPlayer mediaPlayer1 = new MediaPlayer(sound);
            mediaPlayerMenu.setCycleCount(MediaPlayer.INDEFINITE);
            Media sound1 = new Media(new File("assets/SFX/button.mp3").toURI().toString());
            MediaPlayer mp1 = new MediaPlayer(sound1);
            
            
	         int COUNTDOWN_FROM = 3;
	      
	        Button newGame = new Button("New Game");
	        newGame.setOnAction(event -> {
	            Text countdownText = new Text(String.valueOf(COUNTDOWN_FROM));
	            countdownText.setFont(titleFont);
	            countdownText.setFill(Color.GHOSTWHITE);
	            countdownText.setEffect(glowCD);
	            StackPane countdownPane = new StackPane(countdownText);
	            countdownPane.setBackground(background);
	            Scene countdownScene = new Scene(countdownPane, 500, 575);
	            mediaPlayerMenu.stop();
	            primaryStage.setScene(countdownScene);

	            Timeline countdownTimeline = new Timeline();
	            countdownTimeline.getKeyFrames().addAll(
	                    new KeyFrame(Duration.ZERO, new KeyValue(countdownText.textProperty(), String.valueOf(COUNTDOWN_FROM))),         
	                    new KeyFrame(Duration.seconds(1), new KeyValue(countdownText.textProperty(), String.valueOf(COUNTDOWN_FROM - 1))),
	                    new KeyFrame(Duration.seconds(2), new KeyValue(countdownText.textProperty(), String.valueOf(COUNTDOWN_FROM - 2))),
	                    new KeyFrame(Duration.seconds(3), new KeyValue(countdownText.textProperty(), "")));
	            		countdownTimeline.setOnFinished(finishedEvent -> {
	            			gamePane.getChildren().add(gameBoard.getBoardPane());
	            			gamePane.getChildren().add(pauseButton);
	            			mediaPlayerMenu.stop();
	        	        	isMainMenu = false;
	        	        	mp1.play();
	            			primaryStage.setScene(gameScene);
	            			gameLoop.start();	
	            		});
	            countdownTimeline.play();
	        	});
	        newGame.setOnMouseEntered(event ->{
	        	MediaPlayer mediaPlayer4 = new MediaPlayer(sound);
	        	mediaPlayer4.play();
	        });
	        
	        
	        Button loadGame = new Button("Load Game");
	        
	        loadGame.setOnAction(event -> {
	        	
	        	//prep the countdown scene
	            Text countdownText = new Text(String.valueOf(COUNTDOWN_FROM));
	            countdownText.setFont(titleFont);
	            countdownText.setFill(Color.GHOSTWHITE);
	            countdownText.setEffect(glowCD);
	            StackPane countdownPane = new StackPane(countdownText);
	            countdownPane.setBackground(background);
	            Scene countdownScene = new Scene(countdownPane, 500, 575);
	            mediaPlayerMenu.stop();
	            primaryStage.setScene(countdownScene);
	            
	            //prep the game scene
	            GameData gameData = new GameData(gameBoard.getPoints_Collection(),gameBoard.getScore());
				gameData.loadGameProgress();
				gameBoard.setScore(gameData.getScore());
				for(SerializableRectangle rect : gameData.getSavedData()) { 
					gameBoard.getPoints_Collection().add(rect.toRectangle());
					System.out.println(rect.getColor());
				}
				for(Rectangle rect : gameBoard.getPoints_Collection()) {
					blocksGroup.getChildren().add(rect);
				}
				
    			mediaPlayerMenu.stop();
	        	isMainMenu = false;
	            MediaPlayer mp2 = new MediaPlayer(sound1);
	        	mp2.play();

	            Timeline countdownTimeline = new Timeline();
	            countdownTimeline.getKeyFrames().addAll(
	                    new KeyFrame(Duration.ZERO, new KeyValue(countdownText.textProperty(), String.valueOf(COUNTDOWN_FROM))),         
	                    new KeyFrame(Duration.seconds(1), new KeyValue(countdownText.textProperty(), String.valueOf(COUNTDOWN_FROM - 1))),
	                    new KeyFrame(Duration.seconds(2), new KeyValue(countdownText.textProperty(), String.valueOf(COUNTDOWN_FROM - 2))),
	                    new KeyFrame(Duration.seconds(3), new KeyValue(countdownText.textProperty(), "")));
	            		countdownTimeline.setOnFinished(finishedEvent -> {
	            			gamePane.getChildren().add(gameBoard.getBoardPane());
	            			gamePane.getChildren().add(pauseButton);
	            			mediaPlayerMenu.stop();
	        	        	isMainMenu = false;
	        	        	mp1.play();
	            			primaryStage.setScene(gameScene);
	            			gameLoop.start();	
	            		});
	            countdownTimeline.play();
	        	});
	        loadGame.setOnMouseEntered(event ->{
	        	MediaPlayer mediaPlayer5 = new MediaPlayer(sound);
	        	mediaPlayer5.play();
	        });
	        
	        
	        Button quit = new Button("Quit");
	        quit.setOnAction(event -> {
	            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
	            alert.setTitle("Confirm Exit");
	            alert.setHeaderText("Are you sure you want to quit?");
	            Optional<ButtonType> result = alert.showAndWait();
	            if (result.isPresent() && result.get() == ButtonType.OK) {
	                System.exit(0);
	            }
	        });
	        quit.setOnMousePressed(event ->{
	        	MediaPlayer mp3 = new MediaPlayer(sound1);
	        	mp3.play();
	        });
	        quit.setOnMouseEntered(event ->{
	        	MediaPlayer mediaPlayer6 = new MediaPlayer(sound);
	        	mediaPlayer6.play();
	        });
	        
	        VBox buttonVB = new VBox(newGame, loadGame, quit);
	        buttonVB.setAlignment(Pos.CENTER);
	        buttonVB.setSpacing(20);
	     
	        mainMenuScene.setOnKeyPressed(event -> {
	            if (event.getCode() == KeyCode.ESCAPE) {
	                mediaPlayerMenu.stop();
	            }
	        });
	        
	       
	    
	        mediaPlayerMenu.setCycleCount(MediaPlayer.INDEFINITE);
			mediaPlayerMenu.play();
			node.setBackground(background);
			node.setCenter(buttonVB);
			primaryStage.setTitle("T E T R I S");
			primaryStage.getIcons().add(icon1);
			primaryStage.setScene(mainMenuScene);
			primaryStage.setResizable(false);
			primaryStage.show();
           
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public  Tetromino spawnTetromino() {
    	
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
    	Color neonCyan = Color.rgb(0, 255, 255);
    	Color neonBlue = Color.rgb(0, 102, 204);
    	Color neonOrange = Color.rgb(255, 140, 0);
    	Color neonYellow = Color.rgb(255, 255, 0);
    	Color neonGreen = Color.rgb(57, 255, 20);
    	Color neonPink = Color.rgb(255, 105, 180);
    	Color neonRed = Color.rgb(255, 62, 62);

        final Tetromino[] POSSIBLE_SHAPES = {
                new TetrominoI(neonCyan, gameBoard),
                new TetrominoJ(neonBlue, gameBoard),
                new TetrominoL(neonOrange, gameBoard),
                new TetrominoO(neonYellow, gameBoard),
                new TetrominoS(neonGreen, gameBoard),
                new TetrominoT(neonPink, gameBoard),
                new TetrominoZ(neonRed, gameBoard)
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


    private void holdBlock() {
    	
    	for (Rectangle block : currentTetromino.getPoints()) {
            blocksGroup.getChildren().remove(block);
        }
    	
        if (holdBlock == null) {
            holdBlock = currentTetromino;    
            currentTetromino = spawnTetromino();
        }
        // Otherwise, swap the hold block with the current falling block
        else {
            Tetromino tempBlock = currentTetromino;
            currentTetromino = holdBlock;
            holdBlock = tempBlock;
            
            currentTetromino.resetPosition();
        }
        
        for (Rectangle block : currentTetromino.getPoints()) {
            blocksGroup.getChildren().add(block);
        }
    	
	}

    public static Board createBoard() {
        Board gameBoard = new Board(NUM_ROW, NUM_COL);
        gameBoard.createBoardPane();
        
        
        return gameBoard;
    }

}