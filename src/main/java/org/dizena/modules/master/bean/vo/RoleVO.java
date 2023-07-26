package org.dizena.modules.master.bean.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RoleVO implements Serializable
{
    private String id;
    private String role;
    private List<String> resIds;
    private List<TreeVO> menu;

}
