package items;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import logic.FlowerPuzzleController;
// flower item which can be played
public class FlowerPlayingItem extends BaseItem implements Playable{
	private boolean bloom;

	
	public FlowerPlayingItem(String name, boolean bloom, double layoutX, double layoutY) {
		super(name,bloom ? "images/flowerPuzzle/bloomed" + name + ".png" : "images/flowerPuzzle/unbloomed" + name + ".png",
				0.08, 0, layoutX, layoutY);
        this.bloom = bloom;
        //change cursor when mouse hovered
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
                Color color = reader.getColor(x, y);
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
	
	@Override
	public void play() {
		this.toggle();
		((FlowerPuzzleController)FlowerPuzzleController.getInstance()).changeStatus(this.getName());
	}


	public boolean isBloom() {
		return bloom;
	}

	public void setBloom(boolean bloom) {
		this.bloom = bloom;
		if(bloom)
			super.setImageView("images/flowerPuzzle/bloomed"+super.getName()+".png");
		else
			super.setImageView("images/flowerPuzzle/unbloomed"+super.getName()+".png");
	}

	public void toggle() {
		if(isBloom())
			this.setBloom(false);
		else 
			this.setBloom(true);
	}
	
}
