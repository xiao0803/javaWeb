package com.example.common;

public class RespUtil {


    /***
     * 返回成功，结果为空
     *
     */
    public static <T>RespResult<T> success() {
        RespResult<T> result = new RespResult<>(RespSystemCode.SUCCESS);
        return result;
    }

    /**
     * 返回成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> RespResult<T> success(T data) {
        RespResult<T> result = new RespResult<>(RespSystemCode.SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 返回系统异常
     * 为区分哪个服务出现系统异常，不建议使用此方法了
     *
     * @return
     */
    @Deprecated
    public static <T>RespResult<T> error() {
        RespResult<T> result = new RespResult<>(RespSystemCode.SYSTEM_ERROR);
        return result;
    }

    /**
     * 返回失败
     *
     * @param source 错误码
     * @return
     */
    public static <T>RespResult<T> fail(RespCode source) {
        RespResult<T> result = new RespResult<>(source);
        return result;
    }

    /**
     * 返回失败
     *
     * @param source 错误码
     * @return
     */
    public static <T> RespResult<T> fail(RespCode source, T data) {
        RespResult<T> result = new RespResult<>(source);
        result.setData(data);
        return result;
    }

    /**
     * 返回失败
     *
     * @param source  错误码
     * @param message 重写错误信息
     * @return
     */
    public static <T>RespResult<T> fail(RespCode source, String message) {
        RespResult<T> result = new RespResult<>(source);
        result.setRespMsg(message);
        return result;
    }

    /**
     * 返回失败
     *
     * @param code
     * @param msg
     * @return
     */
    public static <T> RespResult<T> fail(String code, String msg) {
        RespResult<T> result = new RespResult<>(code, msg);
        return result;
    }

    /**
     * 根据返回码判断处理是否成功
     * @param respResult
     * @return
     */
    public static boolean isSuccess(RespResult respResult){
        if (respResult == null) {
            throw new BizServiceException(RespSystemCode.SYSTEM_ERROR);
        }
        if (RespSystemCode.SUCCESS.getCode().equals(respResult.getRespCode())) {
            return true;
        }
        return false ;
    }

    /**
     * 判断返回的是否是调用外部接口超时异常码
     * @param respResult
     * @return
     */
    public static boolean isSocketTimeOut(RespResult respResult){
        return RespSystemCode.SOCKET_READ_TIMEOUT.getCode().equals(respResult.getRespCode());
    }

    /**
     * 判断处理是否失败
     * @param respResult
     * @return
     */
    public static boolean isFailed(RespResult respResult){
        return !isSuccess(respResult);
    }


    /**
     * 返回码为000000时返回结果，否则抛出ServiceException
     *
     * @param respResult
     * @param <T>
     * @return
     */
    public static <T> T getRespResult(RespResult<T> respResult) {
        if (respResult == null) {
            throw new BizServiceException(RespSystemCode.SYSTEM_ERROR);
        }
        if (RespSystemCode.SUCCESS.getCode().equals(respResult.getRespCode())) {
            return respResult.getData();
        } else {
            throw new BizServiceException(respResult.getRespCode(), respResult.getRespMsg());
        }
    }

    /**
     * 返回码为000000时返回结果，否则抛出自定义的ServiceException
     * @param respResult
     * @param errorCode
     * @param errorMsg
     * @return
     */
    public static <T> T getRespResultWithErrorCodeAndMsg(RespResult<T> respResult, String errorCode, String errorMsg) {
        if (respResult == null) {
            throw new BizServiceException(errorCode, errorMsg);
        }
        if (RespSystemCode.SUCCESS.getCode().equals(respResult.getRespCode())) {
            return respResult.getData();
        } else {
            throw new BizServiceException(errorCode, errorMsg);
        }
    }

    /**
     * 返回码为000000时返回结果，否则抛出自定义的ServiceException
     * @param respResult
     * @param error
     * @return
     */
    public static <T> T getRespResultWithErrorCodeAndMsg(RespResult<T> respResult, RespCode error) {
        if (respResult == null) {
            throw new BizServiceException(error);
        }
        if (RespSystemCode.SUCCESS.getCode().equals(respResult.getRespCode())) {
            return respResult.getData();
        } else {
            throw new BizServiceException(error);
        }
    }

    /**
     * 返回码为000000时返回业务结果，为空时，返回空
     * @param respResult
     * @return
     */
    public static <T> T getRespResultWithNULL(RespResult<T> respResult) {
        if (respResult == null) {
            return null;
        }
        if (RespSystemCode.SUCCESS.getCode().equals(respResult.getRespCode())) {
            return respResult.getData();
        } else {
            throw new BizServiceException(respResult.getRespCode(), respResult.getRespMsg());
        }
    }

    /**
     * 不校验返回码是否成功，直接获取结果
     *
     * @param respResult
     * @param <T>
     * @return
     */
    public static <T> T getRespResultNoCheck(RespResult<T> respResult) {
        if (respResult == null) {
            throw new BizServiceException(RespSystemCode.SYSTEM_ERROR);
        }
        return respResult.getData();
    }
}
