package ac.cn.saya.lab.entity;

/**
 * @Title: UserEntity
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author Administrator
 * @Date: 2020/3/31 0031 11:37
 * @Description:
 */

public class UserEntity {

    /**
     * 账号
     */
    private String account;

    /**
     * 姓名
     */
    private String name;

    /**
     * 所属角色
     */
    private Long roleId;

    /**
     * 密码
     */
    private String password;

    /**
     * 添加时间
     */
    private String createTime;

    /**
     * 性别：M-男，F-女
     */
    private String gender;

    /**
     * 最后登录时间
     */
    private String lastLoginTime;

    /**
     * 是否可用：0-否，1-是
     */
    private Integer isAble;

    /**
     * 备注
     */
    private String remark;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 行政区域编号
     */
    private String regionId;

    /**
     * 行政区域
     */
    private String regionName;

    public UserEntity() {
    }

    public UserEntity(String account, String name, String roleName, String createTime) {
        this.account = account;
        this.name = name;
        this.createTime = createTime;
        this.roleName = roleName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(String lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getIsAble() {
        return isAble;
    }

    public void setIsAble(Integer isAble) {
        this.isAble = isAble;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
}
