package ac.cn.saya.lab.controller;

import ac.cn.saya.lab.GUIApplication;
import ac.cn.saya.lab.tools.DateUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Title: HomeViewController
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author Administrator
 * @Date: 2020/4/3 0003 17:17
 * @Description:
 */

public class HomeViewController implements Initializable {

    private GUIApplication mainApp;

    /**
     * 用户头像
     */
    @FXML
    private Label userLogo;

    /**
     * 平台欢迎信息
     */
    @FXML
    private Label userHelloText;

    /**
     * 平台欢迎信息-邮箱
     */
    @FXML
    private Label userEmailText;

    /**
     * 平台欢迎信息-上次操作明细
     */
    @FXML
    private Label userLastActionText;

    /**
     * 今日安排
     */
    @FXML
    private Label todayPlan;

    /**
     * 日历-月
     */
    @FXML
    private Label calendarMonth;

    /**
     * 日历-年
     */
    @FXML
    private Label calendarDay;

    /**
     * 页面名
     */
    @FXML
    private Label pageName;

    /**
     * 子页面
     */
    @FXML
    private AnchorPane childPane;

    /**
     * 当前页面
     */
    private String thisPane;

    /**
     * 获取主控制器的引用
     */
    public void setMainApp(GUIApplication mainApp) {
        this.mainApp = mainApp;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userLogo.setGraphic(new ImageView(new Image(GUIApplication.class.getResourceAsStream("/images/logo.png"),114,114,false,false)));
        userHelloText.setText("晚上好！刘能凯");
        userEmailText.setText("我的邮箱：saya@saya.ac.cn");
        userLastActionText.setText("最后一次操作：2020-04-10 22:39:14 四川成都");
        todayPlan.setText("复习JVM");
        // 设置日历
        calendarMonth.setText(DateUtils.getCurrentMonth());
        calendarDay.setText(DateUtils.getCurrentDay());
        // 设置页面名
        pageName.setText("主页");
        // 页面默认打开主页
        pageSwitch("page/general.fxml");
    }

    /**
     * 跳转到平台总览
     * @return
     */
    public void handleSwitchToGeneralAction(MouseEvent event){
        pageSwitch("page/general.fxml");
    }

    /**
     * 跳转到财务流水
     * @return
     */
    public void handleSwitchToTransactionAction(MouseEvent event){
        pageSwitch("page/transaction.fxml");
    }

    /**
     * 跳转到申报页面
     * @return
     */
    public void handleSwitchToDeclareAction(MouseEvent event){
        pageSwitch("page/declare.fxml");
    }


    /**
     * @Title 页面场景切换
     * @Params  [pagePath]
     * @Return  void
     * @Author  saya.ac.cn-刘能凯
     * @Date  2020-04-11
     * @Description
     */
    private void pageSwitch(String pagePath){
        if (null != thisPane && thisPane.equals(pagePath)){
            // 如果当前页面已经打开，则不执行任何操作
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource(pagePath));
            AnchorPane pane = loader.load();
            pane.setPrefWidth(childPane.getWidth());
            pane.setPrefHeight(childPane.getHeight());
            ObservableList<Node> page = childPane.getChildren();
            if (page.size() > 0){
                // 这里不能用remove
                childPane.getChildren().clear();
            }
            childPane.getChildren().add(pane);
            // 保存当前页面路径
            thisPane = pagePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
