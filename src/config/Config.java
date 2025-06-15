package config;

import java.util.Calendar;
import java.util.Date;
//config of the game
public class Config {
	public static Date startTime;
	public static double screenWidth;
	public static double screenHeight;
	public static double imageBackgroundWidth;
	public static double imageBackgroundHeight;
	public static double gap;
	public static double inventoryHeight;
	public static double inventoryWidth = 100;

	public static final String MAIN_FONT = "Palatino Linotype";
	public static final String SUB_FONT = "DejaVu Serif";
	public static final double HEADING_SIZE = 35;
	public static final double SUBHEADING_SIZE = 24;
	public static final double BODY_SIZE = 20;
	public static final double CAPTION_SIZE = 16;
	
	public static final int NUM_OF_SCENE = 18;

	public static void setWindowSizeConfig(double width, double height) {
		screenWidth = width;
		screenHeight = height;
		imageBackgroundWidth = width - 100;
		imageBackgroundHeight = imageBackgroundWidth / 16 * 9;
		inventoryHeight = imageBackgroundHeight;
		gap = (screenHeight - imageBackgroundHeight) / 2;
	}
	
	public static void setStartTime() {
		startTime = Calendar.getInstance().getTime();
	}

}
