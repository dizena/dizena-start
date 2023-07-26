package org.dizena.modules.master.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "t_master_log")
@NoArgsConstructor
@Data
public class LogLogin {
    @Id
    private String id;
    private String uid;
    private String useAccount;
    private Long createTime;
}
