package game;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
//  http://gondr.asuscomm.com/phpmyadmin/
// yy_102XX
// 1234

public class BGM {
	private MediaPlayer player;
	public BGM() {
		Media wav = new Media( getClass().getResource(
								"/images/bgm.wav").toString());
		player = new MediaPlayer(wav);
		
		player.setOnEndOfMedia( ()->{
			player.stop();
			player.seek(player.getStartTime());
			player.play();
		});
		
		player.play();
	}
}
