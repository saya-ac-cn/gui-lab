package ac.cn.saya.lab;

import ac.cn.saya.lab.tools.NoticeUtils;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import javax.swing.*;

/**
 * @Title: AlertTest
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-05-30 20:01
 * @Description:
 */

public class AlertTest {

    public static void main(String[] args) {
        //JOptionPane.showMessageDialog(null, "标题【出错啦】", "年龄请输入数字", JOptionPane.ERROR_MESSAGE);
        Platform.runLater(() -> {
            NoticeUtils.show("12","12");
        });
    }

}
