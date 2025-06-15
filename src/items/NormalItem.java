package items;

import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import logic.InventoryController;
// normal item which can be picked
public class NormalItem extends BaseItem implements Pickable {

	private boolean pickState;

	public NormalItem(String name, String imagePath, double width, double height, double layoutX, double layoutY) {
		super(name, imagePath, width, height, layoutX, layoutY);
		pickState = false;

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
			int x = (int) (e.getX() * scaleX);
			int y = (int) (e.getY() * scaleY);

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

	@Override
	public void pick() {
		if (!isPick()) {
			InventoryController.getInstance().addItem(this);
			pickState = true;
		}
	}

	@Override
	public boolean isPick() {
		return pickState;
	}

}
