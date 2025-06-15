package logic;

import java.util.ArrayList;
import java.util.Arrays;

import gui.DisplayPane;
import gui.WhoPuzzlePane;
import items.NormalItem;
import items.PersonPlayingItem;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
//manage find the villains game
public class WhoPuzzleController extends BaseGameController {

	private WhoPuzzlePane whoPuzzlePane;
	private Text text;
	private static int countSelected = 0;
	private static ArrayList<Integer> answersIndex = new ArrayList<Integer>(Arrays.asList(2, 9, 11));
	private static WhoPuzzleController instance;

	@Override
	public void init() {
		((WhoPuzzleController) instance).setWhoPuzzlePane(new WhoPuzzlePane());
		instance.getDisplayPane().addEventBtn(SceneController.createEventBtn(0.4, 0.7, 0.1, 0.6), WhoPuzzleController.getInstance());
	}

	public WhoPuzzleController(DisplayPane displayPane) {
		super(displayPane);
		instance = this;
		
		
	}

	public static WhoPuzzleController getInstance() {
		return instance;
	}

	public void select(PersonPlayingItem person) {
		if (countSelected == 3 & !person.isSelect())
			updateText("Can't select more than 3");
		else {
			if (person.isSelect()) {
				person.setSelect(false);
				countSelected -= 1;
			} else {
				person.setSelect(true);
				countSelected += 1;
			}
		}
	}

	@Override
	public boolean checkWin() {
		for (int index : answersIndex) {
			if (!this.getWhoPuzzlePane().getPersonList().get(index).isSelect()) {
				updateText("Wrong answer");
				return false;
			}
		}
		return true;
	}

	@Override
	public void update() {
		switch (super.getStatus()) {
		case INITIAL:
			if (InventoryController.getInstance().isCurrentItem("candle")) {
				SoundController.getInstance().playCorrectEff();
				super.setStatus(Status.STEP1);
				super.getDisplayPane().updateImageBackground("images/background/whoGameAddCandle.jpg");
				InventoryController.getInstance().removeCurrentItem();
			}
			break;
		case STEP1:
			if (InventoryController.getInstance().isCurrentItem("match")) {
				SoundController.getInstance().playCorrectEff();
				super.setStatus(Status.STEP2);
				super.getDisplayPane().updateImageBackground("images/background/whoGameWithLight.jpg");
				super.getDisplayPane().getChildren().remove(2);
				
				super.getDisplayPane().getChildren().remove(1);
				super.getDisplayPane().getChildren().add(whoPuzzlePane);
				super.getDisplayPane().addBackBtn("room2");

				SceneController.getInstance().getDisplayPaneByName("room2")
						.updateImageBackground("images/background/room2V2.jpg");
				InventoryController.getInstance().removeCurrentItem();
				
				super.getDisplayPane().updateDescriptionStep();
				DescriptionController.getInstance().showDescriptions();
			}
			break;
		case STEP2:
			if(checkWin()) {
				SoundController.getInstance().playCorrectEff();
				super.setStatus(Status.COMPLETE);
				super.getDisplayPane().getChildren().remove(whoPuzzlePane);
				super.getDisplayPane().updateImageBackground("images/background/whoGameKnife.jpg");
				Button knifeBtn = SceneController.createEventBtn(0.1, 0.4,0.4, 0.13);
				knifeBtn.setOnMouseEntered(event -> knifeBtn.setCursor(Cursor.HAND));
				super.getDisplayPane().addEventBtn(knifeBtn, WhoPuzzleController.getInstance());
			}
			break;
			
		case COMPLETE:
			super.setStatus(Status.FINISH);
			super.getDisplayPane().updateImageBackground("images/background/whoGameKnifeTaken.jpg");
			super.getDisplayPane().getChildren().removeLast();
			NormalItem knife = new NormalItem("knife", "images/item/knife.png", 0.3, 0, 0.35, 0.3);
			knife.pick();
			SceneController.getInstance().getDisplayPaneByName("room2").getChildren().remove(2);
			break;
		default:
			
		}

	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public void updateText(String sentence) {
		this.text.setText(sentence);
	}

	public WhoPuzzlePane getWhoPuzzlePane() {
		return whoPuzzlePane;
	}

	public void setWhoPuzzlePane(WhoPuzzlePane whoPuzzlePane) {
		this.whoPuzzlePane = whoPuzzlePane;
	}

}
