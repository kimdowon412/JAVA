package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import views.Maincontroller;

//타이머
//게임 재시작
//DB연결 및 기록 -> 파일기록 및 리딩
//시작시 랭킹 오버롤

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/views/MainLayout.fxml"));
			AnchorPane root = loader.load();
			Scene scene = new Scene(root);
			Maincontroller mc = loader.getController();
			
			scene.addEventFilter(KeyEvent.KEY_PRESSED, e-> {
				mc.keyPressHandle(e);
			});
			scene.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
				mc.keyUpHandle(e);
			});
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
