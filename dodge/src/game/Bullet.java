package game;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
//code.gondr.net => 80
import javafx.scene.paint.Color;

public class Bullet {
	public double x;
	public double y;
	public double radius = 5; 
	
	private double speed = 80;
	
	private double dx = 0;
	private double dy = 0;
	
	private Color color;
	
	private Random rnd;
	
	private double w;
	private double h;
	
	public Bullet() {
		rnd = new Random();
		w = MainGame.game.gameCanvas.getWidth();
		h = MainGame.game.gameCanvas.getHeight();
		reset();
	}
	
	public void reset() {
		int r = rnd.nextInt(256);
		int g = rnd.nextInt(256);
		int b = rnd.nextInt(256);
		color = Color.rgb(r, g, b, 0.8);
		
		int direction = rnd.nextInt(4);
		
		if (direction == 0) {
			x = -10;
			y = rnd.nextDouble() * h;
		}else if(direction == 1) {
			x = rnd.nextDouble() * w;
			y = -10;
		}else if (direction == 2) {
			x = w + 10;
			y = rnd.nextDouble() * h;   // 0.0 <= x < 1.0
		}else {
			x = rnd.nextDouble() * w;
			y = h + 10;
		}
		
		double px = MainGame.game.player.x;
		double py = MainGame.game.player.y;
		
		double tempX = px - x;
		double tempY = py - y;
		
		double distance = Math.sqrt(Math.pow(tempX, 2) + Math.pow(tempY, 2));
		dx = tempX / distance;
		dy = tempY / distance;
	}
	
	public void update(double d) {
		x = x + dx * speed * d;
		y = y + dy * speed * d;
		
		if( x < -20  || x > w + 20 || y < -20 || y > h + 20) {
			reset();
		}
	}
	
	public void render(GraphicsContext gc) {
		gc.beginPath();
		gc.arc(x, y, radius, radius, 0, 360);
		gc.setFill(color);
		gc.fill();
		gc.setStroke(Color.WHITE);
		gc.stroke();
	}
}