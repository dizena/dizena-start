package org.dizena.modules.master.bean.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LineUserVO implements Serializable
{
    /**
     * Session ID
     */
    private String id;
    /**
     * IP
     */
    private String host;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 最后访问时间
     */
    private Long lastAccessTime;

    /**
     * 登录用户
     */
    private String uid;
    /**
     * 用户名称
     */
    private String nickname;
    /**
     * 用户账户
     */
    private String account;

}
