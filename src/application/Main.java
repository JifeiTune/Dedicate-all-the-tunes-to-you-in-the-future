package application;
	
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import play.PlayC;
import select.SelectC;


public class Main extends Application 
{
	public void start(Stage primaryStage) throws IOException 
	{
		//将primaryStage共享
		TitleC.primaryStage=primaryStage;
		SelectC.primaryStage=primaryStage;
		PlayC.primaryStage=primaryStage;
		//打开主界面
		TitleC.start();
	}
	public static void main(String[] args) 
	{
		launch(args);
	}
}
