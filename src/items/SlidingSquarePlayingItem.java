package items;

import javafx.scene.Cursor;
import logic.SlidingPuzzleController;
//sliding square item which can be played
public class SlidingSquarePlayingItem extends BaseItem implements Playable {

	private int positionX;
	private int positionY;
	private int currentId;

	public SlidingSquarePlayingItem(String name, String imagePath, int positionX, int positionY, int id) {

		super(name, "");
		this.setPositionX(positionX);
		this.setPositionY(positionY);
		this.setCurrentId(id);
		super.getImageView().setFitWidth(config.Config.imageBackgroundWidth * 0.16);
		super.getImageView().setPreserveRatio(true);
		//change cursor when mouse hovered
		this.setOnMouseEntered(e -> this.setCursor(Cursor.HAND));
	    this.setOnMouseExited(e -> this.setCursor(Cursor.DEFAULT));
	}

	@Override
	public void play() { //When clicked the grid that are beside the empty one, they switch positions.
		int dif = Math.abs(positionX - ((SlidingPuzzleController) SlidingPuzzleController.getInstance()).getEmptyPositionX())
				+ Math.abs(positionY - ((SlidingPuzzleController) SlidingPuzzleController.getInstance()).getEmptyPositionY());
		if (dif == 1)
			((SlidingPuzzleController) SlidingPuzzleController.getInstance()).swap(positionX, positionY,
					((SlidingPuzzleController) SlidingPuzzleController.getInstance()).getEmptyPositionX(),
					((SlidingPuzzleController) SlidingPuzzleController.getInstance()).getEmptyPositionY());
	}

	public int getCurrentId() {
		return currentId;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public void setCurrentId(int id) {
		this.currentId = id;
		if (id == 15)
			super.setImageView("images/slidingPuzzle/emptySquare.png");
		else
			super.setImageView("images/slidingPuzzle/row-" + Integer.toString((int) id / 4 + 1) + "-column-"
					+ Integer.toString(id % 4 + 1) + ".jpg");

	}
}
