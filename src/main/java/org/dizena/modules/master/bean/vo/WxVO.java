package org.dizena.modules.master.bean.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class WxVO implements Serializable
{
    private String url;
    private String state;
}
