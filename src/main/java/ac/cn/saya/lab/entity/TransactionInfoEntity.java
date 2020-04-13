package ac.cn.saya.lab.entity;

import java.io.Serializable;

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
    private Double currencyNumber;

    /**
     * 交易明细
     */
    private String currencyDetails;

    public TransactionInfoEntity() {
    }

    public TransactionInfoEntity(Integer flog, Double currencyNumber, String currencyDetails) {
        this.flog = flog;
        this.currencyNumber = currencyNumber;
        this.currencyDetails = currencyDetails;
    }
}
