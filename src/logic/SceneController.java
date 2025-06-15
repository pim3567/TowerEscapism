package logic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import config.Config;
import config.MyColor;
import gui.DisplayPane;
import gui.SettingPane;
import items.NormalItem;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
//manage all scenes
public class SceneController {
	private static ArrayList<Parent> sceneRoots;
	private final Stage stage;

	private static SceneController instance;
	private Rectangle blackOverlay = new Rectangle();
	private int currentSceneIndex = 0;
	private boolean isTransitioning = false;
	private static Map<String, Integer> SceneIndexMap = new HashMap<String, Integer>();
	private SettingPane settingPane = new SettingPane();

	private ArrayList<BaseGameController> classController = new ArrayList<BaseGameController>();
	// 0:"home",1:"room1",2:"room2",3:"room3",4:"bookGame",5:"craftTable"
	// 6:"flowerGame",7:"fireplace",8:"infoBoard"
	// 9:"slidingPuzzle",10:"desk",11:"doll"
	// 12:"telescope",13;"blackboard",14:"correctStarGame",15:"safety"
	// 16:"endingStory", 17:"endingScene"
	
	// Constructor: Initializes scene roots, controllers, homepage, items, back buttons, skip buttons, and events.
	public SceneController(Stage stage) {

		this.stage = stage;
		SceneController.sceneRoots = new ArrayList<>();

		setInitialSceneRoots();

		classController.add(new BookPuzzleController(getDisplayPaneByName("bookGame")));
		classController.add(new CookController(getDisplayPaneByName("craftTable")));
		classController.add(new FlowerPuzzleController(getDisplayPaneByName("flowerGame")));
		classController.add(new SlidingPuzzleController(getDisplayPaneByName("slidingPuzzle")));
		classController.add(new WhoPuzzleController(getDisplayPaneByName("desk")));
		classController.add(new TelescopePuzzleController(getDisplayPaneByName("telescope")));
		classController.add(new PlanetPuzzleController(getDisplayPaneByName("correctStarGame")));
		classController.add(new MazePuzzleController(getDisplayPaneByName("safety")));
		setController();

		setHomePage();
		setInitialItem();
		setInitialBackBtn();

		addSkipBtn("bookGame", BookPuzzleController.getInstance());
		addSkipBtn("flowerGame", FlowerPuzzleController.getInstance());
		addSkipBtn("slidingPuzzle", SlidingPuzzleController.getInstance());

		addEventBtn();

	}
	// Initializes scene roots by reading scene layout and buttons from a file.
	public void setInitialSceneRoots() {
		
		int sceneIndex;
		String sceneName, imagePath;
		int numOfNextSceneBtn;
		double width, height, layoutY, layoutX;
		int nextSceneIndex;
		
        try (InputStream is = SceneController.class.getResourceAsStream("/data/sceneRootsData.txt")) {
			if (is == null) {
				throw new IllegalStateException("Resource not found: /data/sceneRootsData.txt");
			}
			try (Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(is)))) {
				while (sc.hasNext()) {
					String[] parsedDetails = sc.nextLine().split(",");


					sceneIndex = Integer.parseInt(parsedDetails[0]);
					sceneName = parsedDetails[1];
					imagePath = parsedDetails[2];
					numOfNextSceneBtn = Integer.parseInt(parsedDetails[3]);

					SceneIndexMap.put(sceneName, sceneIndex);
					DisplayPane displayPane = new DisplayPane(sceneIndex, imagePath);

					for (int i = 0; i < numOfNextSceneBtn; i++) {

						String[] btnDetails = sc.nextLine().split(",");

						width = Double.parseDouble(btnDetails[0]);
						height = Double.parseDouble(btnDetails[1]);
						layoutY = Double.parseDouble(btnDetails[2]);
						layoutX = Double.parseDouble(btnDetails[3]);
						nextSceneIndex = Integer.parseInt(btnDetails[4]);

						displayPane.addNextBtn(createEventBtn(width, height, layoutY, layoutX), nextSceneIndex);
					}
					sceneRoots.add(displayPane);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Sets up the home page with title, background image, and play/exit buttons.
	public void setHomePage() {
		Text nameGame = new Text("Tower Escapism");
		nameGame.setFont(Font.font(Config.MAIN_FONT, FontWeight.BOLD, FontPosture.ITALIC, 80));
		nameGame.setTextAlignment(TextAlignment.CENTER);

		BorderPane borderPane = new BorderPane();
		borderPane.setTop(nameGame);
		borderPane.setPrefWidth(Config.screenWidth * 0.075);
		borderPane.setPrefHeight(Config.screenHeight * 0.2);

		ImageView imageBackground = new ImageView(
				new Image(ClassLoader.getSystemResource("images/background/homePage.jpg").toString()));
		imageBackground.setFitWidth(Config.screenWidth);
		imageBackground.setPreserveRatio(true);

		Button newGameBtn = createBtnWithStyle(0.2, 0.075, 0, 0, "Play");
		newGameBtn.setOnMouseClicked(event -> {
			SceneController.getInstance().setCurrentScene("room1");
			Config.setStartTime();
		});
		newGameBtn.setOnMouseEntered(event -> newGameBtn.setCursor(Cursor.HAND));

		Button exitBtn = createBtnWithStyle(0.2, 0.075, 0, 0, "Exit");
		exitBtn.setOnMouseClicked(event -> {
			exitGame();
		});
		exitBtn.setOnMouseEntered(event -> exitBtn.setCursor(Cursor.HAND));


		VBox vBox = new VBox(5);
		vBox.getChildren().add(borderPane);
		vBox.getChildren().add(newGameBtn);
		vBox.getChildren().add(exitBtn);
		vBox.setSpacing(40);
		vBox.setLayoutX(config.Config.screenWidth * 0.6);
		vBox.setLayoutY(config.Config.screenHeight * 0.2);
		vBox.setAlignment(Pos.CENTER);

		((DisplayPane) sceneRoots.get(0)).getChildren().add(imageBackground);
		((DisplayPane) sceneRoots.get(0)).getChildren().add(vBox);

	}
	// Calls init() on all game controllers to initialize their internal states.
	public void setController() {
		// polymorphism
		for (int i = 0; i < classController.size(); i++) {
			classController.get(i).init();
		}
	}
	// Loads initial items for each scene from a file and adds them to their respective scenes.
	public void setInitialItem() {
		String itemName, imagePath;
		int sceneIndex;
		double width, height, layoutX, layoutY;
		
		try (InputStream is = SceneController.class.getResourceAsStream("/data/itemsData.txt")) {
			if (is == null) {
				throw new IllegalStateException("Resource not found: /data/itemsData.txt");
			}
			try (Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(is)))) {
				while (sc.hasNext()) {
					String[] parsedDetails = sc.nextLine().split(",");

					
					itemName = parsedDetails[0];
					imagePath = parsedDetails[1];
					width = Double.parseDouble(parsedDetails[2]);
					height = Double.parseDouble(parsedDetails[3]);
					layoutX = Double.parseDouble(parsedDetails[4]);
					layoutY = Double.parseDouble(parsedDetails[5]);
					sceneIndex = Integer.parseInt(parsedDetails[6]);
					
					((DisplayPane) sceneRoots.get(sceneIndex))
							.addItem(new NormalItem(itemName, imagePath, width, height, layoutX, layoutY));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	// Adds back buttons to puzzle scenes to return to corresponding room scenes.
	public void setInitialBackBtn() {
		for (int i = 4; i < 16; i++) {
			if (i < 9)
				((DisplayPane) sceneRoots.get(i)).addBackBtn("room1");
			else if (i < 12)
				((DisplayPane) sceneRoots.get(i)).addBackBtn("room2");
			else
				((DisplayPane) sceneRoots.get(i)).addBackBtn("room3");
		}
	}
	// Shows the ending story scene and waits for user click to proceed to final ending screen.
	public void showEndingStory() {
		SceneController.getInstance().setCurrentScene("endingStory");
		DescriptionController.getInstance().getDescriptionPane().setEndingSceneStyle();

		ImageView imageBackground = new ImageView(
				new Image(ClassLoader.getSystemResource("images/background/ending.jpg").toString()));
		imageBackground.setFitWidth(Config.screenWidth);
		imageBackground.setPreserveRatio(true);

		SceneController.getInstance().getDisplayPaneByName("endingStory").getChildren().add(imageBackground);

		DescriptionController.getInstance().getDescriptionPane().setOnMouseClicked(event -> {
			DescriptionController.getInstance().hideDescriptions();
			showEndingScene();
		});
	}
	// Displays the final ending scene with the total playtime and exit button.
	public void showEndingScene() {
		SceneController.getInstance().setCurrentScene("endingScene");

		Date endTime = Calendar.getInstance().getTime();
		Long diff = endTime.getTime() - Config.startTime.getTime();
		int hourDiff = (int) (diff / (60 * 60 * 1000) % 24);
		int minuteDiff = (int) (diff / (60 * 1000) % 60);
		int secondDiff = (int) (diff / 1000 % 60);
		String time = (String.format("%02d", hourDiff) + ":" 
				+ String.format("%02d", minuteDiff) + ":"
				+ String.format("%02d", secondDiff));

		Text timeText = new Text();
		timeText.setText("Thank you for playing.\nYou escaped the tower in " + time + ".");
		timeText.setFont(Font.font(Config.MAIN_FONT, FontWeight.BOLD, 100));
		timeText.setFill(MyColor.BISTRE);
		timeText.setTextAlignment(TextAlignment.CENTER);

		HBox hbox = new HBox(5);
		Button exitBtn = createBtnWithStyle(0.2, 0.075, 0, 0, "Exit");
		exitBtn.setOnMouseClicked(event -> {
			exitGame();
		});
		hbox.getChildren().add(exitBtn);
		hbox.setAlignment(Pos.CENTER);

		BorderPane borderPane = new BorderPane();
		borderPane.setPrefWidth(Config.screenWidth);
		borderPane.setPrefHeight(Config.screenHeight);
		borderPane.setCenter(timeText);
		borderPane.setBottom(hbox);
		BorderPane.setMargin(hbox, new Insets(100));

		SceneController.getInstance().getDisplayPaneByName("endingScene").getChildren().add(borderPane);
		SceneController.getInstance().getDisplayPaneByName("endingScene")
				.setBackground(new Background(new BackgroundFill(Color.WHITE, 
						CornerRadii.EMPTY, Insets.EMPTY)));

	}
	// Adds a skip button to a puzzle scene that marks the puzzle as completed.
	public void addSkipBtn(String sceneName, BaseGameController controller) {
		Button skipBtn = createBtnWithStyle(0.06, 0, 0, 0, "skip");
		skipBtn.setFont(Font.font(Config.SUB_FONT, Config.CAPTION_SIZE));
		skipBtn.setOnMouseClicked(event -> {
			if (controller.getStatus() != Status.FINISH) {
				controller.setStatus(Status.COMPLETE);
				controller.update();
				controller.setStatus(Status.FINISH);
			}
		});
		getDisplayPaneByName(sceneName).getChildren().add(skipBtn);
	}
	// Creates an invisible button used for event interactions in scenes.
	public static Button createEventBtn(double width, double height, double layoutY, double layoutX) {
		Button btn = new Button();
		btn.setPrefSize(Config.imageBackgroundWidth * width, Config.imageBackgroundHeight * height);
		btn.setBackground(new Background(new BackgroundFill(Color.web("00000000"), 
				CornerRadii.EMPTY, Insets.EMPTY)));

		btn.setLayoutX(Config.screenWidth * layoutX);
		btn.setLayoutY(Config.screenHeight * layoutY);

		return btn;
	}
	// Creates a styled button with text, hover effects, and layout parameters.
	public static Button createBtnWithStyle(double width, double height, 
					double layoutY, double layoutX, String text) {
		Button btn = new Button(text);

		btn.setPrefSize(Config.imageBackgroundWidth * width, Config.imageBackgroundHeight * height);

		btn.setLayoutX(Config.screenWidth * layoutX);
		btn.setLayoutY(Config.screenHeight * layoutY);

		btn.setBackground(new Background(new BackgroundFill(Color.BLACK, 
				new CornerRadii(10), Insets.EMPTY)));
		
		
		btn.setOnMouseEntered(event -> {
			btn.setBackground(new Background(new BackgroundFill(MyColor.BROWN, 
					new CornerRadii(10), Insets.EMPTY)));
		});
		btn.setOnMouseExited(event -> {
			btn.setBackground(new Background(new BackgroundFill(Color.BLACK, 
					new CornerRadii(10), Insets.EMPTY)));
		});

		btn.setFont(Font.font(Config.SUB_FONT, FontWeight.BOLD, Config.BODY_SIZE));
		btn.setTextFill(MyColor.CREAM);

		return btn;
	}
	// Adds special interactive buttons (e.g., fireplace, doll, dragon) with item-based logic.
	public void addEventBtn() {
		Button fireplaceBtn = createEventBtn(0.35, 0.5, 0.45, 0.3);
		fireplaceBtn.setOnMouseClicked(event -> {
			if (InventoryController.getInstance().isCurrentItem("flower")) {
				SoundController.getInstance().playCorrectEff();
				InventoryController.getInstance().removeCurrentItem();
				getDisplayPaneByName("fireplace")
						.addItem(new NormalItem("flowerCharred", "images/item/flowersCharred.png", 
								0.2, 0.2, 0.4, 0.4));
				getDisplayPaneByName("fireplace").getChildren().remove(fireplaceBtn);
			}
		});

		fireplaceBtn.setOnMouseEntered(event -> {
			if (InventoryController.getInstance().isCurrentItem("flower")) {
				fireplaceBtn.setCursor(Cursor.HAND);
			} else {
				fireplaceBtn.setCursor(Cursor.DEFAULT);
			}
		});

		getDisplayPaneByName("fireplace").getChildren().add(fireplaceBtn);

		Button dollBtn = createEventBtn(0.3, 0.35, 0.5, 0.15);
		dollBtn.setOnMouseClicked(event -> {
			if (InventoryController.getInstance().isCurrentItem("knife")) {
				SoundController.getInstance().playCorrectEff();
				InventoryController.getInstance().removeCurrentItem();
				getDisplayPaneByName("doll")
						.addItem(new NormalItem("feather", "images/item/feather.png", 0.2, 0.2, 0.21, 0.45));
				getDisplayPaneByName("doll").getChildren().remove(dollBtn);
				getDisplayPaneByName("doll").updateImageBackground("images/background/dollV2.jpg");
			}
		});
		dollBtn.setOnMouseEntered(event -> {
			if (InventoryController.getInstance().isCurrentItem("knife")) {
				dollBtn.setCursor(Cursor.HAND);
			} else {
				dollBtn.setCursor(Cursor.DEFAULT);
			}
		});
		getDisplayPaneByName("doll").getChildren().add(dollBtn);

		Button dragonBtn = createEventBtn(0.13, 0.18, 0.75, 0.31);
		dragonBtn.setOnMouseClicked(event -> {
			DescriptionController.getInstance().toggleDescriptions("A dragon? It’s sleeping.");
		});
		dragonBtn.setOnMouseEntered(event -> {
			dragonBtn.setCursor(Cursor.HAND);
		});
		getDisplayPaneByName("room1").getChildren().add(dragonBtn);
	}
	// Returns the singleton instance of SceneController.
	public static SceneController getInstance() {
		return instance;
	}
	// Returns the singleton instance of SceneController, initializing it if necessary.
	public static SceneController getInstance(Stage stage) {
		if (instance == null)
			instance = new SceneController(stage);
		return instance;
	}
	// Returns the index of the currently active scene.
	public int getCurrentSceneIndex() {
		return currentSceneIndex;
	}
	// Gets the DisplayPane object for a specific scene by name.
	public DisplayPane getDisplayPaneByName(String sceneName) {
		if (!SceneIndexMap.containsKey(sceneName)) {
			System.out.println("Key not found.");
		}
		return (DisplayPane) sceneRoots.get(SceneIndexMap.get(sceneName));
	}
	// Returns the currently active DisplayPane.
	public DisplayPane getCurrentDisplayPane() {
		return (DisplayPane) sceneRoots.get(currentSceneIndex);
	}
	// Shows the settings pane on top of the current scene.
	public void showSettingPane() {
		((DisplayPane) stage.getScene().getRoot()).getChildren().add(settingPane);
	}
	// Removes the settings pane from the current scene.
	public void closeSettingPane() {
		((DisplayPane) stage.getScene().getRoot()).getChildren().remove(settingPane);
	}
	// Sets the initial scene (index 0) as the active scene on stage.
	public void setInitialScene() {
		stage.setScene(new Scene(sceneRoots.get(0)));
	}
	// Switches to a new scene by name.
	public void setCurrentScene(String sceneName) {
		try {
			setCurrentScene(SceneIndexMap.get(sceneName));
		} catch (Exception e) {
			System.out.println("Key not found.");
		}
	}
	// Switches to a new scene by index with a fade transition.
	public void setCurrentScene(int sceneIndex) {
		if (isTransitioning)
			return;

		isTransitioning = true;
		currentSceneIndex = sceneIndex;
		if (blackOverlay.getParent() == null) {
			blackOverlay.setWidth(Screen.getPrimary().getBounds().getWidth());
			blackOverlay.setHeight(Screen.getPrimary().getBounds().getHeight());
			blackOverlay.setFill(Color.BLACK);
			blackOverlay.setOpacity(0);
		}

		Scene currentScene = stage.getScene();
		if (currentScene != null) {
			if (!((Pane) currentScene.getRoot()).getChildren().contains(blackOverlay)) {
				((Pane) currentScene.getRoot()).getChildren().add(blackOverlay);
			}
		} else {
			currentScene.setRoot(sceneRoots.get(sceneIndex));
			isTransitioning = false;
			return;
		}

		transitionScene();
	}
	// Handles the fade-to-black and fade-in transition animation between scenes.
	public void transitionScene() {

		FadeTransition fadeToBlack = new FadeTransition(Duration.millis(500), blackOverlay);
		fadeToBlack.setFromValue(0);
		fadeToBlack.setToValue(1);

		fadeToBlack.setOnFinished(e -> {
			Scene scene = stage.getScene();
			Pane newRoot = (Pane) sceneRoots.get(currentSceneIndex);
			if (currentSceneIndex != 16 && currentSceneIndex != 17)
				newRoot.getChildren().add(InventoryController.getInstance().getInventoryPane());

			if (!newRoot.getChildren().contains(blackOverlay)) {
				newRoot.getChildren().add(blackOverlay);
			}
			DescriptionController.getInstance().showDescriptions();

			blackOverlay.toFront();
			blackOverlay.setOpacity(1);

			FadeTransition fadeOut = new FadeTransition(Duration.millis(500), blackOverlay);
			fadeOut.setFromValue(1);
			fadeOut.setToValue(0);

			fadeOut.setOnFinished(ev -> {
				newRoot.getChildren().remove(blackOverlay);
				isTransitioning = false;

			});

			scene.setRoot(newRoot);
			fadeOut.play();
		});

		fadeToBlack.play();

	}

	public void exitGame() {
		Alert alert = new Alert(AlertType.CONFIRMATION, 
				"This will close you game. Are you sure you want to exit?",
				ButtonType.YES, ButtonType.CANCEL);
		
		alert.setHeaderText(null);
		alert.showAndWait().ifPresent(response -> {
			if (response.equals(ButtonType.YES)) {
				Platform.exit();
			}
		});

	}

	public Stage getStage() {
		return stage;
	}

}
