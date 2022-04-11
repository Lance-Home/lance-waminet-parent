package com.waminet.common.tools.exception;


import com.waminet.common.tools.utils.MessageUtils;

/**
 * 自定义异常
 *
 * @author Lance
 * @version 1.0.0
 * @data 2022/04/11 16:23
 */
public class RenException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;

    public RenException(int code) {
        this.code = code;
        this.msg = MessageUtils.getMessage(code);
    }

    public RenException(int code, String... params) {
        this.code = code;
        this.msg = MessageUtils.getMessage(code, params);
    }

    public RenException(int code, Throwable e) {
        super(e);
        this.code = code;
        this.msg = MessageUtils.getMessage(code);
    }

    public RenException(int code, Throwable e, String... params) {
        super(e);
        this.code = code;
        this.msg = MessageUtils.getMessage(code, params);
    }

    public RenException(String msg) {
        super(msg);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
    }

    public RenException(String msg, Throwable e) {
        super(msg, e);
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
