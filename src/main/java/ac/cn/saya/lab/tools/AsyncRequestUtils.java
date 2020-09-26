package ac.cn.saya.lab.tools;

import com.alibaba.fastjson.JSONObject;
import javafx.concurrent.Task;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

/**
 * @Title: AsyncRequestUtils
 * @ProjectName gui-lab
 * @Description: TODO
 * @Author saya
 * @Date: 2020/9/25 22:36
 * @Description: 异步请求
 */

public class AsyncRequestUtils extends Task<Result<Object>> {

    /**
     * 请求参数
     */
    private JSONObject parmar;

    /**
     * 请求的方法
     */
    private Function<JSONObject, Result<Object>> request;

    /**
     * 执行状态，false：未完成，true：完成
     */
    private boolean finishStatus = false;

    private Result<Object> result;

    public AsyncRequestUtils(JSONObject parmar, Function<JSONObject, Result<Object>> request) {
        this.parmar = parmar;
        this.request = request;
    }

    public boolean getFinishStatus() {
        return finishStatus;
    }

    public Result<Object> getResult() {
        return result;
    }

    /**
     * Computes a result, or throws an exception if unable to do so.
     *
     * @return computed result
     * @throws Exception if unable to compute a result
     */
    @Override
    public Result<Object> call() throws Exception {
        CompletableFuture<Result<Object>> future = CompletableFuture.supplyAsync(() -> request.apply(parmar));
        try {
            result = (Result<Object>) future.exceptionally(f -> ResultUtil.error(ResultEnum.TIME_OUT)).get();
            finishStatus = true;
        } catch (Exception e) {
            e.printStackTrace();
            finishStatus = true;
            result = ResultUtil.error(ResultEnum.ERROP);
        }
        return result;
    }
}
