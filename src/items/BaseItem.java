package items;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.StackPane;
// Base class for all game's items
public class BaseItem extends StackPane{
	
	private String name;
	private ImageView imageView = new ImageView();
	
	public BaseItem(String name, String imagePath) {
		this.name = name;
		this.setImageView(imagePath);
		this.getChildren().add(this.imageView);
		this.setOnMouseClicked(event -> clickHandler());    
	    
	    this.setOnMouseMoved(e -> {
            Image image = this.getImageView().getImage();
            PixelReader reader = image.getPixelReader();

            if (reader == null) {
                this.setCursor(Cursor.DEFAULT);
                return;
            }

            double scaleX = image.getWidth() / this.getImageView().getBoundsInLocal().getWidth();
            double scaleY = image.getHeight() / this.getImageView().getBoundsInLocal().getHeight();
            int x = (int)(e.getX() * scaleX);
            int y = (int)(e.getY() * scaleY);

            if (x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight()) {
                javafx.scene.paint.Color color = reader.getColor(x, y);
                if (color.getOpacity() > 0.1) {
                    this.setCursor(Cursor.HAND);
                } else {
                    this.setCursor(Cursor.DEFAULT);
                }
            } else {
                this.setCursor(Cursor.DEFAULT);
            }
        });
	    
	    this.setPickOnBounds(true);
	}
	
	public BaseItem(String name, String imagePath,double width,double height,double layoutX,double layoutY) {
		this.name = name;
		this.setImageView(imagePath);
		this.imageView.setFitWidth(config.Config.imageBackgroundWidth * width);
		this.imageView.setPreserveRatio(true);
		this.imageView.setOnMouseClicked(event -> clickHandler());
		this.getChildren().add(this.imageView);
		
		this.setLayoutX(config.Config.imageBackgroundWidth * layoutX);
		this.setLayoutY(config.Config.imageBackgroundHeight * layoutY);

		this.setOnMouseMoved(e -> {
            javafx.scene.image.Image image = this.getImageView().getImage();
            javafx.scene.image.PixelReader reader = image.getPixelReader();

            if (reader == null) {
                this.setCursor(Cursor.DEFAULT);
                return;
            }

            
            double scaleX = image.getWidth() / this.getImageView().getBoundsInLocal().getWidth();
            double scaleY = image.getHeight() / this.getImageView().getBoundsInLocal().getHeight();
            int x = (int)(e.getX() * scaleX);
            int y = (int)(e.getY() * scaleY);

            if (x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight()) {
                javafx.scene.paint.Color color = reader.getColor(x, y);
                if (color.getOpacity() > 0.1) {
                    this.setCursor(Cursor.HAND);
                } else {
                    this.setCursor(Cursor.DEFAULT);
                }
            } else {
                this.setCursor(Cursor.DEFAULT);
            }
        });
	}
	
	public void clickHandler() {
		if(this instanceof Pickable) {
			((Pickable)this).pick();
		}
		else if(this instanceof Playable)
			((Playable)this).play();
	}

	public String getName() {
		return name;
	}

	public ImageView getImageView() {
		return imageView;
	}

	

	public void setImageView(String imagePath) {
		if(ClassLoader.getSystemResource(imagePath) == null) {
			return ;
		}
		this.imageView.setImage(new Image(ClassLoader.getSystemResource(imagePath).toString()));
	}
	
}
