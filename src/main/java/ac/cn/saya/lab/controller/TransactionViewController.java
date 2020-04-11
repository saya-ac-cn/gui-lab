package ac.cn.saya.lab.controller;

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
import javafx.scene.input.MouseEvent;
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

public class TransactionViewController extends PagingTools implements Initializable {

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

    /**
     * 交易类别
     */
    @FXML
    public ChoiceBox<TransactionTypeEntity> dealType;

    /**
     * 开始时间
     */
    @FXML
    private DatePicker beginTime;

    /**
     * 结束时间
     */
    @FXML
    private DatePicker endTime;

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
     * 初始化时间选择器
     */
    private void initDatePicker(){
        // 设置开始时间
        beginTime.setValue(LocalDate.now());
        final Callback<DatePicker, DateCell> endDatePickerFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isBefore(
                                        beginTime.getValue().plusDays(1))
                                ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #FFCCCC;");
                                }
                            }
                        };
                    }
                };
        // 为结束时间绑定事件，使之不能早于开始时间
        endTime.setDayCellFactory(endDatePickerFactory);
        // 设置结束时间为开始时间的下一天
        endTime.setValue(beginTime.getValue().plusDays(1));
        final Callback<DatePicker, DateCell> firstDatePickerFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);
                                if (item.isAfter(
                                        endTime.getValue().plusDays(1))
                                ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #FFCCCC;");
                                }
                            }
                        };
                    }
                };
        // 为开始时间绑定事件，使之不能晚于结束时间
        beginTime.setDayCellFactory(firstDatePickerFactory);
        StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return (DateUtils.dateFormat).format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, DateUtils.dateFormat);
                } else {
                    return null;
                }
            }
        };
        beginTime.setConverter(converter);
        endTime.setConverter(converter);
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


    public void handlePageJump(ActionEvent event) {
        //event.getSource()获取一个Object对象 实际就是这个button 这里我们需要强制转行
        Button bu = (Button) event.getSource();
        // 即将要跳转的位置
        int newIndex = computeJumpNum(bu.getId());
        getData(newIndex);
    }

}
