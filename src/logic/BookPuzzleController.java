package logic;

import config.Config;
import gui.BookPuzzlePane;
import gui.DisplayPane;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
//manage book game
public class BookPuzzleController extends BaseGameController {

	private static BookPuzzleController instance;
	private final static Image spiderWebImage = new Image(
			ClassLoader.getSystemResource("images/bookPuzzle/spiderWeb.png").toString());
	private BookPuzzlePane bookPane;

	public BookPuzzleController(DisplayPane displayPane) {
		super(displayPane);
		instance = this;
	}

	@Override
	public void init() {
		((BookPuzzleController) instance).setBookPane(new BookPuzzlePane());
		ImageView imageView = new ImageView(spiderWebImage);
		imageView.setFitWidth(Config.imageBackgroundWidth);
		imageView.setPreserveRatio(true);
		imageView.setLayoutY((Config.screenHeight - Config.imageBackgroundHeight) / 2);
		instance.getDisplayPane().getChildren().add(imageView);
		instance.getDisplayPane().addEventBtn(SceneController.createEventBtn(1, 1, 0, 0),
				BookPuzzleController.getInstance());
	}

	public static BaseGameController getInstance() {
		return instance;
	}

	public void swap(int index1, int index2) {
		int tempBookId = this.getBookPane().getBookList().get(index1).getBookId();
		this.getBookPane().getBookList().get(index1)
				.setBookId(this.getBookPane().getBookList().get(index2).getBookId());
		this.getBookPane().getBookList().get(index2).setBookId(tempBookId);

		if (checkWin()) {
			super.setStatus(Status.COMPLETE);
			update();
		}
	}

	@Override
	public boolean checkWin() {
		for (int i = 0; i < 13; i++) {
			if (this.getBookPane().getBookList().get(i).getBookId() != i)
				return false;
		}
		return true;
	}

	@Override
	public void update() {
		switch (super.getStatus()) {

		case INITIAL:
			if (InventoryController.getInstance().isCurrentItem("whisk")) {
				SoundController.getInstance().playCorrectEff();
				super.setStatus(Status.STEP1);
				super.getDisplayPane().getChildren().remove(1);
				super.getDisplayPane().getChildren().add(this.getBookPane());
				InventoryController.getInstance().removeCurrentItem();
				super.getDisplayPane().updateDescriptionStep();
				DescriptionController.getInstance().showDescriptions();
			}
			break;
		case COMPLETE:
			SoundController.getInstance().playCorrectEff();
			SceneController.getInstance().getDisplayPaneByName("room1").getChildren().remove(6);

			Button btn = SceneController.createEventBtn(0.1, 0.5, 0.35, 0);
			btn.setOnMouseClicked(event -> {
					SceneController.getInstance().setCurrentScene("room2");
			});
			btn.setOnMouseEntered(event -> {
				btn.setCursor(Cursor.HAND);
			});
			SceneController.getInstance().getDisplayPaneByName("room1")
					.updateImageBackground("images/background/room1V2.jpg");
			SceneController.getInstance().getDisplayPaneByName("room1").getChildren().add(btn);

			SceneController.getInstance().setCurrentScene("room1");
			super.setStatus(Status.FINISH);
			break;
		default:
		}
	}

	public BookPuzzlePane getBookPane() {
		return bookPane;
	}

	public void setBookPane(BookPuzzlePane bookPane) {
		this.bookPane = bookPane;
	}

}
