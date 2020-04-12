package ac.cn.saya.lab.controller;

import ac.cn.saya.lab.GUIApplication;
import ac.cn.saya.lab.assemb.AdvisorPagingAndDate;
import ac.cn.saya.lab.entity.TransactionTypeEntity;
import ac.cn.saya.lab.entity.UserEntity;
import ac.cn.saya.lab.tools.DateUtils;
import ac.cn.saya.lab.tools.PagingTools;
import ac.cn.saya.lab.tools.SingValueTools;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * @Title: TransactionViewController
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-04-11 12:39
 * @Description: 财务流水报表
 */

public class TransactionViewController extends AdvisorPagingAndDate implements Initializable {

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
    private TableColumn<UserEntity, String> operateCol;

    /**
     * 交易类别
     */
    @FXML
    public ChoiceBox<TransactionTypeEntity> dealType;

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
        dealType.getItems().addAll(SingValueTools.getDealType());
        dealType.getSelectionModel().selectFirst();//默认选中第一个选项
        initDatePicker();
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
        operateCol.setCellFactory((col) -> {
            TableCell<UserEntity, String> cell = new TableCell<UserEntity, String>() {

                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);

                    if (!empty) {
                        HBox box = new HBox();
                        box.setSpacing(10);
                        // 详情按钮
                        Label moreLabel = new Label();
                        ImageView moreIcon = new ImageView(new Image(GUIApplication.class.getResourceAsStream("/images/gengduo.png"),14,14,true,false));
                        moreLabel.setGraphic(moreIcon);
                        // 编辑按钮
                        Label editLabel = new Label();
                        ImageView editIcon = new ImageView(new Image(GUIApplication.class.getResourceAsStream("/images/bianji.png"),14,14,true,false));
                        editLabel.setGraphic(editIcon);
                        // 删除按钮
                        Label deleteLabel = new Label();
                        ImageView deleteIcon = new ImageView(new Image(GUIApplication.class.getResourceAsStream("/images/icon-delete.png"),14,14,true,false));
                        deleteLabel.setGraphic(deleteIcon);

                        box.getChildren().add(moreLabel);
                        box.getChildren().add(editLabel);
                        box.getChildren().add(deleteLabel);
                        this.setGraphic(box);
                        moreLabel.setOnMouseClicked(me -> {
                            new AlertBox().display("title", "message");
                        });
                        deleteLabel.setOnMouseClicked(me -> {
                            UserEntity user = this.getTableView().getItems().get(this.getIndex());
                            System.out.println("删除 " + user.getAccount() + " 的记录");
                        });
                    }
                }

            };
            return cell;
        });
        // 请求成功后更新当前页码 和 总页数
        pageIndex = pageNum;
        pageCount = 10;
        displayTable(true);
    }


    public void handlePageJump(ActionEvent event) {
        //event.getSource()获取一个Object对象 实际就是这个button 这里我们需要强制转行
        Button bu = (Button) event.getSource();
        // 即将要跳转的位置
        int newIndex = computeJumpNum(bu.getId());
        getData(newIndex);
    }

}
