package game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
// SQL = Structured Query Language

public class TimeLabel {
	public double x;
	public double y;
	
	public double time = 0;
	
	public String getTime() {
		double t = Math.round(time * 100) / 100.0;
		return t + "";
	}
	
	public TimeLabel(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void reset() {
		time = 0;
	}
	
	public void update(double d) {
		time += d;  //time은 게임이 시작되고 난 후의 시간을 초단위로 기록하는 게 돼
	}
	
	public void render(GraphicsContext gc) {
		gc.setFill(Color.WHITE);
		// 2.1332123451234123 => 213.32123451234123 => 213 => 2
		//time = Math.round(time * 100) / 100d;
		
		gc.fillText(getTime(), x, y);
	}
}
