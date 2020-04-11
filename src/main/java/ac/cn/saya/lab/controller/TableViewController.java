package ac.cn.saya.lab.controller;

import ac.cn.saya.lab.GUIApplication;
import ac.cn.saya.lab.entity.UserEntity;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Title: TableViewController
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author Administrator
 * @Date: 2020/3/31 0031 11:19
 * @Description:
 */

public class TableViewController implements Initializable {

    private GUIApplication mainApp;

    /**
     * 表格
     */
    @FXML
    private TableView<UserEntity> dataTableView;

    /**
     * 数据
     */
    ObservableList<UserEntity> data = FXCollections.observableArrayList();

    /**
     * name列
     */
    @FXML
    TableColumn<UserEntity, String> account;

    /**
     * name
     */
    @FXML
    TableColumn<UserEntity, String> name;

    /**
     * roleName
     */
    @FXML
    TableColumn<UserEntity, String> roleName;

    /**
     * createTime
     */
    @FXML
    TableColumn<UserEntity, String> createTime;

    @FXML
    private Pagination pagination;

    /**
     * 获取主控制器的引用
     */
    public void setMainApp(GUIApplication mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * 界面打开后的初始化操作
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        account.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("account"));
        name.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("name"));
        roleName.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("roleName"));
        createTime.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("createTime"));
        dataTableView.setItems(data);
        Platform.runLater(()-> initData());
    }

    /**
     * 初始化表格数据
     */
    private void initData() {
        data.add(new UserEntity("saya", "saya", "team", "2020-3-31 12:01:16"));
        data.add(new UserEntity("saya", "saya", "team", "2020-3-31 12:01:16"));
        data.add(new UserEntity("saya", "saya", "team", "2020-3-31 12:01:16"));
    }
}
