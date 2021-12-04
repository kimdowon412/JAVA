package game;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Player {
	public double x = 200;
	public double y = 200;
	
	private double dx = 0;  //방향 저장 변수
	private double dy = 0;
	
	private double size = 40;
	private double speed = 100;
	//private Color playerColor = Color.RED;
	
	private double cw;
	private double ch;
	
	private Image img;
	
	private Map<KeyCode, Boolean> keyMap = new HashMap<>();
	
	public void update(double d) {
		if(keyMap.get(KeyCode.UP)) {
			dy = -1;
		}else if(keyMap.get(KeyCode.DOWN)) {
			dy = 1;
		}else {
			dy = 0;
		}
		
		if(keyMap.get(KeyCode.LEFT)) {
			dx = -1;
		}else if(keyMap.get(KeyCode.RIGHT)) {
			dx = 1;
		}else {
			dx = 0;
		}
		
		x = x + dx * speed * d;
		y = y + dy * speed * d;
		
		if(x < size/2) x = size/2;
		if(x > cw - size/2) x = cw - size/2;
		if(y < size /2) y = size/2;
		if(y > ch - size/2) y = ch - size/2;
	}
	
	public Player() {
		File file = new File(getClass().getResource("/images/ufo.png").getFile());
		img = new Image(file.toURI().toString());
		
		cw = MainGame.game.gameCanvas.getWidth();
		ch = MainGame.game.gameCanvas.getHeight();
		reset();
	}
	
	public void reset() {
		this.x = cw / 2;
		this.y = ch / 2;
		keyMap.put(KeyCode.UP, false);
		keyMap.put(KeyCode.DOWN, false);
		keyMap.put(KeyCode.LEFT, false);
		keyMap.put(KeyCode.RIGHT, false);
	}
	
	public void render(GraphicsContext gc) {
		gc.drawImage(img, x - size/2, y - size/2, size, size);
	}
	
	
	
	public void keyPressHandle(KeyEvent e) {
		keyMap.put(e.getCode(), true);
	}
	public void keyUpHandle(KeyEvent e) {
		keyMap.put(e.getCode(), false);
	}
	
	//총알과의 충돌을 검사
	public boolean checkCollision(Bullet bullet) {
		double distance =  Math.sqrt((bullet.x - x) * (bullet.x - x) + (bullet.y - y) * (bullet.y - y));
		//루트를 씌워 거리를 잰다.
		return distance < bullet.radius + size / 2; // 충돌여부에 따라 true false를 리턴
	}
}
