package play;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import application.TitleC;
import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import select.SelectC;

public class PlayC//本类在使用SceneBuilder以及fxml文件辅助编程时，产生了前所未有的、莫名其妙的bug，
			//调了四五个小时无解，心力交瘁，喷出一口老血，索性纯写代码实现，因此代码会显得比较冗长
{
	public static Stage primaryStage;
	Scene scene;
	Pane bg;//最底下的面板
	//四个第二层的面板分别加入动画
	Pane p1;
	Pane p2;
	Pane p3;
	Pane p4;
	Timeline t1;
	Timeline t2;
	Timeline t3;
	Timeline t4;
	
	String name;//音乐和背景的名字（同名）
	double speed;//游戏速度，1-10
	MediaPlayer bgm;
	ImageView returnS;
	ImageView effect1;
	ImageView effect2;
	ImageView effect3;
	ImageView effect4;
	Line line;
	Text score;
	int s;//分数
	
	public PlayC(String name,Double speed)//构造函数，同时初始化各元素
	{
		//本来面板布局采用了4个VBox安放在一个Hbox上的形式，
		//但途中发现它们自动调整大小的特点带来了许多麻烦，只好改用最原始的Pane
		this.name=name;
		this.speed=speed;
		//最底下的面板
		bg=new Pane();
		bg.setPrefWidth(790);
		bg.setPrefHeight(590);
		bg.setStyle("-fx-background-image: url(\"/images/"+name+".jpg\")");
		bg.setId("bg");
		//四个第二层面板
		p1=new Pane();
		p2=new Pane();
		p3=new Pane();
		p4=new Pane();
		bg.getChildren().addAll(p1,p2,p3,p4);
		initPane(p1,85,0,"p1");
		initPane(p2,265,0,"p2");
		initPane(p3,445,0,"p3");
		initPane(p4,625,0,"p4");
		//四个动画
		initTimeLine(1);
		initTimeLine(2);
		initTimeLine(3);
		initTimeLine(4);
		//判定线
		line=new Line(0,540,790,540);
		line.setStroke(Color.web("#00ffff"));
		line.setStrokeWidth(5);
		line.setEffect(new GaussianBlur(14));
		//显示分数
		score=new Text(20,80,"0");
		score.setFont(Font.font("Arial Narrow",FontWeight.BOLD,FontPosture.ITALIC,50.0));
		score.setFill(Color.web("#ff00bf"));
		score.setStroke(Color.WHITE);
		score.setStrokeWidth(2);
		
		p1.setFocusTraversable(true);//设置p1为焦点，本来想设背景面板bg为焦点但无效，不知原因，此问题待解决
		p1.setOnKeyPressed(new KeyInput());
		//返回按钮
		returnS=new ImageView("/images/back.png");
		returnS.setScaleX(0.8);
		returnS.setScaleY(0.8);
		returnS.setX(684);
		returnS.setY(15);
		returnS.setId("returnS");
		returnS.setCursor(Cursor.HAND);
		returnS.setOnMouseClicked(new Click());
		returnS.setOnMouseEntered(new MIn());
		returnS.setOnMouseExited(new MOut());
		//四张效果图片，对应有动画
		effect1=new ImageView("/images/effect.png");
		effect2=new ImageView("/images/effect.png");
		effect3=new ImageView("/images/effect.png");
		effect4=new ImageView("/images/effect.png");
		initImageView(effect1,-5,450);
		initImageView(effect2,175,450);
		initImageView(effect3,355,450);
		initImageView(effect4,535,450);
		bg.getChildren().addAll(line,score,returnS,effect1,effect2,effect3,effect4);
		
		scene=new Scene(bg);
		bgm=new MediaPlayer(new Media(SelectC.class.getResource("/sounds/"+name+".mp3").toString()));
	}
	//初始化四个第二层的面板
	public void initPane(Pane p,double x,double y,String id)//初始化4个面板
	{
		p.setPrefSize(80,590);
		p.setMaxSize(80,590);
		p.setLayoutX(x);
		p.setLayoutY(y);
		p.setId(id);
	}
	public void initImageView(ImageView im,double x,double y)
	{
		im.setX(x);
		im.setY(y);
		im.setOpacity(0);
	}
	public void initTimeLine(int index)
	{
		switch(index)
		{
			case	1:
				t1=new Timeline(new KeyFrame(Duration.seconds(2),e->addR(t1,p1)));
				t1.setCycleCount(Timeline.INDEFINITE);
			break;
			case	2:
				t2=new Timeline(new KeyFrame(Duration.seconds(2),e->addR(t2,p2)));
				t2.setCycleCount(Timeline.INDEFINITE);
			break;
			case	3:
				t3=new Timeline(new KeyFrame(Duration.seconds(2),e->addR(t3,p3)));
				t3.setCycleCount(Timeline.INDEFINITE);
			break;
			case	4:
				t4=new Timeline(new KeyFrame(Duration.seconds(2),e->addR(t4,p4)));
				t4.setCycleCount(Timeline.INDEFINITE);
			break;
		}	
	}
	public void addR(Timeline t,Pane p)//添加一个方块下落的动画
	{
		t.setRate(1+0.3*speed*Math.random());//speed的作用体现在此
		double h=50+200*Math.random();//方块的高，一定范围内随机
		Rectangle r=new Rectangle(50,h);
		r.setY(-h);//先设为-h，方块将不被显示
		Line l=new Line(0,-h/2,0,540+h/2+2);//注意，这个startX和startY应该是相对于所在Pane的坐标，
		//一开始我用的绝对坐标出了问题，调了半天猜到了是这里出问题，还有末尾纵坐标多加2以确保下面的listener正确判定
		r.setFill(Color.WHITE);
		r.setOpacity(0.8);//白色+降低透明度，产生通透的效果
		//内阴影效果，创造立体感(可惜效果还是不尽人意（无奈）)
		InnerShadow sd=new InnerShadow(30,Color.color(Math.random(), Math.random(), Math.random()));
		sd.setWidth(90);
		sd.setHeight(10);
		sd.setChoke(0.5);
		r.setEffect(sd);
		PathTransition pt=new PathTransition(Duration.seconds(2),l,r);
		p.getChildren().add(r);
		r.translateYProperty().addListener(
    			e->
    			{
    				if(r.getTranslateY()>(540+h))
    				{
    					pt.stop();
    					p.getChildren().remove(0);
    				}
    			});
		pt.play();
	}
	class MIn implements EventHandler<MouseEvent>
	{
		public void handle(MouseEvent event) 
		{
			TitleC.enter.stop();
			TitleC.enter.play();
			((ImageView)event.getSource()).setStyle("-fx-translate-x: 5");
		}
	}
	class MOut implements EventHandler<MouseEvent>
	{
		public void handle(MouseEvent event) 
		{
			((ImageView)event.getSource()).setStyle("-fx-translate-x: -5");
		}
	}
	class Click implements EventHandler<MouseEvent>
	{
		public void handle(MouseEvent event) 
		{
			TitleC.click.stop();
			TitleC.click.play();
			switch(((ImageView)event.getSource()).getId())
			{
				case "returnS":
					try 
					{
						SelectC.start();
						bgm.stop();
						DataOutputStream output=new DataOutputStream(new FileOutputStream("save"));
						output.writeUTF(name);
						output.writeDouble(speed);
						output.close();//写入存档
					} catch (IOException e) 
					{
						e.printStackTrace();
					}
				break;
			}
		}
	}
	public void remover(Pane p,ImageView im)//判定是否击中方块并设击中的方块为不可见
	{
		if(p.getChildren().size()>0)
		{
			double x=p.getChildren().get(0).getTranslateY()-520;
			if(x>=0&&x<=70)
			{
				((Rectangle)p.getChildren().get(0)).setVisible(false);
				s=(int)(s+200-x);
				TitleC.fadePlay(Duration.seconds(0.2),im);
			}
			else
			{
				s=s-50;//扣分
			}
			score.setText(String.valueOf(s));
		}
		
	}
	class KeyInput implements EventHandler<KeyEvent>
	{
		public void handle(KeyEvent event)//将键盘分为四部分，监测输入以判断相应pane上的方块
		{
			switch(event.getCode())
			{
				case Q: case A: case Z: case W: case S: case X:
					remover(p1,effect1);
				break;
				case E: case D: case C: case R: case F: case V:
					remover(p2,effect2);
				break;
				case T: case G: case B: case Y: case H: case N:
					remover(p3,effect3);
				break;
				case U: case J: case M: case I: case K: case L:
					remover(p4,effect4);
				break;
				case BACK_SPACE://输入退格键暂停
					TitleC.click.stop();
					TitleC.click.play();
					t1.pause();
					t2.pause();
					t3.pause();
					t4.pause();
					bgm.pause();
				break;
				case ENTER://输入回车键继续
					TitleC.click.stop();
					TitleC.click.play();
					t1.play();
					t2.play();
					t3.play();
					t4.play();
					bgm.play();
				break;
				
				default:
				break;
			}
		}
	}
	class StopAll implements Runnable
	{
		public void run() 
		{
			t1.stop();
			t2.stop();
			t3.stop();
			t4.stop();
		}
	}
	public void start() throws IOException
	{
		primaryStage.setScene(scene);
		bgm.play();
		bgm.setOnEndOfMedia(new StopAll());//音乐结束，动画也结束
		t1.play();
		t2.play();
		t3.play();
		t4.play();
	}	
}