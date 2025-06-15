package gui;

import config.Config;
import config.MyColor;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.PlanetPuzzleController;
import logic.Status;
//display pane of button in planet game
public class PlanetPuzzleButton extends VBox {

	private static final int NUM_OF_PLANET = 6;
	private int planetIndex;
	private Text text;
	private Button upBtn, downBtn;
	//up button, text, down button
	//When clicked up or down button, the text changed accordingly
	public PlanetPuzzleButton() {
		this.setPrefWidth(Config.imageBackgroundWidth * 0.15);
		this.setAlignment(Pos.CENTER);
		this.setSpacing(10);

		this.text = new Text();
		this.text.setFont(Font.font(Config.MAIN_FONT, FontWeight.BOLD, Config.BODY_SIZE));
		this.text.setEffect(new DropShadow(10, Color.WHITE));

		this.setPlannetIndex(0);

		upBtn = creatButtonWithStyle("↑");
		upBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				nextUp();
			}
		});
		upBtn.setOnMouseEntered(e -> upBtn.setCursor(Cursor.HAND));
	    upBtn.setOnMouseExited(e -> upBtn.setCursor(Cursor.DEFAULT));

		downBtn = creatButtonWithStyle("↓");
		downBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				nextDown();
			}
		});
		downBtn.setOnMouseEntered(e -> downBtn.setCursor(Cursor.HAND));
	    downBtn.setOnMouseExited(e -> downBtn.setCursor(Cursor.DEFAULT));

		this.getChildren().add(upBtn);
		this.getChildren().add(text);
		this.getChildren().add(downBtn);
	}

	public Button creatButtonWithStyle(String text) {
		Button button = new Button(text);
		button.setBackground(new Background(new BackgroundFill(MyColor.BISTRE, new CornerRadii(4), new Insets(5))));
		button.setFont(Font.font(Config.SUB_FONT, FontWeight.BOLD, Config.CAPTION_SIZE));
		button.setTextFill(MyColor.CREAM);
		return button;
	}

	public void nextUp() {
		this.setPlannetIndex((planetIndex + 1) % NUM_OF_PLANET);
		updateStatus();
	}

	public void nextDown() {
		this.setPlannetIndex(((planetIndex - 1) + NUM_OF_PLANET) % NUM_OF_PLANET);
		updateStatus();
	}

	public void updateStatus() {
		if (PlanetPuzzleController.getInstance().checkWin()) {
			PlanetPuzzleController.getInstance().setStatus(Status.COMPLETE);
			PlanetPuzzleController.getInstance().update();
		}
	}

	public Text getText() {
		return text;
	}

	public void setText(String text) {
		this.text.setText(text);
	}

	public int getPlannetIndex() {
		return planetIndex;
	}

	public void setPlannetIndex(int planetIndex) {
		this.planetIndex = planetIndex;
		this.setText(PlanetPuzzleController.getPlanetName().get(planetIndex));
	}

}
