package gui;

import java.util.ArrayList;

import items.SlidingSquarePlayingItem;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import logic.SlidingPuzzleController;
//display pane of sliding game
public class SlidingPuzzlePane extends GridPane {
	private ArrayList<SlidingSquarePlayingItem> allCells = new ArrayList<>();
	//contain 4*4 grids
	public SlidingPuzzlePane() {
		this.setHgap(1);
		this.setVgap(1);
		this.setPadding(new Insets(0));
		this.setLayoutX(config.Config.imageBackgroundWidth*0.1785);
		this.setLayoutY(config.Config.imageBackgroundHeight*0.152);
		this.setBackground(new Background(new BackgroundFill(Color.BLACK,CornerRadii.EMPTY,Insets.EMPTY)));
		this.setBorder(new Border(new BorderStroke(Color.BLACK,BorderStrokeStyle.SOLID,CornerRadii.EMPTY,new BorderWidths(1))));
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				SlidingSquarePlayingItem newSquare = new SlidingSquarePlayingItem("SlidingSquare","images/sliidingPuzzle/row-"+ Integer.toString(i) +"-column-"+Integer.toString(j)+".jpg",i, j, SlidingPuzzleController.getInitialOrder()[i * 4 + j]);
				this.allCells.add(newSquare);
				this.add(newSquare, j, i);
				
				if(SlidingPuzzleController.getInitialOrder()[i * 4 + j]==15) {
					((SlidingPuzzleController)SlidingPuzzleController.getInstance()).setEmptyPositionX(i);
					((SlidingPuzzleController)SlidingPuzzleController.getInstance()).setEmptyPositionY(j);
				}
					
				
			}
		}
	}
	
	public ArrayList<SlidingSquarePlayingItem> getAllCells() {
		return this.allCells;
	}
}
