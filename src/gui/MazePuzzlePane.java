package gui;

import java.util.ArrayList;
import java.util.HashMap;

import config.Config;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.DescriptionController;
import logic.InventoryController;
import logic.MazePuzzleController;
import logic.Status;
//display pane of maze game
public class MazePuzzlePane extends HBox {

	private static final int ROWS = 6;
	private static final int COLS = 4;
	private static final int ANSWER_ROWS = 5;
	private static final int ANSWER_COLS = 4;
	private static final double CELL_SIZE_WIDTH = Config.imageBackgroundWidth * 0.1;
	private static final double CELL_SIZE_HEIGHT = Config.imageBackgroundHeight * 0.1;
	private ArrayList<StackPane> mazeCellList = new ArrayList<StackPane>();
	private ArrayList<StackPane> answerCellList = new ArrayList<StackPane>();
	private ArrayList<Integer> pathAnswer = new ArrayList<Integer>();
	private int currentIndex = 0;
	private String[] textBtn = { "→", "↶", "↷" };
	private static final HashMap<String, Integer> nextpathByName = new HashMap<String, Integer>();
	private static GridPane grid;
	private static GridPane btnBox;
	private static GridPane answerPane;


	public MazePuzzlePane() {
		nextpathByName.put("→", 0);
		nextpathByName.put("↶", 1);
		nextpathByName.put("↷", -1);

		this.setPrefWidth(Config.imageBackgroundWidth * 0.73);
		this.setPrefHeight(Config.imageBackgroundHeight * 0.65); 

		setInitial();
	}
	// add elements in the game
	private void setInitial() {
		this.setBackground(new Background(new BackgroundFill(Color.web("#fcefd9"), CornerRadii.EMPTY, Insets.EMPTY)));
		this.setLayoutX(Config.imageBackgroundWidth * 0.15);
		this.setLayoutY(Config.imageBackgroundHeight * 0.21);

		DropShadow effect = new DropShadow(20, 0, 0, Color.BLACK);
		effect.setInput(new InnerShadow(20, 0, 0, Color.BLACK));
		this.setEffect(effect);
		
		grid = new GridPane();
		btnBox = new GridPane(4, 4);
		answerPane = new GridPane(8, 8);

		grid.setGridLinesVisible(true);
		grid.setPrefSize(CELL_SIZE_WIDTH * COLS, CELL_SIZE_HEIGHT * ROWS);
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				StackPane cell = new StackPane();
				cell.setPrefSize(CELL_SIZE_WIDTH, CELL_SIZE_HEIGHT);
				grid.add(cell, col, row);
				mazeCellList.add(cell);
			}
		}

		addWall(1, 0, "bottom"); // top right bottom left
		addWall(2, 0, "top");
		addWall(1, 1, "bottom");
		addWall(2, 1, "top");

		addWall(4, 2, "right");
		addWall(4, 3, "left");
		addWall(5, 2, "right");
		addWall(5, 3, "left");

		addCircle(1, 2);
		addCircle(3, 0);
		addCircle(3, 3);
		addCircle(4, 1);

		addBlackBox(0, 1, "End", "mazePiece1");
		addBlackBox(5, 3, "Start", "mazePiece2");

		
		for (int row = 0; row < ANSWER_ROWS; row++) {
			for (int col = 0; col < ANSWER_COLS; col++) {
				StackPane cell = new StackPane();
				cell.setPrefSize(70, 60);
				cell.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, CornerRadii.EMPTY,
						BorderWidths.DEFAULT)));
				Text text = new Text();
				text.setFont(Font.font("Arial", 24));
				cell.getChildren().add(text);
				answerPane.add(cell, col, row);
				answerCellList.add(cell);
			}
		}

		addTextBtn();
		addClearBtn();
		addDelBtn();
		addEnterBtn();

		VBox box = new VBox(10);
		box.getChildren().add(answerPane);
		box.getChildren().add(btnBox);

		this.setSpacing(10);
		this.setPadding(new Insets(50));
		this.setAlignment(Pos.TOP_CENTER);
		this.getChildren().add(grid);
		this.getChildren().add(box);

	}

	private void addTextBtn() {
		for (int i = 0; i < textBtn.length; i++) {
			Button btn = new Button(textBtn[i]);
			btn.setFont(Font.font("Arial", 24));
			btn.setStyle("-fx-background-color: #1a1400; -fx-text-fill: #fff9b1;");
			btn.setPrefWidth(100);
			String txt = textBtn[i];

			btn.setOnMouseClicked(event -> {
				if (currentIndex < ANSWER_COLS * ANSWER_ROWS) {
					this.setTextAnswerCell(txt, currentIndex);
					pathAnswer.add(nextpathByName.get(txt));
					currentIndex += 1;
				}

			});
			btn.setOnMouseEntered(e -> btn.setCursor(Cursor.HAND));
		    btn.setOnMouseExited(e -> btn.setCursor(Cursor.DEFAULT));
			btnBox.add(btn, i, 0);

		}
	}

	private void addClearBtn() {
		Button clearBtn = new Button("Clear");
		clearBtn.setFont(Font.font("Arial", 24));
		clearBtn.setStyle("-fx-background-color: #1a1400; -fx-text-fill: #fff9b1");
		clearBtn.setPrefWidth(100);
		clearBtn.setOnMouseClicked(event -> {
			for (int row = 0; row < ANSWER_ROWS; row++) {
				for (int col = 0; col < ANSWER_COLS; col++) {
					this.setTextAnswerCell("", row * ANSWER_COLS + col);
				}
			}
			currentIndex = 0;
			pathAnswer.clear();
		});
		clearBtn.setOnMouseEntered(e -> clearBtn.setCursor(javafx.scene.Cursor.HAND));
	    clearBtn.setOnMouseExited(e -> clearBtn.setCursor(javafx.scene.Cursor.DEFAULT));
		btnBox.add(clearBtn, 0, 1);
	}

	private void addDelBtn() {
		Button delBtn = new Button("del");
		delBtn.setFont(Font.font("Arial", 24));
		delBtn.setStyle("-fx-background-color: #1a1400; -fx-text-fill: #fff9b1");
		delBtn.setPrefWidth(100);
		delBtn.setOnMouseClicked(event -> {
			if (currentIndex > 0) {
				currentIndex -= 1;
				this.setTextAnswerCell("", currentIndex);
				pathAnswer.removeLast();
			}
		});
		delBtn.setOnMouseEntered(e -> delBtn.setCursor(javafx.scene.Cursor.HAND));
	    delBtn.setOnMouseExited(e -> delBtn.setCursor(javafx.scene.Cursor.DEFAULT));
		btnBox.add(delBtn, 1, 1);

	}

	private void addEnterBtn() {
		Button enterBtn = new Button("Enter");
		enterBtn.setFont(Font.font("Arial", 24));
		enterBtn.setStyle("-fx-background-color: #1a1400; -fx-text-fill: #fff9b1");
		enterBtn.setPrefWidth(100);
		enterBtn.setOnMouseClicked(event -> {
			((MazePuzzleController) MazePuzzleController.getInstance()).displayResult();
		});
		enterBtn.setOnMouseEntered(e -> enterBtn.setCursor(javafx.scene.Cursor.HAND));
	    enterBtn.setOnMouseExited(e -> enterBtn.setCursor(javafx.scene.Cursor.DEFAULT));
		btnBox.add(enterBtn, 2, 1);
	}

	private void addWall(int row, int col, String side) {
		if (side.equals("top"))
			this.getMazeCell(row, col).setStyle("-fx-border-color: red; -fx-border-width: 5 0 0 0 ; ");
		else if (side.equals("right"))
			this.getMazeCell(row, col).setStyle("-fx-border-color: red; -fx-border-width: 0 5 0 0 ; ");
		else if (side.equals("bottom"))
			this.getMazeCell(row, col).setStyle("-fx-border-color: red; -fx-border-width: 0 0 5 0 ; ");
		else if (side.equals("left"))
			this.getMazeCell(row, col).setStyle("-fx-border-color: red; -fx-border-width: 0 0 0 5 ; ");
	}

	private void addCircle(int row, int col) {
		Circle circle = new Circle(20, Color.RED);
		this.getMazeCell(row, col).getChildren().add(circle);
	}

	private void addBlackBox(int row, int col, String label, String itemLabel) {
		StackPane cell = this.getMazeCell(row, col);
		cell.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
		cell.setOnMouseClicked(event -> {
			if (InventoryController.getInstance().isCurrentItem(itemLabel)) {
//				addLabel(row, col, label);
				ImageView imageView = new ImageView(new Image(ClassLoader.getSystemResource("images/item/"+itemLabel+".png").toString()));
				imageView.setFitHeight(CELL_SIZE_HEIGHT*0.8);
				imageView.setPreserveRatio(true);
				cell.getChildren().add(imageView);
				cell.setBackground(
						new Background(new BackgroundFill(Color.web("#00000000"), CornerRadii.EMPTY, Insets.EMPTY)));
				InventoryController.getInstance().removeCurrentItem();
				if (MazePuzzleController.getInstance().getStatus() == Status.INITIAL) {
					MazePuzzleController.getInstance().setStatus(Status.STEP1);
				} else if (MazePuzzleController.getInstance().getStatus() == Status.STEP1) {
					MazePuzzleController.getInstance().setStatus(Status.STEP2);
				}
			}
			else {
				if (MazePuzzleController.getInstance().getStatus() == Status.INITIAL
						|| MazePuzzleController.getInstance().getStatus() == Status.STEP1)
					DescriptionController.getInstance().toggleDescriptions("Something is wrong.");
			}
//    		cell.setBackground(new Background(new BackgroundFill(Color.web("#00000000"),CornerRadii.EMPTY,Insets.EMPTY)));
//        	addLabel(row, col, label);
		});
		cell.setOnMouseEntered(event -> {
			if (InventoryController.getInstance().isCurrentItem(itemLabel)) {
				cell.setCursor(Cursor.HAND);
			}
		});
	}


	private StackPane getMazeCell(int row, int col) {
		return mazeCellList.get(row * COLS + col);
	}

	private StackPane getAnswerCell(int row, int col) {
		return answerCellList.get(row * COLS + col);
	}

	private void setTextAnswerCell(String text, int index) {
		StackPane cell = this.getAnswerCell(index / ANSWER_COLS, index % ANSWER_COLS);
		((Text) cell.getChildren().getLast()).setText(text);
		if (text.equals(""))
			cell.setBorder(new Border(
					new BorderStroke(Color.BLACK, BorderStrokeStyle.DASHED, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		else
			cell.setBorder(new Border(
					new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

	}

	public void setColor(int row, int col, String color) {
		if (color.equals("green"))
			this.getMazeCell(row, col).setBackground(
					new Background(new BackgroundFill(Color.web("#a5c478"), CornerRadii.EMPTY, Insets.EMPTY)));
		else if (color.equals("red"))
			this.getMazeCell(row, col).setBackground(
					new Background(new BackgroundFill(Color.web("#d65638"), CornerRadii.EMPTY, Insets.EMPTY)));
		else
			this.getMazeCell(row, col).setBackground(
					new Background(new BackgroundFill(Color.web("#d6563800"), CornerRadii.EMPTY, Insets.EMPTY)));
	}

	public void clearMazePane() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				setColor(row, col, "clear");
			}
		}
	}

	public ArrayList<Integer> getPathAnswer() {
		return pathAnswer;
	}

	public static int getRows() {
		return ROWS;
	}

	public static int getCols() {
		return COLS;
	}

}
