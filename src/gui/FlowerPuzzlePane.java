package gui;

import java.util.ArrayList;
import java.util.Arrays;

import items.FlowerPlayingItem;
import javafx.scene.layout.Pane;
//display pane of flower game
public class FlowerPuzzlePane extends Pane {

	private ArrayList<FlowerPlayingItem> flowerList;
	private static ArrayList<Integer> initialBloomIndex = new ArrayList<Integer>(Arrays.asList(0, 3, 6, 9, 10));
	private static ArrayList<String> flowerName = new ArrayList<String>(Arrays.asList("Daisy", "Daisy", "Daisy", "Rose",
			"Rose", "Poppy", "Poppy", "ForgetMeNot", "ForgetMeNot", "ForgetMeNot", "Hibiscus", "Hibiscus", "Lily"));
	private static ArrayList<Double> flowerLayoutX = new ArrayList<Double>(
			Arrays.asList(0.12, 0.33, 0.09, 0.43, 0.21, 0.15, 0.38, 0.25, 0.41, 0.29, 0.42, 0.18, 0.32));
	private static ArrayList<Double> flowerLayoutY = new ArrayList<Double>(
			Arrays.asList(0.08, 0.13, 0.5, 0.06, 0.43, 0.23, 0.75, 0.23, 0.27, 0.62, 0.44, 0.68, 0.38));
	
	//add 13 flowers to the flower list and to the pane
	public FlowerPuzzlePane() {
		this.flowerList = new ArrayList<>();
		for (int i = 0; i < 13; i++) {
			FlowerPlayingItem flower = new FlowerPlayingItem(flowerName.get(i), false, flowerLayoutX.get(i),
					flowerLayoutY.get(i));
			if (initialBloomIndex.contains(i))
				flower.setBloom(true);
			this.getChildren().add(flower);
			this.getFlowerList().add(flower);
		}
	}

	public ArrayList<FlowerPlayingItem> getFlowerList() {
		return flowerList;
	}
}
