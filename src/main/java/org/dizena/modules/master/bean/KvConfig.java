package org.dizena.modules.master.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "t_master_config")
@NoArgsConstructor
@Data
public class KvConfig implements Serializable
{
    @Id
    private String id;
    @Indexed(unique = true)
    private String key;
    private String value;
    private String description;
    private Long createTime;
}
