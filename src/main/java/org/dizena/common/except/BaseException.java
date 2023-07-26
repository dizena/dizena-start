package org.dizena.common.except;

import org.dizena.common.enums.ResultCode;
import org.slf4j.event.Level;

/**
 * 自定义运行时异常 所有自定义异常需继承此类
 */
public abstract class BaseException extends RuntimeException {
    /**
     * 日志级别
     */
    protected Level logLevel = Level.ERROR;
    /**
     * 状态码
     */
    protected int code = 500;
    /**
     * 数据对象
     */
    protected Object data = null;

    public BaseException() {
        super();
    }

    /**
     *
     * @param code
     */
    public BaseException(ResultCode code) {
        super(code.msg());
        this.code = code.code();
    }

    /**
     * 构造自定义异常
     *
     * @param message 异常信息
     */
    public BaseException(String message) {
        super(message);
    }

    /**
     * 构造自定义异常
     *
     * @param message 异常信息
     * @param data    返回数据
     */
    public BaseException(String message, Object data) {
        super(message);
        this.data = data;
    }

    /**
     * 构造自定义异常
     *
     * @param code    异常状态码
     * @param message 异常信息
     * @param data    返回数据
     */
    public BaseException(int code, String message, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    /**
     * 构造自定义异常
     *
     * @param code    异常状态码
     * @param message 异常信息
     */
    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public int getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }
}
