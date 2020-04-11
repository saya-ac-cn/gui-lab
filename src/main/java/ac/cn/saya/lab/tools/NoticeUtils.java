package ac.cn.saya.lab.tools;

import javafx.scene.control.Alert;

/**
 * @Title: NoticeUtils
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author Administrator
 * @Date: 2020/3/31 0031 09:16
 * @Description: 参考1：https://blog.csdn.net/qq_26954773/article/details/78215554
 */

public class NoticeUtils {

    private static Alert alert;

    //弹出一个信息对话框
    public static void show(Alert.AlertType type,String title, String mmessage){
        alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(mmessage);
        alert.showAndWait();
    }

}
