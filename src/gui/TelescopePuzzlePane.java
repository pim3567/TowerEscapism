package gui;

import config.Config;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import logic.Status;
import logic.TelescopePuzzleController;
//display pane of telescope game
public class TelescopePuzzlePane extends StackPane {
	
	private ImageView backGalaxy;
    private double x = 0;
    private double y = 0;
    private double lastX, lastY;
	private double maxX, maxY, minX,minY ;
    
	
	public TelescopePuzzlePane() {
		
		this.setPrefWidth(Config.imageBackgroundWidth);
		this.setPrefHeight(Config.imageBackgroundHeight);
		this.setLayoutY((Config.screenHeight-Config.imageBackgroundHeight)/2);
	
		this.backGalaxy = new ImageView(new Image(ClassLoader.getSystemResource("images/telescopePuzzle/movingGalaxy.JPG").toString()));
		backGalaxy.setFitWidth(Config.imageBackgroundWidth);
		backGalaxy.setPreserveRatio(true);
		
		ImageView telescopeImage = new ImageView(new Image(ClassLoader.getSystemResource("images/telescopePuzzle/telescope.PNG").toString()));
		StackPane.setAlignment(telescopeImage, Pos.TOP_LEFT);
		telescopeImage.setFitWidth(Config.imageBackgroundWidth);
		telescopeImage.setPreserveRatio(true);
		InnerShadow innerShadow = new InnerShadow();
		innerShadow.setRadius(100);
		innerShadow.setColor(Color.BLACK);
		innerShadow.setChoke(0.5);
		telescopeImage.setEffect(innerShadow);
		
		
        this.getChildren().add(backGalaxy);
        this.getChildren().add(telescopeImage);
        
        javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(config.Config.imageBackgroundWidth, config.Config.imageBackgroundHeight);
        this.setClip(clip);
        
        this.setOnMousePressed((MouseEvent e) -> {
        	this.lastX = e.getSceneX();
        	this.lastY = e.getSceneY();

        });
        
        this.setOnMouseDragged((MouseEvent e) -> {
            this.scroll((e.getSceneX() - this.lastX), (e.getSceneY() - this.lastY));
            this.lastX = e.getSceneX();
            this.lastY = e.getSceneY();
        });
        
        this.setOnMouseEntered(e -> this.setCursor(Cursor.HAND));
        this.setOnMouseExited(e -> this.setCursor(Cursor.DEFAULT));
        
        setEdge();
	}
	
	public void scroll(double dx, double dy) {
	    this.x = Math.max(this.minX, Math.min(this.maxX, this.x));
	    this.y = Math.max(this.minY, Math.min(this.maxY, this.y));
	    this.x += dx;
	    this.y += dy;
        backGalaxy.setTranslateX(this.x);
        backGalaxy.setTranslateY(this.y);
        if(TelescopePuzzleController.getInstance().getStatus()!= Status.COMPLETE && TelescopePuzzleController.getInstance().checkWin()) {
        	TelescopePuzzleController.getInstance().setStatus(Status.STEP1);
        	TelescopePuzzleController.getInstance().update();
        }
        
    }
	
	public void setEdge() {
		this.maxX = Config.imageBackgroundWidth*0.4; //35
		this.maxY = Config.imageBackgroundHeight*0.37;
		this.minX = -(backGalaxy.getBoundsInParent().getWidth() - Config.imageBackgroundWidth)-this.maxX;
		this.minY = -(backGalaxy.getBoundsInParent().getHeight() - Config.imageBackgroundHeight)-this.maxY;
	    
	}
	
	public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }
   
}
