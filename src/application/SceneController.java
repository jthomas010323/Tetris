package application;

import javafx.scene.Scene;

public class SceneController {
	private Scene scene;
	
	public SceneController(Scene s){
		setScene(s);
	}

	public Scene getScene() {
		return scene;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}
}
