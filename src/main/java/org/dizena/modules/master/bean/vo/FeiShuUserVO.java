package org.dizena.modules.master.bean.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class FeiShuUserVO implements Serializable
{
    private String sub;
    private String name;
    private String picture;
    private String open_id;
    private String union_id;

    private String en_name;
    private String tenant_key;
    private String avatar_url;
    private String avatar_thumb;
    private String avatar_middle;

    private String avatar_big;
    private String email;
    private String user_id;
    private String mobile;

}
