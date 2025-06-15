package gui;

import config.Config;
import config.MyColor;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import logic.DescriptionController;
//display pane when story occur
public class DescriptionPane extends BorderPane {

	private Text text;
	//set semi-transparent black background with text as description 
	//that can be triggered when clicked
	public DescriptionPane() {
		this.setPrefWidth(Config.screenWidth);
		this.setPrefHeight(Config.screenHeight);
		this.setBackground(new Background(new BackgroundFill(Color.web("#00000088"), CornerRadii.EMPTY, Insets.EMPTY)));


		text = new Text();
		text.setFont(Font.font(Config.MAIN_FONT, FontWeight.BOLD, Config.SUBHEADING_SIZE));
		text.setFill(MyColor.CREAM);
		text.setTextAlignment(TextAlignment.CENTER);

		this.setBottom(text);
		BorderPane.setAlignment(text, Pos.BOTTOM_CENTER);
		BorderPane.setMargin(text, new Insets(10));
		this.setOnMouseClicked(event -> {
			DescriptionController.getInstance().showDescriptions();
		});
	}

	public void setText(String message) {
		this.text.setText(message);
	}

	//set transparent background and change text's color
	public void setEndingSceneStyle() {
		this.setBackground(new Background(new BackgroundFill(Color.web("#00000000"), CornerRadii.EMPTY, Insets.EMPTY)));
		text.setFill(MyColor.BISTRE);
	}
}
