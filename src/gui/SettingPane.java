package gui;

import config.Config;
import config.MyColor;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.SceneController;
import logic.SoundController;
// display pane of setting menu
public class SettingPane extends StackPane{
	
	public SettingPane() {
		this.setBackground(new Background(new BackgroundFill(Color.web("00000090"),CornerRadii.EMPTY,Insets.EMPTY)));
		this.setPrefHeight(Config.screenHeight);
		this.setPrefWidth(Config.screenWidth);
		
		VBox settingBox = new VBox(15);
		settingBox.setPrefHeight(500);
		settingBox.setMaxHeight(500);
		settingBox.setPrefWidth(500);
		settingBox.setMaxWidth(500);
		settingBox.setBackground(new Background(new BackgroundFill(MyColor.CREAM,new CornerRadii(15),Insets.EMPTY)));
		settingBox.setBorder(new Border(new BorderStroke(MyColor.BISTRE,BorderStrokeStyle.SOLID,new CornerRadii(10),new BorderWidths(8))));
		settingBox.setAlignment(Pos.TOP_CENTER);
		settingBox.setPadding(new Insets(20));
		
		
		ImageView closeBtn = new ImageView(new Image(ClassLoader.getSystemResource("images/uiComponent/closeIcon.png").toString()));
		closeBtn.setFitWidth(30);
		closeBtn.setPreserveRatio(true);
		closeBtn.setOnMouseClicked(event -> SceneController.getInstance().closeSettingPane());
		closeBtn.setOnMouseEntered(e -> closeBtn.setCursor(javafx.scene.Cursor.HAND));
        closeBtn.setOnMouseExited(e -> closeBtn.setCursor(javafx.scene.Cursor.DEFAULT));
		BorderPane borderPane = new BorderPane();
		borderPane.setRight(closeBtn);
		
		
		Text header = new Text("SETTING");
		header.setFont(Font.font(Config.MAIN_FONT,FontWeight.BOLD, Config.HEADING_SIZE));
		header.setFill(MyColor.BISTRE);
		
		//when dragged the slider, the sound changed accordingly
		Slider bgmSlider = new Slider(0, 1, 0.5);
        Slider effSlider = new Slider(0, 1, 0.5);
        
        Button exitBtn = new Button("EXIT");
        exitBtn.setPrefWidth(100);
        exitBtn.setBackground(new Background(new BackgroundFill(MyColor.BISTRE,new CornerRadii(15),Insets.EMPTY)));
        exitBtn.setFont(Font.font(Config.MAIN_FONT,FontWeight.BOLD, Config.BODY_SIZE));
        exitBtn.setTextFill(MyColor.CREAM);
        exitBtn.setOnMouseClicked(event -> SceneController.getInstance().exitGame());
        exitBtn.setOnMouseEntered(e -> exitBtn.setCursor(Cursor.HAND));
        exitBtn.setOnMouseExited(e -> exitBtn.setCursor(Cursor.DEFAULT));
		
		settingBox.getChildren().addAll(borderPane,header,  createHbox("MUSIC",bgmSlider), createHbox("SOUND",effSlider),exitBtn);
		StackPane.setAlignment(settingBox, Pos.CENTER);
		
		this.getChildren().add(settingBox);
		
		SoundController.getInstance().initial();
        SoundController.getInstance().setSlider(bgmSlider, effSlider);

        
	}
	
	public HBox createHbox(String txt, Slider slider) {
		HBox sliderBox = new HBox(10);
		sliderBox.setAlignment(Pos.CENTER);
		Text text = new Text(txt);
		text.setFont(Font.font(Config.MAIN_FONT,FontWeight.BOLD, Config.SUBHEADING_SIZE));
		text.setFill(MyColor.BISTRE);
		slider.setPrefWidth(250);
		sliderBox.getChildren().addAll(text,slider);
		
		return sliderBox;
	}
	

}
