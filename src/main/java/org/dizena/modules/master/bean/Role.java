package org.dizena.modules.master.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Document(collection = "t_master_role")
@Data
@NoArgsConstructor
public class Role implements Serializable {

    private static final long serialVersionUID = -8559378515966200924L;
    @Id
    private String id;
    @Indexed
    @NotEmpty
    private String role;

    private List<String> resIds;

    public Role(String id, String role, List<String> resIds) {
        super();
        this.id = id;
        this.role = role;
        this.resIds = resIds;
    }

}
