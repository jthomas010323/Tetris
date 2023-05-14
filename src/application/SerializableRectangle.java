package application;

import java.io.Serializable;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class SerializableRectangle implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double x;
    private double y;
    private double width;
    private double height;
    private String col;
    
    public SerializableRectangle(Rectangle rect) {
        this.x = rect.getX();
        this.y = rect.getY();
        this.width = rect.getWidth();
        this.height = rect.getHeight();
         
        //turn the color from getFill to a string
        this.col = "" + rect.getFill();
    }
    
    public Rectangle toRectangle() {
    	Rectangle newRect = new Rectangle(x, y, width, height);
    	Color c = Color.web(col);
    	newRect.setFill(c);
    	
        return newRect;
    }
    
    public String getColor() {
    	return col;
    }
    
    public double getX() {
    	return x;
    }
    
    public double getY() {
    	return y;
    }
    public double getWidth() {
    	return width;
    }
    public double getHeight() {
    	return height;
    }
}