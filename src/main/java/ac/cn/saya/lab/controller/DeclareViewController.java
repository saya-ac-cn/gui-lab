package ac.cn.saya.lab.controller;

import ac.cn.saya.lab.GUIApplication;
import ac.cn.saya.lab.api.RequestUrl;
import ac.cn.saya.lab.entity.DealDirection;
import ac.cn.saya.lab.entity.TransactionInfoEntity;
import ac.cn.saya.lab.entity.TransactionListEntity;
import ac.cn.saya.lab.entity.TransactionTypeEntity;
import ac.cn.saya.lab.tools.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.geometry.Pos.CENTER_LEFT;

/**
 * @Title: DeclareViewController
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-04-11 13:35
 * @Description: 财政申报
 */

public class DeclareViewController implements Initializable {

    /**
     * 交易类别
     */
    @FXML
    private ChoiceBox<TransactionTypeEntity> dealType;

    /**
     * 交易时间
     */
    @FXML
    private DatePicker tradeDate;

    /**
     * 交易摘要
     */
    @FXML
    private TextField summaryText;

    /**
     * 摘要明细容器
     */
    @FXML
    private VBox summaryInfoVbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 页面选择器初始化
        dealType.getItems().addAll(SingValueTools.getDealType());
        dealType.converterProperty().set(new TransactionTypeChoiceBox());
        //默认选中第一个选项
        dealType.getSelectionModel().selectFirst();
        // 设置开始时间
        tradeDate.setValue(LocalDate.now());
        tradeDate.setConverter(new DateConverter());
        StringConverter<LocalDate> converter = new DateConverter();
        tradeDate.setConverter(converter);
        // 构造第0行
        addSummaryLineHandleAction();
        summaryText.setTextFormatter(InputFormatter.stringLengthFormatte());
    }

    /**
     * 添加一行交易
     */
    public void addSummaryLineHandleAction() {
        // 事先判断现有的数量
        ObservableList<Node> children = summaryInfoVbox.getChildren();
        int nodeSize = children.size();
        if (nodeSize < 10){
            summaryInfoVbox.getChildren().add(nodeSize,addSummaryLine(nodeSize));
        }else {
            NoticeUtils.show("提示","交易流水明细最多只能有10条，超过部分请分拆！");
        }
    }

    /**
     * 添加一行交易过程
     * @param index 行号
     * @return
     */
    public Node addSummaryLine(int index) {
        // 构建新的额一行容器，并设置样式
        HBox newLine = new HBox();
        newLine.setSpacing(16);
        newLine.setAlignment(CENTER_LEFT);
        // 构造出入类型组件
        ChoiceBox<DealDirection> dealDirection = new ChoiceBox();
        dealDirection.getStyleClass().add("choice-box");
        dealDirection.getItems().addAll(SingValueTools.getDealDirection());
        dealDirection.getSelectionModel().selectFirst();
        // 构造金额输入框
        TextField chargeField = new TextField("0.0");
        chargeField.getStyleClass().add("general-input");
        chargeField.setPrefWidth(105);
        chargeField.setTextFormatter(InputFormatter.doubleFormatte());
        // 构造说明输入框
        TextField descField = new TextField();
        descField.getStyleClass().add("general-input");
        descField.setPrefWidth(240);
        descField.setTextFormatter(InputFormatter.stringLengthFormatte());

        // 按顺序加入到HBox
        newLine.getChildren().add(new Label("出入类型"));
        newLine.getChildren().add(dealDirection);
        newLine.getChildren().add(new Label("金额"));
        newLine.getChildren().add(chargeField);
        newLine.getChildren().add(new Label("详情"));
        newLine.getChildren().add(descField);
        // 构造移除按钮 第 1 行 不需要
        if (0 != index){
            Button removeButton = new Button("移除第 "+(index+1)+" 条");
            removeButton.getStyleClass().add("removeButton");
            removeButton.setGraphic(new ImageView(new Image(GUIApplication.class.getResourceAsStream("/images/icon-delete.png"),20,20,true,false)));
            // 绑定移除事件
            removeButton.setOnMouseClicked(event -> {
                Button button = (Button) event.getSource();
                int lineNum = StringUtils.getButtonIndex(button.getText());
                if (-1 != lineNum){
                    // 执行移除
                    summaryInfoVbox.getChildren().remove(lineNum-1);
                    refreshButtonIndex();
                }
            });
            newLine.getChildren().add(removeButton);
        }
        return newLine;
    }

    /**
     * 重新绘制按钮的序号
     */
    private void refreshButtonIndex(){
        ObservableList<Node> children = summaryInfoVbox.getChildren();
        HBox node;
        Button button;
        try {
            for (int index = 1;index < children.size();index++){
                node = (HBox)children.get(index);
                button = (Button) node.getChildren().get(6);
                button.setText("移除第 "+(index+1)+" 条");
            }
        } catch (Exception e) {
            System.out.println("重新绘制按钮的序号异常");
            e.printStackTrace();
        }
    }

    /**
     * 用户提交事件
     */
    public void handleSubmitActin(){
        JSONObject data = checkData();
        boolean err = data.containsKey("err");
        if (err){
            NoticeUtils.show("提示",data.getString("err"));
        }else {
            Result<Object> requestResult = RequestUrl.applyTransaction(data);
            if (ResultUtil.checkSuccess(requestResult)){
                NoticeUtils.show("处理结果","申报成功");
                // 还原表单
                //默认选中第一个选项
                dealType.getSelectionModel().selectFirst();
                // 设置开始时间
                tradeDate.setValue(LocalDate.now());
                summaryText.setText(null);
                summaryInfoVbox.getChildren().clear();
                // 构造第0行
                addSummaryLineHandleAction();
            }else {
                NoticeUtils.show("处理结果",requestResult.getMsg());
            }
        }
    }

    /**
     * 校验用户数据
     */
    private JSONObject checkData(){
        ObservableList<Node> children = summaryInfoVbox.getChildren();
        HBox node;
        ChoiceBox<DealDirection> choiceBox;
        TextField chargeField,descField;
        JSONObject para = new JSONObject();
        try {
            // 用户选中的支付方式
            Integer _dealType = dealType.getSelectionModel().getSelectedItem().getId();
            if (-1 == _dealType){
                para.put("err","请选择支付方式");
                return para;
            }
            para.put("tradeType",_dealType);
            String _summaryTextText = summaryText.getText();
            if (StringUtils.isBlank(_summaryTextText.trim())){
                para.put("err","请填写摘要");
                return para;
            }
            para.put("transactionAmount",_summaryTextText);
            JSONArray array = new JSONArray();
            for (int index = 0;index < children.size();index++){
                node = (HBox)children.get(index);
                choiceBox = (ChoiceBox<DealDirection>) node.getChildren().get(1);
                // 得到选中的数据
                // choiceBox.getSelectionModel().getSelectedItem().getId();
                // 校验金额是否合法
                chargeField = (TextField) node.getChildren().get(3);
                if (!InputFormatter.isNumber(chargeField.getText().trim())){
                    para.put("err","您填写的金额不合法");
                    return para;
                }
                // 校验描述是否合法
                descField = (TextField) node.getChildren().get(5);
                if (StringUtils.isBlank(descField.getText().trim())){
                    para.put("err","请填写正确描述");
                    return para;
                }
                array.add(JSON.toJSON(new TransactionInfoEntity(choiceBox.getSelectionModel().getSelectedItem().getType(),new BigDecimal(chargeField.getText().trim()),descField.getText().trim())));
            }
            para.put("tradeDate",tradeDate.getValue().format(DateUtils.dateFormat));
            para.put("infoList",array);
            return para;
        } catch (Exception e) {
            para.put("err","校验异常");
            e.printStackTrace();
        }
        return para;
    }


}
