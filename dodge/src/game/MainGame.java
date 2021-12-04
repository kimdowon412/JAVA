package game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

// 시간에 따라 총알의 개수가 증가하기 -- 1
// 배경음악 추가하기.

public class MainGame {
	public static MainGame game;
	
	private AnimationTimer mainLoop;
	private long before;
	
	public Canvas gameCanvas;
	private GraphicsContext gc;
	
	public Player player;
	private List<Bullet> list;
	public TimeLabel timeLabel;
	private List<Background> bgList;
	private Heart heart;
	
	private boolean gameOver = true;
	
	private double w;
	private double h;

	
	private BGM bgm = new BGM();
	
	public MainGame(Canvas canvas) {
		game = this;
		gameCanvas = canvas;
		gc = gameCanvas.getGraphicsContext2D();
		
		w = gameCanvas.getWidth();
		h = gameCanvas.getHeight();
		
		//한번만 셋팅하면 되니까 처음 생성시에 해준다.
		gc.setTextAlign(TextAlignment.CENTER);
		gc.setTextBaseline(VPos.CENTER);
		
		player = new Player(); //더 바닥일줄은 몰랐어.
		timeLabel = new TimeLabel(w / 2, 30);
		list = new ArrayList<>();
		bgList = new ArrayList<>();
		heart = new Heart();
		
		for(int i = 0; i < 30; i++) {
			Bullet temp = new Bullet();
			list.add(temp);
		}
		
		File file = new File(getClass().getResource("/images/sky.png").getFile());
		Image img = new Image(file.toURI().toString());
		
		for(int i = 0; i < 2; i++) {
			Background bg = new Background(img, -h * i);
			bgList.add(bg);
		}
		
		before = System.nanoTime();
		
		mainLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				long deltaTime = now - before;
				before = now;
				// 1000ns => 1us   1000us -> 1ms   1000ms -> 1초
				double d = deltaTime / 1000000000d;  //나노초를 초단위로 변경해준다.
				
				update(d);
				render();
			}
			
		};
		
	}
	
	private void resetGame() {
		player.reset();
		heart.reset();
		for(Bullet b : list) {
			b.reset();
		}
		
		timeLabel.reset();
		
		gameOver = false;
	}
	
	public void update(double d) {
		for(Background b : bgList) {
			b.update(d);
		}
		if(bgList.get(0).checkOut()) {
			Background first = bgList.remove(0);
			first.y = bgList.get(bgList.size() - 1).y - h; //마지막 원소를 뽑아와서
			bgList.add(first);
		}
		
		if(gameOver) return;
		
		player.update(d);
		
		for(Bullet b : list) {
			b.update(d);
			if(player.checkCollision(b)) {
				if(heart.cnt <= 1) {
					gameOver = true;
					break;
				}else {
					b.reset();
					heart.cnt--;
				}				
			}
		}
		
		timeLabel.update(d);
	}
	
	public void render() {
		gc.clearRect(0, 0, w, h);
		for(Background b : bgList) {
			b.render(gc);
		}
		
		if(gameOver) {
			gc.setFill(Color.WHITE);
			gc.fillText("게임을 시작하려면 스페이스 키를 누르세요.", w/2, h/2);
			gc.fillText(timeLabel.getTime() , w/2, h/2 + 40);
			return;
		}
		
		player.render(gc);
		heart.render(gc);
		
		for(Bullet b : list) {
			b.render(gc);
		}			
		
		timeLabel.render(gc);
		
		gc.setStroke(Color.BLACK);
		gc.strokeRect(0, 0, w-1, h-1);
	}
	
	public void keyUpHandle(KeyEvent e) {
		if(gameOver) return;
		player.keyUpHandle(e);
	}
	public void keyPressHandle(KeyEvent e) {
		if(gameOver) {
			if(e.getCode() == KeyCode.SPACE) {
				resetGame();
			}
			return;
		}
		player.keyPressHandle(e);
	}
	
	public void gameStart() {
		mainLoop.start();
	}
}