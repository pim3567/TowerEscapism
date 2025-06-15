package gui;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import logic.SceneController;
//display pane of inventory tab
public class InventoryPane extends VBox {

	private ArrayList<Slot> slotsList;
	
	// contain 6 slots and setting button
	public InventoryPane() {
		slotsList = new ArrayList<>();
		this.setPadding(new Insets(10));
		this.setSpacing(10);
		this.setAlignment(Pos.CENTER);

		Button settingBtn = new Button();
		settingBtn.setPrefWidth(60);
		settingBtn.setPrefHeight(60);
		settingBtn.setBackground(new Background(new BackgroundImage(
				new Image(ClassLoader.getSystemResource("images/uiComponent/settingIcon.png").toString()),
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
				new BackgroundSize(60, 60, false, false, false, false))));
		settingBtn.setOnMouseClicked(event -> SceneController.getInstance().showSettingPane());
		settingBtn.setOnMouseEntered(e -> settingBtn.setCursor(javafx.scene.Cursor.HAND));
	    settingBtn.setOnMouseExited(e -> settingBtn.setCursor(javafx.scene.Cursor.DEFAULT));
		
		this.getChildren().add(settingBtn);

		for (int i = 0; i < 6; i++) {
			Slot newSlot = new Slot();
			slotsList.add(newSlot);
			this.getChildren().add(newSlot);
		}
		this.setPrefSize(config.Config.inventoryWidth, config.Config.inventoryHeight);
		this.setLayoutX(config.Config.imageBackgroundWidth);
		this.setLayoutY((config.Config.screenHeight - config.Config.imageBackgroundHeight) / 2);

	}

	public ArrayList<Slot> getSlotsList() {
		return slotsList;
	}

}
