package ac.cn.saya.lab.entity;

import java.io.Serializable;

/**
 * @Title: TransactionTypeEntity
 * @ProjectName MyDubbo
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/8/5 21:28
 * @Description:交易类别表模型
 */
public class TransactionTypeEntity implements Serializable {

    private static final long serialVersionUID = 4596063478097121895L;
    /**
     * 类别号
     */
    private Integer id;
    /**
     * 类别描述
     */
    private String transactionType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionTypeEntity() {
    }

    public TransactionTypeEntity(Integer id, String transactionType) {
        this.id = id;
        this.transactionType = transactionType;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }else {
            if(this.getClass() == obj.getClass()){
                TransactionTypeEntity u = (TransactionTypeEntity) obj;
                if(this.getId().equals(u.getId())){
                    return true;
                }else{
                    return false;
                }

            }else{
                return false;
            }
        }
    }
}
