package ac.cn.saya.lab.tools;

/**
 * 枚举异常的类型
 */
public enum ResultEnum {

    TIME_OUT(-9, "请求超时"),
    DB_ERROR(-8, "数据库读写失败"),
    NOT_CHECKING(-7, "未登录"),
    NOT_PARAMETER(-6, "缺少参数"),
    FORBID_POWER(-5, "接口已关闭"),
    RollBACK(-4, "操作无效，数据回滚"),
    NOT_EXIST(-3, "记录不存在"),
    ERROP(-2, "处理失败"),
    UNKONW_ERROR(-1, "未知错误"),
    SUCCESS(0, "处理成功");

    private final int code;

    private final String msg;

    ResultEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}