package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class GameData implements Serializable {

	private static final long serialVersionUID = 5225288019494844988L;
	
	private int score;
	private ArrayList<SerializableRectangle> Saved_Points;
	private SerializableRectangle newRect;
    
	public GameData(ArrayList<Rectangle> Points_Collection) {
    	
    	Saved_Points = new ArrayList<SerializableRectangle>();
    	
    	for(Rectangle rect : Points_Collection) {
        	newRect = new SerializableRectangle(rect);
        	System.out.println("Rectangle at: " + (int) rect.getX()/30 + "," + (int) rect.getY()/30 + " added.");

    		Saved_Points.add(newRect);
    	}
    	 
    }
    
    public void saveGameProgress() {
        try {
            FileOutputStream fileOut = new FileOutputStream("save/gameData.ser");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            
 
            objectOut.writeObject(Saved_Points);
            
            objectOut.close();
            fileOut.close();
            System.out.println("Object has been serialized");
        } catch (IOException e) {
        	System.out.println("IOException is caught");
            e.printStackTrace();
        }
    }

    public void loadGameProgress() {    	
        try {
            FileInputStream fileIn = new FileInputStream("save/gameData.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            
            
            Saved_Points = (ArrayList<SerializableRectangle>) objectIn.readObject();            
                    
            objectIn.close();
            fileIn.close();
            System.out.println("Object has been deserialized");
            
            for(SerializableRectangle rect : Saved_Points) {
            	System.out.println("Rectangle at: " + (int) rect.getX()/30 + "," + (int) rect.getY()/30 + " loaded.");
            }
        } catch (IOException e) {
        	System.out.println("IOException is caught");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
        	System.out.println("IOException is caught");
            e.printStackTrace();
        }
    }

	public ArrayList<SerializableRectangle> getSavedData() {
		
		return Saved_Points;
	}

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
}