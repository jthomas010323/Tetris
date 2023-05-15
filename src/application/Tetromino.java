package application;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

//Tetromino provides a template to each unique shape
//Each shape will have their own unique rotation and color

public abstract class Tetromino {
	private ArrayList<Rectangle> Points;
	private Rectangle a;
	private Rectangle b;
	private Rectangle c;
	private Rectangle d;
	private Color color;
	Board gameBoard;
	protected int Rotation_Index;
		
		Tetromino(Color color, Board gameBoard) {
	        Points = new ArrayList<Rectangle>();
	        this.gameBoard=gameBoard;
	        a = new Rectangle(0, 0, Tetris.BLOCK_SIZE, Tetris.BLOCK_SIZE);
	        b = new Rectangle(0, 0, Tetris.BLOCK_SIZE, Tetris.BLOCK_SIZE);
	        c = new Rectangle(0, 0, Tetris.BLOCK_SIZE, Tetris.BLOCK_SIZE);
	        d = new Rectangle(0, 0, Tetris.BLOCK_SIZE, Tetris.BLOCK_SIZE);
	        
	        a.setStroke(Color.BLACK);
	        b.setStroke(Color.BLACK);
	        c.setStroke(Color.BLACK);
	        d.setStroke(Color.BLACK);
	        
	        a.setStrokeType(StrokeType.INSIDE);
	        b.setStrokeType(StrokeType.INSIDE);
	        c.setStrokeType(StrokeType.INSIDE);
	        d.setStrokeType(StrokeType.INSIDE);
	      
	        a.setStrokeWidth(2);
	        b.setStrokeWidth(2);
	        c.setStrokeWidth(2);
	        d.setStrokeWidth(2);
	        
	        Points.add(a);
	        Points.add(b);
	        Points.add(c);
	        Points.add(d);
	        
	        Rotation_Index = 0;
	        this.color = color;
      
	       // Set the color of each rectangle
	       for (Rectangle rect : Points) {
	       rect.setFill(color);
	        }
	 
	    }
		
		Tetromino(Tetromino otherTetromino) {
		    this.Points = new ArrayList<>();
		    for (Rectangle rect : otherTetromino.getPoints()) {
		        Rectangle newRect = new Rectangle(rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight());
		        newRect.setFill(otherTetromino.getColor());
		        this.Points.add(newRect);
		    }
		    this.color = otherTetromino.getColor();
		    this.Rotation_Index = otherTetromino.getRotationIndex();
		}

	
	private int getRotationIndex() {
			return Rotation_Index;
		}

	public abstract void rotate(Board gameBoard);
	
	public Rectangle getRectA() {
		return a;
	}
	
	public Rectangle getRectB() {
		return b;
	}	
	
	public Rectangle getRectC() {
		return c;
	}	
	
	public Rectangle getRectD() {
		return d;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public ArrayList<Rectangle> getPoints() {
		return Points;
	}

	protected abstract void resetPosition();
	
}
