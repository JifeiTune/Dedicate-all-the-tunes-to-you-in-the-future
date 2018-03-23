package application;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import play.PlayC;
import select.SelectC;
import javafx.scene.image.ImageView;

public class TitleC
{
	public static Stage primaryStage;
	public static Scene scene;//前两个界面是固定的，它们的类都保存各自的scene，免得每次跳转都重新加载
	public static MediaPlayer click;
	public static MediaPlayer enter;
	public static MediaPlayer bgm;
	public static MediaPlayer begin;
	public static MediaPlayer con;//本来是continue，但因为是java关键字所以省略写
	public static MediaPlayer gift;
	public static MediaPlayer exit1;
	public static MediaPlayer exit2;
	@FXML Pane rootPane;
	@FXML VBox vb;
	@FXML ImageView be;
	@FXML ImageView co;
	@FXML ImageView gi;
	@FXML ImageView ex;
	@FXML ImageView confirm;
	@FXML ImageView yes;
	@FXML ImageView no;
	static
	{
		try 
		{
			click=new MediaPlayer(new Media(TitleC.class.getResource("/sounds/click.mp3").toString()));
			enter=new MediaPlayer(new Media(TitleC.class.getResource("/sounds/enter.mp3").toString()));
			bgm=new MediaPlayer(new Media(TitleC.class.getResource("/sounds/かわいい足音.mp3").toString()));
			bgm.setCycleCount(-1);//bgm设为在调用stop方法之前无限循环
			begin=new MediaPlayer(new Media(TitleC.class.getResource("/sounds/begin.mp3").toString()));
			con=new MediaPlayer(new Media(TitleC.class.getResource("/sounds/continue.mp3").toString()));
			gift=new MediaPlayer(new Media(TitleC.class.getResource("/sounds/gift.mp3").toString()));
			exit1=new MediaPlayer(new Media(TitleC.class.getResource("/sounds/exit1.mp3").toString()));
			exit2=new MediaPlayer(new Media(TitleC.class.getResource("/sounds/exit2.mp3").toString()));
			scene=new Scene(FXMLLoader.load(TitleC.class.getResource("TitleF.fxml")));
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void start() throws IOException//界面固定不必每次选择性地初始化，故设为静态
	{
		bgm.play();
        primaryStage.setScene(scene);
        primaryStage.setTitle("把所有的曲献给未来的你");
        primaryStage.setResizable(false);
        primaryStage.show();
	}
	@FXML void click(MouseEvent e) throws IOException//处理鼠标点击事件
	{
		click.stop();
		click.play();//总是播放click音效
		switch(((ImageView)e.getSource()).getId())
		{
			case	"be"://转向选关界面
				begin.stop();
				begin.play();
				SelectC.start();
				bgm.stop();
			break;
			case	"co"://转向读取存档界面
				bgm.stop();
				con.stop();
				con.play();
				DataInputStream output=null;
				String name="A";
				double speed=3.0;
				try
				{
					output=new DataInputStream(new FileInputStream("save"));
					name=output.readUTF();
					speed=output.readDouble();//读入存档
					output.close();
				}
				catch (FileNotFoundException e2) {}
				(new PlayC(name,speed)).start();
			break;
			case	"gi"://转向欣赏界面
				gift.stop();
				gift.play();
			break;
			case	"ex"://跳出确认是否结束游戏对话框
				exit1.stop();
				exit1.play();
				//跳出对话框时，主菜单节点不再可互动
				be.setDisable(true);
				co.setDisable(true);
				gi.setDisable(true);
				ex.setDisable(true);
				
				confirm.setVisible(true);
				yes.setVisible(true);
				no.setVisible(true);
			break;
		}		
	}
	@FXML void mIn(MouseEvent e) throws InterruptedException//鼠标移入的特效和音效
	{
		enter.stop();
		enter.play();
		if(((ImageView)e.getSource()).getId().equals("yes")||((ImageView)e.getSource()).getId().equals("no")) 
		{
			((ImageView)e.getSource()).setEffect(new DropShadow(20,Color.BLACK));
		}
		else
		{
			((ImageView)e.getSource()).setStyle("-fx-translate-x: -5");
		}
		
	}
	@FXML void mOut(MouseEvent e)//鼠标移出的特效和音效
	{
		if(((ImageView)e.getSource()).getId().equals("yes")||((ImageView)e.getSource()).getId().equals("no")) 
		{
			((ImageView)e.getSource()).setEffect(null);
		}
		else
		{
			((ImageView)e.getSource()).setStyle("-fx-translate-x: 5");
		}
	}
	@FXML void confirm(MouseEvent e) throws InterruptedException//对话框
	{
		click.stop();
		click.play();
		if(((ImageView)e.getSource()).getId().equals("yes"))
		{
			bgm.stop();
			exit2.stop();
			exit2.play();
			primaryStage.close();//关闭窗口，结束游戏
			Thread.sleep(3570);
		}
		else
		{
			//关闭对话框
			be.setDisable(false);
			co.setDisable(false);
			gi.setDisable(false);
			ex.setDisable(false);
			
			confirm.setVisible(false);
			yes.setVisible(false);
			no.setVisible(false);
		}
	}
	public static void fadePlay(Duration du,ImageView im)//为指定图片播放指定时间的动画
	{
		FadeTransition f=new FadeTransition(du,im);
		f.setFromValue(0);
		f.setToValue(1);
		f.setAutoReverse(true);
		f.setCycleCount(2);
		f.play();
	}
}

