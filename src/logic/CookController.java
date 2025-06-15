package logic;

import gui.DisplayPane;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
//manage cooking
public class CookController extends BaseGameController {
	private static CookController instance;
	private final String CRAFTTABLEV2 = "images/background/craftTableV2.jpg";
	private final String CRAFTTABLEV3 = "images/background/craftTableV3.jpg";
	private final String CRAFTTABLEV4 = "images/background/craftTableV4.jpg";

	@Override
	public void init() {

		Button water = SceneController.createEventBtn(0.2, 0.4, 0.47, 0.32);
		water.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				instance.update();
				instance.getDisplayPane().getChildren().remove(water);
			}
		});
		water.setOnMouseEntered(event -> water.setCursor(Cursor.HAND));
		instance.getDisplayPane().getChildren().add(water);

		Button pot = SceneController.createEventBtn(0.4, 0.4, 0.13, 0.55);
		pot.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				if (instance.getStatus() != Status.INITIAL)
					instance.update();
			}

		});
		pot.setOnMouseEntered(event -> {
			if(instance.getStatus() != Status.INITIAL)
				pot.setCursor(Cursor.HAND);
		});
		
		instance.getDisplayPane().getChildren().add(pot);
	}

	public CookController(DisplayPane displayPane) {
		super(displayPane);
		instance = this;
	}

	public static CookController getInstance() {
		return instance;
	}

	@Override
	public boolean checkWin() {
		return false;
	}

	@Override
	public void update() {
		switch (super.getStatus()) {
		case INITIAL:
			SoundController.getInstance().playCorrectEff();
			super.setStatus(Status.STEP1);
			this.getDisplayPane().updateImageBackground(CRAFTTABLEV2);
			break;
		case STEP1:
			if (InventoryController.getInstance().isCurrentItem("flowerCharred")) {
				SoundController.getInstance().playCorrectEff();
				super.setStatus(Status.STEP2);
				this.getDisplayPane().updateImageBackground(CRAFTTABLEV3);
				InventoryController.getInstance().removeCurrentItem();
			}
			break;
		case STEP2:
			if (InventoryController.getInstance().isCurrentItem("feather")) {
				SoundController.getInstance().playCorrectEff();
				super.setStatus(Status.STEP3);
				this.getDisplayPane().updateImageBackground(CRAFTTABLEV4);
				InventoryController.getInstance().removeCurrentItem();
			}
			break;
		case STEP3:
			if (InventoryController.getInstance().isCurrentItem("goldBar")) {
				SoundController.getInstance().playCorrectEff();
				super.setStatus(Status.COMPLETE);
				SceneController.getInstance().showEndingStory();
				InventoryController.getInstance().removeCurrentItem();
			}
			break;
		default:
			break;
		}

	}

}
