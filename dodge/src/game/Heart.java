package game;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Heart {
	public double x = 20;
	public double y = MainGame.game.gameCanvas.getHeight() - 50;
	
	public int cnt = 3;
	
	private Image img;
	
	public Heart() {
		File file = new File(getClass().getResource(
							"/images/heart.png").getFile());
		img = new Image(file.toURI().toString());
	}
	
	public void reset() {
		cnt = 3;
	}
	
	public void update(double d) {
		//do nothing
	}
	
	public void render(GraphicsContext gc) {
		for(int i = 0; i < cnt; i++) {
			gc.drawImage(img, x + i * 40, y, 30, 30);
		}
	}
}
