package ac.cn.saya.lab.tools;

import java.util.Optional;

/**
 * 统一返回的外包装类
 */
public class ResultUtil {

    public ResultUtil() {
    }

    /**
     * 用于查询，添加，修改等方法返回值
     *
     * @param object
     * @return
     */
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        //返回执行成功后的模型
        result.setData(object);
        return result;
    }

    /**
     * 用于修改、删除等方法返回的值，只返回操作的结果
     *
     * @return
     */
    public static Result success() {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("成功");
        return result;
    }

    /**
     * 用于特殊场景下的返回值（eg：查询学生注册状态）
     *
     * @param code
     * @param msg
     * @param object
     * @return
     */
    public static Result success(int code, String msg, Object object) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        //返回执行成功后的模型
        result.setData(object);
        return result;
    }

    /**
     * 用于错误，异常等方法返回值
     *
     * @param code
     * @param msg
     * @return
     */
    public static Result error(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * @描述 用于返回枚举错误
     * @参数
     * @返回值
     * @创建人 saya.ac.cn-刘能凯
     * @创建时间 2020-03-15
     * @修改人和其它信息
     */
    public static Result error(ResultEnum resultEnum) {
        Result result = new Result();
        result.setCode(resultEnum.getCode());
        result.setMsg(resultEnum.getMsg());
        return result;
    }

    /**
     * 检查返回体是否成功
     *
     * @param result
     * @param <T>
     * @return 正常为true
     */
    public static <T> boolean checkSuccess(Result<T> result) {
        // 可能为空
        Optional<Result<T>> ofNull = Optional.ofNullable(result);
        // 判定Optional中是否有值
        boolean bool = ofNull.isPresent();
        if (bool && result.getCode() == ResultEnum.SUCCESS.getCode()) {
            return true;
        }
        return false;
    }

    /**
     * 提取包装类中的long
     *
     * @param result
     * @return
     */
    public static long extractLong(Result<Long> result) {
        boolean success = checkSuccess(result);
        if (success) {
            return result.getData();
        }
        return 0;
    }

    /**
     * 提取包装类中的list
     *
     * @param result
     * @return
     */
    public static <T> T extractList(Result<T> result) {
        boolean success = checkSuccess(result);
        if (success) {
            return result.getData();
        }
        return null;
    }

}