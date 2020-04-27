package ac.cn.saya.lab.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Title: TransactionInfoEntity
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author Administrator
 * @Date: 2020/4/13 0013 15:33
 * @Description: 交易详情表
 */

public class TransactionInfoEntity implements Serializable {

    private static final long serialVersionUID = -4390626752357452125L;

    /**
     * '明细编号',
     */
    private Integer id;

    /**
     * '流水号',
     */
    private Integer tradeId;

    /**
     * '存入/支出标志位(1:入，2:出)',
     */
    private Integer flog;

    /**
     * '交易额',
     */
    private BigDecimal currencyNumber;

    /**
     * 交易明细
     */
    private String currencyDetails;

    public TransactionInfoEntity() {
    }

    public TransactionInfoEntity(Integer flog, BigDecimal currencyNumber, String currencyDetails) {
        this.flog = flog;
        this.currencyNumber = currencyNumber;
        this.currencyDetails = currencyDetails;
    }

    public TransactionInfoEntity(Integer id, Integer flog, BigDecimal currencyNumber, String currencyDetails) {
        this.id = id;
        this.flog = flog;
        this.currencyNumber = currencyNumber;
        this.currencyDetails = currencyDetails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public Integer getFlog() {
        return flog;
    }

    public void setFlog(Integer flog) {
        this.flog = flog;
    }

    public BigDecimal getCurrencyNumber() {
        return currencyNumber;
    }

    public void setCurrencyNumber(BigDecimal currencyNumber) {
        this.currencyNumber = currencyNumber;
    }

    public String getCurrencyDetails() {
        return currencyDetails;
    }

    public void setCurrencyDetails(String currencyDetails) {
        this.currencyDetails = currencyDetails;
    }
}
