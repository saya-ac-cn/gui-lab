package ac.cn.saya.lab.controller;

import ac.cn.saya.lab.GUIApplication;
import ac.cn.saya.lab.api.RequestUrl;
import ac.cn.saya.lab.entity.LogEntity;
import ac.cn.saya.lab.entity.PlanEntity;
import ac.cn.saya.lab.entity.UserEntity;
import ac.cn.saya.lab.tools.DateUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
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

    /**
     * 用户登录成功后，保存的数据
     */
    private JSONObject userData;

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
    private Label userGreetText;

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
     * 初始化界面用户信息
     * @param value
     */
    public void setUserData(JSONObject value){
        if (null == value){
            // 获取用户数据失败，显示默认
            userLogo.setGraphic(new ImageView(new Image(GUIApplication.class.getResourceAsStream("/images/logo.png"),114,114,false,false)));
            userHelloText.setText(DateUtils.getNowHourHello()+"好！未知用户");
            userGreetText.setText(DateUtils.getGreetText());
            userLastActionText.setText("最后一次操作：");
            todayPlan.setText("没有安排");
        }else {
            this.userData = value;
            JSONObject user = (JSONObject)this.userData.getOrDefault("user",null);
            JSONArray plan = (JSONArray) this.userData.getOrDefault("plan",null);
            JSONObject log = (JSONObject) this.userData.getOrDefault("log",null);
            if (null == user || user.isEmpty()){
                userLogo.setGraphic(new ImageView(new Image(GUIApplication.class.getResourceAsStream("/images/logo.png"),114,114,false,false)));
                userHelloText.setText(DateUtils.getNowHourHello()+"好！未知用户");
            }else {
                userLogo.setGraphic(new ImageView(new Image(RequestUrl.prefixUrh+user.getString("logo"),114,114,false,false)));
                userHelloText.setText(DateUtils.getNowHourHello()+"好！"+user.getString("user"));
            }
            if (null == log || log.isEmpty()){
                userLastActionText.setText("Hi，这是您第一次使用吧？愿您有个好的开始！");
            }else {
                userLastActionText.setText("最后一次操作："+log.getString("date")+" "+log.getString("city"));
            }
            if (null == plan || plan.isEmpty()){
                todayPlan.setText("无安排");
            }else {
                todayPlan.setText(((JSONObject)plan.get(0)).getString("describe"));
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 用户欢迎语
        userGreetText.setText(DateUtils.getGreetText());
        // 设置日历
        calendarMonth.setText(DateUtils.getCurrentMonth());
        calendarDay.setText(DateUtils.getCurrentDay());
        // 页面默认打开主页
        pageSwitch("page/general.fxml","主页");
    }

    /**
     * 跳转到平台总览
     * @return
     */
    public void handleSwitchToGeneralAction(MouseEvent event){
        pageSwitch("page/general.fxml","主页");
    }

    /**
     * 跳转到财务流水
     * @return
     */
    public void handleSwitchToTransactionAction(MouseEvent event){
        pageSwitch("page/transaction.fxml","财务流水");
    }

    /**
     * 跳转到申报页面
     * @return
     */
    public void handleSwitchToDeclareAction(MouseEvent event){
        pageSwitch("page/declare.fxml","财务申报");
    }

    /**
     * 跳转到日度报表
     * @return
     */
    public void handleSwitchToTransactionForDayAction(MouseEvent event){
        pageSwitch("page/transactionForDay.fxml","日度报表");
    }

    /**
     * 跳转到月度报表
     * @return
     */
    public void handleSwitchToTransactionForMonthAction(MouseEvent event){
        pageSwitch("page/transactionForMonth.fxml","月度报表");
    }

    /**
     * 跳转到年度报表
     * @return
     */
    public void handleSwitchToTransactionForYearAction(MouseEvent event){
        pageSwitch("page/transactionForYear.fxml","年度报表");
    }

    /**
     * @Title 页面场景切换
     * @Params  [pagePath, pageTielt]
     * @Return  void
     * @Author  saya.ac.cn-刘能凯
     * @Date  2020-04-12
     * @Description
     */
    private void pageSwitch(String pagePath,String pageTielt){
        if (null != thisPane && thisPane.equals(pagePath)){
            // 如果当前页面已经打开，则不执行任何操作
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource(pagePath));
            AnchorPane pane = loader.load();
            if (childPane.getWidth() != 0.0 && childPane.getHeight() != 0.0){
                pane.setPrefWidth(childPane.getWidth());
                pane.setPrefHeight(childPane.getHeight());
            }else {
                /// pane.setPrefWidth(1246.0);
                /// pane.setPrefHeight(508.0);
                // 启动后第一个页面没有尺寸，这里需要预先指定
                pane.setPrefWidth(childPane.getMinWidth());
                pane.setPrefHeight(childPane.getMinHeight());
            }
            ObservableList<Node> page = childPane.getChildren();
            // 第一次打开为空
            if (page.size() > 0){
                // 这里不能用remove
                childPane.getChildren().clear();
            }
            childPane.getChildren().add(pane);
            // 设置页面名
            pageName.setText(pageTielt);
            // 保存当前页面路径
            thisPane = pagePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
