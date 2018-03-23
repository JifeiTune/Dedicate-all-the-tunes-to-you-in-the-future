# Dedicate-all-the-tunes-to-you-in-the-future
游戏中使用的插图与音乐皆非原创，之后会注明出处。
--------------------------------------------------------------------------------------------------------------------------------

国内JavaFx的教程是真的少，当时学起来挺艰难的，许多实现也得自己去查源码。我水平较菜，代码中肯定有无数需要优化的地方。最近在学git，所以传上来。
主界面
![主界面](https://github.com/JifeiTune/Dedicate-all-the-tunes-to-you-in-the-future/blob/master/Illustration/title.png)
选择界面
![选择界面](https://github.com/JifeiTune/Dedicate-all-the-tunes-to-you-in-the-future/blob/master/Illustration/select.png)
游戏界面与玩法
![游戏界面与玩法](https://github.com/JifeiTune/Dedicate-all-the-tunes-to-you-in-the-future/blob/master/Illustration/play.png)
操作键
![操作键](https://github.com/JifeiTune/Dedicate-all-the-tunes-to-you-in-the-future/blob/master/Illustration/key.jpg)
注：请务必开启声音！谢谢。

1.因为个人非常喜欢纯音乐，再加上图片等素材比较多，所以做了一个Galgame风格的音游，媒体资源比较丰富。

2.使用fxml文件布局，结合Scene Builder的使用（略坑，前两个界面使用了fxml，主界面使用中出现bug，调了5个小时无果，放弃，纯写代码）。

3.fxml的使用方法：每个fxml布局，设置一个类作为controller，对于需要显示使用的在fxml文件中创建的元素或方法，使用@FXML，在controller类中标记。
  导入fxml中的布局，FXMLLoader.load(类名.class.getResource("fxml文件路径"));（静态方法中）。  controller类可考虑实现Initializable接口，重写initialize方法，用于对界面初始化，在fxml文件加载好后会自动调用此方法。

4.界面跳转，同一个Stage，不同界面替换不同的scene。

5.前两个界面相对固定，几乎都使用静态数据成员和成员方法。

6.主界面，四个等大的pane放在一个背景pane上（一开始使用HBox与VBox，但因为它们各自的特点显示效果出现了严重问题，遂用最原始的pane），每一个pane对应一个Timeline动画，实现方块下落。

7.前面的工作虽然繁琐，但都只是些搬砖活，这里介绍一下动画部分。
  对于每一个Timeline动画，设置KeyFrame为每隔2秒（初始）调用一次addR()方法，addR()方法中会有setRate()方法根据每首曲子的速度来随机设置Timeline的速度，addR()方法的主要功能即为对应的pane添加一个方块下落的动画PathTransition，每个方块设置了监听器，下落出判断线后会被自动地从所在pane移出。
  为第一个pane设置了键盘事件处理器，通过输入的键值（将字母类键分成了4部分，对应于4个pane）对应到相应的pane，然后判断pane的ObservableList中第一个方块的TranslateY（PathTransition动画中，元素变的是TranslateX与TranslateY），处于一定范围内就将该方块设为不可见，同时播放FadeTransition动画标志成功，并根据用户是否输入正确以及准确程度加减分，界面上会实时显示分数。
  游戏音乐使用setOnEndOfMedia方法，创建一个StopAll类实现Runnable接口，重写run方法，在音乐结束时调用run方法，结束所有Timeline动画。

8.特效的使用。
  位置变化通过改变TranslateX或TranslateY即可。
  使用setEffect方法设置Java中提供的特效：
  DropShadow，投影效果，用在确认是否结束游戏的窗口。
  InnerShadow，内阴影效果，用在下落的方块上。
  BoxBlur，均值模糊效果，用在选曲界面。
  GaussianBlur，高斯模糊效果，用在判断线上。
  



















