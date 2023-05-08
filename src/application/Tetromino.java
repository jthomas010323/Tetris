package application;

import java.util.ArrayList;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//Shapes provides a template to each unique Tetromino
//Each Tetromino will have their own rotate and offset values

public abstract class Tetromino {
	private ArrayList<Rectangle> Points;
	private Rectangle a;
	private Rectangle b;
	private Rectangle c;
	private Rectangle d;
	private Color color;
	protected int Rotation_Index;
	
	   Tetromino(Color color) {
	        Points = new ArrayList<Rectangle>();

	        a = new Rectangle(0, 0, Tetris.BLOCK_SIZE, Tetris.BLOCK_SIZE);
	        b = new Rectangle(0, 0, Tetris.BLOCK_SIZE, Tetris.BLOCK_SIZE);
	        c = new Rectangle(0, 0, Tetris.BLOCK_SIZE, Tetris.BLOCK_SIZE);
	        d = new Rectangle(0, 0, Tetris.BLOCK_SIZE, Tetris.BLOCK_SIZE);

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
	
	public abstract void rotate();
	
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
	
	public void setColor() {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public ArrayList<Rectangle> getPoints() {
		return Points;
	}
	
}
