package org.dizena.modules.master.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "t_master_black")
@NoArgsConstructor
@Data
public class BlackUser {
    @Id
    private String id;
    private String uid;
    private String toUid;
    private String nickname;
    private String pic;

    private Long createTime;
}
