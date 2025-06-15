package gui;

import java.util.ArrayList;

import config.Config;
import javafx.scene.layout.Pane;
//display pane of planet game
public class PlanetPuzzlePane extends Pane {

	private static final double[][] positionBtn = { { 0.225, 0.82 }, { 0.295, 0.28 }, { 0.596, 0.52 },
			{ 0.675, 0.77 } };
	private ArrayList<PlanetPuzzleButton> planetBtnList;
	
	//add 4 buttons to the pane
	public PlanetPuzzlePane() {
		this.setPrefWidth(Config.imageBackgroundWidth);
		this.setPrefHeight(Config.screenHeight);
		planetBtnList = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			PlanetPuzzleButton btn = new PlanetPuzzleButton();
			btn.setLayoutX(positionBtn[i][0] * Config.imageBackgroundWidth);
			btn.setLayoutY(positionBtn[i][1] * Config.screenHeight);
			planetBtnList.add(btn);
			this.getChildren().add(btn);
		}
	}

	public ArrayList<PlanetPuzzleButton> getPlanetBtnList() {
		return planetBtnList;
	}

}
