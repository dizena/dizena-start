package org.dizena.modules.master.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "t_master_relation")
@NoArgsConstructor
@Data
public class Relation {
    @Id
    private String id;
    private String uid;
    private String toUid;
    private String backName;
    private String backDesc;

    private Integer isFriend;
    private Long createTime;

}
