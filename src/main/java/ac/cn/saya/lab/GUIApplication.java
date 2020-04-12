package ac.cn.saya.lab;

import ac.cn.saya.lab.controller.HomeViewController;
import ac.cn.saya.lab.controller.LoginViewController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * @Title: GUIApplication
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-03-30 21:55
 * @Description:
 * 常用操作Application：https://blog.csdn.net/qq_31384551/article/details/81254504?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task
 * 布局：https://blog.csdn.net/theonegis/article/details/50184811
 * 布局：https://www.cnblogs.com/pinlantu/p/11426828.html
 * AnchorPane设置子容器和父容器同等大小:https://blog.csdn.net/qq_41886200/article/details/98946299
 * 设置窗口可拖动：https://blog.csdn.net/CPOHUI/article/details/86447259
 */

public class GUIApplication extends Application {

    /**
     * 当前舞台
     */
    private Stage stage;

    /**
     * 当前
     */
    private Scene scene;

    // 定义偏移量，用于处理窗口移动
    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            stage  = primaryStage;
            //showLoginView();
            showHomeView();
            //showTableView();
            // 全局名字统一
            stage.setTitle("Home");
            // 设置窗口风格，去掉窗口修饰 TRANSPARENT——透明背景，没有操作系统平台装饰
            stage.initStyle(StageStyle.TRANSPARENT);
            // logo
            stage.getIcons().add(new Image(GUIApplication.class.getResourceAsStream("/images/system.png")));
            primaryStage.show();
            // 手动设置Stage的位置，注意在show()方法后面
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            primaryStage.setX((screenBounds.getWidth() - primaryStage.getWidth()) / 2);
            primaryStage.setY((screenBounds.getHeight() - primaryStage.getHeight()) / 2);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *	显示登录界面
     */
    public void showLoginView() {
        try {
            LoginViewController lgController = (LoginViewController)replaceSceneContent("page/login.fxml",false);
            lgController.setMainApp(this);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *	显示主页界面
     */
    public void showHomeView() {
        try {
            HomeViewController rpController = (HomeViewController)replaceSceneContent("page/home.fxml",true);
            rpController.setMainApp(this);
        }catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
     *	显示指定的视图
     */
    private Object replaceSceneContent(String fxmlFile,boolean resizable) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource(fxmlFile));
        AnchorPane ap = null;
        try {
            ap = loader.load();
        }catch(IOException e) {
            e.printStackTrace();
        }
        ap.setOnMousePressed(new EventHandler<MouseEvent>() {
            /*
             * 鼠标按下时，记下相对于 root左上角(0,0) 的 x, y坐标, 也就是x偏移量 = x - 0, y偏移量 = y - 0
             */
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        ap.setOnMouseDragged(new EventHandler<MouseEvent>() {
            /*
             * 根据偏移量设置primaryStage新的位置
             */
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
        scene = new Scene(ap);
        stage.setScene(scene);
        stage.setResizable(resizable);
        // 防止在页面切换后，窗口不居中
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
        return loader.getController();
    }

    /**
     *	获取scene
     */
    public Scene getScene() {
        return scene;
    }

    /**
     *	获取Stage
     */
    public Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        // 调用Application进行显示
        launch(args);
    }

}
