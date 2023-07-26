package org.dizena.modules.master.bean.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MenuVO implements Serializable
{
    private String id;
    private String title;
    private String icon;
    private String index;
    private List<MenuVO> subs;

    public MenuVO(String id,String title, String icon, String index)
    {
        this.id = id;
        this.title = title;
        this.icon = icon;
        this.index = index;
    }
}
