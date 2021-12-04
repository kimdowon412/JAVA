package views;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import application.RecordVO;
import game.MainGame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class Maincontroller {
	@FXML
	private Canvas canvas;
	@FXML
	private TextField txtName;
	@FXML
	private ListView<RecordVO> lstTop10;
	
	private ObservableList<RecordVO> list 
							= FXCollections.observableArrayList();
	
	private GraphicsContext gc;
	
	private MainGame game;
	
	@FXML
	private void initialize() {
		lstTop10.setItems(list);
		
		getRecordFromServer();
		
		gc = canvas.getGraphicsContext2D();
		game = new MainGame(canvas);
		game.gameStart();
	}
	
	public void getRecordFromServer() {
		list.clear();
		
		try {
			//데이터베이스 연결을 위한 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			//연결할 주소
			String cStr = "jdbc:mysql://gondr.asuscomm.com/yy_10225"
				+ "?useUnicode=true"
				+ "&characterEncoding=utf8"
				+ "&useSSL=false"
				+ "&serverTimezone=Asia/Seoul";
			String userId = "yy_10225";
			String password = "1234";
			
			//java.sql.connection
			Connection con = DriverManager.getConnection(
										cStr, userId, password);
			
			String sql = "SELECT * FROM scores ORDER BY time DESC LIMIT 0, 10";
			
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				String name = rs.getString("name");
				double time = rs.getDouble("time");
				
				RecordVO vo = new RecordVO();
				vo.name = name;
				vo.time = time;
				list.add(vo);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void recordToServer() {
		//사용자가 입력한 이름을 가져온다.
		String name = txtName.getText();
		//저장하는 시점의 게임 기록을 가져온다.
		double time = MainGame.game.timeLabel.time;
		
		try {
			//데이터베이스 연결을 위한 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver");
			//연결할 주소
			String cStr = "jdbc:mysql://gondr.asuscomm.com/yy_10225"
				+ "?useUnicode=true"
				+ "&characterEncoding=utf8"
				+ "&useSSL=false"
				+ "&serverTimezone=Asia/Seoul";
			String userId = "yy_10225";
			String password = "1234";
			
			//java.sql.connection
			Connection con = DriverManager.getConnection(
										cStr, userId, password);
			
			String sql = "INSERT INTO scores(time, name) VALUES(?, ?)";
			
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setDouble(1, time);
			pstmt.setString(2, name);
			pstmt.executeUpdate();
			
			getRecordFromServer();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void keyPressHandle(KeyEvent e) {
		game.keyPressHandle(e);
	}
	public void keyUpHandle(KeyEvent e) {
		game.keyUpHandle(e);
	}
}
