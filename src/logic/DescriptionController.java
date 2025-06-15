package logic;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import config.Config;
import gui.DescriptionPane;
//manage description
public class DescriptionController {

	private static DescriptionController instance;
	private ArrayList<ArrayList<String>> descriptionList;
	private ArrayList<ArrayList<Integer>> descriptionNumber;
	private int[] currentIndex = new int[Config.NUM_OF_SCENE];

	private DescriptionPane descriptionPane;
	private boolean isShowing = false;

	public DescriptionController() {
		descriptionPane = new DescriptionPane();
		descriptionList = new ArrayList<ArrayList<String>>();
		descriptionNumber = new ArrayList<ArrayList<Integer>>();
		loadDescriptions();
	}

	public void reset() {
		currentIndex = new int[Config.NUM_OF_SCENE];
	}
	
	public static DescriptionController getInstance() {
		if (instance == null)
			instance = new DescriptionController();
		return instance;
	}

	public void loadDescriptions() {
		

		try (InputStream is = SceneController.class.getResourceAsStream("/data/descriptions.txt")) {
			if (is == null) {
				throw new IllegalStateException("Resource not found: /data/descriptions.txt");
			}
			try (Scanner sc = new Scanner(new BufferedReader(new InputStreamReader(is)))) {
				while (sc.hasNext()) {
					String[] messageNumber = sc.nextLine().split(",");
					ArrayList<Integer> number = new ArrayList<Integer>();
					for (String i : messageNumber) {
						number.add(Integer.parseInt(i));
					}
					descriptionNumber.add(number);

					String[] messages = sc.nextLine().split("#");
					ArrayList<String> strList = new ArrayList<String>();
					for (String m : messages) {
						strList.add(m);
					}
					descriptionList.add(strList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void showDescriptions() {
		int sceneNumber = SceneController.getInstance().getCurrentSceneIndex();
		int step = SceneController.getInstance().getCurrentDisplayPane().getDescriptionStep();
		if (currentIndex[sceneNumber] > descriptionNumber.get(sceneNumber).get(step))
			return;
		int endIndex = descriptionNumber.get(sceneNumber).get(step);
		if (currentIndex[sceneNumber] < endIndex) {
			if (!isShowing) {
				SceneController.getInstance().getCurrentDisplayPane().getChildren().add(descriptionPane);
				isShowing = true;
			}
			descriptionPane.setText(descriptionList.get(sceneNumber).get(currentIndex[sceneNumber]));
			currentIndex[sceneNumber]++;
		} else if (currentIndex[sceneNumber] == endIndex) {
			hideDescriptions();
		}
	}

	public void toggleDescriptions(String sentence) {
		if (!isShowing) {
			SceneController.getInstance().getCurrentDisplayPane().getChildren().add(descriptionPane);
			descriptionPane.setText(sentence);
			isShowing = true;
		} else {
			hideDescriptions();
		}

	}

	public void hideDescriptions() {
		if (isShowing) {
			descriptionPane.setText("");
			SceneController.getInstance().getCurrentDisplayPane().getChildren().remove(descriptionPane);
			isShowing = false;
		}
	}

	public boolean isShowing() {
		return isShowing;
	}

	public DescriptionPane getDescriptionPane() {
		return descriptionPane;
	}

}
