package main;

import config.Config;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import logic.SceneController;
import logic.SoundController;

public class Main extends Application {

	@Override
	public void start(Stage stage) {
		
		Config.setWindowSizeConfig(Screen.getPrimary().getBounds().getWidth(),
				Screen.getPrimary().getBounds().getHeight());

		SceneController.getInstance(stage).setInitialScene();

		stage.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
			SoundController.getInstance().playClickEff();
		});

		// Auto Full-Screen When user clicks the restore up
		stage.maximizedProperty().addListener((obs, wasMaximized, isNowMaximized) -> {
			if (isNowMaximized) {
				stage.setFullScreen(true);
				stage.setFullScreenExitHint("");
			}
		});

		stage.setTitle("Tower escapism");
		stage.setFullScreen(true);
		stage.setFullScreenExitHint("");
		stage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}
