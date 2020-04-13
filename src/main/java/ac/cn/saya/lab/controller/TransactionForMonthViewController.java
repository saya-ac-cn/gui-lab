package ac.cn.saya.lab.controller;

import ac.cn.saya.lab.assemb.AdvisorPagingAndDate;
import ac.cn.saya.lab.entity.UserEntity;
import ac.cn.saya.lab.tools.DateUtils;
import ac.cn.saya.lab.tools.PagingTools;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @Title: TransactionForMonthViewController
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-04-11 12:39
 * @Description: 月度报表
 */

public class TransactionForMonthViewController extends PagingTools implements Initializable {

    /**
     * 表格
     */
    @FXML
    public TableView<UserEntity> dataTableView;

    /**
     * 数据
     */
    public ObservableList<UserEntity> data = FXCollections.observableArrayList();

    /**
     * account
     */
    @FXML
    public TableColumn<UserEntity, String> account;

    /**
     * name
     */
    @FXML
    public TableColumn<UserEntity, String> name;

    /**
     * roleName
     */
    @FXML
    public TableColumn<UserEntity, String> roleName;

    /**
     * createTime
     */
    @FXML
    public TableColumn<UserEntity, String> createTime;




    @FXML
    public Button search;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        account.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("account"));
        name.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("name"));
        roleName.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("roleName"));
        createTime.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("createTime"));
        account.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("account"));
        name.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("name"));
        roleName.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("roleName"));
        createTime.setCellValueFactory(new PropertyValueFactory<UserEntity, String>("createTime"));
        dataTableView.setItems(data);
        Platform.runLater(() -> getData(1));
    }

    /**
     * 渲染表格
     *
     * @param pageNum 用户想要得到的页
     */
    private void getData(int pageNum) {
        // 数据查询
        data.clear();
        data.add(new UserEntity("saya", "saya", "team" + pageNum, "2020-3-31 12:01:16"));
        data.add(new UserEntity("saya", "saya", "team" + pageNum, "2020-3-31 12:01:16"));
        data.add(new UserEntity("saya", "saya", "team" + pageNum, "2020-3-31 12:01:16"));
        dataTableView.setItems(data);
        // 请求成功后更新当前页码 和 总页数
        pageIndex = pageNum;
        pageCount = 10;
        displayTable(true);
    }

    /**
     * 分页事件
     * @param event
     */
    public void handlePageJump(ActionEvent event) {
        //event.getSource()获取一个Object对象 实际就是这个button 这里我们需要强制转行
        Button bu = (Button) event.getSource();
        // 即将要跳转的位置
        int newIndex = computeJumpNum(bu.getId());
        getData(newIndex);
    }

}
