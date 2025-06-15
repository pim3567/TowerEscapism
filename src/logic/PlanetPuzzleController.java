package logic;

import java.util.ArrayList;
import java.util.Arrays;

import gui.DisplayPane;
import gui.PlanetPuzzlePane;
import items.NormalItem;
//manage planet game
public class PlanetPuzzleController extends BaseGameController{
	
	private static ArrayList<String> planetNameList = new ArrayList<String>(Arrays.asList("Nyvora", "Thalorix", "Zerenthia", "Elystra", "Morvex", "Kaelion"));
	private static ArrayList<Integer> planetIndexAnswerList = new ArrayList<Integer>(Arrays.asList(1,3,5,2));
	private PlanetPuzzlePane planetPuzzlePane;
	private static PlanetPuzzleController instance;
	
	public PlanetPuzzleController(DisplayPane displayPane) {
		super(displayPane);
		instance = this;
	}
	
	@Override
	public void init() {
		PlanetPuzzlePane planetPane = new PlanetPuzzlePane();
		instance.setPlanetPuzzlePane(planetPane);
		instance.getDisplayPane().getChildren().add(planetPane);

	}
	
	public static PlanetPuzzleController getInstance() {
		return instance;
	}
	
	public static ArrayList<String> getPlanetName() {
		return planetNameList;
	}
	
	@Override
	public boolean checkWin() {
		for(int i=0;i<planetIndexAnswerList.size();i++) {
			if(planetPuzzlePane.getPlanetBtnList().get(i).getPlannetIndex() != planetIndexAnswerList.get(i)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public void update() {
		if(super.getStatus() == Status.COMPLETE) {
			SoundController.getInstance().playCorrectEff();
			this.getDisplayPane().getChildren().remove(planetPuzzlePane);
			this.getDisplayPane().updateImageBackground("images/background/planetPuzzleV2.jpg");
			SceneController.getInstance().getDisplayPaneByName("room3")
					.addItem(new NormalItem("mazePiece1", "images/item/mazePiece1.png", 0.2, 0, 0.5, 0.7));
			SceneController.getInstance().setCurrentScene("room3");
			super.setStatus(Status.FINISH);
		}
	}

	public void setPlanetPuzzlePane(PlanetPuzzlePane planetPuzzlePane) {
		this.planetPuzzlePane = planetPuzzlePane;
	}
	
	
}
