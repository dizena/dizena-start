package org.dizena.common.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ResultCode {
    SUCCESS(200, "ok"),
    ERROR(500,""),

    /* 参数错误 1001-1999*/
    PARAM_IS_INVALID(1001, "参数无效"),

    /* 用户错误 2001-2999*/
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_LOGIN_ERROR(2002, "用户账户或者密码错误"),
    USER_LOCKED(2003, "用户锁定"),
    USER_ACCOUNT_EXIST(2004,"账户已存在"),

    TOKEN_ERROR(500,"Token不存在或错误"),
    ROLE_AUTH_ERROR(4000,"该角色没有对应的权限"),
    ;

    private Integer code;
    private String msg;

    public Integer code() {
        return this.code;
    }

    public String msg() {
        return this.msg;
    }

}
