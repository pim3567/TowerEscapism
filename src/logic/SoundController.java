package logic;

import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
//manage sound
public class SoundController {
	private Media bgm = new Media(ClassLoader.getSystemResource("sounds/backgroundMusic.mp3").toExternalForm());
	private Media clickEff = new Media(ClassLoader.getSystemResource("sounds/clickEffect.mp3").toExternalForm());
	private Media correctEff = new Media(ClassLoader.getSystemResource("sounds/correctEffect.mp3").toExternalForm());
	private Media wrongEff = new Media(ClassLoader.getSystemResource("sounds/wrongEffect.mp3").toExternalForm());
	private MediaPlayer bgmPlayer, clickPlayer, correctEffPlayer, wrongEffPlayer;
	private Slider bgmSlider, effSlider;
	private static SoundController instance;

	public SoundController() {
		bgmPlayer = new MediaPlayer(bgm);
		bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop music
		clickPlayer = new MediaPlayer(clickEff);
		correctEffPlayer = new MediaPlayer(correctEff);
		wrongEffPlayer = new MediaPlayer(wrongEff);
	}
	

	static public SoundController getInstance() {
		if (instance == null)
			instance = new SoundController();
		return instance;
	}
	//make volume change accordingly with slider
	public void setSlider(Slider bgmSlider,Slider effSlider) {
		this.bgmSlider = bgmSlider;
        bgmPlayer.volumeProperty().bind(bgmSlider.valueProperty());

        this.effSlider = effSlider;
        clickPlayer.volumeProperty().bind(effSlider.valueProperty());
        correctEffPlayer.volumeProperty().bind(effSlider.valueProperty());
        wrongEffPlayer.volumeProperty().bind(effSlider.valueProperty());
	}
	
	public void initial() {
		bgmPlayer.play();
	}
	
	public void playClickEff() {
		clickPlayer.stop(); 
        clickPlayer.play();
	}
	
	public void playCorrectEff() {
		correctEffPlayer.stop(); 
        correctEffPlayer.play();
	}
	
	public void playWrongEff() {
		wrongEffPlayer.stop(); 
        wrongEffPlayer.play();
	}
	

}
