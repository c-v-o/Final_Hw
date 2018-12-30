package enter.Gui;

import enter.Factions.AllCharacters;
import enter.Map.Positions;
import enter.Strategies.ZFs;
import javafx.application.Application;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.net.URL;

//在别的包里，用一个类将所有的人物装起来，并在其中定义让人物动起来的方法，即启动各个线程的方法
//然后在main或者control类中实例化这个类，并调用这个方法启动各个线程，并获取各个人物的坐标在地图上绘制图片
public class Main extends Application {
    private Controller controller;

    enum Mode{
        NORMAL,
        REP
    }

    private Mode mode = Mode.REP;

    private AllCharacters allcharacters;


    @Override
    public void start(Stage primaryStage) throws Exception {


        URL url = getClass().getClassLoader().getResource("layout.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(url);
        //fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        BorderPane root = fxmlLoader.load();



        Scene scene = new Scene(root, 600, 600);
        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        root.setTop(menuBar);

        Menu fileMenu = new Menu("File");
        MenuItem loadMenuItem = new MenuItem("Load Log");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/main/java/replay"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML","*.xml"));
        loadMenuItem.setOnAction(event -> {
            fileChooser.setTitle("Load Log");
            File replayFile = fileChooser.showOpenDialog(primaryStage);
            //mode = Mode.REP;
            controller.inputBattlelog(replayFile);
            controller.initReplayThread();
        });

        MenuItem saveMenuItem = new MenuItem("Save Log");
        saveMenuItem.setOnAction((event) -> {
            fileChooser.setTitle("Save Log");
            File file = fileChooser.showSaveDialog(primaryStage);
            if (file != null && mode == Mode.NORMAL) {
                controller.outputBattlelog(file);
            }
        });
        fileMenu.getItems().addAll(loadMenuItem,saveMenuItem);

        Menu zfMenu = new Menu("阵形选择");
        Menu cbZF = new Menu("葫芦方");
        Menu demonZF = new Menu("妖精方");
        zfMenu.getItems().addAll(cbZF,demonZF);
        MenuItem cbCs = new MenuItem("长蛇");
        MenuItem cbHy = new MenuItem("鹤翼");
        MenuItem cbFm = new MenuItem("方门");
        MenuItem demonCs = new MenuItem("长蛇");
        MenuItem demonHy = new MenuItem("鹤翼");
        MenuItem demonYl = new MenuItem("鱼鳞");
        cbZF.getItems().addAll(cbCs,cbHy,cbFm);
        demonZF.getItems().addAll(demonCs,demonHy,demonYl);

        cbCs.setOnAction((event)->{
            allcharacters.getZfs().lineUp(ZFs.ZF.ChangShe,"葫芦娃");
            allcharacters.changeZF();
            controller.addChildren();
        });
        demonCs.setOnAction((event)->{
            allcharacters.getZfs().lineUp(ZFs.ZF.ChangShe,"妖精");
            allcharacters.changeZF();
            controller.addChildren();
        });

        cbHy.setOnAction((event)->{
            allcharacters.getZfs().lineUp(ZFs.ZF.HeYi,"葫芦娃");
            allcharacters.changeZF();
            controller.addChildren();
        });
        demonHy.setOnAction((event)->{
            allcharacters.getZfs().lineUp(ZFs.ZF.HeYi,"妖精");
            allcharacters.changeZF();
            controller.addChildren();
        });

        cbFm.setOnAction((event)->{
            allcharacters.getZfs().lineUp(ZFs.ZF.FangMen,"葫芦娃");
            allcharacters.changeZF();
            controller.addChildren();
        });

        demonYl.setOnAction((event)->{
            allcharacters.getZfs().lineUp(ZFs.ZF.YuLin,"妖精");
            allcharacters.changeZF();
            controller.addChildren();
        });
        menuBar.getMenus().addAll(fileMenu,zfMenu);




        this.controller = fxmlLoader.getController(); //获取Controller的实例对象
        //传递primaryStage，scene参数给Controller
        controller.setStage(primaryStage);
        controller.setScene(scene);

        //创建刷新类实例并把controller实例对象传给它
        UpdateUi updateui = new UpdateUi(controller);
        //将刷新类实例传给controller实例
        controller.setUpdateui(updateui);
        //创建地图实例
        Positions positions = new Positions();

        //创建人物类
        allcharacters = new AllCharacters(positions);
        controller.setCbs(allcharacters.getCbs());
        updateui.setCbs(allcharacters.getCbs());
        controller.setDemons(allcharacters.getDemons());
        updateui.setDemons(allcharacters.getDemons());
        updateui.setEndflag(allcharacters.getEndflag());
        updateui.setAllCharacters(allcharacters);
        controller.setAllCharacters(allcharacters);


        //创建锁管理并发线程
        Object lock = new Object();
        allcharacters.setLock(lock);
        updateui.setLock(lock);
        controller.setLock(lock);

        //创建次序对象管理并发顺序
        Turn turn = new Turn(0);
        allcharacters.setTurn(turn);
        updateui.setTurn(turn);
        controller.setTurn(turn);

        //创建线程
        controller.initUpdateUiThread();
        allcharacters.getCbs().initThreads();
        allcharacters.getDemons().initThreads();
        //controller.initReplayThread();

        controller.addChildren();
        //键盘事件
        scene.setOnKeyPressed((event)->{
            if (event.getCode() == KeyCode.SPACE) {
                controller.addChildren();
                controller.startThread();
                mode = Mode.NORMAL;

            }
            else if(event.getCode() == KeyCode.R){
                controller.startReplayThread();
                //mode = Mode.REP;
            }

        });

        Replay replay = new Replay();
        controller.setReplay(replay);
        replay.setController(controller);

        primaryStage.setTitle("葫芦兄弟 vs 妖精");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    @Override
    public void stop(){
        if(mode == Mode.NORMAL)
            controller.outputBattlelog(new File("src/replay/default.xml"));

    }


    public static void main(String[] args) {
        launch(args);
    }
}
