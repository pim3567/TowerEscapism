package logic;

import java.util.ArrayList;
import java.util.Arrays;

import gui.DisplayPane;
import gui.MazePuzzlePane;
import items.NormalItem;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
//manage maze game
public class MazePuzzleController extends BaseGameController {
	private static MazePuzzleController instance;
	private MazePuzzlePane mazePane;
	private Button openBtn, closeBtn;
	private static NormalItem goldItem; // = new NormalItem("goldBar", "images/item/goldbar.png", 0.35, 0, 0.35, 0.4);
	private static final int[][] direction = new int[][] { { -1, 0 }, { 0, -1 }, { 1, 0 }, { 0, 1 } };
	private static final int[][] WRONG_DIRECTION = new int[][] {{5,3,1}, {4,3,1}, {2,0,0}, {2,1,0}, {5,2,3}, {4,2,3}, {1,0,2}, {1,1,2}};
	private static ArrayList<ArrayList<Integer>> pointPosition = new ArrayList<ArrayList<Integer>>(
			Arrays.asList(new ArrayList<Integer>(Arrays.asList(1, 2)), new ArrayList<Integer>(Arrays.asList(3, 0)),
					new ArrayList<Integer>(Arrays.asList(3, 3)), new ArrayList<Integer>(Arrays.asList(4, 1))));
	private int currentRow = 5, currentCol = 3;
	private boolean[] memCheckPoint = new boolean[] { false, false, false, false };
	

	public MazePuzzleController(DisplayPane displayPane) {
		super(displayPane);
		goldItem = new NormalItem("goldBar", "images/item/goldBar.png", 0.35, 0, 0.35, 0.4);
		instance = this;
	}
	
	public void init() {
		((MazePuzzleController) instance).setMazePane(new MazePuzzlePane());
		((MazePuzzleController) instance).setBtn(SceneController.createEventBtn(0.75, 0.72, 0.18, 0.12),
				SceneController.createEventBtn(0.15, 0.7, 0.18, 0));
	}

	public static MazePuzzleController getInstance() {
		return instance;
	}

	public void setBtn(Button openBtn, Button closeBtn) {
		this.closeBtn = closeBtn;
		this.closeBtn.setOnMouseClicked(event -> {
			closeSafe();
		});
		this.closeBtn.setOnMouseEntered(e -> this.closeBtn.setCursor(Cursor.HAND));
	    this.closeBtn.setOnMouseExited(e -> this.closeBtn.setCursor(Cursor.DEFAULT));
		this.openBtn = openBtn;
		this.openBtn.setOnMouseClicked(event -> {
			openSafe();
		});
		this.openBtn.setOnMouseEntered(e -> this.openBtn.setCursor(Cursor.HAND));
	    this.openBtn.setOnMouseExited(e -> this.openBtn.setCursor(Cursor.DEFAULT));

		super.getDisplayPane().getChildren().add(openBtn);
	}

	@Override
	public boolean checkWin() {
		if (currentRow != 0 || currentCol != 1)
			return false;
		for (int i = 0; i < 4; i++) {
			if (!memCheckPoint[i]) {
				return false;
			}
		}
		//if reached the winning position and passed all 4 checkpoints
		return true;
	}

	@Override
	public void update() {
		if (super.getStatus() == Status.COMPLETE) {
			SoundController.getInstance().playCorrectEff();
			super.getDisplayPane().updateImageBackground("images/background/safeV4.jpg");
			super.getDisplayPane().getChildren().remove(mazePane);
			super.getDisplayPane().addItem(goldItem);
		}
	}

	public void openSafe() {
		super.getDisplayPane().getChildren().add(closeBtn);
		super.getDisplayPane().getChildren().remove(openBtn);
		if (super.getStatus() == Status.COMPLETE) {
			super.getDisplayPane().updateImageBackground("images/background/safeV4.jpg");
			if (!goldItem.isPick())
				super.getDisplayPane().addItem(goldItem);
		} else {
			super.getDisplayPane().getChildren().add(mazePane);
			super.getDisplayPane().updateImageBackground("images/background/safeV2.jpg");
		}

	}

	public void closeSafe() {
		if (super.getStatus() == Status.COMPLETE) {
			if (!goldItem.isPick())
				super.getDisplayPane().getChildren().remove(goldItem);
		}
		else {
			super.getDisplayPane().getChildren().remove(mazePane);
		}
		super.getDisplayPane().getChildren().add(openBtn);
		super.getDisplayPane().getChildren().remove(closeBtn);
		super.getDisplayPane().updateImageBackground("images/background/safeV1.jpg");

	}

	public void setMazePane(MazePuzzlePane mazePane) {
		this.mazePane = mazePane;
	}

	public void displayResult() {
		if (super.getStatus() != Status.STEP2) {
			DescriptionController.getInstance().toggleDescriptions("Something is wrong.");
			return;
		}
		currentRow = 6;
		currentCol = 3;
		memCheckPoint = new boolean[] { false, false, false, false };
		Thread displayMazeThread = new Thread(() -> {
			ArrayList<Integer> path = mazePane.getPathAnswer();
			int currentDirection = 0;
			for (int i = 0; i < path.size(); i++) {
				try {
					if (path.get(i) == 0) {
						if (checkNextPosition(currentRow, currentCol, currentDirection)) {
							currentRow += direction[currentDirection][0];
							currentCol += direction[currentDirection][1];
							mazePane.setColor(currentRow, currentCol, "green");
							ArrayList<Integer> currentPos = new ArrayList<Integer>(
									Arrays.asList(currentRow, currentCol));
							if (pointPosition.contains(currentPos)) {
								memCheckPoint[pointPosition.indexOf(currentPos)] = true;
							}
						} else {
							mazePane.setColor(currentRow, currentCol, "red");
							break;
						}
					} else {
						currentDirection = (currentDirection + path.get(i) + 4) % 4;
					}
					Thread.sleep(150);
				} catch (InterruptedException e) {
					System.out.println(e.toString());
				}
			}
			if (checkWin()) {
				super.setStatus(Status.COMPLETE);
				Platform.runLater(() -> {
					this.update();
				});
			} else {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mazePane.clearMazePane();
			}
		});
		displayMazeThread.start();

	}

	public boolean checkNextPosition(int row, int col, int direct) {
		if (!(row + direction[direct][0] >= 0 && col + direction[direct][1] >= 0 && row + direction[direct][0] < MazePuzzlePane.getRows()
				&& col + direction[direct][1] < MazePuzzlePane.getCols()))
			return false;
		for(int i=0;i<WRONG_DIRECTION.length;i++) {
			if(row==WRONG_DIRECTION[i][0] && col == WRONG_DIRECTION[i][1] && direct == WRONG_DIRECTION[i][2])
				return false;
		}
		return true;
	} 

}
