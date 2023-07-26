package org.dizena.modules.master.bean.vo;

import lombok.Data;

@Data
public class RelationVO {
    private String id;
    private String uid;
    private String toUid;
    private String backName;
    private String backDesc;
    private Integer isFriend;
    private Long createTime;

    private String pic;
    private String nickname;
    private String description;
    private String sex;
    private Integer birth;
}
