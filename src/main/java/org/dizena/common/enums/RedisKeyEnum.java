package org.dizena.common.enums;

import lombok.Getter;

@Getter
public enum RedisKeyEnum {
    CODE_MOBILE("dxy:code:mobile:"),
    CODE_EMAIL("dxy:code:email:"),
    CODE_IMAGE("dxy:code:image:"),

    CONFIG_INFO("dxy:config:info:"),

    POSITION_INFO("dxy:position:"),

    LOCK_BUSINESS_EMOTION("dxy:lock:business:"),
    LOCK_COMMENT_EMOTION("dxy:lock:comment:");

    private String key;

    RedisKeyEnum(String key) {
        this.key = key;
    }

    public String key(Object unique) {
        return key + unique;
    }
}
