package logic;

import gui.DisplayPane;
//Base class for all controllers
public abstract class BaseGameController {
	private Status status;
	private DisplayPane displayPane;

	public BaseGameController(DisplayPane displayPane) {
		this.displayPane = displayPane;
		this.setStatus(Status.INITIAL);
	}
	//abstract method to init controller
	public abstract void init();
	//abstract method to check whether controller is won
	public abstract boolean checkWin();
	//abstract method to update controller behavior
	public abstract void update();

	public static BaseGameController getInstance() {
		System.out.println("call getInstance() from BaseGameController");
		return null;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public DisplayPane getDisplayPane() {
		return displayPane;
	}

	public void setDisplayPane(DisplayPane displayPane) {
		this.displayPane = displayPane;
	}

}
