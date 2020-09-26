package ac.cn.saya.lab.entity;



import java.io.Serializable;

/**
 * @Title: TransactionAmountEntity
 * @ProjectName MyDubbo
 * @Description: TODO
 * @Author Saya
 * @Date: 2018/8/5 21:28
 * @Description:交易摘要表模型
 */
public class TransactionAmountEntity implements Serializable {

    private static final long serialVersionUID = 4596063478097121896L;

    /**
     * 类别号
     */
    private Integer id;

    /**
     * 存入/支出标志位(1:入，2:出)
     */
    private Integer flog;

    /**
     * 摘要描述
     */
    private String tag;

    public TransactionAmountEntity() {
    }

    public TransactionAmountEntity(Integer id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public TransactionAmountEntity(Integer id, Integer flog, String tag) {
        this.id = id;
        this.flog = flog;
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFlog() {
        return flog;
    }

    public void setFlog(Integer flog) {
        this.flog = flog;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null){
            return false;
        }else {
            if(this.getClass() == obj.getClass()){
                TransactionAmountEntity u = (TransactionAmountEntity) obj;
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

    @Override
    public String toString() {
        return "交易编号：" + id + ", 摘要描述：'" + tag;
    }
}
