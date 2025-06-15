package logic;

import gui.DisplayPane;
import gui.SlidingPuzzlePane;
import items.NormalItem;
//manage sliding game
public class SlidingPuzzleController extends BaseGameController{

	private int emptyPositionX;
	private int emptyPositionY;
	private SlidingPuzzlePane board;
	private static SlidingPuzzleController instance ;
	private static int[] initialOrder = {4,0,3,9,1,6,2,7,5,12,11,14,8,13,10,15};
	private static String[] imagePaths = { "images/row-1-column-1.jpg", "images/row-1-column-2.jpg", "images/row-1-column-3.jpg", "images/row-1-column-4.jpg",
											"images/row-2-column-1.jpg", "images/row-2-column-2.jpg", "images/row-2-column-3.jpg", "images/row-2-column-4.jpg",
											"images/row-3-column-1.jpg", "images/row-3-column-2.jpg", "images/row-3-column-3.jpg", "images/row-3-column-4.jpg",
											"images/row-4-column-1.jpg", "images/row-4-column-2.jpg", "images/row-4-column-3.jpg", "images/emptySquare.png" };
	
	public SlidingPuzzleController(DisplayPane displayPane) {
		super(displayPane);
		instance = this;
	}
	
	@Override
	public void init() {
		((SlidingPuzzleController)instance).setSlidingPuzzlePane(new SlidingPuzzlePane());
	}
	
	
	
	public static SlidingPuzzleController getInstance() {
		return instance;
	}
	
	public void swap(int x1,int y1,int x2,int y2) {
		int tempCurrentId = board.getAllCells().get(x1*4+y1).getCurrentId();
		board.getAllCells().get(x1*4+y1).setCurrentId(board.getAllCells().get(x2*4+y2).getCurrentId());
		board.getAllCells().get(x2*4+y2).setCurrentId(tempCurrentId);

		
		this.setEmptyPositionX(x1);
		this.setEmptyPositionY(y1);
		
		if(checkWin()) {
			super.setStatus(Status.COMPLETE);
			this.update();
		}
	}
	
	@Override
	public boolean checkWin() {
		for (int i = 0; i < 16; i++) {
			if(board.getAllCells().get(i).getCurrentId()!=i)
				return false;
		}
		return true;
	}
	
	@Override
	public void update() {
		if(super.getStatus() == Status.COMPLETE) {
			SoundController.getInstance().playCorrectEff();
			super.getDisplayPane().getChildren().remove(this.board);
			super.getDisplayPane().addItem(new NormalItem("match", "images/item/match.png", 0.1, 0, 0.45, 0.85));;
			super.setStatus(Status.FINISH);
		}
	}

	
	public int getEmptyPositionX() {
		return emptyPositionX;
	}

	public void setEmptyPositionX(int emptyPositionX) {
		this.emptyPositionX = emptyPositionX;
	}

	public int getEmptyPositionY() {
		return emptyPositionY;
	}

	public void setEmptyPositionY(int emptyPositionY) {
		this.emptyPositionY = emptyPositionY;
	}
	
	public void setSlidingPuzzlePane(SlidingPuzzlePane board) {
		this.board = board;
		super.getDisplayPane().getChildren().add(board);
	}
	
	public static int[] getInitialOrder() {
		return initialOrder;
	}

	public static void setInitialOrder(int[] initialOrder) {
		SlidingPuzzleController.initialOrder = initialOrder;
	}

	public static String[] getImagePaths() {
		return imagePaths;
	}

	public static void setImagePaths(String[] imagePaths) {
		SlidingPuzzleController.imagePaths = imagePaths;
	}
}
