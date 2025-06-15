package items;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.paint.Color;
import logic.WhoPuzzleController;
//person item which can be played
public class PersonPlayingItem extends BaseItem implements Playable{
	private boolean isSelect;
	private String sentence;
	
	public PersonPlayingItem(String name, String imagePath, String sentence) {
		super(name, imagePath);
		super.getImageView().setFitWidth(config.Config.imageBackgroundWidth*0.1);
		super.getImageView().setPreserveRatio(true);
		this.setSelect(false);
		this.setSentence(sentence);
		//change cursor when mouse hovered
		this.setOnMouseEntered(event -> {
			this.setCursor(Cursor.HAND);
			((WhoPuzzleController)WhoPuzzleController.getInstance()).updateText(sentence);
		});
		this.setOnMouseExited(event -> {
			this.setCursor(Cursor.DEFAULT);
			((WhoPuzzleController)WhoPuzzleController.getInstance()).updateText("");
		});
		
	}

	@Override
	public void play() {
		((WhoPuzzleController)WhoPuzzleController.getInstance()).select(this);
	}

	public boolean isSelect() {
		return isSelect;
	}

	public void setSelect(boolean isSelect) {
		if(isSelect) {
			this.setEffect(new DropShadow(20, Color.WHITE));
		}
		else {
			this.setEffect(null);
		}
		this.isSelect = isSelect;
		
	}

	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	

}
