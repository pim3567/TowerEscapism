package items;

import logic.BookPuzzleController;
// book item which can be played
public class BookPlayingItem extends BaseItem implements Playable{

	private int index;
	private int bookId;
	
	public BookPlayingItem(String name, int index, int bookId) {
		super(name, "");
		super.getImageView().setFitWidth(config.Config.imageBackgroundWidth*0.034);
		super.getImageView().setPreserveRatio(true);
		this.setIndex(index);
		this.setBookId(bookId);
		// change cursor when hovered
		this.setOnMouseMoved(e -> {
            javafx.scene.image.Image image = this.getImageView().getImage();
            javafx.scene.image.PixelReader reader = image.getPixelReader();

            if (reader == null) {
                this.setCursor(javafx.scene.Cursor.DEFAULT);
                return;
            }

           
            double scaleX = image.getWidth() / this.getImageView().getBoundsInLocal().getWidth();
            double scaleY = image.getHeight() / this.getImageView().getBoundsInLocal().getHeight();
            int x = (int)(e.getX() * scaleX);
            int y = (int)(e.getY() * scaleY);

            if (x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight()) {
                javafx.scene.paint.Color color = reader.getColor(x, y);
                if (color.getOpacity() > 0.1) {
                    this.setCursor(javafx.scene.Cursor.HAND);
                } else {
                    this.setCursor(javafx.scene.Cursor.DEFAULT);
                }
            } else {
                this.setCursor(javafx.scene.Cursor.DEFAULT);
            }
        });
	}
	
	@Override
	public void play() {
		if(index==12) //a book on the rightest switch position with its left one
			((BookPuzzleController)BookPuzzleController.getInstance()).swap(index-1,index);
		else //others switch with their right one
			((BookPuzzleController)BookPuzzleController.getInstance()).swap(index,index+1);
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	public void setBookId(int bookId) {
		this.bookId = bookId;
		super.setImageView("images/bookPuzzle/book"+Integer.toString(bookId)+".png");
		
	}

	public int getBookId() {
		return bookId;
	}

}
