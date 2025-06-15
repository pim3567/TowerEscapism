package logic;

import gui.DisplayPane;
import gui.TelescopePuzzlePane;
import items.NormalItem;
//manage telescope game
public class TelescopePuzzleController extends BaseGameController {
	private TelescopePuzzlePane telescopePane;
	private static final double GOAL_X = -0.38 * config.Config.imageBackgroundWidth;
	private static final double GOAL_Y = 0.37 * config.Config.imageBackgroundHeight;
	private static TelescopePuzzleController instance;

	public TelescopePuzzleController(DisplayPane displayPane) {
		super(displayPane);
		instance = this;
	}

	@Override
	public void init() {
		TelescopePuzzlePane telescopePane = new TelescopePuzzlePane();
		((TelescopePuzzleController) instance).setTelescopePane(telescopePane);
		instance.getDisplayPane().getChildren().add(telescopePane);
	}

	public static TelescopePuzzleController getInstance() {
		return instance;
	}

	@Override
	public boolean checkWin() {
		return Math.abs(telescopePane.getX() - GOAL_X) < 20 && Math.abs(telescopePane.getY() - GOAL_Y) < 20;
	}

	@Override
	public void update() {
		if (super.getStatus() == Status.STEP1) {
			SoundController.getInstance().playCorrectEff();
			SceneController.getInstance().getDisplayPaneByName("room3")
					.addItem(new NormalItem("mazePiece2", "images/item/mazePiece2.png", 0.1, 0, 0.55, 0.6));
			SceneController.getInstance().setCurrentScene("room3");
			super.setStatus(Status.COMPLETE);
		}

	}

	public TelescopePuzzlePane getTelescopePane() {
		return telescopePane;
	}

	public void setTelescopePane(TelescopePuzzlePane telescopePane) {
		this.telescopePane = telescopePane;
	}

}
