package org.dizena.modules.master.bean.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResVO implements Serializable
{
    private String id;
    private String title;
    private String uri;
    private Integer sort;
    private Integer level;

    private String pid;
    private String icon;
    private List<ResVO> children;
}
