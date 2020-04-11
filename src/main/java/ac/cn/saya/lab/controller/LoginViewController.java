package ac.cn.saya.lab.controller;

import ac.cn.saya.lab.GUIApplication;
import ac.cn.saya.lab.api.RequestUrl;
import ac.cn.saya.lab.tools.NoticeUtils;
import ac.cn.saya.lab.tools.StringUtils;
import com.alibaba.fastjson.JSONObject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Title: LoginViewController
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-03-30 21:59
 * @Description:登录界面控制器
 */
//界面控制器必须实现自接口Initializable
public class LoginViewController implements Initializable {

    private GUIApplication mainApp;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField userNameField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label errorInfoLabel;


    /**
     * 获取主控制器的引用
     */
    public void setMainApp(GUIApplication mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * 界面打开后的初始化操作
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userNameLabel.setGraphic(new ImageView(new Image(GUIApplication.class.getResourceAsStream("/images/user.png"),20,20,false,false)));
        passwordLabel.setGraphic(new ImageView(new Image(GUIApplication.class.getResourceAsStream("/images/password.png"),20,20,false,false)));
    }

    /**
     * 响应登录事件
     * @return
     */
    public void handleLoginButtonAction(){
        if (StringUtils.isBlank( userNameField.getText()) || StringUtils.isBlank(passwordField.getText())){
            errorInfoLabel.setText("用户名或密码不能为空");
        }else {
            if ("saya".equals(userNameField.getText()) && "111111".equals(passwordField.getText())){
                //显示主界面
                mainApp.showHomeView();
            }else {
                errorInfoLabel.setText("用户名或密码错误");
                userNameField.setText(null);
                passwordField.setText(null);
            }
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("account",userNameField.getText());
//            jsonObject.put("password",passwordField.getText());
//            JSONObject result = RequestUrl.login(jsonObject);
//            if (RequestUrl.checkSuccess(result) && result.getInteger("resultCode") == 0){
//                //显示主界面
//                mainApp.showHomeView();
//            }else {
//                errorInfoLabel.setText(result.getString("msg"));
//                userNameField.setText(null);
//                passwordField.setText(null);
//            }
        }
    }

}
