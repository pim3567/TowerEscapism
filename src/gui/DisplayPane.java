package gui;

import config.Config;
import items.BaseItem;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import logic.BaseGameController;
import logic.BookPuzzleController;
import logic.InventoryController;
import logic.SceneController;
import logic.WhoPuzzleController;
//display pane
public class DisplayPane extends Pane {

	private int descriptionStep;

	private ImageView imageBackground;

	public DisplayPane(int index, String imagePath) {

		this.descriptionStep = 0;
		this.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		if(ClassLoader.getSystemResource(imagePath) != null) {
			this.imageBackground = new ImageView(new Image(ClassLoader.getSystemResource(imagePath).toString()));
			imageBackground.setFitWidth(Config.imageBackgroundWidth);
			imageBackground.setFitHeight(Config.imageBackgroundHeight);
			imageBackground.setLayoutX(0);
			imageBackground.setLayoutY(Config.gap);
			
			InnerShadow innerShadow = new InnerShadow();
			innerShadow.setRadius(100);
			innerShadow.setColor(Color.BLACK);
			innerShadow.setChoke(0.5);
			imageBackground.setEffect(innerShadow);

			this.getChildren().add(imageBackground);
		} else {
			this.imageBackground = new ImageView();
		}
			

		
	}

	public void updateImageBackground(String imagePath) {
		this.imageBackground.setImage(new Image(ClassLoader.getSystemResource(imagePath).toString()));
	}

	public void updateDescriptionStep() {
		this.descriptionStep += 1;
	}

	public int getDescriptionStep() {
		return descriptionStep;
	}

	public void addItem(BaseItem item) {
		this.getChildren().add(item);
	}

	public void removeItem(BaseItem item) {
		this.getChildren().remove(item);
	}

	
	public void addEventBtn(Button btn, BaseGameController instance) {
		// polymorphism
		btn.setOnMouseClicked(e -> {
			instance.update();
		});
		if (instance instanceof WhoPuzzleController) {
			btn.setOnMouseEntered(event -> {
				Slot currentSlot = InventoryController.getInstance().getCurrentSlot();
				if (currentSlot != null && currentSlot.getItem() != null
						&& (currentSlot.getItem().getName().equals("candle")
								|| currentSlot.getItem().getName().equals("match"))) {
					btn.setCursor(Cursor.HAND);
				} else {
					btn.setCursor(Cursor.DEFAULT);
				}
			});
			btn.setOnMouseExited(e -> btn.setCursor(Cursor.DEFAULT));
		} else if (instance instanceof BookPuzzleController) {
			btn.setOnMouseEntered(event -> {
				Slot currentSlot = InventoryController.getInstance().getCurrentSlot();
				if (currentSlot != null && currentSlot.getItem() != null
						&& (currentSlot.getItem().getName().equals("whisk"))) {
					btn.setCursor(Cursor.HAND);
				} else {
					btn.setCursor(Cursor.DEFAULT);
				}
			});
			btn.setOnMouseExited(e -> btn.setCursor(Cursor.DEFAULT));

		} else {
			btn.setOnMouseEntered(e -> btn.setCursor(Cursor.HAND));
			btn.setOnMouseExited(e -> btn.setCursor(Cursor.DEFAULT));
		}
		;
		this.getChildren().add(btn);
	}

	public void addNextBtn(Button btn, int nextSceneIndex) {
		btn.setOnMouseClicked(event -> SceneController.getInstance().setCurrentScene(nextSceneIndex));
		btn.setOnMouseEntered(event -> btn.setCursor(Cursor.HAND));
		btn.setOnMouseExited(event -> btn.setCursor(Cursor.DEFAULT));
		this.getChildren().add(btn);

	}
	
	public void addBackBtn(String nextSceneName) {
		Button btn = new Button();
		btn.setPrefSize(Config.imageBackgroundWidth * 0.1, Config.imageBackgroundWidth * 0.1);
		btn.setBackground(new Background(new BackgroundImage(
				new Image(ClassLoader.getSystemResource("images/uiComponent/backButton.png").toString()),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				BackgroundSize.DEFAULT)));
		btn.setOnAction(e -> SceneController.getInstance().setCurrentScene(nextSceneName));
		btn.setOnMouseEntered(e -> btn.setCursor(Cursor.HAND));
		btn.setOnMouseExited(e -> btn.setCursor(Cursor.DEFAULT));
		btn.setLayoutX(0);
		btn.setLayoutY(Config.screenHeight - Config.gap - Config.imageBackgroundWidth * 0.1);

		this.getChildren().add(btn);
	}
}
