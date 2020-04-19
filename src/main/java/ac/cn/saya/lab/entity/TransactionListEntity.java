package ac.cn.saya.lab.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @Title: TransactionListEntity
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author Administrator
 * @Date: 2020/4/13 0013 15:37
 * @Description: 交易表
 */

public class TransactionListEntity implements Serializable {

    private static final long serialVersionUID = -2361499569268952309L;


    /**
     * 流水编号
     */
    private Integer tradeId;
    /**
     * 存入
     */
    private Double deposited;
    /**
     * 所属用户
     */
    private String source;
    /**
     * 支出
     */
    private Double expenditure;
    /**
     * 交易日
     */
    private String tradeDate;
    /**
     * 交易类别
     */
    private Integer tradeType;
    /**
     * 交易金额
     */
    private Double currencyNumber;
    /**
     * 摘要
     */
    private String transactionAmount;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 修改时间
     */
    private String updateTime;
    /**
     * 交易类别
     */
    private TransactionTypeEntity tradeTypeEntity;
    /**
     * 明细表
     */
    private List<TransactionInfoEntity> infoList;

    public TransactionListEntity() {
    }

    public TransactionListEntity(String transactionAmount,String tradeDate, TransactionTypeEntity tradeTypeEntity, List<TransactionInfoEntity> infoList) {
        this.transactionAmount = transactionAmount;
        this.tradeDate = tradeDate;
        this.tradeTypeEntity = tradeTypeEntity;
        this.infoList = infoList;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public Double getDeposited() {
        return deposited;
    }

    public void setDeposited(Double deposited) {
        this.deposited = deposited;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Double getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(Double expenditure) {
        this.expenditure = expenditure;
    }

    public String getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Integer getTradeType() {
        return tradeType;
    }

    public void setTradeType(Integer tradeType) {
        this.tradeType = tradeType;
    }

    public Double getCurrencyNumber() {
        return currencyNumber;
    }

    public void setCurrencyNumber(Double currencyNumber) {
        this.currencyNumber = currencyNumber;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public TransactionTypeEntity getTradeTypeEntity() {
        return tradeTypeEntity;
    }

    public void setTradeTypeEntity(TransactionTypeEntity tradeTypeEntity) {
        this.tradeTypeEntity = tradeTypeEntity;
    }

    public List<TransactionInfoEntity> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<TransactionInfoEntity> infoList) {
        this.infoList = infoList;
    }
}
