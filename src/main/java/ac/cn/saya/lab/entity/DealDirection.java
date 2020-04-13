package ac.cn.saya.lab.entity;

/**
 * @Title: DealDirection
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author Administrator
 * @Date: 2020/4/13 0013 10:34
 * @Description: 交易流向
 */

public class DealDirection {

    /**
     * 序号
     */
    private Integer type;

    /**
     * 名称
     */
    private String description;

    public DealDirection() {
    }

    public DealDirection(Integer type, String description) {
        this.type = type;
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
