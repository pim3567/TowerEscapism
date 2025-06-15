package gui;

import java.util.ArrayList;
import java.util.Arrays;

import config.Config;
import items.PersonPlayingItem;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import logic.SceneController;
import logic.WhoPuzzleController;
//display pane of finding villains game
public class WhoPuzzlePane extends VBox{
	private GridPane gridPane;
	private ArrayList<PersonPlayingItem> personList;
	private static final ArrayList<String> SENTENCE_LIST = new ArrayList<String>(Arrays.asList(
													"The one on my left tells the truth.", "The betrayers consist of two mages.",
													"There is a betrayer in the warriors.", "The betrayers are not next to each other in any direction.",
													"One of the betrayers wears a hat.", "Every priest tells the truth.", "All of the betrayers are mages.",
													"There is no betrayal next to me.", "The one on my left or right tells lies.",
													"The betrayers have both a truthful and a deceitful person.", "There is only one betrayer next to me.",
													"There is a betrayer near me."));
	private static final ArrayList<String> IMAGE_PATHS = new ArrayList<String>(Arrays.asList("images/whoPuzzle/warrior.png",
													"images/whoPuzzle/priest.png", "images/whoPuzzle/mage.png", "images/whoPuzzle/warrior.png", "images/whoPuzzle/warrior.png", "images/whoPuzzle/mage.png",
													"images/whoPuzzle/priest.png", "images/whoPuzzle/priest.png", "images/whoPuzzle/mage.png", "images/whoPuzzle/priest.png", "images/whoPuzzle/warrior.png",
													"images/whoPuzzle/mageHat.png"));
	
	
	public WhoPuzzlePane() {
		
		this.setPrefWidth(Config.imageBackgroundWidth);
		this.setPrefHeight(Config.imageBackgroundHeight);
		this.setBackground(new Background(new BackgroundImage(new Image(ClassLoader.getSystemResource("images/whoPuzzle/blankPaper.png").toString()),BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,new BackgroundSize(Config.imageBackgroundWidth,Config.imageBackgroundHeight, false, false, true, false))));
		this.setAlignment(Pos.TOP_CENTER);
		
		
		gridPane = new GridPane(1,1);
		personList = new ArrayList<>();
		
		//Create a 6*2 grid and add each person to the grid
		for(int i=0;i<2;i++) {
			for(int j=0;j<6;j++) {
				PersonPlayingItem person = new PersonPlayingItem("person", IMAGE_PATHS.get(i*6+j), SENTENCE_LIST.get(i*6+j));
				this.personList.add(person);
				gridPane.add(person, j, i);
			}
		}
		gridPane.setAlignment(Pos.CENTER);
		
		addText();
		addGameDetail();
		this.getChildren().add(gridPane);
		addCheckBtn();
		
		
	}
	
//	public void addBlankPaper() {
//		
//		this.setBackground(new Background(new BackgroundImage(image,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,BackgroundSize.DEFAULT)));
////		ImageView blankPaper = new ImageView(
////				new Image(ClassLoader.getSystemResource("images/whoPuzzle/blankPaper.png").toString()));
////		blankPaper.setFitWidth(Config.imageBackgroundWidth);
////		blankPaper.setPreserveRatio(true);
////		this.getChildren().add(blankPaper);
//	}
	
	public void addText() {
		
		Text text = new Text();
		text.setFont(Font.font(Config.MAIN_FONT, Config.HEADING_SIZE));
		text.setTextAlignment(TextAlignment.CENTER);
		
		this.getChildren().add(text);
		this.setMargin(text, new Insets(Config.gap+Config.imageBackgroundHeight*0.13,0,0,0));
		((WhoPuzzleController)WhoPuzzleController.getInstance()).setText(text);
	}
	
	public void addCheckBtn() {
		Button checkBtn = SceneController.createBtnWithStyle(0.2, 0, 0, 0, "check");
		checkBtn.setFont(Font.font(Config.MAIN_FONT,Config.CAPTION_SIZE));
		checkBtn.setOnMouseClicked(event -> {
			WhoPuzzleController.getInstance().update();
		});
		checkBtn.setOnMouseEntered(e -> checkBtn.setCursor(Cursor.HAND));
		checkBtn.setOnMouseExited(e -> checkBtn.setCursor(Cursor.DEFAULT));
		this.getChildren().add(checkBtn);
		
	}

	public void addGameDetail() {
		BorderPane gameDetail = new BorderPane();
		gameDetail.setPrefWidth(Config.imageBackgroundWidth);
		Text text = new Text();
		text.setText("There are 3 bad guys here. Warriors speak only truths, mages weave naught but lies,"
				+ "\nand priests—ah, priests may utter either, for their words dance between light and shadow.");
		text.setFont(Font.font(Config.MAIN_FONT, Config.CAPTION_SIZE));
		text.setFill(Color.BLACK);
		text.setTextAlignment(TextAlignment.CENTER);
		gameDetail.setCenter(text);

		this.getChildren().add(gameDetail);
	}
	

	public ArrayList<PersonPlayingItem> getPersonList() {
		return personList;
	}

	public void setPersonList(ArrayList<PersonPlayingItem> personList) {
		this.personList = personList;
	}
	
	
}

