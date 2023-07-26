package org.dizena.modules.master.bean.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class TreeVO implements Serializable
{
    private String id;
    private String label;
    private List<TreeVO> children;
}
