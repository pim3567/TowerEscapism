package logic;

import java.util.ArrayList;
import java.util.Arrays;

import gui.DisplayPane;
import gui.FlowerPuzzlePane;
import items.FlowerPlayingItem;
import items.NormalItem;
//manage flower game
public class FlowerPuzzleController extends BaseGameController {

	private static FlowerPuzzleController instance;
	private static final ArrayList<String> FLOWER_ORDER = new ArrayList<String>(
			Arrays.asList("Daisy", "Rose", "Poppy", "ForgetMeNot", "Hibiscus", "Lily"));
	private static FlowerPuzzlePane flowerPuzzlePane;
	private static final int NUM_OF_FLOWER = 13;

	public FlowerPuzzleController(DisplayPane displayPane) {
		super(displayPane);
		instance = this;
	}
	
	@Override
	public void init() {
		FlowerPuzzlePane flowerPane = new FlowerPuzzlePane();
		((FlowerPuzzleController) instance).setFlowerPuzzlePane(flowerPane);
		instance.getDisplayPane().getChildren().add(flowerPane);
	}


	public static FlowerPuzzleController getInstance() {
		return instance;
	}

	public void changeStatus(String name) {
		String nextOrder = FLOWER_ORDER.get((FLOWER_ORDER.indexOf(name) + 1) % FLOWER_ORDER.size());
		for (int i = 0; i < NUM_OF_FLOWER; i++) {
			if (flowerPuzzlePane.getFlowerList().get(i).getName().equals(nextOrder)) {
				flowerPuzzlePane.getFlowerList().get(i).toggle();
			}
		}
		if (checkWin()) {
			super.setStatus(Status.COMPLETE);
			update();
		}
	}


	@Override
	public boolean checkWin() {
		for (FlowerPlayingItem flower : flowerPuzzlePane.getFlowerList()) {
			if (!flower.isBloom())
				return false;
		}
		//if all 13 flowers bloomed
		return true;
	}

	@Override
	public void update() {
		if (super.getStatus() == Status.COMPLETE) {
			SoundController.getInstance().playCorrectEff();
			super.getDisplayPane().getChildren().remove(flowerPuzzlePane);
			super.getDisplayPane().addItem(new NormalItem("flower", "images/item/flowers.png", 0.25, 0.25, 0.18, 0.2));
			super.setStatus(Status.FINISH);
		}
	}

	public FlowerPuzzlePane getFlowerPuzzlePane() {
		return flowerPuzzlePane;
	}

	public void setFlowerPuzzlePane(FlowerPuzzlePane flowerPuzzlePane) {
		FlowerPuzzleController.flowerPuzzlePane = flowerPuzzlePane;
	}

}