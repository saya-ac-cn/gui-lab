package ac.cn.saya.lab.tools;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * @Title: PagingTools
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author liunengkai
 * @Date: 2020-04-11 17:19
 * @Description: 分页组件
 */

public class PagingTools {

    /**
     * 当前页（显示从0开始）
     */
    @FXML
    public Label nowPageIndex;

    /**
     * 总页数
     */
    @FXML
    public Label totalPageNum;

    /**
     * 首页
     */
    @FXML
    public Button firstButton;

    /**
     * 前一页
     */
    @FXML
    public Button preButton;

    /**
     * 后一页
     */
    @FXML
    public Button postButton;

    /**
     * 尾页
     */
    @FXML
    public Button lastButton;

    /**
     * 当前页
     */
    public int pageIndex = 1;

    /**
     * 总页数
     */
    public int pageCount = 0;

    /**
     * 计算即将跳转到哪一页
     * @param jumpType 跳转类别
     * @return
     */
    public int computeJumpNum(String jumpType){
        if ("firstButton".equals(jumpType)){
            return 1;
        }else if ("lastButton".equals(jumpType)){
            return pageCount;
        }else if ("preButton".equals(jumpType)){
            return ((pageIndex-1)>=1)?pageIndex-1:0;
        }else if ("postButton".equals(jumpType)){
            return ((pageIndex+1)<=pageCount)?pageIndex+1:pageCount;
        }else {
            return 1;
        }
    }

    /**
     * 渲染分页控件
     *
     * @param flog，为true正常显示，为false（异常或无数据时），不可分页
     */
    public void displayTable(boolean flog) {
        if (!flog) {
            pageIndex = 1;
            pageCount=0;
            nowPageIndex.setText(String.valueOf(pageIndex));
            totalPageNum.setText(String.valueOf(pageCount));
            firstButton.setDisable(true);
            preButton.setDisable(true);
            postButton.setDisable(true);
            lastButton.setDisable(true);
        }else {
            nowPageIndex.setText(String.valueOf(pageIndex));
            totalPageNum.setText(String.valueOf(pageCount));
            // 预先激活所有
            firstButton.setDisable(false);
            preButton.setDisable(false);
            postButton.setDisable(false);
            lastButton.setDisable(false);
            if (1 == pageIndex){
                firstButton.setDisable(true);
                preButton.setDisable(true);
            }
            if (pageIndex == pageCount){
                postButton.setDisable(true);
                lastButton.setDisable(true);
            }
        }
    }

}
