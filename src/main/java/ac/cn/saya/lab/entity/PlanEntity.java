package ac.cn.saya.lab.entity;

import java.io.Serializable;

/**
 * @Title: PlanEntity
 * @ProjectName DataCenter
 * @Description: TODO
 * @Author Saya
 * @Date: 2019/1/21 22:49
 * @Description:
 * 日程计划实体类
 */
public class PlanEntity implements Serializable {

    private static final long serialVersionUID = 6740035219118171437L;
    /**
     * 计划编号
     */
    private Integer id;

    /**
     * 创建者
     */
    private String source;

    /**
     * 描述
     */
    private String describe;

    /**
     * 施行日期
     */
    private String plandate;

    /**
     * 创建时间
     */
    private String createtime;

    /**
     * 修改时间
     */
    private String updatetime;

    /**
     * 该条记录在本月的序号
     */
    private Integer number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPlandate() {
        return plandate;
    }

    public void setPlandate(String plandate) {
        this.plandate = plandate;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public PlanEntity() {
    }
}
