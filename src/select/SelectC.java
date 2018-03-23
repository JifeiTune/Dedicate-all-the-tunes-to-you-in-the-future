package select;

import java.io.IOException;
import application.TitleC;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import play.PlayC;


public class SelectC
{
	public static Stage primaryStage;
	public static Scene scene;
	public static MediaPlayer bgm;
	static
	{
		try 
		{
			bgm=new MediaPlayer(new Media(SelectC.class.getResource("/sounds/天真爛漫.mp3").toString()));
			bgm.setCycleCount(-1);//bgm设为在调用stop方法之前无限循环
			scene=new Scene(FXMLLoader.load(SelectC.class.getResource("SelectF.fxml")));
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	@FXML Pane secondPane;
	static @FXML ImageView returnT;
	public static void start() throws IOException
	{
		bgm.stop();
		bgm.play();
        primaryStage.setScene(scene);
	}
	@FXML void mIn(MouseEvent e)
	{
		TitleC.enter.stop();
		TitleC.enter.play();
		if(e.getSource() instanceof ImageView)
		{
			((ImageView)e.getSource()).setStyle("-fx-translate-x: 5");
		}
		else
		{
			((Circle)e.getSource()).setEffect(new BoxBlur(10, 10, 3));
		}
		
	}
	@FXML void mOut(MouseEvent e)
	{
		if(e.getSource() instanceof ImageView)
		{
			((ImageView)e.getSource()).setStyle("-fx-translate-x: -5");
		}
		else
		{
			((Circle)e.getSource()).setEffect(null);
		}
	}
	@FXML void click(MouseEvent e) throws IOException//处理鼠标点击事件
	{
		if( e.getSource() instanceof ImageView)//点击返回图片，返回主界面
		{
			TitleC.click.stop();
			TitleC.click.play();
			TitleC.start();
			bgm.stop();
		}
		else
		{
			bgm.stop();
			switch(((Circle)e.getSource()).getId())
			{
				case	"A":
					(new PlayC(((Circle)e.getSource()).getId(),3.0)).start();
				break;
				case	"B":
					(new PlayC(((Circle)e.getSource()).getId(),5.5)).start();
				break;
				case	"C":
					(new PlayC(((Circle)e.getSource()).getId(),7.3)).start();
				break;
				case	"D":
					(new PlayC(((Circle)e.getSource()).getId(),8.4)).start();
				break;
				case	"E":
					(new PlayC(((Circle)e.getSource()).getId(),5.8)).start();
				break;
			}
		}
	}
}
