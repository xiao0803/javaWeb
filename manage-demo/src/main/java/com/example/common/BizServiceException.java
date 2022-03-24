package com.example.common;

public class BizServiceException extends RuntimeException {

    /**
     * 异常码
     */
    private String code;

    /**
     * 异常消息
     */
    private String msg;

    public BizServiceException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BizServiceException(RespCode respCode) {
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
    }

    public BizServiceException(RespCode respCode, String msg) {
        this.code = respCode.getCode();
        if (msg != null && msg.trim().length() >0){
            this.msg = msg;
        }else {
            this.msg = respCode.getMsg();
        }
    }



    /**
     * Returns the detail message string of this throwable.
     *
     * @return the detail message string of this {@code Throwable} instance
     * (which may be {@code null}).
     */
    @Override
    public String getMessage() {
        return String.format("[%s:%s]",this.code,this.msg);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this == null) {
            return obj == this;
        }
        if (obj instanceof BizServiceException) {
            BizServiceException e = (BizServiceException) obj;
            return e.getCode().equals(this.getCode()) && e.getMsg().equals(this.getMsg());
        } else {
            return false;
        }

    }

    @Override
    public String toString() {
        return String.format("code:[%s],msg:[%s]",code,msg);
    }
}
