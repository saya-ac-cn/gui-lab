package ac.cn.saya.lab.control;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @Title: ProgressFrom
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author saya
 * @Date: 2020/9/26 09:43
 * @Description:
 */

public class ProgressFrom {

    private Stage dialogStage;
    private ProgressIndicator progressIndicator;

    public ProgressFrom(final Task<?> task, Stage primaryStage) {

        dialogStage = new Stage();
        progressIndicator = new ProgressIndicator();

        // 窗口父子关系
        dialogStage.initOwner(primaryStage.getOwner());
        dialogStage.initStyle(StageStyle.UNDECORATED);
        dialogStage.initStyle(StageStyle.TRANSPARENT);
        dialogStage.initModality(Modality.APPLICATION_MODAL);

        // progress bar
        Label label = new Label("数据加载中, 请稍后...");
        label.setTextFill(Color.rgb(91,49,125));
        label.setPrefWidth(120);
        label.setPrefHeight(20);
        // 不确定
        progressIndicator.setProgress(-1F);
        progressIndicator.progressProperty().bind(task.progressProperty());
        progressIndicator.setPrefWidth(50);
        progressIndicator.setPrefHeight(50);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setBackground(Background.EMPTY);
        vBox.getChildren().addAll(progressIndicator,label);

        dialogStage.setWidth(120);
        dialogStage.setHeight(60);

        Scene scene = new Scene(vBox);
        scene.setFill(null);
        dialogStage.setScene(scene);

        // 在所在的stage居中
        dialogStage.setX(primaryStage.getX() + (primaryStage.getWidth()-dialogStage.getWidth()) / 2);
        dialogStage.setY(primaryStage.getY() + (primaryStage.getHeight()-dialogStage.getHeight()) / 2);

        Thread inner = new Thread(task);
        inner.start();

        task.setOnSucceeded(event -> {
            dialogStage.close();
        });
    }

    public void activateProgressBar() {
        dialogStage.show();
    }

    public Stage getDialogStage(){
        return dialogStage;
    }

    public void cancelProgressBar() {
        dialogStage.close();
    }

}
