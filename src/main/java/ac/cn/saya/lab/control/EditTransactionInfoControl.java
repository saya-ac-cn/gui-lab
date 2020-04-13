package ac.cn.saya.lab.control;

import javafx.event.EventTarget;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Title: EditTransactionInfoControl
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author Administrator
 * @Date: 2020/4/13 0013 17:42
 * @Description: 修改交易明细
 */

public class EditTransactionInfoControl implements Initializable {

    // 定义偏移量，用于处理窗口移动
    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * 交易摘要
     */
    @FXML
    public TextField addDescField;

    /**
     * 底部关闭，提交按钮
     */
    @FXML
    public Button closeButton, submitButton;

    /**
     * 存储外部传递的资源
     */
    private Stage _stage;

    private Parent _root;

    /**
     * 对外接受资源
     *
     * @param stage
     */
    public void passedResource(Stage stage,Parent root) {
        _stage = stage;
        _root = root;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("init");
    }

    /**
     * 页面二次刷新，由外部触发，
     */
    public void secondRefresh() {
        _root.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        _root.setOnMouseDragged(event -> {
            _stage.setX(event.getScreenX() - xOffset);
            _stage.setY(event.getScreenY() - yOffset);
        });
        // 防止在页面切换后，窗口不居中
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        _stage.setX((screenBounds.getWidth() - _stage.getWidth()) / 2);
        _stage.setY((screenBounds.getHeight() - _stage.getHeight()) / 2);
    }

    public void handleClosePageAction(MouseEvent event) {
        // 调用父容器传进来的stage进行操作
        _stage.close();
    }

}
