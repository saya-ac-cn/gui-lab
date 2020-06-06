package ac.cn.saya.lab.tools;

import ac.cn.saya.lab.GUIApplication;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @Title: NoticeUtils
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author Administrator
 * @Date: 2020/3/31 0031 09:16
 * @Description: 参考1：https://blog.csdn.net/qq_26954773/article/details/78215554
 */

public class NoticeUtils {

    //弹出一个确认对话框
    public static void confirm(Stage stage,String title, String message, EventHandler confirmEvent){
        //Stage stage = new Stage();
        stage.setWidth(400);
        stage.setHeight(160);
        stage.setTitle(title);
        stage.getIcons().add(new Image(GUIApplication.class.getResourceAsStream("/images/system.png")));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.TRANSPARENT);

        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background:transparent;");

        // 绘制标题
        Label titleLeble = new Label(title);
        titleLeble.setPadding(new Insets(10,0,0,10));
        titleLeble.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD,16));
        titleLeble.setTextFill(Paint.valueOf("#467cfd"));

        // 绘制正文
        Label contentLeble = new Label(message);
        contentLeble.setFont(Font.font(14));
        contentLeble.setTextFill(Paint.valueOf("#467cfd"));

        // 绘制关闭按钮
        Label closeButton = new Label("关闭");
        closeButton.setOnMouseClicked(e -> stage.close());
        closeButton.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD,14));
        closeButton.setTextFill(Paint.valueOf("#467cfd"));
        closeButton.setBackground(Background.EMPTY);
        closeButton.setPadding(new Insets(0,0,8,0));

        Label submitButton = new Label("确定");
        submitButton.setOnMouseClicked(confirmEvent);
        submitButton.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD,14));
        submitButton.setTextFill(Paint.valueOf("#467cfd"));
        submitButton.setBackground(Background.EMPTY);
        submitButton.setPadding(new Insets(0,10,8,0));

        HBox box = new HBox();
        box.setSpacing(10);
        box.getChildren().add(closeButton);
        box.getChildren().add(submitButton);
        box.setAlignment(Pos.CENTER_RIGHT);

        pane.setTop(titleLeble);
        pane.setCenter(contentLeble);
        pane.setBottom(box);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        // 设置背景透明
        scene.setFill(Paint.valueOf("rgba(241,250,250,0.65)"));
        stage.show();
    }

    /**
     * 得到一个消息提示框
     * @param title
     * @param message
     */
    public static void show(String title , String message){
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.getIcons().add(new Image(GUIApplication.class.getResourceAsStream("/images/system.png")));
        //modality要使用Modality.APPLICATION_MODEL
        stage.initModality(Modality.APPLICATION_MODAL);
        // 没有最小化 最大化按钮 只有关闭按钮
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setWidth(400);
        stage.setHeight(160);


        // 绘制标题
        Label titleLeble = new Label(title);
        titleLeble.setPadding(new Insets(10,0,0,10));
        titleLeble.setTextFill(Paint.valueOf("#467cfd"));
        titleLeble.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD,16));


        // 绘制正文
        Label contentLeble = new Label(message);
        contentLeble.setTextFill(Paint.valueOf("#467cfd"));
        contentLeble.setFont(Font.font(14));

        // 绘制关闭按钮
        Label closeButton = new Label("关闭");
        closeButton.setOnMouseClicked(e -> stage.close());
        closeButton.setFont(Font.font("Microsoft YaHei", FontWeight.BOLD,14));
        closeButton.setTextFill(Paint.valueOf("#467cfd"));
        closeButton.setBackground(Background.EMPTY);
        closeButton.setPadding(new Insets(0,10,8,0));
        HBox box = new HBox(closeButton);
        box.setAlignment(Pos.CENTER_RIGHT);

        BorderPane pane = new BorderPane();
        pane.setStyle("-fx-background:transparent;");
        pane.setTop(titleLeble);
        pane.setCenter(contentLeble);
        pane.setBottom(box);


        Scene scene = new Scene(pane);
        stage.setScene(scene);
        // 设置背景透明
        scene.setFill(Paint.valueOf("rgba(241,250,250,0.65)"));
        stage.show();
    }

}
