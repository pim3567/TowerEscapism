package gui;

import config.MyColor;
import items.BaseItem;
import javafx.geometry.Insets;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import logic.InventoryController;
//display pane of slot
public class Slot extends BorderPane{

	private BaseItem item;
	private boolean isHold;
	
	public Slot() {
		
		this.setPrefSize(80, 80);
		this.setInitialSlot();
		
		this.setOnMouseClicked(event -> {
			clickHandler();
		});
		
	}
	
	public void clickHandler() {
		if(this.getItem() == null)
			return ;
		if(this.isHold()) {
			this.setEffect(null);
			this.setHold(false);
			InventoryController.getInstance().setCurrentSlot(null);
		}
		else {
			if(InventoryController.getInstance().getCurrentSlot() != null) {
				InventoryController.getInstance().getCurrentSlot().setEffect(null);
				InventoryController.getInstance().getCurrentSlot().setHold(false);
			}
			InventoryController.getInstance().setCurrentSlot(this);
			this.setEffect(new DropShadow(40, Color.WHITE));
			this.setHold(true);
		}
		
	}
	
	public void setInitialSlot() {

		this.setBackground(new Background(new BackgroundFill(MyColor.CREAM,new CornerRadii(20),Insets.EMPTY)));
		this.setBorder(new Border(new BorderStroke(MyColor.BROWN,BorderStrokeStyle.SOLID,new CornerRadii(20),new BorderWidths(2))));
		this.setHold(false);
		this.setEffect(null);
		if(!this.getChildren().isEmpty())
			this.getChildren().clear();
	}

	public BaseItem getItem() {
		return item;
	}

	public void setItem(BaseItem item) {
		if(item == null) {
			this.item = null;
		}
		else {
			this.item = item;
			if(this.item.getImageView().getImage().getHeight()>this.item.getImageView().getImage().getWidth()) 
				this.item.getImageView().setFitHeight(70);
			else 
				this.item.getImageView().setFitWidth(70);
			
			this.item.getImageView().setLayoutX(0);
			this.item.getImageView().setLayoutY(0);
			this.setCenter(item.getImageView());

		}
	}

	public boolean isHold() {
		return isHold;
	}

	public void setHold(boolean isHold) {
		this.isHold = isHold;
	}
	
	public boolean equals(String name) {
		return this.getItem().getName().equals(name);
	}
	
	
}