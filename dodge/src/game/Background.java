package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Background {
	
	private Image img;
	public double x = 0;
	public double y;
	
	private double scrollSpeed = 50;
	
	private double cw;
	private double ch;
	
	public Background(Image img, double y) {
		this.img = img;
		this.y = y;
		cw = MainGame.game.gameCanvas.getWidth();
		ch = MainGame.game.gameCanvas.getHeight();
	}
	
	public boolean checkOut() {
		return this.y > ch;
	}
	
	public void update(double d) {
		y += scrollSpeed * d;
	}
	
	public void render(GraphicsContext gc) {
		gc.drawImage(img, x, y, cw, ch);
	}
}
